import router from "@/router"

//设置动态路由
//permissions是一个资源数组
export async function activeRouter(permissions) {
    let root = {}
    permissions.forEach(p => {
        let obj = {
            path: p.path,
            name: p.name,
            component: () => import('@/views/' + p.name + '.vue')
        };
        root.children.push(obj);
    })
    //动态添加路由信息，重复路由覆盖
    router.addRoute(root);
}