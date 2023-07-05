<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 40px">
      <el-col :span="6">
        <el-card style="color: #409eff">
          <div><i class="el-icon-user-solid"></i>用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67c23a">
          <div><i class="el-icon-s-check"></i>销售总量</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            $10000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #e6a23c">
          <div><i class="el-icon-s-finance"></i>收益总额</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            $30000000
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #909399">
          <div><i class="el-icon-s-flag"></i>门店总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            200
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 650px; height: 350px"></div>
        <div
          id="pie"
          style="width: 700px; height: 350px;"
        ></div>
      </el-col>
      <el-col :span="12">
         <div
          id="main2"
          style="width: 680px; height: 700px;margin:20px -40px;"
        ></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  data() {
    return {};
  },
  mounted() {
    var option;
    var chartDom = document.getElementById("main");
    var myChart = echarts.init(chartDom);
   option = {
      title: {
        text: "面试人数",
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

    this.request.get("/echarts/members").then((res) => {
      // 填空
      // option.xAxis.data = res.data.x;
      option.series[0].data = res.data;
      option.series[1].data = res.data;
      myChart.setOption(option);

      Pieoption.series[0].data = [
        { name: "第一季度", value: res.data[0] },
        { name: "第二季度", value: res.data[1] },
        { name: "第三季度", value: res.data[2] },
        { name: "第四季度", value: res.data[3] },
      ];
      myChartPie.setOption(Pieoption);
    });

    var chartDomPie = document.getElementById("pie");
    var myChartPie = echarts.init(chartDomPie);
    var Pieoption = {
      title: {
        text: "各季度应聘数量统计",
        subtext: "比例图",
        left: "center",
      },
      tooltip: {
        trigger: "item",
      },
      legend: {
        orient: "vertical",
        left: "left",
      },
      series: [
        {
          type: "pie",
          radius: "60%",
          label: {
            //饼图图形上的文本标签
            normal: {
              show: true,
              position: "inner", //标签的位置
              textStyle: {
                fontWeight: 300,
                fontSize: 14, //文字的字体大小
                color: "#fff",
              },
              formatter: "{d}%",
            },
          },
          data: [], // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
        },
      ],
    };


    var chartDom = document.getElementById("main2");
    var myChart2 = echarts.init(chartDom);
    option = {
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b}: {c} ({d}%)'
  },
  legend: {
    data: [
      '阿里巴巴',
      '华为',
      '京东',
      '字节跳动'
    ]
  },
  series: [
    {
      name: '员工',
      type: 'pie',
      selectedMode: 'single',
      radius: [0, '30%'],
      label: {
        position: 'inner',
        fontSize: 14
      },
      labelLine: {
        show: false
      },
      data: [
       { value: 1580, name: '应聘情况' },
        { value: 775, name: '实践人数' },
        { value: 279, name: '解约人数'}
      ]
    },
    {
      name: '公司',
      type: 'pie',
      radius: ['45%', '60%'],
      labelLine: {
        length: 30
      },
      label: {
        formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
        backgroundColor: '#F6F8FC',
        borderColor: '#8C8D8E',
        borderWidth: 1,
        borderRadius: 4,
        rich: {
          a: {
            color: '#6E7079',
            lineHeight: 22,
            align: 'center'
          },
          hr: {
            borderColor: '#8C8D8E',
            width: '100%',
            borderWidth: 1,
            height: 0
          },
          b: {
            color: '#4C5058',
            fontSize: 14,
            fontWeight: 'bold',
            lineHeight: 33
          },
          per: {
            color: '#fff',
            backgroundColor: '#4C5058',
            padding: [3, 4],
            borderRadius: 4
          }
        }
      },
      data: [
        { value: 1048, name: '阿里巴巴' },
        { value: 335, name: '华为' },
        { value: 310, name: '京东' },
        { value: 102, name: '字节跳动' }
      ]
    }
  ]
    };
    option && myChart2.setOption(option);
  },
};
</script>

<style scoped>
.good {
  margin-top: 100px !important;
}
.zzz {
  text-align: center;
}
ul li {
  list-style: none;
}
</style>