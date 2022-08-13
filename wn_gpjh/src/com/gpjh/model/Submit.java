package com.gpjh.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Repository;

@Entity
@Table(name="submit")
@Repository("submit")
@SequenceGenerator(name = "EMP_SUBMIT", sequenceName = "EMP_SUBMIT", allocationSize = 1)
public class Submit implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SUBMIT")
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
    @ManyToOne
    @JoinColumn(name="loginkey")
    private LoginKey loginkey;
    
    @ManyToOne
    @JoinColumn(name="psq")
    private Paper paper;
    
	@Column(name = "creattime", unique = true, nullable = false)
	private Date creatTime = new Date();

	@Column(name = "ip", unique = true, nullable = false)
	private String ip;

	@Transient
	private String project;
	
	@Transient
	private String subject;
	
	@Transient
	private String unit;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LoginKey getLoginkey() {
		return loginkey;
	}

	public void setLoginkey(LoginKey loginkey) {
		this.loginkey = loginkey;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
