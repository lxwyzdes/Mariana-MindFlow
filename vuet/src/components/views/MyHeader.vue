<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1;">
      <span
        :class="collapseBtnClass"
        style="cursor: pointer;
        font-size: 18px"
        @click="collapse"
      ></span>

      <div style="display:inline-block;margin-left:10px">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="'/login'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    </div>
    <el-dropdown style="width: 100px; cursor: pointer">
      <div style="">
        <img :src="user.avatarUrl" class="img" alt="">
        <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div> 
      <el-dropdown-menu slot="dropdown" style="width:100px;height:80px;text-align:center">
        <el-dropdown-item>
          <router-link to="/password">修改密码</router-link>
        </el-dropdown-item>
        <el-dropdown-item>
          <router-link to="/person">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item>
          <span style="text-decoration:none" @click="logort">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>
<script>
export default {
  props: ["collapseBtnClass"],
  methods: {
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 64;
        this.collapseBtnClass = "el-icon-s-unfold";
        this.logoTextShow = false;
      } else {
        this.sideWidth = 200;
        this.collapseBtnClass = "el-icon-s-fold";
        this.logoTextShow = true;
      }
    },
  },
   computed: {
    currentPathName () {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  data(){
    return{
      user:localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) :{}
    }
  },
  methods:{
    collapse(){
      this.$emit("asideCollapse");
    },
    logort(){
      this.$store.commit("logout")
      // this.$router.push("/login");
      // localStorage.removeItem("user");
      this.$message.success("退出成功");
    }
  },
  created(){
    console.log('11',this.user);
  }
};
</script>

<style scoped>
.img{
  width: 30px;
  height: 30px;
  border-radius: 50%;
  float: left;
  margin: 13px 5px;
  overflow: hidden;
}
</style>