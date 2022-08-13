package com.gpjh.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "PE_PRO_APPLYNO")
@Repository("projectAppNo")
public class ProjectAppNo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private String id;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "YEAR")
	private String year;
	
	@Column(name = "FK_PROGRAM_TYPE")
	private String projectType;
	
	@Column(name = "REPLY_BOOK")
	private String reply_book;
	
	@Column(name = "FK_PROGRAM_STATUS")
	private String progam_status;
	
	@Column(name = "DEADLINE")
	private Date deadLine;
	
	@Column(name = "FK_PROVINCE_CHECK")
	private String provinceCheck;

	@Column(name = "NOTE")
	private String note;
	
	@Column(name = "FEE_STANDARD")
	private Double standard;
	
	@Column(name = "LIMIT")
	private int limit;
	
	@Column(name = "CLASS_HOUR")
	private int classHour;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getReply_book() {
		return reply_book;
	}

	public void setReply_book(String reply_book) {
		this.reply_book = reply_book;
	}

	public String getProgam_status() {
		return progam_status;
	}

	public void setProgam_status(String progam_status) {
		this.progam_status = progam_status;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public String getProvinceCheck() {
		return provinceCheck;
	}

	public void setProvinceCheck(String provinceCheck) {
		this.provinceCheck = provinceCheck;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getStandard() {
		return standard;
	}

	public void setStandard(Double standard) {
		this.standard = standard;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getClassHour() {
		return classHour;
	}

	public void setClassHour(int classHour) {
		this.classHour = classHour;
	}

}
