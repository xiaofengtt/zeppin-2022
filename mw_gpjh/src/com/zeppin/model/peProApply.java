package com.zeppin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "PE_PRO_APPLY")
@Repository("peProApply")
public class peProApply implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	@Column(name = "FK_UNIT")
	private String unitId;

	@Column(name = "FK_SUBJECT")
	private String subjectId;

	@Column(name = "FK_CHECK_RESULT_PROVINCE")
	private String checkResultProvince = "402880962a7d9161012a7d9de2cd0008";

	@Column(name = "FK_CHECK_NATIONAL")
	private String checkNational = "402880962a7d9161012a7da071d5000c";

	@Column(name = "FK_CHECK_FIRST")
	private String checkFirst = "402880962a8ea45a012a8eae4255000b";

	@Column(name = "FK_CHECK_FINAL")
	private String checkFinal = "402880962a8ea45a012a8eaa43520003";

	@Column(name = "DECLARE_DATE")
	private Date declareDate = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getCheckResultProvince() {
		return checkResultProvince;
	}

	public void setCheckResultProvince(String checkResultProvince) {
		this.checkResultProvince = checkResultProvince;
	}

	public String getCheckNational() {
		return checkNational;
	}

	public void setCheckNational(String checkNational) {
		this.checkNational = checkNational;
	}

	public String getCheckFirst() {
		return checkFirst;
	}

	public void setCheckFirst(String checkFirst) {
		this.checkFirst = checkFirst;
	}

	public String getCheckFinal() {
		return checkFinal;
	}

	public void setCheckFinal(String checkFinal) {
		this.checkFinal = checkFinal;
	}

	public Date getDeclareDate() {
		return declareDate;
	}

	public void setDeclareDate(Date declareDate) {
		this.declareDate = declareDate;
	}

	public String getFkApplyNo() {
		return fkApplyNo;
	}

	public void setFkApplyNo(String fkApplyNo) {
		this.fkApplyNo = fkApplyNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "FK_APPLYNO")
	private String fkApplyNo;

}
