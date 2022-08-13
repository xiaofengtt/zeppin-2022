package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeRoomOpenTime entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeRoomOpenTime extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String flagIsvalid;
	private Date startDate;
	private Date endDate;
	private String flagBak;
	private String startTime;
	private String endTime;

	// Constructors

	/** default constructor */
	public PeRoomOpenTime() {
	}

	/** full constructor */
	public PeRoomOpenTime(String name, String code, String flagIsvalid,
			Date startDate, Date endDate, String flagBak, String startTime,
			String endTime) {
		this.name = name;
		this.code = code;
		this.flagIsvalid = flagIsvalid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.flagBak = flagBak;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFlagIsvalid() {
		return this.flagIsvalid;
	}

	public void setFlagIsvalid(String flagIsvalid) {
		this.flagIsvalid = flagIsvalid;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}