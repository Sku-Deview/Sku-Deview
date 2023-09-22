<template>
    <div class="board-detail mt-5">
        <div class="common-buttons mb-3">
            <button v-if="isAuthor()" type="button" class="btn btn-primary btn-rounded mr-2" @click="fnUpdate"
                    style="margin-right: 3px;">수정
            </button>
            <button v-if="isAuthor()" type="button" class="btn btn-danger btn-rounded mr-2" @click="fnDelete"
                    style="margin-right: 3px;">삭제
            </button>
            <button type="button" class="btn btn-success btn-rounded mr-2" @click="fnList">목록</button>
        </div>
        <h2><strong>[{{ category }}] {{ title }}</strong></h2>
        <div>
            <p class="w3-large mb-3 mt-3">
                <i class="fa-solid fa-comment" v-if="!isAuthor()" @click="toMessageWrite(author)"></i>
                <i class="fa-solid" v-if="!isAuthor()" @click="toReportPost(postId, title)">신고</i>
                {{ author }}
                <span class="small-font">&nbsp {{ created_at }}</span>
                <span class="small-font">&nbsp&nbsp 조회수: {{ view_count }}</span>
            </p>
        </div>

        <hr>

        <div class="board-contents" v-html="content"></div>

        <div class="button-container">
            <button @click="toPostLike(postId, loginUserNickname)" class="btn btn-outline-primary btn-rounded" :class="{ 'btn-liked': isLiked }">
                {{ isLiked ? '좋아요' : '좋아요' }}
            </button>
        </div>

        <hr>

        <div v-for="(reply, idx) in replyList" :key="idx" class="mt-5">
<!--            <i class="fa-solid fa-trash" v-if="isReplyAuthor()" @click="removeReply(reply.replyId,reply.postId)"></i>-->
<!--            <i class="fa-solid fa-comment" v-if="!isReplyAuthor()" @click="toMessageWrite(reply.memberNickname)"></i>-->
            <i class="fa-solid fa-trash" @click="removeReply(reply.replyId,reply.postId)"></i>
            <i class="fa-solid fa-comment" @click="toMessageWrite(reply.memberNickname)"></i>
            <div class="reply-detail">
                <strong>[{{ reply.memberNickname }}]</strong>
                <div class="create-at">
                    <span>{{ formatDate(reply.regDate) }}</span>
                </div>
                <p>
                    {{ reply.content }}
                </p>
            </div>
        </div>

        <div class="mt-5">
            <label for="reply"><strong>댓글</strong></label>
            <div style="position: relative;">
                <textarea id="reply" ref="replyInput" rows="5" v-model="reply" class="form-control"
                          style="resize: none;"
                          placeholder="댓글을 남겨보세요."></textarea>
                <button type="button" class="btn btn-outline-primary btn-rounded" @click="replySave"
                        style="position: absolute; right: 10px; bottom: 10px;">댓글 저장
                </button>
            </div>
        </div>
    </div>
</template>


<style scoped>
.small-font {
    font-size: 0.8rem;
    color: gray;
}
.button-container {
    display: flex;
    justify-content: center;
}

/* 좋아요 버튼 스타일 */
.btn {
    margin: 0 auto; /* 가운데 정렬 */
    /* 나머지 스타일 유지 */
}
/* 좋아요 버튼 채우기 스타일 */
.btn-liked {
    justify-content: center;
    margin: 0 auto; /* 가운데 정렬 */
    background-color: #007bff; /* 원하는 색상으로 변경 */
    color: #fff; /* 텍스트 색상을 변경할 수도 있음 */
}
</style>


