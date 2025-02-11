const deletePlanButton = document.getElementById('deletePlanButton');

deletePlanButton.addEventListener('click', (e) => {
    e.preventDefault();

    // 現在のURLのパスを取得
    const path = window.location.pathname;
    // 正規表現で`/share/{uuid}/plan`形式の`uuid`を抽出
    const uuid = path.match(/\/share\/([^\/]+)\/plan/)[1];
    console.log(uuid[1]);

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    try {
        const response = fetch(`/api/delete-plan?uuid=${uuid}`, {
            method: 'POST',
            headers: {
                [csrfHeaderName]: csrfToken
            }
        });
        if (!response.ok) throw new Error(`送信エラー：${response.status}`);

        // /api/delete-planでのレスポンス
        const data = response.json();
        if (data.status === 'Failed') throw new Error(`APIエラー：${data}が発生しました`);

        // delete-plan成功時 プラン一覧にリダイレクト（戻るが効かない様に）
        window.location.replace('/plan/list');
    } catch (error) {
        console.error(error);
    }
});
