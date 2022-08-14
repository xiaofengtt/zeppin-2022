package cn.product.payment.vo.system;

import java.io.Serializable;

import cn.product.payment.entity.CompanyChannelAccount;

public class CompanyChannelAccountVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2508940686835626531L;
	
	private String uuid;
	private String company;
	private String companyName;
	private String companyChannel;
	private String channel;
	private String channelName;
	private String channelAccount;
	private String channelAccountName;
	
	public CompanyChannelAccountVO() {
		super();
	}
	
	public CompanyChannelAccountVO (CompanyChannelAccount cca) {
		this.uuid = cca.getUuid();
		this.companyChannel = cca.getCompanyChannel();
		this.company = cca.getCompany();
		this.channel = cca.getChannel();
		this.channelAccount = cca.getChannelAccount();
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
}