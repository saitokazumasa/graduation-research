// class EditButtons {
//     #backButton; // 保存して戻るボタン
//     #crateRouteButton; // 経路生成ボタン
//     #addDayButton; // 日付追加ボタン
//     #planConfirmButton; // プラン確定ボタン
//     #editButtonsDiv; // 日付追加ボタンとプラン確定ボタンを囲ったdivタグ
//
//     constructor() {
//         this.#backButton = document.getElementById('backButton');
//         this.#crateRouteButton = document.getElementById('crateRouteButton');
//         this.#addDayButton = document.getElementById('addDayButton');
//         this.#planConfirmButton = document.getElementById('planConfirmButton');
//         this.#editButtonsDiv = document.getElementById('editButtonsDiv');
//         this.initButtonEvent();
//     }
//
//     /**
//      * ボタンイベント初期化
//      */
//     initButtonEvent() {
//         this.#backButton.addEventListener('click', async(e) => { await this.#backButtonEvent(e); });
//         this.#crateRouteButton.addEventListener('click', async(e) => { await this.#createRouteButtonEvent(e); });
//         this.#addDayButton.addEventListener('click', async(e) => { await this.#addDayButtonEvent(e); });
//         this.#planConfirmButton.addEventListener('click', async(e) => { await this.#planConfirmButtonEvent(e); });
//     }
//
//     /**
//      * 保存して戻るボタンのイベント
//      * @param e
//      * @returns {Promise<void>}
//      */
//     async #backButtonEvent(e) {
//         e.preventDefault();
//
//         editForm.initForm();
//         // editPlanFormを使って更新
//         await editForm.postEditPlanAPI();
//         // editDayFormを使って更新
//         await editForm.postEditWayPointListAPI();
//
//         // プラン一覧に遷移（ブラウザの戻るボタンが効かないように）
//         window.location.replace('/plan/list');
//     }
//
//     /**
//      * 経路生成ボタンのイベント
//      * @param e
//      * @returns {Promise<void>}
//      */
//     async #createRouteButtonEvent(e) {
//         e.preventDefault();
//
//         editForm.initForm();
//         // editPlanFormを使って更新
//         await editForm.postEditPlanAPI();
//         // editDayFormを使って更新
//         await editForm.postEditWayPointListAPI();
//         // todo: 経路生成をするAPIに送信
//         // todo: 経路生成後にplacesも更新
//
//         // 表示切替
//         // todo: map.jsの更新
//
//         // 交通手段・移動時間表示
//         const startTrans = document.getElementById(TransportationKeys[0]);
//         startTrans.classList.remove('hidden');
//         for (let index = 1; index <= placeNum.value(); index++ ) {
//             const placeTrans = document.getElementById(TransportationKeys[1] + index);
//             placeTrans.classList.add('hidden');
//         }
//
//         // 日付追加ボタン・プラン確定ボタンを表示する
//         this.#editButtonsDiv.classList.remove('hidden');
//     }
//
//     /**
//      * 日付追加ボタンのイベント
//      * @param e
//      */
//     async #addDayButtonEvent(e) {
//         e.preventDefault();
//
//         // todo: 日付追加のAPIに送信
//         // todo: リダイレクトでこのページに遷移
//     }
//
//     /**
//      * プラン確定ボタンのイベント
//      * @param e
//      */
//     async #planConfirmButtonEvent(e) {
//         e.preventDefault();
//
//         editForm.initForm();
//         // editPlanFormを使って更新
//         await editForm.postEditPlanAPI();
//         // editDayFormを使って更新
//         await editForm.postEditWayPointListAPI();
//
//         // プラン一覧に遷移（ブラウザの戻るボタンが効かないように）
//         window.location.replace('/plan/list');
//     }
// }
//
// const TransportationKeys = [
//     'startTransportation',
//     'placeTransportation',
// ];
//
// new EditButtons();
