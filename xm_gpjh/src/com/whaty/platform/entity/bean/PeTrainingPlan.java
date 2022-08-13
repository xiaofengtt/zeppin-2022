package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeTrainingPlan entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTrainingPlan extends com.whaty.platform.entity.bean.AbstractBean
		implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private Long daysLimit;
	private Long creditRequire;
	private String trainingType;
	private Date activeDate;
	private String version;
	private String note;
	private String spotTraining;
	private String onlineAnswer;
	private Long shamtestTimes;

	// Constructors

	/** default constructor */
	public PeTrainingPlan() {
	}

	/** full constructor */
	public PeTrainingPlan(String name, Long daysLimit, Long creditRequire,
			String trainingType, Date activeDate, String version, String note,
			String spotTraining, String onlineAnswer, Long shamtestTimes) {
		this.name = name;
		this.daysLimit = daysLimit;
		this.creditRequire = creditRequire;
		this.trainingType = trainingType;
		this.activeDate = activeDate;
		this.version = version;
		this.note = note;
		this.spotTraining = spotTraining;
		this.onlineAnswer = onlineAnswer;
		this.shamtestTimes = shamtestTimes;
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

	public Long getDaysLimit() {
		return this.daysLimit;
	}

	public void setDaysLimit(Long daysLimit) {
		this.daysLimit = daysLimit;
	}

	public Long getCreditRequire() {
		return this.creditRequire;
	}

	public void setCreditRequire(Long creditRequire) {
		this.creditRequire = creditRequire;
	}

	public String getTrainingType() {
		return this.trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public Date getActiveDate() {
		return this.activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSpotTraining() {
		return this.spotTraining;
	}

	public void setSpotTraining(String spotTraining) {
		this.spotTraining = spotTraining;
	}

	public String getOnlineAnswer() {
		return this.onlineAnswer;
	}

	public void setOnlineAnswer(String onlineAnswer) {
		this.onlineAnswer = onlineAnswer;
	}

	public Long getShamtestTimes() {
		return this.shamtestTimes;
	}

	public void setShamtestTimes(Long shamtestTimes) {
		this.shamtestTimes = shamtestTimes;
	}

}