package com.whaty.platform.entity.bean;

/**
 * PrPriManagerGrade entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPriManagerGrade extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeGrade peGrade;
	private String fkSsoUserId;

	// Constructors

	/** default constructor */
	public PrPriManagerGrade() {
	}

	/** full constructor */
	public PrPriManagerGrade(PeGrade peGrade, String fkSsoUserId) {
		this.peGrade = peGrade;
		this.fkSsoUserId = fkSsoUserId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeGrade getPeGrade() {
		return this.peGrade;
	}

	public void setPeGrade(PeGrade peGrade) {
		this.peGrade = peGrade;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

}