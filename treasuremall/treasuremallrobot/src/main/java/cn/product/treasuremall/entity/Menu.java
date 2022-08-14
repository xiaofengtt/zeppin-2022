package cn.product.treasuremall.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class Menu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2232197667887454098L;
	
	@Id
	private String uuid;
	private String name;
	private String url;
	private Integer level;
	private String parent;
	private String icon;
	private Integer sort;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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