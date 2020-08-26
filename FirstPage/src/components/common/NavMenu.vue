<template>
  <el-menu
    :default-active="this.$route.path"
    router
    mode="horizontal"
    @select="handleSelect"
    background-color="white"
    text-color="#222"
    active-text-color="blue"
    style="min-width: 1300px;"
  >
    <el-menu-item v-for="(item, i) in navList" :key="i" :index="item.name">
      {{item.navItem}}
    </el-menu-item>
    <span style="position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold">招标信息管理系统</span>
    <i class="el-icon-switch-button" v-on:click="logout" style="float:right;font-size: 40px;color: #222;padding: 10px"></i>
  </el-menu>
</template>

<script>
export default {
  name: 'NavMenu',
  data () {
    return {
      navList: [
        {name: '/index', navItem: '首页'},
        {name: '/province', navItem: '省级项目'},
        {name: '/city', navItem: '市级项目'},
        {name: '/profile', navItem: '用户管理'}
      ]
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      // console.log(key)
    },
    logout () {
      this.$axios
        .put('/logout')
        .then(resp => {
          if (resp.data.code === 200) {
            this.$store.commit('logout')
            this.$router.replace('/login')
          }
        })
    }
  }
}
</script>

<style>
  .el-icon-switch-button {
    cursor: pointer;
  }
</style>
