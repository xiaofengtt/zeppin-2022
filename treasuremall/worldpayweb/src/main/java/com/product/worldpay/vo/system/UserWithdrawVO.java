package com.product.worldpay.vo.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import com.product.worldpay.entity.UserWithdraw;
import com.product.worldpay.util.JSONUtils;

public class UserWithdrawVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7484887943661961389L;
	
	private String uuid;
	private String channel;
	private String channelName;
	private String channelCode;
	private String channelAccount;
	private String channelAccountName;
	private String channelAccountNum;
	private String company;
	private String companyName;
	private String companyCode;
	private String currency;
	private BigDecimal balance;
    private BigDecimal balanceLock;
	private String companyChannel;
    private String orderNum;
    private BigDecimal totalAmount;
    private BigDecimal poundage;
    private BigDecimal amount;
    private String companyOrderNum;
	private String companyData;
	private String companyNotifyUrl;
	private String transData;
	private Map<String, Object> transDataMap;
	private String operator;
	private String operatorName;
    private Timestamp operattime;
    private String proof;
    private String status;
    private String failReason;
    private Timestamp createtime;
	
	public UserWithdrawVO() {
		super();
	}
	
	public UserWithdrawVO (UserWithdraw uw) {
		this.uuid = uw.getUuid();
		this.channel = uw.getChannel();
		this.channelAccount = uw.getChannelAccount();
		this.company = uw.getCompany();
		this.companyChannel = uw.getCompanyChannel();
		this.currency = uw.getCurrency();
		this.orderNum = uw.getOrderNum();
		this.totalAmount = uw.getTotalAmount();
		this.poundage = uw.getPoundage();
		this.amount = uw.getAmount();
		this.companyOrderNum = uw.getCompanyOrderNum();
		this.companyData = uw.getCompanyData();
		this.companyNotifyUrl = uw.getCompanyNotifyUrl();
		this.transData = uw.getTransData();
		if(transData != null){
			this.transDataMap = JSONUtils.json2map(uw.getTransData());
		}
		this.operator = uw.getOperator();
		this.operattime = uw.getOperattime();
		this.proof = uw.getProof();
		this.status = uw.getStatus();
		this.failReason = uw.getFailReason();
		this.createtime = uw.getCreatetime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelAccount() {
		return channelAccount;
	}

	public void setChannelAccount(String channelAccount) {
		this.channelAccount = channelAccount;
	}

	public String getChannelAccountName() {
		return channelAccountName;
	}

	public void setChannelAccountName(String channelAccountName) {
		this.channelAccountName = channelAccountName;
	}

	public String getChannelAccountNum() {
		return channelAccountNum;
	}

	public void setChannelAccountNum(String channelAccountNum) {
		this.channelAccountNum = channelAccountNum;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceLock() {
		return balanceLock;
	}

	public void setBalanceLock(BigDecimal balanceLock) {
		this.balanceLock = balanceLock;
	}

	public String getCompanyChannel() {
		return companyChannel;
	}

	public void setCompanyChannel(String companyChannel) {
		this.companyChannel = companyChannel;
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

	public String getCompanyOrderNum() {
		return companyOrderNum;
	}

	public void setCompanyOrderNum(String companyOrderNum) {
		this.companyOrderNum = companyOrderNum;
	}

	public String getCompanyData() {
		return companyData;
	}

	public void setCompanyData(String companyData) {
		this.companyData = companyData;
	}

	public String getCompanyNotifyUrl() {
		return companyNotifyUrl;
	}

	public void setCompanyNotifyUrl(String companyNotifyUrl) {
		this.companyNotifyUrl = companyNotifyUrl;
	}

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
	}

	public Map<String, Object> getTransDataMap() {
		return transDataMap;
	}

	public void setTransDataMap(Map<String, Object> transDataMap) {
		this.transDataMap = transDataMap;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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