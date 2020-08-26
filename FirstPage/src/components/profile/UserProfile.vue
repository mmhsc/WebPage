<template>
  <el-container>
    <el-aside style="width: 200px; margin-top: 20px">
      <sideMenu @editKeywords="edit"></sideMenu>
    </el-aside>
    <el-main>
      <el-dialog
        title="选择你的关键词"
        :visible.sync="dialogVisible">
        <span slot="footer" class="dialog-footer"></span>
        <!-- <div style="margin: 15px 0;"></div> -->
        <el-checkbox-group v-model="keywords">
          <el-checkbox v-for="keyword in keywordOptions" :label="keyword" :key="keyword">{{keyword}}</el-checkbox>
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        </el-checkbox-group>
        <br><br>
        <el-button size="small" type="primary" @click="submit">提交</el-button>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import SideMenu from './SideMenu'
const keywordOptions = ['公安', '教育', '法院', '消防']
export default {
  name: 'UserProfile',
  components: {SideMenu},
  data () {
    return {
      dialogVisible: false,
      checkAll: false,
      keywords: [],
      keywordOptions: keywordOptions,
      isIndeterminate: true
    }
  },
  methods: {
    edit () {
      this.dialogVisible = true
    },
    handleCheckAllChange (val) {
      this.checkedKeywords = val ? keywordOptions : []
      this.isIndeterminate = false
    },
    submit () {
      this.$axios
        .post('/subscribe', {'keywords': this.keywords})
        .then(successsResponse => {
          console.log(successsResponse.data)
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
