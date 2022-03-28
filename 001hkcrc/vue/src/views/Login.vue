<template>
  <div style="width: 100%; height:100vh; background-color: darkslateblue; overflow: hidden">
    <div style="width:400px; margin:100px auto">
      <div style="color:#cccccc; font-size: 30px; text-align: center; padding: 30px">欢迎登陆</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password></el-input>
        </el-form-item>
        <el-form-item >
          <el-button style="width: 100%" @click="login">登陆</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Login",
  data(){
    return{
      form:{},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }
    }

  },
  methods:{
    login(){
      // this.$refs[this.form].validate((valid) => {
      //   if (valid) {
      //     alert('submit!')
      //   }
      //   else
      //   {
      //     console.log("not valid")
      //   }
      // })
      request.post("/api/cell/login/"+"aa", this.form.username).then(res =>{
        if(res.code ===0){
          this.$message({
            type: "error",
            message: "登陆成功."
          })
          this.$router.push({name:'CellMonitor'})
        }else{
          this.$message({
            type: "error",
            message: res.msg
          })
          this.$router.push({name:'CellMonitor'})
        }
      })
    }
  }
}
</script>

<style scoped>

</style>