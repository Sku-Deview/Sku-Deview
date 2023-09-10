<!--<template>-->
<!--    <div>-->
<!--        <b-container>-->
<!--            <h4>회원가입</h4>-->
<!--            <b-form>-->
<!--                <b-form-group label="USER Email:" label-for="input-1">-->
<!--                    <b-form-input-->
<!--                        v-model="form.email"-->
<!--                        type="email"-->
<!--                        placeholder="이메일을 입력해주세요"-->
<!--                        required-->
<!--                    ></b-form-input>-->
<!--                </b-form-group>-->

<!--                <b-form-group id="input-group-3" label="USER PW:" label-for="input-3">-->
<!--                    <b-form-input-->
<!--                        v-model="form.password"-->
<!--                        type="password"-->
<!--                        placeholder="비밀번호를 입력해주세요"-->
<!--                        required-->
<!--                    ></b-form-input>-->
<!--                </b-form-group>-->

<!--                <b-form-group id="input-group-2" label="USER NAME:" label-for="input-2">-->
<!--                    <b-form-input-->
<!--                        type="text"-->
<!--                        v-model="form.name"-->
<!--                        placeholder="이름을 입력해주세요"-->
<!--                        required-->
<!--                    ></b-form-input>-->
<!--                </b-form-group>-->

<!--                <b-form-group id="input-group-3" label="USER Nickname:" label-for="input-3">-->
<!--                    <b-form-input-->
<!--                        v-model="form.nickname"-->
<!--                        type="text"-->
<!--                        placeholder="닉네임을 입력해주세요"-->
<!--                        required-->
<!--                    ></b-form-input>-->
<!--                </b-form-group>-->

<!--                <b-form-group id="input-group-3" label="USER telephone:" label-for="input-3">-->
<!--                    <b-form-input-->
<!--                        v-model="form.telephone"-->
<!--                        type="tel"-->
<!--                        placeholder="전화번호를 입력해주세요"-->
<!--                        required-->
<!--                    ></b-form-input>-->
<!--                </b-form-group>-->

<!--                <b-form-group id="input-group-3" label="USER address:" label-for="input-3">-->
<!--                    <b-form-input-->
<!--                        v-model="form.address"-->
<!--                        type="text"-->
<!--                        placeholder="주소를 입력해주세요"-->
<!--                        required-->
<!--                    ></b-form-input>-->
<!--                </b-form-group>-->

<!--                <b-form-group id="input-group-3" label="USER birth-date:" label-for="input-3">-->
<!--                    <b-form-input-->
<!--                        v-model="form.birthDate"-->
<!--                        type="date"-->
<!--                        placeholder="생년 월일을 입력해주세요"-->
<!--                        required-->
<!--                    ></b-form-input>-->
<!--                </b-form-group>-->

<!--                <hr>-->
<!--                <b-form-group id="input-group-3" label="USER gender:" label-for="input-3">-->
<!--                    <b-form-radio v-model="form.gender" :aria-describedby="ariaDescribedby" name="some-radios"-->
<!--                                  value="MALE">MALE-->
<!--                    </b-form-radio>-->
<!--                    <b-form-radio v-model="form.gender" :aria-describedby="ariaDescribedby" name="some-radios"-->
<!--                                  value="FEMALE">FEMALE-->
<!--                    </b-form-radio>-->
<!--                </b-form-group>-->
<!--                <hr>-->

<!--                <div>-->
<!--                    <label>Skills</label>-->
<!--                    <b-form-select v-model="form.skillName" :options="form.options" multiple-->
<!--                                   :select-size="4"></b-form-select>-->
<!--                    <div class="mt-3">Selected: <strong>{{ form.skillName }}</strong></div>-->
<!--                </div>-->


<!--                <b-button @click="onSubmit" variant="primary">Submit</b-button>-->
<!--                <b-button type="reset" variant="danger">Reset</b-button>-->
<!--            </b-form>-->
<!--        </b-container>-->
<!--    </div>-->
<!--</template>-->

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
                        <b-form-input v-model="form.email" type="email" id="input-1" placeholder="이메일을 입력해주세요" required></b-form-input>
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label for="input-2" class="col-md-4 col-form-label text-md-right">이름</label>
                    <div class="col-md-8">
                        <b-form-input v-model="form.name" type="text" id="input-2" placeholder="이름을 입력해주세요" required></b-form-input>
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label for="input-3" class="col-md-4 col-form-label text-md-right">비밀번호</label>
                    <div class="col-md-8">
                        <b-form-input v-model="form.password" type="password" id="input-3" placeholder="비밀번호를 입력해주세요" required></b-form-input>
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label for="input-4" class="col-md-4 col-form-label text-md-right">닉네임</label>
                    <div class="col-md-8">
                        <b-form-input v-model="form.nickname" type="text" id="input-4" placeholder="닉네임을 입력해주세요"
                                      required></b-form-input>
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label for="input-5" class="col-md-4 col-form-label text-md-right">전화번호</label>
                    <div class="col-md-8">
                        <b-form-input v-model="form.telephone" type="tel" id="input-5" placeholder="전화번호를 입력해주세요" required></b-form-input>
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label for="input-6" class="col-md-4 col-form-label text-md-right">주소</label>
                    <div class="col-md-8">
                        <b-form-input v-model="form.address" type="text" id="input-6" placeholder="주소를 입력해주세요" required></b-form-input>
                    </div>
                </div>

                <div class="form-group row mb-3">
                    <label for="input-7" class="col-md-4 col-form-label text-md-right">생년 월일</label>
                    <div class="col-md-8">
                        <b-form-input v-model="form.birthDate" type="date" id="input-7" placeholder="생년 월일을 입력해주세요" required></b-form-input>
                    </div>
                </div>

                <div class="form-group row mb-4">
                    <label for="input-8" class="col-md-4 col-form-label text-md-right">성별</label>
                    <div class="col-md-8 d-flex align-items-center">
                        <div class="mr-3">
                            <b-form-radio v-model="form.gender" name="userGender" value="MALE">남성</b-form-radio>
                        </div>
                        <div>
                            <b-form-radio v-model="form.gender" name="userGender" value="FEMALE">여성</b-form-radio>
                        </div>
                    </div>
                </div>

                <hr>

                <div class="form-group mb-4">
                    <label>보유 기술</label>
                    <b-form-select v-model="form.skillName" :options="form.options" multiple :select-size="4"></b-form-select>
                    <div class="mt-3">Selected: <strong>{{ form.skillName }}</strong></div>
                </div>

                <hr>

                <div class="text-center">
                    <b-button type="submit" variant="primary" style="margin-right: 10px;">가입하기</b-button>
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
                gender: '',
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
        async onSubmit() {
            try {
                await axios.post('/api/v1/member', this.form);
                await this.$router.push({name: 'Login'})
            } catch (err) {
                throw new Error(err);
            }
        }
    }
}
</script>

<style scoped>

</style>
