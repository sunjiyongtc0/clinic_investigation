import request from '@/utils/request'

export function getQuestionList(listQuery) {
  return request({
    url: '/clin/pro/question/getQuestionList',
    method: 'get',
    params:listQuery
  })
}


export function saveQuestion(data) {
  return request({
    url: '/clin/pro/question/saveQuestion',
    contentType : 'application/json;charset=utf-8', //设置请求头信息
    method: 'post',
    data,
  })
}


export function setQuestionEnable(id) {
  return request({
    url: '/clin/pro/question/setQuestionEnable',
    method: 'get',
    params:{
      id:id
    }
  })
}


export function findTitleById(id) {
  return request({
    url: '/clin/pro/question/findTitleById',
    method: 'get',
    params:{
      id:id
    }
  })
}
