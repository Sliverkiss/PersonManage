<template>
  <div class="content" id="pjax-container">
    <div class="row p-4">
      <div class="col-12">
        <div class="content">
          <br/>
          <div class="block block-rounded">
            <el-card class="notice-card ">
              <template #header>
                <div class="text-muted fw-bold">
                  <Key class="m-1 " width="20px"/>
                  修改密码
                </div>
              </template>
              <div class="block-content">
                <form id="edit-profile" onsubmit="return false;">
                  <div class="row items-push">
                    <div class="col-lg-3">
                      <p class="text-muted">
                        你可以在这里修改登录密码
                      </p>
                    </div>
                    <div class="col-lg-7 offset-lg-1">
                      <el-form :model="state.data" class="" status-icon :rules="rules" ref="ruleFormRef">
                        <el-row :gutter="24">
                          <el-col :span="24">
                            <el-form-item prop="oldPassword" size="large" label="旧密码：" label-width="100">
                              <el-input v-model="state.data.oldPassword" type="text"/>
                            </el-form-item>
                          </el-col>
                          <el-col :span="24">
                            <el-form-item prop="newPassword" size="large" label="新密码：" label-width="100">
                              <el-input v-model="state.data.newPassword" type="text"/>
                            </el-form-item>
                          </el-col>
                          <el-col :span="24">
                            <el-form-item prop="secNewPassword" size="large" label="确认新密码：" label-width="100">
                              <el-input v-model="state.data.secNewPassword" type="text"/>
                            </el-form-item>
                          </el-col>
                          <el-col :span="24">
                            <el-form-item prop="secNewPassword" size="large" label-width="100">
                              <el-button class="pt-3 pb-3" type="primary" @click="change">更新</el-button>
                            </el-form-item>
                          </el-col>
                        </el-row>
                      </el-form>
                    </div>
                  </div>
                </form>
              </div>
            </el-card>
          </div>
          <br/>
          <br/>
        </div>
      </div>
    </div>

  </div>
  <Foot/>
</template>

<script setup>
import {reactive, toRaw} from "vue";
//引入用户
import {useUser} from '@/stores/user.js'
import {changeUser} from "@/api/system/user.js";
import {ElMessage} from "element-plus";

const useStore = useUser();
const user = useStore.getUser();

const state = reactive({
  data: {
    username: user.username,
    oldPassword: '',
    newPassword: '',
    secNewPassword: ""
  },
})
const change = () => {
  console.log(toRaw(state))
  changeUser(state.data).then((res) => {
    res.code == 200 ? ElMessage.success(res.msg) : ElMessage.error(res.msg);
  });
}
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>