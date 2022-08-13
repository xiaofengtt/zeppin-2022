package com.whaty.platform.vote.basic;

import java.util.Date;

import com.whaty.platform.vote.util.exception.VoteException;

public abstract class VoteRecord {
	
	private String id;
	
	private String paperId;
	
	private String sessionId;
	
	private String ip;
	
	private Date voteDate;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the paperId
	 */
	public String getPaperId() {
		return paperId;
	}

	/**
	 * @param paperId the paperId to set
	 */
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the voteDate
	 */
	public Date getVoteDate() {
		return voteDate;
	}

	/**
	 * @param voteDate the voteDate to set
	 */
	public void setVoteDate(Date voteDate) {
		this.voteDate = voteDate;
	}
	
	public abstract void add() throws VoteException;
	
	public abstract void delete() throws VoteException;
}
