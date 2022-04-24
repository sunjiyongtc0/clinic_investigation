import request from '@/utils/request'

export function getScoreData(data) {
  return request({
    url: '/clin/pro/analy/getScoreData',
    contentType : 'application/json;charset=utf-8', //设置请求头信息
    method: 'post',
    data,
  })
}

export function getAgeData(data) {
  return request({
    url: '/clin/pro/analy/getAgeData',
    contentType : 'application/json;charset=utf-8', //设置请求头信息
    method: 'post',
    data,
  })
}

export function getSexData(data) {
  return request({
    url: '/clin/pro/analy/getSexData',
    contentType : 'application/json;charset=utf-8', //设置请求头信息
    method: 'post',
    data,
  })
}
