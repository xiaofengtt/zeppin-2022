package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * InteractionQuestionElitedir entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InteractionQuestionElitedir extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String note;
	private Date date;
	private String courseId;
	private String dirFather;

	// Constructors

	/** default constructor */
	public InteractionQuestionElitedir() {
	}

	/** minimal constructor */
	public InteractionQuestionElitedir(String name, Date date, String courseId,
			String dirFather) {
		this.name = name;
		this.date = date;
		this.courseId = courseId;
		this.dirFather = dirFather;
	}

	/** full constructor */
	public InteractionQuestionElitedir(String name, String note, Date date,
			String courseId, String dirFather) {
		this.name = name;
		this.note = note;
		this.date = date;
		this.courseId = courseId;
		this.dirFather = dirFather;
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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCourseId() {
		return this.courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getDirFather() {
		return this.dirFather;
	}

	public void setDirFather(String dirFather) {
		this.dirFather = dirFather;
	}

}