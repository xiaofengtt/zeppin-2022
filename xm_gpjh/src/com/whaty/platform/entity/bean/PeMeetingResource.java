package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeMeetingResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeMeetingResource extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private PeUnit peUnit;
	private PeMeeting peMeeting;
	private PeManager peManager;
	private Date uploadDate;
	private String note;
	private String meetingresource;

	// Constructors

	/** default constructor */
	public PeMeetingResource() {
	}

	/** full constructor */
	public PeMeetingResource(PeUnit peUnit, PeMeeting peMeeting,
			PeManager peManager, Date uploadDate, String note,String name,
			String meetingresource) {
		this.peUnit = peUnit;
		this.peMeeting = peMeeting;
		this.peManager = peManager;
		this.uploadDate = uploadDate;
		this.note = note;
		this.name = name;
		this.meetingresource = meetingresource;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public PeMeeting getPeMeeting() {
		return this.peMeeting;
	}

	public void setPeMeeting(PeMeeting peMeeting) {
		this.peMeeting = peMeeting;
	}

	public PeManager getPeManager() {
		return this.peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMeetingresource() {
		return this.meetingresource;
	}

	public void setMeetingresource(String meetingresource) {
		this.meetingresource = meetingresource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}