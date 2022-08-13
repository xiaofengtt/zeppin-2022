package com.whaty.platform.entity.bean;

/**
 * PeBzzTchCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeBzzTchCourse extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String code;
	private String flagIsvalid;
	private String flagCoursetype;
	private Long time;
	private String teacher;
	private String teacherNote;
	private String note;
	private String flagCoursecategory;
	private Long suqnum;
	private String fkTeachTeacherId;
	private String fkAnsTeacherId;

	// Constructors

	/** default constructor */
	public PeBzzTchCourse() {
	}

	/** minimal constructor */
	public PeBzzTchCourse(String name, String code) {
		this.name = name;
		this.code = code;
	}

	/** full constructor */
	public PeBzzTchCourse(String name, String code, String flagIsvalid,
			String flagCoursetype, Long time, String teacher,
			String teacherNote, String note, String flagCoursecategory,
			Long suqnum, String fkTeachTeacherId, String fkAnsTeacherId) {
		this.name = name;
		this.code = code;
		this.flagIsvalid = flagIsvalid;
		this.flagCoursetype = flagCoursetype;
		this.time = time;
		this.teacher = teacher;
		this.teacherNote = teacherNote;
		this.note = note;
		this.flagCoursecategory = flagCoursecategory;
		this.suqnum = suqnum;
		this.fkTeachTeacherId = fkTeachTeacherId;
		this.fkAnsTeacherId = fkAnsTeacherId;
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

	public String getFlagCoursetype() {
		return this.flagCoursetype;
	}

	public void setFlagCoursetype(String flagCoursetype) {
		this.flagCoursetype = flagCoursetype;
	}

	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getTeacher() {
		return this.teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getTeacherNote() {
		return this.teacherNote;
	}

	public void setTeacherNote(String teacherNote) {
		this.teacherNote = teacherNote;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getFlagCoursecategory() {
		return this.flagCoursecategory;
	}

	public void setFlagCoursecategory(String flagCoursecategory) {
		this.flagCoursecategory = flagCoursecategory;
	}

	public Long getSuqnum() {
		return this.suqnum;
	}

	public void setSuqnum(Long suqnum) {
		this.suqnum = suqnum;
	}

	public String getFkTeachTeacherId() {
		return this.fkTeachTeacherId;
	}

	public void setFkTeachTeacherId(String fkTeachTeacherId) {
		this.fkTeachTeacherId = fkTeachTeacherId;
	}

	public String getFkAnsTeacherId() {
		return this.fkAnsTeacherId;
	}

	public void setFkAnsTeacherId(String fkAnsTeacherId) {
		this.fkAnsTeacherId = fkAnsTeacherId;
	}

}