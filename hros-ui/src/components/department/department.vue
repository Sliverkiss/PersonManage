<template>
  <div v-if="user.employeeVo.workState=='离职'">无权限</div>
  <div v-else>
    <div class="content" id="pjax-container">
      <div class="block block-rounded">
        <el-card class="notice-card ">
          <template #header>
            <div class="text-muted fw-bold " style="display:flex">
              <div style="width:200px">
                <UserFilled class="m-1 " width="20px"/>
                部门岗位管理
              </div>
              <div style="flex:1"></div>
              <div class="" style="width:120px" v-if="user.role">
                <el-button type="success" plain @click="dialogFormVisible= true">
                  <el-icon>
                    <Plus/>
                  </el-icon>
                  <span>新增部门</span></el-button>
              </div>
            </div>
          </template>
          <div class="block-content block-content-full">
            <div id="usersList_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
              <div class="row">
                <div class="col-sm-12 col-md-12">
                  <el-select v-model="departmentId" style="width:150px" clearable
                             placeholder="请选择部门名称">
                    <el-option
                        v-for="department in  state.departmentList"
                        :key="department.id"
                        :label="department.departmentName"
                        :value="department.id"
                    />
                  </el-select>
                  <!--                  <el-input v-model="manager    " style="width:160px;margin-left:10px"-->
                  <!--                            placeholder="请输入负责人" clearable></el-input>-->
                  <el-button type="primary" class="ms-2" @click="load">
                    <el-icon>
                      <Search/>
                    </el-icon>
                    搜索
                  </el-button>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 p-3">
                  <div class="demo-collapse">
                    <el-collapse v-model="activeNames" @change="handleChange">
                      <el-collapse-item v-for="department in state.tableData"
                                        :title="department.departmentName" :name="department.id">
                        <div>
                          <el-descriptions
                              class="margin-top"
                              :title="department.departmentName"
                              :column="3"
                              :size="size"
                              border
                          >
                            <template #extra v-if="user.role">
                              <el-button size="small" style="background-color:#66b1ff"
                                         @click="EditDepartment(department)">
                                <el-icon>
                                  <Edit style="color:#213d5b"/>
                                </el-icon>
                              </el-button>
                              <el-popconfirm @confirm="DeleteEntity(department.id)" title="确认删除?"
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
                            <el-descriptions-item>
                              <template #label>
                                <div class="cell-item">
                                  <el-icon>
                                    <user/>
                                  </el-icon>
                                  负责人
                                </div>
                              </template>
                              {{ department.manager }}
                            </el-descriptions-item>
                            <el-descriptions-item>
                              <template #label>
                                <div class="cell-item">
                                  <el-icon>
                                    <iphone/>
                                  </el-icon>
                                  联系电话
                                </div>
                              </template>
                              {{ department.phone }}
                            </el-descriptions-item>
                            <el-descriptions-item>
                              <template #label>
                                <div class="cell-item">
                                  <el-icon>
                                    <location/>
                                  </el-icon>
                                  部门位置
                                </div>
                              </template>
                              {{ department.location || '无' }}
                            </el-descriptions-item>
                            <el-descriptions-item>
                              <template #label>
                                <div class="cell-item">
                                  <el-icon :style="iconStyle">
                                    <tickets/>
                                  </el-icon>
                                  上级部门
                                </div>
                              </template>
                              {{ department?.parentDepartment?.departmentName || '无' }}
                            </el-descriptions-item>
                            <el-descriptions-item :span="2">
                              <template #label>
                                <div class="cell-item">
                                  <el-icon :style="iconStyle">
                                    <office-building/>
                                  </el-icon>
                                  部门岗位
                                </div>
                              </template>
                              <div v-show="department?.postList">
                                <el-tag v-for="post in department?.postList" class="me-2">
                                  {{ post.name }}
                                </el-tag>
                              </div>
                              <div v-show="!department?.postList">无</div>
                            </el-descriptions-item>
                            <el-descriptions-item>
                              <template #label>
                                <div class="cell-item">
                                  <el-icon>
                                    <office-building/>
                                  </el-icon>
                                  部门描述
                                </div>
                              </template>
                              <div class="" v-for="item in department.contect.split(`&`)">
                                {{ item }}
                              </div>
                            </el-descriptions-item>
                          </el-descriptions>
                        </div>

                      </el-collapse-item>
                    </el-collapse>
                    <!--                    <el-collapse v-model="state.tableData" accordion>-->
                    <!--                      <el-collapse-item v-for="(item,index) in state.tableData" title="Feedback" :name="activeName">-->
                    <!--                      </el-collapse-item>-->
                    <!--                    </el-collapse>-->
                  </div>
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
  </div>
  <!--  新增薪资表单-->
  <div>
    <el-dialog v-model="dialogFormVisible" title="新增部门" align-center center class="" width="450"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="departmentName" size="large" label="部门名称：">
              <el-input v-model="state.formData.departmentName" placeholder="请输入部门名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="parentId" size="large" label="上级部门：" label-width="90">
              <el-select v-model="state.formData.parentId" placeholder="请选择上级部门" style="width:2250px"
                         clearable>
                <el-option value="" label="无"></el-option>
                <el-option
                    v-for="department in departmentStore.departmentList"
                    :key="department.id"
                    :label="department.departmentName"
                    :value="department.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="location" size="large" label="部门位置：" label-width="90">
              <el-input v-model="state.formData.location" type="text" placeholder="请输入部门位置"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="phone" size="large" label="联系方式：">
              <el-input v-model="state.formData.phone" type="text"
                        placeholder="请输入部门联系方式"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="manager" size="large" label="负责人：" label-width="90">
              <el-select v-model="state.formData.manager" style="width:320px" placeholder="请输入或选择部门负责人"
                         clearable filterable>
                <el-option v-for="item in state.empList" :label="item.id+' '+item.personal.name"
                           :value="item.personal.name"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="departmentComment" size="large" label="部门描述：" label-width="90">
              <el-input v-model="state.formData.contect" type="textarea"
                        placeholder="请输入部门描述"></el-input>
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
  </div>
  <!--  修改薪资表单-->
  <div>
    <el-dialog v-model="dialogUpdateVisible" title="修改部门" align-center center class="" width="450"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="departmentName" size="large" label="部门名称：">
              <el-input v-model="state.updateData.departmentName" placeholder="请输入部门名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="parentId" size="large" label="上级部门：" label-width="90">
              <el-select v-model="state.updateData.parentId" placeholder="请选择上级部门" style="width:2250px"
                         clearable>
                <el-option value="" label="无"></el-option>
                <el-option
                    v-for="department in departmentStore.departmentList"
                    :key="department.id"
                    :label="department.departmentName"
                    :value="department.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="location" size="large" label="部门位置：" label-width="90">
              <el-input v-model="state.updateData.location" type="text" placeholder="请输入部门位置"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="phone" size="large" label="联系方式：">
              <el-input v-model="state.updateData.phone" type="text"
                        placeholder="请输入部门联系方式"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="manager" size="large" label="负责人：" label-width="90">
              <el-select v-model="state.updateData.manager" style="width:320px" placeholder="请输入或选择部门负责人"
                         clearable filterable>
                <el-option v-for="item in state.empList" :label="item.id+' '+item.personal.name"
                           :value="item.personal.name"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="departmentComment" size="large" label="部门描述：" label-width="90">
              <el-input v-model="state.updateData.contect" type="textarea"
                        placeholder="请输入部门描述"></el-input>
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
</template>

