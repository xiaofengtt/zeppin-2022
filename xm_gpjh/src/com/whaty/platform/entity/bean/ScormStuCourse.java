package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * ScormStuCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ScormStuCourse extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private ScormCourseInfo scormCourseInfo;
	private String studentId;
	private String credit;
	private String lessonLocation;
	private String lessonStatus;
	private String rawScore;
	private String maxScore;
	private String minScore;
	private String totalTime;
	private String lessonMode;
	private String coreLesson;
	private String coreVendor;
	private String highScore;
	private String highStatus;
	private Date firstDate;
	private Date lastDate;
	private String browseScore;
	private Date sessionDate;
	private Double completePercent;
	private Long attemptNum;
	private String courseCode;

	// Constructors

	/** default constructor */
	public ScormStuCourse() {
	}

	/** minimal constructor */
	public ScormStuCourse(ScormCourseInfo scormCourseInfo, String studentId) {
		this.scormCourseInfo = scormCourseInfo;
		this.studentId = studentId;
	}

	/** full constructor */
	public ScormStuCourse(ScormCourseInfo scormCourseInfo, String studentId,
			String credit, String lessonLocation, String lessonStatus,
			String rawScore, String maxScore, String minScore,
			String totalTime, String lessonMode, String coreLesson,
			String coreVendor, String highScore, String highStatus,
			Date firstDate, Date lastDate, String browseScore,
			Date sessionDate, Double completePercent, Long attemptNum,
			String courseCode) {
		this.scormCourseInfo = scormCourseInfo;
		this.studentId = studentId;
		this.credit = credit;
		this.lessonLocation = lessonLocation;
		this.lessonStatus = lessonStatus;
		this.rawScore = rawScore;
		this.maxScore = maxScore;
		this.minScore = minScore;
		this.totalTime = totalTime;
		this.lessonMode = lessonMode;
		this.coreLesson = coreLesson;
		this.coreVendor = coreVendor;
		this.highScore = highScore;
		this.highStatus = highStatus;
		this.firstDate = firstDate;
		this.lastDate = lastDate;
		this.browseScore = browseScore;
		this.sessionDate = sessionDate;
		this.completePercent = completePercent;
		this.attemptNum = attemptNum;
		this.courseCode = courseCode;
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

	public String getStudentId() {
		return this.studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCredit() {
		return this.credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getLessonLocation() {
		return this.lessonLocation;
	}

	public void setLessonLocation(String lessonLocation) {
		this.lessonLocation = lessonLocation;
	}

	public String getLessonStatus() {
		return this.lessonStatus;
	}

	public void setLessonStatus(String lessonStatus) {
		this.lessonStatus = lessonStatus;
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

	public String getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getLessonMode() {
		return this.lessonMode;
	}

	public void setLessonMode(String lessonMode) {
		this.lessonMode = lessonMode;
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

	public Date getFirstDate() {
		return this.firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
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

	public Long getAttemptNum() {
		return this.attemptNum;
	}

	public void setAttemptNum(Long attemptNum) {
		this.attemptNum = attemptNum;
	}

	public String getCourseCode() {
		return this.courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

}