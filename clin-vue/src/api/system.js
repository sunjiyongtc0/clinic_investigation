import request from '@/utils/request'



export function querySysOrg(query) {
  return request({
    url: '/clin/sysorg/querySysOrg',
    method: 'get',
    params: query
  })
}

export function querySysRole(query) {
  return request({
    url: '/clin/sysrole/querySysRole',
    method: 'get',
    params: query
  })
}

export function querySysMenuTree() {
  return request({
    url: '/clin/sysmenu/querySysMenuTree',
    method: 'get'
  })
}

export function queryOrgUserTree(roleId, assign) {
  return request({
    url: '/clin/sysorg/queryOrgUserTree',
    method: 'get',
    params: {
      roleId: roleId,
      assign: assign
    }
  })
}

export function queryMenuIdByRoleId(data) {
  return request({
    url: '/clin/sysmenu/queryMenuIdByRoleId',
    method: 'get',
    params: {
      roleId: data
    }
  })
}

export function updateSysUser(data) {
  return request({
    url: '/clin/sysuser/updateSysUser',
    method: 'put',
    data
  })
}

export function updateNickName(data) {
  return request({
    // url: '/clin/sysuser/updateNickName',
    url: '/clin/file/uploadImg',
    method: 'post',
    data:data,
    contentType : 'multipart/form-data', //设置请求头信息
  })
}

export function updateSysUrl(data) {
  return request({
    url: '/clin/sysurl/updateSysUrl',
    method: 'put',
    data
  })
}


export function updateSysRole(data) {
  return request({
    url: '/clin/sysrole/updateSysRole',
    method: 'put',
    data
  })
}


export function updateSysOrg(data) {
  return request({
    url: '/clin/sysorg/updateSysOrg',
    method: 'put',
    data
  })
}


export function querySysUser(query) {
  return request({
    url: '/clin/sysuser/querySysUser',
    method: 'get',
    params: query
  })
}


export function addSysUser(data) {
  return request({
    url: '/clin/sysuser/addSysUser',
    method: 'post',
    data
  })
}


export function insertSysUserAndRole(data) {
  return request({
    url: '/clin/sysuser/insertSysUserAndRole',
    method: 'post',
    data
  })
}


export function queryRoleName(userId, assign) {
  return request({
    url: '/clin/sysrole/queryRoleName',
    method: 'get',
    params: {
      userId: userId,
      assign: assign
    }
  })
}


export function querySysOrgTree() {
  return request({
    url: '/clin/sysorg/querySysOrgTree',
    method: 'get'
  })
}




export function deleteSysUser(data) {
  return request({
    url: '/clin/sysuser/deleteSysUser',
    method: 'post',
    params: {
      id: data
    }
  })
}


export function exportSysUser(data) {
  return request({
    url: '/clin/sysuser/exportSysUser',
    method: 'post',
    responseType: 'blob',
    data
  })
}


export function authorizeRoleToUser(data) {
  return request({
    url: '/clin/sysuser/authorizeRoleToUser',
    method: 'post',
    data
  })
}


export function queryRoleNameCheckbox() {
  return request({
    url: '/clin/sysrole/queryRoleNameCheckbox',
    method: 'get'
  })
}


export function updateSysUserDetail(data) {
  return request({
    url: '/clin/sysuserdetail/updateSysUserDetail',
    method: 'put',
    data
  })
}


export function updateSysMenu(data) {
  return request({
    url: '/clin/sysmenu/updateSysMenu',
    method: 'put',
    data
  })
}


export function queryUrlIdByRoleId(roleId) {
  return request({
    url: '/clin/sysurl/queryUrlIdByRoleId',
    method: 'get',
    params: {
      roleId: roleId
    }
  })
}


export function querySysUrl(query) {
  return request({
    url: '/clin/sysurl/querySysUrl',
    method: 'get',
    params: query
  })
}



export function querySysMenuButtonTree() {
  return request({
    url: '/clin/sysmenu/querySysMenuButtonTree',
    method: 'get'
  })
}





export function querySysMenu(query) {
  return request({
    url: '/clin/sysmenu/querySysMenu',
    method: 'get',
    params: query
  })
}


