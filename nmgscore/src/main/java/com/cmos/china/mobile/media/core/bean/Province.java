package com.cmos.china.mobile.media.core.bean;

public class Province extends Entity {
	
	private static final long serialVersionUID = -7830986319810140113L;
	private String id;
	private String name;
	private String template;
	private String status;
	
	public Province() {
		super();
	}

	public Province(String id, String name, String template, String status) {
		super();
		this.id = id;
		this.name = name;
		this.template = template;
		this.status = status;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
