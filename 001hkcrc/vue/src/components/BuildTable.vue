<template>
  <div style="padding: 0px">
    <div id="buttons" >

      <el-button size="mini" type="primary" @click="add" style="width: 18%">新增</el-button>

      <el-button size="mini" type="primary" @click="loadList" style="width: 25%">重新导入</el-button>
      <el-button size="mini" type="primary" @click="deleteList" style="width: 25%">清除列表</el-button>

<!--      <el-button @click = "updateCurrentTruck">test</el-button>-->
    </div>

    <div id="uploadfile">
      <el-upload action="http://localhost:9090/files/upload" :on-sucess="filesUploadSucces">
        <el-button type="primary">文件上传</el-button>
      </el-upload>
    </div>

    <div id="search" >
      <el-input  size="mini" v-model="search" placeholder = "请输入关键字" style="width:60%" clearable></el-input>
      <el-button id="searchButton" size="mini" type="primary" style="margin-left: 2px" @click="load">查询</el-button>
    </div>

    <div id="table">
      <el-table
    :data="tableData"
    style="width: 100%"
    stripe
    :row-style="tableRowClassName">
      <el-table-column
          prop="num"
          label="ID"
          width="180"
          sortable="false"
          v-if =  this.ShowID>
      </el-table-column>
      <el-table-column
        prop="docketno"
        label="序列号"
        width="180"
        sortable="false"
        v-if =  this.ShowDocketNO>
      </el-table-column>
      <el-table-column
          prop="sitename"
          label="地点"
          width="180"
          sortable="true"
          v-if =  this.ShowSiteName>
      </el-table-column>
      <el-table-column
          prop="location"
          label="地点2"
          width="180"
          sortable="true"
          v-if =  this.ShowLocation>
      </el-table-column>
      <el-table-column
        prop="trucknumber"
        label="车牌号码"
        width="180"
        v-if =  this.ShowTruckNO>
      </el-table-column>
      <el-table-column
          prop="batchname"
          label="槽口"
          v-if =  this.ShowBatchName>
      </el-table-column>
      <el-table-column
          prop="despatchtime"
          label="离槽时间"
          v-if =  this.ShowDespatchT>
      </el-table-column>
      <el-table-column
        prop="arrivaltime"
        label="到达时间"
        v-if =  this.ShowArrivalT>
      </el-table-column>
      <el-table-column
        prop="thisload"
        label="载重"
        v-if =  this.ShowThisLoad>
      </el-table-column>
      <el-table-column
        prop="cummulatedqty"
        label="总重"
        v-if =  this.ShowCummulatedqty>
      </el-table-column>
      <el-table-column  label="操作" width="120">
      <template #default="scope">
      <el-button size="mini" @click="handleEdit(scope.row)" type="text">编辑</el-button>
      <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
        <template #reference>
          <el-button size="mini" type="danger">删除</el-button>
        </template>
      </el-popconfirm>
    </template>
    </el-table-column>
  </el-table>
    </div>

    <div id="pagination">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[8, 9, 10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    </div>

    <div id="Footer">
      <Footer />
    </div>

    <el-dialog title="新增信息" v-model="dialogVisible" width="50%">
      <el-form :model="form"  label-width="120px">
        <el-form-item label="序列号">
          <el-input v-model="form.docketno" style="width: 60%;"></el-input>
        </el-form-item>
        <el-form-item label="地点">
          <el-input v-model="form.sitename" style="width: 60%;"></el-input>
        </el-form-item>
        <el-form-item label="地点2">
          <el-input v-model="form.location" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="车牌号码">
          <el-input v-model="form.trucknumber" style="width: 60%"></el-input>
        </el-form-item>
<!--        <el-form-item label="离槽时间">-->
<!--          <el-input v-model="form.despatchtime" style="width: 60%"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="到达时间">-->
<!--          <el-input v-model="form.arrivaltime" style="width: 60%"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="槽点2">
          <el-input v-model="form.batchname" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="载重">
          <el-input v-model="form.thisload" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="总重">
          <el-input v-model="form.cummulatedqty" style="width: 60%"></el-input>
        </el-form-item>
        <el-form-item label="封面">
          <el-upload action="http://localhost:9090/files/upload" :on-sucess="filesUploadSucces">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">确定</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<style scoped>
#search{
  position: absolute;
  width:45%;
  height: 30px;
  margin-left:0.5%;
  top: 0;
  left: 0;
  overflow: hidden;
  /*background-color: #fff9ea;*/
}

