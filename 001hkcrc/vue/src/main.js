import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//import VueSocketIO from 'vue-socket.io'
// import ElementUI from 'element-ui';
// import 'element-ui/lib/theme-chalk/index.css';


import '@/assets/css/global.css'
//import Vue from "element-plus/es/locale";


createApp(App).use(store).use(router).use(ElementPlus).mount('#app')

// Vue.use(new VueSocketIO({
//     debug: true,
//     connection: 'http://localhost:3000',
//     vuex:{
//         store,
//         mutationsPrefix:"SOCKET_",
//         actionPrefix: "SOCKET_"
//     }
// }))
