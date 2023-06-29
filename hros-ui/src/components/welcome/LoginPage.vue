<template>
  <el-scrollbar max-height="800px">
    <div class="p-3 mt-4">
      <div class="px-4 py-2 mb-4">
        <a class="fw-bold text-decoration-none text-dark ">
          <OfficeBuilding class="m-1 " width="30px" style="font-weight:bold; color:dodgerblue"/>
          <span><el-link class="fs-5 fw-bold  text-dark">Cheerio</el-link></span>
        </a>
        <h1 class="h3 fw-bold mt-4 mb-2 fs-4">欢迎来到您的用户中心</h1>
        <h2 class="h5 mt-3 fw-medium text-muted mb-0 fs-5">请登录</h2>
      </div>
      <form class="js-validation-signin px-4" onsubmit="return false;">
        <div class="form-floating mb-4">
          <input class="form-control" id="username" name="username" placeholder="输入你的用户名" type="text">
          <label class="form-label" for="username">用户名</label>
        </div>
        <div class="form-floating mb-4">
          <input class="form-control" id="password" name="password" placeholder="输入你的密码" type="password">
          <label class="form-label" for="password">密码</label>
        </div>
        <div class="mb-4">
          <div class="form-check">
            <input checked="" class="form-check-input" id="login-remember-me" name="login-remember-me" type="checkbox"
                   value="">
            <label class="form-check-label" for="login-remember-me">记住我</label>
          </div>
        </div>
        <div class="mb-4">
          <el-button class="p-4" :type="info" type="submit">
            登录
          </el-button>
          <div class="mt-4 text-decoration-none ">
            <el-link class="fs-sm fw-medium link-fx text-muted me-2 mb-1 d-inline-block text-decoration-none " href="#">
              QQ登录
            </el-link>
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
      </form>
    </div>
  </el-scrollbar>
</template>

<script setup>
import {useRouter} from "vue-router";
const router = useRouter()

</script>

<style scoped>

</style>