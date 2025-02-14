class ModalForm {
    #startFormElement;
    #placeFormElement;
    #endFormElement;
    #startUpdateFormElement;
    #endUpdateFormElement;
    #placesUpdateFormElement = [];

    constructor() {
        // todo: formのkeyからエレメント取得
        this.#startFormElement = !isSetStart ? document.getElementById('startPlaceForm') : null;
        this.#startUpdateFormElement = isSetStart ? document.getElementById('startUpdatePlace') : null;

        this.#placeFormElement = document.getElementById(`placeForm${placeNum.value()}`);
        // すでに目的地が登録されているとき
        if (isSetPlace) {
            for (let i = 0; i < placeNum.value(); i++) {
                this.#placesUpdateFormElement.push(document.getElementById(`updatePlaceForm${i}`));
            }
        }

        this.#endFormElement = !isSetEnd ? document.getElementById('endPlaceForm') : null;
        this.#endUpdateFormElement = isSetEnd ? document.getElementById('endUpdatePlace') : null;

        this.initFormEvent();
    }

    /**
     * 各formにsubmitイベントの割り当て
     */
    initFormEvent() {
        if (this.#startFormElement) this.#startFormElement.addEventListener('submit', (e) => this.#createFormSubmit(e, ModalType.start));
        if (this.#startUpdateFormElement) this.#startUpdateFormElement.addEventListener('submit', (e) => this.#updateFormSubmit(e, ModalType.updateStart));

        if (this.#endFormElement) this.#endFormElement.addEventListener('submit', (e) => this.#createFormSubmit(e, ModalType.end));
        if (this.#endUpdateFormElement) this.#endUpdateFormElement.addEventListener('submit', (e) => this.#updateFormSubmit(e, ModalType.updateEnd));

        if (this.#placeFormElement) this.#placeFormElement.addEventListener('submit', async(e) => await this.#createFormSubmit(e, ModalType.places));
        if (this.#placesUpdateFormElement) this.#placesUpdateFormElement.forEach((element, index) =>
            element.addEventListener('submit', async(e) => await this.#updateFormSubmit(e, ModalType.updatePlaces, index)));
    }

    // /**
    //  * おすすめ目的地のformイベントを割り当て
    //  */
    // #attachRecommendFormEvent() {
    //     // 各form要素に recommend という独自クラスを付与し取得する
    //     document.querySelectorAll('.recommend').forEach(element => {
    //         element.addEventListener('submit', async(e) => {
    //             await this.#createFormSubmit(e, ModalType.recommend);
    //         });
    //     });
    // }

    /**
     * 目的地追加formの更新
     */
    #setPlaceFormElement() {
        this.#placeFormElement = document.getElementById(`placeForm${placeNum.value()}`);
        this.#placeFormElement.addEventListener('submit', async(e) => await this.#createFormSubmit(e, ModalType.places));
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

        // formNumの設定
        if (modalType === ModalType.places) formNum = Number(e.target.id.replace('placeForm',''));
        if (modalType === ModalType.recommend) formNum = Number(e.target.id.replace('recommendForm',''));

        // 値の検証（nullがあるか）
        if (!formValidate.validate(modalType,formNum)) {
            errorMessage.displayFormError(modalType, formNum);
            return;
        }
        // エラーメッセージ削除
        errorMessage.hiddenFormError(modalType, formNum);

        // api/create-planに送信
        const formData = new FormData(e.target);
        // 目的地、おすすめ目的地の終了時間を（startTime + 滞在時間）に
        if (formNum !== null) this.#setEndTime(formNum, formData, modalType);
        if (modalType === ModalType.recommend) {
            const nameInput = document.getElementById(`recommend${formNum}`);
            formData.append(nameInput.name, nameInput.value);
        }
        await this.postCreatePlaceAPI(formData, modalType, formNum);
    }

    /**
     * /api/crate-place成功時処理
     * @param placeId {number} placesテーブルのid
     * @param modalType {string} モーダルの種類
     * @param formNum {number} formの項番(placesの時だけ)
     */
    async #createPlaceSuccess(placeId, modalType, formNum=null) {
        modal.closeModal(modalType, formNum);
        if (modalType === ModalType.recommend) modal.deleteModal();
        // modalの動作
        modal.changeToggleDisplay(modalType, formNum, placeId);

        // 更新formフラグメントを追加
        const fragment = new Fragment();
        switch (modalType) {
        case ModalType.start:
            await fragment.initStartUpdateForm(placeId);
            fragment.addStartUpdateForm();
            modal.setStartUpdateModal();
            break;
        case ModalType.end:
            await fragment.initEndUpdateForm(placeId);
            fragment.addEndUpdateForm();
            modal.setEndUpdateModal();
            break;
        case ModalType.recommend:
        case ModalType.places:
            await fragment.initPlacesUpdateForm(placeId, placeNum.value());
            fragment.addPlacesUpdateForm();
            modal.setPlacesUpdateModal(placeNum.value());
            this.#setStayTimeValue(placeNum.value());
        }

        // modalを切り替えるターゲット変更
        modalType === ModalType.recommend ? modal.changeToggleTarget(ModalType.places, placeNum.value()) : modal.changeToggleTarget(modalType, formNum);
        // // おすすめ目的地フラグメントを呼び出し
        // await this.newAddRecommendFragment();
        // this.#attachRecommendFormEvent();
        // 目的地追加とおすすめ目的地追加時
        if (modalType === ModalType.places || modalType === ModalType.recommend) await this.#newAddPlaceFragment();

        // updateFormのsubmitイベントをアタッチ
        switch (modalType) {
        case ModalType.start:
            this.#startUpdateFormElement = document.getElementById('updateStartForm');
            this.#startUpdateFormElement.addEventListener('submit', (e) => this.#updateFormSubmit(e, ModalType.updateStart));
            break;
        case ModalType.end:
            this.#endUpdateFormElement = document.getElementById('updateEndForm');
            this.#endUpdateFormElement.addEventListener('submit', (e) => this.#updateFormSubmit(e, ModalType.updateEnd));
            break;
        case ModalType.places:
            this.#placesUpdateFormElement.push(document.getElementById(`updatePlaceForm${formNum}`));
            this.#placesUpdateFormElement[formNum].addEventListener('submit', (e) => this.#updateFormSubmit(e, ModalType.updatePlaces, formNum));
        }

        // modalイベントの再アタッチ
        initFlowbite();
    }

    /**
     * 追加フラグメントを挿入
     * @returns {Promise<void>}
     */
    async #newAddPlaceFragment() {
        // 目的地追加フラグメント呼び出し
        const newFragment = new Fragment();
        await newFragment.initialize();
        newFragment.addFragment();

        // form項番を増やす
        placeNum.increment();
        modal.setPlaceElement();
        // 追加した目的地フラグメントを変数に入れる
        this.#setPlaceFormElement();
    }

    // /**
    //  * おすすめ目的地フラグメントを挿入
    //  */
    // async newAddRecommendFragment() {
    //     // おすすめ目的地フラグメント呼び出し
    //     const newFragment = new Fragment();
    //     // ローディング表示を出す
    //     const loading = document.getElementById('loadingDiv');
    //     loading.classList.remove('hidden');
    //     // オススメ目的地を取得する
    //     await newFragment.initRecommendFragment();
    //     newFragment.addRecommendFragment();
    //     // ローディング表示を消す
    //     loading.classList.add('hidden');
    // }

    /**
     * endTimeを(startTime+stayTime)に
     */
    #setEndTime(formNum, formData, modalType) {
        const startTime = formData.get('startTime');
        // modalTypeによって取得するstayTimeKeyを変更
        let stayTimeKey;
        switch (modalType) {
        case ModalType.places:
            stayTimeKey = `stayTime${formNum}`;
            break;
        case ModalType.updatePlaces:
            stayTimeKey = `updateStayTime${formNum}`;
            break;
        case ModalType.recommend:
            stayTimeKey = `recommendStayTime${formNum}`;
        }
        const stayTime = formData.get(stayTimeKey);
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
        // FormDataからstayTimeを削除
        formData.delete(stayTimeKey);
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
     * 更新フォーム送信submitイベントの処理
     * @param e イベントオブジェクト
     * @param modalType モーダルの種類
     * @param formNum (任意) フォーム番号
     */
    async #updateFormSubmit(e, modalType, formNum = null) {
        e.preventDefault();

        if (modalType === ModalType.updatePlaces) formNum = Number(e.target.id.replace('updatePlaceForm',''));

        // 値の検証
        if (!formValidate.validate(modalType, formNum)) {
            errorMessage.displayFormError(modalType, formNum);
            return;
        }
        errorMessage.hiddenFormError(modalType, formNum);

        const formData = new FormData(e.target);
        // 目的地更新フォームの場合は追加データを設定
        if (formNum !== null) {
            // disabledになってる目的地をformObjectに手動で追加
            const updateNameInput = document.getElementById(`updatePlace${formNum}`);
            formData.append(updateNameInput.name, updateNameInput.value);
            this.#setEndTime(formNum, formData, modalType);
            // idをセット
            const id = document.getElementById(`id${formNum}`);
            formData.set('id', id.value);
        }

        await this.postUpdatePlaceAPI(formData, modalType, formNum);
    }

    /**
     * /api/update-placeの成功時処理
     * @param placeId {number} placesテーブルのid
     * @param modalType {string} モーダルの種類
     * @param formNum {number} formの項番(placesの時だけ)
     */
    async #updatePlaceSuccess(placeId, modalType, formNum=null) {
        // modalの動作
        modal.closeModal(modalType, formNum);
        modal.changeToggleDisplay(modalType, formNum, placeId);

        // // おすすめ目的地フラグメントを呼び出し
        // await this.newAddRecommendFragment();
        // this.#attachRecommendFormEvent();

        // modalイベントの再アタッチ
        initFlowbite();
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
            if (!response.ok) throw new Error(`送信エラー: ${response.status}`);

            // /api/create-placeでFailedが返される
            const data = await response.json();
            if (data.status === 'Failed') {
                console.error(`APIエラー：${data}`);
                throw new Error('エラーが発生しました。');
            }
            // 成功時の処理
            placeId = data.placeId;
            await this.#createPlaceSuccess(placeId, modalType, formNum);
        } catch (error) {
            const errorText = '送信中にエラーが発生しました。もう一度お試しください。';
            errorMessage.displayFormError(modalType, formNum, errorText);
        }
    }

    /**
     * /api/update-placeに送信する
     * @param formData {FormData} 送信formData
     * @param modalType {String} 更新するformのType
     * @param formNum {number | null} 送信formの項番
     */
    async postUpdatePlaceAPI(formData, modalType, formNum=null) {
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
        let placeId = null;
        try {
            // 非同期でPOSTリクエストを送信
            const response = await fetch('/api/update-place', {
                method: 'POST',
                headers: {
                    [csrfHeaderName]: csrfToken
                },
                body: formData,
            });

            // 通信が成功しないとき
            if (!response.ok) throw new Error(`送信エラー: ${response.status}`);

            // /api/create-placeでFailedが返される
            const data = await response.json();
            if (data.status === 'Failed') throw new Error(`APIエラー：${data} が発生しました。`);
            // 成功時の処理
            placeId = data.placeId;
            await this.#updatePlaceSuccess(placeId, modalType, formNum);
        } catch (error) {
            const errorText = `送信中にエラーが発生しました。${error} もう一度お試しください。`;
            errorMessage.displayFormError(modalType, formNum, errorText);
        }
    }
}
