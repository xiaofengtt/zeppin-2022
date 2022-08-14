package cn.product.payment.vo.system;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

import cn.product.payment.entity.UserRecharge;
import cn.product.payment.util.JSONUtils;

public class UserRechargeVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1442004709773562742L;
	
	private String uuid;
	private String channel;
	private String channelName;
	private String channelCode;
	private String channelAccount;
	private String channelAccountName;
	private String channelAccountNum;
	private String company;
	private String companyName;
	private String companyCode;
	private BigDecimal companyBalance;
    private BigDecimal companyBalanceLock;
	private String companyChannel;
    private String orderNum;
    private BigDecimal totalAmount;
    private BigDecimal poundage;
    private BigDecimal amount;
    private String companyOrderNum;
	private String companyData;
	private String companyNotifyUrl;
	private Timestamp timeout;
	private String transData;
	private Map<String, Object> transDataMap;
	private String operator;
	private String operatorName;
    private Timestamp operattime;
    private String noticeInfo;
    private String status;
    private String processCode;
    private Timestamp createtime;
	
	public UserRechargeVO() {
		super();
	}
	
	public UserRechargeVO (UserRecharge ur) {
		this.uuid = ur.getUuid();
		this.channel = ur.getChannel();
		this.channelAccount = ur.getChannelAccount();
		this.company = ur.getCompany();
		this.companyChannel = ur.getCompanyChannel();
		this.orderNum = ur.getOrderNum();
		this.totalAmount = ur.getTotalAmount();
		this.poundage = ur.getPoundage();
		this.amount = ur.getAmount();
		this.companyOrderNum = ur.getCompanyOrderNum();
		this.companyData = ur.getCompanyData();
		this.companyNotifyUrl = ur.getCompanyNotifyUrl();
		this.timeout = ur.getTimeout();
		this.transData = ur.getTransData();
		if(transData != null){
			this.transDataMap = JSONUtils.json2map(ur.getTransData());
		}
		this.operator = ur.getOperator();
		this.operattime = ur.getOperattime();
		this.noticeInfo = ur.getNoticeInfo();
		this.status = ur.getStatus();
		this.processCode = ur.getProcessCode();
		this.createtime = ur.getCreatetime();
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

	public String getChannelAccountNum() {
		return channelAccountNum;
	}

	public void setChannelAccountNum(String channelAccountNum) {
		this.channelAccountNum = channelAccountNum;
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

	public BigDecimal getCompanyBalance() {
		return companyBalance;
	}

	public void setCompanyBalance(BigDecimal companyBalance) {
		this.companyBalance = companyBalance;
	}

	public BigDecimal getCompanyBalanceLock() {
		return companyBalanceLock;
	}

	public void setCompanyBalanceLock(BigDecimal companyBalanceLock) {
		this.companyBalanceLock = companyBalanceLock;
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

	public Timestamp getTimeout() {
		return timeout;
	}

	public void setTimeout(Timestamp timeout) {
		this.timeout = timeout;
	}

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
	}

	public Map<String, Object> getTransDataMap() {
		return transDataMap;
	}

	public void setTransDataMap(Map<String, Object> transDataMap) {
		this.transDataMap = transDataMap;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getNoticeInfo() {
		return noticeInfo;
	}

	public void setNoticeInfo(String noticeInfo) {
		this.noticeInfo = noticeInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}