<template>
    <div :id="id" style="width: 300px; height: 300px"></div>
</template>

<script>
export default {
  name: 'LineChart',
  data () {
    return {
    }
  },
  props: ['id', 'title', 'xData', 'yData'],
  mounted () {
    this.drawLine()
  },
  methods: {
    drawLine () {
      // 基于准备好的dom，初始化echarts实例
      this.myChart = this.$echarts.init(document.getElementById(this.id))
      // 绘制图表
      this.option = ({
        title: this.title,
        xAxis: {
          type: 'category',
          data: this.xData
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.yData,
          type: 'line',
          smooth: true
        }]
      })
      this.myChart.setOption(this.option)
    }
  }
}
</script>

<style scoped>

</style>
