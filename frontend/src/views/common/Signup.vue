<template>
  <div>
    <b-container>
      <h4>회원가입</h4>
      <b-form>
        <b-form-group label="USER Email:" label-for="input-1">
          <b-form-input
              v-model="form.email"
              type="email"
              placeholder="이메일을 입력해주세요"
              required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="USER PW:" label-for="input-3">
          <b-form-input
              v-model="form.password"
              type="password"
              placeholder="비밀번호를 입력해주세요"
              required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-2" label="USER NAME:" label-for="input-2">
          <b-form-input
              type="text"
              v-model="form.name"
              placeholder="이름을 입력해주세요"
              required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="USER Nickname:" label-for="input-3">
          <b-form-input
              v-model="form.nickname"
              type="text"
              placeholder="닉네임을 입력해주세요"
              required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="USER telephone:" label-for="input-3">
          <b-form-input
              v-model="form.telephone"
              type="tel"
              placeholder="전화번호를 입력해주세요"
              required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="USER address:" label-for="input-3">
          <b-form-input
              v-model="form.address"
              type="text"
              placeholder="주소를 입력해주세요"
              required
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-3" label="USER birth-date:" label-for="input-3">
          <b-form-input
              v-model="form.birthDate"
              type="date"
              placeholder="생년 월일을 입력해주세요"
              required
          ></b-form-input>
        </b-form-group>

        <hr>
        <b-form-group id="input-group-3" label="USER gender:" label-for="input-3">
          <b-form-radio v-model="form.gender" :aria-describedby="ariaDescribedby" name="some-radios" value="MALE">MALE</b-form-radio>
          <b-form-radio v-model="form.gender" :aria-describedby="ariaDescribedby" name="some-radios" value="FEMALE">FEMALE</b-form-radio>
        </b-form-group>
        <hr>

        <div>
          <label>Skills</label>
          <b-form-select v-model="form.skillName" :options="form.options" multiple :select-size="4"></b-form-select>
          <div class="mt-3">Selected: <strong>{{ form.skillName }}</strong></div>
        </div>


        <b-button @click="onSubmit" variant="primary">Submit</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </b-form>
    </b-container>
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
        gender:'',
        role:'USER',
        skillName: ['null'],
        options: [
          { value: 'null', text: 'Default Selected null' },
          { value: 'Java', text: 'Java' },
          { value: 'Python', text: 'Python' },
          { value: 'JavaScript', text: 'JavaScript' },
          { value: 'SpringBoot', text: 'SpringBoot' },
          { value: 'React', text: 'React' },
          { value: 'C++', text: 'C++' },
          { value: 'C#', text: 'C#' },
          { value: 'Flutter', text: 'Flutter' },
          { value: 'Spring', text: 'Spring' },
          { value: 'Node.js', text: 'Node.js' }
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