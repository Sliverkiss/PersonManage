<template>
  <div>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>员工考核</span>
        </span>
        </template>
        <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
          <div class="row">
            <div class="col-sm-12 col-md-12">
              <el-input v-model="title" style="width:180px" clearable
                        placeholder="请输入主题">
              </el-input>
              <el-input v-model="title" style="width:180px" clearable
                        placeholder="请输入状态">
              </el-input>
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
          <!--              <span>新增人员</span></el-button>-->
          <!--          </div>-->
        </div>
        <div class="row">
          <div class="col-sm-12 p-3 ">
            <el-table :data="state.tableData" stripe class="text-center" height="400" max-height="400"
                      element-loading-text="拼命加载中">
              <el-table-column label="标题" prop="assessTitle" align="center"
                               :width="flexWidth('assessTitle',state.tableData,'考核主题')"/>
              <el-table-column label="员工编号" prop="employeeId" align="center"/>
              <el-table-column label="姓名" prop="employeeName" align="center"/>
              <el-table-column label="所属部门" prop="deptName" align="center"/>
              <el-table-column label="自评总分" prop="score" align="center"/>
              <el-table-column label="审批总分" prop="approvalScore" align="center"/>
              <el-table-column label="状态" prop="status" align="center"
                               :width="flexWidth('status',state.tableData,'状态')">
                <template #default="scope">
                  <el-tag
                      :type="statusColorTag(scope.row.status)"
                      disable-transitions>{{ scope.row.status }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column fixed="right" align="center" label="操作" width="120" v-if="user.role">
                <template #default="scope">
                  <el-link type="primary" @click="Reviewed(scope.row)" v-if="(!user.role)&&scope.row.status=='未申报'">
                    申报
                  </el-link>
                  <el-link type="primary" @click="Approval(scope.row)"
                           v-else-if="user.role&&scope.row.status=='已申报，等待审批'">
                    审批
                  </el-link>
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
    <el-dialog v-model="dialogFormVisible" title="新增考核计划" align-center center class="" width="450"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="assessId" size="large" label="考核主题：" label-width="120">
              <el-select v-model="state.formData.assessId" style="width:250px;" placeholder="请选择审核状态"
                         clearable>
                <el-option :label="item.title" :value="item.id" v-for="(item,index) in state.assessSetList"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="assessId" size="large" label="考核部门：" label-width="120">
              <el-select v-model="state.formData.deptId" style="width:250px">
                <el-option
                    v-for="department in  state.departmentList"
                    :key="department.id"
                    :label="department.departmentName"
                    :value="department.id"
                />
              </el-select>
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
    <el-dialog v-model="dialogUpdateVisible" title="修改计划" align-center center class="" width="450"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-form-item prop="assessId" size="large" label="考核主题：" label-width="120">
            <el-select v-model="state.updateData.assessId" style="width:250px;" placeholder="请选择审核状态"
                       clearable>
              <el-option :label="item.title" :value="item.id" v-for="(item,index) in state.assessSetList"/>
            </el-select>
          </el-form-item>

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
import {getCurrentInstance, reactive, ref, toRaw} from "vue";
import {useContract} from "@/stores/employee.js";
import {ElMessage, ElNotification} from "element-plus";
import {flexWidth} from '@/utils/tableUtils.js'
import {useUser} from '@/stores/user.js'
//加载路由
import {useRouter} from "vue-router";

const useStore = useUser();
const user = useStore.getUser();
const router = useRouter();

const {proxy} = getCurrentInstance();
const userStore = useUser();
const contractStore = useContract();
const activeName = ref('first')


const state = reactive({
  tableData: [],
  formData: {},
  assessSetList: [],
  updateData: {},
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
const title = ref('');
const status = ref('');
//状态tag样式
const statusColorTag = (status) => {
  switch (status) {
    case '已申报，等待审批':
      return 'primary';
    case '未申报':
      return 'danger';
    case "审核通过":
      return 'success';
    case "审核未通过":
      return 'warning'
    default:
      return 'primary'
  }
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
  dialogUpdateVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
  console.log(toRaw(state.updateData))
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
      // state.formData.director = userStore.getUser().username
      //发送后台请求
      console.log(toRaw(state.formData))
      request.post('/admin/assess/staff/save', state.formData).then(res => {
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
  request.post('/admin/assess/set/update', state.updateData).then((res) => {
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

//个人绩效申报
const Reviewed = (row) => {
  router.push({
    path: '/assess/declare/review',
    query: {
      // employeeId:row.employeeId,
      assessId: row.assessId,
    }
  })
}
//绩效审批
const Approval = (row) => {
  router.push({
    path: '/assess/declare/approval',
    query: {
      declareId: row.id,
      employeeId: row.employeeId,
      assessId: row.assessId,
    }
  })
}
//
const load = () => {
  request.get('/admin/assess/declare/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      title: title.value,
      status: status.value,
      //数据隔离
      userRole: user.role,
      userEmpId: user.employeeId
    }
  }).then(res => {
    try {
      if (res.code === 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
        console.log(toRaw(state.tableData))
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
//考核计划列表
const getAssessList = () => {
  request.get('/admin/assess/set/title/list').then(res => {
    try {
      if (res.code === 200) {
        state.assessSetList = res.data
        console.log(toRaw(state.assessSetList));
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
//部门列表
const selectDepartmentList = () => {
  request.get('admin/department/list').then(res => {
    try {
      if (res.code === 200) {
        state.departmentList = res.data;
        console.log(toRaw(state.departmentList))
      }
    } catch (e) {
      ElMessage.error(e);
    }
  })
}

const Del = (id) => {
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
load()
getAssessList()
selectDepartmentList()
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>