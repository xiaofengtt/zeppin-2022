package cn.product.payment.vo.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.payment.entity.CompanyAccountHistory;

public class CompanyAccountHistoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7956743776866239865L;
	
	private String uuid;
	private String channel;
	private String channelName;
	private String channelCode;
	private String channelAccount;
	private String channelAccountName;
	private String company;
	private String companyName;
	private String companyCode;
	private String companyChannel;
	private String type;
	private String orderInfo;
    private String orderNum;
    private BigDecimal poundage;
    private BigDecimal amount;
    private BigDecimal balance;
    private String companyOrderNum;
	private String companyData;
    private Timestamp submittime;
    private Timestamp createtime;
	
	public CompanyAccountHistoryVO() {
		super();
	}
	
	public CompanyAccountHistoryVO (CompanyAccountHistory cah) {
		this.uuid = cah.getUuid();
		this.channel = cah.getChannel();
		this.channelAccount = cah.getChannelAccount();
		this.company = cah.getCompany();
		this.companyChannel = cah.getCompanyChannel();
		this.type = cah.getType();
		this.orderInfo = cah.getOrderInfo();
		this.orderNum = cah.getOrderNum();
		this.poundage = cah.getPoundage();
		this.amount = cah.getAmount();
		this.balance = cah.getBalance();
		this.companyOrderNum = cah.getCompanyOrderNum();
		this.companyData = cah.getCompanyData();
		this.submittime = cah.getSubmittime();
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

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyChannel() {
		return companyChannel;
	}

	public void setCompanyChannel(String companyChannel) {
		this.companyChannel = companyChannel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Timestamp getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Timestamp submittime) {
		this.submittime = submittime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}