package com.whaty.platform.entity.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * PeRecExamcourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeRecExamcourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String flagArrangeRoom;
	private String flagBak;
	private String note;
	private Set prRecExamCourseTimes = new HashSet(0);
	private Set prRecCourseMajorEdutypes = new HashSet(0);

	// Constructors

	/** default constructor */
	public PeRecExamcourse() {
	}

	/** minimal constructor */
	public PeRecExamcourse(String name) {
		this.name = name;
	}

	/** full constructor */
	public PeRecExamcourse(String name, String flagArrangeRoom, String flagBak,
			String note, Set prRecExamCourseTimes, Set prRecCourseMajorEdutypes) {
		this.name = name;
		this.flagArrangeRoom = flagArrangeRoom;
		this.flagBak = flagBak;
		this.note = note;
		this.prRecExamCourseTimes = prRecExamCourseTimes;
		this.prRecCourseMajorEdutypes = prRecCourseMajorEdutypes;
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

	public String getFlagArrangeRoom() {
		return this.flagArrangeRoom;
	}

	public void setFlagArrangeRoom(String flagArrangeRoom) {
		this.flagArrangeRoom = flagArrangeRoom;
	}

	public String getFlagBak() {
		return this.flagBak;
	}

	public void setFlagBak(String flagBak) {
		this.flagBak = flagBak;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Set getPrRecExamCourseTimes() {
		return this.prRecExamCourseTimes;
	}

	public void setPrRecExamCourseTimes(Set prRecExamCourseTimes) {
		this.prRecExamCourseTimes = prRecExamCourseTimes;
	}

	public Set getPrRecCourseMajorEdutypes() {
		return this.prRecCourseMajorEdutypes;
	}

	public void setPrRecCourseMajorEdutypes(Set prRecCourseMajorEdutypes) {
		this.prRecCourseMajorEdutypes = prRecCourseMajorEdutypes;
	}

}