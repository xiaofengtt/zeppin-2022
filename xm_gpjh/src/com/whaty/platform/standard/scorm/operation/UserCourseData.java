/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;



/**
 * @author Administrator
 *
 */
public class UserCourseData {
	
	private String id;
	
	private String courseId;
	
	private String studentId;
	
	private String completedPercent;
	
	private String lessonLocation;
	
	private String lessonStatus;
	
	private String totalTime;
	
	private String firstDate;
	
	private String lastDate;
	
	private String attemptNum;
	public UserCourseData()
	{
		id = "";
		courseId = "";
		studentId = "";
		completedPercent = "0";
		lessonLocation = "";
		lessonStatus = "uncomplete";
		totalTime = "0";
		firstDate = "";
		lastDate = "";
		attemptNum = "0";
	}
	
	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getLessonLocation() {
		return lessonLocation;
	}

	public void setLessonLocation(String lessonLocation) {
		this.lessonLocation = lessonLocation;
	}

	public String getLessonStatus() {
		return lessonStatus;
	}

	public void setLessonStatus(String lessonStatus) {
		this.lessonStatus = lessonStatus;
	}

	public String getCompletedPercent() {
		return completedPercent;
	}

	public void setCompletedPercent(String completedPercent) {
		this.completedPercent = completedPercent;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getAttemptNum() {
		return attemptNum;
	}

	public void setAttemptNum(String attempt_num) {
		this.attemptNum = attempt_num;
	}
	

}
