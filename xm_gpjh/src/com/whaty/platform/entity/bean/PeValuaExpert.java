package com.whaty.platform.entity.bean;

import java.sql.Clob;
import java.util.Date;

/**
 * PeValuaExpert entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeValuaExpert extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private EnumConst enumConstByFkGender;
	private EnumConst enumConstByFkStatusValuate;
	private String name;
	private String telephone;
	private String email;
	private String officePhone;
	private String age;
	private String note;
	private String folk;
	private String birthyearmonth;
	private String education;
	private String major;
	private String politics;
	private String zhiwu;
	private String zhicheng;
	private String researchArea;
	private String idcard;
	private String trainingArea;
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
	private Date inputDate;
	private String loginId;

	// Constructors

	/** default constructor */
	public PeValuaExpert() {
	}

	/** full constructor */
	public PeValuaExpert(SsoUser ssoUser, EnumConst enumConstByFkGender,
			EnumConst enumConstByFkStatus, String name, String telephone,
			String email, String officePhone, String age, String note, String folk,
			String birthyearmonth, String education, String major,
			String politics, String zhiwu, String zhicheng,
			String researchArea, String idcard, String trainingArea,
			String workplace, String address, String zip, String homePhone,
			String fax, String personalResume, String trainingResult,
			String trainingExperience, String otherItems, String unitComment,
			String recommendComment, String finalComment, Date inputDate) {
		this.ssoUser = ssoUser;
		this.enumConstByFkGender = enumConstByFkGender;
		this.enumConstByFkStatusValuate = enumConstByFkStatus;
		this.name = name;
		this.telephone = telephone;
		this.email = email;
		this.officePhone = officePhone;
		this.age = age;
		this.note = note;
		this.folk = folk;
		this.birthyearmonth = birthyearmonth;
		this.education = education;
		this.major = major;
		this.politics = politics;
		this.zhiwu = zhiwu;
		this.zhicheng = zhicheng;
		this.researchArea = researchArea;
		this.idcard = idcard;
		this.trainingArea = trainingArea;
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
		this.inputDate = inputDate;
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

	public EnumConst getEnumConstByFkStatusValuate() {
		return this.enumConstByFkStatusValuate;
	}

	public void setEnumConstByFkStatusValuate(EnumConst enumConstByFkStatusValuate) {
		this.enumConstByFkStatusValuate = enumConstByFkStatusValuate;
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

	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getBirthyearmonth() {
		return this.birthyearmonth;
	}

	public void setBirthyearmonth(String birthyearmonth) {
		this.birthyearmonth = birthyearmonth;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
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

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getTrainingArea() {
		return this.trainingArea;
	}

	public void setTrainingArea(String trainingArea) {
		this.trainingArea = trainingArea;
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
		return personalResume;
	}

	public void setPersonalResume(String personalResume) {
		this.personalResume = personalResume;
	}

	public String getTrainingResult() {
		return trainingResult;
	}

	public void setTrainingResult(String trainingResult) {
		this.trainingResult = trainingResult;
	}

	public String getTrainingExperience() {
		return trainingExperience;
	}

	public void setTrainingExperience(String trainingExperience) {
		this.trainingExperience = trainingExperience;
	}

	public String getOtherItems() {
		return otherItems;
	}

	public void setOtherItems(String otherItems) {
		this.otherItems = otherItems;
	}

	public String getUnitComment() {
		return unitComment;
	}

	public void setUnitComment(String unitComment) {
		this.unitComment = unitComment;
	}

	public String getRecommendComment() {
		return recommendComment;
	}

	public void setRecommendComment(String recommendComment) {
		this.recommendComment = recommendComment;
	}

	public String getFinalComment() {
		return finalComment;
	}

	public void setFinalComment(String finalComment) {
		this.finalComment = finalComment;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

}