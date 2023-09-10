<!-- PageHeader.vue -->
<!--<template>-->
<!--    <header>-->
<!--        <div id="nav">-->
<!--            <router-link to="/">Home</router-link>-->
<!--            |-->
<!--            <router-link to="/board/list">게시판</router-link>-->
<!--            |-->
<!--            <router-link to="/signup">회원 가입</router-link>-->
<!--            |-->
<!--            <router-link to="/message/received">받은 메세지</router-link>-->
<!--            |-->
<!--            <router-link to="/message/send">보낸 메세지</router-link>-->
<!--            |-->
<!--            <router-link to="/login" v-if="!this.$store.state.isLogin">로그인</router-link>-->
<!--            <a v-if="this.$store.state.isLogin" @click="fnLogout">로그아웃</a>-->

<!--        </div>-->
<!--    </header>-->
<!--    <hr/>-->
<!--</template>-->

<template>
    <header>
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
                            <router-link to="/signup" class="nav-link">회원 가입</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/message/received" class="nav-link">받은 메세지</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/message/send" class="nav-link">보낸 메세지</router-link>
                        </li>
                    </ul>
                </div>
                <div class="ml-auto">
                    <router-link to="/login" v-if="!this.$store.state.isLogin" class="no-underline">로그인</router-link>
                    <a v-if="this.$store.state.isLogin" @click="fnLogout" >로그아웃</a>
                </div>
            </div>
        </div>
    </header>
</template>

<style scoped>
/* 추가적인 CSS 스타일링을 여기에 적용하세요. */

/* 밑줄 스타일 제거 */
.no-underline {
    text-decoration: none !important;
}
</style>

<script>
export default {
    methods: {
        fnLogout() {
            localStorage.removeItem("user_token")
            localStorage.removeItem("user_role")
            location.reload()
        }
    }
}
</script>

<style scoped>

</style>