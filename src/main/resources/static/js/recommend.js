class Recommend {
    constructor() {
        // 各form要素に recommend という独自クラスを付与し取得する
        document.querySelectorAll('.recommend').forEach(element => {
            element.addEventListener('submit', (e) => {
                this.recommendSubmitEvent(e);
            });
        });
    }

    // おすすめ目的地のサブミットイベント
    recommendSubmitEvent(e) {
        e.preventDefault();

        // todo: formValidateチェック

        // todo: /api/create-placeに送信

        // todo: modalのtoggle追加

        // todo: modalToggleの表示変更

        // todo: updateModalFormを呼び出す
    }
}
