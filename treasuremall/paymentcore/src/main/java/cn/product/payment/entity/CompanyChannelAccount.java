package cn.product.payment.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class CompanyChannelAccount implements Serializable{
	
	/**
	 * 商户交易渠道账户
	 */
	private static final long serialVersionUID = -3969985016616087112L;
	
	@Id
    private String uuid;
	private String company;
	private String companyChannel;
	private String channel;
	private String channelAccount;
    
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

	public String getCompanyChannel() {
		return companyChannel;
	}

	public void setCompanyChannel(String companyChannel) {
		this.companyChannel = companyChannel;
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
}