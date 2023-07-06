import {createRouter, createWebHistory} from 'vue-router'

import NotFound from '@/views/error/404.vue'
// import {useUser} from '@/stores/user.js'

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
        }, {
            path: '/index/userInfo',
            name: 'Index/userInfo',
            component: () => import('@/components/userInfo/userInfo.vue')
        }, {
            path: '/index/employeeManagement',
            name: 'Index/employeeManagement',
            component: () => import('@/components/employee/EmployeeManagement.vue')
        },
            {
                path: '/index/contract',
                name: 'Index/contract',
                component: () => import('@/components/employee/contract.vue')
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

// const userStore = useUser();

// //路由守卫
// router.beforeEach((to, from, next) => {
//     if (to.path == '/login') next()
//     if (!userStore && to.path != '/login') return next('/login')
//     next()
// })


export default router
