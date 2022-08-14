package com.product.worldpay.vo.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.product.worldpay.entity.ChannelAccountHistory;

public class ChannelAccountHistoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1205792520470164767L;
	
	private String uuid;
	private String channel;
	private String channelName;
	private String channelAccount;
	private String channelAccountName;
	private String company;
	private String companyName;
	private String type;
	private String currency;
	private String orderInfo;
    private String orderNum;
    private BigDecimal poundage;
    private BigDecimal amount;
    private BigDecimal balance;
    private Timestamp createtime;
	
	public ChannelAccountHistoryVO() {
		super();
	}
	
	public ChannelAccountHistoryVO (ChannelAccountHistory cah) {
		this.uuid = cah.getUuid();
		this.channel = cah.getChannel();
		this.channelAccount = cah.getChannelAccount();
		this.company = cah.getCompany();
		this.type = cah.getType();
		this.currency = cah.getCurrency();
		this.orderInfo = cah.getOrderInfo();
		this.orderNum = cah.getOrderNum();
		this.poundage = cah.getPoundage();
		this.amount = cah.getAmount();
		this.balance = cah.getBalance();
		this.createtime = cah.getCreatetime();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}