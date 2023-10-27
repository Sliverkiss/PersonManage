<template>
  <div v-if="user.employeeVo.workState=='离职'">无权限</div>
  <div v-else>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>

        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>员工培训管理</span>
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
              <el-input v-model="planName" style="width:180px" clearable
                        placeholder="请输入培训计划名称">
              </el-input>
              <!--              <el-input v-model="employeeId" style="width:140px;margin-left: 10px" placeholder="请输入员工编号"-->
              <!--                        v-if="user.role"-->
              <!--                        clearable></el-input>-->
              <el-select v-model="planState" style="width:150px;margin-left: 10px" placeholder="请选择培训状态"
                         clearable>
                <el-option label="已报名" value="已报名"/>
                <el-option label="进行中" value="进行中"/>
                <el-option label="已结束" value="已结束"/>
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
          <!--          <div class="" style="width:120px">-->
          <!--            <el-button type="success" plain @click="dialogFormVisible= true" v-if="user.role">-->
          <!--              <el-icon>-->
          <!--                <Plus/>-->
          <!--              </el-icon>-->
          <!--              <span>新增培训</span></el-button>-->
          <!--          </div>-->
        </div>
        <div class="row">
          <div class="col-sm-12 p-3 ">
            <el-table :data="state.tableData" stripe class="text-center" height="400" max-height="400"
                      element-loading-text="拼命加载中">
              <el-table-column v-for="(col,index) in state.planTitleMap" :key="index"
                               :prop="col.key" :label="col.value" align="center"
                               :width="flexWidth(col.key,state.tableData,col.value)"></el-table-column>
              <el-table-column prop="workState" label="培训结果" align="center">
                <template #default="scope">
                  <el-tag
                      v-show="scope.row.level"
                      :type="scope.row.level === '优秀' ? '' :'合格'?'warning': 'danger'"
                      disable-transitions
                  >{{ scope.row.level }}
                  </el-tag
                  >
                </template>
              </el-table-column>
              <el-table-column label="培训状态" prop="planState" align="center"/>
              <el-table-column fixed="right" align="center" label="操作" width="120" v-if="user.role">
                <template #default="scope">
                  <el-button size="small" style="background-color:#66b1ff" @click="EditRenewal(scope.row)">
                    <el-icon>
                      <Edit style="color:#213d5b"/>
                    </el-icon>
                  </el-button>
                  <el-popconfirm @confirm="Del(scope.row.id)" title="确认删除?"
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

  <!--  新增-->
  <div>
    <el-dialog v-model="dialogFormVisible" title="新增培训" align-center center class="" width="430"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="planId" size="large" label="培训计划编号：" label-width="130">
              <el-input v-model="state.formData.planId" placeholder="请输入培训计划编号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="employeeId" size="large" label="员工编号：" label-width="130">
              <el-input v-model="state.formData.employeeId" placeholder="请输入员工编号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="score" size="large" label="成绩：" label-width="130">
              <el-input v-model.number="state.formData.score" placeholder="请输入培训成绩"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="save" type="primary">添加</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <!--  审核表单-->
  <div>
    <el-dialog v-model="dialogUpdateVisible" title="录入成绩" align-center center class="" width="400"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="planId" size="large" label="培训计划编号：" label-width="130">
              <el-input v-model="state.updateData.planId" placeholder="请输入培训计划编号" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="employeeId" size="large" label="员工编号：" label-width="130">
              <el-input v-model="state.updateData.employeeId" placeholder="请输入员工编号" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="score" size="large" label="成绩：" label-width="130">
              <el-input v-model.number="state.updateData.score" placeholder="请输入培训成绩"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

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
import request from "@/request.js";
import {getCurrentInstance, onMounted, reactive, ref} from "vue";
import {useContract} from "@/stores/employee.js";
import {useDepartment} from "@/stores/department.js"
import {ElMessage, ElNotification} from "element-plus";
import {flexWidth} from '@/utils/tableUtils.js'
import {useUser} from '@/stores/user.js'

const useStore = useUser();
const user = useStore.getUser();
const {proxy} = getCurrentInstance();
const userStore = useUser();
const contractStore = useContract();
const departmentStore = useDepartment();
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
  planTitleMap: [
    {
      key: 'id',
      value: '编号'
    }, {
      key: 'planName',
      value: '培训计划名称'
    }, {
      key: 'employeeId',
      value: '员工编号'
    }, {
      key: 'employeeName',
      value: '姓名'
    }, {
      key: 'score',
      value: '成绩'
    }
  ]
})

const rules = reactive({
  planId: [
    {required: true, message: '请输入培训计划编号', trigger: 'blur'},
  ],
  employeeId: [
    {required: true, message: '请输入教练名称', trigger: 'blur'},
  ]
})
//打开新增续约视图
const dialogFormVisible = ref(false)
const dialogUpdateVisible = ref(false)
//模糊查询条件
const currentPage = ref(1);//当前页
const pageSize = ref(5);//页码展示数量
const total = ref(10);//页码总数
const planName = ref('');
const planState = ref('');
const employeeId = ref('');

const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}
const EditRenewal = (row) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  if (row.planState != '已结束') {
    ElMessage.warning("该培训未结束，无需录入成绩～")
  } else {
    dialogUpdateVisible.value = true;
    state.updateData = JSON.parse(JSON.stringify(row));
  }
}
const clearFormData = () => {
  let clearData = {}
  state.formData = clearData;
  dialogFormVisible.value = false;
  dialogUpdateVisible.value = false;
}

const save = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      state.formData.director = userStore.getUser().username
      //发送后台请求
      request.post('admin/training/record/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('添加成功！')
        } else {
          ElMessage.error('系统服务异常，请稍后再试~')
        }
        clearFormData();
        load();
      })
    } else {
      ElMessage.error('添加信息填写错误')
    }
  })
}
//修改员工资料
const update = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.put('admin/training/record/update', state.updateData).then((res) => {
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

const load = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.get('/admin/training/record/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      planName: planName.value,
      planState: planState.value,
      employeeId: user.employeeId,
      userRole: user.role
    }
  }).then(res => {
    try {
      if (res.code === 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
        if (res.data.records.length == 0) {
          ElMessage.warning('查询结果为空～');
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

const Del = (id) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.delete('admin/training/record/delete/' + id).then((res) => {
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
const selectDepartmentList = () => {
  request.get('admin/department/list').then(res => {
    try {
      if (res.code === 200) {
        departmentStore.setDepartmentList(res.data)
      }
    } catch (e) {
      ElMessage.error(e);
    }
  })
}

onMounted(() => {
  load();
  selectDepartmentList();
})
</script>
<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>