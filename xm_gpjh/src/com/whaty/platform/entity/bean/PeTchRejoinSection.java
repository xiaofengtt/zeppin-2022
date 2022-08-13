package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeTchRejoinSection entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchRejoinSection extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeSemester peSemester;
	private String name;
	private Date rejoinDatetime;
	private Long sequence;
	private Set prTchStuPapers = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchRejoinSection() {
	}

	/** full constructor */
	public PeTchRejoinSection(PeSemester peSemester, String name,
			Date rejoinDatetime, Long sequence, Set prTchStuPapers) {
		this.peSemester = peSemester;
		this.name = name;
		this.rejoinDatetime = rejoinDatetime;
		this.sequence = sequence;
		this.prTchStuPapers = prTchStuPapers;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeSemester getPeSemester() {
		return this.peSemester;
	}

	public void setPeSemester(PeSemester peSemester) {
		this.peSemester = peSemester;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRejoinDatetime() {
		return this.rejoinDatetime;
	}

	public void setRejoinDatetime(Date rejoinDatetime) {
		this.rejoinDatetime = rejoinDatetime;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public Set getPrTchStuPapers() {
		return this.prTchStuPapers;
	}

	public void setPrTchStuPapers(Set prTchStuPapers) {
		this.prTchStuPapers = prTchStuPapers;
	}

}