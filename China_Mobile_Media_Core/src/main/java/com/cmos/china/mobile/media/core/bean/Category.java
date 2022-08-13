package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class Category extends Entity {

	private static final long serialVersionUID = 377829351023598212L;
	private String id;
	private String name;
	private Integer level;
	private String parent;
	private String status;
	private String scode;
	private String creator;
	private Timestamp createtime;
	
	
	public Category() {
		super();
	}

	public Category(String id, String name, Integer level, String parent,
			String status, String scode, String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.name = name;
		this.level = level;
		this.parent = parent;
		this.status = status;
		this.scode = scode;
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
	
	public Integer getLevel() {
		return this.level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public String getParent() {
		return this.parent;
	}
	
	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getScode() {
		return this.scode;
	}
	
	public void setScode(String scode) {
		this.scode = scode;
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
