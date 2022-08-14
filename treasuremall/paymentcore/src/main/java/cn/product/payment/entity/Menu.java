package cn.product.payment.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class Menu implements Serializable{
	
	/**
	 * 左菜单
	 */
	private static final long serialVersionUID = 8714545187106316990L;
	
	@Id
	private String uuid;
	private String type;
	private String name;
	private String url;
	private Integer level;
	private String parent;
	private String icon;
	private Integer sort;
	
	public class MenuType{
		public final static String SYSTEM = "system";
		public final static String STORE = "store";
    }
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}