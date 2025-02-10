const createPlanButton = document.getElementById('createPlanButton');
const updatePlan = document.getElementById('updatePlan');
const updateDay = document.getElementById('updateDay');

// 経路生成クリック時
createPlanButton.addEventListener('click', async(e) => {
    e.preventDefault();

    // updatePlanの送信
    const updatePlanForm = new FormData(updatePlan);
    await postUpdatePlanAPI(new FormData(updatePlan));

    // updateDayの送信
    await postUpdateDayAPI(new FormData(updateDay));

    const uuid = updatePlanForm.get('uuid');
    window.location.replace(`/plan/${uuid}/edit`);
});

async function postUpdatePlanAPI(formData) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    try {
        // 非同期でPOSTリクエストを送信
        const response = await fetch('/api/update-plan', {
            method: 'POST',
            headers: {
                [csrfHeaderName]: csrfToken
            },
            body: formData,
        });

        // 通信が成功しないとき
        if (!response.ok) throw new Error(`送信エラー: ${response.status}`);

        const data = await response.json();
        if (data.status === 'Failed') throw new Error(`APIエラー：${data} が発生しました。`);
    } catch (error) {
        console.error(error);
    }
}

async function postUpdateDayAPI(formData) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
    try {
        // 非同期でPOSTリクエストを送信
        const response = await fetch('/api/update-day', {
            method: 'POST',
            headers: {
                [csrfHeaderName]: csrfToken
            },
            body: formData,
        });

        // 通信が成功しないとき
        if (!response.ok) throw new Error(`送信エラー: ${response.status}`);

        const data = await response.json();
        if (data.status === 'Failed') throw new Error(`APIエラー：${data} が発生しました。`);
    } catch (error) {
        console.error(error);
    }
}
