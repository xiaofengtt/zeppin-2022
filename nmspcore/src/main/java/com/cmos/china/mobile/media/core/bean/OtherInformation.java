package com.cmos.china.mobile.media.core.bean;


public class OtherInformation extends Entity {
	
	private static final long serialVersionUID = 6210686135281807355L;
	private Integer id;
	private String password;
	private String name;
	private String status;
	
	public OtherInformation() {
		super();
	}

	public OtherInformation(Integer id, String password, String name, String status) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
