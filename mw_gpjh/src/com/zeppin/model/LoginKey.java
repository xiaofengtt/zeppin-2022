package com.zeppin.model;

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
	
    @Column(name="PSQMAPID")    
    private int psqMap;
	
	@Column(name="status", nullable = false)
	private int status;
	
	@Column(name="creattime", nullable = false)
	private Date updateTime = new Date();
	
	@Column(name="TELEPHONE")
	private String telephone;
	
	@Column(name="TRAINEEID")
	private String traineeId;
	
	@Column(name="PSQSTATUS")
	private int psgStatus;

	public int getPsgStatus() {
		return psgStatus;
	}

	public void setPsgStatus(int psgStatus) {
		this.psgStatus = psgStatus;
	}

	public String getTraineeId() {
		return traineeId;
	}

	public void setTraineeId(String traineeId) {
		this.traineeId = traineeId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getStatus() {
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

	public int getProject() {
		return psqMap;
	}

	public void setProject(int project) {
		this.psqMap = project;
	}

	
}
