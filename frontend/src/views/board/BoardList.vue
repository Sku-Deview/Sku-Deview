<template>
  <div class="board-list mt-5">
    <div class="nav-buttons mb-3">
      <button :class="{ active: postCategory === '' }" class="btn btn-link" @click="fnSelectCategory('')">
        전체글
      </button>
      <button :class="{ active: postCategory === 'QNA' }" class="btn btn-link" @click="fnSelectCategory('QNA')">
        질문
      </button>
      <button :class="{ active: postCategory === 'INFO' }" class="btn btn-link" @click="fnSelectCategory('INFO')">
        정보
      </button>
      <button :class="{ active: postCategory === 'FREE' }" class="btn btn-link" @click="fnSelectCategory('FREE')">
        자유
      </button>
    </div>
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
      <!--공지사항 상시 상단 고정-->
      <tr v-for="(item, idx) in noticeList" :key="idx" @click="fnView(item.postId)" class="hover-pointer notice-post">
        <td>{{ item.postId }}</td>
        <td>{{ item.postCategory }}</td>
        <td>
                    <span v-if="item.title.length < 10">{{ item.title }} &nbsp;&nbsp; <i
                        class="fa-solid fa-comment">{{ item.replyCount }}</i>
                      <i class="fa-solid fa-heart">{{ item.likeCount }}</i> </span>
          <span v-else>{{ item.title.substring(0, 10) + "..." }}<i class="fa-solid fa-comment">{{ item.replyCount }}</i>
                      <i class="fa-solid fa-heart">{{ item.likeCount }}</i></span>
        </td>
        <td>{{ item.memberNickname }}</td>
        <td>{{ formatDateTime(item.regDate) }}</td>
      </tr>

      <tr v-for="(item, idx) in list" :key="idx" @click="fnView(item.postId)" class="hover-pointer">
        <td>{{ item.postId }}</td>
        <td>{{ item.postCategory }}</td>
        <td>
                    <span v-if="item.title.length < 10">{{ item.title }} &nbsp;&nbsp; <i
                        class="fa-solid fa-comment">{{ item.replyCount }}</i>
                      <i class="fa-solid fa-heart">{{ item.likeCount }}</i> </span>
          <span v-else>{{ item.title.substring(0, 10) + "..." }}<i class="fa-solid fa-comment">{{ item.replyCount }}</i>
                      <i class="fa-solid fa-heart">{{ item.likeCount }}</i></span>
        </td>
        <td>{{ item.memberNickname }}</td>
        <td>{{ formatDateTime(item.regDate) }}</td>
      </tr>
      </tbody>
    </table>

    <div class="common-buttons mb-3">
      <button type="button" class="btn btn-outline-dark btn-rounded" @click="fnWrite">
        <i class="fa-solid fa-pen"></i>
        <!--              글쓰기-->
      </button>
    </div>

    <div class="pagination d-flex justify-content-center">
      <ul class="pagination">
        <li class="page-item">
          <a class="page-link" @click="fnPage(0)" href="javascript:;">&lt;&lt;</a>
        </li>
        <li class="page-item">
          <a class="page-link" @click="fnPage(page - 1)" href="javascript:;">&lt;</a>
        </li>
        <li class="page-item" v-for="n in paginavigation()" :key="n">
          <a class="page-link" @click="fnPage(n)" href="javascript:;">{{ n + 1 }}</a>
        </li>
        <li class="page-item">
          <a class="page-link" @click="fnPage(page + 1)" href="javascript:;">&gt;</a>
        </li>
        <li class="page-item">
          <a class="page-link" @click="fnPage(totalPage - 1)" href="javascript:;">&gt;&gt;</a>
        </li>
      </ul>
    </div>
    <hr>

    <div class="form-group row align-items-center">
      <div class="col-md-2">
        <b-form-select v-model="searchType" :options="selectedOption" id="searchType"></b-form-select>
      </div>
      <div class="col-md">
        <input type="text" class="form-control" id="searchText" v-model="searchText" placeholder="검색어를 입력하세요"/>
      </div>
      <div class="col-md-2">
        <button type="submit" class="btn btn-primary btn-rounded" @click="fnSearch(searchType, searchText)">검색
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.hover-pointer {
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

/*TODO : 마우스 hover 시 테이블 열 색이 바뀌도록, 현재는 적용이 안됨 */
.hover-pointer:hover {
  background-color: blue; /* 마우스 호버 시 원하는 배경색으로 변경하세요. */
}

.notice-post {
  font-weight: bold;
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


<!--<script>-->
<!--import {reactive} from "vue";-->
<!--import axios from "axios";-->

<!--export default {-->
<!--  setup() {-->
<!--    const state = reactive({-->
<!--      items: [],-->
<!--      form: {-->
<!--        name: "",-->

<!--      }-->
<!--    })-->

<!--    const load = () => {-->
<!--      axios.get("/api/v1/post").then(({data}) => {-->
<!--        console.log(data);-->
<!--        state.items = data;-->
<!--      })-->
<!--    };-->


<!--    load();-->

<!--    return {state}-->
<!--  }-->
<!--}-->
<!--</script>-->
<script>
export default {

  data() {
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
      noticeList: {},
      postCategory: '',
      replyCount: '',
      likeCount: '',
      searchType: 'title',
      searchText: '',
      totalPage: '',
      selectedOption: [
        {value: 'title', text: '제목'},
        {value: 'writer', text: '작성자'},
        {value: 'titleAndWriter', text: '제목 + 작성자'},
      ],

      // URL에서 page, size 정보를 가져와서 저장
      page: this.$route.query.page ? this.$route.query.page : 0,
      size: this.$route.query.size ? this.$route.query.size : 10,

      paginavigation: function () {
        let pageNumber = []
        let start_page = 0;
        let end_page = this.totalPage;
        for (let i = start_page; i < end_page; i++) pageNumber.push(i);
        return pageNumber;
      }
    }
  },
  mounted() {
    this.fnGetList();
    this.fnGetNoticeList();
  },
  methods: {
    fnGetNoticeList() {
      this.$axios.get("/api/v1/posts/notice", {}).then((res) => {
        console.log(res);
        this.noticeList = res.data.data;
      }).catch((err) => {
        alert(err.response.data.message)
        location.reload()
      })
    },

    fnGetList() {
      this.requestBody = {
        page: this.page,
        size: this.size,
        totalPage: this.totalPage,
        postCategory: this.postCategory,
        searchText: this.searchText,
        searchType: this.searchType,
      }
      this.$axios.get("/api/v1/posts", {
        params: this.requestBody,
      }).then((res) => {
        console.log(res);
        this.page = res.data.data.number;
        this.size = res.data.data.size;
        this.totalPage = res.data.data.totalPages;
        this.list = res.data.data.content;
        this.replyCount = res.data.data.replyCount;
        this.likeCount = res.data.data.likeCount;
      }).catch((err) => {
        alert(err.response.data.message);
        location.reload()
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: {idx}
      })
    },
    fnWrite() {
      this.$router.push({
        path: './write'
      })
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnPage(n) {
      if (this.page !== n) {
        if (this.totalPage > n && n >= 0) {
          this.page = n;
          this.fnGetList()
        }
      }
    },
    fnSelectCategory(postCategory) {
      this.postCategory = postCategory;
      this.page = 0;
      this.$router.push({path: this.$route.path, query: {postCategory}});
      this.fnGetList();
    },
    fnSearch(searchType, searchText) {
      this.searchText = searchText;
      this.searchType = searchType;
      this.page = 0;
      this.$router.push({
        path: this.$route.path,
        query: {
          searchType: searchType,
          searchText: searchText
        }
      });
      this.fnGetList();
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
}
</script>
