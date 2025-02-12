class EditForm {
    #editPlanForm;
    #editDayForm;

    constructor() {
        this.#editPlanForm = document.getElementById('editPlanForm');
        this.#editDayForm = document.getElementById('editDayForm');
    }

    initForm() {
        this.#editPlanForm = document.getElementById('editPlanForm');
        this.#editDayForm = document.getElementById('editDayForm');
    }

    /**
     * URLからUUIDを取得する
     */
    #getUUID() {
        const pathname = window.location.pathname;
        return pathname.match(/([0-9a-f]{8})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{4})-([0-9a-f]{12})/)[0];
    }

    /**
     * editPlanFormのformData取得
     * @returns {FormData}
     */
    #getPlanFormData() {
        return new FormData(this.#editPlanForm);
    }

    /**
     * editDayFormのformData取得
     * @returns {FormData}
     */
    #getDayFormData() {
        return new FormData(this.#editDayForm);
    }

    /**
     * EditPlanRestControllerに送信
     * @returns {Promise<any>}
     */
    async postEditPlanAPI() {
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
        const uuid = this.#getUUID();
        try {
            // 非同期でPOSTリクエストを送信
            const response = await fetch(`/api/plan/edit/${uuid}`, {
                method: 'POST',
                headers: {
                    [csrfHeaderName]: csrfToken
                },
                body: this.#getPlanFormData(),
            });

            // 通信が成功しないとき
            if (!response.ok) throw new Error(`送信エラー: ${response.status}`);

            return await response.json();
        } catch (error) {
            console.error(error);
        }
    }

    /**
     * EditDayRestControllerに送信
     * @returns {Promise<void>}
     */
    async postEditDayAPI() {
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
        const uuid = this.#getUUID();
        try {
            // 非同期でPOSTリクエストを送信
            const response = await fetch(`/api/day/edit/${uuid}`, {
                method: 'POST',
                headers: {
                    [csrfHeaderName]: csrfToken
                },
                body: this.#getDayFormData(),
            });

            // 通信が成功しないとき
            if (!response.ok) throw new Error(`送信エラー: ${response.status}`);

            return await response.json();
        } catch (error) {
            console.error(error);
        }
    }
}

const editForm = new EditForm();
