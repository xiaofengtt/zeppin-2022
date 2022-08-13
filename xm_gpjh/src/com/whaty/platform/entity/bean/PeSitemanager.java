package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeSitemanager entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeSitemanager extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeSite peSite;
	private String loginId;
	private String name;
	private String trueName;
	private String code;
	private String fkSsoUserId;
	private String flagIsvalid;
	private String regionName;
	private String mobilePhone;
	private String phone;
	private String email;
	private String gender;
	private String idCard;
	private String graduationInfo;
	private Date graduationDate;
	private String address;
	private String zhiCheng;
	private String workTime;
	private String groupId;
	private String note;
	private Set peFeeBatchs = new HashSet(0);
	private Set peBulletins = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeSitemanager() {
	}

	/** minimal constructor */
	public PeSitemanager(String name, String trueName) {
		this.name = name;
		this.trueName = trueName;
	}

	/** full constructor */
	public PeSitemanager(PeSite peSite, String loginId, String name,
			String trueName, String code, String fkSsoUserId,
			String flagIsvalid, String regionName, String mobilePhone,
			String phone, String email, String gender, String idCard,
			String graduationInfo, Date graduationDate, String address,
			String zhiCheng, String workTime, String groupId, String note,
			Set peFeeBatchs, Set peBulletins) {
		this.peSite = peSite;
		this.loginId = loginId;
		this.name = name;
		this.trueName = trueName;
		this.code = code;
		this.fkSsoUserId = fkSsoUserId;
		this.flagIsvalid = flagIsvalid;
		this.regionName = regionName;
		this.mobilePhone = mobilePhone;
		this.phone = phone;
		this.email = email;
		this.gender = gender;
		this.idCard = idCard;
		this.graduationInfo = graduationInfo;
		this.graduationDate = graduationDate;
		this.address = address;
		this.zhiCheng = zhiCheng;
		this.workTime = workTime;
		this.groupId = groupId;
		this.note = note;
		this.peFeeBatchs = peFeeBatchs;
		this.peBulletins = peBulletins;
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

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFkSsoUserId() {
		return this.fkSsoUserId;
	}

	public void setFkSsoUserId(String fkSsoUserId) {
		this.fkSsoUserId = fkSsoUserId;
	}

	public String getFlagIsvalid() {
		return this.flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}

	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getGraduationInfo() {
		return this.graduationInfo;
	}

	public void setGraduationInfo(String graduationInfo) {
		this.graduationInfo = graduationInfo;
	}

	public Date getGraduationDate() {
		return this.graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZhiCheng() {
		return this.zhiCheng;
	}

	public void setZhiCheng(String zhiCheng) {
		this.zhiCheng = zhiCheng;
	}

	public String getWorkTime() {
		return this.workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPeFeeBatchs() {
		return this.peFeeBatchs;
	}

	public void setPeFeeBatchs(Set peFeeBatchs) {
		this.peFeeBatchs = peFeeBatchs;
	}

	public Set getPeBulletins() {
		return this.peBulletins;
	}

	public void setPeBulletins(Set peBulletins) {
		this.peBulletins = peBulletins;
	}

}