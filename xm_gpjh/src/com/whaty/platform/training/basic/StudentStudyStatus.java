/**
 * 
 */
package com.whaty.platform.training.basic;

import java.util.Date;

import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.RelativeTime;

/**
 * @author chenjian
 *
 */
public abstract class StudentStudyStatus {

	private TrainingStudent student;
	
	private int selectedCourseNum;
	
	private int completedCourseNum;
	
	private int unCompletedCourseNum;
	
	private int selectedClassNum;
	
	private int completedClassNum;
	
	private int uncompletedClassNum;
	
	private Date lastStudyTime;
	
	private RelativeTime studyTotalTime;

	public int getCompletedClassNum() {
		return completedClassNum;
	}

	public void setCompletedClassNum(int completedClassNum) {
		this.completedClassNum = completedClassNum;
	}

	public int getCompletedCourseNum() {
		return completedCourseNum;
	}

	public void setCompletedCourseNum(int completedCourseNum) {
		this.completedCourseNum = completedCourseNum;
	}

	public Date getLastStudyTime() {
		return lastStudyTime;
	}

	public void setLastStudyTime(Date lastStudyTime) {
		this.lastStudyTime = lastStudyTime;
	}

	public int getSelectedClassNum() {
		return selectedClassNum;
	}

	public void setSelectedClassNum(int selectedClassNum) {
		this.selectedClassNum = selectedClassNum;
	}

	public int getSelectedCourseNum() {
		return selectedCourseNum;
	}

	public void setSelectedCourseNum(int selectedCourseNum) {
		this.selectedCourseNum = selectedCourseNum;
	}

	public TrainingStudent getStudent() {
		return student;
	}

	public void setStudent(TrainingStudent student) {
		this.student = student;
	}

	public RelativeTime getStudyTotalTime() {
		return studyTotalTime;
	}

	public void setStudyTotalTime(RelativeTime studyTotalTime) {
		this.studyTotalTime = studyTotalTime;
	}

	public int getUncompletedClassNum() {
		return uncompletedClassNum;
	}

	public void setUncompletedClassNum(int uncompletedClassNum) {
		this.uncompletedClassNum = uncompletedClassNum;
	}

	public int getUnCompletedCourseNum() {
		return unCompletedCourseNum;
	}

	public void setUnCompletedCourseNum(int unCompletedCourseNum) {
		this.unCompletedCourseNum = unCompletedCourseNum;
	}
	
	
}