#buttons {
  position: absolute;
  top: 0;  /* 距离上面50像素 */
  left: 40%;
  height: 8%;
  width: 55%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
  background-color: #fff9ea;
}
#uploadfile{
  position: absolute;
  top: 0;  /* 距离上面50像素 */
  left: 85%;
  height: 4%;
  width: 55%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
  background-color: #fff9ea;
}
#searchButton{
  position: absolute;
  top: 0;  /* 距离上面50像素 */
  left: 60%;
  height: 8%;
  width: 25%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
}
#table {
  position: absolute;
  top: 30px;  /* 距离上面30像素 */
  left: 0;
  margin-left:0.5%;
  height: 65%;/*78%*/
  width: 99.5%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
  background-color: #fff9ea;
}

#pagination {
  position: absolute;
  left: 0;
  top:68%;
  height:8%;
  width: 100%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
}

#Footer {
  position: absolute;
  top: 71.8%;
  height: 28%;

  left: 0px;  /* 距离右边0像素 */
  margin-top: 0.2%;
  width: 100%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
  background-color: #b1deff;
}
/*#table.warning-row{*/
/*  background-color:red;*/
/*}*/

/*#table.sucess-row{*/
/*  background-color:blue;*/
/*}*/


</style>

<script>

import request from "../utils/request";
import Header from "./Header";
//import Aside2 from "./Aside2";
import Footer from "./Footer";
import Aside from "./Aside";
import ShowItems from "../views/ShowItems";

