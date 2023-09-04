<template>
  <table class="w3-table-all">
    <thead>
    <tr>
      <th>No</th>
      <th>받는 사람</th>
      <th>제목</th>
      <th>보낸 사람</th>

    </tr>
    </thead>
    <tbody>
    <tr v-for="(item, idx) in list" :key="idx">
      <td>{{ idx }}</td>
      <td>{{ item.receiverName }}</td>
      <td>{{ item.title }}</td>
      <td>{{ item.senderName }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  data() {
    return{
      list:{}
    }
  },
  mounted() {
    this.SendListGet()
  },
  methods: {
    SendListGet() {
      this.$axios.get("/api/v1/message/send", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            this.list=res.data
          }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },

  }
}
</script>