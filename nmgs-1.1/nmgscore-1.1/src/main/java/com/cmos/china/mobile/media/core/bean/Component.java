package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class Component extends Entity {

	private static final long serialVersionUID = 7682308667489287433L;
	private String id;
	private String name;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	
	public Component() {
		super();
	}

	public Component(String id, String name, String status, String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.creator = creator;
		this.createtime = createtime;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
}
