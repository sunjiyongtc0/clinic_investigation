package com.clin.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.clin.auth.entity.SysUserDetail;
import com.clin.auth.service.SysUserDetailService;
import com.clin.core.result.ActionResult;
import com.clin.core.result.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * <pre>
 * Title :  用户详细信息的控制层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
@RestController
@RequestMapping("/sysuserdetail")
public class SysUserDetailController {

	@Autowired
	private SysUserDetailService sysUserDetailService;


	/**
	 * 查询当前用户的授权角色、菜单和按钮
	 *
	 * @param username 用户名
	 * @param mobile   手机号
	 * @param parentId 菜单信息的上级ID
	 * @param postCode 岗位编码
	 * @param userId   用户ID
	 * @return
	 */
	@PostMapping(path = "/queryCurrentAuthority")
	public String queryCurrentAuthority(@RequestParam(name = "username", required = false) String username,
                                        @RequestParam(name = "mobile", required = false) String mobile, @RequestParam(name = "parentId", required = false) String parentId,
                                        @RequestParam(name = "postCode", required = false) String postCode, @RequestParam(name = "userId", required = false) Long userId) {
		if (StringUtils.isBlank(username) && StringUtils.isBlank(mobile)) {
			throw new IllegalArgumentException("用户名或手机号不能为空");
		}
		LinkedHashMap<String, Object> authorityMap = new LinkedHashMap<>();
		authorityMap.put("status", "ok");
		authorityMap.put("type", "account");
		List<String> roleCode = sysUserDetailService.getRoleCodeBySysUser(username, mobile);
		authorityMap.put("currentAuthority", roleCode);
		List<LinkedHashMap<String, Object>> menuList = sysUserDetailService.querySysMenuAuthorityTree(username, mobile, parentId, postCode, userId);
		authorityMap.put("menu", menuList);
		List<String> menuButtonList = sysUserDetailService.queryRoleMenuButton(username, mobile);
		authorityMap.put("menuButton", menuButtonList);

		return JSON.toJSONString(authorityMap);
	}


	/**
	 * 编辑用户详细信息
	 *
	 * @param sysUserDetail 用户详细信息
	 * @return
	 */
	@PutMapping(path = "/updateSysUserDetail")
	public ActionResult updateSysUserDetail(@RequestBody SysUserDetail sysUserDetail) {
		sysUserDetailService.updateSysUserDetail(sysUserDetail);
		return ResultBuilder.buildActionSuccess();
	}




	/**
	 * 修改用户密码
	 *
	 * @param password    原密码
	 * @param newPassword 新密码
	 * @param id          用户ID
	 * @return
	 */
	@PutMapping(path = "/updatePassword")
	public ActionResult updatePassword(@RequestParam(name = "password", required = true) String password,
                                       @RequestParam(name = "newPassword", required = true) String newPassword, @RequestParam(name = "id", required = true) Long id) {
		sysUserDetailService.updatePassword(password, newPassword, id);
		return ResultBuilder.buildActionSuccess();
	}


		/**
	 * 比对验证码
	 *
	 * @param charCaptcha 验证码字符
	 * @return
	 */
	@PostMapping(path = "/compareCaptcha")
	public ActionResult compareCaptcha(@RequestParam(name = "charCaptcha", required = true) String charCaptcha,  HttpServletRequest request) {
		HttpSession session = request.getSession();
		String key=(String) session.getAttribute("verifyCode");
		sysUserDetailService.compareCaptcha(charCaptcha,key);
		session.setAttribute("verifyCode","");
		return ResultBuilder.buildActionSuccess();
	}

	/**
	 * 重置密码（000000）
	 * */
	@PostMapping(path = "/retrievePassword")
	public ActionResult retrievePassword(@RequestParam(name = "id", required = true) long id) {
		sysUserDetailService.retrievePassword(id);
		return ResultBuilder.buildActionSuccess();
	}

}
