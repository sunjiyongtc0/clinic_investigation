package com.clin.system.vo;


import com.clin.core.vo.CommonVO;

import java.io.Serializable;

/**
 * <pre>
 * Title :  角色信息的参数类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysRoleVO extends CommonVO implements Serializable {

	private static final long serialVersionUID = 4928960051913469176L;
	private String roleName;// 角色名称
	private Long roleId;// 角色ID
	private String[][] userId;// 用户ID

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String[][] getUserId() {
		return userId;
	}

	public void setUserId(String[][] userId) {
		this.userId = userId;
	}

}
