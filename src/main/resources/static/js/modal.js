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
     * @Param num formId-1
     */
    addButtonEvent(modalType, num) {
        const modal = this.getModal(modalType, num);
        const toggleBtn = this.getToggleBtn(modalType, num);
        const closeBtn = this.getCloseBtn(modalType, num);

        // イベントのアタッチ
        toggleBtn.addEventListener('click', () => modal.toggle() );
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
        if (modalType === ModalType.places) {
            return new Modal(this.#modals.places[num]);
        }
        return new Modal(this.#modals[modalType]);
    }

    /**
     * toggle取得
     * @param modalType {String} モーダルの種別
     * @param num formNum
     * @returns {*}
     */
    getToggleBtn(modalType, num=null) {
        if (modalType === ModalType.places) {
            return this.#toggleButtons.places[num];
        }
        return this.#toggleButtons[modalType];
    }

    /**
     * close取得
     * @param modalType {String} モーダルの種別
     * @param num formNum
     * @returns {*}
     */
    getCloseBtn(modalType, num=null) {
        if (modalType === ModalType.places) {
            return this.#closeButtons.places[num];
        }
        return this.#closeButtons[modalType];
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
    setPlacesUpdateModal() {
        this.#modals.updatePlaces.push(document.getElementById('placesUpdateModal'));
        this.#closeButtons.updatePlaces.push(document.getElementById('placesUpdateClose'));

        // autocomplete適用
        this.setAutoComplete(document.getElementById(`placeModal${placeNum.value()}`));
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
     * 出発地点の表示を変更
     */
    changeStartDisplay() {
        const timeSpan = document.getElementById('startTimeSpan'); // 時間spanタグ取得
        const placeSpan = document.getElementById('startPlaceSpan'); // 場所名spanタグ

        const startTime = document.getElementById('startTime');
        timeSpan.textContent = startTime.value; // 開始時間を入れる
        const startPlace = document.getElementById('startPlace');
        placeSpan.textContent = startPlace.value; // spanの文字を場所名に

        timeSpan.classList.remove('absolute');
    }

    /**
     * 終了地点の表示を変更
     */
    changeEndDisplay() {
        const placeSpan = document.getElementById('endPlaceSpan'); // spanタグ取得

        const endPlace = document.getElementById('endPlace');
        placeSpan.textContent = endPlace.value; // spanの文字を場所名に
    }

    /**
     * 目的地の表示を変更
     * @param formNum formの項番
     */
    changePlaceDisplay(formNum) {
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
        if (!budgetInput.value) budgetSpan.textContent = '予算：----' + '円';
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
        const newTarget = num ? `${modalType}UpdateModal${num}` : `${modalType}UpdateModal`;

        // data-modal-target data-modal-toggleを変更
        toggleBtn.setAttribute('data-modal-target', newTarget);
        toggleBtn.setAttribute('data-modal-toggle', newTarget);

        // createのFormを削除
        const createForm = this.getModal(modalType, num);
        if (!createForm) return;
        createForm.remove();
    }
}

class ModalForm {
    #startFormElement;
    placeFormElement = [];
    #endFormElement;
    #startUpdateFormElement;
    #endUpdateFormElement;
    #placesUpdateFormElement;

    constructor() {
        this.#startFormElement = document.getElementById('startPlaceForm');
        for (let i = 0; i <= placeNum.value(); i++) {
            this.placeFormElement.push(document.getElementById(`placeForm${i}`));
        }
        this.#endFormElement = document.getElementById('endPlaceForm');
        this.initFormEvent();
    }

    /**
     * #startFormElement placeFormElement #endFormElementにsubmitイベント割り当て
     */
    initFormEvent() {
        if (this.#startFormElement) this.#startFormElement.addEventListener('submit', (e) => this.#createFormSubmit(e, ModalType.start) );
        if (this.#endFormElement) this.#endFormElement.addEventListener('submit', (e) => this.#createFormSubmit(e, ModalType.end) );
        if (this.placeFormElement) this.placeFormElement.forEach((element) =>
            element.addEventListener('submit', async(e) => await this.#createFormSubmit(e, ModalType.places)));
    }

    /**
     * CreateFormのsubmitイベント
     * @param e
     * @param modalType
     * @param formNum
     * @returns {Promise<void>}
     */
    async #createFormSubmit(e, modalType, formNum=null) {
        e.preventDefault();
        if (modalType === ModalType.places) {
            formNum = Number(e.target.id.replace('placeForm',''));
        }

        // 値の検証（nullがあるか）
        switch (modalType) {
        case ModalType.start:
            if (!this.#startFormCheck()) {
                this.#setErrorMessage('startError', '出発地点・予定時間を正しく入力してください。');
                return;
            }
            this.#setErrorMessage('startError', '');
            break;
        case ModalType.end:
            if (!this.#endFormCheck()) {
                this.#setErrorMessage('endError', '終了地点を正しく入力してください。');
                return;
            }
            this.#setErrorMessage('endError', '');
            break;
        case ModalType.places:
            if (!this.#placeFormCheck(formNum)) {
                this.#setErrorMessage(`placeError${formNum}`, '目的地を正しく入力してください。');
                return;
            }
            this.#setErrorMessage(`placeError${formNum}`, '');
            break;
        }

        // api/create-planに送信
        const formData = new FormData(e.target);
        if (modalType === ModalType.places) {
            this.#setEndTime(formNum, formData);
            formData.delete(`stayTime${formNum}`);
        }
        await this.postCreatePlaceAPI(formData, modalType, formNum);
    }

    /**
     * エラーメッセージの設定
     * @param {string} elementId エラーメッセージを表示する要素のID
     * @param {string} message エラーメッセージ
     */
    #setErrorMessage(elementId, message) {
        document.getElementById(elementId).textContent = message;
    }

    /**
     * 出発地点のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #startFormCheck() {
        const placeName = document.getElementById('startPlace').value;
        const placeId = document.getElementById('startPlaceId').value;
        const lat = document.getElementById('startLat').value;
        const lng = document.getElementById('startLng').value;
        const time = document.getElementById('startTime').value;

        return !!(placeName && placeId && lat && lng && time);
    }

    /**
     * 終了地点のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #endFormCheck() {
        const placeName = document.getElementById('endPlace').value;
        const placeId = document.getElementById('endPlaceId').value;
        const lat = document.getElementById('endLat').value;
        const lng = document.getElementById('endLng').value;

        return !!(placeName && placeId && lat && lng);
    }

    /**
     * 目的地のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #placeFormCheck(num) {
        const placeName = document.getElementById(`place${num}`).value;
        const placeId = document.getElementById(`placeId${num}`).value;
        const lat = document.getElementById(`placeLat${num}`).value;
        const lng = document.getElementById(`placeLng${num}`).value;

        return !!(placeName && placeId && lat && lng);
    }

    /**
     * 出発地点更新のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #updateStartFormCheck() {
        const placeName = document.getElementById('startUpdatePlace').value;
        const placeId = document.getElementById('startUpdatePlaceId').value;
        const lat = document.getElementById('startUpdateLat').value;
        const lng = document.getElementById('startUpdateLng').value;
        const time = document.getElementById('startUpdateTime').value;

        return !!(placeName && placeId && lat && lng && time);
    }

    /**
     * 終了地点更新のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #updateEndFormCheck() {
        const placeName = document.getElementById('endUpdatePlace').value;
        const placeId = document.getElementById('endUpdatePlaceId').value;
        const lat = document.getElementById('endUpdateLat').value;
        const lng = document.getElementById('endUpdateLng').value;

        return !!(placeName && placeId && lat && lng);
    }

    /**
     * 目的地更新のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #updatePlaceFormCheck(num) {
        const placeName = document.getElementById(`updatePlace${num}`).value;
        const placeId = document.getElementById(`placeUpdatePlaceId${num}`).value;
        const lat = document.getElementById(`placeUpdateLat${num}`).value;
        const lng = document.getElementById(`placeUpdateLng${num}`).value;

        return !!(placeName && placeId && lat && lng);
    }

    /**
     * 出発地点の /api/create-placeが成功したとき
     * @param placeId {number} placesテーブルのid
     */
    async #startPlaceCreateSuccess(placeId) {
        // modal関連の動作
        modal.closeModal(ModalType.start);
        modal.changeStartDisplay();

        // startUpdateFormを呼び出す
        const fragment = new Fragment();
        await fragment.initStartUpdateForm(placeId); // 追加フラグメントの初期化
        fragment.addStartUpdateForm(); // HTMLに追加
        modal.setStartUpdateModal(); // 変数にElement追加・autocomplete適用

        // startToggleの data-modal-target data-modal-toggleを変更
        modal.changeToggleTarget(ModalType.start);

        // formのsubmitイベントをアタッチ
        this.#startUpdateFormElement = modal.getModal(ModalType.updateStart);
        this.#startUpdateFormElement.addEventListener('submit', (e) => this.#startUpdateFormSubmit(e));
    }

    /**
     * 終了地点の /api/create-placeが成功したとき
     * @param placeId {number} placesテーブルのid
     */
    async #endPlaceCreateSuccess(placeId) {
        // modal関連の動作
        modal.closeModal(ModalType.end);
        modal.changeEndDisplay();

        // endUpdateFormフラグメントを追加
        const fragment = new Fragment();
        await fragment.initEndUpdateForm(placeId); // 追加フラグメントの初期化
        fragment.addEndUpdateForm(); // HTMLに追加
        modal.setEndUpdateModal(); // 変数にElement追加・autocomplete適用

        // endToggleの data-modal-target data-modal-toggleを変更
        modal.changeToggleTarget(ModalType.end);

        // formのsubmitイベントをアタッチ
        this.#endUpdateFormElement = modal.getModal(ModalType.updateEnd);
        this.#endUpdateFormElement.addEventListener('submit', (e) => this.#endUpdateFormSubmit(e));
    }

    /**
     * 目的地の /api/create-placeが成功したとき
     * @param placeId {number} placesテーブルのid
     * @param formNum {number} formの項番
     */
    async #placesCreateSuccess(placeId, formNum) {
        // modal関連の動作
        modal.closeModal(ModalType.places);
        modal.changePlaceDisplay();

        // 目的地追加フラグメント呼び出し
        await this.newAddPlaceFragment();

        // placesUpdateFormを呼び出す
        const fragment = new Fragment();
        await fragment.initPlacesUpdateForm(placeId, formNum); // fragment初期化
        fragment.addPlacesUpdateForm(); // HTMLに追加
        modal.setPlacesUpdateModal(); // Elementを追加

        // stayTimeのvalueを更新
        this.#setStayTimeValue(formNum);

        // placesToggleの data-modal-target data-modal-toggleを変更
        modal.changeToggleTarget(ModalType.places, formNum);

        // formのsubmitイベントをアタッチ
        this.#placesUpdateFormElement = modal.getModal(ModalType.updatePlaces, formNum);
        this.#placesUpdateFormElement.addEventListener('submit', (e) => this.#placeUpdateFormSubmit(e));
    }

    /**
     * 出発地点の更新submitイベント
     * @param e
     */
    async #startUpdateFormSubmit(e) {
        e.preventDefault();

        // 値の検証
        if (!this.#updateStartFormCheck()) {
            document.getElementById('updateStartError').textContent = '予定時間を正しく入力してください。';
            return;
        }
        document.getElementById('updateStartError').textContent = '';

        const formData = new FormData(e.target);
        await this.postUpdatePlaceAPI(formData, ModalType.start);
    }

    /**
     * 終了地点の更新submitイベント
     * @param e
     */
    async #endUpdateFormSubmit(e) {
        e.preventDefault();

        // 値の検証
        if (!this.#updateEndFormCheck()) {
            document.getElementById('updateEndError').textContent = '予定時間を正しく入力してください。';
            return;
        }
        document.getElementById('updateEndError').textContent = '';

        // api/update-planに送信
        const formData = new FormData(e.target);
        await this.postUpdatePlaceAPI(formData, ModalType.end);
    }

    /**
     * 目的地の更新submitイベント
     * @param e
     */
    async #placeUpdateFormSubmit(e) {
        e.preventDefault();

        const formId = e.target.id;
        const formNum = Number(formId.replace('updatePlaceForm', ));

        // 値の検証
        if (!this.#updatePlaceFormCheck(formNum)) {
            document.getElementById(`placeUpdateError${formNum}`).textContent = '予定時間を正しく入力してください。';
            return;
        }
        document.getElementById(`placeUpdateError${formNum}`).textContent = '';

        const formData = new FormData(e.target);
        // updatePlaceがdisabledなので手動で追加
        const updateNameInput = document.getElementById(`updatePlace${formNum}`);
        formData.append(updateNameInput.name, updateNameInput.value);
        // api/update-planに送信
        await this.postUpdatePlaceAPI(formData, ModalType.places, formNum);
    }

    /**
     * 追加フラグメントを挿入
     * @returns {Promise<void>}
     */
    async newAddPlaceFragment() {
        const newFragment = new Fragment();
        await newFragment.initialize();

        newFragment.addFragment();
        placeNum.increment();
        modal.addPlacesElement();
        modal.addButtonEvent(ModalType.places, placeNum.value()); // 新しいModalにイベント追加
        new ModalForm(); // modalFormイベントをアタッチ
    }

    /**
     * endTimeを(startTime+stayTime)に
     */
    #setEndTime(formNum, formData) {
        const startTime = formData.get('startTime');
        const stayTime = formData.get(`stayTime${formNum}`);
        // startTimeをパースしてDate型に変換
        const [startHours, startMinutes] = startTime.split(':').map(Number);
        const startDate = new Date();
        startDate.setHours(startHours, startMinutes, 0, 0); // 時間, 分, 秒, ミリ秒
        // stayTime(分)を加算
        const endDate = new Date(startDate.getTime() + stayTime * 60000); // 60000ms = 1分
        // endDateをHH:mm形式にフォーマット
        const endHours = String(endDate.getHours()).padStart(2, '0'); // ゼロ埋め
        const endMinutes = String(endDate.getMinutes()).padStart(2, '0'); // ゼロ埋め
        const endTime = `${endHours}:${endMinutes}`;
        // FormDataにendTimeをセット
        formData.set('endTime', endTime);
    }

    /**
     * 更新用FormのstayTimeのvalueを更新
     * @param num {number} formの項番
     */
    #setStayTimeValue(num) {
        const stayTimeInput = document.getElementById(`updateStayTime${num}`);
        const startTime = document.getElementById(`placeUpdateStartTime${num}`).value;
        const endTime = document.getElementById(`placeUpdateEndTime${num}`).value;

        // 開始時間の分数取得
        const [startHours, startMinutes] = startTime.split(':').map(Number);
        const startMin = startHours * 60 + startMinutes;

        // 終了時間の分数取得
        const [endHours, endMinutes] = endTime.split(':').map(Number);
        const endMin = endHours * 60 + endMinutes;

        // 終了時間と開始時間の差分を value に設定
        stayTimeInput.value = endMin - startMin;
    }

    /**
     * /api/crate-placeに送信する
     * @param formData {FormData} 送信するformのデータ
     * @param modalType {String} 送信するformのタイプ
     * @param formNum {number | null} 送信するformの項番 placeFormのみ
     */
    async postCreatePlaceAPI(formData, modalType, formNum=null) {
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
        let placeId = null;
        try {
            // 非同期でPOSTリクエストを送信
            const response = await fetch('/api/create-place', {
                method: 'POST',
                headers: {
                    [csrfHeaderName]: csrfToken
                },
                body: formData,
            });

            // 通信が成功しないとき
            if (!response.ok) {
                throw new Error(`送信エラー: ${response.status}`);
            }

            // /api/create-placeでFailedが返される
            const data = await response.json();
            if (data.status === 'Failed') {
                console.error(`APIエラー：${data}`);
                throw new Error('エラーが発生しました。');
            }
            // 成功時の処理
            placeId = data.placeId;
            if (modalType === ModalType.start) {
                await this.#startPlaceCreateSuccess(placeId);
            } else if (modalType === ModalType.end) {
                await this.#endPlaceCreateSuccess(placeId);
            } else {
                await this.#placesCreateSuccess(placeId, formNum);
            }
        } catch (error) {
            console.error(`エラー詳細：${error}`);
            const errorMessage = '送信中にエラーが発生しました。もう一度お試しください。';
            if (modalType === ModalType.start) {
                document.getElementById('startError').textContent = errorMessage;
            } else if (modalType === ModalType.end) {
                document.getElementById('endError').textContent = errorMessage;
            } else {
                document.getElementById(`placeError${formNum}`).textContent = errorMessage;
            }
        }
    }

    /**
     * /api/update-placeに送信する
     * @param formData {FormData} 送信formData
     * @param modalType {String} 更新するformのType
     * @param formNum {number | null} 送信formの項番
     */
    postUpdatePlaceAPI(formData, modalType, formNum=null) {
        try {
            // 非同期でPOSTリクエストを送信
            fetch('/api/update-place', {
                method: 'POST',
                body: formData,
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`送信エラー: ${response.status}`);
                    }
                    return response.json(); // 必要に応じてレスポンスを処理
                })
                .then(data => {
                    if (data.status === 'Failed')
                        throw new Error('エラーが発生しました。');
                });
        } catch (error) {
            const errorMessage = '送信中にエラーが発生しました。もう一度お試しください。';
            if (modalType === ModalType.updateStart) {
                document.getElementById('updateStartError').textContent = errorMessage;
            } else if (modalType === ModalType.updateEnd) {
                document.getElementById('updateEndError').textContent = errorMessage;
            } else {
                document.getElementById(`placesUpdateModal${formNum}`).textContent = errorMessage;
            }
        }
    }
}

const placeNum = new PlaceNum();
const modal = new ModalElement();

document.addEventListener('DOMContentLoaded', () => {
    new ModalForm();
});
