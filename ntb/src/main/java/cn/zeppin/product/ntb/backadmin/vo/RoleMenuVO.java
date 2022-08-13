package cn.zeppin.product.ntb.backadmin.vo;

import java.util.ArrayList;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.base.Entity;

public class RoleMenuVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String role;
	private String menu;
	private Integer sort;
	private String title;
	private Integer level;
	private String pid;
	private String icon;
	private List<RoleMenuVO> children = new ArrayList<>();
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getMenu() {
		return menu;
	}
	
	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public List<RoleMenuVO> getChildren() {
		return children;
	}
	
	public void setChildren(List<RoleMenuVO> children) {
		this.children = children;
	}
}
