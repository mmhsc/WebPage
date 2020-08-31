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
export default {
  name: 'EditKey',
  data () {
    return {
      dialogVisible: false,
      checkAll: false,
      keywords: [],
      keywordOptions: [],
      isIndeterminate: true
    }
  },
  mounted: function () {
    this.initKeywordOptions()
  },
  methods: {
    initKeywordOptions () {
      this.$axios
        .get('/categories')
        .then(resp => {
          this.keywordOptions = resp.data
        })
        .catch(resp => {
          console.log('关键字初始化失败')
        })
    },
    handleCheckAllChange (val) {
      this.checkedKeywords = val ? this.keywordOptions : []
      this.isIndeterminate = false
    },
    submit () {
      this.dialogVisible = false
      this.$axios
        .post('/subscribe', {'keywords': this.keywords})
        .then(successsResponse => {
          alert('修改成功')
        })
        .catch(failResponse => {
          alert('修改失败')
        })
    }
  }
}
</script>

<style scoped>

</style>
