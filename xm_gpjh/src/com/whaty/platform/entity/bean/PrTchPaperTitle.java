package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PrTchPaperTitle entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchPaperTitle extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeSemester peSemester;
	private String fkTeacher;
	private String title;
	private String titleMemo;
	private Long stuCountLimit;
	private Set prTchStuPapers = new HashSet(0);

	// Constructors

	/** default constructor */
	public PrTchPaperTitle() {
	}

	/** minimal constructor */
	public PrTchPaperTitle(String title) {
		this.title = title;
	}

	/** full constructor */
	public PrTchPaperTitle(PeSemester peSemester, String fkTeacher,
			String title, String titleMemo, Long stuCountLimit,
			Set prTchStuPapers) {
		this.peSemester = peSemester;
		this.fkTeacher = fkTeacher;
		this.title = title;
		this.titleMemo = titleMemo;
		this.stuCountLimit = stuCountLimit;
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

	public String getFkTeacher() {
		return this.fkTeacher;
	}

	public void setFkTeacher(String fkTeacher) {
		this.fkTeacher = fkTeacher;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleMemo() {
		return this.titleMemo;
	}

	public void setTitleMemo(String titleMemo) {
		this.titleMemo = titleMemo;
	}

	public Long getStuCountLimit() {
		return this.stuCountLimit;
	}

	public void setStuCountLimit(Long stuCountLimit) {
		this.stuCountLimit = stuCountLimit;
	}

	public Set getPrTchStuPapers() {
		return this.prTchStuPapers;
	}

	public void setPrTchStuPapers(Set prTchStuPapers) {
		this.prTchStuPapers = prTchStuPapers;
	}

}