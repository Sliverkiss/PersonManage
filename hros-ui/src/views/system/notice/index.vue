<template>
  <div v-if="user.employeeVo.workState=='离职'">无权限</div>
  <div v-else>
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
                  <el-link @click="openDialog(scope.row)">查看内容</el-link>
                </template>
              </el-table-column>
              <el-table-column label="发布日期" prop="createDate" align="center"/>
              <el-table-column label="发布人" prop="director" align="center"/>
              <!--              <el-table-column label="封面" prop="img" align="center">-->
              <!--                <template #default="scope">-->
              <!--                  <el-image style="height:80px" :src="getImageUrl(scope.row.img)"></el-image>-->
              <!--                </template>-->
              <!--              </el-table-column>-->
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

  <!--  表单 -->
  <div>
    <el-dialog v-model="open" :title="title" align-center center class="" width="800"
               style="border-radius: 0.875rem 1rem;">
      <el-form :model="state.formData" class="" status-icon :rules="rules" ref="ruleFormRef">
        <el-row :gutter="24">
          <el-col :span="24">
            <el-form-item prop="title" size="large" label="公告标题：" label-width="100">
              <el-input v-model="state.formData.title" placeholder="请输入公告标题"></el-input>
            </el-form-item>
          </el-col>
          <!--          <el-col :span="24">-->
          <!--            <el-form-item prop="img" size="img" label="封面：" label-width="100">-->
          <!--              <el-upload-->
          <!--                  class="avatar-uploader"-->
          <!--                  action="http://localhost:9090/admin/notice/upload"-->
          <!--                  :show-file-list="true"-->
          <!--                  :auto-upload="true"-->
          <!--                  :on-success="handleAvatarSuccess"-->
          <!--                  :before-upload="beforeAvatarUpload">-->
          <!--                <img style="height:200px" v-if="imageUrl" :src="imageUrl" class="avatar"/>-->
          <!--                <el-icon v-else class="avatar-uploader-icon">-->
          <!--                  <Plus/>-->
          <!--                </el-icon>-->
          <!--              </el-upload>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <el-col :span="24">
            <el-form-item prop="content" label="内容: " label-width="100">
              <div id="richText"></div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="submitForm" type="primary">确定</el-button>
        <el-button @click="cancel" type="primary">
          取消
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
  <el-drawer
      v-model="tableDialog"
      title="公告详情"
      direction="rtl"
      size="50%">
    <template #header="{ close, titleId, titleClass }">
      <h4 :id="titleId" class="titleClass text-center">{{ state.noticeDetail.title }}</h4>
    </template>
    <div v-html="state.noticeDetail.content"></div>
  </el-drawer>
</template>

<script setup>
import request from "@/request.js";
import {getCurrentInstance, onMounted, reactive, ref, toRaw} from "vue";
import {ElMessage, ElNotification} from "element-plus";
//接口api
import {addNotice, delNotice, updateNotice} from "@/api/system/notice.js";
//引入富文本
import E from 'wangeditor'
//引入用户
import {useUser} from '@/stores/user.js'

const useStore = useUser();
const user = useStore.getUser();
//侧边栏活动标志
const activeName = ref('first')
//vue对象
const {proxy} = getCurrentInstance();
//富文本编辑器
let editor;
//封面上传
const imageUrl = ref('')
//获取图片路径
const getImageUrl = (name) => {
  return new URL(`../../../assets/img/upload_photo/` + name, import.meta.url).href
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
const handleAvatarSuccess = (response, uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw)
  state.formData.img = response;
}
//数据
const state = reactive({
  tableData: [],
  noticeDetail: {},
  formData: {},
  updateData: {},
})
//数据校验规则
const rules = reactive({})
//打开新增续约视图
const open = ref(false)
//弹出窗口标题
const title = ref('');
//分页
const currentPage = ref(1);//当前页
const pageSize = ref(15);//页码展示数量
const total = ref(10);//页码总数
//抽屉
const tableDialog = ref(false);

const openDialog = (notice) => {
  tableDialog.value = true;
  state.noticeDetail = JSON.parse(JSON.stringify(notice));
  console.log(toRaw(state.noticeDetail))
}

const handleSizeChange = (val) => {
  pageSize.value = val;
  load();
}
const handleCurrentChange = (val) => {
  currentPage.value = val;
  load()
}
//模糊查询条件
const planName = ref('');
const status = ref('');
//初始化加载数据
const load = () => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
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
  title.value = "发布公告"
  createEditer();
  editor.txt.html('')
}
//创建富文本编辑器
const createEditer = () => {
  //创建富文本公告框
  proxy.$nextTick(() => {
    if (!editor) {
      editor = new E("#richText");
      editor.config.uploadImgServer = 'http://localhost:9090/admin/notice/file/uploadImg';
      editor.config.uploadFileName = 'file';
      // 或者 const editor = new E(document.getElementById('div1'))
      editor.create();
    }
  })
}
//修改按钮操作
const handleUpdate = (row) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  open.value = true
  title.value = "修改公告"
  createEditer();
  state.formData = JSON.parse(JSON.stringify(row));
  editor.txt.html(state.formData.content);
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
      state.formData.content = editor.txt.html();
      state.formData.director = user.employeeVo.name;
      //id不为空则进行修改
      if (state.formData.id != null) {
        updateNotice(state.formData).then((res) => {
          res.code == 200 ? ElNotification.success(res.msg) : ElMessage.error(res.msg);
          load();
        });
      } else {
        //否则进行新增
        addNotice(state.formData).then((res) => {
          res.code == 200 ? ElNotification.success(res.msg) : ElMessage.error(res.msg);
          load();
        });
      }
      open.value = false;
    } else {
      ElMessage.error('申请信息填写错误')
    }
  })
}
//删除按钮操作
const handleDelete = (id) => {
  if (user.employeeVo.workState == '离职') {
    ElMessage.warning("sorry,您已离职，无操作权限~")
    return;
  }
  delNotice(id).then((res) => {
    res.code == 200 ? ElNotification.success(res.msg) : ElMessage.error(res.msg);
    load();
  });
}
//生命周期钩子
onMounted(() => {
  load();
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