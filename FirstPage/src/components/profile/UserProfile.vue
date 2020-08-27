<template>
  <el-container>
    <el-aside style="width: 200px; margin-top: 20px">
      <sideMenu @editKeywords="edit"></sideMenu>
    </el-aside>
    <el-main>
      <EditKey ref="editKey"></EditKey>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
import EditKey from './EditKey'
export default {
  name: 'UserProfile',
  components: {SideMenu, EditKey},
  methods: {
    edit () {
      var url = '/categories/' + this.$store.state.user.username
      this.$axios
        .get(url)
        .then(Response => {
          this.$refs.editKey.keywords = Response.data
          this.$refs.editKey.dialogVisible = true
        })
        .catch(failResponse => {
          console.log(failResponse.data)
        })
    }
  }
}
</script>

<style>
</style>
