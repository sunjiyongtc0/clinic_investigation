package com.clin.system.entity;


import com.clin.core.entity.CommonEntity;

import java.io.Serializable;


/**
 * <pre>
 * Title :  菜单信息的实体类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/
public class SysMenu extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 3142559115628496219L;
	private Long id;// ID
	private String menuCode;// 菜单编码
	private String menuName;// 菜单名称
	private String menuIcon;// 菜单图标
	private String menuPath;// 菜单路由
	private String menuComponent;// 菜单组件
	private Long menuSequence;// 菜单排序
	private Short menuStatus;// 菜单状态 0：隐藏 1：显示
	private String[] menuButton;// 菜单按钮

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getMenuComponent() {
		return menuComponent;
	}

	public void setMenuComponent(String menuComponent) {
		this.menuComponent = menuComponent;
	}

	public Long getMenuSequence() {
		return menuSequence;
	}

	public void setMenuSequence(Long menuSequence) {
		this.menuSequence = menuSequence;
	}

	public Short getMenuStatus() {
		return menuStatus;
	}

	public void setMenuStatus(Short menuStatus) {
		this.menuStatus = menuStatus;
	}

	public String[] getMenuButton() {
		return menuButton;
	}

	public void setMenuButton(String[] menuButton) {
		this.menuButton = menuButton;
	}



//	@Override
//	public boolean equals(Object o) {
//		if (this == o)
//			return true;
//		if (o == null || getClass() != o.getClass())
//			return false;
//		SysMenu item = (SysMenu) o;
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
//		return MoreObjects.toStringHelper(this).add("id", id).add("menuCode", menuCode).add("menuName", menuName).add("menuIcon", menuIcon)
//				.add("menuPath", menuPath).add("menuComponent", menuComponent).add("menuSequence", menuSequence).add("menuStatus", menuStatus)
//				.add("menuButton", menuButton).add("parentId", super.getParentId()).add("createTime", super.getCreateTime())
//				.toString();
//	}

}
