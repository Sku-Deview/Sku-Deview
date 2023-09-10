<template>
    <div class="board-detail mt-5">
        <div class="common-buttons mb-3">
            <button type="button" class="btn btn-primary btn-rounded" @click="fnUpdate">수정</button>
            <button type="button" class="btn btn-danger btn-rounded" @click="fnDelete">삭제</button>
            <button type="button" class="btn btn-success btn-rounded" @click="fnList">목록</button>
        </div>

        <h2><strong>[{{ category }}] {{ title }}</strong></h2>
        <div>
            <p class="w3-large mb-3 mt-3">
                {{ author }}
                <span class="small-font">&nbsp {{ created_at }}</span>
                <span class="small-font">&nbsp&nbsp 조회수: {{view_count}}</span>
            </p>
        </div>

        <hr>

        <div class="board-contents" v-html="content"></div>


        <div v-for="(reply, idx) in replyList" :key="idx">
            <i class="fa-solid fa-trash" @click="removeReply(reply.replyId,reply.postId)"></i>
            <i class="fa-solid fa-comment" @click="toMessageWrite(reply.memberNickname)"></i>
            <div class="reply-detail">
                <strong>[{{ reply.memberNickname }}]</strong>
                <div class="create-at">
                    <span>{{ reply.regDate }}</span>
                </div>
                <p>
                    {{ reply.content }}
                </p>
            </div>
        </div>

        <div class="mt-5">
            <label for="postTitle"><strong>댓글</strong></label>
            <div style="position: relative;">
                <textarea id="postTitle" rows="5" v-model="reply" class="form-control" style="resize: none;"
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
</style>


<script>
export default {
    data() { //변수생성
        return {
            requestBody: this.$route.query,
            idx: this.$route.query.idx,
            category: '',
            title: '',
            author: '',
            content: '',
            created_at: '',
            view_count:'',
            //댓글
            replyList: [],
            reply: '',

        }
    },
    mounted() {
        this.fnGetView()
        this.fnGetReply()
    },
    methods: {
        formatDate(dateString) {
            const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' };
            return new Date(dateString).toLocaleString(undefined, options);
        },

        fnGetView() {
            this.$axios.get('/api/v1/post/' + this.idx, {
                params: this.requestBody
            }).then((res) => {
                this.title = res.data.data.title
                this.author = res.data.data.memberNickname
                this.content = res.data.data.content
                this.created_at = this.formatDate(res.data.data.regDate)
                this.category = res.data.data.postCategory
                this.view_count = res.data.data.viewCount
            }).catch((err) => {
                if (err.message.indexOf('Network Error') > -1) {
                    alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
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
                console.log(err);
            })
        },
        fnGetReply() {
            this.$axios.get('/api/v1/reply/' + this.idx, {
                params: this.requestBody
            }).then((res) => {
                this.replyList = res.data.data
            }).catch((err) => {
                if (err.message.indexOf('Network Error') > -1) {
                    alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
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
                if (err.message.indexOf('Network Error') > -1) {
                    alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
            })
        },
        replySave() {
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
                if (err.message.indexOf('Network Error') > -1) {
                    alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                }
            })
        },
        toMessageWrite(receiverNickname) {
            this.$router.push({
                path: '/message/write',
                query: {name: receiverNickname}
            })
        },
    }
}
</script>
