<template>
  <div class="content" id="pjax-container">
    <div class="row p-4">
      <div class="col-12">
        <div class="content">
          <div class="block block-rounded">
            <el-card class="notice-card ">
              <template #header>
                <div class="text-muted fw-bold"> <UserFilled class="m-1 " width="20px"/>个人资料</div>
              </template>
              <div class="block-content">
                <form id="edit-profile" onsubmit="return false;">
                  <div class="row items-push">
                    <div class="col-lg-3">
                      <p class="text-muted">
                        你可以在这里修改你的用户信息
                      </p>
                    </div>
                    <div class="col-lg-7 offset-lg-1">
                      <div class="mb-4">
                        <label class="form-label">用户名</label>
                        <input class="form-control form-control-lg" id="username" name="username" placeholder="Enter your username.." readonly="" type="text" value="sliverkiss">
                      </div>
                      <div class="mb-4">
                        <label class="form-label">员工编号</label>
                        <input class="form-control form-control-lg fs-6" id="nickname" name="nickname" placeholder="Enter your NICKNAME.." readonly type="text" value="员工编号">
                      </div>
                      <div class="mb-4">
                        <label class="form-label">QQ</label>
                        <input class="form-control form-control-lg  fs-6" id="qq" name="qq" placeholder="Enter your QQ.." type="number" value="1393579810">
                      </div>
                      <div class="mb-4">
                        <label class="form-label">邮箱</label>
                        <input class="form-control form-control-lg  fs-6" id="mail" name="mail" placeholder="Enter your mail.." type="email" value="1393579810@qq.com">
                      </div>
                      <div class="mb-4">
                        <el-button class="pt-3 pb-3" type="primary">更新</el-button>
                      </div>
                    </div>
                  </div>
                </form>
              </div>
            </el-card>
          </div>
            <br/>
            <br/>
          <div class="block block-rounded">
            <el-card class="notice-card ">
              <template #header>
                <div class="text-muted fw-bold"> <Key class="m-1 " width="20px"/>修改密码</div>
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
                      <div class="mb-4">
                        <label class="form-label">当前密码</label>
                        <input class="form-control form-control-lg" id="outpass" name="outpass" type="text">
                      </div>
                      <div class="mb-4">
                        <label class="form-label">新密码</label>
                        <input class="form-control form-control-lg fs-6" id="password" name="password" type="text">
                      </div>
                      <div class="mb-4">
                        <label class="form-label">确认新密码</label>
                        <input class="form-control form-control-lg fs-6" id="repass" name="repass" type="text">
                      </div>
                      <div class="mb-4">
                        <el-button class="pt-3 pb-3" type="primary">更新</el-button>
                      </div>
                    </div>
                  </div>
                </form>
              </div>
            </el-card>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: "userInfo"
}
</script>

<style scoped>
.notice-card {
  border-radius: 0.875rem 1rem;
  background-color: white;
  font-size: 15px;
}
</style>