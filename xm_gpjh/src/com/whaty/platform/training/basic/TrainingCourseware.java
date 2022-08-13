/**
 * 
 */
package com.whaty.platform.training.basic;


import java.util.Map;


import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public abstract class TrainingCourseware{

	
	private String id;
	
	private String name;
	
	private String note;
	
	private String coursewareType;
	
	private boolean isActive;
	
	private String navigate;
	

	
	/**
	 * ������ͣ������ʽ(POPWIN)����Ƕ�봰��ʽ(INWIN);
	 */
	private String viewType;
	
	
	public String getCoursewareType() {
		return coursewareType;
	}




	public void setCoursewareType(String coursewareType) {
		this.coursewareType = coursewareType;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public boolean getIsActive() {
		return isActive;
	}



	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public abstract String getEntranceURL(Map params) throws PlatformException;
	
	public abstract String getStudyStatisticsURL(Map params) throws PlatformException;



	public String getViewType() {
		return viewType;
	}



	public void setViewType(String viewType) {
		this.viewType = viewType;
	}




	public String getNavigate() {
		return navigate;
	}




	public void setNavigate(String navigate) {
		this.navigate = navigate;
	}
}

