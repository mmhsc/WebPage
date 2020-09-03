<template>
  <div :id="id" style="width: 300px; height: 300px"></div>
</template>

<script>
export default {
  name: 'PieChart',
  data () {
    return {
    }
  },
  props: ['id', 'name', 'data'],
  mounted () {
    this.drawLine()
  },
  methods: {
    drawLine () {
      // 基于准备好的dom，初始化echarts实例
      this.myChart = this.$echarts.init(document.getElementById(this.id))
      // 绘制图表
      this.option = ({
        series: [
          {
            name: this.name,
            type: 'pie',
            radius: '55%',
            roseType: 'angle',
            data: this.data
          }
        ]
      })
      this.myChart.setOption(this.option)
    }
  }
}
</script>
