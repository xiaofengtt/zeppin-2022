/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;

/**
 * @author Administrator
 *
 */
public class ScormItem {
	
	private String id;
	private String courseId;
	private String type;
	private String title;
	private String launch;
	private String parameterString;
	private String dataFromLms;
	private String preRequisites;
	private String masteryScore;
	private String maxTimeAllowed;
	private String timeLimitation;
	private int sequence;
	private int theLevel;
	private String itemId;
	public String getDataFromLms() {
		return dataFromLms;
	}
	public void setDataFromLms(String dataFromLms) {
		this.dataFromLms = dataFromLms;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLaunch() {
		return launch;
	}
	public void setLaunch(String launch) {
		this.launch = launch;
	}
	public String getMasteryScore() {
		return masteryScore;
	}
	public void setMasteryScore(String masteryScore) {
		this.masteryScore = masteryScore;
	}
	public String getMaxTimeAllowed() {
		return maxTimeAllowed;
	}
	public void setMaxTimeAllowed(String maxTimeAllowed) {
		this.maxTimeAllowed = maxTimeAllowed;
	}
	public String getParameterString() {
		return parameterString;
	}
	public void setParameterString(String parameterString) {
		this.parameterString = parameterString;
	}
	public String getPreRequisites() {
		return preRequisites;
	}
	public void setPreRequisites(String preRequisites) {
		this.preRequisites = preRequisites;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public int getTheLevel() {
		return theLevel;
	}
	public void setTheLevel(int theLevel) {
		this.theLevel = theLevel;
	}
	public String getTimeLimitation() {
		return timeLimitation;
	}
	public void setTimeLimitation(String timeLimitation) {
		this.timeLimitation = timeLimitation;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
