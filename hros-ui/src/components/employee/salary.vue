<template>
  <div class="">
    <div class="content" id="pjax-container">
      <div class="block block-rounded">
        <el-card class="notice-card ">
          <template #header>
            <div class="text-muted fw-bold " style="display:flex">
              <div style="width:200px">
                <UserFilled class="m-1 " width="20px"/>
                员工薪资管理
              </div>
              <div style="flex:1"></div>
              <div class="" style="width:120px">
                <el-button type="success" plain @click="dialogFormVisible= true">
                  <el-icon>
                    <Plus/>
                  </el-icon>
                  <span>新增薪资</span></el-button>
              </div>
            </div>
          </template>
          <div class="block-content block-content-full">
            <div id="usersList_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
              <div class="row">
                <div class="col-sm-12 p-3">
                  <el-table :data="state.tableData" stripe class="text-center">
                    <template v-for="(col,index) in toRaw(salaryStore.salaryMap)" :key="index">
                      <el-table-column :prop="col.key" :label="col.value" align="center" sortable></el-table-column>
                    </template>
                    <el-table-column prop="status" label="发放状态" align="center" sortable>
                      <template #default="scope">
                        <el-tag
                            :type="scope.row.status === '已发放' ? '' :'审核中'?'warning': 'danger'"
                            disable-transitions>{{ scope.row.status }}
                        </el-tag>
                      </template>
                    </el-table-column>
                    <!--修改删除-->
                    <el-table-column fixed="right" align="center" label="操作" width="120">
                      <template #default="scope">
                        <el-button size="small" style="background-color:#66b1ff" @click="EditSalary(scope.row)">
                          <el-icon>
                            <Edit style="color:#213d5b"/>
                          </el-icon>
                        </el-button>
                        <el-popconfirm @confirm="DeleteSalary(scope.row.id)" title="确认删除?"
                                       confirm-button-text="确认"
                                       cancel-button-text="取消">
                          <template #reference>
                            <el-button type="danger" size="small">
                              <el-icon>
                                <Delete style="color:#582e2e"/>
                              </el-icon>
                            </el-button>
                          </template>
                        </el-popconfirm>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-md-4">
                  <div class="dataTables_info" id="usersList_info" role="status" aria-live="polite"><span
                      class="text-muted ">共有 {{ total }} 条 / {{ currentPage }} 页</span></div>
                </div>
                <div class="col-sm-12 col-md-7">
                  <el-pagination
                      background
                      :page-sizes="[1,10,20,30]"
                      layout="prev,pager,next"
                      v-model::current-page="currentPage"
                      v-model:page-size="pageSize"
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange"
                      :total="total">
                  </el-pagination>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <Foot/>
  </div>
  <!--  新增薪资表单-->
  <div>
    <el-dialog v-model="dialogFormVisible" title="新增薪资" align-center center class="" width="700"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item prop="employeeId" size="large" label="员工编号：">
              <el-input v-model="state.formData.employeeId" placeholder="请输入员工编号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="salaryDate" size="large" label="工资月份：">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.formData.salaryDate"
                      type="month"
                      format="YYYY/MM"
                      value-format="YYYY-MM"
                      placeholder=""
                      :size="10"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="baseSalary" size="large" label="基础工资：">
              <el-input v-model.number="state.formData.baseSalary" placeholder="请输入基础工资"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="performance" size="large" label="绩效奖金：">
              <el-input v-model.number="state.formData.performance" placeholder="请输入绩效奖金"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="deduLeave" size="large" label="请假扣款：">
              <el-input v-model.number="state.formData.deduLeave" placeholder="请输入请假扣款"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="deduLate" size="large" label="迟到扣款：">
              <el-input v-model.number="state.formData.deduLate" placeholder="请输入迟到扣款"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="insure" size="large" label="五险一金：">
              <el-input v-model.number="state.formData.insure" placeholder="请输入五险一金"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="status" size="large" label="发放状态：">
              <el-select v-model="state.formData.status" placeholder="请选择发放状态" style="width:2250px">
                <el-option label="已发放" value="已发放"/>
                <el-option label="未发放" value="未发放"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="save" type="primary">新增</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
    <!--修改薪资信息-->
    <div>
      <el-dialog v-model="dialogUpdateVisible" title="薪资修改" align-center center class="" width="700"
                 style="border-radius: 0.875rem 1rem;">
        <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item prop="employeeId" size="large" label="员工编号：">
                <el-input v-model="state.updateData.employeeId" placeholder="请输入员工编号" disabled></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="salaryDate" size="large" label="工资月份：">
                <div class="demo-date-picker">
                  <div class="block">
                    <el-date-picker
                        v-model="state.updateData.salaryDate"
                        type="month"
                        format="YYYY/MM"
                        value-format="YYYY-MM"
                        placeholder=""
                        :size="10"/>
                  </div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="baseSalary" size="large" label="基础工资：">
                <el-input v-model.number="state.updateData.baseSalary" placeholder="请输入基础工资"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="performance" size="large" label="绩效奖金：">
                <el-input v-model.number="state.updateData.performance" placeholder="请输入绩效奖金"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="deduLeave" size="large" label="请假扣款：">
                <el-input v-model.number="state.updateData.deduLeave" placeholder="请输入请假扣款"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="deduLate" size="large" label="迟到扣款：">
                <el-input v-model.number="state.updateData.deduLate" placeholder="请输入迟到扣款"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="insure" size="large" label="五险一金：">
                <el-input v-model.number="state.updateData.insure" placeholder="请输入五险一金"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item prop="status" size="large" label="发放状态：">
                <el-select v-model="state.updateData.status" placeholder="请选择发放状态" style="width:2250px">
                  <el-option label="已发放" value="已发放"/>
                  <el-option label="未发放" value="未发放"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="update" type="primary">修改</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import {getCurrentInstance, reactive, ref, toRaw} from "vue";
