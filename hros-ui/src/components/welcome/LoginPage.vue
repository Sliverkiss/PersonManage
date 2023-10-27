<template>
  <div class="m-0 p-0" style="width:100vw;height:100vh;overflow: hidden;display: flex">
    <div style="flex:1;">
      <el-image style="width:100%;height:100%;" fit="cover"
                src="../../static/img/background.jpg"></el-image>
      <div class="welcome-title">
        <div class="p-4">
          <p class="fs-3 fw-semibold text-white">
            Get Inspired and Create.
          </p>
          <p class="text-white fw-medium">
            Copyright © <span>2023</span>
          </p>
        </div>
      </div>
    </div>
    <div style="width:400px;">
      <el-scrollbar max-height="800px">
        <div class="p-3 mt-4">
          <div class="px-4 py-2 mb-4">
            <a class="fw-bold text-decoration-none text-dark ">
              <OfficeBuilding class="m-1 " width="30px" style="font-weight:bold; color:dodgerblue"/>
              <span><el-link class="fs-5 fw-bold  text-dark">Cheerio</el-link></span>
            </a>
            <h1 class="h3 fw-bold mt-4 mb-2 fs-4">欢迎来到美术馆人事管理系统</h1>
            <h2 class="h5 mt-3 fw-medium text-muted mb-0 fs-5">请登录</h2>
          </div>
          <el-form :model="user" :rules="rules" class="js-validation-signin px-4" onsubmit="return false;">
            <el-form-item>
              <div class="form-floating">
                <input v-model="user.username" class="form-control " id="username" name="username"
                       placeholder="输入你的用户名" type="text" style="margin-right:120px">
                <label class="form-label" for="username">用户名</label>
              </div>
            </el-form-item>
            <el-form-item>
              <div class="form-floating mb-4">
                <input v-model='user.password' class="form-control" id="password" name="password"
                       placeholder="输入你的密码"
                       type="password" style="margin-right:120px"/>
                <label class="form-label" for="password">密码</label>
              </div>
            </el-form-item>
            <!--            <div class="mb-4">-->
            <!--              <div class="form-check">-->
            <!--                <input v-model='user.remenber' checked="" class="form-check-input" id="login-remember-me"-->
            <!--                       name="login-remember-me" type="checkbox"-->
            <!--                       value="">-->
            <!--                <label class="form-check-label" for="login-remember-me">记住我</label>-->
            <!--              </div>-->
            <!--            </div>-->
            <div class="mb-4">
              <el-button class="p-4" :type="info" @click="login">
                登录
              </el-button>
              <div class="mt-4 text-decoration-none ">
                <el-link class="fs-sm fw-medium link-fx text-muted me-2 mb-1 d-inline-block text-decoration-none "
                         @click="router.push('/register')">
                  创建账户
                </el-link>
                <el-link class="fs-sm fw-medium link-fx text-muted me-2 mb-1 d-inline-block text-decoration-none "
                         @click="router.push('/find')">
                  忘记密码?
                </el-link>
              </div>
            </div>
          </el-form>
        </div>
      </el-scrollbar>
      <el-button @click="router.push('/text')"></el-button>
    </div>
  </div>

</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive} from "vue";
import {ElMessage, ElNotification} from 'element-plus'
import request from "@/request.js";
import {useUser} from '@/stores/user.js'
import {useAside} from '@/stores/aside.js'
import {activeRouter} from "@/router/permission.js";
import {useRouteStore} from "@/stores/myRoute.js";

const userStore = useUser();
const asideStore = useAside();
const store = useRouteStore()
const router = useRouter();
const user = reactive({
  username: '',
  password: '',
  remenber: false
})
const rules = reactive({
  name: [
    {}
  ]
})

const login = () => {
  if (!user.username || !user.password) {
    ElMessage.warning('请填写用户名和密码！')
  } else {
    request.post('/user/login', {
      username: user.username,
      password: user.password,
      remenber: user.remenber
    }).then(res => {
      if (res.code == '200') {
        ElNotification.success('登录成功！');
        //将登录信息存储到store中
        userStore.setUser(res.data);
        console.log(res.data)
        const permissions = res.data.permissions;
        store.addRoutes(res.data.permissions, router);
        // console.log(permissions);
        activeRouter(permissions)
        //登录成功后进行页面跳转，跳转到主页
        router.push('/');
      } else {
        ElNotification.error(res.msg);
      }
    })
  }
}
</script>

<style scoped>

</style>