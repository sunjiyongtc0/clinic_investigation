import Layout from '@/layout'

const chartreportRouter = {
  path: '/chartreport',
  component: Layout,
  redirect: 'noRedirect',
  alwaysShow: true,
  name: 'chartreport',
  meta: { title: '数据统计', icon: 'el-icon-stopwatch' },
  children: [
    {
      path: '/bigscreen',
      component: () => import('@/views/chartreport/bigscreen'),
      name: 'bigscreen',
      meta: { title: '数据大屏', icon: 'el-icon-stopwatch' }
    },
    {
      path: '/report',
      component: () => import('@/views/chartreport/report'),
      name: 'report',
      meta: { title: '数据分析', icon: 'el-icon-stopwatch' }
    }

  ]
}

export default chartreportRouter
