import {createPinia} from 'pinia'
//引入pinia的持久化存储插件
import piniaPluginPersist from 'pinia-plugin-persist'

const store=createPinia()
// 使用插件
store.use(piniaPluginPersist)

export default store