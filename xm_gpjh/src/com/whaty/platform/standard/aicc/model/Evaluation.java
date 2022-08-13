
package com.whaty.platform.standard.aicc.model;

public class Evaluation implements DataModel{

	/*Getparam(response) : CMI Optional, AU Optional*/
	private String courseId;
	
	/*	
	private String interactionsFile;
	
	private String ObjectivesStatusFile;
	
	private String pathFile;
	
	private String performanceFile;
*/
	

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	/*public String getInteractionsFile() {
		return interactionsFile;
	}

	public void setInteractionsFile(String interactionsFile) {
		this.interactionsFile = interactionsFile;
	}

	public String getObjectivesStatusFile() {
		return ObjectivesStatusFile;
	}

	public void setObjectivesStatusFile(String objectivesStatusFile) {
		ObjectivesStatusFile = objectivesStatusFile;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public String getPerformanceFile() {
		return performanceFile;
	}

	public void setPerformanceFile(String performanceFile) {
		this.performanceFile = performanceFile;
	}*/
	
    public String toStrData(){
    	//to implement
    	return null;
    }
       
	
}