import request from "@/request.js";
import {ElMessage, ElNotification} from "element-plus";
import {useSalary} from "@/stores/employee.js";

const {proxy} = getCurrentInstance();

const salaryStore = useSalary();
const state = reactive({
  tableData: [],
  formData: {},
  updateData: {
    employeeId: '',
    renewalAge: '',
    departmentComment: '',
    state: ''
  },
})

const rules = reactive({
  employeeId: [
    {required: true, message: '请输入员工编号', trigger: 'blur'},
    {min: 1, max: 11, message: '员工编号范围在1～11位之间', trigger: 'blur'},
  ],
  status: [
    {required: true, message: '请选择发放状态', trigger: 'blur'},
  ],
  salaryDate: [
    {required: true, message: '请选择工资月份', trigger: 'blur'},
  ],
  baseSalary: [
    {type: 'number', required: true, message: '只能输入数字类型', trigger: 'blur'},
  ],
  performance: [
    {type: 'number', required: false, message: '只能输入数字类型', trigger: 'blur'},
  ],
  deduLeave: [
    {type: 'number', required: false, message: '只能输入数字类型', trigger: 'blur'},
  ],
  deduLate: [
    {type: 'number', required: false, message: '只能输入数字类型', trigger: 'blur'},
  ],
  insure: [
    {type: 'number', required: false, message: '只能输入数字类型', trigger: 'blur'},
  ],
})

//打开新增续约视图
const dialogFormVisible = ref(false)
const dialogUpdateVisible = ref(false)
//模糊查询条件
const currentPage = ref(1);//当前页
const pageSize = ref(5);//页码展示数量
const total = ref(10);//页码总数
const employeeId = ref('');//查询员工编号
const name = ref('');//员工姓名
const DepartmentId = ref('');//部门编号
const salaryDate = ref('');//发放年份
const status = ref('');//发放状态

const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}

const EditRenewal = (row) => {
  dialogUpdateVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
}
const clearFormData = () => {
  let clearData = {}
  state.formData = clearData;
  dialogFormVisible.value = false;
  dialogUpdateVisible.value = false;
}
const EditSalary = (row) => {
  dialogUpdateVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
}
//新增薪资记录
const save = () => {
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      //发送后台请求
      request.post('admin/employee/salary/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('新增薪资成功！')
        } else {
          ElMessage.error('系统服务异常，请稍后再试~')
        }
        clearFormData();
        load();
      })
    } else {
      ElMessage.error('薪资信息填写错误')
    }
  })
}
//修改员工资料
const update = () => {
  request.put('admin/employee/salary/update', state.updateData).then((res) => {
    try {
      if (res.code == 200) {
        ElNotification.success(res.msg);
      } else {
        ElMessage.error(res.msg);
      }
    } catch {
      ElMessage.error(res.msg);
    } finally {
      clearFormData();
      load();
    }
  })
}
//删除员工资料
const DeleteSalary = (id) => {
  request.delete('admin/employee/salary/delete/' + id).then((res) => {
    try {
      if (res.code == 200) {
        ElNotification.success(res.msg);
      } else {
        ElMessage.error(res.msg);
      }
    } catch {
      ElMessage.error(res.msg);
    } finally {
      load();
    }
  })
}
//加载数据
const load = () => {
  request.get('/admin/employee/salary/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      employeeId: employeeId.value,
      name: name.value,
      DepartmentId: DepartmentId.value,
      salaryDate: salaryDate.value,
      status: status.value
    }
  }).then(res => {
    try {
      if (res.code === 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
        if (res.data.records.length == 0) {
          ElMessage.warning('暂无薪资记录～');
        }
      } else {
        state.tableData = [];
        total.value = 0
        ElMessage.warning(res.msg);
      }
    } catch {
      ElMessage.error(res.msg);
    }
  })
}
load()
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>