<template>
  <div class="board-detail">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
    </div>
    <div>
      <hr>
      <label>Category</label>
      <b-form-select v-model="postCategory" :options="options" size="sm" class="mt-3" ></b-form-select>
      <hr>

    </div>
    <div class="board-contents">
      <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">
    </div>
    <div class="board-contents">
      <textarea id="" cols="30" rows="10" v-model="content" class="w3-input w3-border" style="resize: none;">
      </textarea>
    </div>
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
    </div>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      title: '',
      content: '',
      created_at: '',
      postCategory:'',
      options: [
        { value: 'NOTICE', text: '공지사항' },
        { value: 'QNA', text: '질문' },
        { value: 'FREE', text: '자유' },
        { value: 'INFO', text: '정보' },
          ]
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      if (this.idx !== undefined) {
        this.$axios.get('/api/v1/post/' + this.idx,{
            params: this.requestBody

        }).then((res) => {
          this.title = res.data.title
          this.author = res.data.memberNickname
          this.contents = res.data.content
          this.created_at = res.data.regDate
        }).catch((err) => {
          console.log(err)
        })
      }
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnSave() {
      // let apiUrl = '/api/v1/post'
      this.form = {
        "title": this.title,
        "content": this.content,
        "postCategory": this.postCategory,
      }

      if (this.idx === undefined) {
        //INSERT
        this.$axios.post("/api/v1/post",this.form, {
          headers:{
            Authorization :`Bearer ${ localStorage.getItem('user_token')}`
          }
        })
            .then((res) => {
              alert('글이 저장되었습니다.')
              this.fnView(res.data)
            }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      } else {
        //UPDATE
        this.$axios.patch(apiUrl, this.form)
            .then((res) => {
              alert('글이 저장되었습니다.')
              this.fnView(res.data.idx)
            }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      }
    }
  }
}
</script>
<style scoped>

</style>