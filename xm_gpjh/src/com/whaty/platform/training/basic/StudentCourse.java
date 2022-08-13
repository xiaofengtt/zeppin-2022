/**
 * 
 */
package com.whaty.platform.training.basic;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.training.user.TrainingStudent;

/**
 * @author chenjian
 *
 */
public abstract class StudentCourse {
	
	private String id;

	private TrainingStudent trainingStudent;
	
	private TrainingCourse trainingCourse;
	
	private String status;
	
	private StudyProgress learnProgress;
	
	private String getDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public StudyProgress getLearnProgress() {
		return learnProgress;
	}

	public void setLearnProgress(StudyProgress learnProgress) {
		this.learnProgress = learnProgress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TrainingCourse getTrainingCourse() {
		return trainingCourse;
	}

	public void setTrainingCourse(TrainingCourse trainingCourse) {
		this.trainingCourse = trainingCourse;
	}

	public TrainingStudent getTrainingStudent() {
		return trainingStudent;
	}

	public void setTrainingStudent(TrainingStudent trainingStudent) {
		this.trainingStudent = trainingStudent;
	}
	
	/**修改学生学习课程的记录
	 * @throws PlatformException
	 */
	public abstract void update() throws PlatformException;
	
	public abstract boolean isExist() throws PlatformException;
}
