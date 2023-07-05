<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="12">
        <div id="main" style="width: 650px; height: 450px"></div>
      </el-col>
      <el-col :span="12" class="nuli">
        <h1 style="font-size: 40px; margin-left: 120px">软件技术覃麒麟</h1>
        <div class="zzz">
          <el-row>
            <el-col :span="12" style="line-height: 30px">
              <ul>
                <li>
                  <h1>前端</h1>
                </li>
                <li>html5</li>
                <li>css3</li>
                <li>javaScript</li>
                <li>jQurey</li>
                <li>vue</li>
                <li>react</li>
                <li>微信小程序</li>
                <li>uni-app</li>
              </ul>
            </el-col>
            <el-col :span="12" style="line-height: 30px">
              <ul>
                <li>
                  <h1>后端</h1>
                </li>
                <li>java</li>
                <li>spring</li>
                <li>mybatis</li>
                <li>spring_mvc</li>
                <li>springboot</li>
              </ul>
            </el-col>
          </el-row>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div class="xxx">
          <h1 style="font-size: 40px">标题</h1>
          <ul class="item">
            <li><h1>世上无难事，只要肯登攀</h1></li>
            <li><h1>人定兮胜天，半壁久无胡日月</h1></li>
            <li><h1>千磨万击还坚劲，任尔东西南北风</h1></li>
            <li><h1>努力不一定成功,但是不努力就一定会失败</h1></li>
            <li><h1>一直游不一定淹死,但停在水上就一定会淹死</h1></li>
          </ul>
        </div>
      </el-col>
      <el-col :span="12">
        <div
          id="main2"
          style="width: 650px; height: 450px;margin-top:-30px;margin-left:-60px"
        ></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  mounted() {
    var chartDom = document.getElementById("main");
    var myChart = echarts.init(chartDom);
    var option;
    option = {
      title: {
        text: "你所付出",
      },
      legend: {
        data: ["天赋", "卓越"],
      },
      radar: {
        // shape: 'circle',
        indicator: [
          { name: "努力", max: 6500 },
          { name: "付出", max: 16000 },
          { name: "奋斗", max: 30000 },
          { name: "失望", max: 38000 },
          { name: "积攒", max: 52000 },
          { name: "收获", max: 25000 },
        ],
      },
      series: [
        {
          name: "Budget vs spending",
          type: "radar",
          data: [
            {
              value: [4200, 3000, 20000, 35000, 50000, 18000],
              name: "天赋",
            },
            {
              value: [5000, 14000, 28000, 26000, 42000, 21000],
              name: "卓越",
            },
          ],
        },
      ],
    };
    option && myChart.setOption(option);

    var chartDom = document.getElementById("main2");
    var myChart2 = echarts.init(chartDom);
    option = {
      tooltip: {
        trigger: "item",
      },
      legend: {
        top: "5%",
        left: "center",
      },
      series: [
        {
          name: "Access From",
          type: "pie",
          radius: ["40%", "70%"],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: "center",
          },
          emphasis: {
            label: {
              show: true,
              fontSize: "40",
              fontWeight: "bold",
            },
          },
          labelLine: {
            show: false,
          },
          data: [
            { value: 1048, name: "努力" },
            { value: 735, name: "付出" },
            { value: 580, name: "收货" },
            { value: 484, name: "失望" },
            { value: 300, name: "奋斗" },
          ],
        },
      ],
    };
    option && myChart2.setOption(option);
  },
};
</script>
<style scoped>
.nuli {
  margin-top: 10px;
}
.nuli h1 {
  margin-top: 10px;
}
.xxx{
  text-align: center;
  margin-right: 170px;
  margin-top: 30px;
}
ul li{
  list-style: none;
}
.item li{
  margin-top: 10px;
}
</style>