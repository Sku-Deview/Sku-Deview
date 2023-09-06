<template>
    <div class="board-detail">
        <div>
            <label>Category</label>
            <b-form-select v-model="postCategory" :options="options" size="sm" class="mt-3"></b-form-select>
            <hr>

        </div>
        <div class="board-contents">
            <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">
        </div>
        <div class=" ck-editor__editable ">
            <ck-editor  v-model="content" :editor="editor" :config="editorConfig"/>
        </div>
        <div class="board-contents post-button">
            <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
            <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
        </div>
    </div>
</template>

<script>
import CKEditor from '@ckeditor/ckeditor5-vue';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

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
                    toolbar: ['imageStyle:alignLeft', 'imageStyle:alignRight', 'imageStyle:inline', 'imageStyle:side']
                },
            },

            requestBody: this.$route.query,
            idx: this.$route.query.idx,

            title: '',
            content: '',
            created_at: '',
            postCategory: '',
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
                this.$axios.get('/api/v1/post/' + this.idx, {
                    params: this.requestBody

                }).then((res) => {
                    this.title = res.data.title
                    this.author = res.data.memberNickname
                    this.content = res.data.content
                    this.created_at = res.data.regDate
                }).catch((err) => {
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
                        alert('글이 저장되었습니다.')
                        this.fnView(res.data)
                    }).catch((err) => {
                    if (err.message.indexOf('Network Error') > -1) {
                        alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                    }
                })
            } else {
                //UPDATE
                this.$axios.patch("/api/v1/post/" + this.idx, this.form, {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('user_token')}`
                    }
                })
                    .then((res) => {
                        alert('글이 저장되었습니다.')
                        this.fnView(res.data)
                    }).catch((err) => {
                    if (err.message.indexOf('Network Error') > -1) {
                        alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
                    }
                })
            }
        }
    }
}
</script>
<style scoped>

</style>