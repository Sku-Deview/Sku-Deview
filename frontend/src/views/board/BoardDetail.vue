<template>
  <div class="board-detail">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnUpdate">수정</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-red" v-on:click="fnDelete">삭제</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-green" v-on:click="fnList">목록</button>
    </div>
    <h2>[{{ category }}] {{ title }}</h2>
    <div class="board-contents">
      <span class=" w3-large">{{ author }}</span>
      <span class="create-at">{{ created_at }}</span>
    </div>
    <div class="board-contents">
      <span>{{ content }}</span>
    </div>
    <hr>

    <div v-for="(reply, idx) in replyList" :key="idx">
      <div class="reply-detail">
        [{{ reply.memberNickname }}]
        <div class="create-at">
          <span><td>{{ reply.regDate }}</td></span>
        </div>
        <p>
          {{ reply.content }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,
      category: '',
      title: '',
      author: '',
      content: '',
      created_at: '',
      //댓글
      replyList: [],

    }
  },
  mounted() {
    this.fnGetView()
    this.fnGetReply()
  },
  methods: {
    fnGetView() {
      this.$axios.get('/api/v1/post/' + this.idx, {
        params: this.requestBody
      }).then((res) => {
        this.title = res.data.title
        this.author = res.data.memberNickname
        this.content = res.data.content
        this.created_at = res.data.regDate
        this.category = res.data.postCategory
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnGetReply() {
      this.$axios.get('/api/v1/reply/' + this.idx, {
        params: this.requestBody
      }).then((res) => {
        this.replyList = res.data
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnUpdate() {
      this.$router.push({
        path: './write',
        query: this.requestBody
      })
    },
    fnDelete() {
      if (!confirm("삭제하시겠습니까?")) return

      this.$axios.delete('/api/v1/post/' + this.idx, {})
          .then(() => {
            alert('삭제되었습니다.')
            this.fnList();
          }).catch((err) => {
        console.log(err);
      })
    }
  }
}
</script>
<style scoped>


</style>