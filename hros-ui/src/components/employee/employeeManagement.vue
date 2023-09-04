<template>
  <div class="">
    <div class="content" id="pjax-container">
      <div class="block block-rounded">
        <el-card class="notice-card ">
          <template #header>
            <div class="text-muted fw-bold " style="display:flex">
              <div style="width:200px">
                <UserFilled class="m-1 " width="20px"/>
                员工资料管理
              </div>
              <div style="flex:1"></div>
              <div class="" style="width:120px" v-if="user.role">
                <el-button type="success" plain @click="dialogFormVisible= true">
                  <el-icon>
                    <Plus/>
                  </el-icon>
                  <span>入职登记</span></el-button>
              </div>
              <div class="" style="width:120px" v-if="user.role">
                <el-button type="success" plain @click="downloadList">
                  <el-icon>
                    <Document/>
                  </el-icon>
                  <span>导出数据</span></el-button>
              </div>
            </div>
          </template>
          <div class="block-content block-content-full">
            <div id="usersList_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
              <div v-if="user.role" class="row">
                <div class="col-sm-12 col-md-12">
                  <el-input v-model="employeeId" style="width:120px" placeholder="请输入员工编号" clearable></el-input>
                  <el-input v-model="name" style="width:120px;margin-left:10px" placeholder="请输入姓名"
                            clearable></el-input>
                  <el-select v-model="DepartmentId" style="width:120px;margin-left:10px" placeholder="请选择部门"
                             clearable>
                    <el-option
                        v-for="department in departmentStore.departmentList"
                        :key="department.id"
                        :label="department.departmentName"
                        :value="department.id"
                    />
                  </el-select>
                  <el-input v-model="post" style="width:120px;margin-left:10px" placeholder="请输入岗位"
                            clearable></el-input>
                  <!--                  <el-input v-model="hireDate" style="width:120px;margin-left:10px" placeholder="请输入入职日期"-->
                  <!--                            clearable></el-input>-->
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
                  <el-table :data="state.tableData" stripe class="text-center" height="400" max-height="400">
                    <template v-for="(col,index) in toRaw(employeeStore.employeeMap)" :key="index">
                      <el-table-column :prop="col.key" :label="col.value" align="center"
                                       :width="flexWidth(col.key,state.tableData,col.value)"></el-table-column>
                    </template>
                    <el-table-column prop="workState" label="在职状态" align="center">
                      <template #default="scope">
                        <el-tag
                            :type="scope.row.workState === '在职' ? '' :'退休'?'warning': 'danger'"
                            disable-transitions
                        >{{ scope.row.workState }}
                        </el-tag
                        >
                      </template>
                    </el-table-column>
                    <el-table-column fixed="right" align="center" label="操作" width="120">
                      <template #default="scope">
                        <el-button size="small" style="background-color:#66b1ff" @click="EditEmployee(scope.row)">
                          <el-icon>
                            <Edit style="color:#213d5b"/>
                          </el-icon>
                        </el-button>
                        <el-popconfirm v-if="user.role"
                                       @confirm="DelEmployee(scope.row.id)" title="确认删除?" confirm-button-text="确认"
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
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <Foot/>
  </div>
  <!--  入职登记表单-->
  <div>
    <el-dialog v-model="dialogFormVisible" title="入职登记" align-center center class="" width=650
               style="border-radius: 0.875rem 1rem;">
      <h6 class="mb-3">基本信息</h6>
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item prop="name" label="姓名:">
              <el-input v-model="state.formData.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="gender" label="性别:">
              <el-select v-model="state.formData.gender" placeholder="">
                <el-option label="男" value="男"/>
                <el-option label="女" value="女"/>
                <el-option label="其他" value="其他"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="tiptopDegree" label="最高学历">
              <el-select v-model="state.formData.tiptopDegree" placeholder="">
                <el-option label="博士" value="博士"/>
                <el-option label="硕士" value="硕士"/>
                <el-option label="本科" value="本科"/>
                <el-option label="大专" value="大专"/>
                <el-option label="高中" value="高中"/>
                <el-option label="初中" value="初中"/>
                <el-option label="小学" value="小学"/>
                <el-option label="其他" value="其他"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item prop="nation" label="民族:">
              <el-input v-model="state.formData.nation"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="naticePlace" label="籍贯:">
              <el-input v-model="state.formData.naticePlace"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="wedlock" label="婚姻状况:">
              <el-select v-model="state.formData.wedlock" placeholder="">
                <el-option label="已婚" value="已婚"/>
                <el-option label="未婚" value="未婚"/>
                <el-option label="离异" value="离异"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item prop="politic" label="政治面貌:">
              <el-select v-model="state.formData.politic" placeholder="">
                <el-option label="中共党员" value="中共党员"/>
                <el-option label="中共预备党员" value="中共预备党员"/>
                <el-option label="共青团员" value="共青团员"/>
                <el-option label="群众" value="群众"/>
                <el-option label="其他" value="其他"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="birthday" label="出生日期:">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.formData.birthday"
                      type="date"
                      placeholder=""
                      format="YYYY/MM/DD"
                      value-format="YYYY-MM-DD"
                      style="width:245px"
                      :size="10"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item prop="school" label="毕业院校:">
              <el-input v-model="state.formData.school"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="phone" label="联系电话:">
              <el-input v-model.number="state.formData.phone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter=24>
          <el-col :span="10">
            <el-form-item prop="specialty" label="所学专业:">
              <el-input v-model="state.formData.specialty"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="email" label="邮箱:">
              <el-input v-model="state.formData.email"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item prop="idCard" label="身份证号:">
              <el-input v-model="state.formData.idCard"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="address" label="地址:">
              <el-input v-model="state.formData.address"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider/>
        <h6 class="mb-3">工作信息</h6>
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item prop="engageForm" label="合同类型:">
              <el-select v-model="state.formData.engageForm" placeholder="">
                <el-option label="劳务合同" value="劳务合同"/>
                <el-option label="外聘合同" value="外聘合同"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="departmentName" label="部门:">
              <el-select v-model="state.formData.departmentName" style="width:220px">
                <el-option
                    v-for="department in  departmentStore.departmentList"
                    :key="department.id"
                    :label="department.departmentName"
                    :value="department.departmentName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item prop="post" label="岗位:">
              <el-input v-model="state.formData.post"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="startContract" label="合同起始日期:">
              <div class="demo-date-picker">
                <div class="block" style=" width:50px">
                  <el-date-picker
                      v-model="state.formData.startContract"
                      type="date"
                      format="YYYY/MM/DD"
                      value-format="YYYY-MM-DD"
                      placeholder=""
                      style="width:165px"
                      :size="10"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="职称:" prop="level">
              <el-input v-model="state.formData.level"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="contractTerm" label="合同年限:">
              <el-select v-model="state.formData.contractTerm" placeholder="Select">
                <el-option
                    v-for="(item,index) in 5"
                    :key="index"
                    :label="item+'年'"
                    :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="save" type="primary">登记</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <!--  修改员工资料表单-->
  <div>
    <el-dialog v-model="dialogUpdateVisible" title="修改员工资料" align-center center class=""
               style="border-radius: 0.875rem 1rem;">
      <h6 class="mb-3">基本信息</h6>
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item prop="name" label="姓名:">
              <el-input v-model="state.updateData.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="gender" label="性别:">
              <el-select v-model="state.updateData.gender" placeholder="">
                <el-option label="男" value="男"/>
                <el-option label="女" value="女"/>
                <el-option label="其他" value="其他"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="tiptopDegree" label="最高学历">
              <el-select v-model="state.updateData.tiptopDegree" placeholder="">
                <el-option label="博士" value="博士"/>
                <el-option label="硕士" value="硕士"/>
                <el-option label="本科" value="本科"/>
                <el-option label="大专" value="大专"/>
                <el-option label="高中" value="高中"/>
                <el-option label="初中" value="初中"/>
                <el-option label="小学" value="小学"/>
                <el-option label="其他" value="其他"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="8">
            <el-form-item prop="nation" label="民族:">
              <el-input v-model="state.updateData.nation"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="naticePlace" label="籍贯:">
              <el-input v-model="state.updateData.naticePlace"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="wedlock" label="婚姻状况:">
              <el-select v-model="state.updateData.wedlock" placeholder="">
                <el-option label="已婚" value="已婚"/>
                <el-option label="未婚" value="未婚"/>
                <el-option label="离异" value="离异"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item prop="politic" label="政治面貌:">
              <el-select v-model="state.updateData.politic" placeholder="">
                <el-option label="中共党员" value="中共党员"/>
                <el-option label="中共预备党员" value="中共预备党员"/>
                <el-option label="共青团员" value="共青团员"/>
                <el-option label="群众" value="群众"/>
                <el-option label="其他" value="其他"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="birthday" label="出生日期:">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.updateData.birthday"
                      type="date"
                      placeholder=""
                      format="YYYY/MM/DD"
                      value-format="YYYY-MM-DD"
                      style="width:245px"
                      :size="10"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item prop="school" label="毕业院校:">
              <el-input v-model="state.updateData.school"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="phone" label="联系电话:">
              <el-input v-model.number="state.updateData.phone"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter=24>
          <el-col :span="10">
            <el-form-item prop="specialty" label="所学专业:">
              <el-input v-model="state.updateData.specialty"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="email" label="邮箱:">
              <el-input v-model="state.updateData.email"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="10">
            <el-form-item prop="idCard" label="身份证号:">
              <el-input v-model="state.updateData.idCard"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item prop="address" label="地址:">
              <el-input v-model="state.updateData.address"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <div v-if="user.role">
          <el-divider/>
          <h6 class="mb-3">工作信息</h6>
          <el-row :gutter="24">
            <el-col :span="10">
              <el-form-item prop="engageForm" label="合同类型:">
                <el-select v-model="state.updateData.engageForm" placeholder="" disabled>
                  <el-option label="劳务合同" value="劳务合同"/>
                  <el-option label="外聘合同" value="外聘合同"/>
                </el-select>
              </el-form-item>
            </el-col>
          <el-col :span="12">
            <el-form-item prop="departmentName" label="部门:">
              <el-select v-model="state.updateData.departmentName" style="width:220px" disabled>
                <el-option
                    v-for="department in  departmentStore.departmentList"
                    :key="department.id"
                    :label="department.departmentName"
                    :value="department.departmentName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item prop="post" label="岗位:">
              <el-input v-model="state.updateData.post" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="startContract" label="合同起始日期:">
              <div class="demo-date-picker">
                <div class="block" style="width:50px">
                  <el-date-picker
                      v-model="state.updateData.startContract"
                      type="date"
                      disabled
                      format="YYYY/MM/DD"
                      value-format="YYYY-MM-DD"
                      placeholder=""
                      style="width:165px"
                      :size="10"/>
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="职称:" prop="level">
              <el-input v-model="state.updateData.level"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="contractTerm" label="合同年限:">
              <el-select v-model="state.updateData.contractTerm" placeholder="Select" disabled>
                <el-option
                    v-for="(item,index) in 5"
                    :key="index"
                    :label="item+'年'"
                    :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
          </el-row>
        </div>
      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="update" type="primary">保存</el-button>
        <el-button @click="clearFormData">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from "@/request.js";