<script setup>
import {computed, getCurrentInstance, onMounted, reactive, ref, toRaw} from 'vue'
import request from "@/request.js";
import {ElMessage, ElNotification} from "element-plus";
import {useDepartment} from "@/stores/department.js"
import {useUser} from '@/stores/user.js'
import {listEmployeeColumnValues} from "@/api/employee/work.js";

const useStore = useUser();
const user = useStore.getUser();
const {proxy} = getCurrentInstance();
const departmentStore = useDepartment();
const activeNames = ref('1')
const size = ref('')

//模糊查询条件
const currentPage = ref(1);//当前页
const pageSize = ref(10);//页码展示数量
const total = ref(10);//页码总数
const departmentId = ref('');//部门编号
const manager = ref('');//部门负责人


const blockMargin = computed(() => {
  const marginMap = {
    large: '32px',
    default: '28px',
    small: '24px',
  }
  return {
    marginTop: marginMap[size.value] || marginMap.default,
  }
})
const handleChange = (val) => {
  console.log(val)
}

// const getDepartmenteById=(id)=> {
//   const department=toRaw(state.tableData).
//   return user ? user.name : 'Unknown';
const state = reactive({
  tableData: [],
  formData: {},
  updateData: {},
  departmentList: [],
  employeeList: [],
})
const lodingy = ref(false);

const rules = reactive({
  departmentName: [
    {required: true, message: '请输入部门名称', trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '请输入部门名称', trigger: 'blur'}
  ],
  manager: [
    {required: true, message: '请输入负责人名称', trigger: 'blur'}
  ]
});
//打开视图
const dialogFormVisible = ref(false)
const dialogUpdateVisible = ref(false)
//换页
const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}

const EditDepartment = (row) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
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
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      //发送后台请求
      request.post('admin/department/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('新增成功！')
        } else {
          ElMessage.error('系统服务异常，请稍后再试~')
        }
        clearFormData();
        load();
        selectDepartmentList();
      })
    } else {
      ElMessage.error('薪资信息填写错误')
    }
  })
}
const update = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.put('admin/department/update', state.updateData).then((res) => {
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
      selectDepartmentList();
    }
  })
}
const DeleteEntity = (id) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.delete('admin/department/delete/' + id).then((res) => {
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
      selectDepartmentList();
    }
  })
}
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

const getEmployeeList = () => {
  listEmployeeColumnValues().then(res => {
    state.employeeList = res.data;
    console.log(toRaw(state.employeeList))
  })
}

const load = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  request.get('/admin/department/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      departmentId: departmentId.value,
      manager: manager.value,
    }
  }).then((res) => {
    try {
      if (res.code == 200) {
        total.value = res.data.total - 0;
        state.tableData = res.data?.records
        console.log(toRaw(state.tableData))
      } else {
        total.value = res.data.total - 0;
        ElMessage.error(res.msg)
      }
    } catch (e) {
      ElMessage.error(e)
    }
  })
}
const getEmpList = () => {
  listEmployeeColumnValues().then((res) => {
    state.empList = res.data;
  })
}

onMounted(() => {
  load();
  getEmployeeList()
  getEmpList();
//数据初始化
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