<script>
export default {
    data() { //변수생성
        return {
            requestBody: this.$route.query,
            idx: this.$route.query.idx,
            postId: '',
            category: '',
            title: '',
            author: '',
            content: '',
            created_at: '',
            view_count: '',
            //댓글
            replyList: [],
            reply: '',
            replyAuthorNickname: '',
            // 좋아요
            loginUserNickname: localStorage.getItem('user_nickname'),
            isLiked: false,
            likeCount: 0,
        }
    },
    mounted() {
        this.fnGetView()
        this.fnGetReply()
    },
    methods: {
        formatDate(dateString) {
            const options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};
            return new Date(dateString).toLocaleString(undefined, options);
        },

        fnGetView() {
            this.$axios.get('/api/v1/post/' + this.idx, {
                params: this.requestBody
            }).then((res) => {
                this.postId = res.data.data.postId
                this.title = res.data.data.title
                this.author = res.data.data.memberNickname
                this.content = res.data.data.content
                this.created_at = this.formatDate(res.data.data.regDate)
                this.category = res.data.data.postCategory
                this.view_count = res.data.data.viewCount
                // 서버에서 게시물의 좋아요 상태와 개수 가져오기
                // this.isLiked = res.data.data.isLiked;
                this.likeCount = res.data.data.likeCount;
            }).catch((err) => {
                alert(err.response.data.message)
                this.fnList()
            })
        },

        fnList() {
            delete this.requestBody.idx
            this.$router.push({
                path: './list',
                query: this.requestBody
            })
        },
        fnPost(postId) {
            this.requestBody.idx = postId
            this.$router.go(this.$router.currentRoute)
        },
        fnUpdate() {
            this.$router.push({
                path: './write',
                query: this.requestBody
            })
        },
        fnDelete() {
            if (!confirm("게시글을 삭제하시겠습니까?")) return

            this.$axios.delete('/api/v1/post/' + this.idx, {})
                .then((res) => {
                    alert(res.data.message)
                    this.fnList();
                }).catch((err) => {
                alert(err.response.data.message)
                location.reload()
            })
        },
        fnGetReply() {
            this.$axios.get('/api/v1/reply/' + this.idx, {
                params: this.requestBody
            }).then((res) => {
                console.log("llll"+res);
                console.log(res.data.data);
                this.replyList = res.data.data
                this.replyAuthorNickname = res.data.data.memberNickname
            }).catch((err) => {
                alert(err.response.data.message)
                location.reload()
            })
        },
        removeReply(replyId, postId) {
            if (!confirm("댓글을 삭제하시겠습니까?")) return
            this.$axios.delete(`/api/v1/reply/${postId}/${replyId}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('user_token')}`
                }
            }).then((res) => {
                alert(res.data.message)
                this.fnPost(postId);
            }).catch((err) => {
                alert(err.response.data.message)
                location.reload()
            })
        },
        replySave() {
            if (!this.reply) {
                alert("댓글을 입력해주세요.");
                this.$refs.replyInput.focus();
                return;
            }

            this.form = {
                "content": this.reply
            }
            console.log(this.form.content)
            //INSERT
            this.$axios.post(`/api/v1/reply/` + this.idx, this.form, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('user_token')}`
                }
            })
                .then((res) => {
                    alert(res.data.message)
                    this.fnPost(this.idx);
                }).catch((err) => {
                alert(err.response.data.message)
                location.reload()
            })
        },
        toMessageWrite(receiverNickname) {
            this.$router.push({
                path: '/message/write',
                query: {name: receiverNickname}
            })
        },
        toReportPost(postId, title) {
            this.$router.push({
                path: '/report/write',
                query: {
                    reportPost: postId,
                    postTitle: title,
                }

            })
        },
        toPostLike(postId, loginNickname) {
            // 좋아요 상태 토글
            this.isLiked = !this.isLiked;

            this.$axios.post(`/api/v1/post/like/${postId}/${loginNickname}`, { like: this.isLiked })

                .then((res) => {
                    if (this.isLiked) {
                        this.likeCount++;
                    } else {
                        this.likeCount--;
                    }
                })
                .catch((err) => {
                    alert(err.response.data.message);
                    location.reload();
                    console.error(err);
                });
        },
        isAuthor() {
            if (localStorage.getItem("user_nickname") === this.author) {
                return true;
            } else return false;
        },
        isReplyAuthor() {
            console.log("===="+this.replyAuthorNickname);
            if (localStorage.getItem("user_nickname") === this.replyAuthorNickname) {
                return true;
            } else return false;
        },

    }
}
</script>
