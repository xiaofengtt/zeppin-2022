package cn.product.score.vo.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoleMenuVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String role;
	private String menu;
	private String name;
	private Integer level;
	private String parent;
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

	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
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
