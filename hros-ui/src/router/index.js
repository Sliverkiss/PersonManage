
import {createRouter, createWebHistory} from 'vue-router'

const routes=[
    {
        path:'/',
        name:'Home',
        component:()=>import('../views/Home.vue')
    },
    {
        path:'/login',
        name:'Login',
        component:()=>import('../views/Login.vue')
    }
]

const router=createRouter({
    history:createWebHistory('/'),
    routes:routes
})

// //路由守卫
// router.beforeEach((guard) => {
//     beforeEach.checkAuth(guard,router);
// })


export default router
