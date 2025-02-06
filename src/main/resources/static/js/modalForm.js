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
        if (!formValidate.validate(modalType,formNum)) {
            errorMessage.displayFormError(modalType, formNum);
            return;
        }
        // エラーメッセージ削除
        errorMessage.hiddenFormError(modalType, formNum);

        // api/create-planに送信
        const formData = new FormData(e.target);
        if (modalType === ModalType.places) {
            this.#setEndTime(formNum, formData);
            formData.delete(`stayTime${formNum}`);
        }
        await this.postCreatePlaceAPI(formData, modalType, formNum);
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
