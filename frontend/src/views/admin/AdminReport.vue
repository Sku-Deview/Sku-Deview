<template>
  <div class="board-list mt-5">
    <table class="table table-striped">
      <colgroup>
        <col style="width: 5%;"/> <!-- No 열의 너비 -->
        <col style="width: 5%;"/> <!-- 카테고리 열의 너비 -->
        <col style="width: 10%;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
        <col style="width: 10%;"/> <!-- 작성자 열의 너비 -->
        <col style="width: auto;"/> <!-- 등록일시 열의 너비 -->
        <col style="width: 20%;"/> <!-- 등록일시 열의 너비 -->
      </colgroup>
      <thead>
      <tr>
        <th scope="col">postId</th>
        <th scope="col">reportId</th>
        <th scope="col">memberName</th>
        <th scope="col">reportCategory</th>
        <th scope="col">postTitle</th>
        <th scope="col">title</th>
        <th scope="col">description</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, idx) in list" :key="idx" @click="fnView(item.reportId)" class="hover-pointer">
        <td>{{ item.postId }}</td>
        <td>{{ item.reportId }}</td>
        <td>{{ item.memberName }}</td>
        <td>{{ item.reportCategory }}</td>
        <td>{{ item.postTitle }}</td>
        <td>{{ item.title }}</td>
        <td>
          <span v-if="item.title.length < 15">{{ item.title }}</span>
          <span v-else>{{ item.title.substring(0, 15) + "..." }}</span>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      list: '',
      requestBody:{
        reportId:''
      }
    }
  },
  mounted() {
    this.ReportList()
  },
  methods: {
    ReportList() {
      this.$axios.get("/api/v1/admin/report", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            this.list = res.data.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
          }).catch((err) => {
        alert(err.response.data.message)
      })
    },
    fnView(reportId) {
      this.requestBody.reportId = reportId
      this.$router.push({
        path: '/admin/report/detail',
        query: this.requestBody
      })
    },
  },
}
</script>

<style>
.hover-pointer {
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}
</style>