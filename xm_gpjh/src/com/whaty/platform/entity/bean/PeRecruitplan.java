package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeRecruitplan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeRecruitplan extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeGrade peGrade;
	private String name;
	private String code;
	private String flagActive;
	private Date startDate;
	private Date registerStartDate;
	private Date registerEndDate;
	private Date examStartDate;
	private Date examEndDate;
	private Date endDate;
	private Date scoreStartDate;
	private Date scoreEndDate;
	private Date matriculateEndDate;
	private Date siteImportStartDate;
	private Date siteImportEndDate;
	private Date siteEditStartDate;
	private Date siteEditEndDate;
	private Date siteCheckStartDate;
	private Date siteCheckEndDate;
	private String note;
	private Set prRecExamCourseTimes = new HashSet(0);
	private Set prRecPlanMajorEdutypes = new HashSet(0);
	private Set peRecRooms = new HashSet(0);
	private Set prRecPatrolSettings = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeRecruitplan() {
	}

	/** minimal constructor */
	public PeRecruitplan(String name, String code, String flagActive) {
		this.name = name;
		this.code = code;
		this.flagActive = flagActive;
	}

	/** full constructor */
	public PeRecruitplan(PeGrade peGrade, String name, String code,
			String flagActive, Date startDate, Date registerStartDate,
			Date registerEndDate, Date examStartDate, Date examEndDate,
			Date endDate, Date scoreStartDate, Date scoreEndDate,
			Date matriculateEndDate, Date siteImportStartDate,
			Date siteImportEndDate, Date siteEditStartDate,
			Date siteEditEndDate, Date siteCheckStartDate,
			Date siteCheckEndDate, String note, Set prRecExamCourseTimes,
			Set prRecPlanMajorEdutypes, Set peRecRooms, Set prRecPatrolSettings) {
		this.peGrade = peGrade;
		this.name = name;
		this.code = code;
		this.flagActive = flagActive;
		this.startDate = startDate;
		this.registerStartDate = registerStartDate;
		this.registerEndDate = registerEndDate;
		this.examStartDate = examStartDate;
		this.examEndDate = examEndDate;
		this.endDate = endDate;
		this.scoreStartDate = scoreStartDate;
		this.scoreEndDate = scoreEndDate;
		this.matriculateEndDate = matriculateEndDate;
		this.siteImportStartDate = siteImportStartDate;
		this.siteImportEndDate = siteImportEndDate;
		this.siteEditStartDate = siteEditStartDate;
		this.siteEditEndDate = siteEditEndDate;
		this.siteCheckStartDate = siteCheckStartDate;
		this.siteCheckEndDate = siteCheckEndDate;
		this.note = note;
		this.prRecExamCourseTimes = prRecExamCourseTimes;
		this.prRecPlanMajorEdutypes = prRecPlanMajorEdutypes;
		this.peRecRooms = peRecRooms;
		this.prRecPatrolSettings = prRecPatrolSettings;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeGrade getPeGrade() {
		return this.peGrade;
	}

	public void setPeGrade(PeGrade peGrade) {
		this.peGrade = peGrade;
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

	public String getFlagActive() {
		return this.flagActive;
	}

	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getRegisterStartDate() {
		return this.registerStartDate;
	}

	public void setRegisterStartDate(Date registerStartDate) {
		this.registerStartDate = registerStartDate;
	}

	public Date getRegisterEndDate() {
		return this.registerEndDate;
	}

	public void setRegisterEndDate(Date registerEndDate) {
		this.registerEndDate = registerEndDate;
	}

	public Date getExamStartDate() {
		return this.examStartDate;
	}

	public void setExamStartDate(Date examStartDate) {
		this.examStartDate = examStartDate;
	}

	public Date getExamEndDate() {
		return this.examEndDate;
	}

	public void setExamEndDate(Date examEndDate) {
		this.examEndDate = examEndDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getScoreStartDate() {
		return this.scoreStartDate;
	}

	public void setScoreStartDate(Date scoreStartDate) {
		this.scoreStartDate = scoreStartDate;
	}

	public Date getScoreEndDate() {
		return this.scoreEndDate;
	}

	public void setScoreEndDate(Date scoreEndDate) {
		this.scoreEndDate = scoreEndDate;
	}

	public Date getMatriculateEndDate() {
		return this.matriculateEndDate;
	}

	public void setMatriculateEndDate(Date matriculateEndDate) {
		this.matriculateEndDate = matriculateEndDate;
	}

	public Date getSiteImportStartDate() {
		return this.siteImportStartDate;
	}

	public void setSiteImportStartDate(Date siteImportStartDate) {
		this.siteImportStartDate = siteImportStartDate;
	}

	public Date getSiteImportEndDate() {
		return this.siteImportEndDate;
	}

	public void setSiteImportEndDate(Date siteImportEndDate) {
		this.siteImportEndDate = siteImportEndDate;
	}

	public Date getSiteEditStartDate() {
		return this.siteEditStartDate;
	}

	public void setSiteEditStartDate(Date siteEditStartDate) {
		this.siteEditStartDate = siteEditStartDate;
	}

	public Date getSiteEditEndDate() {
		return this.siteEditEndDate;
	}

	public void setSiteEditEndDate(Date siteEditEndDate) {
		this.siteEditEndDate = siteEditEndDate;
	}

	public Date getSiteCheckStartDate() {
		return this.siteCheckStartDate;
	}

	public void setSiteCheckStartDate(Date siteCheckStartDate) {
		this.siteCheckStartDate = siteCheckStartDate;
	}

	public Date getSiteCheckEndDate() {
		return this.siteCheckEndDate;
	}

	public void setSiteCheckEndDate(Date siteCheckEndDate) {
		this.siteCheckEndDate = siteCheckEndDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrRecExamCourseTimes() {
		return this.prRecExamCourseTimes;
	}

	public void setPrRecExamCourseTimes(Set prRecExamCourseTimes) {
		this.prRecExamCourseTimes = prRecExamCourseTimes;
	}

	public Set getPrRecPlanMajorEdutypes() {
		return this.prRecPlanMajorEdutypes;
	}

	public void setPrRecPlanMajorEdutypes(Set prRecPlanMajorEdutypes) {
		this.prRecPlanMajorEdutypes = prRecPlanMajorEdutypes;
	}

	public Set getPeRecRooms() {
		return this.peRecRooms;
	}

	public void setPeRecRooms(Set peRecRooms) {
		this.peRecRooms = peRecRooms;
	}

	public Set getPrRecPatrolSettings() {
		return this.prRecPatrolSettings;
	}

	public void setPrRecPatrolSettings(Set prRecPatrolSettings) {
		this.prRecPatrolSettings = prRecPatrolSettings;
	}

}