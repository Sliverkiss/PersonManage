<template>
  <div v-if="user.employeeVo.workState=='离职'">无权限</div>
  <div v-else>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card ">
      <el-tab-pane name="first" @tab-Click="router.push('/console/employee/contract')">
        <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>合同管理</span>
        </span>
        </template>
        <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
          <div class="row">
            <div class="col-sm-12 col-md-12">
              <el-input v-model="employeeId" style="width:120px" placeholder="请输入员工编号" v-if="user.role"
                        clearable></el-input>
              <el-input v-model="name" style="width:100px;margin-left:10px" placeholder="请输入姓名" v-if="user.role"
                        clearable></el-input>
              <!--              <el-select v-model="DepartmentId" style="width:120px;margin-left:10px" placeholder="请选择部门"-->
              <!--                         clearable>-->
              <!--                <el-option-->
              <!--                    v-for="department in departmentStore.departmentList"-->
              <!--                    :key="department.id"-->
              <!--                    :label="department.departmentName"-->
              <!--                    :value="department.id"-->
              <!--                />-->
              <!--              </el-select>-->
              <el-select v-model="status" style="width:150px;;margin-left: 10px" placeholder="请选择续约状态"
                         clearable>
                <el-option label="处理中" value="处理中"/>
                <el-option label="同意" value="同意"/>
                <el-option label="未同意" value="未同意"/>
              </el-select>
              <el-button type="primary" class="ms-2" @Click="load">
                <el-icon>
                  <Search/>
                </el-icon>
                搜索
              </el-button>
            </div>
          </div>
          <div style="flex:1"></div>
          <div class="" style="width:120px" v-if="user.role">
            <el-button type="success" plain @click="dialogFormVisible= true">
              <el-icon>
                <Plus/>
              </el-icon>
              <span>发起续约</span></el-button>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 p-3 ">
            <el-table :data="state.tableData" stripe class="text-center" height="400" max-height="400"
                      element-loading-text="拼命加载中">
              <el-table-column label="UID" prop="employeeId" align="center"/>
              <el-table-column label="姓名" prop="name" align="center"/>
              <el-table-column label="所在部门" prop="departmentName" align="center"/>
              <el-table-column label="合同起始日期" prop="startContract" align="center" width="120"/>
              <el-table-column label="合同终止日期" prop="endContract" align="center" width="120"/>
              <el-table-column label="续约年数" prop="renewalAge" align="center"/>
              <!--              <el-table-column label="审核日期" prop="approvedDate" sortable align="center" width="120"/>-->
              <!--              <el-table-column label="审核人" prop="director" align="center"/>-->
              <el-table-column prop="state" label="续约状态" align="center" sortable width="120">
                <template #default="scope">
                  <el-tag
                      :type="scope.row.state === '同意' ? '' :scope.row.state ==='未同意'?'danger': 'warning'"
                      disable-transitions
                  >{{ scope.row.state }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column fixed="right" align="center" label="流程" width="80">
                <template #default="scope">
                  <el-link @click="handleMordChange(scope.row)" type="primary"> 查看详情</el-link>
                </template>
              </el-table-column>
              <el-table-column fixed="right" align="center" label="操作" width="120" v-if="!user.role">
                <template #default="scope">
                  <div>
                    <el-button size="small" style="background-color:#66b1ff" @click="EditRenewal(scope.row)">
                      <el-icon>
                        <Edit style="color:#213d5b"/>
                      </el-icon>
                    </el-button>
                    <el-popconfirm v-if="user.role"
                                   @confirm="DelRenewal(scope.row.id)" title="确认删除?"
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
                  </div>
                </template>
              </el-table-column>
            </el-table>
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
      </el-tab-pane>
      <Foot/>
    </el-tabs>
  </div>

  <div>
    <el-dialog v-model="dialogFormVisible" title="发起续约" align-center center class="" width="350"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-form-item prop="employeeId" size="large" label="员工编号：">
          <el-select v-model="state.formData.employeeId" style="width:320px" placeholder="请输入或选择员工"
                     clearable filterable>
            <el-option v-for="item in state.empList" :label="item.id+' '+item.personal.name" :value="item.id"/>
          </el-select>
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

      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="save" type="primary">确定</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <!--  续约审核表单-->
  <div>
    <el-dialog v-model="dialogUpdateVisible" title="续约详情" align-center center class="" width="350"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-form-item prop="state" size="large" label="续约结果：">
          <el-radio-group v-model="state.updateData.state" class="ml-4">
            <el-radio label="同意" size="large">同意</el-radio>
            <el-radio label="未同意" size="large">未同意</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="departmentComment" size="large" label="个人意见：">
          <el-input v-model="state.updateData.departmentComment" type="textarea"
                    placeholder="请输入个人意见"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="update" type="primary">确定</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>

  <div>
    <el-dialog v-model="dialogMoreVisible" title="续约详情" align-center center class="" width="600"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules"
               label-position="top" ref="ruleFormRef">
        <el-timeline>
          <el-timeline-item icon="MoreFilled" type="primary" size="large" :timestamp="state.updateData.applyDate">
            邀请续约
            <p class="text-muted p-0 m-0" style="font-size:13px">发起人:{{ state.updateData.director }}</p>
          </el-timeline-item>
          <el-timeline-item icon="MoreFilled" type="warning" size="large" :timestamp="state.updateData.applyDate">
            处理中
          </el-timeline-item>
          <el-timeline-item icon="close" type="danger" size="large" :timestamp="state.updateData.approvedDate"
                            v-if="state.updateData.state=='未同意'">
            拒绝续约
            <el-form-item prop="departmentComment" size="large" label="员工意见：">
              <el-input v-model="state.updateData.departmentComment"
                        :autosize="{ minRows: 4, maxRows: 8 }"
                        type="textarea" disabled></el-input>
            </el-form-item>

          </el-timeline-item>
          <el-timeline-item icon="check" type="success" size="large" :timestamp="state.updateData.approvedDate"
                            v-else-if="state.updateData.state=='同意'">
            同意续约
            <el-form-item prop="departmentComment" size="large" label="员工意见：">
              <el-input v-model="state.updateData.departmentComment"
                        :autosize="{ minRows: 4, maxRows: 8 }"
                        type="textarea" disabled></el-input>
            </el-form-item>

          </el-timeline-item>
        </el-timeline>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="clearFormData" type="primary">确认</el-button>
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
import request from "@/request.js";
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import {useContract} from "@/stores/employee.js";
import {ElMessage, ElNotification} from "element-plus";
import {useUser} from '@/stores/user.js'
import {useRouter} from "vue-router";
//部门列表
import {useDepartment} from "@/stores/department.js"
import {listEmployeeColumnValues} from "@/api/employee/work.js";

const departmentStore = useDepartment();
const {proxy} = getCurrentInstance();
const router = useRouter();
const userStore = useUser();
const user = userStore.getUser();
const contractStore = useContract();
const activeName = ref('first')
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
const dialogMoreVisible = ref(false);
//模糊查询条件
const currentPage = ref(1);//当前页
const pageSize = ref(5);//页码展示数量
const total = ref(10);//页码总数
const employeeId = ref('');//查询员工编号
const name = ref('');//员工姓名
const DepartmentId = ref('');//部门编号
const status = ref('');//审核状态

const handleClick = (tab, event) => {
  router.push('/console/employee/contract')
}
const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}
const EditRenewal = (row) => {
  if (row.state != '处理中') {
    ElMessage.warning("续约处理已结束，无需重复处理～")
    return;
  }
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  dialogUpdateVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
}
const handleMordChange = (row) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  dialogMoreVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
}
const clearFormData = () => {
  let clearData = {}
  state.formData = clearData;
  dialogFormVisible.value = false;
  dialogUpdateVisible.value = false;
  dialogMoreVisible.value = false;
}

const save = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      //发送后台请求
      state.formData.director = user.employeeVo.name;
      request.post('admin/employee/renewal/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('发起续约成功！')
        } else {
          ElMessage.warning(res.msg)
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
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
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
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.get('/admin/employee/renewal/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      name: name.value,
      employeeId: employeeId.value,
      departmentId: DepartmentId.value,
      status: status.value,
      userEmpId: user.employeeId,
      userRole: user.role
    }
  }).then(res => {
    try {
      if (res.code === 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
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
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
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
//获取部门列表
const selectDepartmentList = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.get('admin/department/list').then(res => {
    try {
      if (res.code === 200) {
        departmentStore.setDepartmentList(res.data);
      }
    } catch (e) {
      ElMessage.error(e);
    }
  })
}
const getEmpList = () => {
  listEmployeeColumnValues().then((res) => {
    state.empList = res.data;
  })
}
onMounted(() => {
  selectDepartmentList();
  getEmpList();
  load();
})

</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>