import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '@/views/Home.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import BusinessList from '@/views/BusinessList.vue'
import BusinessInfo from '@/views/BusinessInfo.vue'

const routes = [
  //路由对象,规则
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  
  {
    path: '/register',
    name: 'register',
    component: Register
  },

  {
    path: '/businesslist',
    name: 'businesslist',
    component: BusinessList
  },

  {
    path: '/businessInfo',
    name: 'businessInfo',
    component: BusinessInfo
  }
]

//创建路由对象
const router = createRouter({
  history: createWebHashHistory(),
  //加载规则
  routes//==routes:routes
})


//导出路由
export default router