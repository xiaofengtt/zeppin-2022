package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PrTchTraineeCourse entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrTchTraineeCourse extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private String fkTraineeId;
	private String fkCourseId;
	private String onlineTime;
	private Date lastDate;

	// Constructors

	/** default constructor */
	public PrTchTraineeCourse() {
	}

	/** full constructor */
	public PrTchTraineeCourse(String fkTraineeId, String fkCourseId,
			String onlineTime, Date lastDate) {
		this.fkTraineeId = fkTraineeId;
		this.fkCourseId = fkCourseId;
		this.onlineTime = onlineTime;
		this.lastDate = lastDate;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFkTraineeId() {
		return this.fkTraineeId;
	}

	public void setFkTraineeId(String fkTraineeId) {
		this.fkTraineeId = fkTraineeId;
	}

	public String getFkCourseId() {
		return this.fkCourseId;
	}

	public void setFkCourseId(String fkCourseId) {
		this.fkCourseId = fkCourseId;
	}

	public String getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Date getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

}