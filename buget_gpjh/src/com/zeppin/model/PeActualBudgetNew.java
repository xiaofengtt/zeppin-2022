/**  
 * @Title: PeBudgetmp.java
 * @Package com.zeppin.model
 * @author jiangfei  
 */
package com.zeppin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "PEACTUALBUDGETNEW")
@Repository("peActualBudgetNew")
public class PeActualBudgetNew {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private String id;

	@Column(name = "FK_UNIT")
	private String fk_unit;

	@ManyToOne
	@JoinColumn(name = "FK_PRO_APPLYNO")
	private peProApplyNo fk_pro_applyno;

	@Column(name = "TRAINING_DATA")
	private String training_data;

	@Column(name = "TRAINING_SPACE")
	private String trainingSpace;

	@Column(name = "PAYEE_NAME")
	private String payeeName;

	@Column(name = "OPENING_BANK")
	private String openingBank;

	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;

	@Column(name = "COUNTACT_INFO")
	private String contactInfo;

	@ManyToOne
	@JoinColumn(name = "PE_BUDGET_DETAIL")
	private PeActualBudgetNewDetail peFeeBudgetNewDetail;

	@Column(name = "UNIT_NAME")
	private String unitName;

	@Column(name = "INPUT_DATE")
	private Date input_date = new Date();

	@Column(name = "PROJECTDIRECTOR")
	private String projectDirector;

	@Column(name = "PERSON_COUNT")
	private String personCount;

	@Column(name = "FLAG")
	private int flag;
	
	@Column(name = "AUD_DATE")
	private Date aud_date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFk_unit() {
		return fk_unit;
	}

	public void setFk_unit(String fk_unit) {
		this.fk_unit = fk_unit;
	}

	public peProApplyNo getFk_pro_applyno() {
		return fk_pro_applyno;
	}

	public void setFk_pro_applyno(peProApplyNo fk_pro_applyno) {
		this.fk_pro_applyno = fk_pro_applyno;
	}

	public String getTraining_data() {
		return training_data;
	}

	public void setTraining_data(String training_data) {
		this.training_data = training_data;
	}

	public String getTrainingSpace() {
		return trainingSpace;
	}

	public void setTrainingSpace(String trainingSpace) {
		this.trainingSpace = trainingSpace;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}


	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Date getInput_date() {
		return input_date;
	}

	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}

	public String getProjectDirector() {
		return projectDirector;
	}

	public void setProjectDirector(String projectDirector) {
		this.projectDirector = projectDirector;
	}

	public String getPersonCount() {
		return personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public PeActualBudgetNewDetail getPeFeeBudgetNewDetail() {
		return peFeeBudgetNewDetail;
	}

	public void setPeFeeBudgetNewDetail(PeActualBudgetNewDetail peFeeBudgetNewDetail) {
		this.peFeeBudgetNewDetail = peFeeBudgetNewDetail;
	}

	public Date getAud_date() {
		return aud_date;
	}

	public void setAud_date(Date aud_date) {
		this.aud_date = aud_date;
	}

}
