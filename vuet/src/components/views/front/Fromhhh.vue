<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div>
          <el-card>
            <div class="xxxx">
              <div class="ggg">
                <p>
                  <img :src="Book.src" alt="" width="240px" />
                </p>
                <p style="font-size: 26px">{{ Book.title }}</p>
              </div>
              <div style="margin-top: 30px">
                <p style="font-size: 24px">
                  创始人：<span>{{ Book.author }}</span>
                </p>
                <p style="font-size: 24px">
                  介绍：<span>{{ Book.introduce }}</span>
                </p>
                <p style="font-size: 24px">
                  面试时间：<span>{{ Book.createtime }}</span>
                </p>
                <p style="font-size: 24px">
                  薪资：<span>{{ Book.monney }}</span>
                </p>
                <div>
                  <p class="ml" style="font-size: 24px">
                    <i class="el-icon-reading"></i> 要求：
                  </p>
                  <div>
                    <p>学历要求：---{{ Book.ccc }}</p>
                    <p>技术要求：---{{ Book.xxx }}</p>
                    <p>招聘岗位---{{ Book.gangwei }}</p>
                    <p>电话---{{ Book.phone }}</p>
                    <p>公司面试地址：---{{ Book.address }}</p>
                  </div>
                </div>
                <p>
                  <el-rate v-model="value" disabled show-score text-color="#ff9900" score-template="{value}">
                  </el-rate>
                </p>
                <p>
                  <el-button type="success" class="btn" v-if="gongBoolean" @click="serachGongsi">点我查看公司地址</el-button>
                  <el-button type="danger" class="btn" v-else @click="guanbiGoogsi">点我关闭公司地址</el-button>
                </p>
                <div id="container" v-show="mapBoolean" style="width: 100%;  height: calc(100vh - 400px)"></div>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>
      <el-col :span="11" offset="1">
        <el-row class="box_row">
          <el-col :span="11" v-for="item in items" :key="item.id">
            <el-card style="text-align: center">
              <p>
                <img @click="goTo(item.id)" :src="item.src" alt="" width="100%" height="200px" />
              </p>
              <p>{{ item.title }}</p>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import request from "@/utils/request";
var content = [
  "<div style='font-size: 14px; color: red; width: 200px; height: 50px'>这是信息窗口</div>"
];
export default {
  data() {
    return {
      Book: {},
      value: 3.7,
      items: [],
      gongBoolean: true,
      mapBoolean: false
    };
  },
  methods: {
    goTo(id) {
      this.$router.push("/formhhh/" + id);
      this.xxx();
    },
    xxx() {
      let BookId = this.$route.params.id;
      this.request.get("/zhaopin/" + BookId).then((res) => {
        console.log(res);
        this.Book = res.data;
      });
    },
    serachGongsi() {
      this.gongBoolean = false;
      this.mapBoolean = true;
    },
    guanbiGoogsi() {
      this.gongBoolean = true;
      this.mapBoolean = false;
    }
  },
  created() {
    this.xxx();
    this.request.get("/zhaopin").then((res) => {
      console.log("你好", res);
      if (res.code === "200") {
        this.items = res.data;
      }
    });
  },
  mounted() {
    // 创建地图实例
    var map = new AMap.Map("container", {
      zoom: 12,
      // center: [116.396901,39.919544],
      resizeEnable: true
    })
    var infoWindow = new AMap.InfoWindow({
      anchor: 'top-right',
      content: content.join("<br>")  //传入 dom 对象，或者 html 字符串
    });
    var clickHandler = function (e) {
      console.log('您在[ ' + e.lnglat.getLng() + ',' + e.lnglat.getLat() + ' ]的位置点击了地图！');

      infoWindow.open(map, [116.396901, 39.919544]);
    };
    // 绑定事件
    // map.on('click', clickHandler);
    var marker = new AMap.Marker({
      position: new AMap.LngLat(116.396901, 39.919544),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
      title: '北京故宫',
      icon: '//vdata.amap.com/icons/b18/1/2.png',
      // content: '<div style="font-size: 12px; width: 100px">这是我自定义的内容</div>'
    })
    marker.on('click', clickHandler);
    // 将创建的点标记添加到已有的地图实例：
    map.add(marker);
    // path 是驾车导航的起、途径和终点，最多支持16个途经点
    var path = []
    path.push([116.303843, 39.983412])
    path.push([116.321354, 39.896436])
    path.push([116.407012, 39.992093])
    map.plugin('AMap.DragRoute', function () {
      var route = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_FEE)
      // 查询导航路径并开启拖拽导航
      route.search()
    })
    var polyLine = new AMap.Polyline({
      path: path,
      strokeWeight: 5,
      borderWeight: 5, // 线条宽度，默认为 1
      strokeStyle: "solid",
      strokeColor: '#DC143C', // 线条颜色
      lineJoin: 'round' // 折线拐点连接处样式
    })
    map.add(polyLine)
    //实例化城市查询类
    map.plugin('AMap.Geolocation', function () {
      var geolocation = new AMap.Geolocation({
        // 是否使用高精度定位，默认：true
        enableHighAccuracy: true,
        // 设置定位超时时间，默认：无穷大
        timeout: 10000,
        // 定位按钮的停靠位置的偏移量
        offset: [10, 20],
        //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
        zoomToAccuracy: true,
        //  定位按钮的排放位置,  RB表示右下
        position: 'RB'
      })
      geolocation.getCurrentPosition(function (status, result) {
        if (status == 'complete') {
          onComplete(result)
        } else {
          onError(result)
        }
      });
      function onComplete(data) {
        // data是具体的定位信息
        console.log(data)
      }
      function onError(data) {
        // 定位出错
        console.error(data)
      }
    })
  },
};
</script>

<style scoped>
.btn {
  float: right;
  margin: 15px;
}

.xxxx .ggg {
  text-align: center;
}

.ggg p img {
  box-shadow: 0 2px 2px 0 rgba(0, 0, 0, 0.3);
  width: 200px;
  height: 130px;
}

.bookRow {
  margin-top: 50px;
}

.xxxx p {
  margin-top: 5px;
}

.xxxx p span {
  letter-spacing: 3px;
  font-weight: 300;
  font-family: "宋体";
}

.box_row .el-col {
  margin: 5px 10px;
}
</style>