<template>

  <div>
    MemberMain 페이지 입니다!
    <router-link to="/member/detail">MemberDetail</router-link>
  </div>

  <div class="row mt-3">

    <div class="col-md-2">
      <h2>내 정보</h2>
      <span>{{this.memberInfo}}</span>
    </div>

    <div class="col-md">
      <div class="board-list">
        <h2>내가 작성한 게시물</h2>
        <table class="table table-striped">
          <colgroup>
            <col style="width: 5%;"/> <!-- No 열의 너비 -->
            <col style="width: 5%;"/> <!-- 카테고리 열의 너비 -->
            <col style="width: auto;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
            <col style="width: 15%;"/> <!-- 작성자 열의 너비 -->
            <col style="width: 15%;"/> <!-- 등록일시 열의 너비 -->
          </colgroup>
          <thead>
          <tr>
            <th scope="col">No</th>
            <th scope="col">카테고리</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">등록일시</th>
          </tr>
          </thead>
          <tbody>

          <tr v-for="(item, idx) in list" :key="idx" @click="fnView(item.postId)" class="hover-pointer">
            <td>{{ item.postId }}</td>
            <td>{{ item.postCategory }}</td>
            <td>
              <span v-if="item.title.length < 10">{{ item.title }} &nbsp;&nbsp;
                <i class="fa-solid fa-comment small-icon">{{ item.replyCount }}</i>
                <i class="fa-solid fa-heart small-icon">{{ item.likeCount }}</i>
              </span>
              <span v-else>{{ item.title.substring(0, 10) + "..." }}
                <i class="fa-solid fa-comment small-icon">{{ item.replyCount }}</i>
                <i class="fa-solid fa-heart small-icon">{{ item.likeCount }}</i>
              </span>
            </td>
            <td>{{ item.memberNickname }}</td>
            <td>{{ formatDateTime(item.regDate) }}</td>
          </tr>

          </tbody>
        </table>
      </div>
    </div>

  </div>


</template>

<script>

export default {

  data() {
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
      memberInfo: {},
      postCategory: '',
      replyCount: '',
      likeCount: '',
      memberNickname: localStorage.getItem('user_nickname'),
    }
  },

  mounted() {
    this.fnLoginMember(this.memberNickname);
    this.fnGetList();
  },

  methods: {
    fnLoginMember(memberNickname) {
      this.$axios.post(`/api/v1/member/${memberNickname}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res.data.data);
        // this.memberInfo = res.data.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnGetList() {
      this.$axios.post(`/api/v1/posts/member/${this.memberNickname}`, {})
          .then((res) => {
            this.list = res.data.data;
          }).catch((err) => {
        alert(err.response.data.message)
        location.reload()
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: '../board/detail',
        query: {idx}
      })
    },
    formatDateTime(dateTimeStr) {
      const dateTime = new Date(dateTimeStr);
      const year = dateTime.getFullYear();
      const month = String(dateTime.getMonth() + 1).padStart(2, '0');
      const day = String(dateTime.getDate()).padStart(2, '0');
      const hours = String(dateTime.getHours()).padStart(2, '0');
      const minutes = String(dateTime.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}`;
    },

  }

};

</script>

<style scoped>
.hover-pointer {
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

.small-icon {
  font-size: 15px; /* Adjust the font size to your preference */
}

.small-button {
  font-size: 12px; /* Adjust the font size for the button text */
  padding: 4px 8px; /* Adjust padding for the button */
}

/*TODO : 마우스 hover 시 테이블 열 색이 바뀌도록, 현재는 적용이 안됨 */
.hover-pointer:hover {
  background-color: blue; /* 마우스 호버 시 원하는 배경색으로 변경하세요. */
}

.nav-buttons {
  display: flex;
  gap: 10px;
}

.btn {
  text-decoration: none;
}

.btn.active {
  background-color: #007bff;
  color: #fff;
}
</style>