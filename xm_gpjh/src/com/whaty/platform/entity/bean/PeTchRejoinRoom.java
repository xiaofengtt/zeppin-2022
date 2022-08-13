package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeTchRejoinRoom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTchRejoinRoom extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeSemester peSemester;
	private String name;
	private String trueName;
	private Long code;
	private Set prTchStuPapers = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeTchRejoinRoom() {
	}

	/** full constructor */
	public PeTchRejoinRoom(PeSemester peSemester, String name, String trueName,
			Long code, Set prTchStuPapers) {
		this.peSemester = peSemester;
		this.name = name;
		this.trueName = trueName;
		this.code = code;
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

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Long getCode() {
		return this.code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Set getPrTchStuPapers() {
		return this.prTchStuPapers;
	}

	public void setPrTchStuPapers(Set prTchStuPapers) {
		this.prTchStuPapers = prTchStuPapers;
	}

}