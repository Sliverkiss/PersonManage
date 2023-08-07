<template>
  <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
    <el-tab-pane name="first">
      <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>调岗审核</span>
        </span>
        <!--显示消息数量提示-->
        <div class="oversee">
          <el-badge :value="state.tableData.length" :hidden="isHidden" :max="99" class="item">
          </el-badge>
        </div>
      </template>
      <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
        <div class="row">
          <div class="col-sm-12 col-md-12">
            <el-input v-model="employeeId" style="width:120px" placeholder="请输入员工编号" clearable></el-input>
            <!--            <el-input v-model="employeeName" style="width:120px;margin-left:10px" placeholder="请输入员工姓名"-->
            <!--                      clearable></el-input>-->
            <el-button type="primary" class="ms-2" @click="load">
              <el-icon>
                <Search/>
              </el-icon>
              搜索
            </el-button>
          </div>
        </div>
        <div style="flex:1"></div>
        <div class="" style="width:120px">
          <el-button type="primary" plain @click="dialogFormVisible= true">
            <el-icon>
              <Plus/>
            </el-icon>
            <span>新增调岗</span></el-button>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 p-3 ">
          <el-auto-resizer>
            <el-table :data="state.tableData" stripe
                      class="text-center">
              <template v-for="(col,index) in departmentStore.transferMap" :key="index">
                <el-table-column :prop="col.key" :label="col.value" align="center"
                                 :width="flexWidth(col.key,state.tableData,col.value)"
                ></el-table-column>
              </template>
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
              <el-table-column fixed="right" align="center" label="操作" width="120">
                <template #default="scope">
                  <el-button size="small" style="background-color:#66b1ff" @click="EditEntity(scope.row)">
                    <el-icon>
                      <Edit style="color:#213d5b"/>
                    </el-icon>
                  </el-button>
                  <el-popconfirm @confirm="DeleteEntity(scope.row.id)" title="确认删除?"
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
    </el-tab-pane>
    <el-tab-pane label="调岗记录" name="second">
      <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
        <div class="row">
          <div class="col-sm-12 col-md-12">
            <el-input v-model="secEmployeeId" style="width:120px" placeholder="请输入员工编号" clearable></el-input>
            <!--            <el-input v-model="employeeName" style="width:120px;margin-left:10px" placeholder="请输入员工姓名"-->
            <!--                      clearable></el-input>-->
            <el-button type="primary" class="ms-2" @click="secload">
              <el-icon>
                <Search/>
              </el-icon>
              搜索
            </el-button>
          </div>
        </div>
        <div style="flex:1"></div>
        <div class="" style="width:120px">
          <el-button type="primary" plain @click="dialogFormVisible= true">
            <el-icon>
              <Plus/>
            </el-icon>
            <span>新增调岗</span></el-button>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 p-3 ">
          <el-auto-resizer>
            <el-table :data="state.secTableData" stripe
                      class="text-center">
              <template v-for="(col,index) in departmentStore.transferMap" :key="index">
                <el-table-column :prop="col.key" :label="col.value" align="center"
                                 :width="flexWidth(col.key,state.secTableData,col.value)"
                ></el-table-column>
              </template>
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
              <el-table-column fixed="right" align="center" label="操作" width="120">
                <template #default="scope">
                  <!--                  <el-button size="small" style="background-color:#66b1ff" @click="EditEntity(scope.row)">-->
                  <!--                    <el-icon>-->
                  <!--                      <Edit style="color:#213d5b"/>-->
                  <!--                    </el-icon>-->
                  <!--                  </el-button>-->
                  <el-popconfirm @confirm="DeleteEntity(scope.row.id)" title="确认删除?"
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
              class="text-muted ">共有 {{ secTotal }} 条 / {{ secCurrentPage }} 页</span></div>
        </div>
        <div style="flex:1"></div>
        <div>
          <el-pagination
              background
              :page-sizes="[1,10,20,30]"
              layout="prev,pager,next"
              v-model::current-page="secCurrentPage"
              v-model:page-size="secPageSize"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :total="secTotal">
          </el-pagination>
        </div>
      </div>
    </el-tab-pane>
  </el-tabs>
  <!--  新增调岗表单-->
  <div>
    <el-dialog v-model="dialogFormVisible" title="新增调岗" align-center center class="" width="720"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item prop="employeeId" size="large" label="员工编号：" label-width="120">
              <el-input v-model="state.formData.employeeId" placeholder="请输入员工编号"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="afterDepartment" size="large" label="调入部门：" label-width="100">
              <el-select v-model="state.formData.afterDepartment" placeholder="请选择调入部门" style="width:2250px"
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
          <el-col :span="12">
            <el-form-item prop="transferType" size="large" label="调动类型：" label-width="120">
              <el-select v-model="state.formData.transferType" placeholder="请选择调动类型" style="width:2250px"
                         clearable>
                <el-option value="" label="无"></el-option>
                <el-option value="个人申请" label="个人申请"></el-option>
                <el-option value="调岗" label="调岗"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="kind" size="large" label="调动种类：" label-width="100">
              <el-select v-model="state.formData.kind" placeholder="请选择调动种类" style="width:2250px"
                         clearable>
                <el-option value="升职" label="升职"></el-option>
                <el-option value="降职" label="降职"></el-option>
                <el-option value="调职" label="调职"></el-option>
                <el-option value="复职" label="复职"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="transferPost" size="large" label="调动岗位：" label-width="120">
              <el-input v-model="state.formData.transferPost" placeholder="请输入调动岗位"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="state" size="large" label="审核结果：" label-width="100">
              <el-select v-model="state.formData.state" placeholder="请选择审核结果" style="width:2250px" clearable>
                <el-option label="通过" value="通过"/>
                <el-option label="审核中" value="审核中"/>
                <el-option label="未通过" value="未通过"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="reason" size="large" label="调动理由：" label-width="120">
              <el-input v-model="state.formData.reason" type="textarea" clearable
                        placeholder="请输入调动理由"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="beforeComment" size="large" label="调出部门意见：" label-width="120">
              <el-input v-model="state.formData.beforeComment" type="textarea" clearable
                        placeholder="请输入调出部门意见"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="afterComment" size="large" label="调入部门意见：" label-width="120">
              <el-input v-model="state.formData.afterComment" type="textarea" clearable
                        placeholder="请输入调入部门意见"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="personalComment" size="large" label="人事处意见：" label-width="120">
              <el-input v-model="state.formData.personalComment" type="textarea" clearable
                        placeholder="请输入人事处意见"></el-input>
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
  <!--  修改调岗表单-->
  <div>
    <el-dialog v-model="dialogUpdateVisible" title="调岗审核" align-center center class="" width="750"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item prop="employeeId" size="large" label="员工编号：" label-width="120">
              <el-input v-model="state.updateData.employeeId" placeholder="请输入员工编号" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="afterDepartment" size="large" label="调入部门：" label-width="100">
              <el-select v-model="state.updateData.afterDepartment" placeholder="请选择调入部门" style="width:2250px"
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
          <el-col :span="12">
            <el-form-item prop="transferType" size="large" label="调动类型：" label-width="120">
              <el-select v-model="state.updateData.transferType" placeholder="请选择调动类型" style="width:2250px"
                         clearable>
                <el-option value="" label="无"></el-option>
                <el-option value="个人申请" label="个人申请"></el-option>
                <el-option value="调岗" label="调岗"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="kind" size="large" label="调动种类：" label-width="100">
              <el-select v-model="state.updateData.kind" placeholder="请选择调动种类" style="width:2250px"
                         clearable>
                <el-option value="升职" label="升职"></el-option>
                <el-option value="降职" label="降职"></el-option>
                <el-option value="调职" label="调职"></el-option>
                <el-option value="复职" label="复职"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="transferPost" size="large" label="调动岗位：" label-width="120">
              <el-input v-model="state.updateData.transferPost" placeholder="请输入调动岗位"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="state" size="large" label="审核结果：" label-width="100">
              <el-select v-model="state.updateData.state" placeholder="请选择审核结果" style="width:2250px" clearable>
                <el-option label="通过" value="通过"/>
                <el-option label="审核中" value="审核中"/>
                <el-option label="未通过" value="未通过"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="reason" size="large" label="调动理由：" label-width="120">
              <el-input v-model="state.updateData.reason" type="textarea" clearable
                        placeholder="请输入调动理由"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="beforeComment" size="large" label="调出部门意见：" label-width="120">
              <el-input v-model="state.updateData.beforeComment" type="textarea" clearable
                        placeholder="请输入调出部门意见"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="afterComment" size="large" label="调入部门意见：" label-width="120">
              <el-input v-model="state.updateData.afterComment" type="textarea" clearable
                        placeholder="请输入调入部门意见"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="personalComment" size="large" label="人事处意见：" label-width="120">
              <el-input v-model="state.updateData.personalComment" type="textarea" clearable
                        placeholder="请输入人事处意见"></el-input>
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
import {getCurrentInstance, reactive, ref, toRaw} from 'vue'
import {useDepartment} from "@/stores/department.js"
import request from "@/request.js";
import {ElMessage, ElNotification} from "element-plus";
import {flexWidth} from '@/utils/tableUtils.js'

