package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeSemester entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeSemester extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Long serialNumber;
	private String flagActive;
	private String flagNextActive;
	private Date startDate;
	private Date endDate;
	private Date examBookingStartDate;
	private Date examBookingEndDate;
	private Date finalExamStartDate;
	private Date finalExamEndDate;
	private Date finalExamScoreDate;
	private Date graduateStartDate;
	private Date graduateEndDate;
	private Date electiveFreshStartDate;
	private Date electiveFreshEndDate;
	private Date electiveStartDate;
	private Date electiveEndDate;
	private Date cancelElectiveStartDate;
	private Date cancelElectiveEndDate;
	private Date freshmanElectiveStartDate;
	private Date freshmanElectiveEndDate;
	private Date paperStartDate;
	private Date paperEndDate;
	private Date paperTitleEndDate;
	private Date paperSyllabusEndDate;
	private Date paperDraftEndDate;
	private Date paperFinalEndDate;
	private Date paperRejoinEndDate;
	private Date scoreUsualStartDate;
	private Date scoreUsualEndDate;
	private Date mainCourseStartDate;
	private Date mainCourseEndDate;
	private Set prExamPatrolSettings = new HashSet(0);
	private Set peTchRejoinRooms = new HashSet(0);
	private Set prExamBookings = new HashSet(0);
	private Set prTchOpencourses = new HashSet(0);
	private Set peTchRejoinSections = new HashSet(0);
	private Set peExamMaincourseNos = new HashSet(0);
	private Set prTchPaperTitles = new HashSet(0);
	private Set peExamNos = new HashSet(0);
	private Set prPcOpencourses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeSemester() {
	}

	/** minimal constructor */
	public PeSemester(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeSemester(String name, Long serialNumber, String flagActive,
			String flagNextActive, Date startDate, Date endDate,
			Date examBookingStartDate, Date examBookingEndDate,
			Date finalExamStartDate, Date finalExamEndDate,
			Date finalExamScoreDate, Date graduateStartDate,
			Date graduateEndDate, Date electiveFreshStartDate,
			Date electiveFreshEndDate, Date electiveStartDate,
			Date electiveEndDate, Date cancelElectiveStartDate,
			Date cancelElectiveEndDate, Date freshmanElectiveStartDate,
			Date freshmanElectiveEndDate, Date paperStartDate,
			Date paperEndDate, Date paperTitleEndDate,
			Date paperSyllabusEndDate, Date paperDraftEndDate,
			Date paperFinalEndDate, Date paperRejoinEndDate,
			Date scoreUsualStartDate, Date scoreUsualEndDate,
			Date mainCourseStartDate, Date mainCourseEndDate,
			Set prExamPatrolSettings, Set peTchRejoinRooms, Set prExamBookings,
			Set prTchOpencourses, Set peTchRejoinSections,
			Set peExamMaincourseNos, Set prTchPaperTitles, Set peExamNos,
			Set prPcOpencourses) {
		this.name = name;
		this.serialNumber = serialNumber;
		this.flagActive = flagActive;
		this.flagNextActive = flagNextActive;
		this.startDate = startDate;
		this.endDate = endDate;
		this.examBookingStartDate = examBookingStartDate;
		this.examBookingEndDate = examBookingEndDate;
		this.finalExamStartDate = finalExamStartDate;
		this.finalExamEndDate = finalExamEndDate;
		this.finalExamScoreDate = finalExamScoreDate;
		this.graduateStartDate = graduateStartDate;
		this.graduateEndDate = graduateEndDate;
		this.electiveFreshStartDate = electiveFreshStartDate;
		this.electiveFreshEndDate = electiveFreshEndDate;
		this.electiveStartDate = electiveStartDate;
		this.electiveEndDate = electiveEndDate;
		this.cancelElectiveStartDate = cancelElectiveStartDate;
		this.cancelElectiveEndDate = cancelElectiveEndDate;
		this.freshmanElectiveStartDate = freshmanElectiveStartDate;
		this.freshmanElectiveEndDate = freshmanElectiveEndDate;
		this.paperStartDate = paperStartDate;
		this.paperEndDate = paperEndDate;
		this.paperTitleEndDate = paperTitleEndDate;
		this.paperSyllabusEndDate = paperSyllabusEndDate;
		this.paperDraftEndDate = paperDraftEndDate;
		this.paperFinalEndDate = paperFinalEndDate;
		this.paperRejoinEndDate = paperRejoinEndDate;
		this.scoreUsualStartDate = scoreUsualStartDate;
		this.scoreUsualEndDate = scoreUsualEndDate;
		this.mainCourseStartDate = mainCourseStartDate;
		this.mainCourseEndDate = mainCourseEndDate;
		this.prExamPatrolSettings = prExamPatrolSettings;
		this.peTchRejoinRooms = peTchRejoinRooms;
		this.prExamBookings = prExamBookings;
		this.prTchOpencourses = prTchOpencourses;
		this.peTchRejoinSections = peTchRejoinSections;
		this.peExamMaincourseNos = peExamMaincourseNos;
		this.prTchPaperTitles = prTchPaperTitles;
		this.peExamNos = peExamNos;
		this.prPcOpencourses = prPcOpencourses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getFlagActive() {
		return this.flagActive;
	}

	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}

	public String getFlagNextActive() {
		return this.flagNextActive;
	}

	public void setFlagNextActive(String flagNextActive) {
		this.flagNextActive = flagNextActive;
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

	public Date getExamBookingStartDate() {
		return this.examBookingStartDate;
	}

	public void setExamBookingStartDate(Date examBookingStartDate) {
		this.examBookingStartDate = examBookingStartDate;
	}

	public Date getExamBookingEndDate() {
		return this.examBookingEndDate;
	}

	public void setExamBookingEndDate(Date examBookingEndDate) {
		this.examBookingEndDate = examBookingEndDate;
	}

	public Date getFinalExamStartDate() {
		return this.finalExamStartDate;
	}

	public void setFinalExamStartDate(Date finalExamStartDate) {
		this.finalExamStartDate = finalExamStartDate;
	}

	public Date getFinalExamEndDate() {
		return this.finalExamEndDate;
	}

	public void setFinalExamEndDate(Date finalExamEndDate) {
		this.finalExamEndDate = finalExamEndDate;
	}

	public Date getFinalExamScoreDate() {
		return this.finalExamScoreDate;
	}

	public void setFinalExamScoreDate(Date finalExamScoreDate) {
		this.finalExamScoreDate = finalExamScoreDate;
	}

	public Date getGraduateStartDate() {
		return this.graduateStartDate;
	}

	public void setGraduateStartDate(Date graduateStartDate) {
		this.graduateStartDate = graduateStartDate;
	}

	public Date getGraduateEndDate() {
		return this.graduateEndDate;
	}

	public void setGraduateEndDate(Date graduateEndDate) {
		this.graduateEndDate = graduateEndDate;
	}

	public Date getElectiveFreshStartDate() {
		return this.electiveFreshStartDate;
	}

	public void setElectiveFreshStartDate(Date electiveFreshStartDate) {
		this.electiveFreshStartDate = electiveFreshStartDate;
	}

	public Date getElectiveFreshEndDate() {
		return this.electiveFreshEndDate;
	}

	public void setElectiveFreshEndDate(Date electiveFreshEndDate) {
		this.electiveFreshEndDate = electiveFreshEndDate;
	}

	public Date getElectiveStartDate() {
		return this.electiveStartDate;
	}

	public void setElectiveStartDate(Date electiveStartDate) {
		this.electiveStartDate = electiveStartDate;
	}

	public Date getElectiveEndDate() {
		return this.electiveEndDate;
	}

	public void setElectiveEndDate(Date electiveEndDate) {
		this.electiveEndDate = electiveEndDate;
	}

	public Date getCancelElectiveStartDate() {
		return this.cancelElectiveStartDate;
	}

	public void setCancelElectiveStartDate(Date cancelElectiveStartDate) {
		this.cancelElectiveStartDate = cancelElectiveStartDate;
	}

	public Date getCancelElectiveEndDate() {
		return this.cancelElectiveEndDate;
	}

	public void setCancelElectiveEndDate(Date cancelElectiveEndDate) {
		this.cancelElectiveEndDate = cancelElectiveEndDate;
	}

	public Date getFreshmanElectiveStartDate() {
		return this.freshmanElectiveStartDate;
	}

	public void setFreshmanElectiveStartDate(Date freshmanElectiveStartDate) {
		this.freshmanElectiveStartDate = freshmanElectiveStartDate;
	}

	public Date getFreshmanElectiveEndDate() {
		return this.freshmanElectiveEndDate;
	}

	public void setFreshmanElectiveEndDate(Date freshmanElectiveEndDate) {
		this.freshmanElectiveEndDate = freshmanElectiveEndDate;
	}

	public Date getPaperStartDate() {
		return this.paperStartDate;
	}

	public void setPaperStartDate(Date paperStartDate) {
		this.paperStartDate = paperStartDate;
	}

	public Date getPaperEndDate() {
		return this.paperEndDate;
	}

	public void setPaperEndDate(Date paperEndDate) {
		this.paperEndDate = paperEndDate;
	}

	public Date getPaperTitleEndDate() {
		return this.paperTitleEndDate;
	}

	public void setPaperTitleEndDate(Date paperTitleEndDate) {
		this.paperTitleEndDate = paperTitleEndDate;
	}

	public Date getPaperSyllabusEndDate() {
		return this.paperSyllabusEndDate;
	}

	public void setPaperSyllabusEndDate(Date paperSyllabusEndDate) {
		this.paperSyllabusEndDate = paperSyllabusEndDate;
	}

	public Date getPaperDraftEndDate() {
		return this.paperDraftEndDate;
	}

	public void setPaperDraftEndDate(Date paperDraftEndDate) {
		this.paperDraftEndDate = paperDraftEndDate;
	}

	public Date getPaperFinalEndDate() {
		return this.paperFinalEndDate;
	}

	public void setPaperFinalEndDate(Date paperFinalEndDate) {
		this.paperFinalEndDate = paperFinalEndDate;
	}

	public Date getPaperRejoinEndDate() {
		return this.paperRejoinEndDate;
	}

	public void setPaperRejoinEndDate(Date paperRejoinEndDate) {
		this.paperRejoinEndDate = paperRejoinEndDate;
	}

	public Date getScoreUsualStartDate() {
		return this.scoreUsualStartDate;
	}

	public void setScoreUsualStartDate(Date scoreUsualStartDate) {
		this.scoreUsualStartDate = scoreUsualStartDate;
	}

	public Date getScoreUsualEndDate() {
		return this.scoreUsualEndDate;
	}

	public void setScoreUsualEndDate(Date scoreUsualEndDate) {
		this.scoreUsualEndDate = scoreUsualEndDate;
	}

	public Date getMainCourseStartDate() {
		return this.mainCourseStartDate;
	}

	public void setMainCourseStartDate(Date mainCourseStartDate) {
		this.mainCourseStartDate = mainCourseStartDate;
	}

	public Date getMainCourseEndDate() {
		return this.mainCourseEndDate;
	}

	public void setMainCourseEndDate(Date mainCourseEndDate) {
		this.mainCourseEndDate = mainCourseEndDate;
	}

	public Set getPrExamPatrolSettings() {
		return this.prExamPatrolSettings;
	}

	public void setPrExamPatrolSettings(Set prExamPatrolSettings) {
		this.prExamPatrolSettings = prExamPatrolSettings;
	}

	public Set getPeTchRejoinRooms() {
		return this.peTchRejoinRooms;
	}

	public void setPeTchRejoinRooms(Set peTchRejoinRooms) {
		this.peTchRejoinRooms = peTchRejoinRooms;
	}

	public Set getPrExamBookings() {
		return this.prExamBookings;
	}

	public void setPrExamBookings(Set prExamBookings) {
		this.prExamBookings = prExamBookings;
	}

	public Set getPrTchOpencourses() {
		return this.prTchOpencourses;
	}

	public void setPrTchOpencourses(Set prTchOpencourses) {
		this.prTchOpencourses = prTchOpencourses;
	}

	public Set getPeTchRejoinSections() {
		return this.peTchRejoinSections;
	}

	public void setPeTchRejoinSections(Set peTchRejoinSections) {
		this.peTchRejoinSections = peTchRejoinSections;
	}

	public Set getPeExamMaincourseNos() {
		return this.peExamMaincourseNos;
	}

	public void setPeExamMaincourseNos(Set peExamMaincourseNos) {
		this.peExamMaincourseNos = peExamMaincourseNos;
	}

	public Set getPrTchPaperTitles() {
		return this.prTchPaperTitles;
	}

	public void setPrTchPaperTitles(Set prTchPaperTitles) {
		this.prTchPaperTitles = prTchPaperTitles;
	}

	public Set getPeExamNos() {
		return this.peExamNos;
	}

	public void setPeExamNos(Set peExamNos) {
		this.peExamNos = peExamNos;
	}

	public Set getPrPcOpencourses() {
		return this.prPcOpencourses;
	}

	public void setPrPcOpencourses(Set prPcOpencourses) {
		this.prPcOpencourses = prPcOpencourses;
	}

}