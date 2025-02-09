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
     * modal取得
     * @param modalType {String} モーダルの種別
     * @param num formNum
     * @returns {Modal}
     */
    getModal(modalType, num=null) {
        return num!==null ? new Modal(this.#modals[modalType][num]) : new Modal(this.#modals[modalType]);
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
     * ✕ボタンの表示
     * @param formNum formの項番
     * @param placeId 目的地の項番
     */
    #displayDeleteButton(formNum, placeId) {
        const deleteBtn = document.getElementById(`modalDeleteBtn${formNum}`);
        deleteBtn.classList.remove('hidden');

        deleteBtn.addEventListener('click', () => {
            const csrfToken = document.querySelector('meta[name="_csrf"]').content;
            const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
            try {
                const response = fetch(`/api/delete-place?id=${placeId}`, {
                    method: 'POST',
                    headers: {
                        [csrfHeaderName]: csrfToken
                    }
                });
                if (!response.ok) throw new Error(`送信エラー：${response.status}`);

                // /api/delete-placeでのレスポンス
                const data = response.json();
                if (data.status === 'Failed') throw new Error(`APIエラー：${data}が発生しました`);
            } catch (error) {
                console.error(error);
            }
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
     * @param placeId {number | null} 目的地のid
     */
    changeToggleDisplay(modalType, formNum = null, placeId = null) {
        if (modalType === ModalType.start || modalType === ModalType.updateStart) {
            this.#changeStartDisplay(modalType);
            return;
        }
        if (modalType === ModalType.end || modalType === ModalType.updateEnd) {
            this.#changeEndDisplay(modalType);
            return;
        }
        if (modalType === ModalType.places || modalType === ModalType.updatePlaces) {
            this.#changePlaceDisplay(modalType, formNum, placeId);
        }
    }

    #changeStartDisplay(modalType) {
        const timeSpan = document.getElementById('startTimeSpan'); // 時間spanタグ取得
        const startTime = modalType === ModalType.start
            ? document.getElementById('startTime')
            : document.getElementById('startUpdateTime');
        timeSpan.textContent = startTime.value; // 開始時間を入れる
        timeSpan.classList.remove('absolute');

        const placeSpan = document.getElementById('startPlaceSpan'); // 場所名spanタグ
        const startPlace = modalType === ModalType.start
            ? document.getElementById('startPlace')
            : document.getElementById('startUpdatePlace');
        placeSpan.textContent = startPlace.value; // spanの文字を場所名に
    }

    #changeEndDisplay(modalType) {
        const placeSpan = document.getElementById('endPlaceSpan'); // spanタグ取得
        const endPlace = modalType === ModalType.end
            ? document.getElementById('endPlace')
            : document.getElementById('endUpdatePlace');
        placeSpan.textContent = endPlace.value; // spanの文字を場所名に
    }

    #changePlaceDisplay(modalType, formNum, placeId) {
        // buttonの子要素のspanタグ取得
        const timeSpan = document.getElementById(`placeTimeSpan${formNum}`);
        const placeSpan = document.getElementById(`placeNameSpan${formNum}`);
        const budgetSpan = document.getElementById(`budgetSpan${formNum}`);
        const stayTimeSpan = document.getElementById(`stayTimeSpan${formNum}`);

        const placeInput = modalType === ModalType.places
            ? document.getElementById(`place${formNum}`) : document.getElementById(`updatePlace${formNum}`);
        const desiredStartTimeInput = modalType === ModalType.places
            ? document.getElementById(`desiredStartTime${formNum}`) : document.getElementById(`placeUpdateDesiredStart${formNum}`);
        const desiredEndTimeInput = modalType === ModalType.places
            ? document.getElementById(`desiredEndTime${formNum}`) : document.getElementById(`placeUpdateDesiredEnd${formNum}`);
        const budgetInput = modalType === ModalType.places
            ? document.getElementById(`budget${formNum}`) : document.getElementById(`placeUpdateBudget${formNum}`);
        const stayTimeInput = modalType === ModalType.places
            ? document.getElementById(`stayTime${formNum}`) : document.getElementById(`updateStayTime${formNum}`);

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

        this.#displayDeleteButton(formNum, placeId);
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
