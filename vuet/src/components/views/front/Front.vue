<template>
  <div>
    <!-- 头部 -->
    <div
      style="
        display: flex;
        height: 60px;
        line-height: 60px;
        border-bottom: 1px solid #eee;
      "
    >
      <div style="width: 280px; text-align: center" class="good">
          <img width="40px" style="border-radius: 100%;height:40px;margin-top:10px"  src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201911%2F03%2F20191103074802_WCsT2.thumb.700_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651469836&t=72a68469488fc75e3ea4812f05c89307" alt="">
          <span>
              欢迎来到{{user.nickname}}系统
          </span>
          </div>
      <div style="flex: 1">
        <!-- 导航菜单 -->
        <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal" router>
          <el-menu-item index="/frontHome">首页</el-menu-item>
          <el-menu-item index="/fromGood">学习的课程</el-menu-item>
        </el-menu>
      </div>
      <div style="width: 200px">
        <div
          v-if="!user.username"
          style="text-align: right; padding-right: 30px"
        >
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown style="width: 100px; cursor: pointer">
             <div style="">
              <img :src="user.avatarUrl" class="imgT" alt="" />
              <span>{{ user.nickname }}</span
              ><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu
              slot="dropdown"
              style="width: 100px; height: 80px; text-align: center"
            >
              <el-dropdown-item>
                <router-link to="/frontPassword">修改信息</router-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <router-link to="/frontPerson">个人信息</router-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <span style="text-decoration: none" @click="logort">退出</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </div>

    <div style="width:1400px; margin:20px auto;">
        <router-view />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user"))
        : {},
    };
  },
  methods: {
    logort() {
      this.$store.commit("logout");
      // this.$router.push("/login");
      // localStorage.removeItem("user");
      this.$message.success("退出成功");
    },
  },
};
</script>

<style scoped>
.list {
  list-style: none;
  background-color: #98fb98;
}
.list li {
  color: #1e90ff;
  cursor: pointer;
  text-align: center;
  display: inline-block;
  width: 100px;
}
.list li:hover {
  background-color: lightpink;
}
.list li a {
  text-decoration: none;
}
.good{
    display: flex;
}
.good img{
    margin-left: 20px;
}
.good span{
    margin-left: 20px;
}
.imgT{
  width: 30px;
  height: 30px;
  border-radius: 50%;
  float: left;
  margin: 15px 5px;
  overflow: hidden;
}
</style>