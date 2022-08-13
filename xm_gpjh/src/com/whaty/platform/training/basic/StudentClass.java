/**
 * 
 */
package com.whaty.platform.training.basic;

import com.whaty.platform.training.user.TrainingStudent;

/**
 * @author chenjian
 *
 */
public class StudentClass {
	
	private TrainingStudent trainingStudent;
	
	private TrainingClass trainingClass;
	
	private String status;
	
	private String getDate;

	public TrainingStudent getTrainingStudent() {
		return trainingStudent;
	}

	public void setTrainingStudent(TrainingStudent trainingStudent) {
		this.trainingStudent = trainingStudent;
	}

	public String getGetDate() {
		if(getDate == null || getDate.length() == 0)
			return "²»Ïê";
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TrainingClass getTrainingClass() {
		return trainingClass;
	}

	public void setTrainingClass(TrainingClass trainingClass) {
		this.trainingClass = trainingClass;
	}
	
	
}
