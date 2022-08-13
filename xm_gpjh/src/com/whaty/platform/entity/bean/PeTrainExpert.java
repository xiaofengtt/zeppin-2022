package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeTrainExpert entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTrainExpert extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private EnumConst enumConstByFkGender;
	private PeSubject peSubject;
	private PeUnit peUnit;
	private EnumConst enumConstByFkStatus;
	private PeProvince peProvince;
	private String loginId;
	private String name;
	private String telephone;
	private String email;
	private String officePhone;
	private String age;
	private String note;
	private String education;
	private String politics;
	private String zhiwu;
	private String zhicheng;
	private String researchArea;
	private String trainingArea;
	private String idcard;
	private String workplace;
	private String address;
	private String zip;
	private String homePhone;
	private String fax;
	private String personalResume;
	private String trainingResult;
	private String trainingExperience;
	private String otherItems;
	private String unitComment;
	private String recommendComment;
	private String finalComment;
	private String folk;
	private Date birthdate;
	private Date recommendDate;
	private String major;
	private String workplaceP;
	private Date inputDate;
	private String searchCount;

	// Constructors

	/** default constructor */
	public PeTrainExpert() {
	}

	/** minimal constructor */
	public PeTrainExpert(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeTrainExpert(SsoUser ssoUser, EnumConst enumConstByFkGender,
			PeSubject peSubject, PeUnit peUnit, EnumConst enumConstByFkStatus,
			PeProvince peProvince, String loginId, String name,
			String telephone, String email, String officePhone, String age,
			String note, String education, String politics, String zhiwu,
			String zhicheng, String researchArea, String trainingArea,
			String idcard, String workplace, String address, String zip,
			String homePhone, String fax, String personalResume,
			String trainingResult, String trainingExperience,
			String otherItems, String unitComment, String recommendComment,
			String finalComment, String folk, Date birthdate,
			Date recommendDate, String major, String workplaceP, Date inputDate,String searchCount) {
		this.ssoUser = ssoUser;
		this.enumConstByFkGender = enumConstByFkGender;
		this.peSubject = peSubject;
		this.peUnit = peUnit;
		this.enumConstByFkStatus = enumConstByFkStatus;
		this.peProvince = peProvince;
		this.loginId = loginId;
		this.name = name;
		this.telephone = telephone;
		this.email = email;
		this.officePhone = officePhone;
		this.age = age;
		this.note = note;
		this.education = education;
		this.politics = politics;
		this.zhiwu = zhiwu;
		this.zhicheng = zhicheng;
		this.researchArea = researchArea;
		this.trainingArea = trainingArea;
		this.idcard = idcard;
		this.workplace = workplace;
		this.address = address;
		this.zip = zip;
		this.homePhone = homePhone;
		this.fax = fax;
		this.personalResume = personalResume;
		this.trainingResult = trainingResult;
		this.trainingExperience = trainingExperience;
		this.otherItems = otherItems;
		this.unitComment = unitComment;
		this.recommendComment = recommendComment;
		this.finalComment = finalComment;
		this.folk = folk;
		this.birthdate = birthdate;
		this.recommendDate = recommendDate;
		this.major = major;
		this.workplaceP = workplaceP;
		this.inputDate = inputDate;
		this.searchCount=searchCount;
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

	public PeSubject getPeSubject() {
		return this.peSubject;
	}

	public void setPeSubject(PeSubject peSubject) {
		this.peSubject = peSubject;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public EnumConst getEnumConstByFkStatus() {
		return this.enumConstByFkStatus;
	}

	public void setEnumConstByFkStatus(EnumConst enumConstByFkStatus) {
		this.enumConstByFkStatus = enumConstByFkStatus;
	}

	public PeProvince getPeProvince() {
		return this.peProvince;
	}

	public void setPeProvince(PeProvince peProvince) {
		this.peProvince = peProvince;
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

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPolitics() {
		return this.politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	public String getZhiwu() {
		return this.zhiwu;
	}

	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

	public String getZhicheng() {
		return this.zhicheng;
	}

	public void setZhicheng(String zhicheng) {
		this.zhicheng = zhicheng;
	}

	public String getResearchArea() {
		return this.researchArea;
	}

	public void setResearchArea(String researchArea) {
		this.researchArea = researchArea;
	}

	public String getTrainingArea() {
		return this.trainingArea;
	}

	public void setTrainingArea(String trainingArea) {
		this.trainingArea = trainingArea;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getWorkplace() {
		return this.workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
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

	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPersonalResume() {
		return this.personalResume;
	}

	public void setPersonalResume(String personalResume) {
		this.personalResume = personalResume;
	}

	public String getTrainingResult() {
		return this.trainingResult;
	}

	public void setTrainingResult(String trainingResult) {
		this.trainingResult = trainingResult;
	}

	public String getTrainingExperience() {
		return this.trainingExperience;
	}

	public void setTrainingExperience(String trainingExperience) {
		this.trainingExperience = trainingExperience;
	}

	public String getOtherItems() {
		return this.otherItems;
	}

	public void setOtherItems(String otherItems) {
		this.otherItems = otherItems;
	}

	public String getUnitComment() {
		return this.unitComment;
	}

	public void setUnitComment(String unitComment) {
		this.unitComment = unitComment;
	}

	public String getRecommendComment() {
		return this.recommendComment;
	}

	public void setRecommendComment(String recommendComment) {
		this.recommendComment = recommendComment;
	}

	public String getFinalComment() {
		return this.finalComment;
	}

	public void setFinalComment(String finalComment) {
		this.finalComment = finalComment;
	}

	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getRecommendDate() {
		return this.recommendDate;
	}

	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getWorkplaceP() {
		return this.workplaceP;
	}

	public void setWorkplaceP(String workplaceP) {
		this.workplaceP = workplaceP;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(String searchCount) {
		this.searchCount = searchCount;
	}

}