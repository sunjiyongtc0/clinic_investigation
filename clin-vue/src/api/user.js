import request from '@/utils/request'

export function login(data) {
  data['scope'] = 'ui'
  data['grant_type'] = 'password'
  data['client_id'] = 'browser'
  data['type'] = 'account'
  return request({
    url: '/clin/oauth/token',
    method: 'post',
    headers: { 'Authorization': 'Basic YnJvd3Nlcjo=', 'Content-Type': 'application/x-www-form-urlencoded' },
    params: data
  })
}


export function getInfo(token) {
  return request({
    url: '/clin/users/getSysUser',
    method: 'get'
  })
}

export function registerNewSession() {
  return request({
    url: '/clin/users/registerNewSession',
    method: 'post'
  })
}

export function queryCurrentAuthority(data) {
  return request({
    url: '/clin/sysuserdetail/queryCurrentAuthority',
    method: 'post',
    params: data
  })
}


export function compareCaptcha(data) {
  return request({
    url: '/clin/sysuserdetail/compareCaptcha',
    method: 'post',
    params: {
      charCaptcha: data
    }
  })
}


export function retrievePassword(id) {
  return request({
    url: '/clin/sysuserdetail/retrievePassword',
    method: 'post',
    params: {
      id: id
    }
  })
}


// export function retrievePassword(email, charCaptcha) {
//   return request({
//     url: '/api/account/sysuserdetail/retrievePassword',
//     method: 'post',
//     params: {
//       email: email,
//       charCaptcha: charCaptcha
//     }
//   })
// }
//
// export function registerAccount(data) {
//   return request({
//     url: '/api/account/sysuserdetail/registerAccount',
//     method: 'post',
//     data
//   })
// }
