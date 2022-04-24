import request from '@/utils/request'

export function getStatisticsData() {
  return request({
    url: '/clin/pro/reprot/getStatisticsData',
    method: 'get'
  })
}


export function getAgeData(startTime,endTime) {
  return request({
    url: '/clin/pro/reprot/getAgeData',
    method: 'get',
    params:{
      startTime:startTime,
      endTime:endTime
    }
  })
}


export function getScoreData(startTime,endTime) {
  return request({
    url: '/clin/pro/reprot/getScoreData',
    method: 'get',
    params:{
      startTime:startTime,
      endTime:endTime
    }
  })
}


export function getCountData(startTime,endTime) {
  return request({
    url: '/clin/pro/reprot/getCountData',
    method: 'get',
    params:{
      startTime:startTime,
      endTime:endTime
    }
  })
}



