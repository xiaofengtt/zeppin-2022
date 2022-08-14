package cn.product.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class CompanyAccountHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -379438912402185895L;
	
    private String uuid;
	private String channel;
	private String channelAccount;
	private String company;
	private String companyChannel;
	private String companyBankcard;
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
    
    public class CompanyAccountHistoryType{
    	public final static String USER_WITHDRAW = "user_withdraw";
    	public final static String USER_RECHARGE = "user_recharge";
    	public final static String COMPANY_WITHDRAW = "company_withdraw";
    	public final static String COMPANY_RECHARGE = "company_recharge";
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

	public String getCompanyChannel() {
		return companyChannel;
	}

	public void setCompanyChannel(String companyChannel) {
		this.companyChannel = companyChannel;
	}

	public String getCompanyBankcard() {
		return companyBankcard;
	}

	public void setCompanyBankcard(String companyBankcard) {
		this.companyBankcard = companyBankcard;
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