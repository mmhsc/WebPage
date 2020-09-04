<template>
    <div :id="id" style="width: 300px; height: 300px"></div>
</template>

<script>
export default {
  name: 'LineChart',
  data () {
    return {
      id: '',
      title: '',
      date: [],
      budget: []
    }
  },
  methods: {
    draw () {
      // 基于准备好的dom，初始化echarts实例
      this.myChart = this.$echarts.init(document.getElementById(this.id))
      // 绘制图表
      let option = {
        // title: this.title,
        xAxis: {
          type: 'category',
          data: this.date
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.budget,
          type: 'line',
          smooth: true
        }]
      }
      this.myChart.setOption(option)
    }
  }
}
</script>

<style scoped>

</style>
