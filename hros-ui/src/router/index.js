import {createRouter, createWebHistory} from 'vue-router'

import NotFound from '@/views/error/404.vue'
// import {useUser} from '@/stores/user.js'

const routes = [
    {
        path: '',
        name: 'Welcome',
        component: import('@/views/WelcomeView.vue'),
        children: [{
            path: '/login',
            name: '登录页面',
            component: () => import('@/components/welcome/LoginPage.vue')
        }, {
            path: '/register',
            name: '注册',
            component: () => import('@/components/welcome/RegisterPage.vue')
        }, {
            path: '/find',
            name: '找回密码',
            component: () => import('@/components/welcome/FindPage.vue')
        }
        ]
    },
    {
        path: '/index/',
        name: 'Index',
        component: () => import('@/views/IndexView.vue'),
        children: [{
            path: 'index/home',
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
        }, {
            path: '/index/contract',
            name: 'Index/contract',
            component: () => import('@/components/employee/contract.vue')
        }, {
            path: '/index/department',
            name: 'Index/department',
            component: () => import('@/components/department/department.vue')
        }]
    },
    {
        path: '/console',
        name: '管理后台',
        component: () => import('@/views/IndexView.vue'),
        children: [{
            path: '/console/employee',
            name: '员工管理',
            component: () => import('@/components/employee/employeeManagement.vue')
        },{
            path: '/console/employee/info',
            name: '员工资料管理',
            component: () => import('@/components/employee/employeeManagement.vue'),
        }, {
            path: '/console/employee/contract',
            name: '员工合同管理',
            component: () => import('@/components/employee/contract.vue'),
        },{
            path:'/console/employee/salary',
            name:'员工薪资管理',
            component: () => import('@/components/employee/salary.vue'),
        },{
            path:'/console/department',
            name:'部门岗位管理',
            component: () => import('@/components/department/department.vue'),
        },{
            path: '/console/department/info',
            name: '部门岗位管理',
            component: () => import('@/components/department/department.vue')
        },{
            path:'/console/department/transfer',
            name:'调岗审核管理',
            component: () => import('@/components/department/transfer.vue'),
        },{
            path:'/console/department/reinstatement',
            name:'离职复职管理',
            component: () => import('@/components/department/reinstatement.vue'),
        },{
            path:'/console/indicator',
            name:'绩效考核管理',
            component: () => import('@/components/indicator/indicator.vue'),
        },{
            path:'/console/indicator/info',
            name:'绩效考核管理',
            component: () => import('@/components/indicator/indicator.vue'),
        },{
            path:'/console/indicator/performance',
            name:'考核列表管理',
            component: () => import('@/components/indicator/performance.vue'),
        },{
            path:'/console/trainning',
            name : '培训管理',
            component: () => import('@/components/trainning/trainning.vue'),
        },{
            path:'/console/trainning/info',
            name : '培训管理',
            component: () => import('@/components/trainning/trainning.vue'),
        },{
            path:'/console/trainning/record',
            name:'培训列表管理',
            component: () => import('@/components/trainning/record.vue'),
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
