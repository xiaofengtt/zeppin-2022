package cn.product.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class UserWithdraw implements Serializable{
	
	/**
	 * 用户提现
	 */
	private static final long serialVersionUID = -6565504149543392260L;
	
	@Id
    private String uuid;
	private String channel;
	private String channelAccount;
	private String company;
	private String companyChannel;
    private String orderNum;
    private BigDecimal totalAmount;
    private BigDecimal poundage;
    private BigDecimal amount;
    private String companyOrderNum;
	private String companyData;
	private String companyNotifyUrl;
	private String transData;
	private String operator;
    private Timestamp operattime;
    private String proof;
    private String status;
    private String failReason;
    private Timestamp createtime;
    
    public class UserWithdrawStatus{
    	public final static String CHECKING = "checking";
    	public final static String CHECKED = "checked";
    	public final static String FAIL = "fail";
    	public final static String CLOSE = "close";
    	public final static String SUCCESS = "success";
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
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

	public String getCompanyNotifyUrl() {
		return companyNotifyUrl;
	}

	public void setCompanyNotifyUrl(String companyNotifyUrl) {
		this.companyNotifyUrl = companyNotifyUrl;
	}

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}