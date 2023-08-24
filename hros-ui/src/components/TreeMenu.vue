<!--
思路 :

1. 获取传递进来的数据
2. 使用 el-sub-menu 进行一个遍历 , 遍历传递过来的所有数据, 并且判断他是否有 children , 如果有children ,染病 menuType 为 1 的情况
   则使用 munuName 生成一个组
3. 递归判断是否有多级 children , 如果有 则继续生成下一级菜单
4. 判断到最后一个层级 , 如果说 menuType === 1 情况下,name这个肯定是可以 按钮 ,而不是一个组 所以, 这个应该用 el-menu-item 进行渲染

-->


<template>
  <div class="el-menu font-weight bg-white">
    <template v-for="item in treeData">
      <el-sub-menu :index='item.path'
                   v-if="item.children && item.children.length > 0 && item.children[0].menuType.toString() === '1'"
      >
        <template #title>
          <el-icon>
            <component :is="item.icon"/>
          </el-icon>
          <span>{{ item.menuName }}</span>
        </template>
        <TreeMenu :treeData="item.children"></TreeMenu>
      </el-sub-menu>
      <el-menu-item
          v-else-if="item.menuType.toString() === '1'"
          :index="item.path"
          :key="item.id"
      >
        <el-icon>
          <component :is="item.icon"/>
        </el-icon>
        {{ item.menuName }}
      </el-menu-item>
    </template>
  </div>
</template>

<script setup>
import {useUser} from '@/stores/user.js'
import {useRouter} from "vue-router";

const userStore = useUser();
const props = defineProps(['treeData'])
const handleOpen = (key, keyPath) => {
  console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
  console.log(key, keyPath)
}
const router = useRouter();

</script>

<style scoped>
.asidecolor {
  background-color: #F5F5F5;
}
</style>
