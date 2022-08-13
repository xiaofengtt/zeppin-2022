package com.whaty.platform.standard.aicc.course;

public class CourseTreeNode {
	
	private String systemId;
	private String developId;
	private String title;
	private int sequence;
	private String url;
	private String type;
	private String description;

	public CourseTreeNode() {
		
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDevelopId() {
		return developId;
	}
	public void setDevelopId(String developId) {
		this.developId = developId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	

}
