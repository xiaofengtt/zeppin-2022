package com.whaty.platform.sso.bean;

import com.whaty.platform.entity.bean.PeGrade;

/**
 * PrPriManagerGrade generated by MyEclipse Persistence Tools
 */

public class PrPriManagerGrade implements java.io.Serializable {

	// Fields

	private String id;

	private SsoUser ssoUser;

	private PeGrade peGrade;

	// Constructors

	/** default constructor */
	public PrPriManagerGrade() {
	}

	/** full constructor */
	public PrPriManagerGrade(SsoUser ssoUser, PeGrade peGrade) {
		this.ssoUser = ssoUser;
		this.peGrade = peGrade;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public PeGrade getPeGrade() {
		return this.peGrade;
	}

	public void setPeGrade(PeGrade peGrade) {
		this.peGrade = peGrade;
	}

}