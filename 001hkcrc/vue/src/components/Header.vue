<!--suppress ALL -->
<template>
  <div style = "height:100%;  border-bottom: 1px solid red; display:flex">
    <div style="height:100%; width:40%">
      <img src="../assets/img/xhj1.png" alt="" style="height:100%; width:50%">
    </div>

    <div id="clock" style="width:30%; margin-top:3%; color:#8d91ff">
      <p style="font-weight: 700">(15/12)Site: {{MIXItemsValue}}</p>
    </div>

    <div id="clock" style="width:30%; margin-top:3%; color:#8d91ff">{{ date }}</div>

    <div style="flex:1"></div>

    <div style="width: 10%; margin-top:2%">
      <el-dropdown>
      <span class="el-dropdown-link">用户<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item @click = "$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Header',
  data () {
    return {
      //date: new Date()
      date: String(new Date()),
      MIXItemsValue: ''
    }
  },
  mounted(){
    this.timer = setInterval(()=>{
      let weeks = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
      let months = ['Jan.', 'Feb.', 'Mar.', 'Apr.', 'May', 'Jun.', 'Jul.', 'Aug.', 'Sept.', 'Oct.', 'Nov.', 'Dec.']

      var time=new Date();
      let yy = String(time.getFullYear());
      let m = String(time.getMonth());
      let mm = months[m];
      let dd = String(time.getDate()<10? '0'+time.getDate():time.getDate());
      let hou = String(time.getHours()<10? '0'+time.getHours():time.getHours());
      let min = String(time.getMinutes()<10? '0'+time.getMinutes():time.getMinutes());
      let sec = String(time.getSeconds()<10? '0'+time.getSeconds():time.getSeconds());

      var wk = time.getDay();
      let dayth = String(weeks[wk]);
      this.date = dayth+' '+mm+' '+dd+' '+ yy +','+ hou +':'+min+':'+sec;

      try {
        this.MIXItemsValue = sessionStorage.getItem('MIXItemsValue')
        let ss = this.MIXItemsValue.substr(1, this.MIXItemsValue.length - 2)
        this.MIXItemsValue = ss
        //console.log(this.MIXItemsValue)
      }catch{}
    },1000)
  },

  beforeDestroy() {
    if(this.timer){
      clearInterval(this.timer);
    }
  }
}

</script>

<style scoped>
  .el-dropdown-link {
    cursor: pointer;
    color: #409eff;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
</style>