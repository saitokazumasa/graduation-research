class EditForm {
    #editPlanForm;
    #editWayPointListForm;

    constructor() {
        this.initForm();
    }

    initForm() {
        this.#editPlanForm = document.getElementById('editPlanForm');
        this.#editWayPointListForm = document.getElementById('editWayPointListForm');
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
     * editWayPointListFormのformData取得
     * @returns {FormData}
     */
    #getWayPointListFormData() {
        const formData = new FormData(this.#editWayPointListForm);
        return this.#formatWayPointForm(formData);
    }

    /**
     * todo: editWayPointListFormの交通手段をformatする
     */
    #formatWayPointForm(formData) {
        // 文字列のtrueかfalseが入る
        const car = formData.get('checkCar');
        const cycle = formData.get('checkCycle');
        const train = formData.get('checkTrain');

        return formData;
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
    async postEditWayPointListAPI() {
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;
        try {
            // 非同期でPOSTリクエストを送信
            const response = await fetch('/api/waypoint-list/edit', {
                method: 'POST',
                headers: {
                    [csrfHeaderName]: csrfToken
                },
                body: this.#getWayPointListFormData(),
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
