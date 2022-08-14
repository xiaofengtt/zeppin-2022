package com.cmos.china.mobile.media.core.bean;

public class Module extends Entity {
	
	private static final long serialVersionUID = 5703096467321125198L;
	private String id;
	private String name;
	private String template;
	private Integer count;
	private String status;
	private Integer sequence;
	
	public Module() {
		super();
	}

	public Module(String id, String name, String template, Integer count, 
			String status, Integer sequence) {
		super();
		this.id = id;
		this.name = name;
		this.template = template;
		this.count = count;
		this.status = status;
		this.sequence = sequence;
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
	
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
