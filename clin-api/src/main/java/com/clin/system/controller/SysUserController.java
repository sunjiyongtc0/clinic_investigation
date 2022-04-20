package com.clin.system.controller;

import com.clin.core.result.ActionResult;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.system.entity.SysUser;
import com.clin.system.service.SysUserService;
import com.clin.system.vo.SysUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



/**
 * <pre>
 * Title :  用户信息的控制层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SessionRegistry sessionRegistry;

	/**
	 * 注册一个新的会话
	 *
	 * @param request 请求对象
	 * @return
	 */
	@PostMapping(path = "/registerNewSession")
	public ActionResult registerNewSession(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		sessionRegistry.registerNewSession(request.getSession().getId(), authentication.getPrincipal());
		return ResultBuilder.buildActionSuccess();
	}


	/**
	 * 查询用户分页
	 *
	 * @param sysUserVO 用户前端参数
	 * @return
	 */
	@GetMapping(path = "/querySysUser")
	public ListResult<Object> querySysUser(SysUserVO sysUserVO) {
//		Map<String, Object> data = sysUserService.querySysUser(sysUserVO.getCurrentPage(), sysUserVO.getPageSize(), sysUserVO.getUsername(),
//				sysUserVO.getStatus(), sysUserVO.getSorter());
		Map<String, Object> data = sysUserService.querySysUser(sysUserVO);
		return ResultBuilder.buildListSuccess(data);
	}


	/**
	 * 编辑用户别名
	 *
	 * @param sysUser 用户对象
	 * @return
	 */
	@PutMapping(path = "/updateNickName")
	public ActionResult updateNickName(@RequestBody SysUser sysUser) {
		sysUserService.updateNickName(sysUser);
		return ResultBuilder.buildActionSuccess();
	}


	/**
	 * 新增用户
	 *
	 * @param sysUser 用户对象
	 * @return
	 */
	@PostMapping(path = "/addSysUser")
	public ActionResult addSysUser(@RequestBody SysUser sysUser) {
		sysUserService.insertSysUser(sysUser);
		return ResultBuilder.buildActionSuccess();
	}


	/**
	 * 新增用户并赋值角色
	 *
	 * @param sysUser 用户对象
	 * @return
	 */
	@PostMapping(path = "/insertSysUserAndRole")
	public ActionResult insertSysUserAndRole(@RequestBody SysUser sysUser) {
		sysUserService.insertSysUserAndRole(sysUser);
		return ResultBuilder.buildActionSuccess();
	}


	/**
	 * 编辑用户
	 *
	 * @param sysUser 用户对象
	 * @return
	 */
	@PutMapping(path = "/updateSysUser")
	public ActionResult updateSysUser(@RequestBody SysUser sysUser) {
		sysUserService.updateSysUser(sysUser);
		return ResultBuilder.buildActionSuccess();
	}

	/**
	 * 删除用户
	 *
	 * @param id 用户ID
	 * @return
	 */
	@PostMapping(path = "/deleteSysUser")
	public ActionResult deleteSysUser(@RequestParam(name = "id", required = true) Long[] id) {
//		sysUserService.deleteSysUser(id);
		sysUserService.statusSysUser(id);
		return ResultBuilder.buildActionSuccess();
	}


	/**
	 * 将对应的角色授予用户
	 *
	 * @param sysUserVO 用户前端参数
	 * @return
	 */
	@PostMapping(path = "/authorizeRoleToUser")
	public ActionResult authorizeRoleToUser( @RequestBody SysUserVO sysUserVO) {
		sysUserService.insertRoleIdUserId(sysUserVO.getRoleId(), sysUserVO.getUserId());
		return ResultBuilder.buildActionSuccess();
	}

}
