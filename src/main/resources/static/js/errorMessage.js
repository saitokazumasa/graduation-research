class ErrorMessage {
    #elementKeys;
    #errorMessages;

    constructor() {
        this.#elementKeys = {
            start: 'startError',
            end: 'endError',
            places: 'placeError',
            updateStart: 'updateStartError',
            updateEnd: 'updateEndError',
            updatePlaces: 'updatePlaceError',
        };
        this.#errorMessages = {
            start: '出発地点・予定時間を正しく入力してください。',
            end: '終了地点を正しく入力してください。',
            places: '目的地を正しく入力してください。',
            updateStart: '出発地点・予定時間を正しく入力して下さい。',
            updateEnd: '終了地点を正しく入力して下さい。',
            updatePlaces: '目的地を正しく入力してください。'
        };
    }

    displayFormError(modalType, formNum) {
        if (formNum !== null) {
            this.#setErrorMessage(`${this.#elementKeys[modalType]}${formNum}`, this.#errorMessages[modalType]);
            return;
        }
        this.#setErrorMessage(this.#elementKeys[modalType], this.#errorMessages[modalType]);
    }

    hiddenFormError(modalType, formNum) {
        if (formNum !== null) {
            this.#setErrorMessage(`${this.#elementKeys[modalType]}${formNum}`, '');
            return;
        }
        this.#setErrorMessage(this.#elementKeys[modalType], '');
    }

    /**
     * エラーメッセージの設定
     * @param {string} elementKey エラーメッセージを表示する要素のID
     * @param {string} message エラーメッセージ
     */
    #setErrorMessage(elementKey, message) {
        document.getElementById(elementKey).textContent = message;
    }
}
