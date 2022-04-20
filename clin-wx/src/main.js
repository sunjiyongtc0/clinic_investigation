import Vue from 'vue'
import App from './App'
import  Utils from  './utils/index'

Vue.config.productionTip = false
App.mpType = 'app'

const app = new Vue(App)
app.$mount()

Vue.prototype.$publicUrl = Utils.publicUrlApi+'wx/';
Vue.prototype.utils = Utils;
