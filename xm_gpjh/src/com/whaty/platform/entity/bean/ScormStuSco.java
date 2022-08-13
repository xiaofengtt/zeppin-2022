package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * ScormStuSco entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ScormStuSco extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private ScormCourseInfo scormCourseInfo;
	private String systemId;
	private String studentId;
	private Date firstAccessdate;
	private Date lastAccessdate;
	private String sessionId;
	private String rawScore;
	private String maxScore;
	private String minScore;
	private String status;
	private String totalTime;
	private String credit;
	private Long attemptNum;
	private String lessonLocation;
	private String coreLesson;
	private String coreVendor;
	private String lessonMode;
	private String highScore;
	private String highStatus;
	private String browseScore;
	private Date sessionDate;
	private Double completePercent;
	private String exit;
	private String entry;
	private String comments;
	private String scoExit;

	// Constructors

	/** default constructor */
	public ScormStuSco() {
	}

	/** minimal constructor */
	public ScormStuSco(ScormCourseInfo scormCourseInfo, String systemId,
			String studentId) {
		this.scormCourseInfo = scormCourseInfo;
		this.systemId = systemId;
		this.studentId = studentId;
	}

	/** full constructor */
	public ScormStuSco(ScormCourseInfo scormCourseInfo, String systemId,
			String studentId, Date firstAccessdate, Date lastAccessdate,
			String sessionId, String rawScore, String maxScore,
			String minScore, String status, String totalTime, String credit,
			Long attemptNum, String lessonLocation, String coreLesson,
			String coreVendor, String lessonMode, String highScore,
			String highStatus, String browseScore, Date sessionDate,
			Double completePercent, String exit, String entry, String comments,
			String scoExit) {
		this.scormCourseInfo = scormCourseInfo;
		this.systemId = systemId;
		this.studentId = studentId;
		this.firstAccessdate = firstAccessdate;
		this.lastAccessdate = lastAccessdate;
		this.sessionId = sessionId;
		this.rawScore = rawScore;
		this.maxScore = maxScore;
		this.minScore = minScore;
		this.status = status;
		this.totalTime = totalTime;
		this.credit = credit;
		this.attemptNum = attemptNum;
		this.lessonLocation = lessonLocation;
		this.coreLesson = coreLesson;
		this.coreVendor = coreVendor;
		this.lessonMode = lessonMode;
		this.highScore = highScore;
		this.highStatus = highStatus;
		this.browseScore = browseScore;
		this.sessionDate = sessionDate;
		this.completePercent = completePercent;
		this.exit = exit;
		this.entry = entry;
		this.comments = comments;
		this.scoExit = scoExit;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ScormCourseInfo getScormCourseInfo() {
		return this.scormCourseInfo;
	}

	public void setScormCourseInfo(ScormCourseInfo scormCourseInfo) {
		this.scormCourseInfo = scormCourseInfo;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Date getFirstAccessdate() {
		return this.firstAccessdate;
	}

	public void setFirstAccessdate(Date firstAccessdate) {
		this.firstAccessdate = firstAccessdate;
	}

	public Date getLastAccessdate() {
		return this.lastAccessdate;
	}

	public void setLastAccessdate(Date lastAccessdate) {
		this.lastAccessdate = lastAccessdate;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRawScore() {
		return this.rawScore;
	}

	public void setRawScore(String rawScore) {
		this.rawScore = rawScore;
	}

	public String getMaxScore() {
		return this.maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}

	public String getMinScore() {
		return this.minScore;
	}

	public void setMinScore(String minScore) {
		this.minScore = minScore;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getCredit() {
		return this.credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public Long getAttemptNum() {
		return this.attemptNum;
	}

	public void setAttemptNum(Long attemptNum) {
		this.attemptNum = attemptNum;
	}

	public String getLessonLocation() {
		return this.lessonLocation;
	}

	public void setLessonLocation(String lessonLocation) {
		this.lessonLocation = lessonLocation;
	}

	public String getCoreLesson() {
		return this.coreLesson;
	}

	public void setCoreLesson(String coreLesson) {
		this.coreLesson = coreLesson;
	}

	public String getCoreVendor() {
		return this.coreVendor;
	}

	public void setCoreVendor(String coreVendor) {
		this.coreVendor = coreVendor;
	}

	public String getLessonMode() {
		return this.lessonMode;
	}

	public void setLessonMode(String lessonMode) {
		this.lessonMode = lessonMode;
	}

	public String getHighScore() {
		return this.highScore;
	}

	public void setHighScore(String highScore) {
		this.highScore = highScore;
	}

	public String getHighStatus() {
		return this.highStatus;
	}

	public void setHighStatus(String highStatus) {
		this.highStatus = highStatus;
	}

	public String getBrowseScore() {
		return this.browseScore;
	}

	public void setBrowseScore(String browseScore) {
		this.browseScore = browseScore;
	}

	public Date getSessionDate() {
		return this.sessionDate;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	public Double getCompletePercent() {
		return this.completePercent;
	}

	public void setCompletePercent(Double completePercent) {
		this.completePercent = completePercent;
	}

	public String getExit() {
		return this.exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

	public String getEntry() {
		return this.entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getScoExit() {
		return this.scoExit;
	}

	public void setScoExit(String scoExit) {
		this.scoExit = scoExit;
	}

}