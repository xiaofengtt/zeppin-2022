package com.product.worldpay.vo.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.product.worldpay.entity.CompanyChannel;

public class CompanyChannelVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6841330662558009410L;
	
	private String uuid;
	private String company;
	private String companyName;
	private String channel;
	private String channelName;
	private String currency;
	private List<String> companyChannelAccountList;
    private BigDecimal poundage;
    private BigDecimal poundageRate;
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal balance;
    private BigDecimal balanceLock;
    private String type;
    private String status;
    private Timestamp createtime;
	
	public CompanyChannelVO() {
		super();
	}
	
	public CompanyChannelVO (CompanyChannel cc) {
		this.uuid = cc.getUuid();
		this.company = cc.getCompany();
		this.channel = cc.getChannel();
		this.currency = cc.getCurrency();
		this.poundage = cc.getPoundage();
		this.poundageRate = cc.getPoundageRate();
		this.max = cc.getMax();
		this.min = cc.getMin();
		this.balance = cc.getBalance();
		this.balanceLock = cc.getBalanceLock();
		this.type = cc.getType();
		this.status = cc.getStatus();
		this.createtime = cc.getCreatetime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<String> getCompanyChannelAccountList() {
		return companyChannelAccountList;
	}

	public void setCompanyChannelAccountList(List<String> companyChannelAccountList) {
		this.companyChannelAccountList = companyChannelAccountList;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public BigDecimal getPoundageRate() {
		return poundageRate;
	}

	public void setPoundageRate(BigDecimal poundageRate) {
		this.poundageRate = poundageRate;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}