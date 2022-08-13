package com.gpjh.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name="loginkey")
@Repository("loginKey")
public class LoginKey implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
    @ManyToOne
    @JoinColumn(name="project")
    private Project project;

    @ManyToOne
    @JoinColumn(name="trainingunit")
    private TrainingUnit trainingUnit;
    
    @ManyToOne
    @JoinColumn(name="subject")
    private Subject subject;
	
	@Column(name="plansum", nullable = false)
	private int planSum;
	
	@Column(name="realsum", nullable = false)
	private int realSum;
	
	@Column(name="status", nullable = false)
	private short status;
	
	@Column(name="creattime", nullable = false)
	private Date updateTime = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPlanSum() {
		return planSum;
	}

	public void setPlanSum(int planSum) {
		this.planSum = planSum;
	}

	public int getRealSum() {
		return realSum;
	}

	public void setRealSum(int realSum) {
		this.realSum = realSum;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public TrainingUnit getTrainingUnit() {
		return trainingUnit;
	}

	public void setTrainingUnit(TrainingUnit trainingUnit) {
		this.trainingUnit = trainingUnit;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
