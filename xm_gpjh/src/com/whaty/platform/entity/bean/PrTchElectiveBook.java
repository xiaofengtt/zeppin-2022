package com.whaty.platform.entity.bean;

/**
 * PrTchElectiveBook entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchElectiveBook extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrTchStuElective prTchStuElective;
	private PrTchOpencourseBook prTchOpencourseBook;

	// Constructors

	/** default constructor */
	public PrTchElectiveBook() {
	}

	/** full constructor */
	public PrTchElectiveBook(PrTchStuElective prTchStuElective,
			PrTchOpencourseBook prTchOpencourseBook) {
		this.prTchStuElective = prTchStuElective;
		this.prTchOpencourseBook = prTchOpencourseBook;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrTchStuElective getPrTchStuElective() {
		return this.prTchStuElective;
	}

	public void setPrTchStuElective(PrTchStuElective prTchStuElective) {
		this.prTchStuElective = prTchStuElective;
	}

	public PrTchOpencourseBook getPrTchOpencourseBook() {
		return this.prTchOpencourseBook;
	}

	public void setPrTchOpencourseBook(PrTchOpencourseBook prTchOpencourseBook) {
		this.prTchOpencourseBook = prTchOpencourseBook;
	}

}