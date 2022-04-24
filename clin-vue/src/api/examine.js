import request from '@/utils/request'


export function createExamine(data) {
  return request({
    url: '/clin/pro/inv/createExamine',
    contentType : 'application/json;charset=utf-8', //设置请求头信息
    method: 'post',
    data,
  })
}

export function queryExwaitList(query) {
  return request({
    url: '/clin/pro/examine/queryExwaitList',
    method: 'get',
    params: query
  })
}

export function queryExconfirmList(query) {
  return request({
    url: '/clin/pro/examine/queryExconfirmList',
    method: 'get',
    params: query
  })
}


export function refuse(query) {
  return request({
    url: '/clin/pro/examine/refuse',
    method: 'get',
    params: query
  })
}

export function adopt(query) {
  return request({
    url: '/clin/pro/examine/adopt',
    method: 'get',
    params: query
  })
}

export function findBakDetailById(id) {
  return request({
    url: '/clin/pro/examine/findBakDetailById',
    method: 'get',
    params: {
      id: id
    }
  })
}
