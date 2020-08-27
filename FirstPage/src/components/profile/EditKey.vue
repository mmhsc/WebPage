<template>
  <div>
    <el-dialog
      title="选择你的关键词"
      :visible.sync="dialogVisible">
      <span slot="footer" class="dialog-footer"></span>
      <el-checkbox-group v-model="keywords">
        <el-checkbox v-for="keyword in keywordOptions" :label="keyword" :key="keyword">{{keyword}}</el-checkbox>
        <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
      </el-checkbox-group>
      <br><br>
      <el-button size="small" type="primary" @click="submit">提交</el-button>
    </el-dialog>
  </div>
</template>

<script>
var keywordOptions = ['公安', '教育', '法院', '消防']
export default {
  name: 'EditKey',
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
    handleCheckAllChange (val) {
      this.checkedKeywords = val ? keywordOptions : []
      this.isIndeterminate = false
    },
    submit () {
      this.dialogVisible = false
      this.$axios
        .post('/subscribe', {'keywords': this.keywords})
        .then(successsResponse => {
          alert('修改成功')
          console.log('修改成功')
        })
        .catch(failResponse => {
          console.log(failResponse.data)
        })
    }
  }
}
</script>

<style scoped>

</style>
