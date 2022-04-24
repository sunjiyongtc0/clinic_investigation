import Layout from '@/layout'

const systemRouter = {
  path: '/admin',
  component: Layout,
  redirect: 'noRedirect',
  alwaysShow: true,
  name: 'admin',
  meta: { title: '账户管理', icon: 'el-icon-user-solid' },
  children: [
    {
      path: '/sysuser',
      component: () => import('@/views/admin/sysuser'),
      name: 'SysUser',
      meta: { title: '账户列表', icon: 'el-icon-user' }
    }
  ]
}
export default systemRouter
