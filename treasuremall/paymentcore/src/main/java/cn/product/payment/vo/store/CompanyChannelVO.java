package cn.product.payment.vo.store;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.product.payment.entity.CompanyChannel;

public class CompanyChannelVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -584685969684050666L;
	
	private String uuid;
	private String company;
	private String channel;
	private String channelName;
    private BigDecimal poundage;
    private BigDecimal poundageRate;
    private BigDecimal max;
    private BigDecimal min;
    private String type;
    private String status;
	
	public CompanyChannelVO() {
		super();
	}
	
	public CompanyChannelVO (CompanyChannel cc) {
		this.uuid = cc.getUuid();
		this.company = cc.getCompany();
		this.channel = cc.getChannel();
		this.poundage = cc.getPoundage();
		this.poundageRate = cc.getPoundageRate();
		this.max = cc.getMax();
		this.min = cc.getMin();
		this.type = cc.getType();
		this.status = cc.getStatus();
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
}