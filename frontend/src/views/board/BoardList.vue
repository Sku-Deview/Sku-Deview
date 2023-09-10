<!--<template>-->
<!--    <div class="board-list">-->
<!--        <div class="common-buttons">-->
<!--            <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnWrite">등록</button>-->
<!--        </div>-->
<!--        <table class="w3-table-all">-->
<!--            <thead>-->
<!--            <tr>-->
<!--                <th>No</th>-->
<!--                <th>Category</th>-->
<!--                <th>제목</th>-->
<!--                <th>작성자</th>-->
<!--                <th>등록일시</th>-->
<!--            </tr>-->
<!--            </thead>-->
<!--            <tbody>-->
<!--            <tr v-for="(item, idx) in list" :key="idx">-->
<!--                <td>{{ item.postId }}</td>-->
<!--                <td>{{ item.postCategory }}</td>-->
<!--                <td>-->
<!--                    <b-button v-if="item.title.length<15"><a v-on:click="fnView(`${item.postId}`)">{{ item.title }}</a>-->
<!--                    </b-button>-->
<!--                    <b-button v-else><a v-on:click="fnView(`${item.postId}`)">{{-->
<!--                        item.title.substring(0, 15) + "..."-->
<!--                        }}</a></b-button>-->
<!--                </td>-->
<!--                <td>{{ item.memberNickname }}</td>-->
<!--                <td>{{ item.regDate }}</td>-->
<!--            </tr>-->
<!--            </tbody>-->
<!--        </table>-->

<!--        <div class="pagination w3-bar w3-padding-16 w3-small" v-if="paging.total_list_cnt > 0">-->
<!--      <span class="pg">-->
<!--      <a href="javascript:;" @click="fnPage(1)" class="first w3-button w3-bar-item w3-border">&lt;&lt;</a>-->
<!--      <a href="javascript:;" v-if="paging.start_page > 10" @click="fnPage(`${paging.start_page-1}`)"-->
<!--         class="prev w3-button w3-bar-item w3-border">&lt;</a>-->
<!--      <template v-for=" (n,index) in paginavigation()">-->
<!--          <template v-if="paging.page==n">-->
<!--              <strong class="w3-button w3-bar-item w3-border w3-green" :key="index">{{ n }}</strong>-->
<!--          </template>-->
<!--          <template v-else>-->
<!--              <a class="w3-button w3-bar-item w3-border" href="javascript:;" @click="fnPage(`${n}`)" :key="index">{{-->
<!--                  n-->
<!--                  }}</a>-->
<!--          </template>-->
<!--      </template>-->
<!--      <a href="javascript:;" v-if="paging.total_page_cnt > paging.end_page"-->
<!--         @click="fnPage(`${paging.end_page+1}`)" class="next w3-button w3-bar-item w3-border">&gt;</a>-->
<!--      <a href="javascript:;" @click="fnPage(`${paging.total_page_cnt}`)" class="last w3-button w3-bar-item w3-border">&gt;&gt;</a>-->
<!--      </span>-->
<!--        </div>-->
<!--    </div>-->
<!--</template>-->

<template>
    <div class="board-list mt-5">
        <table class="table table-striped">
            <colgroup>
                <col style="width: 5%;" /> <!-- No 열의 너비 -->
                <col style="width: 10%;" /> <!-- 카테고리 열의 너비 -->
                <col style="width: auto;" /> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
                <col style="width: 15%;" /> <!-- 작성자 열의 너비 -->
                <col style="width: 20%;" /> <!-- 등록일시 열의 너비 -->
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

        <div class="pagination d-flex justify-content-center">
            <ul class="pagination">
                <li class="page-item" :class="{ disabled: paging.start_page <= 1 }">
                    <a class="page-link" @click="fnPage(1)" href="javascript:;">&lt;&lt;</a>
                </li>
                <li class="page-item" :class="{ disabled: paging.start_page <= 1 }">
                    <a class="page-link" @click="fnPage(paging.start_page - 1)" href="javascript:;">&lt;</a>
                </li>
                <li class="page-item" v-for="n in paginavigation()" :key="n" :class="{ active: paging.page === n }">
                    <a class="page-link" @click="fnPage(n)" href="javascript:;">{{ n }}</a>
                </li>
                <li class="page-item" :class="{ disabled: paging.end_page >= paging.total_page_cnt }">
                    <a class="page-link" @click="fnPage(paging.end_page + 1)" href="javascript:;">&gt;</a>
                </li>
                <li class="page-item" :class="{ disabled: paging.end_page >= paging.total_page_cnt }">
                    <a class="page-link" @click="fnPage(paging.total_page_cnt)" href="javascript:;">&gt;&gt;</a>
                </li>
            </ul>
        </div> <hr>

<!--        TODO : 검색 폼만 추가. 아직 동작 X -->
        <form @submit.prevent="fnSearch">
            <div class="form-group row align-items-center">
                <label for="searchText" class="col-md-2 col-form-label">검색:</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" id="searchText" v-model="searchText" placeholder="검색어를 입력하세요" />
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary btn-rounded">검색</button>
                </div>
            </div>
        </form>
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

/* 필요한 경우 다른 스타일을 추가하세요. */
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
    data() { //변수생성
        return {
            requestBody: {}, //리스트 페이지 데이터전송
            list: {}, //리스트 데이터
            no: '', //게시판 숫자처리
            paging: {
                block: 0,
                end_page: 0,
                next_block: 0,
                page: 0,
                page_size: 0,
                prev_block: 0,
                start_index: 0,
                start_page: 0,
                total_block_cnt: 0,
                total_list_cnt: 0,
                total_page_cnt: 0,
            }, //페이징 데이터
            page: this.$route.query.page ? this.$route.query.page : 0,
            size: this.$route.query.size ? this.$route.query.size : 10,
            keyword: this.$route.query.keyword,
            paginavigation: function () { //페이징 처리 for문 커스텀
              let pageNumber = [] //;
              let start_page = this.paging.start_page;
              let end_page = this.paging.end_page;
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
                size: this.size
            }

            this.$axios.get("/api/v1/posts", {
                params: this.requestBody,

            }).then((res) => {

                this.list = res.data.data.content  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
            }).catch((err) => {
                if (err.message.indexOf('Network Error') > -1) {
                    alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
            })
        },
        fnView(idx) {
            this.requestBody.idx = idx
            this.$router.push({
                path: './detail',
                query: this.requestBody
            })
        },
        fnWrite() {
            this.$router.push({
                path: './write'
            })
        },
        fnPage(n) {
            if (this.page !== n) {
                this.page = n
                this.fnGetList()
            }
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
