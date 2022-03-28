<template>
  <div id="SensorsMonitor">
    <Sensor id="Sensor1" :ID="Sensor1" :NoiceValue=76 :PMValue=123 :Temperature=27 :Fire='Safe' :Charge  = "15"></Sensor>
    <Sensor id="Sensor2" :ID="Sensor2" :NoiceValue=82 :PMValue=23 :Temperature=27 :Fire='Safe' :Charge  = 78></Sensor>
    <Sensor id="Sensor3" :ID="'Sensor3'" :NoiceValue=56 :PMValue=45 :Temperature=27 :Fire='Safe' :Charge  = 93></Sensor>
    <Sensor id="Sensor4" :ID="'Sensor4'" :NoiceValue=49 :PMValue=54 :Temperature=27 :Fire='Safe' :Charge  = 72></Sensor>
    <Sensor id="Sensor5" :ID="'Sensor5'" :NoiceValue=110 :PMValue=33 :Temperature=27 :Fire='Safe' :Charge  = 78></Sensor>
    <Sensor id="Sensor6" :ID="'Sensor6'" :NoiceValue=245 :PMValue=23 :Temperature=27 :Fire='Fire' :Charge  = 92></Sensor>
    <Sensor id="Sensor7" :ID="'Sensor7'" :NoiceValue=23 :PMValue=9 :Temperature=27 :Fire='Safe' :Charge  = 67></Sensor>
    <img src="../assets/img/map.png" alt="" style="height:100%; width:100%">
  </div>
</template>

<script>
import request from "../utils/request";
import Battery from "./Battery";
import Sensor from "./Sensor";

export default {
  created() {
    this.timer = setInterval(()=>{
      this.updateSensors();
    },1000*5)
  },
  name: "SensorMonitor",
  components:{
    Sensor
  },
  data() {
    return{
      temperature:1,
      currentPage:1,
      pageSize:1,
      Sensor1: "Sensor1",
      Sensor1Status: "Connected",
      Sensor1NoiceValue:0,
      Sensor1PM25Value:0,
      Sensor1ChargeValue:0,
      Sensor2: "Sensor2",
      Sensor2Status: "Connected",
      Sensor2NoiceValue:0,
      Sensor2PM25Value:0,
      Sensor2ChargeValue:0,
    }
  },
  methods:{
    load(){
      request.get("/api/ESP32", {
        params: {
          pageNum: 1,
          pageSize: 100,
          search: ""
        }
      }).then(res => {
        console.log("Done",res.data.total)
        console.log("noicevalue",res.data.records[0].noicevalue)
        console.log("chargevalue",res.data.records[0].chargevalue)
        for(var i=0;i<res.data.total;i++)
        {
          console.log("i",i)
          console.log("res",res)
          var sensorName = res.data.records[i].name;
          console.log("name",sensorName)
          switch(sensorName){
            case "Sensor1":
              this.Sensor1NoiceValue = res.data.records[0].noicevalue
              this.Sensor1PM25Value = res.data.records[0].pm25value
              this.Sensor1ChargeValue = res.data.records[0].chargevalue
              break
            case "Sensor2":
              this.Sensor2NoiceValue = res.data.records[1].noicevalue
              this.Sensor2PM25Value = res.data.records[1].pm25value
              this.Sensor2ChargeValue = res.data.records[1].chargevalue
              break;
            default:
              break;
          }
        }


      })

    },
    updateSensors(){
      // request.get(./)
      this.load();
    }
  }
}
</script>

<style scoped>
#Sensor1{
  position:absolute;
  top:-2%;
  left:4%;
}

#Sensor2{
  position:absolute;
  top:2%;
  left:45%;
}
#Sensor3{
  position:absolute;
  top:44%;
  left:3%;
}
#Sensor4{
  position:absolute;
  top:41%;
  left:25%;
}

#Sensor5{
  position:absolute;
  top:55%;
  left:45%;
}

#Sensor6{
  position:absolute;
  top:2%;
  left:78%;
}

#Sensor7{
  position:absolute;
  top:60%;
  left:82%;
}

#SensorsMonitor{
  position:absolute;
  top:0;
  height:100%;
  width:100%;
}

</style>