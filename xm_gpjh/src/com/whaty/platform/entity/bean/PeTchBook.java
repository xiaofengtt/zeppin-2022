package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeTchBook entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchBook extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String trueName;
	private String flagIsvalid;
	private String flagBak;
	private String code;
	private String isbn;
	private String author;
	private String publisher;
	private Double price;
	private String note;
	private Set prTchOpencourseBooks = new HashSet(0);
	private Set prTchCourseBooks = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchBook() {
	}

	/** minimal constructor */
	public PeTchBook(String name, String trueName) {
		this.name = name;
		this.trueName = trueName;
	}

	/** full constructor */
	public PeTchBook(String name, String trueName, String flagIsvalid,
			String flagBak, String code, String isbn, String author,
			String publisher, Double price, String note,
			Set prTchOpencourseBooks, Set prTchCourseBooks) {
		this.name = name;
		this.trueName = trueName;
		this.flagIsvalid = flagIsvalid;
		this.flagBak = flagBak;
		this.code = code;
		this.isbn = isbn;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.note = note;
		this.prTchOpencourseBooks = prTchOpencourseBooks;
		this.prTchCourseBooks = prTchCourseBooks;
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

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getFlagIsvalid() {
		return this.flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrTchOpencourseBooks() {
		return this.prTchOpencourseBooks;
	}

	public void setPrTchOpencourseBooks(Set prTchOpencourseBooks) {
		this.prTchOpencourseBooks = prTchOpencourseBooks;
	}

	public Set getPrTchCourseBooks() {
		return this.prTchCourseBooks;
	}

	public void setPrTchCourseBooks(Set prTchCourseBooks) {
		this.prTchCourseBooks = prTchCourseBooks;
	}

}