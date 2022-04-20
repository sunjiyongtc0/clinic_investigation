package com.clin.system.entity;


import com.clin.core.entity.TimeEntity;

import java.io.Serializable;

/**
 * <pre>
 * Title :  用户信息的实体类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysUser extends TimeEntity implements Serializable {

	private static final long serialVersionUID = 9220528102879200417L;
	private Long id;// ID
	private String username;// 用户名
	private String nickname;// 昵称
	private String password;// 密码
	private String email;// 邮箱
	private String mobile;// 手机号
	private String avatar;// 头像位置
	private String prefix;// 手机号国家代码
	private Long[] roleId;// 角色ID
	private Long orgId;// 所属机构ID
	private Short status;// 状态 0：禁用 1：正常

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long[] getRoleId() {
		return roleId;
	}

	public void setRoleId(Long[] roleId) {
		this.roleId = roleId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	//
//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		SysUser item = (SysUser) o;
//		return Objects.equal(id, item.id);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hashCode(id);
//	}
//
//	@Override
//	public String toString() {
//		return MoreObjects.toStringHelper(this).add("id", id).add("username", username)
//				.add("password", password).add("email", email).add("mobile", mobile)
//				.add("prefix", prefix).add("roleId", roleId).add("orgId", orgId)
//				.add("status", status).add("createTime", super.getCreateTime()).toString();
//	}

}
