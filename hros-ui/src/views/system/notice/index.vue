<template>
  <div>
    <el-tabs v-model="activeName" class="demo-tabs bg-white p-3 notice-card " @tab-click="handleClick">
      <el-tab-pane name="first">
        <template #label>
        <span class="custom-tabs-label">
          <el-icon><calendar/></el-icon>
          <span>系统公告管理</span>
        </span>
        </template>
        <div class="text-muted fw-bold mt-2 mb-2" style="display:flex">
          <div class="row">
            <div class="col-sm-12 col-md-12">
              <el-input v-model="planName" style="width:180px;margin-right:10px" clearable
                        placeholder="请输入公告标题">
              </el-input>
              <el-date-picker
                  v-model="state.updateData.createDate"
                  type="date"
                  placeholder="请选择发布日期"
                  format="YYYY/MM/DD"
                  value-format="YYYY-MM-DD"
                  style="width:180px;" clearable
                  :size="10"/>

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
              <span>发布公告</span></el-button>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 p-3 ">
            <el-table :data="state.tableData" stripe class="text-center" height="400" max-height="400"
                      element-loading-text="拼命加载中">
              <el-table-column label="id" prop="id" align="center"/>
              <el-table-column label="标题" prop="title" align="center"/>
              <el-table-column label="内容" prop="content" align="center">
                <template #default="scope">
                  <el-link>查看内容</el-link>
                </template>
              </el-table-column>
              <el-table-column label="发布日期" prop="createDate" align="center"/>
              <el-table-column label="发布人" prop="user" align="center"/>
              <el-table-column label="封面" prop="img" align="center"/>
              <el-table-column fixed="right" align="center" label="操作" width="120">
                <template #default="scope" v-if="user.role">
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

  <!--  新增实体弹窗 -->
  <div style="height:600px">
    <el-dialog v-model="dialogFormVisible" title="发布公告" align-center center class="" width="800"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="planName" size="large" label="公告标题：" label-width="100">
              <el-input v-model="state.formData.planName" placeholder="请输入公告标题"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="trainer" size="large" label="封面：" label-width="100">
              <el-upload
                  class="avatar-uploader"
                  action="http://1ocalhost:9090/admin/notice/upload"
                  :show-file-list="true"
                  :auto-upload="true"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl" :src="imageUrl" class="avatar"/>
                <el-icon v-else class="avatar-uploader-icon">
                  <Plus/>
                </el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="startDate" label="内容: " label-width="100">
              <div id="richText"></div>
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
    <el-dialog v-model="dialogUpdateVisible" title="修改计划" align-center center class="" width="400"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.updateData" class="" status-icon :rules="rules" ref="ruleFormRef">

        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="planName" size="large" label="计划名称：" label-width="100">
              <el-input v-model="state.updateData.planName" placeholder="请输入计划名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="trainer" size="large" label="教练：" label-width="100">
              <el-input v-model="state.updateData.trainer" placeholder="请输入教练名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="startDate" label="开始时间: " label-width="100">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.updateData.startDate"
                      type="datetime"
                      placeholder=""
                      format="YYYY-MM-DD HH:mm:ss"
                      value-format="YYYY-MM-DD HH:mm:ss"
                      style="width:250px"
                  />
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="endDate" label="结束时间:" label-width="100">
              <div class="demo-date-picker">
                <div class="block">
                  <el-date-picker
                      v-model="state.updateData.endDate"
                      type="datetime"
                      placeholder=""
                      format="YYYY-MM-DD HH:mm:ss"
                      value-format="YYYY-MM-DD HH:mm:ss"
                      style="width:250px"
                  />
                </div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="location" size="large" label="培训地点：" label-width="100">
              <el-input v-model="state.updateData.location" placeholder="请输入培训地点"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="content" size="large" label="培训内容：" label-width="100">
              <el-input v-model="state.updateData.content" placeholder="请输入培训内容"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="status" size="large" label="培训状态：" label-width="100">
              <el-select v-model="state.updateData.status" style="width:450px" placeholder="请选择培训状态"
                         clearable>
                <el-option label="未开始" value="未开始"/>
                <el-option label="进行中" value="进行中"/>
                <el-option label="已结束" value="已结束"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

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
</template>

<script setup>
//加载后端合同数据
import request from "@/request.js";
import {getCurrentInstance, reactive, ref} from "vue";
import {useContract} from "@/stores/employee.js";
import {useDepartment} from "@/stores/department.js"
import {ElMessage, ElNotification} from "element-plus";
import {useUser} from '@/stores/user.js'
//引入富文本
import E from 'wangeditor'

const useStore = useUser();
const user = useStore.getUser();
const {proxy} = getCurrentInstance();
const userStore = useUser();
const contractStore = useContract();
const departmentStore = useDepartment();
const activeName = ref('first')
let editor;
//封面上传
const imageUrl = ref('')
const handleAvatarSuccess = (
    response,
    uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw)
}
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}
const state = reactive({
  tableData: [],
  formData: {
    'img': ''
  },
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
      key: 'startDate',
      value: '开始时间'
    }, {
      key: 'endDate',
      value: '结束时间'
    }, {
      key: 'trainer',
      value: '教练'
    }, {
      key: 'location',
      value: '培训地点'
    }, {
      key: 'content',
      value: '培训内容'
    },
  ]
})

const rules = reactive({
  planName: [
    {required: true, message: '请输入计划名称', trigger: 'blur'},
  ],
  trainer: [
    {required: true, message: '请输入教练名称', trigger: 'blur'},
  ],
  location: [
    {required: true, message: '请输入培训地点', trigger: 'blur'},
  ],
  content: [
    {required: true, message: '请输入培训内容', trigger: 'blur'},
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
const status = ref('');

const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}
//新增实体
const handleAdd = () => {
  dialogFormVisible.value = true
  proxy.$nextTick(() => {
    editor = new E("#richText")
    // 或者 const editor = new E(document.getElementById('div1'))
    editor.create()
  })
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
      const content = editor.txt.html();
      console.log(content)
      state.formData.director = userStore.getUser().username
      //发送后台请求
      //     request.post('admin/training/plan/save', state.formData).then(res => {
      //       if (res.code == '200') {
      //         ElNotification.success('添加成功！')
      //       } else {
      //         ElMessage.error('系统服务异常，请稍后再试~')
      //       }
      //       clearFormData();
      //       load();
      //     })
    } else {
      ElMessage.error('申请信息填写错误')
    }
  })
}
//修改员工资料
const update = () => {
  request.put('admin/training/plan/update', state.updateData).then((res) => {
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
const signUp = (row) => {
  console.log(row.id)
  request.get('admin/training/plan/sign', {
    params: {
      planId: row.id,
      employeeId: 1,
    }
  }).then((res) => {
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
  request.get('/admin/notice/page', {
    params: {
      currentPage: currentPage.value,
      pageSize: pageSize.value,
      planName: planName.value,
      status: status.value
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


//删除员工资料
const DelRenewal = (id) => {
  request.delete('admin/training/plan/delete/' + id).then((res) => {
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
load()
selectDepartmentList();
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