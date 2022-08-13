package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeTrainee entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PeTraineeAdu extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {

	// Fields

	private String id;
	private SsoUser ssoUser;
	private Date createTime;
	
	private PeTrainee traineeByFkOldTrainee;
	private PeTrainee traineeByFkNewTrainee;


	// Constructors

	/** default constructor */
	public PeTraineeAdu() {
	}

	/** full constructor */
	public PeTraineeAdu(String id, SsoUser ssoUser, Date createTime,
			PeTrainee traineeByFkOldTrainee, PeTrainee traineeByFkNewTrainee) {
		super();
		this.id = id;
		this.ssoUser = ssoUser;
		this.createTime = createTime;
		this.traineeByFkOldTrainee = traineeByFkOldTrainee;
		this.traineeByFkNewTrainee = traineeByFkNewTrainee;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SsoUser getSsoUser() {
		return this.ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PeTrainee getTraineeByFkOldTrainee() {
		return traineeByFkOldTrainee;
	}

	public void setTraineeByFkOldTrainee(PeTrainee traineeByFkOldTrainee) {
		this.traineeByFkOldTrainee = traineeByFkOldTrainee;
	}

	public PeTrainee getTraineeByFkNewTrainee() {
		return traineeByFkNewTrainee;
	}

	public void setTraineeByFkNewTrainee(PeTrainee traineeByFkNewTrainee) {
		this.traineeByFkNewTrainee = traineeByFkNewTrainee;
	}

	
}