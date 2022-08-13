package com.whaty.platform.interaction.forum;

public abstract class ForumList implements com.whaty.platform.Items {

	private String id;

	private String name;

	private String content;

	private String date;

	private String courseId;

	// private String isBrowser;

	// private String isSpeak;

	// private String num;

	public ForumList() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
}
