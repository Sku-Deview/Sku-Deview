<template>
  <div>
    <div class="container my-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <h4 class="text-center mb-4"><strong>회원수정</strong></h4>
          <hr/>
          <b-form @submit.prevent="onSubmit">

            <div class="form-group row mb-3">
              <label for="input-1" class="col-md-4 col-form-label text-md-right"><strong>이메일</strong></label>
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input v-model="form.email" type="email" id="input-1" placeholder="이메일을 입력해주세요" ref="emailInput" disabled></b-form-input>

                  <b-form-input v-model="form.email" type="email" id="input-1" placeholder="이메일을 입력해주세요" ref="emailInput" hidden></b-form-input>

                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-2" class="col-md-4 col-form-label text-md-right"><strong>이름</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.name" type="text" id="input-2" placeholder="이름을 입력해주세요"
                              ref="nameInput" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-3" class="col-md-4 col-form-label text-md-right"><strong>비밀번호</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.password" type="password" id="input-3"
                              ref="passwordInput" placeholder="비밀번호를 입력해주세요" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-3-1" class="col-md-4 col-form-label text-md-right"><strong>비밀번호 확인</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.confirmPassword" type="password" id="input-3-1"
                              ref="confirmPasswordInput" placeholder="비밀번호 확인을 입력해주세요" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-4" class="col-md-4 col-form-label text-md-right"><strong>닉네임</strong></label>
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input v-model="form.nickname" type="text" id="input-4" placeholder="닉네임을 입력해주세요" ref="nicknameInput" disabled></b-form-input>

                  <b-form-input v-model="form.nickname" type="text" id="input-4" placeholder="닉네임을 입력해주세요" ref="nicknameInput" hidden></b-form-input>

                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-5" class="col-md-4 col-form-label text-md-right"><strong>전화번호</strong></label>
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input v-model="form.telephone" type="tel" id="input-5" ref="telephoneInput" placeholder="전화번호를 입력해주세요" disabled></b-form-input>

                  <b-form-input v-model="form.telephone" type="tel" id="input-5" ref="telephoneInput" placeholder="전화번호를 입력해주세요" hidden></b-form-input>

                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-6" class="col-md-4 col-form-label text-md-right"><strong>주소</strong></label>
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input v-model="form.zonecode" type="text" id="input-6-1" placeholder="우편번호" ref="addressInput" readonly></b-form-input>
                  <div class="input-group-append">
                    <b-button id="postcode" @click="openPostcode">검색</b-button>
                  </div>
                </div>
                <div class="row mt-2">
                  <div class="col-md-12">
                    <b-form-input v-model="form.roadAddress" type="text" id="input-6-2" placeholder="주소" readonly></b-form-input>
                  </div>
                </div>
                <div class="row mt-2">
                  <div class="col-md-12">
                    <b-form-input v-model="form.detailAddress" type="text" id="input-6-3" placeholder="상세주소"></b-form-input>
                  </div>
                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-7" class="col-md-4 col-form-label text-md-right"><strong>생년 월일</strong></label>
              <div class="col-md-8">
                <b-form-input v-model="form.birthDate" type="date" id="input-7"
                              ref="birthInput" placeholder="생년 월일을 입력해주세요" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-4">
              <label for="input-8" class="col-md-4 col-form-label text-md-right"><strong>성별</strong></label>
              <div class="col-md-8 d-flex align-items-center">
                <div class="mr-3">
                  <b-form-radio v-model="form.gender" name="userGender" value="MALE">남성</b-form-radio>
                </div>
                <div>
                  <b-form-radio v-model="form.gender" name="userGender" value="FEMALE">여성
                  </b-form-radio>
                </div>
              </div>
            </div>

            <hr>

            <div class="form-group mb-4">
              <label><strong>보유 기술</strong></label>
              <b-form-select v-model="form.skillName" :options="form.options" multiple
                             :select-size="4"></b-form-select>
              <div class="mt-3">Selected: <strong>{{ form.skillName }}</strong></div>
            </div>

            <hr>

            <div class="text-center">
              <b-button type="button" @click="onSubmit" variant="primary" style="margin-right: 10px;">
                수정
              </b-button>
              <b-button @click="fnList">취소</b-button>
            </div>

          </b-form>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import axios, {options} from 'axios';

