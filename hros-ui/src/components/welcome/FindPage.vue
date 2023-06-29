<template>
  <el-scrollbar max-height="800px">
    <div class="m-3">
      <div class="px-4 py-2 mb-4">
        <a class="fw-bold text-decoration-none text-dark ">
          <OfficeBuilding class="m-1 " width="30px" style="font-weight:bold; color:dodgerblue"/>
          <span><el-link  class="fs-5 fw-bold  text-dark">Cheerio</el-link></span>
        </a>
        <h1 class="h3 fw-bold mt-4 mb-2 fs-4">找回账户密码</h1>
        <h2 class="h5 fw-medium text-muted mb-0 fs-6">请输入你的员工编号</h2>
      </div>
      <form class="js-validation-reminder px-4" onsubmit="return false;">
        <div class="form-floating mb-4">
          <input type="text" class="form-control" id="mail" name="mail" placeholder="输入你的邮箱">
          <label class="form-label">员工编号</label>
          <div class="text-muted fw-bold" style="font-size:12px">邮箱默认为您的QQ账号邮箱</div>
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
              <div><img class="border" id="captcha_img" src="/captcha.html"  alt="captcha" height="58px;" width="100%"></div>
            </div>
          </div>
        </div>
        <div class="mb-4">
          <el-button class="p-4" :type="info" type="submit">
            找回密码
          </el-button>
          <div class="mt-4">
            <el-link class="fs-sm fw-medium link-fx text-muted me-2 mb-1 d-inline-block text-decoration-none"
                     @click="router.push('/')">
              <el-icon class="me-2"><Back/></el-icon>
              返回登录
            </el-link>
          </div>
        </div>
      </form>
    </div>
  </el-scrollbar>
</template>

<script setup>
import {useRouter} from "vue-router";
const router = useRouter();

</script>

<style scoped>

</style>