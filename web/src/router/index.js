import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import AdditionalInfo from '../views/AdditionalInfo.vue'
import Result from '../views/Result.vue'
import Blank from '../views/Blank.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/additionalInfo',
    name: 'AdditionalInfo',
    component: AdditionalInfo
  },
  {
    path: '/result',
    name: 'Result',
    component: Result,
    props: true
  },
  {
    path: '/blank',
    name: 'Blank',
    component: Blank
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
