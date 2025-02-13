const tham = document.querySelector('.tham');
const mobileMenu = document.getElementById('mobile-menu');
const overlay = document.getElementById('overlay');

tham.addEventListener('click', () => {
    tham.classList.toggle('tham-active'); // メニューを表示
    mobileMenu.classList.toggle('hidden'); // ハンバーガーアイコンを非表示
    document.body.classList.toggle('overflow-hidden'); // bodyのスクロールを不可に
    overlay.classList.toggle('hidden'); // 背景の要素の色を非表示

    // ウィンドウのリサイズイベント
    window.addEventListener('resize', () => {
        mobileMenu.classList.add('hidden');  // メニューを非表示にする
        tham.classList.remove('tham-active'); // ハンバーガーアイコンを元の状態に戻す
        document.body.classList.toggle('overflow-hidden'); // bodyのスクロールを可能に戻す
        overlay.classList.toggle('hidden'); // 背景の要素に色を表示
    });
});
