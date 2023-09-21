<template>
    <div>
        <div class="container my-5">
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <h4 class="text-center mb-4"><strong>회원가입</strong></h4>
                    <hr/>
                    <b-form @submit.prevent="onSubmit">
                        <div class="form-group row mb-3">
                            <label for="input-1" class="col-md-4 col-form-label text-md-right">이메일</label>
                            <div class="col-md-8">
                                <b-form-input v-model="form.email" type="email" id="input-1" placeholder="이메일을 입력해주세요"
                                              ref="emailInput" required></b-form-input>
                            </div>
                        </div>

                        <div class="form-group row mb-3">
                            <label for="input-2" class="col-md-4 col-form-label text-md-right">이름</label>
                            <div class="col-md-8">
                                <b-form-input v-model="form.name" type="text" id="input-2" placeholder="이름을 입력해주세요"
                                              ref="nameInput" required></b-form-input>
                            </div>
                        </div>

                        <div class="form-group row mb-3">
                            <label for="input-3" class="col-md-4 col-form-label text-md-right">비밀번호</label>
                            <div class="col-md-8">
                                <b-form-input v-model="form.password" type="password" id="input-3"
                                              ref="passwordInput" placeholder="비밀번호를 입력해주세요" required></b-form-input>
                            </div>
                        </div>

                        <div class="form-group row mb-3">
                            <label for="input-4" class="col-md-4 col-form-label text-md-right">닉네임</label>
                            <div class="col-md-8">
                                <b-form-input v-model="form.nickname" type="text" id="input-4" placeholder="닉네임을 입력해주세요"
                                              ref="nicknameInput" required></b-form-input>
                            </div>
                        </div>

                        <div class="form-group row mb-3">
                            <label for="input-5" class="col-md-4 col-form-label text-md-right">전화번호</label>
                            <div class="col-md-8">
                                <b-form-input v-model="form.telephone" type="tel" id="input-5"
                                              ref="telephoneInput" placeholder="전화번호를 입력해주세요" required></b-form-input>
                            </div>
                        </div>

                        <div class="form-group row mb-3">
                            <label for="input-6" class="col-md-4 col-form-label text-md-right">주소</label>
                            <div class="col-md-8">
                                <b-form-input v-model="form.address" type="text" id="input-6" placeholder="주소를 입력해주세요"
                                              ref="addressInput" required></b-form-input>
                            </div>
                        </div>

                        <div class="form-group row mb-3">
                            <label for="input-7" class="col-md-4 col-form-label text-md-right">생년 월일</label>
                            <div class="col-md-8">
                                <b-form-input v-model="form.birthDate" type="date" id="input-7"
                                              ref="birthInput" placeholder="생년 월일을 입력해주세요" required></b-form-input>
                            </div>
                        </div>

                        <div class="form-group row mb-4">
                            <label for="input-8" class="col-md-4 col-form-label text-md-right">성별</label>
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
                            <label>보유 기술</label>
                            <b-form-select v-model="form.skillName" :options="form.options" multiple
                                           :select-size="4"></b-form-select>
                            <div class="mt-3">Selected: <strong>{{ form.skillName }}</strong></div>
                        </div>

                        <hr>

                        <div class="text-center">
                            <b-button type="button" @click="onSubmit" variant="primary" style="margin-right: 10px;">
                                가입하기
                            </b-button>
                            <b-button type="reset" variant="danger">초기화</b-button>
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
                name: '',
                nickname: '',
                telephone: '',
                address: '',
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
    methods: {
        options,
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
            if (!this.form.address) {
                alert("주소를 입력해주세요.");
                this.$refs.addressInput.focus();
                return;
            }
            if (!this.form.birthDate) {
                alert("생년월일을 선택해주세요.");
                this.$refs.birthInput.focus();
                return;
            }

            this.$axios.post("/api/v1/member", this.form)
                .then((res) => {
                    alert(res.data.message)
                    this.$router.push({name: 'Login'})
                }).catch((err) => {
                    alert(err.response.data.message);
                    return;
            })
        }
    }
}
</script>

<style scoped>

</style>
