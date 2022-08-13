package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class User extends Entity {

	private static final long serialVersionUID = 1058199251149604622L;
	private String id;
	private String username;
	private String role;
	private String status;
	private String password;
	private String phone;
	private String email;
	private String department;
	private String jobtitle;
	private String creator;
	private Timestamp createtime;
	
	public User() {
		super();
	}
	
	public User(String id, String username, String role, String status, String password, String phone, 
			String email, String department, String jobtitle, String creator, Timestamp createtime) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
		this.status = status;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.department = department;
		this.jobtitle = jobtitle;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
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
