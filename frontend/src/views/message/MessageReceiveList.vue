<template>

  <div class="black-bg" v-if="modalOpen === true">
    <div class="white-bg">
      <h1>(받은 메세지) {{ modalList.title }}</h1>
      <h3 class="sendName">발신자 :{{ modalList.senderName }}</h3>
      <h5 class="create-at">{{modalList.regDate}}</h5>
      <hr>
      <h2>{{ modalList.content }}</h2>
      <h3 class="receiverName">수신자 :{{ modalList.receiverName }}</h3>
      <b-button @click="modalOpen = false" class="modal-exit-btn " >
        닫기
      </b-button>
      <b-button @click="[modalOpen = false,toMessageWrite(modalList.senderName)]" class="modal-exit-btn button-button">
        답장
      </b-button>
    </div>
  </div>

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
      <td @click="modalOpen = true">
        <b-button v-if="item.title.length<20" v-on:click="clickModel(item)">{{ item.title }}</b-button>
        <b-button v-else v-on:click="clickModel(item)">{{ item.title.substring(0,20) + "..." }}</b-button>
      </td>
      <td>{{ item.senderName }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  data() {
    return{
      list:{},
      modalOpen:false,
      modalList: {},
    }
  },
  mounted() {
    this.ReceiveListGet()
  },
  methods: {
    ReceiveListGet() {
      this.$axios.get("/api/v1/message/received", {
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
    clickModel(item) {
      this.modalList = item
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

<style>
.button-button{
  margin-left: 10px;
  background-color: #42b983;
}
</style>