import {defineStore} from "pinia";
//管理登录信息
export const useUser = defineStore('user', {
    state: () => {
        return {
            user: {
                username: '',
                password: '',
                employee: {
                    employeeId: '',

                },
                img: '',
                role: ''
            }
        }
    },
    actions: {
        //设置用户信息
        setUser(val) {
            this.user = val;
        },
        getUser() {
            return this.user;
        },
        getUserName() {
            return this.user.username;
        }
    },
    //开启数据缓存
    persist: {
        enabled: true,
        strategies: [{
            key: 'my_user',
            storage: localStorage,
            // path:['username','password','role','img']
        }]
    }
})