import {getCurrentInstance, reactive, ref, toRaw} from "vue";
import {ElMessage, ElNotification} from "element-plus";
import {flexWidth} from '@/utils/tableUtils.js'
import {useEmployee} from "@/stores/employee.js";
import axios from "axios";
import {useUser} from '@/stores/user.js'
//部门列表
import {useDepartment} from "@/stores/department.js"

const useStore = useUser();
const user = useStore.getUser();
const departmentStore = useDepartment();
const employeeStore = useEmployee();
//前端规则校验
const {proxy} = getCurrentInstance();
const dialogFormVisible = ref(false)
const dialogUpdateVisible = ref(false);
const rules = reactive({
  name: [
    {required: true, message: '请输入姓名', trigger: 'blur'}
  ],
  gender: [
    {required: true, message: '请选择性别', trigger: 'blur'}
  ],
  birthday: [
    {type: 'date', required: true, message: '请输入生日', trigger: 'blur'}
  ],
  idCard: [
    {required: true, message: '请输入身份证号', trigger: 'blur'},
    {min: 18, max: 18, message: '身份证号长度必须为18位', trigger: 'blur'},
  ],
  wedlock: [
    {required: true, message: '请选择婚姻状况', trigger: 'change'}
  ],
  nation: [
    {required: true, message: '请输入民族', trigger: 'blur'}
  ],
  naticePlace: [
    {required: true, message: '请输入籍贯', trigger: 'blur'}
  ],
  politic: [
    {required: true, message: '请选择政治面貌', trigger: 'change'}
  ],
  phone: [
    {type: 'number', required: true, message: '请输入联系电话', trigger: 'blur'},
    {type: 'number', message: '联系电话只能输入数字'},
  ],
  email: [
    {required: true, message: '请输入邮箱', trigger: 'blur'}
  ],
  address: [
    {required: true, message: '请输入地址', trigger: 'blur'}
  ],
  tiptopDegree: [
    {required: true, message: '请选择最高学历', trigger: 'change'}
  ],
  specialty: [
    {required: true, message: '请输入所学专业', trigger: 'blur'}
  ],
  school: [
    {required: true, message: '请输入毕业院校', trigger: 'blur'}
  ],
  departmentName: [
    {required: true, message: '请输入部门名称', trigger: 'blur'}
  ],
  startContract: [
    {type: 'date', required: true, message: '请选择合同起始日期', trigger: 'change'}
  ],
  contractTerm: [
    {required: true, message: '请选择合同年限', trigger: 'change'}
  ],
  engageForm: [
    {required: true, message: '请选择合同类型', trigger: 'change'}
  ]
})
const state = reactive({
  tableData: [],
  //入职登记信息
  formData: {},
  updateData: {},
  departmentList: [],
})
//分页数据
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(10);
const employeeId = ref('');
const name = ref('');
const DepartmentId = ref('');
const post = ref('');
const hireDate = ref('');

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
//加载后端员工数据
const load = () => {
  request.get('/admin/employee/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      name: name.value,
      employeeId: employeeId.value,
      DepartmentId: DepartmentId.value,
      post: post.value,
      hireDate: hireDate.value,
      userId: user.id,
      userRole: user.role
    }
  }).then(res => {
    if (res.code === 200) {
      state.tableData = res.data?.records
      total.value = res.data.total - 0;
      if (res.data.records.length == 0) {
        // ElMessage.warning('查找结果不存在～');
      }
    } else {
      state.tableData = [];
      total.value = 0
      ElMessage.warning(res.msg);
    }
  })
}

