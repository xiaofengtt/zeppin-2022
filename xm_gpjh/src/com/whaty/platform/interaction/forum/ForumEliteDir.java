package com.whaty.platform.interaction.forum;

public abstract class ForumEliteDir implements com.whaty.platform.Items,
		com.whaty.platform.DirTree {

	private String id;

	private String name;

	private String note;

	private String date;

	private String courseId;

	private String fatherId;

	private String forumListId;

	/** Creates a new instance of ForumEliteDir */
	public ForumEliteDir() {
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getForumListId() {
		return forumListId;
	}

	public void setForumListId(String forumListId) {
		this.forumListId = forumListId;
	}

}
