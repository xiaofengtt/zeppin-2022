package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PePcStudent entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PePcStudent extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String fkSsoUserId;
	private String trueName;
	private String regNo;
	private String major;
	private String class_;
	private String area;
	private String mobilephone;
	private Set prPcElectives = new HashSet(0);

	// Constructors

	/** default constructor */
	public PePcStudent() {
	}

	/** full constructor */
	public PePcStudent(String name, String fkSsoUserId, String trueName,
			String regNo, String major, String class_, String area,
			String mobilephone, Set prPcElectives) {
		this.name = name;
		this.fkSsoUserId = fkSsoUserId;
		this.trueName = trueName;
		this.regNo = regNo;
		this.major = major;
		this.class_ = class_;
		this.area = area;
		this.mobilephone = mobilephone;
		this.prPcElectives = prPcElectives;
	}

	// Property accessors

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

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getRegNo() {
		return this.regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getMobilephone() {
		return this.mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public Set getPrPcElectives() {
		return this.prPcElectives;
	}

	public void setPrPcElectives(Set prPcElectives) {
		this.prPcElectives = prPcElectives;
	}

}