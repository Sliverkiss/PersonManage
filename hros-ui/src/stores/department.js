import {defineStore} from "pinia";
import {toRaw} from "vue";
//管理部门信息
export const useDepartment=defineStore('department',{
    state:()=>{
        return{
            departmentList: [],
            transferList: [],
            transferMap: [
                {
                    key: 'id',
                    value: '调岗编号'
                },
                {
                    key: 'employeeId',
                    value: '员工编号'
                },
                {
                    key: 'employeeName',
                    value: '员工姓名'
                },
                {
                    key: 'beforeDepartmentName',
                    value: '调出部门'
                },
                {
                    key: 'afterDepartmentName',
                    value: '调入部门'
                },
                {
                    key: 'transferPost',
                    value: '调动岗位'
                },
                {
                    key: 'reason',
                    value: '调动理由'
                },
                {
                    key: 'transferType',
                    value: '调动类型'
                },
                {
                    key: 'kind',
                    value: '调动种类'
                },
                {
                    key: 'applyDate',
                    value: '申请日期'
                },
            ]


        }
    },
    actions: {
        //设置用户信息
        setDepartmentList(val) {
            this.departmentList = val;
        },
        getDepartmentList() {
            return toRaw(this.departmentList);
        },
        setTransferList(val) {
            this.transferList = val;
        },
        getTransferList() {
            return toRaw(this.transferList);
        },
        setTransferMap(val) {
            this.transferMap = val;
        },
        getTransfeMap() {
            return toRaw(this.transferMap);
        },
    },
    //开启数据缓存
    persist: {
        enabled: false,
        strategies: [{
            key: 'departmentList',
            storage: localStorage,
        }]
    }
})