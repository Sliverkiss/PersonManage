<template>
  <div v-if="user.employeeVo.workState=='离职'">无权限</div>
  <div v-else>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>岗位信息管理</span>
        </span>
        </template>
        <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
          <div class="row">
            <div class="col-sm-12 col-md-12">
              <el-input v-model="name" style="width:180px;margin-right:10px" clearable
                        placeholder="请输入岗位名称">
              </el-input>
              <el-select v-model="DepartmentId" style="width:120px;margin-left:10px" placeholder="请选择部门"
                         clearable>
                <el-option
                    v-for="department in state.departmentList"
                    :key="department.id"
                    :label="department.departmentName"
                    :value="department.id"
                />
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
            <el-button type="success" plain @click="handleAdd">
              <el-icon>
                <Plus/>
              </el-icon>
              <span>新增岗位</span></el-button>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 p-3 ">
            <el-table :data="state.tableData" stripe class="text-center" height="400" max-height="400"
                      element-loading-text="拼命加载中">
              <el-table-column label="id" prop="id" align="center"/>
              <el-table-column label="岗位名称" prop="name" align="center"/>
              <el-table-column label="所属部门" prop="department.departmentName" align="center"/>
              <el-table-column label="在岗员工" prop="department.departmentName" align="center">
                <template #default="scope" v-if="user.role">
                  <el-link @click="openEmpListView(scope.row.employeeList)">查看内容</el-link>
                </template>
              </el-table-column>
              <el-table-column fixed="right" align="center" label="操作" width="120">
                <template #default="scope" v-if="user.role">
                  <el-button size="small" style="background-color:#66b1ff" @click="handleUpdate(scope.row)">
                    <el-icon>
                      <Edit style="color:#213d5b"/>
                    </el-icon>
                  </el-button>
                  <el-popconfirm @confirm="handleDelete(scope.row.id)" title="确认删除?"
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
                <template #default="scope" v-else>
                  <el-popconfirm @confirm="signUp(scope.row)" title="确认报名?"
                                 confirm-button-text="确认"
                                 cancel-button-text="取消">
                    <template #reference>
                      <el-link size="small" type="warning">
                        报名
                      </el-link>
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

  <div>
    <el-dialog v-model="open" :title="title" align-center center class="" width="400"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="name" size="large" label="岗位：" label-width="100">
              <el-input v-model="state.formData.name" placeholder="请输入岗位名称" style="width:205px"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="DepartmentId" size="large" label="部门：" label-width="100">
              <el-select v-model="state.formData.departmentId" placeholder="请选择部门"
                         clearable>
                <el-option
                    v-for="department in state.departmentList"
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
        <el-button @click="submitForm" type="primary">添加</el-button>
        <el-button @click="cancel" type="primary">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <!--  在岗员工列表-->
  <div>
    <el-dialog v-model="empListView" title="在岗员工列表" align-center center class="" width="500"
               style="border-radius: 0.875rem 1rem;">
      <el-table :data="state.empByPostList" style="width: 100%" max-height="250">
        <el-table-column prop="id" label="UID"/>
        <el-table-column prop="personal.name" label="名称"/>
        <el-table-column prop="department.departmentName" label="部门"/>
      </el-table>
      <!--      <template #footer>-->
      <!--      <span class="dialog-footer">-->
      <!--        <el-button @click="submitForm" type="primary">确定</el-button>-->
      <!--        <el-button @click="cancel" type="primary">-->
      <!--          取消-->
      <!--        </el-button>-->
      <!--      </span>-->
      <!--      </template>-->
    </el-dialog>
  </div>
</template>

<script setup>
import request from "@/request.js";
import {getCurrentInstance, onMounted, reactive, ref, toRaw} from "vue";
import {ElMessage, ElNotification} from "element-plus";
//接口api
import {addPost, delPost, updatePost} from "@/api/department/post.js";
//引入用户
import {useUser} from '@/stores/user.js'

const useStore = useUser();
const user = useStore.getUser();
//侧边栏活动标志
const activeName = ref('first')
//vue对象
const {proxy} = getCurrentInstance();
//数据
const state = reactive({
  tableData: [],
  empByPostList: [],
  formData: {},
  updateData: {},
  departmentList: [],
})
//数据校验规则
const rules = reactive({})
//打开新增续约视图
const open = ref(false)
//查看在岗员工列表
const empListView = ref(false)
const openEmpListView = (row) => {
  empListView.value = true;
  state.empByPostList = row;
  console.log(toRaw(state.empByPostList))
}
//弹出窗口标题
const title = ref('');
//分页
const currentPage = ref(1);//当前页
const pageSize = ref(15);//页码展示数量
const total = ref(10);//页码总数
const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}
//模糊查询条件
const name = ref('');
const DepartmentId = ref('');
//初始化加载数据
const load = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.get('/admin/department/post/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      name: name.value,
      departmentId: DepartmentId.value
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
//取消按钮
const cancel = () => {
  open.value = false;
  reset();
}
//表单重置
const reset = () => {
  let clearData = {}
  state.formData = clearData;
}
//新增按钮操作
const handleAdd = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  reset();
  open.value = true
  title.value = "添加岗位"
}
//修改按钮操作
const handleUpdate = (row) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  reset();
  open.value = true
  title.value = "修改岗位"
  state.formData = JSON.parse(JSON.stringify(row));
}
//提交按钮
const submitForm = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      //id不为空则进行修改
      if (state.formData.id != null) {
        updatePost(state.formData).then((res) => {
          res.code == 200 ? ElNotification.success(res.msg) : ElMessage.error(res.msg);
          load();
        });
      } else {
        //否则进行新增
        addPost(state.formData).then((res) => {
          res.code == 200 ? ElNotification.success(res.msg) : ElMessage.error(res.msg);
          load();
        });
      }
      open.value = false;
    } else {
      ElMessage.error('信息填写错误')
    }
  })
}
//删除按钮操作
const handleDelete = (id) => {
  delPost(id).then((res) => {
    res.code == 200 ? ElNotification.success(res.msg) : ElMessage.error(res.msg);
    load();
  });
}
//获取部门列表
const selectDepartmentList = () => {
  request.get('admin/department/list').then(res => {
    try {
      if (res.code === 200) {
        state.departmentList = res.data;
      }
    } catch (e) {
      ElMessage.error(e);
    }
  })
}
//生命周期钩子
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

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>