const {proxy} = getCurrentInstance();
const departmentStore = useDepartment();
const activeName = ref('first')

const state = reactive({
  tableData: [],
  secTableData: [],
  formData: {},
  updateData: {},
})

const rules = reactive({
  employeeId: [
    {required: true, message: '请输入员工编号', trigger: 'blur'},
  ],
  afterDepartment: [
    {required: true, message: '请选择调入部门', trigger: 'blur'},
  ],
  kind: [
    {required: true, message: '请选择调动类型', trigger: 'blur'},
  ],
  transferType: [
    {required: true, message: '请选择调动种类', trigger: 'blur'},
  ],
  state: [
    {required: true, message: '请选择审核结果', trigger: 'blur'},
  ]
})

//通用参数模块区域
//模糊查询条件
const currentPage = ref(1);//当前页
const pageSize = ref(5);//页码展示数量
const total = ref(10);//页码总数
const secCurrentPage = ref(1);//当前页
const secPageSize = ref(5);//页码展示数量
const secTotal = ref(10);//页码总数
const employeeId = ref('');//员工编号
const secEmployeeId = ref('');//员工编号
const employeeName = ref('');//员工姓名
const status = ref('审核中');
//打开视图
const dialogFormVisible = ref(false)
const dialogUpdateVisible = ref(false)
const EditEntity = (row) => {
  dialogUpdateVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
}
//换页
const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
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
      //发送后台请求
      request.post('admin/department/transfer/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('新增调岗成功！')
        } else {
          ElMessage.error(res.msg)
        }
        clearFormData();
        load();
        secoad();
        selectDepartmentList();
      })
    } else {
      ElMessage.error('调岗信息填写错误')
    }
  })
}
const update = () => {
  request.put('admin/department/transfer/update', state.updateData).then((res) => {
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
      secoad();
    }
  })
}
const DeleteEntity = (id) => {
  request.delete('admin/department/transfer/delete/' + id).then((res) => {
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
const load = () => {
  request.get('/admin/department/transfer/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      employeeId: employeeId.value,
      employeeName: employeeName.value,
      state: status.value
    }
  }).then((res) => {
    try {
      if (res.code == 200) {
        state.tableData = res.data?.records
        total.value = res.data.total - 0;
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
const secload = () => {
  request.get('/admin/department/transfer/page', {
    params: {
      currentPage: secCurrentPage.value,
      pageSize: secPageSize.value,
      employeeId: secEmployeeId.value,
      employeeName: employeeName.value,
      notState: status.value
    }
  }).then((res) => {
    try {
      if (res.code == 200) {
        state.secTableData = res.data?.records
        secTotal.value = res.data.total - 0;
        console.log(toRaw(state.secTableData))
      } else {
        secTotal.value = res.data.total - 0;
        ElMessage.error(res.msg)
      }
    } catch (e) {
      ElMessage.error(e)
    }
  })
}
//获取部门列表
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

load()
secload()
selectDepartmentList();
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}

.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}

.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}
</style>