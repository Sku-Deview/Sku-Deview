<template>
  <table class="table table-striped table-bordered text-center">
    <thead>
    <tr>
      <th>스킬 아이디</th>
      <th>스킬 이름</th>
    </tr>
    </thead>

    <tbody>
    <tr v-for="(item, idx) in list" :key="idx" class="hover-pointer">
      <td>{{ item.skillId }}</td>
      <td>{{ item.name }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  data() {
    return {
      list: [],
    };
  },
  mounted() {
    this.ReportList();
  },
  methods: {
    ReportList() {
      this.$axios
          .get("/api/v1/admin/skill", {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('user_token')}`
            }
          })
          .then((res) => {
            this.list = res.data.data;
          })
          .catch((err) => {
            alert(err.response.data.message)
          });
    },
  },
};
</script>
