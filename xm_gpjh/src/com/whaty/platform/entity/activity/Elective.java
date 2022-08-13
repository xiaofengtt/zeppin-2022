/**
 * 
 */
package com.whaty.platform.entity.activity;

import com.whaty.platform.entity.user.Student;

/**
 * @author chenjian
 *
 */
public abstract class Elective {
	
	private String id;
	
	private Student student;
	
	private TeachClass teachClass;
	
	private String selectDate;
	
	private String electiveStatus;
	
	private String usualScore="0";
	
	private String examScore="0";
	
	private String totalScore="0";
	
	private String usualScoreStatus="";
	
	private String examScoreStatus="";
	
	private String totalScoreStatus="";
	
	private String expendScoreStatus="";
	
	private String expendScoreStudentStatus="";
	
	private String experimentScore="0";
	
	private String expendScore="0";
	
	private String experimentScoreStatus="";
	
	private String free_total_score_status="";
	
	private String total_expend_score="0";
	
	private String total_score_rule="";
	
	private String total_score_modify_rule="";
	private String total_expend_score_rule="";
	
	private String test_score="0";
	
		

	

	public String getElectiveStatus() {
		return electiveStatus;
	}

	public void setElectiveStatus(String electiveStatus) {
		this.electiveStatus = electiveStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelectDate() {
		return selectDate;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public TeachClass getTeachClass() {
		return teachClass;
	}

	public void setTeachClass(TeachClass teachClass) {
		this.teachClass = teachClass;
	}

	public String getExamScore() {
		return examScore;
	}

	public void setExamScore(String examScore) {
		this.examScore = examScore;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getUsualScore() {
		return usualScore;
	}

	public void setUsualScore(String usualScore) {
		this.usualScore = usualScore;
	}

	public String getExamScoreStatus() {
		return examScoreStatus;
	}

	public void setExamScoreStatus(String examScoreStatus) {
		this.examScoreStatus = examScoreStatus;
	}

	public String getTotalScoreStatus() {
		return totalScoreStatus;
	}

	public void setTotalScoreStatus(String totalScoreStatus) {
		this.totalScoreStatus = totalScoreStatus;
	}

	public String getUsualScoreStatus() {
		return usualScoreStatus;
	}

	public void setUsualScoreStatus(String usualScoreStatus) {
		this.usualScoreStatus = usualScoreStatus;
	}

	public String getExpendScoreStatus() {
		return expendScoreStatus;
	}

	public void setExpendScoreStatus(String expendScoreStatus) {
		this.expendScoreStatus = expendScoreStatus;
	}

	public String getExpendScoreStudentStatus() {
		return expendScoreStudentStatus;
	}

	public void setExpendScoreStudentStatus(String expendScoreStudentStatus) {
		this.expendScoreStudentStatus = expendScoreStudentStatus;
	}

	public String getExpendScore() {
		return expendScore;
	}

	public void setExpendScore(String expendScore) {
		this.expendScore = expendScore;
	}

	public String getExperimentScore() {
		return experimentScore;
	}

	public void setExperimentScore(String experimentScore) {
		this.experimentScore = experimentScore;
	}

	public String getExperimentScoreStatus() {
		return experimentScoreStatus;
	}

	public void setExperimentScoreStatus(String experimentScoreStatus) {
		this.experimentScoreStatus = experimentScoreStatus;
	}

	public String getFree_total_score_status() {
		return free_total_score_status;
	}

	public void setFree_total_score_status(String free_total_score_status) {
		this.free_total_score_status = free_total_score_status;
	}

	public String getTotal_expend_score() {
		return total_expend_score;
	}

	public void setTotal_expend_score(String total_expend_score) {
		this.total_expend_score = total_expend_score;
	}

	public String getTotal_expend_score_rule() {
		return total_expend_score_rule;
	}

	public void setTotal_expend_score_rule(String total_expend_score_rule) {
		this.total_expend_score_rule = total_expend_score_rule;
	}

	public String getTotal_score_modify_rule() {
		return total_score_modify_rule;
	}

	public void setTotal_score_modify_rule(String total_score_modify_rule) {
		this.total_score_modify_rule = total_score_modify_rule;
	}

	public String getTotal_score_rule() {
		return total_score_rule;
	}

	public void setTotal_score_rule(String total_score_rule) {
		this.total_score_rule = total_score_rule;
	}

	public String getTest_score() {
		return test_score;
	}

	public void setTest_score(String test_score) {
		this.test_score = test_score;
	}
	
	
	
}
