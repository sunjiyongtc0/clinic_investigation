import { asyncRoutes, asyncTemplateRoutes, constantRoutes } from '@/router'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

export function filterAuthority(eacMenu) {
  const res = []

  eacMenu.forEach(route => {
    const tmp = { ...route }
    if (tmp.routes) {
      tmp.routes = filterAuthority(tmp.routes)
      tmp.routes.forEach(path => {
        res.push(path)
      })
    }
    res.push(tmp.path)
  })

  return res
}

export function filterConstantRoutes(routes, eacMenuPath) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (tmp.children) {
      tmp.children = filterConstantRoutes(tmp.children, eacMenuPath)
    }
    if (eacMenuPath.toString().indexOf(tmp.path) !== -1) {
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, { routes, eacMenu, roles }) => {
    state.addRoutes = routes
    const eacMenuPath = filterAuthority(JSON.parse(eacMenu))
    const newConstantRoutes = filterConstantRoutes(constantRoutes, eacMenuPath)
    if (roles.includes('ROLE_EAC_ADMIN')) {
      state.routes = newConstantRoutes.concat(asyncTemplateRoutes)
    } else {
      state.routes = newConstantRoutes
    }
  }
}

const actions = {
  generateRoutes({ commit }, { roles, eacMenu }) {
    return new Promise(resolve => {
      let accessedRoutes
      if (roles.includes('ROLE_EAC_ADMIN')) {
        accessedRoutes = asyncTemplateRoutes || []
      } else {
        accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
      }
      commit('SET_ROUTES', { accessedRoutes, eacMenu, roles })
      resolve(accessedRoutes)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
