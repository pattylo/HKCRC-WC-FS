<template>
  <div>
    <el-checkbox class="box1" v-model="ShowID" label="ID" border @change="handleIDStatus"></el-checkbox>
    <el-checkbox class="box1" v-model="ShowDocketNO" label="Docket Number" border @change="handleDocketNOStatus"></el-checkbox>
    <el-checkbox class="box1" v-model="ShowSiteName" label="Site Name" border @change="handleSiteNameStatus"></el-checkbox>
    <el-checkbox class="box1" v-model="ShowLocation" label="Location" border @change="handleLocationStatus"></el-checkbox>
    <el-checkbox class="box1" v-model="ShowTruckNO" label="Truck Number" border @change="handleTruckNOStatus"></el-checkbox>
    <el-checkbox class="box1" v-model="ShowDespatchT" label="Despatch Time" border @change="handleDespatchTStatus"></el-checkbox>

    <el-checkbox class="box1" v-model="ShowArrivalT" label="Arrival Time" border @change="handleArrivalTStatus"></el-checkbox>
    <el-checkbox class="box1" v-model="ShowBatchName" label="Batch Name" border @change="handleBatchNameStatus"></el-checkbox>
    <el-checkbox class="box1" v-model="ShowThisLoad" label="This Load" border @change="handleThisLoadStatus"></el-checkbox>
    <el-checkbox class="box1" v-model="ShowCummulatedqty" label="Sum Load" border @change="handlecummulatedqtyStatus"></el-checkbox>
  </div>
</template>

<script>
export default {
  data () {
    return {
      ShowID: true,
      ShowDocketNO: false,
      ShowSiteName: false,
      ShowLocation: true,
      ShowTruckNO: true,
      ShowDespatchT: true,
      ShowArrivalT: true,
      ShowBatchName:false,
      ShowThisLoad:true,
      ShowCummulatedqty:false
    };
  },
  created(){
    console.log("created function")
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
  load(){
    console.log("created load")
  },
  methods: {
    handleIDStatus(){
      sessionStorage.setItem("ShowID",JSON.stringify(this.ShowID))
    },
    handleDocketNOStatus(){
      sessionStorage.setItem("ShowDocketNO",JSON.stringify(this.ShowDocketNO))
    },
    handleSiteNameStatus(){
      sessionStorage.setItem("ShowSiteName",JSON.stringify(this.ShowSiteName))
    },
    handleLocationStatus(){
      sessionStorage.setItem("ShowLocation",JSON.stringify(this.ShowLocation))
    },
    handleTruckNOStatus(){
      sessionStorage.setItem("ShowTruckNO",JSON.stringify(this.ShowTruckNO))
    },
    handleDespatchTStatus(){
      sessionStorage.setItem("ShowDespatchT",JSON.stringify(this.ShowDespatchT))
    },
    handleArrivalTStatus(){
      sessionStorage.setItem("ShowArrivalT",JSON.stringify(this.ShowArrivalT))
    },
    handleBatchNameStatus(){
      sessionStorage.setItem("ShowBatchName",JSON.stringify(this.ShowBatchName))
    },
    handleThisLoadStatus(){
      sessionStorage.setItem("ShowThisLoad",JSON.stringify(this.ShowThisLoad))
    },
    handlecummulatedqtyStatus(){
      sessionStorage.setItem("ShowCummulatedqty",JSON.stringify(this.ShowCummulatedqty))
    },
  }
}
</script>

<style scoped>

.box1{
  width:150px;
  background-color: azure;
  margin-left:10px;
  margin-top:10px

}
</style>