
<template>
    <header class="header">
        <div id="nav" class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <router-link to="/" class="navbar-brand">홈</router-link>
                <button
                        class="navbar-toggler"
                        type="button"
                        data-toggle="collapse"
                        data-target="#navbarNav"
                        aria-controls="navbarNav"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                >
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <router-link to="/board/list" class="nav-link">게시판</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/message/received" v-if="this.$store.state.isLogin" class="nav-link">받은 메세지</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/message/send" v-if="this.$store.state.isLogin" class="nav-link">보낸 메세지</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/admin/main" v-if="Role()" class="nav-link">관리자 페이지</router-link>
                        </li>
                    </ul>
                </div>

                <div class="d-flex">
                    <router-link to="/signup" v-if="!this.$store.state.isLogin" class="nav-link" style="margin-right: 10px;">회원 가입
                    </router-link>
                    <router-link to="/login" v-if="!this.$store.state.isLogin" class="no-underline" style="margin-right: 10px;">로그인
                    </router-link>
                    <router-link to="/login" v-if="this.$store.state.isLogin" @click="fnLogout" class="no-underline">로그아웃
                    </router-link>
                </div>


            </div>
        </div>
    </header>
</template>

<style scoped>

.no-underline {
    text-decoration: none !important;
}
</style>

<script>

export default {
    data() {
        return {
            role: localStorage.getItem("user_role")

        }

    },
    methods: {
        fnLogout() {
            localStorage.removeItem("user_token")
            localStorage.removeItem("user_role")
            location.reload();
            this.$router.push({
                path: '/login',
            })
        },
        Role() {
            if (localStorage.getItem("user_role") === "ROLE_ADMIN") {
                return true;
            } else return false;
        },

    }
}
</script>

<style scoped>

</style>