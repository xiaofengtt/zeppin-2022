package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeManager entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeManager extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private EnumConst enumConstByFkGender;
	private PeUnit peUnit;
	private EnumConst enumConstByFlagIsvalid;
	private EnumConst enumConstByFlagProperty;
	private EnumConst enumConstByFkStatus;
	private String name;
	private String loginId;
	private String department;
	private String telephone;
	private String email;
	private String officePhone;
	private String fax;
	private Date birthday;
	private String zhiwuzhicheng;
	private String address;
	private String zip;
	private String folk;
	private String politics;
	private String education;
	private String note;
	private Set peBulletins = new HashSet(0);
	private Set peMeetingResources = new HashSet(0);
	private Set peMeetings = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeManager() {
	}

	/** full constructor */
	public PeManager(SsoUser ssoUser, EnumConst enumConstByFkGender,
			PeUnit peUnit, EnumConst enumConstByFlagIsvalid,
			EnumConst enumConstByFlagProperty, EnumConst enumConstByFkStatus,
			String name, String loginId, String department, String telephone,
			String email, String officePhone, String fax, Date birthday,
			String zhiwuzhicheng, String address, String zip, String folk,
			String politics, String education, String note, Set peBulletins,
			Set peMeetingResources, Set peMeetings) {
		this.ssoUser = ssoUser;
		this.enumConstByFkGender = enumConstByFkGender;
		this.peUnit = peUnit;
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
		this.enumConstByFlagProperty = enumConstByFlagProperty;
		this.enumConstByFkStatus = enumConstByFkStatus;
		this.name = name;
		this.loginId = loginId;
		this.department = department;
		this.telephone = telephone;
		this.email = email;
		this.officePhone = officePhone;
		this.fax = fax;
		this.birthday = birthday;
		this.zhiwuzhicheng = zhiwuzhicheng;
		this.address = address;
		this.zip = zip;
		this.folk = folk;
		this.politics = politics;
		this.education = education;
		this.note = note;
		this.peBulletins = peBulletins;
		this.peMeetingResources = peMeetingResources;
		this.peMeetings = peMeetings;
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

	public EnumConst getEnumConstByFkGender() {
		return this.enumConstByFkGender;
	}

	public void setEnumConstByFkGender(EnumConst enumConstByFkGender) {
		this.enumConstByFkGender = enumConstByFkGender;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public EnumConst getEnumConstByFlagIsvalid() {
		return this.enumConstByFlagIsvalid;
	}

	public void setEnumConstByFlagIsvalid(EnumConst enumConstByFlagIsvalid) {
		this.enumConstByFlagIsvalid = enumConstByFlagIsvalid;
	}

	public EnumConst getEnumConstByFlagProperty() {
		return this.enumConstByFlagProperty;
	}

	public void setEnumConstByFlagProperty(EnumConst enumConstByFlagProperty) {
		this.enumConstByFlagProperty = enumConstByFlagProperty;
	}

	public EnumConst getEnumConstByFkStatus() {
		return this.enumConstByFkStatus;
	}

	public void setEnumConstByFkStatus(EnumConst enumConstByFkStatus) {
		this.enumConstByFkStatus = enumConstByFkStatus;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getZhiwuzhicheng() {
		return this.zhiwuzhicheng;
	}

	public void setZhiwuzhicheng(String zhiwuzhicheng) {
		this.zhiwuzhicheng = zhiwuzhicheng;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getPolitics() {
		return this.politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeBulletins() {
		return this.peBulletins;
	}

	public void setPeBulletins(Set peBulletins) {
		this.peBulletins = peBulletins;
	}

	public Set getPeMeetingResources() {
		return this.peMeetingResources;
	}

	public void setPeMeetingResources(Set peMeetingResources) {
		this.peMeetingResources = peMeetingResources;
	}

	public Set getPeMeetings() {
		return this.peMeetings;
	}

	public void setPeMeetings(Set peMeetings) {
		this.peMeetings = peMeetings;
	}

}