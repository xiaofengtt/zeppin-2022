package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class SsoUser extends Entity {
	
	private static final long serialVersionUID = 632546955732035286L;
	private String id;
	private String name;
	private String phone;
	private String status;
	private String token;
	private Timestamp freezeTime;
	private Timestamp createtime;
	
	public SsoUser() {
		super();
	}
	
	public SsoUser(String id, String name, String phone, String status,  String token,
			Timestamp freezeTime, Timestamp createtime) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.status = status;
		this.token = token;
		this.freezeTime = freezeTime;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Timestamp getFreezeTime() {
		return freezeTime;
	}
	
	public void setFreezeTime(Timestamp freezeTime) {
		this.freezeTime = freezeTime;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
