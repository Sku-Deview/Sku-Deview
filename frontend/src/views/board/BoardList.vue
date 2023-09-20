<template>
    <div class="board-list mt-5">
        <div class="nav-buttons mb-3">
            <button :class="{ active: postCategory === '' }" class="btn btn-link" @click="fnSelectCategory('')">
                전체글
            </button>
            <button :class="{ active: postCategory === 'NOTICE' }" class="btn btn-link" @click="fnSelectCategory('NOTICE')">
                공지사항
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
                <col style="width: 10%;"/> <!-- 카테고리 열의 너비 -->
                <col style="width: auto;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
                <col style="width: 15%;"/> <!-- 작성자 열의 너비 -->
                <col style="width: 20%;"/> <!-- 등록일시 열의 너비 -->
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
                    <span v-if="item.title.length < 15">{{ item.title }}</span>
                    <span v-else>{{ item.title.substring(0, 15) + "..." }}</span>
                </td>
                <td>{{ item.memberNickname }}</td>
                <td>{{ formatDateTime(item.regDate) }}</td>
            </tr>
            </tbody>
        </table>

        <div class="common-buttons mb-3">
            <button type="button" class="btn btn-outline-dark btn-rounded" @click="fnWrite">글쓰기</button>
        </div>

        <!--        <div class="pagination d-flex justify-content-center">-->
        <!--            <ul class="pagination">-->
        <!--&lt;!&ndash;                <li class="page-item" :class="{ disabled: paging.start_page <= 1 }">&ndash;&gt;-->
        <!--                <li class="page-item" >-->
        <!--                    <a class="page-link" @click="fnPage(1)" href="javascript:;">&lt;&lt;</a>-->
        <!--                </li>-->
        <!--                <li class="page-item" :class="{ disabled: paging.start_page <= 1 }">-->
        <!--                    <a class="page-link" @click="fnPage(paging.start_page - 1)" href="javascript:;">&lt;</a>-->
        <!--                </li>-->
        <!--                <li class="page-item" v-for="n in paginavigation()" :key="n" :class="{ active: paging.page === n }">-->
        <!--                    <a class="page-link" @click="fnPage(n)" href="javascript:;">{{ n }}</a>-->
        <!--                </li>-->
        <!--                <li class="page-item" :class="{ disabled: paging.end_page >= paging.total_page_cnt }">-->
        <!--                    <a class="page-link" @click="fnPage(paging.end_page + 1)" href="javascript:;">&gt;</a>-->
        <!--                </li>-->
        <!--                <li class="page-item" :class="{ disabled: paging.end_page >= paging.total_page_cnt }">-->
        <!--                    <a class="page-link" @click="fnPage(paging.total_page_cnt)" href="javascript:;">&gt;&gt;</a>-->
        <!--                </li>-->
        <!--            </ul>-->
        <!--        </div> <hr>-->

        <div class="pagination d-flex justify-content-center">
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" @click="fnPage(0)" href="javascript:;">&lt;&lt;</a>
                </li>
                <li class="page-item">
                    <a class="page-link" @click="fnPage(page - 1)" href="javascript:;">&lt;</a>
                </li>
                <!--                <li class="page-item" v-for="n in paginavigation()" :key="n">-->
                <!--                    <a class="page-link" @click="fnPage(n)" href="javascript:;">{{ page }}</a>-->
                <!--                </li>-->
                <li class="page-item" :key="n">
                    <a class="page-link" @click="fnPage(n)" href="javascript:;">{{ page }}</a>
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
                <button type="submit" class="btn btn-primary btn-rounded" @click="fnSearch(searchType, searchText)">검색</button>
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
    // data() { //변수생성
    //     return {
    //         requestBody: {}, //리스트 페이지 데이터전송
    //         list: {}, //리스트 데이터
    //         no: '', //게시판 숫자처리
    //         paging: {
    //             block: 0,
    //             end_page: 0,
    //             next_block: 0,
    //             page: 0,
    //             page_size: 0,
    //             prev_block: 0,
    //             start_index: 0,
    //             start_page: 0,
    //             total_block_cnt: 0,
    //             total_list_cnt: 0,
    //             total_page_cnt: 0,
    //         }, //페이징 데이터
    //         // URL에서 page, size 정보를 가져와서 저장
    //         page: this.$route.query.page ? this.$route.query.page : 0,
    //         size: this.$route.query.size ? this.$route.query.size : 10,
    //         keyword: this.$route.query.keyword,
    //         paginavigation: function () { //페이징 처리 for문 커스텀
    //           let pageNumber = [] //;
    //           let start_page = this.paging.start_page;
    //           let end_page = this.paging.end_page;
    //           for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
    //           return pageNumber;
    //         }
    //     }
    // },
    data() { //변수생성
        let totalPage;
        return {
            requestBody: {}, //리스트 페이지 데이터전송
            list: {}, //리스트 데이터
            postCategory: '',
            searchType: 'title',
            searchText: '',
            selectedOption: [
                {value: 'title', text: '제목' },
                {value: 'writer', text: '작성자'},
                {value: 'titleAndWriter', text: '제목 + 작성자'},
            ],

            // URL에서 page, size 정보를 가져와서 저장
            page: this.$route.query.page ? this.$route.query.page : 0,
            size: this.$route.query.size ? this.$route.query.size : 10,

            paginavigation: function () { //페이징 처리 for문 커스텀
                let pageNumber = [] //;
                let start_page = this.page;
                let end_page = this.page + 5;
                for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
                return pageNumber;
            }
        }
    },
    mounted() {
        this.fnGetList()
    },
    methods: {
        fnGetList() {
            this.requestBody = { // 데이터 전송
                // keyword: this.keyword,
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
                this.page = res.data.data.number;
                this.size = res.data.data.size;
                this.totalPage = res.data.data.totalPages;
                this.list = res.data.data.content  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
            }).catch((err) => {
              alert(err.response.data.message)
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
            this.page = 0; // 카테고리 변경 시 페이지를 0으로 리셋
            // URL을 업데이트하여 선택한 카테고리 정보를 서버로 전달
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
        // 등록일시를 원하는 형식으로 포맷팅하는 메서드
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
