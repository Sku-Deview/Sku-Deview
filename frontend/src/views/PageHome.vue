<template>
  <div>
    <PageBanner />
    <div class="container mt-3">
      HOME PAGE
    </div>
  </div>
</template>

<script>
import PageBanner from "@/components/PageBanner.vue";

export default {
  components: {
    PageBanner
  },
  data() {
    return {};
  },

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
</style>
