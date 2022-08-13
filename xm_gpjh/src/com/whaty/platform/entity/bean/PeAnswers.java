package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeAnswers entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeAnswers extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeQuestions peQuestions;
	private String title;
	private String detail;
	private String fkSsoUser;
	private Date createDate;
	private String flagPotimalAns;
	private String flagCryptonym;
	private Long score;
	private Long support;
	private Long oppose;

	// Constructors

	/** default constructor */
	public PeAnswers() {
	}

	/** full constructor */
	public PeAnswers(PeQuestions peQuestions, String title, String detail,
			String fkSsoUser, Date createDate, String flagPotimalAns,
			String flagCryptonym, Long score, Long support, Long oppose) {
		this.peQuestions = peQuestions;
		this.title = title;
		this.detail = detail;
		this.fkSsoUser = fkSsoUser;
		this.createDate = createDate;
		this.flagPotimalAns = flagPotimalAns;
		this.flagCryptonym = flagCryptonym;
		this.score = score;
		this.support = support;
		this.oppose = oppose;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeQuestions getPeQuestions() {
		return this.peQuestions;
	}

	public void setPeQuestions(PeQuestions peQuestions) {
		this.peQuestions = peQuestions;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getFkSsoUser() {
		return this.fkSsoUser;
	}

	public void setFkSsoUser(String fkSsoUser) {
		this.fkSsoUser = fkSsoUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFlagPotimalAns() {
		return this.flagPotimalAns;
	}

	public void setFlagPotimalAns(String flagPotimalAns) {
		this.flagPotimalAns = flagPotimalAns;
	}

	public String getFlagCryptonym() {
		return this.flagCryptonym;
	}

	public void setFlagCryptonym(String flagCryptonym) {
		this.flagCryptonym = flagCryptonym;
	}

	public Long getScore() {
		return this.score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getSupport() {
		return this.support;
	}

	public void setSupport(Long support) {
		this.support = support;
	}

	public Long getOppose() {
		return this.oppose;
	}

	public void setOppose(Long oppose) {
		this.oppose = oppose;
	}

}