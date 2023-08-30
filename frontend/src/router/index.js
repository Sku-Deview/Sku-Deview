import { createRouter, createWebHistory } from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardDetail from "@/views/board/BoardDetail.vue";
import BoardWrite from "@/views/board/BoardWrite.vue";
import Login from "@/views/common/Login.vue";
import Signup from "@/views/common/Signup.vue";

const routes = [
    {
        path: '/',
        name: 'PageHome',
        component: PageHome
    },
    {
        path: '/login',
        name: 'Login',
        component: Login  //로그인 컴포넌트 추가
    },
    {
        path: '/signup',
        name: 'Signup',
        component: Signup  //로그인 컴포넌트 추가
    },
    {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/PageAbout.vue')
    },
    {
        path: '/board/list',
        name: 'BoardList',
        component: BoardList
    },
    {
        path: '/board/detail',
        name: 'BoardDetail',
        component: BoardDetail
    },
    {
        path: '/board/write',
        name: 'BoardWrite',
        component: BoardWrite
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router