import Layout from '@/layout'

const authorityRouter = {
  path: '/authority',
  component: Layout,
  redirect: 'noRedirect',
  alwaysShow: true,
  name: 'authority',
  meta: { title: 'authority', icon: 'lock' },
  children: [
    {
      path: '/sysrole',
      component: () => import('@/views/authority/sysrole'),
      name: 'SysRole',
      meta: { title: 'sysrole', icon: 'el-icon-postcard' }
    },
    {
      path: '/syslog',
      component: () => import('@/views/authority/syslog'),
      name: 'SysLog',
      meta: { title: '系统操作日志', icon: 'el-icon-set-up' }
    // },
    // {
    //   path: '/sysmenu',
    //   component: () => import('@/views/authority/sysmenu'),
    //   name: 'SysMenu',
    //   meta: { title: 'sysmenu', icon: 'el-icon-menu' }
    }
  ]
}
export default authorityRouter
