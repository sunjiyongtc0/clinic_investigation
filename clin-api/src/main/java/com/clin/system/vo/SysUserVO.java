package com.clin.system.vo;

import com.clin.core.vo.CommonVO;
import java.io.Serializable;

/**
 * <pre>
 * Title :  用户信息的参数类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysUserVO extends CommonVO implements Serializable {

	private static final long serialVersionUID = -228357463766836975L;
	private String username;
	private String nickname;
	private String mobile;
	private String status;
	private Long[] roleId;// 角色ID
	private Long userId;// 用户ID
	private String postCode;// 岗位编码

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long[] getRoleId() {
		return roleId;
	}

	public void setRoleId(Long[] roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