export function queryRoleIdByUrlId(data) {
  return request({
    url: '/clin/sysurl/queryRoleIdByUrlId',
    method: 'get',
    params: {
      urlId: data
    }
  })
}


export function updatePassword(password, newPassword, id) {
  return request({
    url: '/clin/sysuserdetail/updatePassword',
    method: 'put',
    params: {
      password: password,
      newPassword: newPassword,
      id: id
    }
  })
}




export function authorizeMenuToRole(data) {
  return request({
    url: '/clin/sysmenu/authorizeMenuToRole',
    method: 'post',
    data
  })
}


export function addSysUrl(data) {
  return request({
    url: '/clin/sysurl/addSysUrl',
    method: 'post',
    data
  })
}




export function queryMenuButtonByRoleCode(data) {
  return request({
    url: '/clin/sysmenu/queryMenuButtonByRoleCode',
    method: 'get',
    params: {
      roleCode: data
    }
  })
}

export function queryLogData(query) {
  return request({
    url: '/clin/syslog/queryLogData',
    method: 'get',
    params:query
  })
}



export function deleteSysLog(ids) {
  return request({
    url: '/clin/syslog/deleteSysLog',
    method: 'get',
    params:{
      ids:ids
    }
  })
}




/** 用户API开始 */
//
// export function queryOnlineSysUser(query) {
//   return request({
//     url: '/clin/sysuser/queryOnlineSysUser',
//     method: 'get',
//     params: query
//   })
// }
//

// export function queryUsername() {
//   return request({
//     url: '/clin/sysuser/queryUsername',
//     method: 'get'
//   })
// }

//
// export function exportWordSysUser(data) {
//   return request({
//     url: '/clin/sysuser/exportWordSysUser',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysUser(data) {
//   return request({
//     url: '/clin/sysuser/exportPDFSysUser',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//


//
// /** 用户API结束 */
// /** 岗位API开始 */
//
// export function querySysPost(query) {
//   return request({
//     url: '/clin/syspost/querySysPost',
//     method: 'get',
//     params: query
//   })
// }
//
// export function querySysPostTree() {
//   return request({
//     url: '/clin/syspost/querySysPostTree',
//     method: 'get'
//   })
// }
//
// export function addSysPost(data) {
//   return request({
//     url: '/clin/syspost/addSysPost',
//     method: 'post',
//     data
//   })
// }
//
// export function updateSysPost(data) {
//   return request({
//     url: '/clin/syspost/updateSysPost',
//     method: 'put',
//     data
//   })
// }
//
// export function deleteSysPost(data) {
//   return request({
//     url: '/clin/syspost/deleteSysPost',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }
//
// export function exportSysPost(data) {
//   return request({
//     url: '/clin/syspost/exportSysPost',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysPost(data) {
//   return request({
//     url: '/clin/syspost/exportWordSysPost',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysPost(data) {
//   return request({
//     url: '/clin/syspost/exportPDFSysPost',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** 岗位API结束 */
// /** 用户与岗位关联API开始 */
//
// export function querySysUserPost(query) {
//   return request({
//     url: '/clin/sysuserpost/querySysUserPost',
//     method: 'get',
//     params: query
//   })
// }
//
// export function querySysUserPostTree(userId) {
//   return request({
//     url: '/clin/sysuserpost/querySysUserPostTree',
//     method: 'get',
//     params: {
//       userId: userId
//     }
//   })
// }
//
// export function addSysUserPost(data) {
//   return request({
//     url: '/clin/sysuserpost/addSysUserPost',
//     method: 'post',
//     data
//   })
// }
//
// export function updateSysUserPost(data) {
//   return request({
//     url: '/clin/sysuserpost/updateSysUserPost',
//     method: 'put',
//     data
//   })
// }
//
// export function deleteSysUserPost(data) {
//   return request({
//     url: '/clin/sysuserpost/deleteSysUserPost',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }
//
// export function exportSysUserPost(data) {
//   return request({
//     url: '/clin/sysuserpost/exportSysUserPost',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysUserPost(data) {
//   return request({
//     url: '/clin/sysuserpost/exportWordSysUserPost',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysUserPost(data) {
//   return request({
//     url: '/clin/sysuserpost/exportPDFSysUserPost',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** 用户与岗位关联API结束 */
// /** 角色API开始 */


