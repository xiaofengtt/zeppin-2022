package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeQuestions entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeQuestions extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeTchCourse peTchCourse;
	private String title;
	private String detail;
	private String fkTraineeId;
	private String flagCryptonym;
	private Long support;
	private Long oppose;
	private Long score;
	private String replyNum;
	private String flagSolve;
	private Date createDate;
	private Date resolveDate;
	private Set peAnswerses = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeQuestions() {
	}

	/** full constructor */
	public PeQuestions(PeTchCourse peTchCourse, String title, String detail,
			String fkTraineeId, String flagCryptonym, Long support,
			Long oppose, Long score, String replyNum, String flagSolve,
			Date createDate, Date resolveDate, Set peAnswerses) {
		this.peTchCourse = peTchCourse;
		this.title = title;
		this.detail = detail;
		this.fkTraineeId = fkTraineeId;
		this.flagCryptonym = flagCryptonym;
		this.support = support;
		this.oppose = oppose;
		this.score = score;
		this.replyNum = replyNum;
		this.flagSolve = flagSolve;
		this.createDate = createDate;
		this.resolveDate = resolveDate;
		this.peAnswerses = peAnswerses;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeTchCourse getPeTchCourse() {
		return this.peTchCourse;
	}

	public void setPeTchCourse(PeTchCourse peTchCourse) {
		this.peTchCourse = peTchCourse;
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

	public String getFkTraineeId() {
		return this.fkTraineeId;
	}

	public void setFkTraineeId(String fkTraineeId) {
		this.fkTraineeId = fkTraineeId;
	}

	public String getFlagCryptonym() {
		return this.flagCryptonym;
	}

	public void setFlagCryptonym(String flagCryptonym) {
		this.flagCryptonym = flagCryptonym;
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

	public Long getScore() {
		return this.score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public String getReplyNum() {
		return this.replyNum;
	}

	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum;
	}

	public String getFlagSolve() {
		return this.flagSolve;
	}

	public void setFlagSolve(String flagSolve) {
		this.flagSolve = flagSolve;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getResolveDate() {
		return this.resolveDate;
	}

	public void setResolveDate(Date resolveDate) {
		this.resolveDate = resolveDate;
	}

	public Set getPeAnswerses() {
		return this.peAnswerses;
	}

	public void setPeAnswerses(Set peAnswerses) {
		this.peAnswerses = peAnswerses;
	}

}