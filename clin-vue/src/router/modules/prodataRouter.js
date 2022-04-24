import Layout from '@/layout'

const prodataRouter = {
  path: '/prodata',
  component: Layout,
  redirect: 'noRedirect',
  alwaysShow: true,
  name: 'prodata',
  meta: { title: '数据管理', icon: 'el-icon-success' },
  children: [
    {
      path: '/datalist',
      component: () => import('@/views/prodata/datalist'),
      name: 'datalist',
      meta: { title: '义诊列表', icon: 'el-icon-s-operation' }
    }
  ]
}
export default prodataRouter
