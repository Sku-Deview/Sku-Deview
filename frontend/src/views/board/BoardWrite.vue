<!--<template>-->
<!--    <div class="board-detail mt-5">-->
<!--        <div>-->
<!--            <label>Category</label>-->
<!--            <b-form-select v-model="postCategory" :options="options" size="sm" class="mt-3"></b-form-select>-->
<!--            <hr>-->
<!--        </div>-->
<!--        <div class="board-contents">-->
<!--            <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">-->
<!--        </div>-->
<!--        -->
<!--        -->
<!--        <div class=" ck-editor__editable ">-->
<!--            <ck-editor  v-model="content" :editor="editor" :config="editorConfig"/>-->
<!--        </div>-->
<!--        <div class="board-contents post-button">-->
<!--            <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;-->
<!--            <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>-->
<!--        </div>-->
<!--    </div>-->
<!--</template>-->

<template>
  <div class="board-detail mt-5">
    <h4 class="text-center mb-4"><strong>게시판</strong></h4>
    <div class="row">
      <div class="col-sm-2 d-flex flex-column align-items-center">
        <label for="postCategory" class="mt-3"><strong>카테고리</strong></label>
      </div>
      <div class="col">
        <b-form-select v-model="postCategory" :options="options" size="sm" class="mt-3"
                       id="postCategory"></b-form-select>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-2 d-flex flex-column align-items-center">
        <label for="postTitle" class="mt-3"><strong>제목</strong></label>
      </div>
      <div class="col">
        <input type="text" v-model="title" class="w3-input w3-border mt-3" placeholder="제목을 입력해주세요.">
      </div>
    </div>


    <div class=" ck-editor__editable mt-3">
      <ck-editor v-model="content" :editor="editor" :config="editorConfig"/>
    </div>

    <div class="post-button">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
      <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
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
              this.fnView(res.data.data)
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
              this.fnView(res.data.data)
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