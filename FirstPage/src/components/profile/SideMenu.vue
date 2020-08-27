<template>
  <div>
    <el-menu
    router
    default-active="0"
    class="el-menu-admin"
    @select="handleSelect"
    mode="vertical"
    >
      <el-submenu index="0">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>订阅管理</span>
        </template>
        <el-button type="text" @click="editKeyWords">关键字管理</el-button>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'SideMenu',
  data () {
    return {}
  },
  computed: {
    adminMenus () {
      return this.$store.state.adminMenus
    },
    currentPath () {
      return this.$route.path
    }
  },
  methods: {
    editKeyWords () {
      this.$emit('editKeywords')
    },
    handleSelect (key, keyPath) {
      console.log(key)
      console.log(keyPath)
    }
  }
}
</script>

<style>
   .el-menu-admin {
      height: 1000px;
      width: 200px;
      /* background-color: #545c64;
      text-color: #fff;
      active-text-color: #ffd04b;
      border-radius: 5px; */
    }
</style>
