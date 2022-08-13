package com.gpjh.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name="project")
@Repository("project")
public class Project implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	
	@Column(name = "year", unique = true, nullable = false)
	private String year;
	
	@Column(name = "projecttype", unique = true, nullable = false)
	private int projectType;
	
	@Column(name = "traintype", unique = true, nullable = false)
	private int trainType;
	
	@Column(name = "subjectmax", unique = true, nullable = false)
	private int subjectMax;
	
	@Column(name = "timeup", unique = true, nullable = false)
	private Date timeUp = new Date();
	
	@Column(name = "fund", unique = true, nullable = false)
	private float fund;
	
	@Column(name = "classhour", unique = true, nullable = false)
	private String classHour;
	
	@Column(name = "status", unique = true, nullable = false)
	private int status;
	
	@Column(name = "remark", unique = true, nullable = false)
	private String remark;	
	
	@Column(name = "creator", unique = true, nullable = false)
	private int creator;
	
	@Column(name = "creattime", unique = true, nullable = false)
	private Date creattime = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public int getTrainType() {
		return trainType;
	}

	public void setTrainType(int trainType) {
		this.trainType = trainType;
	}

	public int getSubjectMax() {
		return subjectMax;
	}

	public void setSubjectMax(int subjectMax) {
		this.subjectMax = subjectMax;
	}

	public Date getTimeUp() {
		return timeUp;
	}

	public void setTimeUp(Date timeUp) {
		this.timeUp = timeUp;
	}

	public float getFund() {
		return fund;
	}

	public void setFund(float fund) {
		this.fund = fund;
	}

	public String getClassHour() {
		return classHour;
	}

	public void setClassHour(String classHour) {
		this.classHour = classHour;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	public Date getCreattime() {
		return creattime;
	}

	public void setCreattime(Date creattime) {
		this.creattime = creattime;
	}
}
