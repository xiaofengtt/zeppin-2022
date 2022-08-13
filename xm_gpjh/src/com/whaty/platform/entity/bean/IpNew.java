package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * IpNew entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IpNew extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String ip;
	private Date dataDate;

	// Constructors

	/** default constructor */
	public IpNew() {
	}

	/** full constructor */
	public IpNew(String ip, Date dataDate) {
		this.ip = ip;
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

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

}