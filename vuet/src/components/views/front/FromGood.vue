<template>
  <div class="sss-list">
    <div class="qql">
      <h1>欢迎来到我的课程</h1>
      <p>我所学习的课程</p>
    </div>
    <div class="xxx">
      <el-card v-for="item in imgList" :key="item.id" class="item">
        <div @click="listGo(item.id)">
          <img :src="item.src" alt="" />
          <p class="book_n">书名：{{item.title}}</p>
          <p>作者：{{item.author}}</p>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  data() {
    return {
      imgList: [],
    };
  },
  created(){
    this.request.get("/anlian").then(res=>{
      console.log(res);
      this.imgList = res.data;
    })
  },
  methods:{
    listGo(id){
      this.$router.push("/fromBook/"+id);
    }
  }
};
</script>

<style scoped>
.book_n{
  font-size: 20px;
}
.sss-list {
  /* 清除浮动 */
  overflow: hidden;
}
.qql {
  /* background-color:#FFF5EE; */
  background-image: linear-gradient(to bottom right, #1de5e2, #b588f7);
  box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  height: 1000px;
  position: relative;
  overflow: hidden;
}
.qql h1 {
  margin: 80px 550px;
  color: white;
  float: left;
}
.qql p {
  float: left;
  margin: 0px 624px;
  color: white;
  font-size: 18px;
}
.xxx {
  position: absolute;
  margin: -690px 20px;
  width: 1400px;
  height: 400px;
}
.item {
  width: 300px;
  height: 250px;
  float: left;
  text-align: center;
  margin: 20px 20px;
}
.item h1 {
  font-family: "隶书";
}
.item div:hover{
  cursor: pointer;
}
.item img {
  width: 200px;
  height: 120px;
  overflow: hidden;
}
.item p {
  margin-top: 10px;
}
</style>