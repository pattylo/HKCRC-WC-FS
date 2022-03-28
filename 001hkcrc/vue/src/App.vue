<template>
  <div id="nav">
      <router-view />
  </div>

</template>

<style>

</style>

<script>
  import Header from './components/Header'
  //import Aside2 from "./components/Aside2";
  import BuildTable from "./components/BuildTable";
  import Footer from "./components/Footer";
  import Aside from "./components/Aside";

  // Aside.IsGroundTable = 2
  export default {
    name: 'App'

  }
</script>
