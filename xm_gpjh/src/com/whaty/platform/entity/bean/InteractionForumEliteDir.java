package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionForumEliteDir entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionForumEliteDir extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String note;
	private Date createDate;
	private String courseId;
	private String fatherId;
	private String forumlistId;

	// Constructors

	/** default constructor */
	public InteractionForumEliteDir() {
	}

	/** minimal constructor */
	public InteractionForumEliteDir(String name) {
		this.name = name;
	}

	/** full constructor */
	public InteractionForumEliteDir(String name, String note, Date createDate,
			String courseId, String fatherId, String forumlistId) {
		this.name = name;
		this.note = note;
		this.createDate = createDate;
		this.courseId = courseId;
		this.fatherId = fatherId;
		this.forumlistId = forumlistId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getFatherId() {
		return this.fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getForumlistId() {
		return this.forumlistId;
	}

	public void setForumlistId(String forumlistId) {
		this.forumlistId = forumlistId;
	}

}