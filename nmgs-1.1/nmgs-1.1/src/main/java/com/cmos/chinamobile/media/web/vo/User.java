package com.cmos.chinamobile.media.web.vo;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = -3194423285891093633L;
	private String id;
	private String username;
	private String role;
	
	public User() {
		super();
	}
	
	public User(String id, String username, String role) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.username;
	}
	
	public void setName(String username) {
		this.username = username;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
