import NotFound from "@/views/error/404.vue";

export const basicRouter = [


    {
        path: '/',
        name: 'home',
        redirect: '/index',
        component: import('@/views/Home.vue'),
        children: [{
            path: '/index',
            name: 'index',
            component: () => import('@/views/home/index.vue')
        }, {
            path: '/employee',
            name: 'employee',
            component: () => import('@/components/employee/employeeManagement.vue')
        }, {
            path: '/employee/info',
            name: 'employeeInfo',
            component: () => import('@/components/employee/employeeManagement.vue'),
        }, {
            path: '/employee/renewal',
            name: 'renewal',
            component: () => import('@/components/employee/contract.vue'),
        }, {
            path: '/employee/salary',
            name: 'salary',
            component: () => import('@/components/employee/salary.vue'),
        }, {
            path: '/department',
            name: 'department',
            component: () => import('@/components/department/department.vue'),
        }, {
            path: '/department/info',
            name: 'departmentInfo',
            component: () => import('@/components/department/department.vue')
        }, {
            path: '/department/transfer',
            name: 'transfer',
            component: () => import('@/components/department/transfer.vue'),
        }, {
            path: '/department/reinstatement',
            name: 'reinstatement',
            component: () => import('@/components/department/reinstatement.vue'),
        }, {
            path: '/assess',
            name: 'assess',
            component: () => import('@/views/assess/set.vue'),
        }, {
            path: '/assess/set',
            name: 'assessSet',
            component: () => import('@/views/assess/set.vue'),
        }, {
            path: '/assess/declare',
            name: 'assessDeclare',
            component: () => import('@/views/assess/declare.vue'),
        }, {
            path: '/assess/declare/review',
            name: 'assessDeclareReview',
            component: () => import('@/views/assess/review.vue'),
        }, {
            path: '/assess/declare/approval',
            name: 'assessDeclareApproval',
            component: () => import('@/views/assess/approval.vue'),
        }, {
            path: '/assess/item',
            name: 'assessItem',
            component: () => import('@/views/assess/item.vue'),
        }, {
            path: '/assess/staff',
            name: 'assessStaff',
            component: () => import('@/views/assess/staff.vue'),
        }, {
            path: '/assess/approval',
            name: 'assessApproval',
            component: () => import('@/views/assess/approval.vue'),
        }, {
            path: '/trainning',
            name: 'trainning',
            component: () => import('@/components/trainning/trainning.vue'),
        }, {
            path: '/trainning/plan',
            name: 'trainningInfo',
            component: () => import('@/components/trainning/trainning.vue'),
        }, {
            path: '/trainning/record',
            name: 'trainningRecord',
            component: () => import('@/views/train/record/index.vue'),
        }, {
            path: '/report',
            name: 'report',
            component: () => import('@/views/report/index.vue'),
        }, {
            path: '/system/notice',
            name: 'notice',
            component: () => import('@/views/system/notice/index.vue'),
        }, {
            path: '/system/user',
            name: 'user',
            component: () => import('@/views/system/user/index.vue'),
        }]
    }, {
        path: '/login',
        name: 'login',
        component: () => import('@/components/welcome/LoginPage.vue')
    },
    {
        path: '/:catchAll(.*)',
        component: NotFound
    }
]
