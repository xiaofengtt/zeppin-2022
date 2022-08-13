package com.cmos.chinamobile.media.web.vo.iface;

import java.util.List;

public class WebCategoryVO {


	private String id;
	private String name;
	private List<WebCategoryVO> child;
	
	
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

	
}
