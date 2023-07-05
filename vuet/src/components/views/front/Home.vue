<template>
  <div class="ggg">
    <el-container>
    <el-main>
    <div style="margin: 10px 0">
      <el-carousel :interval="4000" type="card" height="550px">
    <el-carousel-item v-for="item in imgs" :key="item">
      <img :src="item.src" alt="" style="width:100%;height:550px;overflow: hidden;">
    </el-carousel-item>
  </el-carousel>
    </div>
    <el-row style="margin-top:50px;" class="qqq">
      <el-col :span="16">
          <el-card>
            <div class="qy_n">课程管理</div>
          <div class="item">
          <div v-for="item in items" :key="item.id" class="item-list" @click="bookGo(item.id)">
             <img :src="item.src" alt="">
             <p>{{item.title}}</p>
          </div>
          </div>
          </el-card>
          <el-card>
            <div class="qy_n">招聘管理</div>
          <div class="item">
          <div v-for="item in zhaopinList" :key="item.id" class="item-list" @click="bookTo(item.id)">
             <img :src="item.src" alt="">
             <p>{{item.title}}</p>
          </div>
          </div>
          </el-card>
      </el-col>
      <el-col style="margin-bottom:20px" :offset="1" :span="7">
         <el-card>
            <h1>书名</h1>
            <div class="qql">
               <a href="#">Vue</a>
               <a href="#">React</a>
               <a href="#">javascript</a>
            </div>
         </el-card>
      </el-col>
      <el-col :offset="1" :span="7">
         <el-card class="col">
           <h1>努力的方向</h1>
           <p style="font-size:14px;color:gray;margin-top:10px;">学海无涯，本着循序渐进的原则，我对学习一直有着明确的目标和规划，合理安排时间，认真努力学好专业知识技能，亦涉猎其他图书，扩充自己的知识储备和视野深度，了解时事，增强自己的学习意识和社会危机感。</p>
           <h1 style="margin-top:10px;">人际管理</h1>
           <p style="font-size:14px;color:gray;margin-top:10px;">与室友、同班同学以及同事之间的关系良好，能够与大家互帮互助，团结一致，共同学习和工作，努力搞好同学、朋友之间的关系，为自己的交际方面和工作方面营造良好的条件，努力使自己的大学生活过得精彩而充实。</p>
         </el-card>
      </el-col>
      <el-col :offset="1" :span="7">
         <el-card>
           <h1>文件管理</h1>
           <div v-for="item in fileListTwo" class="www" :key="item.id">
             <span @click="ddd(item.url)">{{item.name}}</span>
           </div>
         </el-card>
      </el-col>
    </el-row>
    </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  name: "FrontHome",
  data(){
      return{
          imgs:[
            {
              id:1,
              src: 'https://img0.baidu.com/it/u=2288111133,2236696121&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500'
            },
            {
              id:2,
              src: 'https://img1.baidu.com/it/u=2554614510,1634648540&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=629'
            },
            {
              id:3,
              src:'https://img1.baidu.com/it/u=2229886994,797950867&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500'
            },
            {
              id:4,
              src:'https://img0.baidu.com/it/u=215016291,1461517686&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500'
            }   
          ],
          items:[],
          files:[],
          zhaopinList:[],
          fileListTwo:[]
      }
  },
  created(){
    this.request.get("/echarts/file/front/all").then(res=>{
       console.log(res);
       this.files = res.data.filter(v => v.type === 'png' || v.type === 'jpg' || v.type === 'webp')
    });
    this.request.get("/anlian").then(res=>{
         if(res.code === "200"){
            this.items = res.data;
         }
    });
    this.request.get("/zhaopin").then(res=>{
      if(res.code === "200"){
            this.zhaopinList = res.data;
         }
    });
    this.request.get("/file").then(res=>{
      this.fileListTwo = res.data.filter(v => v.type == 'docx' || v.type == 'doc')
      console.log("文件",res.data);
    });
  },
  methods:{
    bookGo(id){
       this.$router.push("/fromBook/"+id);
    },
    bookTo(id){
      this.$router.push("/formhhh/"+id);
    },
    ddd(row){
      // console.log(row);
      window.open(row);
    }
  }
};
</script>
<style scoped>
.el-card{
  margin-top: 20px;
}
.qy_n{
  font-size: 24px;
  padding: 5px;
}
.box{
  border: 1px solid gray;
  height: 400px;
  margin: 10px;
}
.item{
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  align-items: center;
  /* background-color: gray; */
}
.item .item-list{
  width: 22%;
  box-shadow: 0 5px 15px 0 rgba(0, 0,0, 0.5);
  padding: 20px 5px 5px 5px;
  margin-top: 10px;
  /* background-color: white; */
  text-align: center;
  margin: 10px;
}
.item .item-list:hover{
  cursor: pointer;
  margin-top: -2px;
}
.item .item-list img{
  width: 185px;
  height: 132px;
  overflow: hidden;
}
.item .item-list p{
  font-size: 18px;
  color: gray;
}
a{
  text-decoration: none;
  color: black;
  font-size: 18px;
  margin: 0 20px;
}
.qql {
  margin-top: 20px;
  text-align: center;
}
.col{
  height: 290px;
}
.www span{
  cursor: pointer;
  text-decoration: underline;
  float: left;
  margin: 10px 10px;
}
</style>