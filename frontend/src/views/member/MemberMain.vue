<template>

  <div class="row mt-5">

    <div class="col-md-3">
      <div class="row">
        <h2>내 정보</h2>
      </div>
      <div class="row">
        <div class="col-md-4">
          <i class="fa-solid fa-user" style="font-size: 50px"></i>
        </div>
        <div class="col">
          <table>
            <tr>
              <td>이름:</td>
              <td>{{ this.name }}</td>
            </tr>
            <tr>
              <td>닉네임:</td>
              <td>{{ this.nickname }}</td>
            </tr>
            <tr>
              <td>기술:</td>
              <td>{{ this.skillName }}</td>
            </tr>
          </table>
        </div>
      </div>
      <br>
      <button>
        <router-link to="/member/detail">정보 수정</router-link>
      </button>

    </div>

    <div class="col-md">

      <!-- 네비게이션 탭 -->
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a class="nav-link" @click="showTab('myPosts')" :class="{ active: activeTab === 'myPosts' }">내가 작성한 게시물</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" @click="showTab('likedPosts')" :class="{ active: activeTab === 'likedPosts' }">내가 좋아요 누른
            게시물</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" @click="showTab('myReplies')" :class="{ active: activeTab === 'myReplies' }">내가 작성한 댓글</a>
        </li>
      </ul>

      <div v-if="activeTab === 'myPosts'">
        <div class="board-list mt-3">
          <h2>내가 작성한 게시물</h2>
          <table class="table table-striped">
            <colgroup>
              <col style="width: 5%;"/> <!-- No 열의 너비 -->
              <col style="width: 5%;"/> <!-- 카테고리 열의 너비 -->
              <col style="width: auto;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
              <col style="width: 20%;"/> <!-- 등록일시 열의 너비 -->
            </colgroup>
            <thead>
            <tr>
              <th scope="col">No</th>
              <th scope="col">카테고리</th>
              <th scope="col">제목</th>
              <th scope="col">작성일시</th>
            </tr>
            </thead>
            <tbody>

            <tr v-for="(item, idx) in boardList" :key="idx" @click="fnBoardView(item.postId)" class="hover-pointer">
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
              <td>{{ formatDateTime(item.regDate) }}</td>
            </tr>

            </tbody>
          </table>
        </div>
      </div>

      <div v-if="activeTab === 'likedPosts'">
        <div class="board-list mt-3">
          <h2>내가 좋아요 누른 게시물</h2>
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
              <th scope="col">작성일시</th>
            </tr>
            </thead>
            <tbody>

            <tr v-for="(item, idx) in likeBoardList" :key="idx" @click="fnBoardView(item.postId)" class="hover-pointer">
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

      <div v-if="activeTab === 'myReplies'">
        <div class="board-list mt-3">
          <h2>내가 작성한 댓글</h2>
          <table class="table table-striped">
            <colgroup>
              <col style="width: 5%;"/>
              <col style="width: 15%;"/>
              <col style="width: auto;"/>
              <col style="width: 15%;"/>
            </colgroup>
            <thead>
            <tr>
              <th scope="col">No</th>
              <th scope="col">게시글 ID</th>
              <th scope="col">댓글 내용</th>
              <th scope="col">작성일시</th>
            </tr>
            </thead>
            <tbody>

            <tr v-for="(item, idx) in replyList" :key="idx" @click="fnReplyView(item.postId)" class="hover-pointer">
              <td>{{ item.replyId }}</td>
              <td>{{ item.postId }}</td>
              <td>
                <span v-if="item.content.length < 20">{{ item.content }} &nbsp;&nbsp;</span>
                <span v-else>{{ item.content.substring(0, 20) + "..." }}</span>
              </td>
              <td>{{ formatDateTime(item.regDate) }}</td>
            </tr>

            </tbody>
          </table>
        </div>
      </div>
    </div>

  </div>


</template>

<script>

export default {

  data() {
    return {
      requestBody: {}, // 리스트 페이지 데이터전송
      boardList: {}, // 내가 작성한 게시물 리스트
      likeBoardList: {}, // 내가 좋아요 누른 게시물 리스트
      replyList: {}, // 내가 작성한 댓글 리스트
      activeTab: 'myPosts',
      name: '',
      nickname: '',
      skillName: {},
      postCategory: '',
      replyCount: '',
      likeCount: '',
      memberNickname: localStorage.getItem('user_nickname'),
    }
  },

  mounted() {
    this.fnLoginMember(this.memberNickname);
    this.fnBoardList();
    this.fnLikeBoardList();
    this.fnReplyList();
  },

  methods: {
    fnLoginMember(memberNickname) {
      this.$axios.post(`/api/v1/member/${memberNickname}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        this.name = res.data.data.name;
        this.nickname = res.data.data.nickname;
        this.skillName = res.data.data.skillName;
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
        }
      })
    },
    fnBoardList() {
      this.$axios.post(`/api/v1/posts/member/${this.memberNickname}`)
          .then((res) => {
            this.boardList = res.data.data;
          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
    fnLikeBoardList() {
      this.$axios.post(`/api/v1/posts/like/member/${this.memberNickname}`)
          .then((res) => {
            this.likeBoardList = res.data.data;
          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
    fnReplyList() {
      this.$axios.post(`/api/v1/reply/member/${this.memberNickname}`)
          .then((res) => {
            this.replyList = res.data.data;
            console.log(res.data.data);
          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
    showTab(tabName) {
      this.activeTab = tabName;
    },
    fnBoardView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: '../board/detail',
        query: {idx}
      })
    },
    // TODO: --> 내가 작성한 댓글 클릭 시, 해당 댓글을 조회하는 걸로 할지, 해당 게시글을 보여줄지 ?
    fnReplyView(idx) {
      this.requestBody.idx = idx;
      this.$router.push({
        path: '../reply/detail'
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