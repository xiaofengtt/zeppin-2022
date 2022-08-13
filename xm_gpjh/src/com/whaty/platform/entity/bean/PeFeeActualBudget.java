package com.whaty.platform.entity.bean;

import java.util.Date;

/**
 * PeFeeActualBudget entity. @author MyEclipse Persistence Tools
 */

public class PeFeeActualBudget extends
		com.whaty.platform.entity.bean.AbstractBean implements
		java.io.Serializable {

	// Fields

	private String id;
	private PeUnit peUnit;
	private PeProApplyno peProApplyno;
	private PeFeeActualBudgetDetail peFeeActualBudgetDetail;
	private Date trainingDate;
	private String trainingSpace;
	private String payeeName;
	private String openingBank;
	private String accountNumber;
	private String contactInfo;
	private String personCount;
	private String unitName;
	private Date inputDate;
	private String projectDirector;
	
	// Constructors

	/** default constructor */
	public PeFeeActualBudget() {
	}

	/** minimal constructor */
	public PeFeeActualBudget(PeFeeActualBudgetDetail peFeeActualBudgetDetail) {
		this.peFeeActualBudgetDetail = peFeeActualBudgetDetail;
	}

	/** full constructor */
	public PeFeeActualBudget(PeUnit peUnit, PeProApplyno peProApplyno,
			PeFeeActualBudgetDetail peFeeActualBudgetDetail, Date trainingDate,
			String trainingSpace, String payeeName, String openingBank,
			String accountNumber, String contactInfo, String personCount,
			String unitName, Date inputDate, String projectDirector) {
		this.peUnit = peUnit;
		this.peProApplyno = peProApplyno;
		this.peFeeActualBudgetDetail = peFeeActualBudgetDetail;
		this.trainingDate = trainingDate;
		this.trainingSpace = trainingSpace;
		this.payeeName = payeeName;
		this.openingBank = openingBank;
		this.accountNumber = accountNumber;
		this.contactInfo = contactInfo;
		this.personCount = personCount;
		this.unitName = unitName;
		this.inputDate = inputDate;
		this.projectDirector = projectDirector;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeUnit getPeUnit() {
		return this.peUnit;
	}

	public void setPeUnit(PeUnit peUnit) {
		this.peUnit = peUnit;
	}

	public PeProApplyno getPeProApplyno() {
		return this.peProApplyno;
	}

	public void setPeProApplyno(PeProApplyno peProApplyno) {
		this.peProApplyno = peProApplyno;
	}

	public PeFeeActualBudgetDetail getPeFeeActualBudgetDetail() {
		return this.peFeeActualBudgetDetail;
	}

	public void setPeFeeActualBudgetDetail(
			PeFeeActualBudgetDetail peFeeActualBudgetDetail) {
		this.peFeeActualBudgetDetail = peFeeActualBudgetDetail;
	}

	public Date getTrainingDate() {
		return this.trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	public String getTrainingSpace() {
		return this.trainingSpace;
	}

	public void setTrainingSpace(String trainingSpace) {
		this.trainingSpace = trainingSpace;
	}

	public String getPayeeName() {
		return this.payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getOpeningBank() {
		return this.openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getPersonCount() {
		return this.personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getProjectDirector() {
		return projectDirector;
	}

	public void setProjectDirector(String projectDirector) {
		this.projectDirector = projectDirector;
	}

}