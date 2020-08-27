<template>
  <el-menu
    default-active="全部"
    class="sideMenu"
    @select="handleSelect"
    active-text-color="red"
  >
    <el-menu-item index="全部">
      <i class="el-icon-menu"></i>
      <span slot="title">全部</span>
    </el-menu-item>
    <el-menu-item v-for="category in categoryOptions" :key="category" :index="category">
      <i class="el-icon-menu"></i>
      <span slot="title">{{category}}</span>
    </el-menu-item>

  </el-menu>
</template>

<script>
export default {
  name: 'SideMenu',
  data () {
    return {
      category: '',
      categoryOptions: []
    }
  },
  mounted: function () {
    this.loadCategoryOptions()
  },
  methods: {
    loadCategoryOptions () {
      this.$axios
        .get('/categories')
        .then(resp => {
          this.categoryOptions = resp.data
        })
        .catch(resp => {
          console.log('关键字初始化失败')
        })
    },
    handleSelect (key) {
      this.category = key
      this.$emit('indexSelect')
    }
  }
}
</script>

<style>
.sideMenu {
  position: fixed;
  margin-left: auto;
}
</style>
