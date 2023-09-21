<template>
  <div class="board-detail mt-5">
    <div class="common-buttons mb-3">
      <button type="button" class="btn btn-danger btn-rounded" @click="fnDelete">삭제</button>
      <button type="button" class="btn btn-success btn-rounded" @click="fnList">목록</button>
    </div>
    <h2><strong>[{{ category }}] {{ title }}</strong></h2>
    <div>
      <p class="w3-large mb-3 mt-3">
        {{ author }}
        <span class="small-font">&nbsp {{ created_at }}</span>
        <span class="small-font">&nbsp&nbsp 조회수: {{view_count}}</span>
      </p>
    </div>

    <hr>
    <div class="board-contents" v-html="content"></div>
  </div>
</template>


<style scoped>
.small-font {
  font-size: 0.8rem;
  color: gray;
}
</style>


<script>
export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,

      idx: this.$route.query.postId,
      category: '',
      title: '',
      author: '',
      content: '',
      created_at: '',
      view_count:'',
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' };
      return new Date(dateString).toLocaleString(undefined, options);
    },

    fnGetView() {
      this.$axios.get('/api/v1/admin/post-detail/' + this.idx, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        this.title = res.data.data.title
        this.author = res.data.data.memberNickname
        this.content = res.data.data.content
        this.created_at = this.formatDate(res.data.data.regDate)
        this.category = res.data.data.postCategory
        this.view_count = res.data.data.viewCount
      }).catch((err) => {
        alert(err.response.data.message)
      })
    },

    fnList() {
      this.$router.push({
        path: '/admin/member/post',
        query: this.requestBody
      })
    },
    fnDelete() {
      if (!confirm("게시글을 삭제하시겠습니까?")) return

      this.$axios.delete('/api/v1/admin/post/' + this.idx, {})
          .then((res) => {
            alert(res.data.message)
            this.fnList();
          }).catch((err) => {
        alert(err.response.data.message)
      })
    },

  }
}
</script>
