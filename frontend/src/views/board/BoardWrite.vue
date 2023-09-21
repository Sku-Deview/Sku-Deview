
<template>
    <div class="board-detail mt-5">
        <h4 class="text-center mb-4"><strong>게시판</strong></h4>
        <div class="row">
            <div class="col-sm-2 d-flex flex-column align-items-center">
                <label for="postCategory" class="mt-3"><strong>카테고리</strong></label>
            </div>
            <div class="col">
                <b-form-select v-model="postCategory" :options="options" class="mt-3" id="postCategory"></b-form-select>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2 d-flex flex-column align-items-center">
                <label for="postTitle" class="mt-3"><strong>제목</strong></label>
            </div>
            <div class="col">
                <input type="text" v-model="title" class="w3-input w3-border mt-3" placeholder="제목을 입력해주세요." ref="titleInput">
            </div>
        </div>


        <div class="mt-3 mb-3">
            <ck-editor v-model="content" :editor="editor" :config="editorConfig"/>
        </div>

        <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-primary btn-rounded" v-on:click="fnSave">저장</button>&nbsp;
            <button type="button" class="btn btn-success btn-rounded" v-on:click="fnList">목록</button>
        </div>
    </div>
</template>

<script>
import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import UploadAdapter from './UploadAdapter';


export default {
    components: {'ck-editor': CKEditor.component},

    data() { //변수생성
        return {

            editor: ClassicEditor,
            editorConfig: {
                toolbar: ['heading', '|', 'fontBackgroundColor', 'fontColor', 'fontSize', 'bold', 'italic', '|', 'alignment', 'bulletedList', 'numberedList', 'indent', 'outdent', '|', 'imageUpload', 'insertTable', 'link', '|', 'undo', 'redo'],
                table: {
                    contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties'],
                },
                image: {
                    resize: true,
                    toolbar: ['imageStyle:alignLeft', 'imageStyle:alignRight', 'imageStyle:inline', 'imageStyle:side'],
                    // upload: {
                    //   types: ['jpeg', 'jpg', 'png', 'gif'],
                    //   //adapter: UploadAdapter, // 위에서 작성한 UploadAdapter 클래스를 연결
                    // },
                },
                extraPlugins: [function (editor) {
                    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
                        return new UploadAdapter(loader);
                    }
                }]
            },

            requestBody: this.$route.query,
            idx: this.$route.query.idx,

            title: '',
            content: '',
            created_at: '',
            postCategory: 'FREE',
            options: [
                {value: 'NOTICE', text: '공지사항'},
                {value: 'QNA', text: '질문'},
                {value: 'FREE', text: '자유'},
                {value: 'INFO', text: '정보'},
            ]
        }
    },
    mounted() {
        this.fnGetView()
    },
    methods: {
        fnGetView() {
            if (this.idx !== undefined) {
                ``
                this.$axios.get('/api/v1/post/' + this.idx, {
                    params: this.requestBody

                }).then((res) => {
                    console.log(res.data.data)
                    this.title = res.data.data.title
                    this.author = res.data.data.memberNickname
                    this.content = res.data.data.content
                    this.created_at = res.data.data.regDate
                }).catch((err) => {
                    alert(err.response.data.message)
                    console.log(err)
                })
            }
        },
        fnList() {
            delete this.requestBody.idx
            this.$router.push({
                path: './list',
                query: this.requestBody
            })
        },
        fnView(idx) {
            this.requestBody.idx = idx
            this.$router.push({
                path: './detail',
                query: this.requestBody
            })
        },
        fnSave() {
            if (!this.title) { // 제목 또는 내용이 입력되지 않은 경우
                alert("제목을 입력해주세요.");
                this.$refs.titleInput.focus();
                return;
            }
            if (!this.content) {
                alert("내용을 입력해주세요.");
                return;
            }

            // let apiUrl = '/api/v1/post'
            this.form = {
                "title": this.title,
                "content": this.content,
                "postCategory": this.postCategory,
            }

            if (this.idx === undefined) {
                //INSERT
                this.$axios.post("/api/v1/post", this.form, {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('user_token')}`
                    }
                })
                    .then((res) => {
                        alert(res.data.message)
                        this.fnView(res.data.data)
                    }).catch((err) => {
                    alert(err.response.data.message)
                    location.reload()
                })
            } else {
                //UPDATE
                this.$axios.patch("/api/v1/post/" + this.idx, this.form, {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('user_token')}`
                    }
                })
                    .then((res) => {
                        alert(res.data.message)
                        this.fnView(res.data.data)
                    }).catch((err) => {
                    alert(err.response.data.message)
                    location.reload()
                })
            }
        }
    }
}
</script>
<style scoped>

</style>