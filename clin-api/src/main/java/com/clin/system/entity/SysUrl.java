package com.clin.system.entity;


import com.clin.core.entity.TimeEntity;

import java.io.Serializable;

/**
 * <pre>
 * Title :  接口信息的实体类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysUrl extends TimeEntity implements Serializable {

	private static final long serialVersionUID = 5556454535499431912L;
	private Long id;// ID
	private String url;// URL
	private String description;// 描述

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		SysUrl item = (SysUrl) o;
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
//		return MoreObjects.toStringHelper(this).add("id", id).add("url", url).add("description", description)
//				.add("createTime", super.getCreateTime()).toString();
//	}

}
