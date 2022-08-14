package com.product.worldpay.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ChannelAccountHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4935344094551169413L;
	
    private String uuid;
	private String channel;
	private String channelAccount;
	private String company;
	private String type;
	private String currency;
	private String orderInfo;
    private String orderNum;
    private BigDecimal poundage;
    private BigDecimal amount;
    private BigDecimal balance;
    private Timestamp createtime;
    
    public class ChannelAccountHistoryType{
    	public final static String USER_WITHDRAW = "user_withdraw";
    	public final static String USER_RECHARGE = "user_recharge";
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

	public String getChannelAccount() {
		return channelAccount;
	}

	public void setChannelAccount(String channelAccount) {
		this.channelAccount = channelAccount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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