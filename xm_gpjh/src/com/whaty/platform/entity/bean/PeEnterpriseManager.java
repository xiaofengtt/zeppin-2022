package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeEnterpriseManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeEnterpriseManager extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeEnterprise peEnterprise;
	private String loginId;
	private String name;
	private String gender;
	private String position;
	private String phone;
	private String mobilePhone;
	private String email;
	private String bz;
	private String flagIsvalid;
	private String fkSsoUserId;
	private String confirmManagerId;
	private Date confirmDate;
	private Set peBulletins = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeEnterpriseManager() {
	}

	/** full constructor */
	public PeEnterpriseManager(PeEnterprise peEnterprise, String loginId,
			String name, String gender, String position, String phone,
			String mobilePhone, String email, String bz, String flagIsvalid,
			String fkSsoUserId, String confirmManagerId, Date confirmDate,
			Set peBulletins) {
		this.peEnterprise = peEnterprise;
		this.loginId = loginId;
		this.name = name;
		this.gender = gender;
		this.position = position;
		this.phone = phone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.bz = bz;
		this.flagIsvalid = flagIsvalid;
		this.fkSsoUserId = fkSsoUserId;
		this.confirmManagerId = confirmManagerId;
		this.confirmDate = confirmDate;
		this.peBulletins = peBulletins;
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

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getFlagIsvalid() {
		return this.flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

	public String getConfirmManagerId() {
		return this.confirmManagerId;
	}

	public void setConfirmManagerId(String confirmManagerId) {
		this.confirmManagerId = confirmManagerId;
	}

	public Date getConfirmDate() {
		return this.confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Set getPeBulletins() {
		return this.peBulletins;
	}

	public void setPeBulletins(Set peBulletins) {
		this.peBulletins = peBulletins;
	}

}