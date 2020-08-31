<template>
  <el-menu
    default-active="全部"
    class="sideMenu"
    @select="handleSelect"
    active-text-color="red"
  >
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
      category: [],
      categoryOptions: []
    }
  },
  mounted: function () {
    this.loadCategoryOptions()
  },
  methods: {
    loadCategoryOptions () {
      var url = '/categories/' + this.$store.state.user.username
      this.$axios
        .get(url)
        .then(resp => {
          this.categoryOptions = new Array('全部').concat(resp.data)
          this.category = this.categoryOptions
          this.$emit('load')
        })
        .catch(resp => {
          console.log('关键字初始化失败')
        })
    },
    handleSelect (keyword) {
      this.category = (keyword === '全部') ? this.categoryOptions : [keyword]
      this.$emit('categorySelect')
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
