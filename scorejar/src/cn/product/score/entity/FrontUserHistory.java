package cn.product.score.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



public class FrontUserHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1648430524231385202L;
	
	
	private String uuid;
	private String frontUser;
	private String frontUserAccount;
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
	private String capitalAccountHistory;
	private String frontUserBankcard;
	private String frontUserBet;
	private String operator;
	private Timestamp operattime;
	private String status;
	private Timestamp createtime;
    
    public class FrontUserHistoryStatus{
    	public final static String NORMAL = "normal";
    	public final static String SUCCESS = "success";
    	public final static String FAILED = "failed";
    	public final static String CLOSE = "close";
    	public final static String REFUND = "refund";
    	public final static String DELETE = "delete";
    	public final static String CONFIRM = "confirm";
    	public final static String TRANSACTING = "transacting";
    	public final static String WARNNING = "warnning";
    }
    
    public class FrontUserHistoryType{
    	public final static String USER_RECHARGE = "user_recharge";
    	public final static String USER_WITHDRAW = "user_withdraw";
    	public final static String USER_BET = "user_bet";
    	public final static String USER_BET_AWARD = "user_bet_award";
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

	public String getFrontUserAccount() {
		return frontUserAccount;
	}

	public void setFrontUserAccount(String frontUserAccount) {
		this.frontUserAccount = frontUserAccount;
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

	public String getFrontUserBet() {
		return frontUserBet;
	}

	public void setFrontUserBet(String frontUserBet) {
		this.frontUserBet = frontUserBet;
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