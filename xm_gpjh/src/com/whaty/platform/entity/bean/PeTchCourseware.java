package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeTchCourseware entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchCourseware extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String fkCourseId;
	private String version;
	private String flagBak;
	private Date activeDate;
	private String code;
	private String author;
	private String note;
	private String link;
	private Set prTraineeCoursewares = new HashSet(0);
	private Set trainingCourseStudents = new HashSet(0);
	private Set prTchTraineeCoursewares = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchCourseware() {
	}

	/** minimal constructor */
	public PeTchCourseware(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeTchCourseware(String name, String fkCourseId, String version,
			String flagBak, Date activeDate, String code, String author,
			String note, String link, Set prTraineeCoursewares,
			Set trainingCourseStudents, Set prTchTraineeCoursewares) {
		this.name = name;
		this.fkCourseId = fkCourseId;
		this.version = version;
		this.flagBak = flagBak;
		this.activeDate = activeDate;
		this.code = code;
		this.author = author;
		this.note = note;
		this.link = link;
		this.prTraineeCoursewares = prTraineeCoursewares;
		this.trainingCourseStudents = trainingCourseStudents;
		this.prTchTraineeCoursewares = prTchTraineeCoursewares;
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

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public Date getActiveDate() {
		return this.activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
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

	public Set getPrTraineeCoursewares() {
		return this.prTraineeCoursewares;
	}

	public void setPrTraineeCoursewares(Set prTraineeCoursewares) {
		this.prTraineeCoursewares = prTraineeCoursewares;
	}

	public Set getTrainingCourseStudents() {
		return this.trainingCourseStudents;
	}

	public void setTrainingCourseStudents(Set trainingCourseStudents) {
		this.trainingCourseStudents = trainingCourseStudents;
	}

	public Set getPrTchTraineeCoursewares() {
		return this.prTchTraineeCoursewares;
	}

	public void setPrTchTraineeCoursewares(Set prTchTraineeCoursewares) {
		this.prTchTraineeCoursewares = prTchTraineeCoursewares;
	}

}