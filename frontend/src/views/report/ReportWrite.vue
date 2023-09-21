<template>
    <div class="container my-5 col-8">
        <div class="report-header">
            <h2><strong>게시글 신고하기</strong></h2>
        </div>
        <hr class="report-divider">
        <div class="report-input">
            <div class="form-group row align-items-center">
                <div class="row mb-3">
                    <label for="reportPost" class="col-md-2 col-form-label"><strong>신고 게시물 제목:</strong></label>
                    <div class="col-md-10">
                        <input
                                type="text"
                                id="postTitle"
                                v-model="postTitle"
                                class="form-control report-text-input"
                                readonly
                        >
                    </div>
                </div>
                <div class="row">
                    <label for="title" class="col-md-2 col-form-label"><strong>신고 제목:</strong></label>
                    <div class="col-md-4">
                        <input
                                type="text"
                                id="title"
                                v-model="title"
                                class="form-control report-text-input"
                                placeholder="제목을 입력해주세요."
                                ref="titleInput"
                                required
                        >
                    </div>

                    <label for="reportCategory" class="col-md-2 col-form-label"><strong>신고 카테고리:</strong></label>
                    <div class="col-md-4">
                        <b-form-select v-model="reportCategory" :options="options" id="reportCategory"></b-form-select>
                    </div>
                </div>

                <label for="reportPost" class="col-md-2 col-form-label" hidden><strong>신고 게시물 Id:</strong></label>
                <div class="col-md-4" hidden>
                    <input
                            type="text"
                            id="reportPost"
                            v-model="postId"
                            class="form-control report-text-input"
                            readonly
                    >
                </div>
            </div>
        </div>
        <div class="report-input mt-3">
            <div class="form-group row align-items-center">
                <label for="description" class="col-md-2 col-form-label"><strong>신고 내용:</strong></label>
                <div class="col-md-10">
          <textarea
                  id="description"
                  cols="40"
                  rows="10"
                  v-model="description"
                  class="form-control report-text-input"
                  style="resize: none;"
                  placeholder="내용을 입력해주세요."
                  required
          ></textarea>
                </div>
            </div>
        </div>
        <div class="report-buttons mt-3 d-flex justify-content-end">
            <button type="button" class="btn btn-primary btn-rounded" @click="fnSave">보내기</button>
        </div>
    </div>
</template>


<script>
export default {
    name: "ReportWrite",
    data() { //변수생성
        return {
            title: '',
            description: '',
            postId: this.$route.query.reportPost,
            idx: '',
            postTitle: this.$route.query.postTitle,
            reportCategory: 'ABUSE',
            options: [
                {value: 'ABUSE', text: '욕설'},
                {value: 'DEFAMATION', text: '명예훼손'},
                {value: 'PORNOGRAPHY', text: '음란물'},
                {value: 'ADVERTISEMENT', text: '광고'},
                {value: 'SPAMMER', text: '도배'},
            ]
        }
    },
    methods: {
        fnSave() {
            if (!this.title) { // 제목 또는 내용이 입력되지 않은 경우
                alert("제목을 입력해주세요.");
                this.$refs.titleInput.focus();
                return;
            }
            if (!this.description) {
                alert("내용을 입력해주세요.");
                // 내용이 비어있을 때 해당 textarea에 포커스 설정
                this.$nextTick(() => {
                    const textarea = document.getElementById('description');
                    textarea.focus();
                });
                return;
            }

            this.form = {
                "title": this.title,
                "description": this.description,
                "postId": this.postId,
                "reportCategory": this.reportCategory,
            }

            this.$axios.post(`/api/v1/report/${this.form.postId}`, this.form, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('user_token')}`
                }
            }).then((res) => {
                    alert(res.data.message)
                    this.fnBoardList()
                }).catch((err) => {
                alert(err.response.data.message)
                this.fnView(this.postId)
            })
        },
        fnBoardList() {
            this.$router.push({
                path: '/board/list',
            })
        },
        fnView(postId) {
            this.idx = postId
            this.$router.push({
                path: '/board/detail',
                query: {
                    idx: this.idx
                }
            })
        },
    }
}

</script>