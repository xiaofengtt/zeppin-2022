package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzSuggestion entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzSuggestion extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PrBzzTchOpencourse prBzzTchOpencourse;
	private String content;
	private String fkUserId;
	private Date dataDate;
	private String yaoqiu;

	// Constructors

	/** default constructor */
	public PeBzzSuggestion() {
	}

	/** full constructor */
	public PeBzzSuggestion(PrBzzTchOpencourse prBzzTchOpencourse,
			String content, String fkUserId, Date dataDate, String yaoqiu) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
		this.content = content;
		this.fkUserId = fkUserId;
		this.dataDate = dataDate;
		this.yaoqiu = yaoqiu;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PrBzzTchOpencourse getPrBzzTchOpencourse() {
		return this.prBzzTchOpencourse;
	}

	public void setPrBzzTchOpencourse(PrBzzTchOpencourse prBzzTchOpencourse) {
		this.prBzzTchOpencourse = prBzzTchOpencourse;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFkUserId() {
		return this.fkUserId;
	}

	public void setFkUserId(String fkUserId) {
		this.fkUserId = fkUserId;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public String getYaoqiu() {
		return this.yaoqiu;
	}

	public void setYaoqiu(String yaoqiu) {
		this.yaoqiu = yaoqiu;
	}

}