export default {
  name: 'BuildTable',
  created(){
    this.load()

    //this.initWebSocket();
    this.timer = setInterval(() =>{
      this.updateCurrentTruck()
      this.GetCurrentTruck()
    },1000* 5*20)
    this.timer = setInterval(() =>{
      this.loadList()
      this.AutoDeletList()
    },1000* 60*5*2)

    try{
      if (sessionStorage.getItem("ShowID") == String(true)) {
        this.ShowID = true
      }else if (sessionStorage.getItem("ShowID") == String(false)){
        this.ShowID = false
      }
    }catch{
      console.log("failed to get 'ShowID'")
    }

    try{
      if (sessionStorage.getItem("ShowDocketNO") == String(true)) {
        this.ShowDocketNO = true
        console.log("created function  1")
      }else if (sessionStorage.getItem("ShowDocketNO") == String(false)){
        this.ShowDocketNO = false
        console.log("created function  2")
      }
    }catch{
      console.log("failed to get 'ShowDocketNO'")
    }

    try{
      if (sessionStorage.getItem("ShowSiteName") == String(true)) {
        this.ShowSiteName = true
      }else if (sessionStorage.getItem("ShowSiteName") == String(false)){
        this.ShowSiteName = false
      }
    }catch{
      console.log("failed to get 'ShowSiteName'")
    }

    try{
      if (sessionStorage.getItem("ShowLocation") == String(true)) {
        this.ShowLocation = true
      }else if (sessionStorage.getItem("ShowLocation") == String(false)){
        this.ShowLocation = false
      }
    }catch{
      console.log("failed to get 'ShowLocation'")
    }

    try{
      if (sessionStorage.getItem("ShowTruckNO") == String(true)) {
        this.ShowTruckNO = true
      }else if (sessionStorage.getItem("ShowTruckNO") == String(false)){
        this.ShowTruckNO = false
      }
    }catch{
      console.log("failed to get 'ShowTruckNO'")
    }

    try{
      if (sessionStorage.getItem("ShowDespatchT") == String(true)) {
        this.ShowDespatchT = true
      }else if (sessionStorage.getItem("ShowDespatchT") == String(false)){
        this.ShowDespatchT = false
      }
    }catch{
      console.log("failed to get 'ShowDespatchT'")
    }

    try{
      if (sessionStorage.getItem("ShowArrivalT") == String(true)) {
        this.ShowArrivalT = true
      }else if (sessionStorage.getItem("ShowArrivalT") == String(false)){
        this.ShowArrivalT = false
      }
    }catch{
      console.log("failed to get 'ShowArrivalT'")
    }

    try{
      if (sessionStorage.getItem("ShowBatchName") == String(true)) {
        this.ShowBatchName = true
      }else if (sessionStorage.getItem("ShowBatchName") == String(false)){
        this.ShowBatchName = false
      }
    }catch{
      console.log("failed to get 'ShowArrivalT'")
    }

    try{
      if (sessionStorage.getItem("ShowThisLoad") == String(true)) {
        this.ShowThisLoad = true
      }else if (sessionStorage.getItem("ShowThisLoad") == String(false)){
        this.ShowThisLoad = false
      }
    }catch{
      console.log("failed to get 'ShowThisLoad'")
    }

    try{
      if (sessionStorage.getItem("ShowCummulatedqty") == String(true)) {
        this.ShowCummulatedqty = true
      }else if (sessionStorage.getItem("ShowCummulatedqty") == String(false)){
        this.ShowCummulatedqty = false
      }
    }catch{
      console.log("failed to get 'ShowCummulatedqty'")
    }
  },
  destroyed() {
    this.websock.close() //离开路由之后断开websocket连接
  },
  components: {
    Aside,
    Footer,
    ShowItems
  },
  methods: {

    load(){
      console.log("load")
      let aa = sessionStorage.getItem('MIXItemsValue')
      let ss=aa
      //if(aa.indexOf("All")>0||aa.endsWith("All"))aa="";

      if(aa != null && aa.indexOf("All")==-1 && aa!=="All")
      {
        ss = aa.substr(1, aa.length - 2)
        this.SelectedSite = ss
      }
      else{
        this.SelectedSite = ""
      }
      console.log("===================aa=b======================",this.SelectedSite)
      //let ss = aa.substr(1, this.MIXItemsValue.length - 2)
      //this.MIXItemsValue = ss

      request.get("/api/user", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          site:this.SelectedSite
        }
      }).then(res => {
        console.log("-------------------load: ")
        console.log(res.data.records)
        this.tableData = res.data.records
        this.total = res.data.total
      })
      //this.websocketsend("qw");
      console.log("load done")

    },
    GetAllSiteByString(){
      request.get("/api/user", {
        params: {
          pageNum: 1,
          pageSize: 10000,
          search: '',
          site:''
        }
      }).then(res => {
        //console.log("all data: ")
        //console.log(res.data.records)
        let allSiteList = [], num = 0
        for( let i=0;i<res.data.records.length;i++)
        {
          if(allSiteList.length==0)allSiteList.push(res.data.records[i].sitename);
          for(let j=0;j<allSiteList.length;j++)
          {
            //console.log(i,res.data.records[i].sitename,j,allSiteList[i])
            if(res.data.records[i].sitename == allSiteList[j])
            {
              //console.log("already have")
              break;
            }
            if(j==allSiteList.length-1)
            {
              allSiteList.push(res.data.records[i].sitename);
              break;
            }
          }
        }

        let allSiteListStringFormat = "";
        if(allSiteList.length>0) {
          allSiteListStringFormat = String(allSiteList[0])
          for (let i = 1; i < allSiteList.length; i++) {
            allSiteListStringFormat = allSiteListStringFormat + ","+String(allSiteList[i])
          }
        }
        allSiteListStringFormat = allSiteListStringFormat+", All"
        console.log("allSiteListStringFormat===",allSiteListStringFormat)

        sessionStorage.setItem("allSiteListStringFormat",JSON.stringify(allSiteListStringFormat))

      })

    },
    filesUploadSucces(res){
      //console.log("=========================File upload sucess--=================")
      //console.log(res)
      //console.log("--===========================================File upload sucess")
    },
    tableRowClassName ({row, rowIndex}) {
      //console.log(row, rowIndex);
      let styleJoson = {};

      if(row.id === this.currentTrcukID ){
        //console.log("The Current Truck ID is: "+this.currentTrcukID)
        styleJoson.color = 'blue';
        // styleJoson.background = "#66ccff";
        styleJoson.backgroundColor = "##F1D0FB";
        return styleJoson;
      }else{
        styleJoson.color = 'black';
        // styleJoson.background = "#66ccff";
        styleJoson.backgroundColor = "#ECFEFD";
        return styleJoson;
      }
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id){
      request.delete("/api/user/"+id).then(res => { //es6语法
        console.log(res)
        if(res.code ==='0' )
        {
          this.$message({
            type: "success",
            message: "更新成功！"
          })
        }else{
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load();
      })
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      //this.heightNum = 2;
      this.load()
    },
    handleCurrentChange(pageNum){
      this.currentPage = pageNum
      this.load()
    },

    add(){
      this.dialogVisible = true
      this.form = {}
    },
    updateCurrentTruck(){
      console.log("update truck")
      if(this.currentTrcukID==null)this.currentTrcukID=0;
      request.get("/api/user/updateCurrentTruck/"+this.total+"/"+this.currentTrcukID).then(res => { //es6语法
        console.log(res)
        if(res.code ==='0' && res.data.id != null && res.data.id != 0)
        {
          // this.$message({
          //   type: "success",
          //   message: "更新成功！"
          // })
          this.currentPage = Math.ceil(res.data.num/this.pageSize);
          this.currentTrcukID = res.data.num;
        }else if(res.code ==='0') {
          this.currentTrcukID = 0,
          this.$message({
            type: "success",
            message: "第一辆车还未到达！" +this.currentTrcukID
          })
        } else{
          this.$message({
            type: "error",
            message: res.msg +this.currentTrcukID
          })
        }

        this.load();
        //this.dialogVisible = false
      })
      console.log("update truck done")
    },
    GetCurrentTruck(){
      console.log("update truck  333")
      if(this.currentTrcukID==null)this.currentTrcukID=0;
      request.get("/api/user/GetCurrentTruckNO/"+this.total+"/"+this.currentTrcukID).then(res => { //es6语法
        console.log("res:")
        console.log(res.data.docketno)
        console.log("---res:")


      })
      console.log("update truck done")
    },
    loadList(){
      console.log("start load list")
      request.post("/api/user/loadlist/"+this.total).then(res => { //es6语法
        //console.log("loadList: "+res.data)
        if(res.code ==='0' )
        {
          //console.log("load list res----------------------:")
          //console.log(res)
          this.$message({
            type: "success",
            message: "更新成功！"
          })
        }else{
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.GetAllSiteByString();
        this.load();
        this.dialogVisible = false
      })
    },

    deleteList(){
      request.delete("/api/user/deletelist/"+this.total).then(res => { //es6语法
        console.log(res)
        if(res.code ==='0' )
        {
          this.$message({
            type: "success",
            message: "更新成功！"
          })
        }else{
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.total = 0;
        this.load();
        this.dialogVisible = false
      })
    },
    AutoDeletList(){
      var time=new Date();
      let hou = time.getHours();
      let min = time.getMinutes();
      if(hou<1 && min<20)
      {
        this.deleteList();
      }
    },
    save(){
      if(this.form.id)
      {
        request.put("/api/user",this.form).then(res => { //es6语法
          console.log(res)
          if(res.code ==='0' )
          {
            this.$message({
              type: "success",
              message: "更新成功！"
            })
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load();
          this.dialogVisible = false
        })
      } else {
        request.post("/api/user",this.form).then(res => { //es6语法
          console.log(res)
          if(res.code ==='0' )
          {
            this.$message({
              type: "success",
              message: "添加成功！"
            })
          }else{
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load();
          this.dialogVisible = false
        })
      }
      // 数据交互的API， axios, fetch
    },

    initWebSocket(){ //初始化weosocket
      // //const wsuri = "ws://192.168.10.24:10000";
      // console.log("--------------initWebSocket---------")
      // //this.websock = new WebSocket('ws://192.168.10.13:9876');
      // this.websock = new WebSocket('ws://192.168.10.13:9876');
      // this.websock.onmessage = this.websocketonmessage;
      // this.websock.onopen = this.websocketonopen;
      // this.websock.onerror = this.websocketonerror;
      //this.websock.onclose = this.websocketclose;
    },
    websocketonopen(){ //连接建立之后执行send方法发送数据
      let actions = {"test":"12345"};
      this.websocketsend(JSON.stringify(actions));
      console.log("Open sucess")
    },
    websocketonerror(){//连接建立失败重连
      //this.initWebSocket();
    },
    websocketonmessage(e){ //数据接收
      const redata = JSON.parse(e.data);
      console.log("=================---------redata-----------==============");
      console.log("=================---------redata-----------==============");
      console.log("=================---------redata-----------==============");
      console.log("=================---------redata-----------==============");
      console.log(redata)
    },
    websocketsend(Data){//数据发送
      this.websock.send(Data);
    },
    websocketclose(e){  //关闭
      console.log('断开连接',e);
    },
  },
  data () {
    return {
      ShowID:true,
      ShowDocketNO: false,
      ShowSiteName: false,
      ShowLocation: true,
      ShowTruckNO: true,
      ShowDespatchT: true,
      ShowArrivalT: true,
      ShowBatchName:false,
      ShowThisLoad:true,
      ShowCummulatedqty:false,
      SelectedSite:"",

      websock: null,

      form: {},
      filename:'',
      dialogVisible: false,
      currentTrcukID: 0,
      search: '',
      total: 1,
      currentPage: 1,
      pageSize: 8,
      tableData: []
    }
  }
}



</script>
