/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;

import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;

/**
 * @author Administrator
 *
 */
public abstract class CMIWithDB {
	
	private String userId;
	private String CourseId;
	private String systemId;
	private SCODataManager scoData;
	
	private String openId;
	
	public String getCourseId() {
		return CourseId;
	}
	public void setCourseId(String courseId) {
		CourseId = courseId;
	}
	public SCODataManager getScoData() {
		return scoData;
	}
	public void setScoData(SCODataManager scoData) {
		this.scoData = scoData;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public abstract void putToDB() throws ScormException;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
