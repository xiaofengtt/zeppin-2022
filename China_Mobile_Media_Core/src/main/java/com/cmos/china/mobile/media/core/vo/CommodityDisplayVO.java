package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;

public class CommodityDisplayVO implements Serializable{
	
	private static final long serialVersionUID = -6712641681298290231L;
	private String id;
	private String name;
	private Integer displayIndex;
	
	
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
	public Integer getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(Integer displayIndex) {
		this.displayIndex = displayIndex;
	}
	
	

}
