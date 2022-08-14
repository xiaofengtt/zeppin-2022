package com.cmos.china.mobile.media.core.vo.iface;

import java.util.List;

public class WebCategoryVO {

	private String id;
	private String name;
	private List<WebCategoryVO> child;
	private String columnType;
	private String displayStyle;
	private Integer sequence;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<WebCategoryVO> getChild() {
		return child;
	}
	public void setChild(List<WebCategoryVO> child) {
		this.child = child;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getDisplayStyle() {
		return displayStyle;
	}
	public void setDisplayStyle(String displayStyle) {
		this.displayStyle = displayStyle;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
}
