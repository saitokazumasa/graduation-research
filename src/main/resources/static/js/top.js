class ImageUrls {
    urls = [
        '/images/top1.avif',
        '/images/top2.avif',
        '/images/top3.avif',
        '/images/top4.avif',
        '/images/top5.avif',
        '/images/top6.avif',
    ];
    currentIndex = -1;

    nextUrl() {
        const nextIndex = this.currentIndex + 1;
        this.currentIndex = nextIndex >= this.urls.length? 0 : nextIndex;
        return this.urls[this.currentIndex];
    }
}

const imagesElement = document.getElementById('images');
const imageUrls = new ImageUrls();
function nextImage() {
    imagesElement.src = imageUrls.nextUrl();
}
setInterval(() => nextImage(), 10000);
