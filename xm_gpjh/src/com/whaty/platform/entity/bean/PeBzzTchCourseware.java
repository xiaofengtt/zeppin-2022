package com.whaty.platform.entity.bean;

/**
 * PeBzzTchCourseware entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzTchCourseware extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String fkCourseId;
	private String flagIsvalid;
	private String flagBak;
	private String code;
	private String author;
	private String publisher;
	private String note;
	private String link;

	// Constructors

	/** default constructor */
	public PeBzzTchCourseware() {
	}

	/** minimal constructor */
	public PeBzzTchCourseware(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeBzzTchCourseware(String name, String fkCourseId,
			String flagIsvalid, String flagBak, String code, String author,
			String publisher, String note, String link) {
		this.name = name;
		this.fkCourseId = fkCourseId;
		this.flagIsvalid = flagIsvalid;
		this.flagBak = flagBak;
		this.code = code;
		this.author = author;
		this.publisher = publisher;
		this.note = note;
		this.link = link;
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

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}