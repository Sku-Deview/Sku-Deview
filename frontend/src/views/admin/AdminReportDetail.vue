<template>
  <div class="board-detail mt-5">
    <div class="common-buttons mb-3">
      <button type="button" class="btn btn-success btn-rounded" @click="fnList">목록</button>
    </div>
    <h3>{{ this.postId }}번 게시글 대한 신고 - reportId : {{ this.reportId }}</h3>
    <h2><strong>[{{ this.reportCategory }}] {{ this.title }}</strong></h2>
    <div>
      <p class="w3-large mb-3 mt-3">
        신고자: {{ this.memberName }}
        <span class="small-font"> {{ formatDate(regDate) }}</span>
      </p>
      <p>
        <span> 신고 게시물 제목: {{ this.postTitle }}</span>
      </p>
    </div>

    <hr>
    <div>
      <h4>{{this.description}}</h4>
    </div>

  </div>
</template>


<style scoped>
.small-font {
  font-size: 0.8rem;
}
</style>


<script>
export default {
  data() { //변수생성
    return {
      requestId: this.$route.query.reportId,
      reportId: '',
      postId: '',
      memberName: '',
      reportCategory: '',
      postTitle: '',
      title: '',
      description: '',
      regDate: '',

    }
  },
  mounted() {
    this.ReportDetail()
  },
  methods: {
    formatDate(dateString) {
      const options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};
      return new Date(dateString).toLocaleString(undefined, options);
    },

    ReportDetail() {
      this.$axios.get('/api/v1/admin/report-detail/' + this.requestId, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        this.reportId = res.data.data.reportId
        this.postId = res.data.data.postId
        this.memberName = res.data.data.memberName
        this.regDate = res.data.data.regDate
        this.reportCategory = res.data.data.reportCategory
        this.postTitle = res.data.data.postTitle
        this.title = res.data.data.title
        this.description = res.data.data.description
      }).catch((err) => {
        alert(err.response.data.message)
      })
    },

    fnList() {
      this.$router.push({
        path: '/admin/report/list',

      })
    },
  }
}
</script>
