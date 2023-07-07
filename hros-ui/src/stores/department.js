import {defineStore} from "pinia";
//管理部门信息
export const useDepartment=defineStore('department',{
    state:()=>{
        return{
            departmentList:[]
        }
    },
    actions:{
        //设置用户信息
        setDepartmentList(val){
            this.departmentList = val;
        }
    },
    //开启数据缓存
    persist:{
        enabled:true,
        strategies:[{
            key:'DepartmentList',
            storage:localStorage,
        }]
    }
})