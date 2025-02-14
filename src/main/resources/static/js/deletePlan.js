const deletePlanButton = document.getElementById('deletePlanButton');

deletePlanButton.addEventListener('click', (e) => {
    e.preventDefault();

    // 現在のURLのパスを取得
    const path = window.location.pathname;
    // 正規表現でuuidを抽出
    const uuid = path.match(/([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})/)[0];

    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    // '/api/plan/delete/{uuid}' に送信
    try {
        const response = fetch(`/api/plan/delete/${uuid}`, {
            method: 'POST',
            headers: {
                [csrfHeaderName]: csrfToken
            }
        });
        if (!response.ok) throw new Error(`送信エラー：${response.status}`);

        // delete-plan成功時 プラン一覧にリダイレクト（戻るが効かない様に）
        window.location.replace('/plan/list');
    } catch (error) {
        console.error(error);
    }
});
