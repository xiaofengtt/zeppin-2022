package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class SsoUserLogin extends Entity {
	
	private static final long serialVersionUID = 632546955732035286L;
	private String id;
	private String user;
	private String ip;
	private String type;
	private String result;
	private Timestamp createtime;
	
	public SsoUserLogin() {
		super();
	}
	
	public SsoUserLogin(String id, String user, String ip, String type, String result, Timestamp createtime) {
		super();
		this.id = id;
		this.user = user;
		this.ip = ip;
		this.type = type;
		this.result = result;
		this.createtime = createtime;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
