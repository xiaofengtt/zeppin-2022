package com.zeppin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

/*
 * 项目表
 * 
 */

@Entity
@Table(name = "PE_PRO_APPLYNO")
@Repository("peProApplyNO")
public class peProApplyNo implements java.io.Serializable {

	/**
	 * 
	 */
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
	private String fkProgramType;

	@Column(name = "REPLY_BOOK")
	private String replyBook;

	@Column(name = "FK_PROGRAM_STATUS")
	private String fkProgramStatus;

	@Column(name = "DEADLINE")
	private Date deadLine = new Date();

	@Column(name = "FK_PROVINCE_CHECK")
	private String fkProvinceCheck;

	@Column(name = "NOTE")
	private String note;

	@Column(name = "FEE_STANDARD")
	private float feeStandard;

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

	public String getFkProgramType() {
		return fkProgramType;
	}

	public void setFkProgramType(String fkProgramType) {
		this.fkProgramType = fkProgramType;
	}

	public String getReplyBook() {
		return replyBook;
	}

	public void setReplyBook(String replyBook) {
		this.replyBook = replyBook;
	}

	public String getFkProgramStatus() {
		return fkProgramStatus;
	}

	public void setFkProgramStatus(String fkProgramStatus) {
		this.fkProgramStatus = fkProgramStatus;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public String getFkProvinceCheck() {
		return fkProvinceCheck;
	}

	public void setFkProvinceCheck(String fkProvinceCheck) {
		this.fkProvinceCheck = fkProvinceCheck;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getFeeStandard() {
		return feeStandard;
	}

	public void setFeeStandard(float feeStandard) {
		this.feeStandard = feeStandard;
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
