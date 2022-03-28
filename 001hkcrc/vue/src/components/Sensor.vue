<template>
  <div id="sensorTab" style="width:150px;height:135px; background-color: red">
    <Battery :proQuantity = "this.Charge"></Battery>
    <h5 id="ID" style="color: blueviolet">ID:   {{ID}}</h5>
    <h5 id="NoiceSensor">Noice Value:  {{NoiceValue}} DB</h5>
    <h5 id="PMSensor">PM2.5 Value: {{PMValue}} um</h5>
    <h5 id="Temperature">Temperature: {{Temperature}} °C</h5>
    <h5 id="Fire">Fire: {{Fire}}</h5>
    <h5 id="Charge">Charge: {{Charge}} %</h5>
  </div>
</template>

<script>
import Battery from "./Battery";

export default {
  name: "Sensor",
  components:{
    Battery
  },
  props:{
    ID:{
      type:String,
      default:"Sensor x"
    },
    NoiceValue:{
      type:Number,
      default:0
    },
    PMValue:{
      type:Number,
      default:0
    },
    Temperature:{
      type:Number,
      default:0
    },
    Fire:{
      type:String,
      default:"Safe"
    },
    Charge:{
      type:Number,
      default:0
    }
  },

  data(){
    return{
      //Charge1:this.props.Charge
    }
  }
}


</script>

<style scoped>
#sensorTab{
  background-color:  beige;
}
#ID{
  position: absolute;
  top: 20px;
  left: 32px;
  overflow: hidden;
}
#NoiceSensor{
  position: absolute;
  top: 40px;
  left: 32px;
  overflow: hidden;
}

#PMSensor{
  position: absolute;
  top: 55px;
  left: 32px;
  overflow: hidden;
}

#Temperature{
  position: absolute;
  top: 70px;
  left: 32px;
  overflow: hidden;
}
#Fire{
  position: absolute;
  top: 85px;
  left: 32px;
  overflow: hidden;
}

#Charge{
  position: absolute;
  top: 100px;
  left: 32px;
  overflow: hidden;
}

</style>