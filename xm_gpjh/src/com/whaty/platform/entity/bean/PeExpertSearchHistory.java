package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeExpertSearchHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeExpertSearchHistory extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private PeTrainExpert peTrainExpert;
	private Date searchTime;

	// Constructors

	/** default constructor */
	public PeExpertSearchHistory() {
	}

	/** full constructor */
	public PeExpertSearchHistory(SsoUser ssoUser, PeTrainExpert peTrainExpert,
			Date searchTime) {
		this.ssoUser = ssoUser;
		this.peTrainExpert = peTrainExpert;
		this.searchTime = searchTime;
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

	public PeTrainExpert getPeTrainExpert() {
		return this.peTrainExpert;
	}

	public void setPeTrainExpert(PeTrainExpert peTrainExpert) {
		this.peTrainExpert = peTrainExpert;
	}

	public Date getSearchTime() {
		return this.searchTime;
	}

	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}

}