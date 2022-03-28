<template>
  <div style = "height:100%; border-bottom: 1px solid red; display:flex">
    <div id = "camera">
      <img src="..\assets\img\camera.jpg" alt="C:\Software\IDEA_Projects\GIT\001hkcrc\vue\src\assets\img\camera.jpg" />
    </div>

    <div id = "zoonin">
      <img src="..\assets\img\zoonin.jpg" alt="zoonin1.jpg" style="height:100%; width:100%">
    </div>

  </div>
</template>

<script>

export default {
  name: 'Footer',
  data(){
    return{
      image1:"../webfiles/camera.jpg",
      numTest:1
    }
  },
  computed:{
    // getNavImgUrl:function(){
    //   return 'camera.jpg'
    // }
    //this.image1 = ""
  },

  created(){
    // this.timer = setInterval(() =>{
    //   //this.updateEx()
    // },1000* 1)
  },
  methods:{
    updateEx(){
      if(this.numTest==1)
      {
        this.image1 = "../webfiles/camera.jpg"
        this.numTest=2
      }else {
        this.image1 = "../webfiles/camera1.jpg"
        this.numTest=1
      }

      console.log("====Update camera====",this.image1, this.numTest)
    },

  }
}
</script>

<style scoped>
  #camera{
    margin-left: 12%;
    margin-top: 0.15%;
    margin-bottom: 0.15%;
    float: left;
    height: 99.8%;
    width:30%
  }
  #zoonin{
    margin-left: 13%;
    margin-top: 0.15%;
    margin-bottom: 0.15%;
    float: right;
    height: 99.6%;
    width:30%
  }
</style>

