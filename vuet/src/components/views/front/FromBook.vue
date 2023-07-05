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
                  作者：<span>{{ Book.author }}</span>
                </p>
                <p style="font-size: 24px">
                  介绍：<span>{{ Book.introduce }}</span>
                </p>
                <p style="font-size: 24px">
                  发布时间：<span>{{ Book.releaseTime }}</span>
                </p>
                <div>
                  <p class="ml" style="font-size: 24px">
                    <i class="el-icon-reading"></i> 目录：
                  </p>
                  <div>
                    <p>---{{ Book.title }}</p>
                  </div>
                </div>
                <p>
                  <el-rate v-model="value" disabled show-score text-color="#ff9900" score-template="{value}">
                  </el-rate>
                </p>
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
export default {
  data() {
    return {
      Book: {},
      value: 3.7,
      items: [],
    };
  },
  methods: {
    goTo(id) {
      this.$router.push("/fromBook/" + id);
      this.xxx();
    },
    xxx() {
      let BookId = this.$route.params.id;
      this.request.get("/anlian/" + BookId).then((res) => {
        console.log(res);
        this.Book = res.data;
      });
    },
  },
  created() {
    this.xxx();
    this.request.get("/anlian").then((res) => {
      console.log("你好", res);
      if (res.code === "200") {
        this.items = res.data;
      }
    });
  },
};
</script>

<style scoped>
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