export function addSysRole(data) {
  return request({
    url: '/clin/sysrole/addSysRole',
    method: 'post',
    data
  })
}



export function deleteSysRole(data) {
  return request({
    url: '/clin/sysrole/deleteSysRole',
    method: 'post',
    params: {
      id: data
    }
  })
}


//
// export function queryRoleCode() {
//   return request({
//     url: '/clin/sysrole/queryRoleCode',
//     method: 'get'
//   })
// }
//
// export function exportSysRole(data) {
//   return request({
//     url: '/clin/sysrole/exportSysRole',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysRole(data) {
//   return request({
//     url: '/clin/sysrole/exportWordSysRole',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysRole(data) {
//   return request({
//     url: '/clin/sysrole/exportPDFSysRole',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }

export function authorizeUserToRole(data) {
  return request({
    url: '/clin/sysrole/authorizeUserToRole',
    method: 'post',
    data
  })
}
//
// /** 角色API结束 */
// /** URL API开始 */
//


//

//
// export function deleteSysUrl(data) {
//   return request({
//     url: '/clin/sysurl/deleteSysUrl',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }


//
// export function authorizeRoleToUrl(data) {
//   return request({
//     url: '/clin/sysurl/authorizeRoleToUrl',
//     method: 'post',
//     data
//   })
// }

export function authorizeUrlToRole(data) {
  return request({
    url: '/clin/sysurl/authorizeUrlToRole',
    method: 'post',
    data
  })
}
//
// export function exportSysUrl(data) {
//   return request({
//     url: '/clin/sysurl/exportSysUrl',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysUrl(data) {
//   return request({
//     url: '/clin/sysurl/exportWordSysUrl',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysUrl(data) {
//   return request({
//     url: '/clin/sysurl/exportPDFSysUrl',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** URL API结束 */
// /** 菜单API开始 */

//
// export function addSysMenu(data) {
//   return request({
//     url: '/clin/sysmenu/addSysMenu',
//     method: 'post',
//     data
//   })
// }

//
// export function deleteSysMenu(data) {
//   return request({
//     url: '/clin/sysmenu/deleteSysMenu',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }



//
// export function queryMenuButtonCheckbox() {
//   return request({
//     url: '/clin/sysmenu/queryMenuButtonCheckbox',
//     method: 'get'
//   })
// }
//
// export function queryCheckedMenuButton(data) {
//   return request({
//     url: '/clin/sysmenu/queryCheckedMenuButton',
//     method: 'get',
//     params: {
//       menuCode: data
//     }
//   })
// }


//
// export function authorizeMenuButtonToRole(data) {
//   return request({
//     url: '/clin/sysmenu/authorizeMenuButtonToRole',
//     method: 'post',
//     data
//   })
// }
//
// export function queryDataFieldByMenuCode(data) {
//   return request({
//     url: '/clin/sysmenu/queryDataFieldByMenuCode',
//     method: 'get',
//     params: {
//       menuCode: data
//     }
//   })
// }
//
// export function deleteDataField(data) {
//   return request({
//     url: '/clin/sysmenu/deleteDataField',
//     method: 'post',
//     data
//   })
// }
//
// export function authorizeMenuDataToRole(data) {
//   return request({
//     url: '/clin/sysmenu/authorizeMenuDataToRole',
//     method: 'post',
//     data
//   })
// }
//
// export function exportSysMenu(data) {
//   return request({
//     url: '/clin/sysmenu/exportSysMenu',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysMenu(data) {
//   return request({
//     url: '/clin/sysmenu/exportWordSysMenu',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysMenu(data) {
//   return request({
//     url: '/clin/sysmenu/exportPDFSysMenu',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** 菜单API结束 */
// /** 机构API开始 */
//

// export function queryOrgType() {
//   return request({
//     url: '/clin/sysorg/queryOrgType',
//     method: 'get'
//   })
// }
//

