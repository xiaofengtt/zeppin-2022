package com.whaty.platform.entity.bean;

/**
 * PrSmsSendStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrSmsSendStatus extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeSmsInfo peSmsInfo;
	private String mobilePhone;
	private String flagSendStatus;
	private String flagBak;

	// Constructors

	/** default constructor */
	public PrSmsSendStatus() {
	}

	/** full constructor */
	public PrSmsSendStatus(PeSmsInfo peSmsInfo, String mobilePhone,
			String flagSendStatus, String flagBak) {
		this.peSmsInfo = peSmsInfo;
		this.mobilePhone = mobilePhone;
		this.flagSendStatus = flagSendStatus;
		this.flagBak = flagBak;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSmsInfo getPeSmsInfo() {
		return this.peSmsInfo;
	}

	public void setPeSmsInfo(PeSmsInfo peSmsInfo) {
		this.peSmsInfo = peSmsInfo;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFlagSendStatus() {
		return this.flagSendStatus;
	}

	public void setFlagSendStatus(String flagSendStatus) {
		this.flagSendStatus = flagSendStatus;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

}