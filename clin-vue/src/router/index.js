/**
 * 路由配置
 */
import Vue from 'vue'
import Router from 'vue-router'

const originalPush = Router.prototype.push
Router.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(Router)

import Layout from '@/layout'
import authorityRouter from './modules/authorityRouter'
import systemRouter from './modules/systemRouter'
import questionRouter from './modules/questionRouter'
import prodataRouter from './modules/prodataRouter'
import examineRouter from "./modules/examineRouter"
import chartreportRouter from "./modules/chartreportRouter"
export const templateRoutes = []

export const constantRoutes = [
  {
    path: '/',
    component: Layout,
    redirect: '/workbench',
    name: 'home',
    meta: { title: 'home', icon: 'el-icon-s-home' },
    children: [
      {
        path: '/workbench',
        component: () => import('@/views/home/index'),
        name: 'Workbench',
        meta: { title: 'workbench', icon: 'dashboard', affix: true }
      },
    //   {
    //     path: '/mynotification',
    //     component: () => import('@/views/home/mynotification'),
    //     name: 'MyNotification',
    //     meta: { title: 'mynotification', icon: 'message' }
    //   }
    ]
  },
  prodataRouter,
  questionRouter,
  examineRouter,
  chartreportRouter,
  systemRouter,
  authorityRouter,
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: 'profile', icon: 'user', noCache: true }
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  }

  ]



const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router


export const asyncTemplateRoutes = [
  // {
  //   path: '/template',
  //   component: Layout,
  //   redirect: 'noRedirect',
  //   name: 'template',
  //   meta: {
  //     title: 'template',
  //     icon: 'table'
  //   },
  //   children: templateRoutes
  // }
]

export const asyncRoutes = [
  {
    path: '/permission',
    component: Layout,
    redirect: '/permission/page',
    alwaysShow: true, // will always show the root menu
    name: 'Permission',
    meta: {
      title: 'permission',
      icon: 'lock',
      roles: ['ROLE_EDITOR'] // you can set roles in root nav
    },
    children: [
      {
        path: 'page',
        component: () => import('@/views/permission/page'),
        name: 'PagePermission',
        meta: {
          title: 'pagePermission',
          roles: ['ROLE_ADMIN'] // or you can only set roles in sub nav
        }
      },
      {
        path: 'directive',
        component: () => import('@/views/permission/directive'),
        name: 'DirectivePermission',
        meta: {
          title: 'directivePermission'
        }
      },
      {
        path: 'role',
        component: () => import('@/views/permission/role'),
        name: 'RolePermission',
        meta: {
          title: 'rolePermission',
          roles: ['ROLE_ADMIN']
        }
      }
    ]
  }
]
