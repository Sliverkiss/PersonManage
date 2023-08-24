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
    console.log("路由")
    console.log(user.employeeVo?.workState);
    if (!(user || to.path === "/login")) {
        next("/login");
        // }
        // else if (!(user.employeeVo?.workState=="离职"||to.path ==="/404")){
        //     next("/404")
    } else {
        next();
    }


});

export default router
