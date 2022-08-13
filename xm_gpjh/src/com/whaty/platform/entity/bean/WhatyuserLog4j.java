package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * WhatyuserLog4j entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class WhatyuserLog4j extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String userid;
	private Date operateTime;
	private String behavior;
	private String status;
	private String notes;
	private String logtype;
	private String priority;
	private String ip;

	// Constructors

	/** default constructor */
	public WhatyuserLog4j() {
	}

	/** full constructor */
	public WhatyuserLog4j(String userid, Date operateTime, String behavior,
			String status, String notes, String logtype, String priority,
			String ip) {
		this.userid = userid;
		this.operateTime = operateTime;
		this.behavior = behavior;
		this.status = status;
		this.notes = notes;
		this.logtype = logtype;
		this.priority = priority;
		this.ip = ip;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getBehavior() {
		return this.behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLogtype() {
		return this.logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}