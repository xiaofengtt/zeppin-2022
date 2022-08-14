package cn.product.score.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.score.entity.CapitalAccountHistory;

public class CapitalAccountHistoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6493881087077609479L;
	
	private String uuid;
	private String capitalPlatform;
	private String capitalPlatformName;
	private String capitalAccount;
	private String capitalAccountName;
	private String orderId;
	private String orderNum;
	private String orderType;
	private BigDecimal balance;
	private BigDecimal income;
	private BigDecimal pay;
	private BigDecimal poundage;
	private String type;
	private String transData;
	private String frontUser;
	private String frontUserName;
	private String frontUserMobile;
	private String frontUserHistory;
	private String operator;
	private String operatorName;
	private Timestamp operattime;
	private String status;
	private Timestamp createtime;
	
	public CapitalAccountHistoryVO(CapitalAccountHistory cah){
		this.uuid = cah.getUuid();
		this.capitalPlatform = cah.getCapitalPlatform();
		this.capitalAccount = cah.getCapitalAccount();
		this.orderId = cah.getOrderId();
		this.orderNum = cah.getOrderNum();
		this.orderType = cah.getOrderType();
		this.balance = cah.getBalance();
		this.income = cah.getIncome();
		this.pay = cah.getPay();
		this.poundage = cah.getPoundage();
		this.type = cah.getType();
		this.transData = cah.getTransData();
		this.frontUser = cah.getFrontUser();
		this.frontUserHistory = cah.getFrontUserHistory();
		this.operator = cah.getOperator();
		this.operattime = cah.getOperattime();
		this.status = cah.getStatus();
		this.createtime = cah.getCreatetime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getFrontUserHistory() {
		return frontUserHistory;
	}

	public void setFrontUserHistory(String frontUserHistory) {
		this.frontUserHistory = frontUserHistory;
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