import {defineStore} from "pinia";
//管理登录信息
export const useEmployee = defineStore('employee', {
    state: () => {
        return {
            employeeList: [{}],
            employeeMap: [
                {
                    key: 'id',
                    value: 'UID'
                },
                {
                    key: 'name',
                    value: '名称'
                },
                {
                    key: 'phone',
                    value: '联系电话'
                }, {
                    key: 'wedlock',
                    value: '婚姻状况'
                }, {
                    key: 'birthday',
                    value: '出生日期',
                }, {
                    key: 'nation',
                    value: '民族'
                }, {
                    key: 'naticePlace',
                    value: '籍贯'
                }, {
                    key: 'email',
                    value: '邮箱'
                }, {
                    key: 'address',
                    value: '地址'
                }, {
                    key: 'gender',
                    value: '性别'
                }, {
                    key: 'idCard',
                    value: '身份证号'
                }, {
                    key: 'tiptopDegree',
                    value: '最高学历'
                }, {
                    key: 'specialty',
                    value: '所学专业'
                }, {
                    key: 'school',
                    value: '毕业院校'
                }, {
                    key: 'politic',
                    value: '政治面貌'
                }, {
                    key: 'departmentName',
                    value: '部门'
                }, {
                    key: 'post',
                    value: '职务'
                }, {
                    key: 'level',
                    value: '职称'
                }, {
                    key: 'hireDate',
                    value: '入职日期'
                }, {
                    key: 'startContract',
                    value: '合同起始时间'
                }, {
                    key: 'endContract',
                    value: '合同终止时间'
                }, {
                    key: 'contractTerm',
                    value: '合同年限'
                }, {
                    key: 'engageForm',
                    value: '合同类型'
                }
            ]
        }
    },
    actions: {
        //设置用户信息
        setEmployeeList(val) {
            this.employeeList = val;
        },
        setEmployeeMap(val) {
            this.employeeMap = val;
        }
    },
    //开启数据缓存
    persist: {
        enabled: false,
        strategies: [{
            key: 'employeeInfoList',
            storage: localStorage,
        }]
    }
})

export const useContract = defineStore('contract', {
    state: () => {
        return{
            contractMap:[
                {
                    key: 'id',
                    value: '序号'
                }, {
                    key: 'employeeId',
                    value: 'UID'
                }, {
                    key: 'name',
                    value: '姓名'
                }, {
                    key: 'departmentName',
                    value: '所在部门'
                }, {
                    key: 'renewalAge',
                    value: '续约年数'
                }, {
                    key: 'departmentComment',
                    value: '部门意见'
                }, {
                    key: 'approvedDate',
                    value: '审核日期'
                }, {
                    key: 'director',
                    value: '审核人'
                }
            ]
        }

    },
    actions: {
        //设置用户信息

        setContractMap(val) {
            this.contractMap = val;
        }
    },
    //开启数据缓存
    persist: {
        enabled: false,
        strategies: [{
            key: 'contractList',
            storage: localStorage,
        }]
    }
})

export const useSalary = defineStore('salary', {
    state: () => {
        return {
            salaryMap: [
                {
                    key: 'id',
                    value: '序号'
                }, {
                    key: 'employeeId',
                    value: 'UID'
                }, {
                    key: 'name',
                    value: '姓名'
                }, {
                    key: 'departmentName',
                    value: '所在部门'
                }, {
                    key: 'salaryDate',
                    value: '工资月份'
                }, {
                    key: 'baseSalary',
                    value: '基础工资'
                }, {
                    key: 'performance',
                    value: '绩效奖金'
                }, {
                    key: 'deduLeave',
                    value: '请假扣款'
                }, {
                    key: 'deduLate',
                    value: '迟到扣款'
                }, {
                    key: 'insure',
                    value: '五险一金'
                }, {
                    key: 'netSalary',
                    value: '净工资'
                }, {
                    key: 'payDate',
                    value: '创建时间'
                }
            ]
        }
    }, actions: {
        //设置用户信息

        setContractMap(val) {
            this.contractMap = val;
        }
    },
    //开启数据缓存
    persist: {
        enabled: false,
        strategies: [{
            key: 'contractList',
            storage: localStorage,
        }]
    }
})