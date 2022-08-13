package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PeMeeting entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeMeeting extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private PeManager peManager;
	private String name;
	private Date meetingDate;
	private Date createDate;
	private String place;
	private String note;
	private String receiprUnit;
	private String resourceUnit;
	private Set peMeetingResources = new HashSet(0);
	private Set prMeetPersons = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeMeeting() {
	}

	/** full constructor */
	public PeMeeting(PeManager peManager, String name, Date meetingDate,
			Date createDate, String place, String note, String receiprUnit,
			String resourceUnit, Set peMeetingResources, Set prMeetPersons) {
		this.peManager = peManager;
		this.name = name;
		this.meetingDate = meetingDate;
		this.createDate = createDate;
		this.place = place;
		this.note = note;
		this.receiprUnit = receiprUnit;
		this.resourceUnit = resourceUnit;
		this.peMeetingResources = peMeetingResources;
		this.prMeetPersons = prMeetPersons;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeManager getPeManager() {
		return this.peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getMeetingDate() {
		return this.meetingDate;
	}

	public void setMeetingDate(Date meetingDate) {
		this.meetingDate = meetingDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getReceiprUnit() {
		return this.receiprUnit;
	}

	public void setReceiprUnit(String receiprUnit) {
		this.receiprUnit = receiprUnit;
	}

	public String getResourceUnit() {
		return this.resourceUnit;
	}

	public void setResourceUnit(String resourceUnit) {
		this.resourceUnit = resourceUnit;
	}

	public Set getPeMeetingResources() {
		return this.peMeetingResources;
	}

	public void setPeMeetingResources(Set peMeetingResources) {
		this.peMeetingResources = peMeetingResources;
	}

	public Set getPrMeetPersons() {
		return this.prMeetPersons;
	}

	public void setPrMeetPersons(Set prMeetPersons) {
		this.prMeetPersons = prMeetPersons;
	}

}