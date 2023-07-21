<template>
  <div>
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
              <div class="" style="width:120px">
                <el-button type="success" plain @click="dialogFormVisible= true">
                  <el-icon>
                    <Plus/>
                  </el-icon>
                  <span>新增部门</span></el-button>
              </div>
              <div class="" style="width:120px">
                <el-button type="danger" plain @click="dialogFormVisible= true">
                  <el-icon>
                    <CloseBold/>
                  </el-icon>
                  <span>部门合并</span></el-button>
              </div>
            </div>
          </template>
          <div class="block-content block-content-full">
            <div id="usersList_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
              <div class="row">
                <div class="col-sm-12 col-md-12">
                  <el-select v-model="state.updateData.departmentName" style="width:150px" clearable
                             placeholder="请选择部门名称">
                    <el-option
                        v-for="department in toRaw(departmentStore.departmentList)"
                        :key="department.id"
                        :label="department.departmentName"
                        :value="department.departmentName"
                    />
                  </el-select>
                  <el-input style="width:160px;margin-left:10px" placeholder="请输入姓名"></el-input>
                  <el-button type="primary" class="ms-2">
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
                      <el-collapse-item v-for="department in toRaw(departmentStore.departmentList)"
                                        :title="department.departmentName" :name="department.id">
                        <div>
                          <el-descriptions
                              class="margin-top"
                              :title="department.departmentName"
                              :column="3"
                              :size="size"
                              border
                          >
                            <template #extra>
                              <el-button size="small" style="background-color:#66b1ff" @click="EditSalary(scope.row)">
                                <el-icon>
                                  <Edit style="color:#213d5b"/>
                                </el-icon>
                              </el-button>
                              <el-popconfirm @confirm="DeleteSalary(department.id)" title="确认删除?"
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
                            <el-descriptions-item>
                              <template #label>
                                <div class="cell-item">
                                  <el-icon :style="iconStyle">
                                    <office-building/>
                                  </el-icon>
                                  部门岗位
                                </div>
                              </template>
                              <div v-show="department?.postList">
                                <el-tag v-for="post in department?.postList">
                                  {{ post }}
                                </el-tag>
                              </div>
                              <div v-show="!department?.postList">无</div>
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
              <div class="row">
                <div class="col-sm-12 col-md-4">
                  <div class="dataTables_info" id="usersList_info" role="status" aria-live="polite"><span
                      class="text-muted ">共有 5 条 / 1 页</span></div>
                </div>
                <div class="col-sm-12 col-md-7">
                  <el-pagination
                      background
                      page-size="10"
                      layout="prev, pager, next"
                      :total="50">
                  </el-pagination>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, reactive, ref, toRaw} from 'vue'
import request from "@/request.js";
import {ElMessage} from "element-plus";
import {useDepartment} from "@/stores/department.js"

const departmentStore = useDepartment();
const activeNames = ref('1')
const size = ref('')
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
})

const selectDepartmentList = () => {
  request.get('admin/department/list').then(res => {
    try {
      if (res.code === 200) {
        departmentStore.setDepartmentList(res.data.records);
      }
    } catch (e) {
      ElMessage.error(e);
    }
  })
}
const load = () => {
  request.get('/admin/department/list').then((res) => {
    try {
      if (res.code == 200) {
        state.tableData = res.data?.records
        console.log(toRaw(state.tableData))
      } else {
        ElMessage.error(res.msg)
      }
    } catch (e) {
      ElMessage.error(e)
    }
  })
}
load();
//数据初始化
selectDepartmentList();
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>