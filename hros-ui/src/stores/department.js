import {defineStore} from "pinia";
import {toRaw} from "vue";
//管理部门信息
export const useDepartment=defineStore('department',{
    state:()=>{
        return{
            departmentList:[]
        }
    },
    actions: {
        //设置用户信息
        setDepartmentList(val) {
            this.departmentList = val;
        },
        getDepartmentList() {
            return toRaw(this.departmentList);
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