package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeBzzTeachingLiveroom entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzTeachingLiveroom extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Long roomId;
	private Date beginDate;
	private Date endDate;
	private String note;
	private Date dataDate;
	private String fkSsouserId;
	private String isopen;

	// Constructors

	/** default constructor */
	public PeBzzTeachingLiveroom() {
	}

	/** full constructor */
	public PeBzzTeachingLiveroom(String name, Long roomId, Date beginDate,
			Date endDate, String note, Date dataDate, String fkSsouserId,
			String isopen) {
		this.name = name;
		this.roomId = roomId;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.note = note;
		this.dataDate = dataDate;
		this.fkSsouserId = fkSsouserId;
		this.isopen = isopen;
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

	public Long getRoomId() {
		return this.roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDataDate() {
		return this.dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public String getFkSsouserId() {
		return this.fkSsouserId;
	}

	public void setFkSsouserId(String fkSsouserId) {
		this.fkSsouserId = fkSsouserId;
	}

	public String getIsopen() {
		return this.isopen;
	}

	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}

}