<template>
    <div :id="id" class="line"></div>
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
        title: {
          text: this.title,
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: this.date,
          axisLabel: {
            interval: 0
          }
        },
        yAxis: {
          type: 'value'
        },
        tooltip: {
          formatter: '日期: {b}<br/>' + '金额: {c} 万'
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
.line{
  position: center;
  width: 100%;
  height: 100%;
}
</style>
