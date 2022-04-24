import Layout from '@/layout'

const examineRouter = {
  path: '/examine',
  component: Layout,
  redirect: 'noRedirect',
  alwaysShow: true,
  name: 'examine',
  meta: { title: '数据审核', icon: 'el-icon-circle-check' },
  children: [
    {
      path: '/exwait',
      component: () => import('@/views/examine/exwait'),
      name: 'exwait',
      meta: { title: '待审核列表', icon: 'el-icon-document-copy' }
    },
    {
      path: '/exconfirm',
      component: () => import('@/views/examine/exconfirm'),
      name: 'exconfirm',
      meta: { title: '已审核列表', icon: 'el-icon-document-checked' }
    }
  ]
}

export default examineRouter
