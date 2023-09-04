<template>
  <h2>쪽지 보내기</h2>
  <hr>
  <h3> 받는 사람 : {{ receiverName }}</h3>
  <div class="board-contents">
    <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">
  </div>
  <div class="board-contents">
      <textarea id="" cols="30" rows="10" v-model="content" class="w3-input w3-border" style="resize: none;"
                placeholder="내용을 입력해주세요.">
      </textarea>
  </div>
  <div class="common-buttons">
    <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
  </div>
</template>

<script>
export default {
  name: "MessageWrite",
  data() { //변수생성
    return {
      title: '',
      content: '',
      receiverName: this.$route.query.name,
    }
  },
  methods: {
    fnSave() {
      // let apiUrl = '/api/v1/post'
      this.form = {
        "title": this.title,
        "content": this.content,
        "receiverName": this.receiverName,
      }

      if (this.idx === undefined) {
        //INSERT
        this.$axios.post("/api/v1/message", this.form, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('user_token')}`
          }
        })
            .then(() => {
              alert('메세지 전송이 완료 되었습니다.')
              this.fnSendList()
            }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      }
    },
    fnSendList() {
      this.$router.push({
        path: '/message/send',
      })
    },
  }
}

</script>