//
// export function queryOrgUserTree(roleId, assign) {
//   return request({
//     url: '/clin/sysorg/queryOrgUserTree',
//     method: 'get',
//     params: {
//       roleId: roleId,
//       assign: assign
//     }
//   })
// }
//
// export function queryModelOrgUserTree(modelId, assign) {
//   return request({
//     url: '/clin/sysorg/queryModelOrgUserTree',
//     method: 'get',
//     params: {
//       modelId: modelId,
//       assign: assign
//     }
//   })
// }
//
// export function addSysOrg(data) {
//   return request({
//     url: '/clin/sysorg/addSysOrg',
//     method: 'post',
//     data
//   })
// }
//

//
// export function deleteSysOrg(data) {
//   return request({
//     url: '/clin/sysorg/deleteSysOrg',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }
//
// export function exportSysOrg(data) {
//   return request({
//     url: '/clin/sysorg/exportSysOrg',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysOrg(data) {
//   return request({
//     url: '/clin/sysorg/exportWordSysOrg',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysOrg(data) {
//   return request({
//     url: '/clin/sysorg/exportPDFSysOrg',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** 机构API结束 */
// /** 字典API开始 */
//
// export function querySysDict(query) {
//   return request({
//     url: '/clin/sysdict/querySysDict',
//     method: 'get',
//     params: query
//   })
// }
//
// export function querySysDictTree() {
//   return request({
//     url: '/clin/sysdict/querySysDictTree',
//     method: 'get'
//   })
// }
//
// export function addSysDict(data) {
//   return request({
//     url: '/clin/sysdict/addSysDict',
//     method: 'post',
//     data
//   })
// }
//
// export function updateSysDict(data) {
//   return request({
//     url: '/clin/sysdict/updateSysDict',
//     method: 'put',
//     data
//   })
// }
//
// export function deleteSysDict(data) {
//   return request({
//     url: '/clin/sysdict/deleteSysDict',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }
//
// export function exportSysDict(data) {
//   return request({
//     url: '/clin/sysdict/exportSysDict',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysDict(data) {
//   return request({
//     url: '/clin/sysdict/exportWordSysDict',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysDict(data) {
//   return request({
//     url: '/clin/sysdict/exportPDFSysDict',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function queryDictByDictType(dictType) {
//   return request({
//     url: '/clin/sysdict/queryDictByDictType',
//     method: 'get',
//     params: {
//       dictType: dictType
//     }
//   })
// }
//
// /** 字典API结束 */
// /** 区域API开始 */
//
// export function querySysRegion(query) {
//   return request({
//     url: '/clin/sysregion/querySysRegion',
//     method: 'get',
//     params: query
//   })
// }
//
// export function queryRegionType() {
//   return request({
//     url: '/clin/sysregion/queryRegionType',
//     method: 'get'
//   })
// }
//
// export function querySysRegionTree() {
//   return request({
//     url: '/clin/sysregion/querySysRegionTree',
//     method: 'get'
//   })
// }
//
// export function addSysRegion(data) {
//   return request({
//     url: '/clin/sysregion/addSysRegion',
//     method: 'post',
//     data
//   })
// }
//
// export function updateSysRegion(data) {
//   return request({
//     url: '/clin/sysregion/updateSysRegion',
//     method: 'put',
//     data
//   })
// }
//
// export function deleteSysRegion(data) {
//   return request({
//     url: '/clin/sysregion/deleteSysRegion',
//     method: 'post',
//     params: {
//       regionCode: data
//     }
//   })
// }
//
// export function exportSysRegion(data) {
//   return request({
//     url: '/clin/sysregion/exportSysRegion',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysRegion(data) {
//   return request({
//     url: '/clin/sysregion/exportWordSysRegion',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysRegion(data) {
//   return request({
//     url: '/clin/sysregion/exportPDFSysRegion',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** 区域API结束 */
// /** 参数API开始 */
//
// export function querySysParam(query) {
//   return request({
//     url: '/clin/sysparam/querySysParam',
//     method: 'get',
//     params: query
//   })
// }
//
// export function addSysParam(data) {
//   return request({
//     url: '/clin/sysparam/addSysParam',
//     method: 'post',
//     data
//   })
// }
//
// export function updateSysParam(data) {
//   return request({
//     url: '/clin/sysparam/updateSysParam',
//     method: 'put',
//     data
//   })
// }
//
// export function deleteSysParam(data) {
//   return request({
//     url: '/clin/sysparam/deleteSysParam',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }
//
// export function exportSysParam(data) {
//   return request({
//     url: '/clin/sysparam/exportSysParam',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysParam(data) {
//   return request({
//     url: '/clin/sysparam/exportWordSysParam',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysParam(data) {
//   return request({
//     url: '/clin/sysparam/exportPDFSysParam',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** 参数API结束 */
// /** 应用API开始 */
//
// export function queryAppClient(query) {
//   return request({
//     url: '/api/uaa/appclient/queryAppClient',
//     method: 'get',
//     params: query
//   })
// }
//
// export function addAppClient(data) {
//   return request({
//     url: '/api/uaa/appclient/addAppClient',
//     method: 'post',
//     data
//   })
// }
//
// export function updateAppClient(data) {
//   return request({
//     url: '/api/uaa/appclient/updateAppClient',
//     method: 'put',
//     data
//   })
// }
//
// export function deleteAppClient(data) {
//   return request({
//     url: '/api/uaa/appclient/deleteAppClient',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }
//
// export function exportAppClient(data) {
//   return request({
//     url: '/api/uaa/appclient/exportAppClient',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordAppClient(data) {
//   return request({
//     url: '/clin/appclient/exportWordAppClient',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFAppClient(data) {
//   return request({
//     url: '/clin/appclient/exportPDFAppClient',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** 应用API结束 */
// /** 租户API开始 */
//
// export function querySysTenant(query) {
//   return request({
//     url: '/clin/systenant/querySysTenant',
//     method: 'get',
//     params: query
//   })
// }
//
// export function addSysTenant(data) {
//   return request({
//     url: '/clin/systenant/addSysTenant',
//     method: 'post',
//     data
//   })
// }
//
// export function updateSysTenant(data) {
//   return request({
//     url: '/clin/systenant/updateSysTenant',
//     method: 'put',
//     data
//   })
// }
//
// export function deleteSysTenant(data) {
//   return request({
//     url: '/clin/systenant/deleteSysTenant',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }
//
// export function exportSysTenant(data) {
//   return request({
//     url: '/clin/systenant/exportSysTenant',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportWordSysTenant(data) {
//   return request({
//     url: '/clin/systenant/exportWordSysTenant',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// export function exportPDFSysTenant(data) {
//   return request({
//     url: '/clin/systenant/exportPDFSysTenant',
//     method: 'post',
//     responseType: 'blob',
//     data
//   })
// }
//
// /** 租户API结束 */
// /** 消息通知API开始 */
//
// export function queryNotification(query) {
//   return request({
//     url: '/api/notification/notification/queryNotification',
//     method: 'get',
//     params: query
//   })
// }
//
// export function addNotification(data) {
//   return request({
//     url: '/api/notification/notification/addNotification',
//     method: 'post',
//     data
//   })
// }
//
// export function updateNotification(data) {
//   return request({
//     url: '/api/notification/notification/updateNotification',
//     method: 'put',
//     data
//   })
// }
//
// export function deleteNotification(data) {
//   return request({
//     url: '/api/notification/notification/deleteNotification',
//     method: 'post',
//     params: {
//       id: data
//     }
//   })
// }
//
// export function queryMyNotification(query) {
//   return request({
//     url: '/api/notification/notification/queryMyNotification',
//     method: 'get',
//     params: query
//   })
// }
//
// export function queryNotificationType() {
//   return request({
//     url: '/api/notification/notification/queryNotificationType',
//     method: 'get'
//   })
// }
//
// export function queryNotificationPriority() {
//   return request({
//     url: '/api/notification/notification/queryNotificationPriority',
//     method: 'get'
//   })
// }
//
// /** 消息通知API结束 */
