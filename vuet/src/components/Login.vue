<template>
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../assets/logo.png" alt="" />
      </div>
      <div class="good">
        <h1>登录</h1>
      </div>
      <!-- 登录表单区域 -->
      <el-form class="login_form" :model="loginForm" :rules="loginFormRules" ref="loginFormRef">
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="loginForm.username" name="username" placeholder="请输入用户名">
          </el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-s-grid" v-model="loginForm.password" type="password" name="password"
            placeholder="请输入密码"></el-input>
        </el-form-item>
        <!-- 按钮区域 -->
        <el-form-item class="btns">
          <el-button type="primary" icon="el-icon-search" size="small" @click="login('loginFormRef')"
            @keyup.enter="login">登录</el-button>
          <el-button icon="el-icon-eleme" type="info" size="small" @click="resetLogin('loginFormRef')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { setRoutes } from "@/router";
export default {
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
      },
      loginFormRules: {
        username: [
          { required: true, message: "请输入登录名称", trigger: "blur" },
          { min: 3, max: 10, message: "长度在3到10个字符", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入登录密码", trigger: "blur" },
          { min: 3, max: 15, message: "长度在3到15个字符", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    //绑定事件
    window.addEventListener("keydown", this.keyDown);
  },
  methods: {
    keyDown(e) {
      //如果是回车则执行登录方法
      if (e.keyCode == 13) {
        this.login();
      }
    },
    resetLogin() {
      this.$refs.loginFormRef.resetFields();
    },
    login() {
      this.$refs["loginFormRef"].validate((valid) => {
        if (valid) {
          this.request.post("/user/login", this.loginForm).then((res) => {
            if (res.code == "200") {
              setRoutes();
              if (res.data.role === "ROLE_STUDENT") {
                this.$router.push("/front");
                localStorage.setItem("user", JSON.stringify(res.data));
                localStorage.setItem("menus", JSON.stringify(res.data.menus));
              } else {
                this.$router.push("/");
                this.$message.success("登录成功");
                // 存储用户信息到浏览器
                localStorage.setItem("user", JSON.stringify(res.data));
                localStorage.setItem("menus", JSON.stringify(res.data.menus));
                // 动态设置当前用户的路由
              }
            } else {
              this.$message.error(res.msg);
            }
          });
        } else {
          return false;
        }
      });
    },
  },
  destroyed() {
    window.removeEventListener("keydown", this.keyDown, false);
  },
};
</script>

<style scoped>
.login_container {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #fc466b, #3f5efb);
}

.login_box {
  width: 400px;
  height: 250px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.good {
  margin-top: 50px;
  /* margin: 40px auto; */
  text-align: center;
}

.avatar_box {
  height: 100px;
  border: 1px solid #eee;
  border-radius: 50%;
  width: 100px;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #ffffff;
}

.avatar_box img {
  width: 100%;
  border-radius: 50%;
  background-color: #eee;
  height: 100%;
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.btns {
  display: flex;
  /* 左对齐 */
  /* justify-content: flex-end; */
}
</style>