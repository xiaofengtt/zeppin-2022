/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;
import com.whaty.platform.standard.scorm.datamodels.SCODataManager;
/**
 * @author Administrator
 *
 */
public abstract class UserScoData extends SCODataManager{
	
	private String id;
	
	private String systemId;
	
	private String courseId;
	
	
	private String launch;
	
	private String type;
	
	private String title;
	
	public String getLaunch() {
		return launch;
	}

	public void setLaunch(String launch) {
		this.launch = launch;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserScoData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
