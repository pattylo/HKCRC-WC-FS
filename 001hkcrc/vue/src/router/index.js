import { createRouter, createWebHistory } from 'vue-router'

import Layout from "../layout/Layout";

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/buildTable",
    children:[{
      path: 'buildTable',
      name: 'BuildTable',
      component: () => import("@/components/BuildTable")
      },
      {
        path: 'book',
        name: 'Book',
        component: () => import("@/views/Book")
      },
      {
        path: 'ShowItems',
        name: 'ShowItems',
        component: () => import("@/views/ShowItems")
      },
      {
        path: 'CellMonitor',
        name: 'CellMonitor',
        component: () => import("@/views/CellMonitor")
      },
      {
        path: 'SensorMonitor',
        name: 'SensorMonitor',
        component: () => import("@/components/SensorMonitor")
      },
      {
        path: 'Battery',
        name: 'Battery',
        component: () => import("@/components/Battery")
      },
      {
        path: 'Sensor',
        name: 'Sensor',
        component: () => import("@/components/Sensor")
      }
    ]
  },
  {
    path: '/Login',
    name: 'Login',
    component:  () => import("@/views/Login")
  },
  {
    path: '/Register',
    name: 'Register',
    component:  () => import("@/views/Register")
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
