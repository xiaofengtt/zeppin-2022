package com.product.worldpay.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class StatisticsChannel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4321896512847199955L;
	
    private String uuid;
	private String dailyDate;
	private String channel;
	private String channelAccount;
	private String type;
	private String currency;
    private BigDecimal totalAmount;
    private BigDecimal poundage;
    private BigDecimal amount;
    private Timestamp createtime;
    
    public class StatisticsCompanyType{
    	public final static String WITHDRAW = "withdraw";
    	public final static String RECHARGE = "recharge";
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}