package com.whaty.platform.entity.bean;

/**
 * PrTchCourseBook entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchCourseBook extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeTchBook peTchBook;
	private String fkCourseId;

	// Constructors

	/** default constructor */
	public PrTchCourseBook() {
	}

	/** full constructor */
	public PrTchCourseBook(PeTchBook peTchBook, String fkCourseId) {
		this.peTchBook = peTchBook;
		this.fkCourseId = fkCourseId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTchBook getPeTchBook() {
		return this.peTchBook;
	}

	public void setPeTchBook(PeTchBook peTchBook) {
		this.peTchBook = peTchBook;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

}