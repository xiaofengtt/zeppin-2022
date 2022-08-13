package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;

public class ComponentVO implements Serializable{
	
	private static final long serialVersionUID = -8436477244441442968L;
	private String id;
	private String name;
	private String status;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
