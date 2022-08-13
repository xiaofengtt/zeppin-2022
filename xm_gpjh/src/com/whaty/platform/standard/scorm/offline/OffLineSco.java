package com.whaty.platform.standard.scorm.offline;

import java.util.ArrayList;
import java.util.List;

public class OffLineSco {
	
	private String sid;
	private String rid;
	private String courseID;
	private String studentID;
	private String title;
	private String href;
	private OffLineScoData RTData;
	
	private String toJSONString;
	private String parseJSON;
	private boolean visible;
	private List<OffLineSco> items = new ArrayList<OffLineSco>();
	
	public OffLineSco(){}
	
	public OffLineSco(String sid,String rid,String title,OffLineScoData scoData){
		this.sid = sid;
		this.rid = rid;
		this.title = title;
		this.RTData = scoData;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
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

	public OffLineScoData getRTData() {
		return RTData;
	}

	public void setRTData(OffLineScoData data) {
		RTData = data;
	}

	

	public List<OffLineSco> getItems() {
		return items;
	}

	public void setItems(List<OffLineSco> items) {
		this.items = items;
	}

	public String getParseJSON() {
		return parseJSON;
	}

	public void setParseJSON(String parseJSON) {
		this.parseJSON = parseJSON;
	}

	public String getToJSONString() {
		return toJSONString;
	}

	public void setToJSONString(String toJSONString) {
		this.toJSONString = toJSONString;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	

	
	
	
}
