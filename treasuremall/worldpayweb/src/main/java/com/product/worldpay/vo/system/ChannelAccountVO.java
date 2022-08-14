package com.product.worldpay.vo.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import com.product.worldpay.entity.ChannelAccount;
import com.product.worldpay.util.JSONUtils;

public class ChannelAccountVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2495698398835462680L;
	
	private String uuid;
	private String channel;
	private String channelName;
	private String currency;
	private String name;
	private String accountNum;
	private String transferUrl;
    private String data;
    private Map<String, Object> dataMap;
    private BigDecimal balance;
    private BigDecimal poundage;
    private BigDecimal poundageRate;
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal dailyMax;
    private BigDecimal totalMax;
    private String type;
    private String status;
    private Timestamp createtime;
	
	public ChannelAccountVO() {
		super();
	}
	
	public ChannelAccountVO (ChannelAccount ca) {
		this.uuid = ca.getUuid();
		this.channel = ca.getChannel();
		this.currency = ca.getCurrency();
		this.name = ca.getName();
		this.accountNum = ca.getAccountNum();
		this.transferUrl = ca.getTransferUrl();
		this.data = ca.getData();
		this.dataMap = JSONUtils.json2map(ca.getData());
		this.balance = ca.getBalance();
		this.poundage = ca.getPoundage();
		this.poundageRate = ca.getPoundageRate();
		this.max = ca.getMax();
		this.min = ca.getMin();
		this.dailyMax = ca.getDailyMax();
		this.totalMax = ca.getTotalMax();
		this.type = ca.getType();
		this.status = ca.getStatus();
		this.createtime = ca.getCreatetime();
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getTransferUrl() {
		return transferUrl;
	}

	public void setTransferUrl(String transferUrl) {
		this.transferUrl = transferUrl;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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

	public BigDecimal getDailyMax() {
		return dailyMax;
	}

	public void setDailyMax(BigDecimal dailyMax) {
		this.dailyMax = dailyMax;
	}

	public BigDecimal getTotalMax() {
		return totalMax;
	}

	public void setTotalMax(BigDecimal totalMax) {
		this.totalMax = totalMax;
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