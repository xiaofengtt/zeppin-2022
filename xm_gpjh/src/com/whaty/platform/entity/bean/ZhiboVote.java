package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * ZhiboVote entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ZhiboVote extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String ip;
	private String result;
	private Date dataDate;

	// Constructors

	/** default constructor */
	public ZhiboVote() {
	}

	/** full constructor */
	public ZhiboVote(String ip, String result, Date dataDate) {
		this.ip = ip;
		this.result = result;
		this.dataDate = dataDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

}