export default {
  data() {
    return {
      form: {
        email: '',
        password: '',
        confirmPassword: '',
        name: '',
        nickname: '',
        telephone: '',
        address: '',
        zonecode: '',
        roadAddress: '',
        detailAddress: '',
        birthDate: '',
        gender: 'MALE',
        role: 'USER',
        skillName: ['null'],
        options: [
          {value: 'null', text: 'Default Selected null'},
          {value: 'Java', text: 'Java'},
          {value: 'Python', text: 'Python'},
          {value: 'JavaScript', text: 'JavaScript'},
          {value: 'SpringBoot', text: 'SpringBoot'},
          {value: 'React', text: 'React'},
          {value: 'C++', text: 'C++'},
          {value: 'C#', text: 'C#'},
          {value: 'Flutter', text: 'Flutter'},
          {value: 'Spring', text: 'Spring'},
          {value: 'Node.js', text: 'Node.js'}
        ]

      }
    }
  },
  mounted() {
    let memberNickname = localStorage.getItem('user_nickname');
    this.fnLoginMember(memberNickname);
  },
  methods: {
    options,
    openPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.form.zonecode = data.zonecode;
          this.form.roadAddress = data.roadAddress;
        },
      }).open();
    },
    fnLoginMember(memberNickname) {
      this.$axios.post(`/api/v1/member/${memberNickname}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log(res.data.data);
        // this.form = res.data.data;
        this.form.email = res.data.data.email;
        this.form.name = res.data.data.name;
        this.form.nickname = res.data.data.nickname;
        this.form.telephone = res.data.data.telephone;
        this.form.birthDate = res.data.data.birthDate;
        this.form.address = res.data.data.address;
        this.form.skillName = res.data.data.skillName;
        this.form.gender = res.data.data.gender;
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
        }
      })
    },
    onSubmit() {
      if (!this.form.email) {
        alert("이메일을 입력해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      if (!this.form.name) {
        alert("이름을 입력해주세요.");
        this.$refs.nameInput.focus();
        return;
      }
      if (!this.form.password) {
        alert("비밀번호를 입력해주세요.");
        this.$refs.passwordInput.focus();
        return;
      }
      if(!this.form.confirmPassword) {
        alert("비밀번호 확인을 입력해주세요.");
        this.$refs.confirmPasswordInput.focus();
        return;
      }
      if(this.form.password != this.form.confirmPassword) {
        alert("비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요.");
        this.$refs.confirmPasswordInput.focus();
        return;
      }
      if (!this.form.nickname) {
        alert("닉네임을 입력해주세요.");
        this.$refs.nicknameInput.focus();
        return;
      }
      if (!this.form.telephone) {
        alert("전화번호를 입력해주세요.");
        this.$refs.telephoneInput.focus();
        return;
      }
      if (!this.form.zonecode) {
        alert("주소를 입력해주세요.");
        this.$refs.addressInput.focus();
        return;
      }
      if (!this.form.birthDate) {
        alert("생년월일을 선택해주세요.");
        this.$refs.birthInput.focus();
        return;
      }

      this.form.address = (this.form.roadAddress + " " + this.form.detailAddress);

      this.$axios.put("/api/v1/member/" + this.form.email, this.form, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        console.log("-------1");
        console.log(this.form);
        console.log("-------2");
            console.log(res.data);
        console.log("-------3");

            alert('수정 완료!');
            this.$router.go(-1);
          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
        }
        this.$store.state.loadingStatus = false;
        return;
      });
    },
    fnList() {
      this.$router.go(-1);
    }
  }
}
</script>

<style scoped>

</style>
