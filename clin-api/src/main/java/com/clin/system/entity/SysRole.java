package com.clin.system.entity;

import com.clin.core.entity.TimeEntity;

import java.io.Serializable;

/**
 * <pre>
 * Title :  角色信息的实体类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysRole extends TimeEntity implements Serializable {

	private static final long serialVersionUID = 7011937068326603900L;
	private Long id;// ID
	private String roleCode;// 角色编码
	private String roleName;// 角色名称
	private String roleDescription;// 角色描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		SysRole item = (SysRole) o;
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
//		return MoreObjects.toStringHelper(this).add("id", id).add("roleCode", roleCode).add("roleName", roleName).add("roleDescription", roleDescription)
//				.add("createTime", super.getCreateTime()).toString();
//	}

}
