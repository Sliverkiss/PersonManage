import {createRouter, createWebHistory} from 'vue-router'

import NotFound from '@/views/error/404.vue'

const routes = [
    {
        path: '',
        name: 'Welcome',
        component: import('@/views/WelcomeView.vue'),
        children: [{
            path: '',
            name: 'welcome/login',
            component: () => import('@/components/welcome/LoginPage.vue')
        }, {
            path: '/register',
            name: 'welcome/register',
            component: () => import('@/components/welcome/RegisterPage.vue')
        }, {
            path: '/find',
            name: 'welcome/find',
            component: () => import('@/components/welcome/FindPage.vue')
        }
        ]
    },
    {
        path: '/index/home',
        name: 'Index',
        component: () => import('@/views/IndexView.vue'),
        children: [{
            path: '/index/home',
            name: 'Index/home',
            component: () => import('@/components/index/Home.vue')
        }]
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: NotFound
    }

]

const router = createRouter({
    history: createWebHistory('/'),
    routes: routes
})

// //路由守卫
// router.beforeEach((guard) => {
//     beforeEach.checkAuth(guard,router);
// })


export default router
