package com.zeppin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "PROCHILDAPPLYNO")
@Repository("proChildApplyNo")
public class proChildApplyNo implements java.io.Serializable {

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

	@Column(name = "FK_PROVINCE_CHECK")
	private String fkProvinceCheck;

	@Column(name = "PROVINCEID")
	private String provice;

	@Column(name = "PARENTID")
	private String parentid;

	@Column(name = "PARENTNAME")
	private String parentName;

	@Column(name = "SELECTFLAG")
	private int selectFlag;

	@Column(name = "PLEVEL")
	private int plevel;
	
	@Column(name = "DATETIME")
	private Date dateTime = new Date();

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

	public String getFkProvinceCheck() {
		return fkProvinceCheck;
	}

	public void setFkProvinceCheck(String fkProvinceCheck) {
		this.fkProvinceCheck = fkProvinceCheck;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getSelectFlag() {
		return selectFlag;
	}

	public void setSelectFlag(int selectFlag) {
		this.selectFlag = selectFlag;
	}

	public int getPlevel() {
		return plevel;
	}

	public void setPlevel(int plevel) {
		this.plevel = plevel;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	

}
