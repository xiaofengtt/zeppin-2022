package cn.product.worldmall.entity;

import java.io.Serializable;

public class GoodsType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1618672835733592052L;
	private String code;
	private String name;
	private Integer level;
	private String parent;
	private String icon;
	private String status;
	private Integer sort;
	
	public class GoodsTypeStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}