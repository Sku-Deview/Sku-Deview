<!--<template>-->
<!--    <h2>쪽지 보내기</h2>-->
<!--    <hr>-->
<!--    <h3> 받는 사람 : {{ receiverName }}</h3>-->
<!--    <div class="board-contents">-->
<!--        <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">-->
<!--    </div>-->
<!--    <div class="board-contents">-->
<!--      <textarea id="" cols="30" rows="10" v-model="content" class="w3-input w3-border" style="resize: none;"-->
<!--                placeholder="내용을 입력해주세요.">-->
<!--      </textarea>-->
<!--    </div>-->
<!--    <div class="common-buttons">-->
<!--        <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;-->
<!--    </div>-->
<!--</template>-->

<template>
  <div class="container my-5 col-8">
    <div class="message-header">
      <h2><strong>쪽지 보내기</strong></h2>
    </div>
    <hr class="message-divider">
    <div class="message-input">
      <div class="form-group row align-items-center">
        <label for="title" class="col-md-2 col-form-label"><strong>제목:</strong></label>
        <div class="col-md-4">
          <input
              type="text"
              id="title"
              v-model="title"
              class="form-control message-text-input"
              placeholder="제목을 입력해주세요."
              required
          >
        </div>
        <label for="receiverName" class="col-md-2 col-form-label"><strong>받는 사람:</strong></label>
        <div class="col-md-4">
          <input
              type="text"
              id="receiverName"
              v-model="receiverName"
              class="form-control message-text-input"
              placeholder="받는 사람을 입력해주세요."
              readonly
          >
        </div>
      </div>
    </div>
    <div class="message-input mt-3">
      <div class="form-group row align-items-center">
        <label for="content" class="col-md-2 col-form-label"><strong>내용:</strong></label>
        <div class="col-md-10">
          <textarea
              id="content"
              cols="40"
              rows="10"
              v-model="content"
              class="form-control message-text-input"
              style="resize: none;"
              placeholder="내용을 입력해주세요."
              required
          ></textarea>
        </div>
      </div>
    </div>
    <div class="message-buttons mt-3 d-flex justify-content-end">
      <button type="button" class="btn btn-primary btn-rounded" @click="fnSave">보내기</button>
    </div>
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
      if (!this.title) { // 제목 또는 내용이 입력되지 않은 경우
        alert("제목을 입력해주세요.");
        return;
      }
      if (!this.content) {
        alert("내용을 입력해주세요.");
        return;
      }

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
            .then((res) => {
              alert(res.data.message)
              this.fnSendList()
            }).catch((err) => {
          alert(err.response.data.message)
          location.reload()
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