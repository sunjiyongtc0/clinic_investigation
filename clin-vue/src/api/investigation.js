import request from '@/utils/request'

export function queryPdcInfos(query) {
  return request({
    url: '/clin/pro/inv/queryPdcInfos',
    method: 'get',
    params: query
  })
}

export function deleteInv(id) {
  return request({
    url: '/clin/pro/inv/deleteInv',
    method: 'post',
    params: {
      id: id
    }
  })
}

export function findDetailById(id) {
  return request({
    url: '/clin/pro/inv/findDetailById',
    method: 'get',
    params: {
      id: id
    }
  })
}




export function exportInv(data) {
  return request({
    url: '/clin/pro/inv/exportInv',
    method: 'post',
    responseType: 'blob',
    data
  })
}
