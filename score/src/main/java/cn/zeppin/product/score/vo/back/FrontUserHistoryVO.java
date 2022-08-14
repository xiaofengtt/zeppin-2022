package cn.zeppin.product.score.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.score.entity.FrontUserHistory;

public class FrontUserHistoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3614089750174839122L;
	
	private String uuid;
	private String frontUser;
	private String frontUserName;
	private String frontUserMobile;
	private String frontUserAccount;
	private String frontUserAccountCheck;
	private String frontUserAccountCheckProof;
	private String orderId;
	private String orderNum;
	private String orderType;
	private BigDecimal balance;
	private BigDecimal income;
	private BigDecimal pay;
	private BigDecimal poundage;
	private String type;
	private String transData;
	private String capitalAccount;
	private String capitalAccountName;
	private String capitalPlatform;
	private String capitalPlatformName;
	private String capitalAccountHistory;
	private String frontUserBankcard;
	private String frontUserBankcardAccountHolder;
	private String frontUserBankcardAccountNumber;
	private String frontUserBankcardBankName;
	private String operator;
	private String operatorName;
	private Timestamp operattime;
	private String status;
	private Timestamp createtime;
    
	public FrontUserHistoryVO(FrontUserHistory fuh){
		this.uuid = fuh.getUuid();
		this.frontUser = fuh.getFrontUser();
		this.frontUserAccount = fuh.getFrontUserAccount();
		this.orderId = fuh.getOrderId();
		this.orderNum = fuh.getOrderNum();
		this.orderType = fuh.getOrderType();
		this.balance = fuh.getBalance();
		this.income = fuh.getIncome();
		this.pay = fuh.getPay();
		this.poundage = fuh.getPoundage();
		this.type = fuh.getType();
		this.transData = fuh.getTransData();
		this.capitalAccount = fuh.getCapitalAccount();
		this.capitalAccountHistory = fuh.getCapitalAccountHistory();
		this.frontUserBankcard = fuh.getFrontUserBankcard();
		this.operator = fuh.getOperator();
		this.operattime = fuh.getOperattime();
		this.status = fuh.getStatus();
		this.createtime = fuh.getCreatetime();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public String getFrontUserName() {
		return frontUserName;
	}

	public void setFrontUserName(String frontUserName) {
		this.frontUserName = frontUserName;
	}

	public String getFrontUserMobile() {
		return frontUserMobile;
	}

	public void setFrontUserMobile(String frontUserMobile) {
		this.frontUserMobile = frontUserMobile;
	}

	public String getFrontUserAccount() {
		return frontUserAccount;
	}

	public void setFrontUserAccount(String frontUserAccount) {
		this.frontUserAccount = frontUserAccount;
	}

	public String getFrontUserAccountCheck() {
		return frontUserAccountCheck;
	}

	public void setFrontUserAccountCheck(String frontUserAccountCheck) {
		this.frontUserAccountCheck = frontUserAccountCheck;
	}

	public String getFrontUserAccountCheckProof() {
		return frontUserAccountCheckProof;
	}

	public void setFrontUserAccountCheckProof(String frontUserAccountCheckProof) {
		this.frontUserAccountCheckProof = frontUserAccountCheckProof;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getPay() {
		return pay;
	}

	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
	}

	public String getCapitalAccount() {
		return capitalAccount;
	}

	public void setCapitalAccount(String capitalAccount) {
		this.capitalAccount = capitalAccount;
	}

	public String getCapitalAccountName() {
		return capitalAccountName;
	}

	public void setCapitalAccountName(String capitalAccountName) {
		this.capitalAccountName = capitalAccountName;
	}

	public String getCapitalPlatform() {
		return capitalPlatform;
	}

	public void setCapitalPlatform(String capitalPlatform) {
		this.capitalPlatform = capitalPlatform;
	}

	public String getCapitalPlatformName() {
		return capitalPlatformName;
	}

	public void setCapitalPlatformName(String capitalPlatformName) {
		this.capitalPlatformName = capitalPlatformName;
	}

	public String getCapitalAccountHistory() {
		return capitalAccountHistory;
	}

	public void setCapitalAccountHistory(String capitalAccountHistory) {
		this.capitalAccountHistory = capitalAccountHistory;
	}

	public String getFrontUserBankcard() {
		return frontUserBankcard;
	}

	public void setFrontUserBankcard(String frontUserBankcard) {
		this.frontUserBankcard = frontUserBankcard;
	}

	public String getFrontUserBankcardAccountHolder() {
		return frontUserBankcardAccountHolder;
	}

	public void setFrontUserBankcardAccountHolder(
			String frontUserBankcardAccountHolder) {
		this.frontUserBankcardAccountHolder = frontUserBankcardAccountHolder;
	}

	public String getFrontUserBankcardAccountNumber() {
		return frontUserBankcardAccountNumber;
	}

	public void setFrontUserBankcardAccountNumber(
			String frontUserBankcardAccountNumber) {
		this.frontUserBankcardAccountNumber = frontUserBankcardAccountNumber;
	}

	public String getFrontUserBankcardBankName() {
		return frontUserBankcardBankName;
	}

	public void setFrontUserBankcardBankName(String frontUserBankcardBankName) {
		this.frontUserBankcardBankName = frontUserBankcardBankName;
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