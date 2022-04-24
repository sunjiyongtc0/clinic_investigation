import Layout from '@/layout'

const questionRouter = {
  path: '/question',
  component: Layout,
  redirect: 'noRedirect',
  alwaysShow: true,
  name: 'question',
  meta: { title: '问卷管理', icon: 'el-icon-s-management' },
  children: [
    {
      path: '/questlist',
      component: () => import('@/views/question/questlist'),
      name: 'questlist',
      meta: { title: '问卷列表', icon: 'el-icon-s-order' }
    }
  ]
}
export default questionRouter
