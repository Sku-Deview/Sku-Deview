<template>
  <Carousel>
    <slide v-for="(image, index) in images" :key="index">
      <img :src="getImagePath(image)" class="slide-image"/>
    </slide>

    <template #addons>
      <Navigation/>
      <Pagination/>
    </template>
  </Carousel>
</template>

<script>
import {defineComponent} from 'vue'
import {Carousel, Navigation, Pagination, Slide} from 'vue3-carousel'

import 'vue3-carousel/dist/carousel.css'

export default defineComponent({
  name: 'Basic',
  components: {
    Carousel,
    Slide,
    Pagination,
    Navigation,
  },
  data() {
    return {
      images: [
        'image1.jpg',
        'image2.png',
      ],
    };
  },
  methods: {
    getImagePath(imageFileName) {
      return require(`@/assets/image/${imageFileName}`);
    },
  },

})
</script>

<style>
.slide-image {
  width: 100%;
  height: 300px; /* 원하는 높이로 조정 */
  object-fit: cover; /* 이미지 비율 유지 및 부족한 부분 채우기 */
}

</style>
