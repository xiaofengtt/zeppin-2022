package com.whaty.platform.standard.scorm.offline;

import java.util.ArrayList;
import java.util.List;

public class OffLineScoData {
	
	private String suspend_data;
	private String launch_data;
	private String comments;
	private List<OffLineElement> core;
	
	private String toJSONString;
	private String parseJSON;
	private String scoID;
	private List cmiParL1;
	private List cmiParL2;
	private List<OffLineElement> student_data;
	
	
	public String getSuspend_data() {
		return suspend_data;
	}
	public void setSuspend_data(String suspend_data) {
		this.suspend_data = suspend_data;
	}
	public String getLaunch_data() {
		return launch_data;
	}
	public void setLaunch_data(String launch_data) {
		this.launch_data = launch_data;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<OffLineElement> getCore() {
		return core;
	}
	public void setCore(List<OffLineElement> core) {
		this.core = core;
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
	public String getScoID() {
		return scoID;
	}
	public void setScoID(String scoID) {
		this.scoID = scoID;
	}
	public List getCmiParL1() {
		return cmiParL1;
	}
	public void setCmiParL1(List cmiParL1) {
		this.cmiParL1 = cmiParL1;
	}
	public List getCmiParL2() {
		return cmiParL2;
	}
	public void setCmiParL2(List cmiParL2) {
		this.cmiParL2 = cmiParL2;
	}
	public List<OffLineElement> getStudent_data() {
		return student_data;
	}
	public void setStudent_data(List<OffLineElement> student_data) {
		this.student_data = student_data;
	}
	
	
}
