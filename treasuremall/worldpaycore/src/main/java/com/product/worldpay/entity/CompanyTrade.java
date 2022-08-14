package com.product.worldpay.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class CompanyTrade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -379438912402185895L;
	
	@Id
    private String uuid;
	private String type;
	private String company;
	private String companyChannel;
	private String companyBankcard;
	private String currency;
	private String orderNum;
    private BigDecimal totalAmount;
    private BigDecimal poundage;
    private BigDecimal amount;
    private String data;
    private String operator;
    private Timestamp operattime;
    private String status;
    private String proof;
    private String failReason;
    private Timestamp createtime;
    
    public class CompanyTradeStatus{
    	public final static String CHECKING = "checking";
    	public final static String CHECKED = "checked";
    	public final static String FAIL = "fail";
    	public final static String CLOSE = "close";
    	public final static String SUCCESS = "success";
    }
    
    public class CompanyTradeType{
    	public final static String RECHARGE = "recharge";
    	public final static String WITHDRAW = "withdraw";
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

	public String getCompanyChannel() {
		return companyChannel;
	}

	public void setCompanyChannel(String companyChannel) {
		this.companyChannel = companyChannel;
	}

	public String getCompanyBankcard() {
		return companyBankcard;
	}

	public void setCompanyBankcard(String companyBankcard) {
		this.companyBankcard = companyBankcard;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
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