// 送信されるmodalFormの種別
const ModalType = Object.freeze({
    start: 'start',
    end: 'end',
    places: 'places',
    updateStart: 'updateStart',
    updateEnd: 'updateEnd',
    updatePlaces: 'updatePlaces',
    recommend: 'recommend',
});

class PlaceNum {
    #value = 0;

    value() {
        return this.#value;
    }

    increment() {
        this.#value ++;
    }
}

class Fragment {
    #toggle;
    #form;
    #startUpdateForm;
    #endUpdateForm;
    #placesUpdateForm;

    constructor() {
        this.#toggle = null;
        this.#form = null;
        this.#startUpdateForm = null;
        this.#endUpdateForm = null;
        this.#placesUpdateForm = null;
    }

    /**
     * 追加fragmentの初期化
     * @returns {Promise<void>}
     */
    async initialize() {
        // toggle取得 /fragment/modal/placesToggle
        try {
            const response = await fetch(`/fragment/modal/placesToggle?num=${(placeNum.value()+1)}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#toggle = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
        // form取得 /fragment/modal/placesForm
        try {
            const response = await fetch(`/fragment/modal/placesForm?num=${(placeNum.value()+1)}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#form = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * 出発地点更新formフラグメントの初期化
     * @param placeId placesテーブルのid
     * @returns {Promise<void>}
     */
    async initStartUpdateForm(placeId) {
        // 出発地点更新form取得 /fragment/modal/startUpdateForm
        try {
            const response = await fetch(`/fragment/update-modal/startUpdateForm?placeId=${placeId}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#startUpdateForm = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * 終了地点更新formフラグメントの初期化
     * @param placeId placesテーブルのid
     * @returns {Promise<void>}
     */
    async initEndUpdateForm(placeId) {
        // 終了地点更新form取得 /fragment/modal/endUpdateForm
        try {
            const response = await fetch(`/fragment/update-modal/endUpdateForm?placeId=${placeId}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#endUpdateForm = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * 目的地更新formフラグメントの初期化
     * @param placeId placesテーブルのid
     * @param formNum formの項番
     * @returns {Promise<void>}
     */
    async initPlacesUpdateForm(placeId, formNum) {
        // 目的地更新form取得 /fragment/modal/placesUpdateForm?num=
        try {
            const response = await fetch(`/fragment/update-modal/placesUpdateForm?placeId=${placeId}&num=${formNum}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#placesUpdateForm = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * HTMLにfragment追加 toggleとform
     */
    addFragment() {
        if (!this.#toggle || !this.#form) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');

        // id=destination の子要素にToggleを追加
        const destination = document.getElementById('destination');
        const newToggle = document.createElement('div');
        newToggle.innerHTML = this.#toggle;
        destination.appendChild(newToggle);

        // id=formDiv の子要素に Form を追加
        const formDiv = document.getElementById('formDiv');
        const newForm = document.createElement('div');
        newForm.innerHTML = this.#form;
        formDiv.appendChild(newForm);
    }

    /**
     * HTMLにstartUpdateFormフラグメント追加
     */
    addStartUpdateForm() {
        if (!this.#startUpdateForm) return;

        // id=formDivの子要素に #startUpdateForm を追加
        this.#addUpdateFragment(this.#startUpdateForm);
    }

    /**
     * HTMLにendUpdateFormフラグメント追加
     */
    addEndUpdateForm() {
        if (!this.#endUpdateForm) return;

        // id=formDivの子要素に #endUpdateForm を追加
        this.#addUpdateFragment(this.#endUpdateForm);
    }

    /**
     * HTMLにplacesUpdateFormフラグメント追加
     */
    addPlacesUpdateForm() {
        if (!this.#placesUpdateForm) return;

        // id=formDivの子要素に placesUpdateForm を追加
        this.#addUpdateFragment(this.#placesUpdateForm);
    }

    /**
     * id=formDivの子要素に UpdateForm を追加
     */
    #addUpdateFragment(updateForm) {
        const formDiv = document.getElementById('formDiv');
        const newForm = document.createElement('div');
        newForm.innerHTML = updateForm;
        formDiv.appendChild(newForm);
    }
}

const placeNum = new PlaceNum();
const modal = new ModalElement();
const errorMessage = new ErrorMessage();
const formValidate = new FormValidator();

document.addEventListener('DOMContentLoaded', () => {
    new ModalForm();
});
