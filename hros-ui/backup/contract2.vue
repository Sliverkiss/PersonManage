<template>
  <div>
    <div class="content" id="pjax-container">
      <div class="block block-rounded">
        <el-card class="notice-card ">
          <template #header>
            <div class="text-muted fw-bold " style="display:flex">
              <div style="width:200px">
                <UserFilled class="m-1 " width="20px"/>
                员工合同管理
              </div>
              <div style="flex:1"></div>
              <div class="" style="width:120px">
                <el-button type="success" plain @click="dialogFormVisible= true">
                  <el-icon>
                    <Plus/>
                  </el-icon>
                  <span>新增续约</span></el-button>
              </div>
            </div>
          </template>
          <div class="block-content block-content-full">
            <div id="usersList_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
              <div class="row">
                <div class="col-sm-12 col-md-12">
                  <el-input v-model="employeeId" style="width:120px" placeholder="请输入员工编号"></el-input>
                  <el-input v-model="name" style="width:100px;margin-left:10px" placeholder="请输入姓名"></el-input>
                  <el-select v-model="DepartmentId" style="width:120px;margin-left:10px" placeholder="请选择部门"
                             clearable>
                    <el-option
                        v-for="department in toRaw(departmentStore.departmentList)"
                        :key="department.id"
                        :label="department.departmentName"
                        :value="department.id"
                    />
                  </el-select>
                  <el-select v-model="status" style="width:140px;margin-left:10px" placeholder="请选择审核状态"
                             clearable>
                    <el-option label="通过" value="通过"/>
                    <el-option label="审核中" value="审核中"/>
                    <el-option label="未通过" value="未通过"/>
                  </el-select>
                  <el-button type="primary" class="ms-2" @Click="load">
                    <el-icon>
                      <Search/>
                    </el-icon>
                    搜索
                  </el-button>
                </div>
              </div>
              <h6 class=" mt-2 p-3 pb-0 text-secondary">待审核列表</h6>
              <div class="row">
                <div class="col-sm-12 p-3 ">
                  <el-table :data="state.tableData.filter(e=>e.state=='审核中')" stripe class="text-center">
                    <template v-for="(col,index) in toRaw(contractStore.contractMap)" :key="index">
                      <el-table-column :prop="col.key" :label="col.value" align="center" sortable></el-table-column>
                    </template>
                    <el-table-column prop="state" label="审核状态" align="center" sortable>
                      <template #default="scope">
                        <el-tag
                            :type="scope.row.state === '通过' ? '' :'审核中'?'warning': 'danger'"
                            disable-transitions>{{ scope.row.state }}
                        </el-tag>
                      </template>
                    </el-table-column>
                    <el-table-column fixed="right" align="center" label="操作" width="120">
                      <template #default="scope">
                        <el-button size="small" style="background-color:#66b1ff" @click="EditRenewal(scope.row)">
                          <el-icon>
                            <Edit style="color:#213d5b"/>
                          </el-icon>
                        </el-button>
                        <el-popconfirm @confirm="DelRenewal(scope.row.id)" title="确认删除?"
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
              <h6 class=" mt-2 p-3 pb-0 text-secondary">合同续约记录</h6>
              <div class="row">
                <div class="col-sm-12 p-3 ">
                  <el-auto-resizer>
                    <el-table :data="state.tableData.filter(e=>e.state=='未通过'||e.state=='通过')" stripe
                              class="text-center">
                      <template v-for="(col,index) in toRaw(contractStore.contractMap)" :key="index">
                        <el-table-column :prop="col.key" :label="col.value" align="center" sortable></el-table-column>
                      </template>
                      <el-table-column prop="state" label="审核状态" align="center" sortable>
                        <template #default="scope">
                          <el-tag
                              :type="scope.row.state === '通过' ? '' :scope.row.state ==='未通过'?'danger': 'warning'"
                              disable-transitions
                          >{{ scope.row.state }}
                          </el-tag
                          >
                        </template>
                      </el-table-column>
                      <el-table-column fixed="right" align="center" label="操作" width="120">
                        <template #default="scope">
                          <!--                          <el-button size="small" style="background-color:#66b1ff" @click="EditRenewal(scope.row)">-->
                          <!--                            <el-icon>-->
                          <!--                              <Edit style="color:#213d5b"/>-->
                          <!--                            </el-icon>-->
                          <!--                          </el-button>-->
                          <el-popconfirm @confirm="DelRenewal(scope.row.id)" title="确认删除?"
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
                  </el-auto-resizer>
                </div>
              </div>
              <div style="display:flex">
                <div>
                  <div class="dataTables_info" id="usersList_info" role="status" aria-live="polite"><span
                      class="text-muted ">共有 {{ total }} 条 / {{ currentPage }} 页</span></div>
                </div>
                <div style="flex:1"></div>
                <div>
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

  <!--  新增续约表单-->
  <div>
    <el-dialog v-model="dialogFormVisible" title="续约申请" align-center center class="" width="350"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-form-item prop="employeeId" size="large" label="员工编号：">
          <el-input v-model="state.formData.employeeId" placeholder="请输入员工编号"></el-input>
        </el-form-item>
        <el-form-item prop="renewalAge" size="large" label="续约年数：">
          <el-select v-model="state.formData.renewalAge" placeholder="请选择续约年数" style="width:2250px">
            <el-option
                v-for="(item,index) in 5"
                :key="index"
                :label="item+'年'"
                :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item prop="departmentComment" size="large" label="部门意见：">
          <el-input v-model="state.formData.departmentComment" type="textarea" placeholder="请输入部门意见"></el-input>
        </el-form-item>
        <el-form-item prop="state" size="large" label="审核结果：">
          <el-select v-model="state.formData.state" placeholder="请选择审核结果" style="width:2250px">
            <el-option label="通过" value="通过"/>
            <el-option label="审核中" value="审核中"/>
            <el-option label="未通过" value="未通过"/>
          </el-select>
        </el-form-item>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="save" type="primary">申请</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <!--  续约审核表单-->
  <div>
    <el-dialog v-model="dialogUpdateVisible" title="续约修改" align-center center class="" width="350"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-form-item prop="employeeId" size="large" label="员工编号：">
          <el-input v-model="state.updateData.employeeId" placeholder="请输入员工编号" disabled></el-input>
        </el-form-item>
        <el-form-item prop="renewalAge" size="large" label="续约年数：">
          <el-select v-model="state.updateData.renewalAge" placeholder="请选择续约年数" style="width:225px" disabled>
            <el-option
                v-for="(item,index) in 5"
                :key="index"
                :label="item+'年'"
                :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item prop="departmentComment" size="large" label="部门意见：">
          <el-input v-model="state.updateData.departmentComment" type="textarea"
                    placeholder="请输入部门意见"></el-input>
        </el-form-item>
        <el-form-item prop="state" size="large" label="审核结果：">
          <el-select v-model="state.updateData.state" placeholder="请选择审核结果" style="width:2250px">
            <el-option label="通过" value="通过"/>
            <el-option label="审核中" value="审核中"/>
            <el-option label="未通过" value="未通过"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="update" type="primary">审核</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
