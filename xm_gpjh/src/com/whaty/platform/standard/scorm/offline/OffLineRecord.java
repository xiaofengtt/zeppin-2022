package com.whaty.platform.standard.scorm.offline;

import java.util.List;

public class OffLineRecord {
	
	private String sid;
	private String courseID;
	private String studentID;
	private String title;
	private String rid;
	private boolean visible;
	private String href;
	private String RTData;
	
	
	private List<OffLineSco> items;
	
	public OffLineRecord(){}
	
	public OffLineRecord(String sid,String courseId,String studentId,String title,List<OffLineSco> items){
		
		this.sid = sid;
		this.courseID = courseId;
		this.studentID = studentId;
		this.title = title;
		this.items = items;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<OffLineSco> getItems() {
		return items;
	}

	public void setItems(List<OffLineSco> items) {
		this.items = items;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getRTData() {
		return RTData;
	}

	public void setRTData(String data) {
		RTData = data;
	}

	
	
}
