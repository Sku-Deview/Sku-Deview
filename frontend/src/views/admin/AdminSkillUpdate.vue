<template>
  <div class="d-flex justify-content-end">
    <button type="button" class="btn btn-primary btn-rounded" v-on:click="fnList">목록</button>&nbsp;
  </div>
  <div class="info-row">
    <div class="info">
      <table class="table table-striped table-bordered text-center">
        <thead>
        <tr>
          <th>스킬 아이디</th>
          <th>스킬 이름</th>
        </tr>
        </thead>

        <tbody>
        <tr v-for="(item, idx) in list" :key="idx">
          <td>&nbsp;
            {{ item.skillId }}

          </td>
          <td>
            현재 기술 명 : {{ item.name }}
            <div>
              바꿀 기술 명 :&nbsp&nbsp<input type="text" id="input-2" required @change="onChangeInput">&nbsp&nbsp
              <button type="button" class="btn btn-success btn-rounded" v-on:click="SkillUpdate(item.skillId,value)">
                수정
              </button>
            </div>

          </td>

        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      list: [],
      skillRequestDto: {
        skillId: '',
        name: ''
      },
      InputName: ''
    };
  },
  mounted() {
    this.SkillList();
  },
  methods: {
    SkillList() {
      this.$axios
          .get("/api/v1/admin/skill", {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('user_token')}`
            }
          })
          .then((res) => {
            this.list = res.data.data;

          })
          .catch((err) => {
            alert(err.response.data.message)
          });
    },
    SkillUpdate(skillId) {
      this.skillRequestDto = {
        skillId: skillId,
        name: this.InputName
      }
      this.$axios
          .put("/api/v1/admin/skill", this.skillRequestDto, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('user_token')}`
            }
          })
          .then((res) => {
            this.list = res.data.data;
            this.fnList()

          })
          .catch((err) => {
            alert(err.response.data.message)
          });
    },
    fnList() {
      this.$router.push({
        path: '/admin/skill/list',

      })
    },
    onChangeInput(event) {
      this.InputName = event.target.value
    },

  },
};
</script>