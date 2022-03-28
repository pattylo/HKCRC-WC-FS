<template>
  <div id="nav">
    <div id = "Header">
      <Header />
    </div>
    <div id = "Aside">
      <Aside />
    </div>
    <div id = "Table">
      <router-view style = "flex: 1"/>
    </div>
<!--    <div id="Footer">-->
<!--      <Footer />-->
<!--    </div>-->

  </div>
</template>

<style>
*{
  overflow: hidden;
}
#Header{
  position: absolute;
  height: 10%;
  top: 0;
  left: 0;
  right: 0;
  overflow: hidden;
  /*background-color: #fff9ea;*/
}
#Table {
  position: absolute;
  top: 10%;  /* 距离上面50像素 */
  left: 15%;
  height: 89.4%;
  width: 85%;
  margin-left:0.2%;
  margin-top: 0.2%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
  /*background-color:red;*/
  background-color: #fff9ea;
}
#Aside {
  position: absolute;
  top: 10%;  /* 距离上面50像素 */
  left: 0;
  height: 89.5%;
  width: 15%;
  margin-top: 0.2%;
  overflow: hidden; /* 当内容过多时y轴出现滚动条 */
  /*background-color: #fff9ea;*/
}
#Footer {
  position: absolute;
  top: 75%;
  bottom: 0px;
  left: 0px;  /* 距离右边0像素 */
  margin-top: 0.2%;
  width: 100%;
  overflow-y: hidden; /* 当内容过多时y轴出现滚动条 */
  background-color: #b1deff;
}

</style>

<script>


// Aside.IsGroundTable = 2
import Header from "../components/Header";
//import Aside2 from "../components/Aside2";
import BuildTable from "../components/BuildTable";
import Footer from "../components/Footer";
import Aside from "../components/Aside";

export default {
  name: 'Layout',
  components: {
    Header,
    //Aside2,
    BuildTable,
    Footer,
    Aside
    // GroundTable,
    // BuildTable
  }

}
</script>