package com.whaty.platform.standard.scorm.offline;

import java.util.ArrayList;
import java.util.List;

public class OffLineScoStruts {

	private String toJSONString;
	private String parseJSON;
	private String sid;
	private String rid;
	private String courseID;
	private String studentID;
	private boolean visible;
	private String title;
	private List<OffLineScoStruts> items;
	private String href;
	private OffLineScoData RTData;

	
	public OffLineScoStruts() {

		this.toJSONString = "";
		this.parseJSON = "";
		this.sid = "";
		this.rid = "";
		this.courseID = "";
		this.studentID = "";
		this.visible = true;
		this.title = "";
		this.items = new ArrayList<OffLineScoStruts>();
		this.href = "";
		this.RTData = null;
	}
	
	/**
	 * 有参数初始化方法;
	 * @param sid
	 * @param rid
	 * @param courseID
	 * @param studentID
	 * @param title
	 * @param items
	 * @param href
	 */
	public OffLineScoStruts(String sid,String rid,String courseID,String studentID,String title,List<OffLineScoStruts> items,String href,OffLineScoData RTData){
		this.sid = sid;
		this.rid = rid;
		this.courseID = courseID;
		this.studentID = studentID;
		this.title = title;
		this.items = items;
		this.href = href;
		this.RTData = RTData;
		
		this.toJSONString = "";
		this.parseJSON = "";
	}

	public String getToJSONString() {
		return toJSONString;
	}

	public void setToJSONString(String toJSONString) {
		this.toJSONString = toJSONString;
	}

	public String getParseJSON() {
		return parseJSON;
	}

	public void setParseJSON(String parseJSON) {
		this.parseJSON = parseJSON;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<OffLineScoStruts> getItems() {
		return items;
	}

	public void setItems(List<OffLineScoStruts> items) {
		this.items = items;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public OffLineScoData getRTData() {
		return RTData;
	}

	public void setRTData(OffLineScoData data) {
		RTData = data;
	}
}
