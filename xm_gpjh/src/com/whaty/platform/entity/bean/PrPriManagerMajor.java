package com.whaty.platform.entity.bean;

/**
 * PrPriManagerMajor entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPriManagerMajor extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeMajor peMajor;
	private String fkSsoUserId;

	// Constructors

	/** default constructor */
	public PrPriManagerMajor() {
	}

	/** full constructor */
	public PrPriManagerMajor(PeMajor peMajor, String fkSsoUserId) {
		this.peMajor = peMajor;
		this.fkSsoUserId = fkSsoUserId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeMajor getPeMajor() {
		return this.peMajor;
	}

	public void setPeMajor(PeMajor peMajor) {
		this.peMajor = peMajor;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

}