package cn.product.score.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



public class CapitalAccountHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5377516109128211956L;
	
	
	private String uuid;
	private String capitalPlatform;
	private String capitalAccount;
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
	private String frontUserHistory;
	private String operator;
	private Timestamp operattime;
	private String status;
	private Timestamp createtime;
    
    public class CapitalAccountHistoryStatus{
    	public final static String NORMAL = "normal";
    	public final static String SUCCESS = "success";
    	public final static String FAILED = "failed";
    	public final static String CLOSE = "close";
    	public final static String REFUND = "refund";
    	public final static String DELETE = "delete";
    }
    
    public class CapitalAccountHistoryType{
    	public final static String USER_RECHARGE = "user_recharge";
    	public final static String USER_WITHDRAW = "user_withdraw";
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

	public String getCapitalAccount() {
		return capitalAccount;
	}

	public void setCapitalAccount(String capitalAccount) {
		this.capitalAccount = capitalAccount;
	}
	
	public String getCapitalPlatform() {
		return capitalPlatform;
	}

	public void setCapitalPlatform(String capitalPlatform) {
		this.capitalPlatform = capitalPlatform;
	}

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
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