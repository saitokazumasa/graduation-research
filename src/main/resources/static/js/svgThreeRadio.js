class ChangeSVGColor {
    #radioButtons = [];
    #targetElements = [];

    constructor(radioKeys, targetKeys) {
        this.#radioButtons = {
            car: document.getElementById(radioKeys.car),
            walk: document.getElementById(radioKeys.walk),
            cycle: document.getElementById(radioKeys.cycle),
        };
        this.#targetElements = {
            car: document.getElementById(targetKeys.car),
            walk: document.getElementById(targetKeys.walk),
            cycle: document.getElementById(targetKeys.cycle),
        };

        // 要素が存在しない時
        if (this.#targetElements.length <= 0 || this.#targetElements.length <= 0) return;
        this.eventInit();
    }

    eventInit() {
        // 変更イベントを設定
        for (const key in this.#radioButtons) {
            this.#radioButtons[key].addEventListener('change', e => this.changeEvent(e, key));
        }
    }

    changeEvent(e, key) {
        // すべての要素にfill-labelを付与
        for (const key in this.#targetElements) {
            this.#targetElements[key].classList.add('fill-label');
        }

        // 選択された要素のfill-label削除
        if (e.target.checked) this.#targetElements[key].classList.remove('fill-label');
    }
}

const radioKeys = {
    walk: 'radioWalk',
    car: 'radioCar',
    cycle: 'radioCycle'
};

const targetKeys = {
    walk: 'targetElementWalk',
    car: 'targetElementCar',
    cycle: 'targetElementCycle'
};

new ChangeSVGColor(radioKeys, targetKeys);
