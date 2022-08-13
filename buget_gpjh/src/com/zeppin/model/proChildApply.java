package com.zeppin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

@Entity
@Table(name = "PROCHILDAPPLY")
@Repository("proChildApply")
public class proChildApply implements java.io.Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	@Column(name = "FK_UNIT")
	private String unitId;

	@Column(name = "FK_SUBJECT")
	private String subjectId;

	@Column(name = "FK_APPLYNO")
	private String fkApplyNo;
	
	@Column(name = "DECLARATION")
	private String declaration;
	
	@Column(name = "SCHEME")
	private String scheme;
	
	@Column(name = "SELECTFLAG")
	private int selectFlag;
	
	@Column(name = "PINFLAG")
	private int pinfenflag;
	
	@Column(name = "DATETIME")
	private Date dateTime = new Date();
	
	@Column(name = "PINSHENG")
	private int pinsheng;

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

	public String getFkApplyNo() {
		return fkApplyNo;
	}

	public void setFkApplyNo(String fkApplyNo) {
		this.fkApplyNo = fkApplyNo;
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public int getSelectFlag() {
		return selectFlag;
	}

	public void setSelectFlag(int selectFlag) {
		this.selectFlag = selectFlag;
	}

	public int getPinfenflag() {
		return pinfenflag;
	}

	public void setPinfenflag(int pinfenflag) {
		this.pinfenflag = pinfenflag;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getPinsheng() {
		return pinsheng;
	}

	public void setPinsheng(int pinsheng) {
		this.pinsheng = pinsheng;
	}
	
	
	
}
