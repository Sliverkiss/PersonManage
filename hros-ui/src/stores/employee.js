import {defineStore} from "pinia";
//管理登录信息
export const useEmployee=defineStore('employee',{
    state:()=>{
        return{
            employeeList:[{}]
        }
    },
    actions:{
        //设置用户信息
        setEmployeeList(val){
            this.employeeList = val;
        }
    },
    //开启数据缓存
    persist:{
        enabled:true,
        strategies:[{
            key:'employeeInfoList',
            storage:localStorage,
        }]
    }
})