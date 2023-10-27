import {defineStore} from "pinia";
//管理侧边栏信息
export const useAside = defineStore('aside', {
    state: () => {
        return {
            indexAside: true,
            consoleAside: false,
        }
    },
    actions: {
        //用户侧边栏开关
        setIndexAside(val) {
            this.indexAside = val;
        },
        //管理员侧边栏开关
        setConsoleAside(val) {
            this.consoleAside = val;
        },
        getIndexAside() {
            return this.indexAside;
        },
        getConsoleAside() {
            return this.consoleAside;
        }

    },
    //开启数据缓存
    persist: {
        enabled: false,
        strategies: [{
            key: 'Aside',
            storage: localStorage,
        }]
    }
})