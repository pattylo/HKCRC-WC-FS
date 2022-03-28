<template>
  <div style="width:138%">
    <el-col :span="12" id="aside" style="margin-right:5px; width:100%">
      <el-menu style="width: 151%; border-box:0 0 0 0" default-active="2" :default-openeds="[1]"
               class="el-menu-vertical-demo"
               router
               @open="handleOpen"
               @close="handleClose">
        <el-sub-menu index="1" style="width: 100%" class="asideSlot">
          <template #title>
            <i class="el-icon-location"></i>
            <span>选项</span>
          </template>
            <el-menu-item index="buildTable">车辆监测</el-menu-item>
            <el-menu-item index="book">安全帽</el-menu-item>
            <el-menu-item index="CellMonitor">井盖监测</el-menu-item>
            <el-menu-item index="SensorMonitor">环境监测</el-menu-item>
        </el-sub-menu>


        <el-sub-menu index="2" style="width: 100%" class="asideSlot">
          <template #title>
            <i class="el-icon-menu"></i>
            <span>MIX</span>
          </template>
          <el-menu-item v-for="(item, i) in MIXItems" @click="handleMIXItemsClick(i)">{{this.MIXItems[i]}}</el-menu-item>
        </el-sub-menu>


        <el-sub-menu index="3" style="width: 100%" class="asideSlot">
          <template #title>
            <i class="el-icon-document"></i>
            <span>语言</span>
          </template>
          <el-menu-item index="buildTable" disabled>繁体字</el-menu-item>
          <el-menu-item index="book" disabled>中文</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="4" style="width: 100%" class="asideSlot">
          <template #title>
            <i class="el-icon-setting"></i>
            <span>设置</span>
          </template>
          <el-menu-item index="ShowItems">显示设置</el-menu-item>
<!--          <el-menu-item index="book">**</el-menu-item>-->
        </el-sub-menu>
      </el-menu>
    </el-col>
  </div>
</template>

<script>
export default {
  data(){
    return {
      MIXItems:['All'],
      MIXItemsString: "",
    }
  },
  mounted() {
    this.timer = setInterval(()=>{

      try {
        this.MIXItemsString = sessionStorage.getItem('allSiteListStringFormat')
        //let ss = this.MIXItemsValue.substr(1, this.MIXItemsValue.length - 2)
        //this.MIXItemsValue = ss
      }catch{}

      this.MIXItems = this.MIXItemsString.substr(1,this.MIXItemsString.length-2).split(",")
      console.log(this.MIXItems)

    },1000)
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleMIXItemsClick(i){
      console.log(this.MIXItems[i])
      sessionStorage.setItem("MIXItemsValue",JSON.stringify(this.MIXItems[i]))
    }
  }
}
</script>

<style scoped>
.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.asideSlot{
}
</style>
