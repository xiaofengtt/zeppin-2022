package cn.product.payment.vo.store;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.payment.entity.CompanyTrade;

public class CompanyTradeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9094691628892112298L;
	
	private String uuid;
	private String type;
	private String company;
	private String companyName;
	private String companyCode;
	private String companyBankcard;
	private String companyBankcardBankName;
	private String companyBankcardArea;
	private String companyBankcardBranchBank;
	private String companyBankcardNum;
	private BigDecimal companyBalance;
    private BigDecimal companyBalanceLock;
    private String orderNum;
    private BigDecimal totalAmount;
    private BigDecimal poundage;
    private BigDecimal amount;
    private String data;
    private Timestamp operattime;
    private String proof;
    private String status;
    private String failReason;
    private Timestamp createtime;
	
	public CompanyTradeVO() {
		super();
	}
	
	public CompanyTradeVO (CompanyTrade ct) {
		this.uuid = ct.getUuid();
		this.type = ct.getType();
		this.company = ct.getCompany();
		this.companyBankcard = ct.getCompanyBankcard();
		this.orderNum = ct.getOrderNum();
		this.totalAmount = ct.getTotalAmount();
		this.poundage = ct.getPoundage();
		this.amount = ct.getAmount();
		this.data = ct.getData();
		this.operattime = ct.getOperattime();
		this.proof = ct.getProof();
		this.status = ct.getStatus();
		this.failReason = ct.getFailReason();
		this.createtime = ct.getCreatetime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyBankcard() {
		return companyBankcard;
	}

	public void setCompanyBankcard(String companyBankcard) {
		this.companyBankcard = companyBankcard;
	}

	public String getCompanyBankcardBankName() {
		return companyBankcardBankName;
	}

	public void setCompanyBankcardBankName(String companyBankcardBankName) {
		this.companyBankcardBankName = companyBankcardBankName;
	}

	public String getCompanyBankcardArea() {
		return companyBankcardArea;
	}

	public void setCompanyBankcardArea(String companyBankcardArea) {
		this.companyBankcardArea = companyBankcardArea;
	}

	public String getCompanyBankcardBranchBank() {
		return companyBankcardBranchBank;
	}

	public void setCompanyBankcardBranchBank(String companyBankcardBranchBank) {
		this.companyBankcardBranchBank = companyBankcardBranchBank;
	}

	public String getCompanyBankcardNum() {
		return companyBankcardNum;
	}

	public void setCompanyBankcardNum(String companyBankcardNum) {
		this.companyBankcardNum = companyBankcardNum;
	}

	public BigDecimal getCompanyBalance() {
		return companyBalance;
	}

	public void setCompanyBalance(BigDecimal companyBalance) {
		this.companyBalance = companyBalance;
	}

	public BigDecimal getCompanyBalanceLock() {
		return companyBalanceLock;
	}

	public void setCompanyBalanceLock(BigDecimal companyBalanceLock) {
		this.companyBalanceLock = companyBalanceLock;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}