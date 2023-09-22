<template>
    <div>
        <div class="banner">
            <div class="slider">
                <img
                    v-for="(image, index) in images"
                    :key="index"
                    :src="getImagePath(image)"
                    :class="{ active: index === currentIndex }"
                />
            </div>
            <button @click="prevSlide" class="prev-button">Previous</button>
            <button @click="nextSlide" class="next-button">Next</button>
        </div>
        <div class="container mt-3">
            HOME PAGE
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            images: [
                // 'image0.jpg',
                // 'image1.jpg',
                // 'image2.jpg',
                // 'image3.png',
                // 'image4.png'
            ],
            currentIndex: 0,
        };
    },
    // methods: {
    //     getImagePath(imageFileName) {
    //         // 이미지 파일의 상대 경로를 반환합니다.
    //         return require(`@/assets/images/${imageFileName}`);
    //     },
    //     nextSlide() {
    //         this.currentIndex = (this.currentIndex + 1) % this.images.length;
    //     },
    //     prevSlide() {
    //         this.currentIndex = (this.currentIndex - 1 + this.images.length) % this.images.length;
    //     },
    // },
};
</script>

<style scoped>
.container {
    background-color: #f5f5f5;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    text-align: center;
    font-size: 24px;
    color: #333;
}

.banner {
    position: relative;
    overflow: hidden;
}

.slider {
    display: flex;
    transition: transform 0.5s ease-in-out;
}

.slider img {
    width: 100%;
    height: 300px;
    flex-shrink: 0;
    opacity: 0;
    transition: opacity 0.5s ease-in-out;
}

.slider img.active {
    opacity: 1;
}

.prev-button,
.next-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    background-color: #333;
    color: #fff;
    border: none;
    padding: 10px 20px;
    cursor: pointer;
    z-index: 1;
}

.prev-button {
    left: 10px;
}

.next-button {
    right: 10px;
}
</style>
