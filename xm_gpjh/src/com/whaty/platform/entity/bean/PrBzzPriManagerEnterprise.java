package com.whaty.platform.entity.bean;

/**
 * PrBzzPriManagerEnterprise entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrBzzPriManagerEnterprise extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeEnterprise peEnterprise;
	private String fkSsoUserId;

	// Constructors

	/** default constructor */
	public PrBzzPriManagerEnterprise() {
	}

	/** full constructor */
	public PrBzzPriManagerEnterprise(PeEnterprise peEnterprise,
			String fkSsoUserId) {
		this.peEnterprise = peEnterprise;
		this.fkSsoUserId = fkSsoUserId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeEnterprise getPeEnterprise() {
		return this.peEnterprise;
	}

	public void setPeEnterprise(PeEnterprise peEnterprise) {
		this.peEnterprise = peEnterprise;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

}