import {defineStore} from 'pinia'

let modules = import.meta.glob("../views/*.vue")

// pinia状态管理器
export const useRouteStore = defineStore('myRoute', {
    persist: true,
    state: () => {
        return {
            // 路由表
            routes: []
        }
    },
    getters: {},
    actions: {
        // 添加动态路由，并同步到状态管理器中
        addRoutes(data, router) {
            this.routes = [];
            data.forEach((e) => {
                this.routes.push({
                    path: e.path,
                    name: e.name,
                    component: modules[`../views/Pages/${e.name}.vue`]
                });
            });
            this.routes.forEach((e) => {
                router.addRoute('Home', e);
            })
        }
    }
})
