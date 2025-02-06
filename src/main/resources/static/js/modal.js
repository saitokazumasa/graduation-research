// 送信されるmodalFormの種別
const ModalType = Object.freeze({
    start: 'start',
    end: 'end',
    places: 'places',
    updateStart: 'updateStart',
    updateEnd: 'updateEnd',
    updatePlaces: 'updatePlaces'
});

class PlaceNum {
    #value = 0;

    value() {
        return this.#value;
    }

    increment() {
        this.#value ++;
    }
}

class Fragment {
    #toggle;
    #form;
    #startUpdateForm;
    #endUpdateForm;
    #placesUpdateForm;

    constructor() {
        this.#toggle = null;
        this.#form = null;
        this.#startUpdateForm = null;
        this.#endUpdateForm = null;
        this.#placesUpdateForm = null;
    }

    /**
     * 追加fragmentの初期化
     * @returns {Promise<void>}
     */
    async initialize() {
        // toggle取得 /fragment/modal/placesToggle
        try {
            const response = await fetch(`/fragment/modal/placesToggle?num=${(placeNum.value()+1)}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#toggle = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
        // form取得 /fragment/modal/placesForm
        try {
            const response = await fetch(`/fragment/modal/placesForm?num=${(placeNum.value()+1)}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#form = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * 出発地点更新formフラグメントの初期化
     * @param placeId placesテーブルのid
     * @returns {Promise<void>}
     */
    async initStartUpdateForm(placeId) {
        // 出発地点更新form取得 /fragment/modal/startUpdateForm
        try {
            const response = await fetch(`/fragment/update-modal/startUpdateForm?placeId=${placeId}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#startUpdateForm = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * 終了地点更新formフラグメントの初期化
     * @param placeId placesテーブルのid
     * @returns {Promise<void>}
     */
    async initEndUpdateForm(placeId) {
        // 終了地点更新form取得 /fragment/modal/endUpdateForm
        try {
            const response = await fetch(`/fragment/update-modal/endUpdateForm?placeId=${placeId}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#endUpdateForm = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * 目的地更新formフラグメントの初期化
     * @param placeId placesテーブルのid
     * @param formNum formの項番
     * @returns {Promise<void>}
     */
    async initPlacesUpdateForm(placeId, formNum) {
        // 目的地更新form取得 /fragment/modal/placesUpdateForm?num=
        try {
            const response = await fetch(`/fragment/update-modal/placesUpdateForm?placeId=${placeId}&num=${formNum}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#placesUpdateForm = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * HTMLにfragment追加 toggleとform
     */
    addFragment() {
        if (!this.#toggle || !this.#form) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');

        // id=destination の子要素にToggleを追加
        const destination = document.getElementById('destination');
        const newToggle = document.createElement('div');
        newToggle.innerHTML = this.#toggle;
        destination.appendChild(newToggle);

        // id=formDiv の子要素に Form を追加
        const formDiv = document.getElementById('formDiv');
        const newForm = document.createElement('div');
        newForm.innerHTML = this.#form;
        formDiv.appendChild(newForm);
    }

    /**
     * HTMLにstartUpdateFormフラグメント追加
     */
    addStartUpdateForm() {
        if (!this.#startUpdateForm) return;

        // id=formDivの子要素に #startUpdateForm を追加
        this.#addUpdateFragment(this.#startUpdateForm);
    }

    /**
     * HTMLにendUpdateFormフラグメント追加
     */
    addEndUpdateForm() {
        if (!this.#endUpdateForm) return;

        // id=formDivの子要素に #endUpdateForm を追加
        this.#addUpdateFragment(this.#endUpdateForm);
    }

    /**
     * HTMLにplacesUpdateFormフラグメント追加
     */
    addPlacesUpdateForm() {
        if (!this.#placesUpdateForm) return;

        // id=formDivの子要素に placesUpdateForm を追加
        this.#addUpdateFragment(this.#placesUpdateForm);
    }

    /**
     * id=formDivの子要素に UpdateForm を追加
     */
    #addUpdateFragment(updateForm) {
        const formDiv = document.getElementById('formDiv');
        const newForm = document.createElement('div');
        newForm.innerHTML = updateForm;
        formDiv.appendChild(newForm);
    }
}

class ModalElement {
    /**
     * モーダル要素を格納するオブジェクト
     */
    #modals;
    /**
     * トグルボタンを格納するオブジェクト
     */
    #toggleButtons;
    /**
     * クローズボタンを格納するオブジェクト
     */
    #closeButtons;

    constructor() {
        this.#modals = {
            start: document.getElementById('startModal'),
            end: document.getElementById('endModal'),
            places: [document.getElementById(`placeModal${placeNum.value()}`)],
            updateStart: null,
            updateEnd: null,
            updatePlaces: []
        };

        this.#toggleButtons = {
            start: document.getElementById('startToggle'),
            end: document.getElementById('endToggle'),
            places: [document.getElementById(`placeToggleBtn${placeNum.value()}`)],
        };

        this.#closeButtons = {
            start: document.getElementById('startClose'),
            end: document.getElementById('endClose'),
            places: [document.getElementById(`placeCloseBtn${placeNum.value()}`)],
            updateStart: null,
            updateEnd: null,
            updatePlaces: []
        };
    }

    /**
     * 目的地のelementを配列に追加・autocomplete適用
     */
    addPlacesElement() {
        const modal = document.getElementById(`placeModal${placeNum.value()}`);
        const toggleButton = document.getElementById(`placeToggleBtn${placeNum.value()}`);
        const closeButton = document.getElementById(`placeCloseBtn${placeNum.value()}`);

        this.#modals.places.push(modal);
        this.#toggleButtons.places.push(toggleButton);
        this.#closeButtons.places.push(closeButton);

        // autocomplete適用
        this.setAutoComplete(document.getElementById(`place${placeNum.value()}`));
    }

    /**
     * ModalButtonイベント アタッチ
     * @param modalType {String}
     * @Param num {number | null}
     */
    addButtonEvent(modalType, num = null) {
        const modal = this.getModal(modalType, num);
        const toggleBtn = this.getToggleBtn(modalType, num);
        const closeBtn = this.getCloseBtn(modalType, num);

        // イベントのアタッチ
        toggleBtn.addEventListener('click', () => modal.toggle());
        closeBtn.addEventListener('click', () => {
            modal.hide();
            document.activeElement.blur(); // フォーカスを外す
        });
    }

    /**
     * modal取得
     * @param modalType {String} モーダルの種別
     * @param num formNum
     * @returns {Modal}
     */
    getModal(modalType, num=null) {
        return num!==null ? new Modal(this.#modals[modalType][num]) :new Modal(this.#modals[modalType]);
    }

    /**
     * toggle取得
     * @param modalType {String} モーダルの種別
     * @param num formNum
     * @returns {*}
     */
    getToggleBtn(modalType, num=null) {
        if (modalType.startsWith('update')) {
            modalType = modalType.replace('update', '').toLowerCase();
        }
        return num!==null ? this.#toggleButtons[modalType][num] : this.#toggleButtons[modalType];
    }

    /**
     * close取得
     * @param modalType {String} モーダルの種別
     * @param num formNum
     * @returns {*}
     */
    getCloseBtn(modalType, num=null) {
        return num!==null ? this.#closeButtons[modalType][num] : this.#closeButtons[modalType];
    }

    /**
     * ✕ボタンの表示
     * @param formNum formの項番
     */
    #displayDeleteButton(formNum) {
        const deleteBtn = document.getElementById(`modalDeleteBtn${formNum}`);
        deleteBtn.classList.remove('hidden');

        deleteBtn.addEventListener('click', () => {
            const toggleBtn = this.getToggleBtn(ModalType.places, formNum);
            toggleBtn.classList.add('hidden'); // Modal表示のボタンを隠す
            deleteBtn.classList.add('hidden'); // ✕ボタンを隠す
        });
    }

    /**
     * 出発地点更新のelementを配列に追加・autocomplete適用
     */
    setStartUpdateModal() {
        this.#modals.updateStart = document.getElementById('startUpdateModal');
        this.#closeButtons.updateStart = document.getElementById('startUpdateClose');

        // autocomplete適用
        this.setAutoComplete(document.getElementById('startUpdatePlace'));
    }

    /**
     * 終了地点更新のelementを配列に追加・autocomplete適用
     */
    setEndUpdateModal() {
        this.#modals.updateEnd = document.getElementById('endUpdateModal');
        this.#closeButtons.updateEnd = document.getElementById('endUpdateClose');

        // autocomplete適用
        this.setAutoComplete(document.getElementById('endUpdatePlace'));
    }

    /**
     * 目的地更新のelementを配列に追加・autocomplete適用
     */
    setPlacesUpdateModal(num) {
        this.#modals.updatePlaces.push(document.getElementById(`placesUpdateModal${num}`));
        this.#closeButtons.updatePlaces.push(document.getElementById(`placesUpdateClose${num}`));

        // autocomplete適用
        this.setAutoComplete(document.getElementById(`updatePlace${num}`));
    }

    /**
     * autocomplete適用
     * @param inputElement 適用するInputElement
     */
    setAutoComplete(inputElement) {
        const autoComplete = new AutoComplete(inputElement);
        autoCompleteList.add(autoComplete);
    }

    /**
     * modalを閉じる
     * @param modalType {String} モーダルの種別
     * @param num modalListのnumber(form項番-1)
     */
    closeModal(modalType, num=null) {
        const modal = this.getModal(modalType, num);
        modal.hide();
    }

    /**
     * modal切り替えのトグル表示を変える
     * @param modalType {string} モーダルの種類
     * @param formNum {number | null} フォーム項番
     */
    changeToggleDisplay(modalType, formNum = null) {
        switch (modalType) {
        case ModalType.start:
            this.#changeStartDisplay();
            break;
        case ModalType.end:
            this.#changeEndDisplay();
            break;
        case ModalType.places:
            this.#changePlaceDisplay(formNum);
            break;
        }
    }

    #changeStartDisplay() {
        const timeSpan = document.getElementById('startTimeSpan'); // 時間spanタグ取得
        const placeSpan = document.getElementById('startPlaceSpan'); // 場所名spanタグ

        const startTime = document.getElementById('startTime');
        timeSpan.textContent = startTime.value; // 開始時間を入れる
        const startPlace = document.getElementById('startPlace');
        placeSpan.textContent = startPlace.value; // spanの文字を場所名に

        timeSpan.classList.remove('absolute');
    }

    #changeEndDisplay() {
        const placeSpan = document.getElementById('endPlaceSpan'); // spanタグ取得

        const endPlace = document.getElementById('endPlace');
        placeSpan.textContent = endPlace.value; // spanの文字を場所名に
    }

    #changePlaceDisplay(formNum) {
        // buttonの子要素のspanタグ取得
        const timeSpan = document.getElementById(`placeTimeSpan${formNum}`);
        const placeSpan = document.getElementById(`placeNameSpan${formNum}`);
        const budgetSpan = document.getElementById(`budgetSpan${formNum}`);
        const stayTimeSpan = document.getElementById(`stayTimeSpan${formNum}`);

        // inputの要素取得
        const placeInput = document.getElementById(`place${formNum}`);
        const desiredStartTimeInput = document.getElementById(`desiredStartTime${formNum}`);
        const desiredEndTimeInput = document.getElementById(`desiredEndTime${formNum}`);
        const budgetInput = document.getElementById(`budget${formNum}`);
        const stayTimeInput = document.getElementById(`stayTime${formNum}`);

        /* ---- 表示を変更 ---- */
        placeInput.disabled = true; // 目的地部分をdisabledに
        placeInput.classList.add('bg-gray-100');

        // 場所名
        placeSpan.textContent = placeInput.value;

        // 希望時間
        if (!desiredStartTimeInput.value) timeSpan.textContent = '';
        else timeSpan.textContent = desiredStartTimeInput.value + '~' + desiredEndTimeInput.value;
        timeSpan.classList.remove('absolute');

        // 予算
        if (budgetInput.value !== null) budgetSpan.textContent = '予算：---- ' + '円';
        else budgetSpan.textContent = '予算：' + budgetInput.value + '円';

        // 滞在時間
        if (!stayTimeInput.value) stayTimeSpan.textContent = '滞在時間：30分';
        else stayTimeSpan.textContent = '滞在時間：' + stayTimeInput.value + '分';

        // 緑色の枠線をけす
        const toggleBtn = this.getToggleBtn(ModalType.places, formNum);
        toggleBtn.classList.remove('border-interactive');

        this.#displayDeleteButton(formNum);
    }

    /**
     * 開くModalをUpdateFormに切り替える
     * createのFormを削除
     * @param modalType {String}
     * @param num
     */
    changeToggleTarget(modalType, num=null) {
        const toggleBtn = this.getToggleBtn(modalType, num);

        // '○○UpdateModal' にターゲットを変える
        const newTarget = num!==null ? `${modalType}UpdateModal${num}` : `${modalType}UpdateModal`;

        // data-modal-target data-modal-toggleを変更
        toggleBtn.setAttribute('data-modal-target', newTarget);
        toggleBtn.setAttribute('data-modal-toggle', newTarget);
    }
}

const placeNum = new PlaceNum();
const modal = new ModalElement();
const errorMessage = new ErrorMessage();
const formValidate = new FormValidator();

document.addEventListener('DOMContentLoaded', () => {
    new ModalForm();
});
