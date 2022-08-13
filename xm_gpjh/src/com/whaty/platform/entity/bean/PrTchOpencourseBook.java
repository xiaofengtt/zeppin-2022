package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrTchOpencourseBook entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchOpencourseBook extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrTchOpencourse prTchOpencourse;
	private PeTchBook peTchBook;
	private Set prTchElectiveBooks = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrTchOpencourseBook() {
	}

	/** full constructor */
	public PrTchOpencourseBook(PrTchOpencourse prTchOpencourse,
			PeTchBook peTchBook, Set prTchElectiveBooks) {
		this.prTchOpencourse = prTchOpencourse;
		this.peTchBook = peTchBook;
		this.prTchElectiveBooks = prTchElectiveBooks;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrTchOpencourse getPrTchOpencourse() {
		return this.prTchOpencourse;
	}

	public void setPrTchOpencourse(PrTchOpencourse prTchOpencourse) {
		this.prTchOpencourse = prTchOpencourse;
	}

	public PeTchBook getPeTchBook() {
		return this.peTchBook;
	}

	public void setPeTchBook(PeTchBook peTchBook) {
		this.peTchBook = peTchBook;
	}

	public Set getPrTchElectiveBooks() {
		return this.prTchElectiveBooks;
	}

	public void setPrTchElectiveBooks(Set prTchElectiveBooks) {
		this.prTchElectiveBooks = prTchElectiveBooks;
	}

}