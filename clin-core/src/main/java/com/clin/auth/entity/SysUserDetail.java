package com.clin.auth.entity;


import java.io.Serializable;

/**
 * <pre>
 * Title :  用户详细信息的实体类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysUserDetail  implements Serializable {

	private static final long serialVersionUID = 7638764865196438417L;
	private Long id;// ID
	private String username;// 用户名
	private String password;// 密码
	private String email;// 邮箱
	private String mobile;// 手机号
	private String prefix;// 手机号国家代码
	private Long[] roleId;// 角色ID
	private Long orgId;// 所属机构ID
	private Short status;// 状态 0：禁用 1：正常
	private String nickname;// 昵称
	private String phone;// 联系电话
	private String provinceRegionCode;// 省份区域代码
	private String cityRegionCode;// 地市区域代码
	private String address;// 街道地址
	private String profile;// 个人简介
	private Short notifyMessage;// 是否通知系统消息 0：关 1：开
	private Short notifyTodo;// 是否通知待办任务 0：关 1：开

	public Long getId() {
		return id;
	}

	@SuppressWarnings("all")
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	@SuppressWarnings("all")
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	@SuppressWarnings("all")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	@SuppressWarnings("all")
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	@SuppressWarnings("all")
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPrefix() {
		return prefix;
	}

	@SuppressWarnings("all")
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long[] getRoleId() {
		return roleId;
	}

	@SuppressWarnings("all")
	public void setRoleId(Long[] roleId) {
		this.roleId = roleId;
	}

	public Long getOrgId() {
		return orgId;
	}

	@SuppressWarnings("all")
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Short getStatus() {
		return status;
	}

	@SuppressWarnings("all")
	public void setStatus(Short status) {
		this.status = status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvinceRegionCode() {
		return provinceRegionCode;
	}

	public void setProvinceRegionCode(String provinceRegionCode) {
		this.provinceRegionCode = provinceRegionCode;
	}

	public String getCityRegionCode() {
		return cityRegionCode;
	}

	public void setCityRegionCode(String cityRegionCode) {
		this.cityRegionCode = cityRegionCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Short getNotifyMessage() {
		return notifyMessage;
	}

	public void setNotifyMessage(Short notifyMessage) {
		this.notifyMessage = notifyMessage;
	}

	public Short getNotifyTodo() {
		return notifyTodo;
	}

	public void setNotifyTodo(Short notifyTodo) {
		this.notifyTodo = notifyTodo;
	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		SysUserDetail item = (SysUserDetail) o;
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
//		return MoreObjects.toStringHelper(this).add("id", id).add("username", username).add("password", password).add("email", email).add("mobile", mobile)
//				.add("prefix", prefix).add("roleId", roleId).add("orgId", orgId).add("status", status).add("nickname", nickname).add("phone", phone)
//				.add("provinceRegionCode", provinceRegionCode).add("cityRegionCode", cityRegionCode).add("address", address).add("profile", profile)
//				.add("notifyMessage", notifyMessage).add("notifyTodo", notifyTodo).add("createTime", super.getCreateTime()).toString();
//	}

}
