/**
 * 
 */
package com.whaty.platform.training;


/**
 * @author chenjian
 *
 */
public abstract class TrainingManagePriv {
	
	private String trainingManagerId;
	
	public int addTrainingCourseType=1;
	
	public int updateTrainingCourseType=1;
	
	public int moveTrainingCourseType=1;
	
	public int deleteTrainingCourseType=1;
	
	public int getTrainingCourseTypes=1;
	
	public int getTrainingCourseType=1;

	public String getTrainingManagerId() {
		return trainingManagerId;
	}

	public void setTrainingManagerId(String trainingManagerId) {
		this.trainingManagerId = trainingManagerId;
	}
	
	

}
