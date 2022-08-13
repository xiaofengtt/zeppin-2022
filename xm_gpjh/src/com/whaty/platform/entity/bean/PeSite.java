package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeSite entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeSite extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeSite peSite;
	private String flagActive;
	private String flagBak;
	private String name;
	private String code;
	private String province;
	private String zipCode;
	private String city;
	private String line;
	private String sequence;
	private String address;
	private String manager;
	private String managerPhoneOffice;
	private String managerPhoneHome;
	private String managerMobilephone;
	private String recruitPhone;
	private String recruitFax;
	private Date foundDate;
	private Date lastNianshenDate;
	private String note;
	private Long scoreLine;
	private Set peSmsInfos = new HashSet(0);
	private Set prEduMajorSiteFeeLevels = new HashSet(0);
	private Set peExamMaincourseRooms = new HashSet(0);
	private Set prExamPatrolSettings = new HashSet(0);
	private Set peRecRooms = new HashSet(0);
	private Set peExamRooms = new HashSet(0);
	private Set peSites = new HashSet(0);
	private Set prPriManagerSites = new HashSet(0);
	private Set peExamPatrols = new HashSet(0);
	private Set peSitemanagers = new HashSet(0);
	private Set prStuChangeSitesForFkNewSiteId = new HashSet(0);
	private Set prRecPatrolSettings = new HashSet(0);
	private Set peStudents = new HashSet(0);
	private Set prRecPlanMajorSites = new HashSet(0);
	private Set peFeeBatchs = new HashSet(0);
	private Set prStuChangeSitesForFkOldSiteId = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeSite() {
	}

	/** minimal constructor */
	public PeSite(String name, String code) {
		this.name = name;
		this.code = code;
	}

	/** full constructor */
	public PeSite(PeSite peSite, String flagActive, String flagBak,
			String name, String code, String province, String zipCode,
			String city, String line, String sequence, String address,
			String manager, String managerPhoneOffice, String managerPhoneHome,
			String managerMobilephone, String recruitPhone, String recruitFax,
			Date foundDate, Date lastNianshenDate, String note, Long scoreLine,
			Set peSmsInfos, Set prEduMajorSiteFeeLevels,
			Set peExamMaincourseRooms, Set prExamPatrolSettings,
			Set peRecRooms, Set peExamRooms, Set peSites,
			Set prPriManagerSites, Set peExamPatrols, Set peSitemanagers,
			Set prStuChangeSitesForFkNewSiteId, Set prRecPatrolSettings,
			Set peStudents, Set prRecPlanMajorSites, Set peFeeBatchs,
			Set prStuChangeSitesForFkOldSiteId) {
		this.peSite = peSite;
		this.flagActive = flagActive;
		this.flagBak = flagBak;
		this.name = name;
		this.code = code;
		this.province = province;
		this.zipCode = zipCode;
		this.city = city;
		this.line = line;
		this.sequence = sequence;
		this.address = address;
		this.manager = manager;
		this.managerPhoneOffice = managerPhoneOffice;
		this.managerPhoneHome = managerPhoneHome;
		this.managerMobilephone = managerMobilephone;
		this.recruitPhone = recruitPhone;
		this.recruitFax = recruitFax;
		this.foundDate = foundDate;
		this.lastNianshenDate = lastNianshenDate;
		this.note = note;
		this.scoreLine = scoreLine;
		this.peSmsInfos = peSmsInfos;
		this.prEduMajorSiteFeeLevels = prEduMajorSiteFeeLevels;
		this.peExamMaincourseRooms = peExamMaincourseRooms;
		this.prExamPatrolSettings = prExamPatrolSettings;
		this.peRecRooms = peRecRooms;
		this.peExamRooms = peExamRooms;
		this.peSites = peSites;
		this.prPriManagerSites = prPriManagerSites;
		this.peExamPatrols = peExamPatrols;
		this.peSitemanagers = peSitemanagers;
		this.prStuChangeSitesForFkNewSiteId = prStuChangeSitesForFkNewSiteId;
		this.prRecPatrolSettings = prRecPatrolSettings;
		this.peStudents = peStudents;
		this.prRecPlanMajorSites = prRecPlanMajorSites;
		this.peFeeBatchs = peFeeBatchs;
		this.prStuChangeSitesForFkOldSiteId = prStuChangeSitesForFkOldSiteId;
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

	public String getFlagActive() {
		return this.flagActive;
	}

	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getSequence() {
		return this.sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManagerPhoneOffice() {
		return this.managerPhoneOffice;
	}

	public void setManagerPhoneOffice(String managerPhoneOffice) {
		this.managerPhoneOffice = managerPhoneOffice;
	}

	public String getManagerPhoneHome() {
		return this.managerPhoneHome;
	}

	public void setManagerPhoneHome(String managerPhoneHome) {
		this.managerPhoneHome = managerPhoneHome;
	}

	public String getManagerMobilephone() {
		return this.managerMobilephone;
	}

	public void setManagerMobilephone(String managerMobilephone) {
		this.managerMobilephone = managerMobilephone;
	}

	public String getRecruitPhone() {
		return this.recruitPhone;
	}

	public void setRecruitPhone(String recruitPhone) {
		this.recruitPhone = recruitPhone;
	}

	public String getRecruitFax() {
		return this.recruitFax;
	}

	public void setRecruitFax(String recruitFax) {
		this.recruitFax = recruitFax;
	}

	public Date getFoundDate() {
		return this.foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public Date getLastNianshenDate() {
		return this.lastNianshenDate;
	}

	public void setLastNianshenDate(Date lastNianshenDate) {
		this.lastNianshenDate = lastNianshenDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getScoreLine() {
		return this.scoreLine;
	}

	public void setScoreLine(Long scoreLine) {
		this.scoreLine = scoreLine;
	}

	public Set getPeSmsInfos() {
		return this.peSmsInfos;
	}

	public void setPeSmsInfos(Set peSmsInfos) {
		this.peSmsInfos = peSmsInfos;
	}

	public Set getPrEduMajorSiteFeeLevels() {
		return this.prEduMajorSiteFeeLevels;
	}

	public void setPrEduMajorSiteFeeLevels(Set prEduMajorSiteFeeLevels) {
		this.prEduMajorSiteFeeLevels = prEduMajorSiteFeeLevels;
	}

	public Set getPeExamMaincourseRooms() {
		return this.peExamMaincourseRooms;
	}

	public void setPeExamMaincourseRooms(Set peExamMaincourseRooms) {
		this.peExamMaincourseRooms = peExamMaincourseRooms;
	}

	public Set getPrExamPatrolSettings() {
		return this.prExamPatrolSettings;
	}

	public void setPrExamPatrolSettings(Set prExamPatrolSettings) {
		this.prExamPatrolSettings = prExamPatrolSettings;
	}

	public Set getPeRecRooms() {
		return this.peRecRooms;
	}

	public void setPeRecRooms(Set peRecRooms) {
		this.peRecRooms = peRecRooms;
	}

	public Set getPeExamRooms() {
		return this.peExamRooms;
	}

	public void setPeExamRooms(Set peExamRooms) {
		this.peExamRooms = peExamRooms;
	}

	public Set getPeSites() {
		return this.peSites;
	}

	public void setPeSites(Set peSites) {
		this.peSites = peSites;
	}

	public Set getPrPriManagerSites() {
		return this.prPriManagerSites;
	}

	public void setPrPriManagerSites(Set prPriManagerSites) {
		this.prPriManagerSites = prPriManagerSites;
	}

	public Set getPeExamPatrols() {
		return this.peExamPatrols;
	}

	public void setPeExamPatrols(Set peExamPatrols) {
		this.peExamPatrols = peExamPatrols;
	}

	public Set getPeSitemanagers() {
		return this.peSitemanagers;
	}

	public void setPeSitemanagers(Set peSitemanagers) {
		this.peSitemanagers = peSitemanagers;
	}

	public Set getPrStuChangeSitesForFkNewSiteId() {
		return this.prStuChangeSitesForFkNewSiteId;
	}

	public void setPrStuChangeSitesForFkNewSiteId(
			Set prStuChangeSitesForFkNewSiteId) {
		this.prStuChangeSitesForFkNewSiteId = prStuChangeSitesForFkNewSiteId;
	}

	public Set getPrRecPatrolSettings() {
		return this.prRecPatrolSettings;
	}

	public void setPrRecPatrolSettings(Set prRecPatrolSettings) {
		this.prRecPatrolSettings = prRecPatrolSettings;
	}

	public Set getPeStudents() {
		return this.peStudents;
	}

	public void setPeStudents(Set peStudents) {
		this.peStudents = peStudents;
	}

	public Set getPrRecPlanMajorSites() {
		return this.prRecPlanMajorSites;
	}

	public void setPrRecPlanMajorSites(Set prRecPlanMajorSites) {
		this.prRecPlanMajorSites = prRecPlanMajorSites;
	}

	public Set getPeFeeBatchs() {
		return this.peFeeBatchs;
	}

	public void setPeFeeBatchs(Set peFeeBatchs) {
		this.peFeeBatchs = peFeeBatchs;
	}

	public Set getPrStuChangeSitesForFkOldSiteId() {
		return this.prStuChangeSitesForFkOldSiteId;
	}

	public void setPrStuChangeSitesForFkOldSiteId(
			Set prStuChangeSitesForFkOldSiteId) {
		this.prStuChangeSitesForFkOldSiteId = prStuChangeSitesForFkOldSiteId;
	}

}