<template>
  <div v-if="user.employeeVo.workState=='离职'">无权限</div>
  <div v-else>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>离职管理</span>
        </span>
          <!--显示消息数量提示-->
          <!--          <div class="oversee">-->
          <!--            <el-badge :value="state.tableData.length" :hidden="isHidden" :max="99" class="item">-->
          <!--            </el-badge>-->
          <!--          </div>-->
        </template>
        <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
          <div class="row">
            <div class="col-sm-12 col-md-12">
              <el-input v-model="employeeId" style="width:120px" placeholder="请输入员工编号"
                        v-if="user.role"></el-input>
              <el-input v-model="employeeName" style="width:100px;margin-left:10px" placeholder="请输入姓名"
                        v-if="user.role"></el-input>
              <el-select v-model="status" style="width:160px;margin-left:10px" placeholder="请选择审核状态"
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
          <div style="flex:1"></div>
          <div class="" style="width:120px" v-if="!user.role">
            <el-button type="success" plain @click="dialogFormVisible= true">
              <el-icon>
                <Plus/>
              </el-icon>
              <span>申请离职</span></el-button>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 p-3 ">
            <el-table :data="state.tableData" stripe class="text-center"
                      element-loading-text="拼命加载中">
              <el-table-column label="UID" prop="employeeId" align="center"/>
              <el-table-column label="姓名" prop="employeeName" align="center"/>
              <el-table-column label="部门" prop="departmentName" align="center"/>
              <el-table-column label="离职类型" prop="kind" align="center"/>
              <el-table-column label="离职原因" prop="reason" min-width="120"/>
              <el-table-column label="申请日期" prop="applyDate" sortable min-width="120"/>
              <!--              <el-table-column label="审核日期" prop="reviewDate" sortable min-width="120"/>-->
              <!--              <el-table-column v-if="user.role" label="部门意见" prop="departmentComment" min-width="120"/>-->
              <!--              <el-table-column label="审核人" prop="director"/>-->
              <el-table-column prop="state" label="审核状态" align="center" sortable width="110">
                <template #default="scope">
                  <el-tag
                      :type="scope.row.state === '通过' ? '' :scope.row.state ==='未通过'?'danger': 'warning'"
                      disable-transitions
                  >{{ scope.row.state }}
                  </el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column fixed="right" align="center" label="流程" width="80">
                <template #default="scope">
                  <el-link @click="handleMordChange(scope.row)" type="primary"> 查看详情</el-link>
                </template>
              </el-table-column>
              <el-table-column fixed="right" align="center" label="操作" width="120" v-if="user.role">
                <template #default="scope">
                  <div v-if="user.role">
                    <el-button size="small" style="background-color:#66b1ff" @click="EditRenewal(scope.row)"
                               v-if="scope.row.state== '审核中'&&user.role">
                      <el-icon>
                        <Edit style="color:#213d5b"/>
                      </el-icon>
                    </el-button>
                    <el-popconfirm @confirm="DelEntity(scope.row.id)" title="确认删除?"
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

  <!--  新增续约表单-->
  <div>
    <el-dialog v-model="dialogFormVisible" title="离职信息" align-center center class="" width="350"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-form-item prop="reason" size="large" label="离职原因：">
          <el-input v-model="state.formData.reason" type="textarea" placeholder="请输入离职原因"></el-input>
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
    <el-dialog v-model="dialogUpdateVisible" title="离职审核" align-center center class="" width="400"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
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

  <!--  审核详情-->
  <div>
    <el-dialog v-model="dialogMoreVisiblee" title="审核详情" align-center center class="" width="600"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules"
               label-position="top" ref="ruleFormRef">
        <el-timeline>
          <el-timeline-item icon="MoreFilled" type="primary" size="large" :timestamp="state.updateData.applyDate">
            提交审核
          </el-timeline-item>
          <el-timeline-item icon="MoreFilled" type="warning" size="large" :timestamp="state.updateData.applyDate">
            审核中
          </el-timeline-item>
          <el-timeline-item icon="close" type="danger" size="large" :timestamp="state.updateData.reviewDate"
                            v-if="state.updateData.state=='未通过'">
            未通过
            <p class="text-muted " style="font-size:13px">审核人:{{ state.updateData.director }}</p>
            <el-form-item prop="departmentComment" size="large" label="部门意见：">
              <el-input v-model="state.updateData.departmentComment"
                        :autosize="{ minRows: 4, maxRows: 8 }"
                        type="textarea" disabled></el-input>
            </el-form-item>

          </el-timeline-item>
          <el-timeline-item icon="check" type="success" size="large" :timestamp="state.updateData.reviewDate"
                            v-else-if="state.updateData.state=='通过'">
            通过
            <p class="text-muted " style="font-size:13px">审核人:{{ state.updateData.director }}</p>
            <el-form-item prop="departmentComment" size="large" label="部门意见：">
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
//请求方法
import request from "@/request.js";
import {getCurrentInstance, reactive, ref} from "vue";
//复职组件
//部门信息
import {useDepartment} from "@/stores/department.js"
//通知组件
import {ElMessage, ElNotification} from "element-plus";
//获取用户信息
import {useUser} from '@/stores/user.js'

const departmentStore = useDepartment();
const {proxy} = getCurrentInstance();
const userStore = useUser();
const user = userStore.getUser();
//头部面包屑
const activeName = ref('first')
//表单数据
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
//表单校验
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
const dialogMoreVisiblee = ref(false);
//模糊查询条件
const currentPage = ref(1);//当前页
const pageSize = ref(5);//页码展示数量
const total = ref(10);//页码总数
const employeeId = ref('');//查询员工编号
const employeeName = ref('');//员工姓名
const status = ref('');//审核状态
//分页
const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}
const handleMordChange = (row) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  dialogMoreVisiblee.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
}
//打开修改窗口
const EditRenewal = (row) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  dialogUpdateVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
}
//清除表单
const clearFormData = () => {
  let clearData = {}
  state.formData = clearData;
  dialogFormVisible.value = false;
  dialogUpdateVisible.value = false;
  dialogMoreVisiblee.value = false;
}
//保存
const save = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      state.formData.employeeId = user.employeeId;
      state.formData.kind = '辞职';
      //发送后台请求
      request.post('admin/department/resignation/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('申请离职成功，请等待管理员审核！')
        } else {
          ElMessage.warning(res.msg)
        }
        clearFormData();
        load();
      })
    } else {
      ElMessage.error('申请信息填写错误')
    }
  })
}
//修改
const update = () => {
  if (user.employeeId == row.employeeId) {
    ElMessage.warning("sorry,不允许审核自己~")
    return;
  }
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  //传入审核人参数
  state.updateData.director = user.employeeVo.name;
  request.put('admin/department/resignation/update', state.updateData).then((res) => {
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
//加载
const load = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.get('/admin/department/resignation/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      employeeName: employeeName.value,
      employeeId: employeeId.value,
      state: status.value,
      userEmpId: user.employeeId,
      userRole: user.role
    }
  }).then(res => {
    try {
      if (res.code === 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
        if (res.data.records.length == 0) {
          ElMessage.warning('查找结果不存在～');
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
//删除
const DelEntity = (id) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.delete('admin/department/resignation/delete/' + id).then((res) => {
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