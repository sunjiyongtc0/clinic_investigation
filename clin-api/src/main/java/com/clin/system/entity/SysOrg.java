package com.clin.system.entity;


import com.clin.core.entity.CommonEntity;

import java.io.Serializable;


/**
 * <pre>
 * Title :  机构信息的实体类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysOrg extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 6414776805478207026L;
	private Long id;// ID
	private String orgName;// 机构名称
	private String orgType;// 机构类型
	private String orgDescription;// 机构描述
	private Long orgSequence;// 排序

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getOrgDescription() {
		return orgDescription;
	}

	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}

	public Long getOrgSequence() {
		return orgSequence;
	}

	public void setOrgSequence(Long orgSequence) {
		this.orgSequence = orgSequence;
	}



//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		SysOrg item = (SysOrg) o;
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
//		return MoreObjects.toStringHelper(this).add("id", id).add("orgName", orgName).add("orgType", orgType).add("orgDescription", orgDescription)
//				.add("orgSequence", orgSequence).add("parentId", super.getParentId()).add("createTime", super.getCreateTime())
//				.toString();
//	}

}
