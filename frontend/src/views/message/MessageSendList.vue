<template>

  <div class="black-bg" v-if="modalOpen === true">
    <div class="white-bg">
      <h1>(보낸 메세지) {{ modalList.title }}</h1>
      <h3 class="receiverName">수신자 :{{ modalList.receiverName }}</h3>
      <hr>
      <h2>{{ modalList.content }}</h2>
      <h3 class="sendName">발신자 :{{ modalList.senderName }}</h3>
      <b-button @click="modalOpen = false" class="modal-exit-btn">
        닫기
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
        <b-button v-on:click="clickModel(item)">{{ item.title }}</b-button>
      </td>
      <td>{{ item.senderName }}</td>
    </tr>
    </tbody>
  </table>


</template>

<script>
export default {
  data() {
    return {
      list: {},
      modalList: {},
      modalOpen: false,
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
            this.list = res.data
          }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    clickModel(item) {
      this.modalList = item
    },

  }
}
</script>

<style>
.sendName {
  color: #ff2b1d;
}

.receiverName {
  color: #204bff;
}

.black-bg {
  display: flex;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.432);
  position: fixed;
  padding: 5px;
}

.white-bg {
  width: 100%;
  height: 50%;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
}

.modal-exit-btn {
  margin-top: 30px;
}

.modal-exit-btn:hover {
  cursor: pointer;
}
</style>