<template>
    <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../../assets/logo.png" alt="">
      </div>
      <div class="good">
        <h1>二维码登录</h1>
      </div>
      
      <!-- <el-form class="login_form" :model="loginForm" :rules="loginFormRules" ref="loginFormRef">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="loginForm.username" name="username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-s-grid" v-model="loginForm.password" type="password" name="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" prefix-icon="el-icon-s-grid" v-model="loginForm.confirmPassword" type="password" name="password"></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" icon="el-icon-search" size="small" @click="login('loginFormRef')">注册</el-button>
          <el-button icon="el-icon-eleme" type="info" size="small" @click="resetLogin('loginFormRef')">重置</el-button>
          <el-button type="warning" size="small" icon="el-icon-setting" @click="$router.push('/login')">返回登录</el-button>
        </el-form-item>
      </el-form> -->

      <div id="qrCode"></div>
      <!-- <el-button type="warning" size="small" icon="el-icon-setting" @click="$router.push('/login')">返回登录</el-button> -->
    </div>
  </div>
</template>

<script>
export default {
    data(){
        return{

        }
    },
}
</script>

<style scoped>
.login_container{
  height: 100vh;
  background-image: linear-gradient(to bottom right,#fc466b,#3f5efb);
}
.login_box{
width: 400px;
height: 300px;
background-color: #fff;
border-radius: 3px;
position: absolute;
left: 50%;
top: 50%;
transform: translate(-50%,-50%);
}
.good{
  margin-top: 55px;
  /* margin: 40px auto; */
  text-align: center;
}
.avatar_box{
height: 100px;
border: 1px solid #eee;
border-radius: 50%;
width: 100px;
padding: 10px;
box-shadow: 0 0 10px #ddd;
position: absolute;
left: 50%;
transform: translate(-50%,-50%);
background-color: #ffffff;
}
.avatar_box img{
  width: 100%;
  border-radius: 50%;
  background-color: #eee;
  height: 100%;
}
.login_form{
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
.btns{
  display: flex;
  /* 左对齐 */
  /* justify-content: flex-end; */
}
</style>