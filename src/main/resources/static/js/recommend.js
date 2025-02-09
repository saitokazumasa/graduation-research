// class RecommendPlace {
//     /**
//      * おすすめ目的地submit時に発火
//      */
//     async submitEvent(e) {
//         e.preventDefault();
//
//         // submit送信したformを取得
//         const formKey = e.target.id;
//         const formNum = Number(formKey.replace('recommendForm', ''));
//
//         // 「目的地を追加する」のform取得
//         const modalForm = new ModalForm();
//         const placeFormList = modalForm.placeFormElement; // modalFormクラスのformListを取得
//         // 一番後ろのplaceFormの項番を取得
//         const placeFormKey = placeFormList[placeFormList.length - 1].id;
//         const placeFormNum = Number(placeFormKey.replace('placesSubmit', ''));
//
//         // 今表示されている「目的地を追加する」のvalueを更新する
//         this.#setPlaceFormValue(placeFormNum, e);
//
//         // 今表示されている「目的地を追加する」の表示内容変更
//         modal.changePlaceDisplay(placeFormNum);
//
//         // 新規フラグメントの呼び出し
//         await modalForm.newAddFragment();
//
//         // api/create-placeへの処理、成功時 #hideModal,#hideDisplay 呼び出し
//         this.#postApiCreatePlace(formNum, e);
//     }
//
//     /**
//      * modalの表示を隠す
//      * @param formNum
//      */
//     #hideModal(formNum) {
//         const modalElement = document.getElementById(`recommendModal${formNum}`);
//         const modal = new Modal(modalElement);
//         modal.hide(); // 表示されているmodalを閉じる
//     }
//
//     /**
//      * toggleBtnの表示を隠す
//      */
//     #hideDisplay(formNum) {
//         // modal表示切り替えのtoggleBtn取得
//         const toggleBtn = document.getElementById(`recommendToggle${formNum}`);
//         toggleBtn.style.display = 'none';
//     }
//
//     /**
//      * sessionから値を取得してformのvalueに入れる
//      * @param placeFormNum {number} 「目的地」formの項番
//      * @param e {event}
//      */
//     #setPlaceFormValue(placeFormNum, e) {
//         // FormDataを利用してフォームのデータを取得
//         const formData = new FormData(e.target);
//         // フォームデータをオブジェクトとして取得
//         const formObject = Object.fromEntries(formData.entries());
//
//         document.getElementById(`placeId${placeFormNum}`).value = formObject.googlePlaceId;
//         document.getElementById(`placeLat${placeFormNum}`).value = formObject.latitude;
//         document.getElementById(`placeLng${placeFormNum}`).value = formObject.longitude;
//         document.getElementById(`place${placeFormNum}`).value = formObject.placeName;
//
//         if (formObject.budget)
//             document.getElementById(`budget${placeFormNum}`).value = formObject.budget;
//
//         if (formObject.stayTime)
//             document.getElementById(`stayTime${placeFormNum}`).value = formObject.stayTime;
//         else
//             document.getElementById(`stayTime${placeFormNum}`).value = 30;
//
//         if (formObject.desiredStartTime)
//             document.getElementById(`desiredStartTime${placeFormNum}`).value = formObject.desiredStartTime;
//
//         if (formObject.desiredEndTime)
//             document.getElementById(`desiredEndTime${placeFormNum}`).value = formObject.desiredEndTime;
//     }
//
//     /**
//      * api/create-place にPost送信
//      */
//     #postApiCreatePlace(formNum, e) {
//         const formData = new FormData(e.target);
//
//         // disabledにしているplaceNameの値を手動で追加
//         const disabledInput = document.getElementById(`recommend${formNum}`).value;
//         if (disabledInput) formData.append('placeName', disabledInput.value);
//
//         // /api/crete-placeにPOST送信
//         try {
//             // 非同期でPOSTリクエストを送信
//             fetch('/api/create-place', {
//                 method: 'POST',
//                 body: formData,
//             })
//                 .then(response => {
//                     if (!response.ok) {
//                         throw new Error(`送信エラー: ${response.status}`);
//                     }
//                     return response.json(); // 必要に応じてレスポンスを処理
//                 })
//                 .then(data => {
//                     if (data.status === 'Failed')
//                         throw new Error('エラーが発生しました。');
//                     // modal変更
//                     this.#hideModal(formNum); // 現在開かれてるmodalを閉じる
//                     this.#hideDisplay(formNum); // 追加したおすすめ目的地の表示を隠す
//                 });
//         } catch (error) {
//             document.getElementById('startError').textContent = '送信中にエラーが発生しました。もう一度お試しください。' + error;
//         }
//     }
// }
//
// const recommendPlace = new RecommendPlace();
//
// // th呼び出し後 submitイベントをアタッチ
// document.addEventListener('DOMContentLoaded', function() {
//     // 各form要素に recommend という独自クラスを付与し取得する
//     document.querySelectorAll('.recommend').forEach(element => {
//         element.addEventListener('submit', async function(e) {
//             await recommendPlace.submitEvent(e);
//         });
//     });
// });
