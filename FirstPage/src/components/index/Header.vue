<template>
  <div>
    <el-row :gutter="20" class="mgb20">
        <el-col :span="6">
            <el-card shadow="hover" :body-style="{padding: '0px'}">
                <div class="grid-content grid-con-1">
                    <i class="el-icon-info grid-con-icon"></i>
                    <div class="grid-cont-right">
                      <div class="grid-num">{{provinceInfo.num}}</div>
                      <div>近一周省级招标数</div>
                    </div>
                </div>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card shadow="hover" :body-style="{padding: '0px'}">
                <div class="grid-content grid-con-2">
                    <i class="el-icon-chat-dot-round grid-con-icon"></i>
                    <div class="grid-cont-right">
                        <div class="grid-num">{{provinceInfo.totalBudget}}万</div>
                        <div>近一周省级招标总额</div>
                    </div>
                </div>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card shadow="hover" :body-style="{padding: '0px'}">
                <div class="grid-content grid-con-3">
                    <i class="el-icon-info grid-con-icon"></i>
                    <div class="grid-cont-right">
                        <div class="grid-num">{{cityInfo.num}}</div>
                        <div>近一周市级招标数</div>
                    </div>
                </div>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card shadow="hover" :body-style="{padding: '0px'}">
                <div class="grid-content grid-con-4">
                    <i class="el-icon-chat-dot-round grid-con-icon"></i>
                    <div class="grid-cont-right">
                        <div class="grid-num">{{cityInfo.totalBudget}}万</div>
                        <div>近一周市级招标总额</div>
                    </div>
                </div>
            </el-card>
        </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data () {
    return {
      cityInfo: {
        num: 0,
        totalBudget: 0.0
      },
      provinceInfo: {
        num: 0,
        totalBudget: 0.0
      }
    }
  }
}
</script>

<style scoped>
.el-row {
    margin-bottom: 20px;
}
.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}
.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}
.grid-num {
    height: 50%;
    position: center;
    font-size: 30px;
    font-weight: bold;
}
.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}
.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}
.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}
.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}
.grid-con-2 .grid-num {
    color: rgb(100, 213, 114);
}
.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}
.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}
.grid-con-4 .grid-con-icon {
    background: rgb(170, 255, 0);
}
.grid-con-4 .grid-num {
    color: rgb(170, 255, 0);
}
.mgb20 {
    margin-bottom: 20px;
}
</style>
