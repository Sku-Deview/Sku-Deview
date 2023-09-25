<template>
  <div class="black-bg" v-if="modalOpen === true">
    <div class="white-bg">
      <b-button @click="modalOpen = false" class="modal-exit-btn ">
        닫기
      </b-button>
      <h1>{{updateMemberId}}번 회원 상세</h1>

      <div class="info-row">
        <div class="info">
          <b-form>
            <div class="form-group row mb-3">
              <label for="input-1" class="col-md-4 col-form-label text-md-right">이메일</label>
              <div class="col-md-8">
                <b-form-input readonly v-model="MemberRequestDto.email" type="email" id="input-1"
                              required>{{ MemberRequestDto.email }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-2" class="col-md-4 col-form-label text-md-right">이름</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.name" type="text" id="input-2"
                              required>{{ MemberRequestDto.name }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-3" class="col-md-4 col-form-label text-md-right">비밀번호</label>
              <div class="col-md-8">
                <b-form-input readonly v-model="MemberRequestDto.password" type="text" id="input-3"
                              required>{{ MemberRequestDto.password }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-4" class="col-md-4 col-form-label text-md-right">닉네임</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.nickname" type="text" id="input-4"
                              required>{{ MemberRequestDto.nickname }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-5" class="col-md-4 col-form-label text-md-right">전화번호</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.telephone" type="tel" id="input-5"
                              required>{{ MemberRequestDto.telephone }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-6" class="col-md-4 col-form-label text-md-right">주소</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.address" type="text" id="input-6"
                              required>{{ MemberRequestDto.address }}
                </b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-6" class="col-md-4 col-form-label text-md-right">현재 권한 :
                {{ MemberRequestDto.role }}</label>
              <div class="col-md-8">
                <select v-model="MemberRequestDto.role">
                  <option disabled value="">권한을 선택해 주세요</option>
                  <option>ROLE_USER</option>
                  <option>ROLE_MANAGER</option>
                  <option>ROLE_ADMIN</option>
                </select>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-7" class="col-md-4 col-form-label text-md-right">현재 생년 월일 :
                {{ MemberRequestDto.birthDate }}</label>
              <div class="col-md-8">
                <b-form-input v-model="MemberRequestDto.birthDate" type="date" id="input-7"
                              required></b-form-input>
              </div>
            </div>

            <div class="form-group mb-4">
              <div class="mt-3">현재 보유 기술: <strong>{{ MemberRequestDto.skillName }}</strong></div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-8" class="col-md-4 col-form-label text-md-right">현재 성별 :
                {{ MemberRequestDto.gender }}</label>
              <div class="col-md-8 d-flex align-items-center">
                <div class="mr-3">
                  <b-form-radio v-model="MemberRequestDto.gender" name="userGender" value="MALE">남성</b-form-radio>
                </div>
                <div>
                  <b-form-radio v-model="MemberRequestDto.gender" name="userGender" value="FEMALE">여성
                  </b-form-radio>
                </div>
              </div>
            </div>

            <hr>

            <div class="text-center">
              <b-button @click="[modalOpen = false,updateMember()]"
                        class="modal-exit-btn button-button">
                변경
              </b-button>
            </div>
          </b-form>
        </div>
      </div>
    </div>
  </div>
  <b-button v-on:click="toReportList">신고 리스트</b-button>
  <b-button v-on:click="toSkillList">스킬 리스트</b-button>
      <div class="board-list mt-5">
        <table class="table table-striped">
          <colgroup>
            <col style="width: 5%;"/> <!-- No 열의 너비 -->
            <col style="width: auto;"/> <!-- 카테고리 열의 너비 -->
            <col style="width: 15%;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
            <col style="width: 20%;"/> <!-- 게시글 열의 너비 -->
            <col style="width: 20%;"/> <!-- 댓글 열의 너비 -->
          </colgroup>
          <thead>
          <tr>
            <th scope="col">MemberId</th>
            <th scope="col">Nickname</th>
            <th scope="col">Role</th>
            <th scope="col">Post</th>
            <th scope="col">Reply</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, idx) in list" :key="idx" class="hover-pointer">
            <td>{{ item.memberId }}</td>
            <td @click="modalOpen = true">
              <b-button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="clickModel(item)">
                {{ item.nickname }}
              </b-button>
            </td>
            <td>{{ item.role }}</td>
            <td>
              <b-button v-on:click="toMemberPost(item.memberId)">작성 게시물 보기</b-button>
            </td>
            <td>
              <b-button v-on:click="toMemberReply(item.memberId)">작성 댓글 보기</b-button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
</template>


<script>
export default {
  data() { //변수생성
    return {
      requestBody:{},
      modalOpen: false,
      updateMemberId: '',
      MemberRequestDto: {
        password: '',
        name: '',
        nickname: '',
        telephone: '',
        address: '',
        birthDate: '',
        gender: '',
        role: '',
        skillName: [],
        email:''
      },
      list: {}, //리스트 데이터

    }
  },
  mounted() {
    this.fnGetList()
  },
  methods: {
    fnGetList() {
      this.$axios.get("/api/v1/admin/member", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {

        this.list = res.data.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    clickModel(item) {
      this.MemberRequestDto.email = item.email
      this.MemberRequestDto.role = item.role
      this.MemberRequestDto.password = item.password
      this.MemberRequestDto.name = item.name
      this.MemberRequestDto.nickname = item.nickname
      this.MemberRequestDto.address = item.address
      this.MemberRequestDto.birthDate = item.birthDate
      this.MemberRequestDto.gender = item.gender
      this.MemberRequestDto.skillName = item.skillName
      this.MemberRequestDto.telephone = item.telephone
      this.updateMemberId = item.memberId
    },
    updateMember(){
      this.$axios.put("/api/v1/admin/member/" + this.updateMemberId, this.MemberRequestDto, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then(() => {
            alert('멤버 수정이 성공적으로 완료되었어요! ')
            this.SendList()
          }).catch((err) => {
            console.log(err)
        alert(err.response.data.message)
      })
    },
    SendList() {
      this.$router.push({
        path: '/admin/main',
      })
      location.reload()
    },
    toMemberPost(memberId) {
      this.requestBody.idx = memberId
      this.$router.push({
        path: '/admin/member/post',
        query: this.requestBody
      })
    },
    toMemberReply(memberId) {
      this.requestBody.idx = memberId
      this.$router.push({
        path: '/admin/member/reply',
        query: this.requestBody
      })
    },
    toReportList() {
      this.$router.push({
        path: '/admin/report/list',

      })
    },
    toSkillList() {
      this.$router.push({
        path: '/admin/skill/list',

      })
    },
  }
}
</script>

<style>

.black-bg {
  display: flex;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.432);
  position: fixed;
  padding: 5px;
}

.white-bg {
  width: 60%;
  height: 90%;
  background-color: white;
  border-color:black;
  border-width : 2px ;
  border-radius: 8px;
  border-style : solid ;
  position: absolute;
  top: 40%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: left; /* 왼쪽 정렬로 변경 */
}

.button-container {
  text-align: right; /* 버튼을 오른쪽 정렬 */
}

.modal-exit-btn {
  margin-top: 0; /* 마진 값 제거 */
  margin-right: 0; /* 마진 값 제거 */
  float: right; /* 버튼을 오른쪽으로 띄움 */
}

.button-button {
  position: absolute;
  bottom: 20px; /* 하단과의 간격 조절 */
  right: 20px; /* 오른쪽과의 간격 조절 */
}

.modal-exit-btn:hover {
  cursor: pointer;
}
</style>