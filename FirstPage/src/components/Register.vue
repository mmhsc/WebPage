<template>
  <body id="registerPage">
    <el-form class="register-container" lablel-position="left"
    label-width="0px">
      <h3 class="title">用户注册</h3>
      <el-form-item>
        <el-input type="text" v-model="loginForm.username"
        auto-complete="off" placeholder="帐号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="loginForm.password"
        auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button type="primary" style="width: 40%;background: #505458;border: none" v-on:click="register">注册</el-button>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  name: 'Register',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    register () {
      this.$axios
        .post('/register', {
          username: this.loginForm.username,
          password: this.loginForm.password
        })
        .then(successResponse => {
          if (successResponse.data.code === 200) {
            this.$alert('注册成功', '提示', {confirmButtonText: '确定'})
            this.$router.replace('/login')
          } else {
            this.$alert(successResponse.data.message, '提示', {confirmButtonText: '确定'})
          }
        })
        .catch(failResponse => {
          this.$alert('注册失败', '提示', {confirmButtonText: '确定'})
        })
    }
  }
}
</script>

<style>
  #registerPage {
    background:url("../assets/img/bg/loginBG.jpg") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
  body{
    margin: -5px 0px;
  }
  .register-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
