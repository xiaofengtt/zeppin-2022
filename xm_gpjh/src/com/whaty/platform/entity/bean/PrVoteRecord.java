package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrVoteRecord entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrVoteRecord extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeVotePaper peVotePaper;
	private PrVoteSuggest prVoteSuggest;
	private PeVoteDetail peVoteDetail;
	private String ip;
	private String userSession;
	private Date voteDate;
	private String classIdentifier;
	private String content;

	// Constructors

	/** default constructor */
	public PrVoteRecord() {
	}

	/** full constructor */
	public PrVoteRecord(PeVotePaper peVotePaper, PrVoteSuggest prVoteSuggest,
			PeVoteDetail peVoteDetail, String ip, String userSession,
			Date voteDate, String classIdentifier, String content) {
		this.peVotePaper = peVotePaper;
		this.prVoteSuggest = prVoteSuggest;
		this.peVoteDetail = peVoteDetail;
		this.ip = ip;
		this.userSession = userSession;
		this.voteDate = voteDate;
		this.classIdentifier = classIdentifier;
		this.content = content;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeVotePaper getPeVotePaper() {
		return this.peVotePaper;
	}

	public void setPeVotePaper(PeVotePaper peVotePaper) {
		this.peVotePaper = peVotePaper;
	}

	public PrVoteSuggest getPrVoteSuggest() {
		return this.prVoteSuggest;
	}

	public void setPrVoteSuggest(PrVoteSuggest prVoteSuggest) {
		this.prVoteSuggest = prVoteSuggest;
	}

	public PeVoteDetail getPeVoteDetail() {
		return this.peVoteDetail;
	}

	public void setPeVoteDetail(PeVoteDetail peVoteDetail) {
		this.peVoteDetail = peVoteDetail;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserSession() {
		return this.userSession;
	}

	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

	public Date getVoteDate() {
		return this.voteDate;
	}

	public void setVoteDate(Date voteDate) {
		this.voteDate = voteDate;
	}

	public String getClassIdentifier() {
		return this.classIdentifier;
	}

	public void setClassIdentifier(String classIdentifier) {
		this.classIdentifier = classIdentifier;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}