//加载后端合同数据
import request from "@/../src/request";
import {getCurrentInstance, reactive, ref, toRaw} from "vue";
import {useContract} from "@/stores/employee.js";
import {useDepartment} from "@/stores/department.js"
import {ElMessage, ElNotification} from "element-plus";
import {useUser} from '@/stores/user.js'

const {proxy} = getCurrentInstance();
const userStore = useUser();
const contractStore = useContract();
const departmentStore = useDepartment();

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
    {min: 1, max: 11, message: '员工编号范围在1～11位之间', trigger: 'blur'}
  ],
  renewalAge: [
    {required: true, message: '请选择续约年数', trigger: 'blur'},
  ],
  state: [
    {required: true, message: '请选择审核结果', trigger: 'blur'},
  ]
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
const status = ref('');//审核状态

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

const save = () => {
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      state.formData.director = userStore.getUser().username
      //发送后台请求
      request.post('admin/employee/renewal/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('续约申请成功！')
        } else {
          ElMessage.error('系统服务异常，请稍后再试~')
        }
        clearFormData();
        load();
      })
    } else {
      ElMessage.error('续约申请信息填写错误')
    }
  })
}
//修改员工资料
const update = () => {
  request.put('admin/employee/renewal/update', state.updateData).then((res) => {
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

//
const load = () => {
  request.get('/admin/employee/renewal/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      name: name.value,
      employeeId: employeeId.value,
      DepartmentId: DepartmentId.value,
      status: status.value,
    }
  }).then(res => {
    try {
      if (res.code === 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
        if (res.data.records.length == 0) {
          ElMessage.warning('暂无待审核列表～');
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
//删除员工资料
const DelRenewal = (id) => {
  request.delete('admin/employee/renewal/delete/' + id).then((res) => {
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
load()
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>