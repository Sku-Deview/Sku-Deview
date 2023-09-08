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
      <span v-html="content"></span>
    </div>
    <hr>

    <div class="reply-contents">
      <textarea id="" cols="30" rows="10" v-model="reply" class="w3-input w3-border" style="resize: none;">
      </textarea>
      <div class="common-buttons">
        <button type="button" class="w3-button w3-round w3-blue" v-on:click="replySave()">댓글 저장</button>&nbsp;
      </div>
    </div>


    <div v-for="(reply, idx) in replyList" :key="idx">
      <i class="fa-solid fa-trash" @click="removeReply(reply.replyId,reply.postId)"></i>
      <i class="fa-solid fa-comment" @click="toMessageWrite(reply.memberNickname)"></i>
      <div class="reply-detail">
        [{{ reply.memberNickname }}]
        <div class="create-at">
          <span>{{ reply.regDate }}</span>
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
      reply: '',

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
        this.title = res.data.data.title
        this.author = res.data.data.memberNickname
        this.content = res.data.data.content
        this.created_at = res.data.data.regDate
        this.category = res.data.data.postCategory
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
    fnPost(postId) {
      this.requestBody.idx = postId
      this.$router.go(this.$router.currentRoute)
    },
    fnUpdate() {
      this.$router.push({
        path: './write',
        query: this.requestBody
      })
    },
    fnDelete() {
      if (!confirm("게시글을 삭제하시겠습니까?")) return

      this.$axios.delete('/api/v1/post/' + this.idx, {})
          .then((res) => {
            alert(res.data.message)
            this.fnList();
          }).catch((err) => {
        console.log(err);
      })
    },
    fnGetReply() {
      this.$axios.get('/api/v1/reply/' + this.idx, {
        params: this.requestBody
      }).then((res) => {
        console.log(res.data)
        this.replyList = res.data
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    removeReply(replyId, postId) {
      if (!confirm("댓글을 삭제하시겠습니까?")) return
      this.$axios.delete(`/api/v1/reply/${postId}/${replyId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then(() => {
        alert('삭제되었습니다.')
        this.fnPost(postId);
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    replySave() {
      this.form = {
        "content": this.reply
      }
      console.log(this.form.content)
      //INSERT
      this.$axios.post(`/api/v1/reply/` + this.idx, this.form, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then(() => {
            alert('댓글이 저장되었습니다.')
            this.fnPost(this.idx);
          }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    toMessageWrite(receiverNickname) {
      this.$router.push({
        path:'/message/write',
        query:{name: receiverNickname}
      })
    },
  }
}
</script>
<style scoped>

</style>