class ImageUrls {
    urls = [
        '/images/top1.jpg',
        '/images/top2.jpg',
        '/images/top3.jpg',
        '/images/top4.jpg',
        '/images/top5.jpg',
        '/images/top6.jpg',
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
