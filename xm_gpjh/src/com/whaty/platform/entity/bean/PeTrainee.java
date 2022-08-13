package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeTrainee entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTrainee extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private PeProApplyno peProApplyno;
	private EnumConst enumConstByFkGender;
	private PeSubject peSubject;
	private EnumConst enumConstByFkStatusTraining;
	private EnumConst enumConstByFkGraduted;
	private EnumConst enumConstByFkModifyChecked;
	private EnumConst enumConstByFkCheckedTrainee;
	private PeUnit peUnitByFkTrainingUnit;
	private PeUnit peUnitByFkUnitFrom;
	private PeProvince peProvince;
	private String loginId;
	private String name;
	private String telephone;
	private String email;
	private String officePhone;
	private Long age;
	private String note;
	private String volk;
	private String workPlace;
	private String educate;
	private String zhiwu;
	private String workyear;
	private String subject;
	private String province;
	private String zhicheng;
	private String notesecond;
	private String certificateNumber;
	private Date startDate;
	private Date endDate;

	private String yaoqiudefen;
	private String guochengdefen;
	private String chengxiaodefen;
	
	private City city;
	private String cityy;
	private County county;
	private String countyy;
	
	private UnitAttribute unitAttribute;
//	private JobTitle zhi;
	private MainTeachingGrade mainTeachingGrade;
	
	private MainTeachingSubject mainTeachingSubject;
	
	private Folk folk;
	
	private Education education;
	
	private JobTitle jobTitle;
	
	private String idcard;
	private String graduation;
	private String major;
	private Date hireDate;

	private String yijian;

	// Constructors

	/** default constructor */
	public PeTrainee() {
	}

	/** full constructor */
	public PeTrainee(SsoUser ssoUser, PeProApplyno peProApplyno, EnumConst enumConstByFkGender, PeSubject peSubject,
			EnumConst enumConstByFkStatusTraining, EnumConst enumConstByFkGraduted, EnumConst enumConstByFkModifyChecked,
			EnumConst enumConstByFkCheckedTrainee, PeUnit peUnitByFkTrainingUnit, PeUnit peUnitByFkUnitFrom, PeProvince peProvince, String loginId,
			String name, String telephone, String email, String officePhone, Long age, String note, String volk, String workPlace, String educate,
			String zhiwu, String workyear, String subject, String province, String zhicheng, String notesecond, String certificateNumber,
			Date startDate, Date endDate, String yaoqiudefen, String guochengdefen, String chengxiaodefen, String yijian, City city, County county, UnitAttribute unitAttribute,
			MainTeachingGrade mainTeachingGrade, MainTeachingSubject mainTeachingSubject, String idcard, String graduation, String major,Date hireDate,Folk folk,Education education,
			String cityy,String countyy,JobTitle jobTitle) {
		this.ssoUser = ssoUser;
		this.peProApplyno = peProApplyno;
		this.enumConstByFkGender = enumConstByFkGender;
		this.peSubject = peSubject;
		this.enumConstByFkStatusTraining = enumConstByFkStatusTraining;
		this.enumConstByFkGraduted = enumConstByFkGraduted;
		this.enumConstByFkModifyChecked = enumConstByFkModifyChecked;
		this.enumConstByFkCheckedTrainee = enumConstByFkCheckedTrainee;
		this.peUnitByFkTrainingUnit = peUnitByFkTrainingUnit;
		this.peUnitByFkUnitFrom = peUnitByFkUnitFrom;
		this.peProvince = peProvince;
		this.loginId = loginId;
		this.name = name;
		this.telephone = telephone;
		this.email = email;
		this.officePhone = officePhone;
		this.age = age;
		this.note = note;
		this.folk = folk;
		this.workPlace = workPlace;
		this.education = education;
		this.zhiwu = zhiwu;
		this.workyear = workyear;
		this.subject = subject;
		this.province = province;
		this.zhicheng = zhicheng;
		this.notesecond = notesecond;
		this.certificateNumber = certificateNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.yaoqiudefen = yaoqiudefen;
		this.guochengdefen = guochengdefen;
		this.chengxiaodefen = chengxiaodefen;
		this.yijian = yijian;
		this.city = city;
		this.county = county;
		this.unitAttribute = unitAttribute;
		this.mainTeachingGrade = mainTeachingGrade;
		this.mainTeachingSubject = mainTeachingSubject;
		this.idcard = idcard;
		this.graduation = graduation;
		this.major = major;
		this.hireDate = hireDate;
		this.cityy = cityy;
		this.countyy = countyy;
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

	public PeProApplyno getPeProApplyno() {
		return this.peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
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

	public EnumConst getEnumConstByFkStatusTraining() {
		return this.enumConstByFkStatusTraining;
	}

	public void setEnumConstByFkStatusTraining(EnumConst enumConstByFkStatusTraining) {
		this.enumConstByFkStatusTraining = enumConstByFkStatusTraining;
	}

	public EnumConst getEnumConstByFkGraduted() {
		return this.enumConstByFkGraduted;
	}

	public void setEnumConstByFkGraduted(EnumConst enumConstByFkGraduted) {
		this.enumConstByFkGraduted = enumConstByFkGraduted;
	}

	public EnumConst getEnumConstByFkModifyChecked() {
		return this.enumConstByFkModifyChecked;
	}

	public void setEnumConstByFkModifyChecked(EnumConst enumConstByFkModifyChecked) {
		this.enumConstByFkModifyChecked = enumConstByFkModifyChecked;
	}

	public EnumConst getEnumConstByFkCheckedTrainee() {
		return this.enumConstByFkCheckedTrainee;
	}

	public void setEnumConstByFkCheckedTrainee(EnumConst enumConstByFkCheckedTrainee) {
		this.enumConstByFkCheckedTrainee = enumConstByFkCheckedTrainee;
	}

	public PeUnit getPeUnitByFkTrainingUnit() {
		return this.peUnitByFkTrainingUnit;
	}

	public void setPeUnitByFkTrainingUnit(PeUnit peUnitByFkTrainingUnit) {
		this.peUnitByFkTrainingUnit = peUnitByFkTrainingUnit;
	}

	public PeUnit getPeUnitByFkUnitFrom() {
		return this.peUnitByFkUnitFrom;
	}

	public void setPeUnitByFkUnitFrom(PeUnit peUnitByFkUnitFrom) {
		this.peUnitByFkUnitFrom = peUnitByFkUnitFrom;
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

	public Long getAge() {
		return this.age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public String getWorkPlace() {
		return this.workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getZhiwu() {
		return this.zhiwu;
	}

	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

	public String getWorkyear() {
		return this.workyear;
	}

	public void setWorkyear(String workyear) {
		this.workyear = workyear;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZhicheng() {
		return this.zhicheng;
	}

	public void setZhicheng(String zhicheng) {
		this.zhicheng = zhicheng;
	}

	public String getNotesecond() {
		return this.notesecond;
	}

	public void setNotesecond(String notesecond) {
		this.notesecond = notesecond;
	}

	public String getCertificateNumber() {
		return this.certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getYaoqiudefen() {
		return yaoqiudefen;
	}

	public void setYaoqiudefen(String yaoqiudefen) {
		this.yaoqiudefen = yaoqiudefen;
	}

	public String getGuochengdefen() {
		return guochengdefen;
	}

	public void setGuochengdefen(String guochengdefen) {
		this.guochengdefen = guochengdefen;
	}

	public String getChengxiaodefen() {
		return chengxiaodefen;
	}

	public void setChengxiaodefen(String chengxiaodefen) {
		this.chengxiaodefen = chengxiaodefen;
	}

	public String getYijian() {
		return yijian;
	}

	public void setYijian(String yijian) {
		this.yijian = yijian;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	
	public UnitAttribute getUnitAttribute() {
		return unitAttribute;
	}
	

	public void setUnitAttribute(UnitAttribute unitAttribute) {
		this.unitAttribute = unitAttribute;
	}
	

	public MainTeachingGrade getMainTeachingGrade() {
		return mainTeachingGrade;
	}
	

	public void setMainTeachingGrade(MainTeachingGrade mainTeachingGrade) {
		this.mainTeachingGrade = mainTeachingGrade;
	}
	

	public MainTeachingSubject getMainTeachingSubject() {
		return mainTeachingSubject;
	}
	

	public void setMainTeachingSubject(MainTeachingSubject mainTeachingSubject) {
		this.mainTeachingSubject = mainTeachingSubject;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getGraduation() {
		return graduation;
	}

	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}


	public JobTitle getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(JobTitle jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getVolk() {
		return volk;
	}

	public void setVolk(String volk) {
		this.volk = volk;
	}

	public Folk getFolk() {
		return folk;
	}

	public void setFolk(Folk folk) {
		this.folk = folk;
	}

	public String getEducate() {
		return educate;
	}

	public void setEducate(String educate) {
		this.educate = educate;
	}

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public String getCityy() {
		return cityy;
	}

	public void setCityy(String cityy) {
		this.cityy = cityy;
	}

	public String getCountyy() {
		return countyy;
	}

	public void setCountyy(String countyy) {
		this.countyy = countyy;
	}

	
	
}