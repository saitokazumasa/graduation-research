// class ErrorMessage {
//     #elementKeys;
//     #errorMessages;
//
//     constructor() {
//         this.#elementKeys = {
//             start: 'startError',
//             end: 'endError',
//             places: 'placeError',
//             updateStart: 'updateStartError',
//             updateEnd: 'updateEndError',
//             updatePlaces: 'updatePlaceError',
//             recommend: 'recommendError',
//         };
//         this.#errorMessages = {
//             start: '出発地点・予定時間を正しく入力してください。',
//             end: '終了地点を正しく入力してください。',
//             places: '目的地を正しく入力してください。',
//             updateStart: '出発地点・予定時間を正しく入力して下さい。',
//             updateEnd: '終了地点を正しく入力して下さい。',
//             updatePlaces: '目的地を正しく入力してください。',
//             recommend: '目的地を正しく入力してください'
//         };
//     }
//
//     displayFormError(modalType, formNum = null, message = null) {
//         const elementKey = formNum !== null
//             ? `${this.#elementKeys[modalType]}${formNum}`
//             : this.#elementKeys[modalType];
//         if (message === null) message = this.#errorMessages[modalType];
//         this.#setErrorMessage(elementKey, message);
//     }
//
//     hiddenFormError(modalType, formNum = null) {
//         formNum !== null
//             ? this.#setErrorMessage(`${this.#elementKeys[modalType]}${formNum}`, '')
//             : this.#setErrorMessage(this.#elementKeys[modalType], '');
//     }
//
//     /**
//      * エラーメッセージの設定
//      * @param {string} elementKey エラーメッセージを表示する要素のID
//      * @param {string} message エラーメッセージ
//      */
//     #setErrorMessage(elementKey, message) {
//         document.getElementById(elementKey).textContent = message;
//     }
// }
