import {defineStore} from "pinia";
//管理登录信息
export const useUser=defineStore('user',{
    state:()=>{
        return{
            user:{
                usernames:'',
                password:'',
                employee:{
                    employeeId:'',

                },
                img:'',
                role:''
            }
        }
    },
    actions:{
        //设置用户信息
        setUser(val){
             this.user = val;
        }
    },
    //开启数据缓存
    persist:{
        enabled:true,
        strategies:[{
            key:'my_user',
            storage:localStorage,
            // path:['username','password','role','img']
        }]
    }
})