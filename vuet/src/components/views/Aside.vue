<template>
  <!-- overflow-x:hidden把超出来的部门隐藏 -->
  <div>
    <el-menu
      style="overflow-x: hidden"
      :collapse="isCollapse"
      :collapse-transition="false"
      active-text-color="#ffd04b"
      background-color="rgb(48,65,86)"
      text-color="#fff"
      :default-openeds="opens"
      router
      class="left-menu"
      @select="handleSelect"
    >
      <div style="height: 60px; line-height: 60px; text-align: center">
        <img
          src="../../assets/logo.png"
          alt=""
          style="width: 20px; position: relative; top: 5px; margin-right: 5px"
        />
        <b style="color: white" v-show="logoTextShow">学生就业管理系统</b>
      </div>

       <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id + ''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div  v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
          </el-submenu>
        </div>
      </div>
    </el-menu>
  </div>
</template>
<script>
export default {
  props: ["logoTextShow", "isCollapse"],
  methods: {
    handleSelect(index) {
      // this.$route.fullPath.split("/")
      console.log(index);
      console.log(this.$router.options.routes);
    },
  },
  data() {
    return {
      menus: localStorage.getItem("menus")
        ? JSON.parse(localStorage.getItem("menus"))
        : {},
        opens:localStorage.getItem("menus")
        ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '')
        : {}
    }
  },
  created() {
    console.log("22", this.menus);
  },
};
</script>

<style scoped>
/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
  visibility: hidden;
}

.left-menu{
    /* height: 100vh; */
    height: 1200px;
}
</style>