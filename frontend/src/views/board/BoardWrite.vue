<template>
  <div class="board-detail mt-5">
    <h4 class="text-center mb-4"><strong>게시판</strong></h4>
    <div class="row">
      <div class="col-sm-2 d-flex flex-column align-items-center">
        <label for="postCategory" class="mt-3"><strong>카테고리</strong></label>
      </div>
      <div class="col">
        <b-form-select v-model="postCategory" :options="options" v-if="!isAdmin()" class="mt-3"
                       id="postCategory"></b-form-select>
        <b-form-select v-model="postCategory" :options="adminOptions" v-if="isAdmin()" class="mt-3"
                       id="postCategory"></b-form-select>
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

    <input type="file" ref="fileInput" @change="handleFileChange" multiple/>

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

  data() {
    return {
      postRequestDto: {
        title: "",
        content: "",
        postCategory: "",
        files: [], // 파일 업로드 관련 필드 추가
      },
      editor: ClassicEditor,
      editorConfig: {
        toolbar: ['heading', '|', 'fontBackgroundColor', 'fontColor', 'fontSize', 'bold', 'italic', '|', 'alignment', 'bulletedList', 'numberedList', 'indent', 'outdent', '|',  'insertTable', 'link', '|', 'undo', 'redo'],
        table: {
          contentToolbar: ['tableColumn', 'tableRow', 'mergeTableCells', 'tableProperties', 'tableCellProperties'],
        },
        // image: {
        //   resize: true,
        //   toolbar: ['imageStyle:alignLeft', 'imageStyle:alignRight', 'imageStyle:inline', 'imageStyle:side'],
        // },
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
        {value: 'FREE', text: '자유'},
        {value: 'QNA', text: '질문'},
        {value: 'INFO', text: '정보'},
      ],
      adminOptions: [
        {value: 'NOTICE', text: '공지사항'},
        {value: 'FREE', text: '자유'},
        {value: 'QNA', text: '질문'},
        {value: 'INFO', text: '정보'},
      ]
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    handleFileChange(event) {
      this.postRequestDto.files = Array.from(event.target.files); // 선택한 모든 파일을 배열로 저장
    },
    fnGetView() {
      if (this.idx !== undefined) {
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
      this.postRequestDto.title = this.title
      this.postRequestDto.content = this.content
      this.postRequestDto.postCategory = this.postCategory
      if (!this.postRequestDto.title) {
        alert("제목을 입력해주세요.");
        return;
      }
      if (!this.postRequestDto.content) {
        alert("내용을 입력해주세요.");
        return;
      }

      const formData = new FormData();

      // DTO를 JSON 문자열로 변환하여 FormData에 추가
      const json = JSON.stringify(this.postRequestDto);
      const blob = new Blob([json], { type: "application/json" });
      formData.append("createParams", blob);

      // 파일 업로드 처리
      for (const file of this.postRequestDto.files) {
        console.log(file)
        formData.append("files", file); // 각 파일을 FormData에 추가
      }

      if (!this.idx) {
        // 새 게시글 작성
        this.$axios.post("/api/v1/post", formData, {
          headers: {
            'Content-Type': 'multipart/form-data', // 헤더 설정
            Authorization: `Bearer ${localStorage.getItem("user_token")}`,
          },
        })
            .then((response) => {
              alert("게시글 업로드 성공");
              console.log(response.data.data)
              this.fnView(response.data.data)

            })
            .catch((err) => {
              console.log(err)
              alert("게시글 업로드 실패");
              location.reload()
            });
      } else {
        // 게시글 업데이트
        this.$axios
            .patch("/api/v1/post/" + this.idx, this.postRequestDto, {
              headers: {
                Authorization: `Bearer ${localStorage.getItem("user_token")}`,
              },
            })
            .then((res) => {
              alert(res.data.message);
              this.fnView(res.data.data);
            })
            .catch((err) => {
              alert(err.response.data.message);
              location.reload();
            });
      }
    },
    isAdmin() {
      if (localStorage.getItem("user_role") === "ROLE_ADMIN") {
        return true;
      } else return false;
    },
  }
}
</script>
<style scoped>

</style>