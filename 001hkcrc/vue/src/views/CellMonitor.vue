<template>
  <div style="width: 100%; height: 100%">

    <div id="planeFig">
      <div id="cell00">
        <img v-if="this.cell00==0" :src= "require('../assets/img/open.png')" alt="picture"/>
        <img v-else-if="this.cell00==1" :src= "require('../assets/img/closed.png')" alt="picture"/>
        <img v-else :src= "require('../assets/img/lost.png')" alt="picture"/>
        <div >
          <p1 v-if="this.cell00==0" class="userinfo">{{this.cell00Operator}}</p1>
          <p1 v-else class="userinfo"></p1>
        </div>

      </div>


    </div>
  </div>
</template>

<script>

import request from "../utils/request";

export default {
  name: "CellMonitor",
  created() {
    this.timer = setInterval(() =>{
      this.updateCellInfo()
    },1000* 1)
    this.timer = setInterval(() =>{
      this.checkCellStatus()
    },1000* 60*3)

  },
  methods:{
    updateCellInfo(){
      console.log("Start update cell info function.*******")

      request.get("/api/cell", {
        params: {
          pageNum: 1,
          pageSize: 10,
          search: "",
          site:""
        }
      }).then(res => {
        console.log("-------------------cell information status: ")
        console.log(res.data.records[0].status)
        this.CellData = res.data.records
        this.cell00 = res.data.records[0].status;
        this.cell00Operator = res.data.records[0].operator;
        console.log("this.cell00Operator==="+this.cell00Operator)
      })
      //this.websocketsend("qw");
      console.log("load done")

    },

    checkCellStatus(){
      console.log("Start checkCellStatus info function.  333")

      request.get("/api/cell/checkCellStatus").then(res => { //es6语法
        console.log("res:")
        console.log(res.data.docketno)
        console.log("---res:")


      })
      console.log("********************")
    },
  },
  data(){
    return{
      cell00:0,
      cell00Operator:"operator A",
      CellData: []
    }
  }
}
</script>

<style scoped >
#planeFig {
  position: absolute;
  top: 0;  /* 距离上面50像素 */
  left: 0%;
  height: 100%;
  width: 100%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
  background-color: #fff9ea;
  background-image:url("../assets/img/planet.jpg");
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
#cell00{
  position: absolute;
  top: 14%;  /* 距离上面50像素 */
  left: 20%;
  width: 200px;
  height: 100px;
}
.userinfo{
  position: absolute;
  top: 0%;
  left: 35%;
  font-size:30px;
  height: 70px;
  width: 100px;
  background-color: antiquewhite;
}
</style>