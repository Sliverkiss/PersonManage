<template>
  <el-scrollbar max-height="800px">
    <div class="m-3">
      <div class="px-4 py-2 mb-4">
        <a class="fw-bold text-decoration-none text-dark ">
          <OfficeBuilding class="m-1 " width="30px" style="font-weight:bold; color:dodgerblue"/>
          <span><el-link class="fs-5 fw-bold  text-dark">Cheerio</el-link></span>
        </a>
        <h1 class="h3 fw-bold mt-4 mb-2 fs-4">创建新账户</h1>
        <h2 class="h5 fw-medium text-muted mb-0 fs-6">请填写您的详细资料</h2>
      </div>
      <form class="js-validation-signup px-4" onsubmit="return false;">
        <div class="form-floating mb-4">
          <input class="form-control" id="username" name="username" placeholder="输入你的用户名" type="text">
          <label class="form-label" for="username">用户名</label>
        </div>
        <div class="form-floating mb-4">
          <input class="form-control" id="password" name="password" placeholder="输入你的密码" type="password">
          <label class="form-label" for="password">密码</label>
        </div>
        <div class="form-floating mb-4">
          <input class="form-control" id="qq" name="qq" placeholder="输入你的员工编号" type="number">
          <label class="form-label" for="qq">员工编号</label>
        </div>
        <div class="row mb-4">
          <div class="col-6">
            <div class="form-floating">
              <input type="text" class="form-control" id="captcha" name="captcha" placeholder="请输入验证码">
              <label class="form-label" for="captcha">验证码</label>
            </div>
          </div>
          <div class="col-6">
            <div class="form-floating">
              <div><img class="border" id="captcha_img" src="/captcha.html"
                        alt="captcha" height="58px;"
                        width="100%"></div>
            </div>
          </div>
        </div>
        <div class="mb-4">
          <div class="form-check">
            <input checked="" class="form-check-input" id="signup-terms" name="signup-terms" type="checkbox">
            <label class="form-check-label" for="signup-terms">我同意条款</label>
          </div>
        </div>
        <div class="mb-4">
          <el-button class="p-4" :type="info" type="submit">
            创建账户
          </el-button>
          <div class="mt-4">
            <el-link class="fs-sm fw-medium link-fx text-muted me-2 mb-1 d-inline-block text-decoration-none"
                     @click="router.push('/')">
              返回登录
            </el-link>
            <el-link class="fs-sm fw-medium link-fx text-muted me-2 mb-1 d-inline-block text-decoration-none"
                     @click="router.push('/find')">
              找回密码
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