package com.whaty.platform.entity.bean;

/**
 * PrPriManagerSite entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrPriManagerSite extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeSite peSite;
	private String fkSsoUserId;

	// Constructors

	/** default constructor */
	public PrPriManagerSite() {
	}

	/** full constructor */
	public PrPriManagerSite(PeSite peSite, String fkSsoUserId) {
		this.peSite = peSite;
		this.fkSsoUserId = fkSsoUserId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSite getPeSite() {
		return this.peSite;
	}

	public void setPeSite(PeSite peSite) {
		this.peSite = peSite;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

}