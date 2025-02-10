class Packings {
    constructor(itemListId, addBtnId) {
        this.itemList = document.getElementById(itemListId);
        this.addBtn = document.getElementById(addBtnId);

        if (this.itemList && this.addBtn) {
            this.loadItems();
            this.initialize();
        }
    }

    // 初期化
    initialize() {
        this.addBtn.removeEventListener('click', this.addItemSet.bind(this)); // 二重登録防止のため一旦削除
        this.addBtn.addEventListener('click', this.addItemSet.bind(this));

        // 既存の削除ボタンにイベントリスナーを設定
        const existingDeleteButtons = this.itemList.querySelectorAll('button.delete-button');
        existingDeleteButtons.forEach((deleteBtn) => {
            this.setDeleteEvent(deleteBtn);
        });

        // フォームの入力変更を監視
        const inputs = this.itemList.querySelectorAll('input');
        inputs.forEach((input) => {
            input.addEventListener('change', () => this.saveItems());
        });
    }

    // 削除ボタンのイベントリスナーを設定
    setDeleteEvent(deleteBtn) {
        deleteBtn.addEventListener('click', () => {
            const itemSet = deleteBtn.closest('.item-set');
            if (itemSet) {
                this.itemList.removeChild(itemSet);
                this.updateItemIndexes();
                this.saveItems();
            }
        });
    }

    // インデックスを更新
    updateItemIndexes() {
        const itemSets = this.itemList.querySelectorAll('.item-set');
        itemSets.forEach((itemSet, index) => {
            const checkbox = itemSet.querySelector('input[type="checkbox"]');
            const input = itemSet.querySelector('input[type="text"]');

            if (checkbox) checkbox.name = `items[${index}].checked`;
            if (input) input.name = `items[${index}].value`;
            if (input) input.placeholder = '持ち物を入力'; // プレースホルダーを設定
        });
    }

    // アイテムセットを追加
    addItemSet() {
        const itemSet = document.createElement('div');
        itemSet.className = 'item-set flex items-center space-x-4';

        // チェックボックス
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.name = `items[${this.itemList.children.length}].checked`;
        checkbox.className = 'w-5 h-5';

        // テキスト入力
        const input = document.createElement('input');
        input.type = 'text';
        input.name = `items[${this.itemList.children.length}].value`;
        input.placeholder = '持ち物を入力';
        input.className = 'w-full input';

        // 削除ボタン
        const deleteBtn = document.createElement('button');
        deleteBtn.type = 'button';
        deleteBtn.className = 'delete-button';
        deleteBtn.innerHTML = `
            <svg class="w-8 h-8 sm:w-11 sm:h-11" fill="none" viewBox="0 0 30 30" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                <circle cx="15" cy="15" r="14.5" stroke="#F45555"/>
                <mask height="18" id="mask0_434_2722" maskUnits="userSpaceOnUse" style="mask-type:alpha" width="18" x="6" y="6">
                    <rect fill="url(#pattern0_434_2722)" height="17.7273" width="17.7273" x="6.13635" y="6.13635"/>
                </mask>
                <g mask="url(#mask0_434_2722)">
                    <rect fill="#F45555" height="27.6718" width="25.7851" x="2.04541"/>
                </g>
                <defs>
                    <pattern height="1" id="pattern0_434_2722" patternContentUnits="objectBoundingBox" width="1">
                        <use transform="scale(0.00390625)" xlink:href="#image0_434_2722"/>
                    </pattern>
                    <image height="256" id="image0_434_2722" width="256" xlink:href="data:image/png;base64,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"/>
                </defs>
            </svg>`;

        this.setDeleteEvent(deleteBtn);

        itemSet.appendChild(checkbox);
        itemSet.appendChild(input);
        itemSet.appendChild(deleteBtn);

        this.itemList.appendChild(itemSet);
        this.updateItemIndexes();
        this.saveItems();
    }

    // アイテムを保存
    saveItems() {
        const items = [];
        const itemSets = this.itemList.querySelectorAll('.item-set');
        itemSets.forEach((itemSet) => {
            const checkbox = itemSet.querySelector('input[type="checkbox"]');
            const input = itemSet.querySelector('input[type="text"]');
            items.push({ checked: checkbox.checked, value: input.value });
        });
        localStorage.setItem('packings', JSON.stringify(items));
    }

    // アイテムを読み込み
    loadItems() {
        this.itemList.innerHTML = ''; // 既存のアイテムをクリア
        const savedItems = JSON.parse(localStorage.getItem('packings'));
        if (savedItems && savedItems.length > 0) {
            savedItems.forEach((item) => {
                this.createItemSet(item.checked, item.value);
            });
        } else {
            this.createItemSet(false, ''); // 初期状態で入力欄を1つ追加
        }
        this.updateItemIndexes();
    }

    // アイテムセットを生成
    createItemSet(checked, value) {
        const itemSet = document.createElement('div');
        itemSet.className = 'item-set flex items-center space-x-4';

        // チェックボックス
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.checked = checked;
        checkbox.className = 'w-5 h-5';

        // テキスト入力
        const input = document.createElement('input');
        input.type = 'text';
        input.value = value;
        input.placeholder = '持ち物を入力';
        input.className = 'w-full input';

        // 削除ボタン
        const deleteBtn = document.createElement('button');
        deleteBtn.type = 'button';
        deleteBtn.className = 'delete-button';
        deleteBtn.innerHTML = `
            <svg class="w-8 h-8 sm:w-11 sm:h-11" fill="none" viewBox="0 0 30 30" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                <circle cx="15" cy="15" r="14.5" stroke="#F45555"/>
                <mask height="18" id="mask0_434_2722" maskUnits="userSpaceOnUse" style="mask-type:alpha" width="18" x="6" y="6">
                    <rect fill="url(#pattern0_434_2722)" height="17.7273" width="17.7273" x="6.13635" y="6.13635"/>
                </mask>
                <g mask="url(#mask0_434_2722)">
                    <rect fill="#F45555" height="27.6718" width="25.7851" x="2.04541"/>
                </g>
                <defs>
                    <pattern height="1" id="pattern0_434_2722" patternContentUnits="objectBoundingBox" width="1">
                        <use transform="scale(0.00390625)" xlink:href="#image0_434_2722"/>
                    </pattern>
                    <image height="256" id="image0_434_2722" width="256" xlink:href="data:image/png;base64,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"/>
                </defs>
            </svg>`;

        this.setDeleteEvent(deleteBtn);

        itemSet.appendChild(checkbox);
        itemSet.appendChild(input);
        itemSet.appendChild(deleteBtn);

        this.itemList.appendChild(itemSet);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new Packings('item-list', 'add-button');
});
