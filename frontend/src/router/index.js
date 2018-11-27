import Vue from 'vue'
import Router from 'vue-router'
import TaskList from '@/components/TaskList'
import Login from '@/components/Login'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'TaskList',
      component: TaskList
    },
    {
      path: '/Login',
      component: Login
    }
  ]
})
