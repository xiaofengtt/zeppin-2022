package com.whaty.platform.vote.basic;

import java.util.Date;

import com.whaty.platform.Items;

public abstract class VoteSuggest implements Items {
	
	private String id;
	
	private String votePaperId;
	
	private String note;
	
	private Date found_date;
	
	private String ip;
	
	private boolean active;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getVotePaperId() {
		return votePaperId;
	}

	public void setVotePaperId(String votePaperId) {
		this.votePaperId = votePaperId;
	}

	/**
	 * @return the found_date
	 */
	public Date getFound_date() {
		return found_date;
	}

	/**
	 * @param found_date the found_date to set
	 */
	public void setFound_date(Date found_date) {
		this.found_date = found_date;
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
