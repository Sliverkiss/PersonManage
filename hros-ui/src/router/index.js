import {createRouter, createWebHistory} from 'vue-router'
import {basicRouter} from "@/router/Basicrouter.js";
//获取用户信息
import {useUser} from '@/stores/user.js'
//路由默认配置
const routes = basicRouter;
const router = createRouter({
    history: createWebHistory('/'),
    routes
})
//前置路由守卫
router.beforeEach((to, from, next) => {
    //to代表要去的路径
    //from表示原来的路径
    //next()通行
    // 验证token,只有存在token的时候，才能跳转到内容页
    const userStore = useUser();
    const user = userStore.getUser();
    //访问注册页面，放行
    if (to.path == "/register") next();
    //访问找回密码，放行
    if (to.path == "/find") next();
    //如果用户未登录或访问非登录页面，拦截并跳转到登录页面
    if (!(user || to.path === "/login")) {
        next("/login");
    }
    //如果用户已登录且为离职状态，拦截并跳转到复职页面
    // if (!(user.employeeVo.workState == '离职' || to.path == "/department/reinstatement")) {
    //     console.log(user)
    //     next("/department/reinstatement")
    // }
    next();


});

export default router
