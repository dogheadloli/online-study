<template>
  <div class="dashboard-container">
    <div class="dashboard-title">欢迎来到后台管理系统</div>
    <div class="dashboard-text">姓名:{{ name }}</div>
    <div class="dashboard-text">角色:<span v-for="role in roles" :key="role">{{ role }}</span></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin-top: 10px;
    margin-left: 10px;
  }
  &-title{
    font-size: 30px;
    line-height: 46px;
  }
  &-text {
    font-size: 20px;
    line-height: 46px;
  }
}
</style>
