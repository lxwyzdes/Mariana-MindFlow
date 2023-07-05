import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/gloable.css'
import request from '../src/utils/request'
import store from './store'


Vue.use(ElementUI,{size:"mini"});
Vue.prototype.request = request;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