const selectDepartmentList = () => {
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

const downloadList = () => {
  axios({ // 用axios发送get请求
    method: 'get',
    url: 'http://localhost:9090/admin/employee/download', // 请求地址
    responseType: 'blob', // 表明返回服务器返回的数据类型
    // data:patient.patient,  //提交的数据
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(res => {// 处理返回的文件流
    //new Blob([res])中不加data就会返回下图中[objece objece]内容（少取一层）
    const blob = new Blob([res.data], {type: "application/vnd.ms-excel"});
    const fileName = '员工信息表.xlsx';//下载文件名称
    const elink = document.createElement('a');
    elink.download = fileName;
    elink.style.display = 'none';
    elink.href = URL.createObjectURL(blob);
    document.body.appendChild(elink);
    elink.click();
    URL.revokeObjectURL(elink.href); // 释放URL 对象
    document.body.removeChild(elink);
  })
}

//入职登记调用接口
const save = () => {
  //表单校检
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (valid) {
      //发送后台请求
      request.post('admin/employee/save', state.formData).then(res => {
        if (res.code == '200') {
          ElNotification.success('入职登记成功！')
        } else {
          ElMessage.error('系统服务异常，请稍后再试~')
        }
        clearFormData();
        load();
      })
    } else {
      ElMessage.error('入职登记信息填写错误')
    }
  })
}
//打开修改员工资料视图
const EditEmployee = (row) => {
  dialogUpdateVisible.value = true;
  state.updateData = JSON.parse(JSON.stringify(row));
}
//修改员工资料
const update = () => {
  request.post('admin/employee/update', state.updateData).then((res) => {
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
const DelEmployee = (id) => {
  request.delete('admin/employee/delete/' + id).then((res) => {
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
//数据初始化
selectDepartmentList();
load();
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>