<template>
  <div style="width:22px; height:120px">
    <canvas ref="mycanvas" :width="cWidth" :height="cHeight"></canvas>
  </div>
</template>

<script>
export default {
  name: "Battery",
  mycanvas:null,
  ctx:null,
  myInterval:null,
  props:{
    proQuantity:{
      type:Number,
      default:15
    },
    proIsCharge:{
      type:Boolean,
      default:false
    }
  },
  data(){
    return{
      cWidth:22,
      cHeight:120,
      jd:0
    }
  },
  created() {
  },
  watch:{
    //监听是否有电量变化，如果是，做一个充电动画
    proIsCharge(newVal, oldal){
      console.log('====proIsCharge===',newVal)
      let _this = this
      clearInterval(_this.myInterval)
      this.jd = 0
      if(_this.proIsCharge){
        _this.myInterval = setInterval(function(){
          _this.drawCharge()
        },300)
      }
    }
  },
  mounted() {
    this.mycanvas = this.$refs.mycanvas
    this.ctx = this.mycanvas.getContext('2d')
    this.drawBg()
    this.drawPath(this.proQuantity)
  },
  methods:{
    drawBg(){
      this.ctx.strokeStyle = 'rgb(255,255,255,1)'
      this.ctx.lineWidth = 3
      this.ctx.strokeRect(1,120,20,-100)
      this.ctx.fillStyle = 'rgba(79,94,227,1)'
      this.ctx.fillRect(1,120,20,-100)
      this.ctx.fillStyle = 'rgba(79,94,227,1)'
      this.ctx.fillRect(6,19,10,-5)
    },
    drawPath(quantity){
      if(this.proIsCharge){
        this.ctx.fillStyle = 'green'
      }else if(quantity<20){
        this.ctx.fillStyle = 'red'
      }else{
        this.ctx.fillStyle = 'green'
      }
      this.ctx.fillRect(0.9,120,20.2,-(100-2)*quantity/100)
    },
    drawCharge(){
      this.ctx.clearRect(0,0,this.cWidth, this.cHeight)
      this.drawBg()
      this.jd = this.jd+10
      if(this.jd>100){
        this.jd=0
      }
      this.drawPath(this.jd)
    }
  }
}
</script>

<style scoped>

</style>