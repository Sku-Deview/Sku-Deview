import {createRouter, createWebHistory} from 'vue-router'
import PageHome from '@/views/PageHome.vue'
import BoardList from '@/views/board/BoardList.vue'
import BoardDetail from "@/views/board/BoardDetail.vue";
import BoardWrite from "@/views/board/BoardWrite.vue";
import Login from "@/views/common/Login.vue";
import Signup from "@/views/common/Signup.vue";
import store from "@/vuex/store";
import MessageWrite from "@/views/message/MessageWrite.vue";
import MessageReceiveList from "@/views/message/MessageReceiveList.vue";
import MessageSendList from "@/views/message/MessageSendList.vue";
import AdminMain from "@/views/admin/AdminMain.vue";

import ReportWrite from "@/views/report/ReportWrite.vue";
import AdminMemberPost from "@/views/admin/AdminMemberPost.vue";
import AdminMemberPostDetail from "@/views/admin/AdminMemberPostDetail.vue";
import AdminMemberReply from "@/views/admin/AdminMemberReply.vue";
import AdminReport from "@/views/admin/AdminReport.vue";
import AdminReportDetail from "@/views/admin/AdminReportDetail.vue";



const requireAuth = () => (from, to, next) => {
    const token = localStorage.getItem('user_token')
    if (token) {
        store.state.isLogin = true
        return next()
    } // isLogin === true면 페이지 이동
    next('/login') // isLogin === false면 다시 로그인 화면으로 이동
}



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
        component: BoardList,
        beforeEnter: requireAuth()
    },
    {
        path: '/board/detail',
        name: 'BoardDetail',
        component: BoardDetail,
        beforeEnter: requireAuth()
    },
    {
        path: '/board/write',
        name: 'BoardWrite',
        component: BoardWrite,
        beforeEnter: requireAuth()
    },
    {
        path: '/message/write',
        name: 'MessageWrite',
        component: MessageWrite,
        beforeEnter: requireAuth()
    },
    {
        path: '/message/received',
        name: 'MessageReceiveList',
        component: MessageReceiveList,
        beforeEnter: requireAuth()
    },
    {
        path: '/message/send',
        name: 'MessageSendList',
        component: MessageSendList,
        beforeEnter: requireAuth()
    },
    {
        path: '/admin/main',
        name: 'AdminMain',
        component: AdminMain,
        beforeEnter: requireAuth()
    },
    {
        path: '/report/write',
        name: 'ReportWrite',
        component: ReportWrite,
    },
    {
        path: '/admin/member/post',
        name: 'AdminMemberPost',
        component: AdminMemberPost,
        beforeEnter: requireAuth()
    },
    {
        path: '/admin/member/post/detail',
        name: 'AdminMemberPostDetail',
        component: AdminMemberPostDetail,
        beforeEnter: requireAuth()
    },
    {
        path: '/admin/member/reply',
        name: 'AdminMemberReply',
        component: AdminMemberReply,
        beforeEnter: requireAuth()
    },
    {
        path: '/admin/report/list',
        name: 'AdminReport',
        component: AdminReport,
        beforeEnter: requireAuth()
    },
    {
        path: '/admin/report/detail',
        name: 'AdminReportDetail',
        component: AdminReportDetail,
        beforeEnter: requireAuth()
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


export default router