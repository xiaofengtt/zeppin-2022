package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.FrontUserRechargeOrder;

public class FrontUserRechargeOrderVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2533164832917933958L;
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String frontUserNickname;
	private BigDecimal frontUserBalance;
	private BigDecimal frontUserBalanceLock;
	
	private String orderNum;
	private String orderType;
	private String orderChannel;
	private BigDecimal amount;
	private BigDecimal increaseDAmount;
	
	private String type;
	private String capitalAccount;
	private String operator;
	private String operatorName;
	private Timestamp operattime;
	private Timestamp createtime;
	private String status;
	
	private String transData;
	private String remark;
	private Boolean isFirsttime;
	
	//封装充入信息
	private String capitalPlatform;
	private String accountName;
	private String accountNum;
	
	public FrontUserRechargeOrderVO() {
		super();
	}
	
	public FrontUserRechargeOrderVO (FrontUserRechargeOrder furo) {
		this.uuid = furo.getUuid();
		this.frontUser = furo.getFrontUser();
		this.frontUserShowId = furo.getFrontUserShowId();
		this.orderNum = String.valueOf(furo.getOrderNum());
		this.orderType = furo.getOrderType();
		this.orderChannel = furo.getOrderChannel();
		this.amount = furo.getAmount();
		this.increaseDAmount = furo.getIncreaseDAmount();
		this.type = furo.getType();
		this.capitalAccount = furo.getCapitalAccount();
		this.operator = furo.getOperator();
		this.operattime = furo.getOperattime();
		this.createtime = furo.getCreatetime();
		this.status = furo.getStatus();
		this.transData = furo.getTransData();
		this.remark = furo.getRemark();
		this.isFirsttime = furo.getIsFirsttime();
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

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}
	
	public String getFrontUserNickname() {
		return frontUserNickname;
	}

	public void setFrontUserNickname(String frontUserNickname) {
		this.frontUserNickname = frontUserNickname;
	}

	public BigDecimal getFrontUserBalance() {
		return frontUserBalance;
	}

	public void setFrontUserBalance(BigDecimal frontUserBalance) {
		this.frontUserBalance = frontUserBalance;
	}

	public BigDecimal getFrontUserBalanceLock() {
		return frontUserBalanceLock;
	}

	public void setFrontUserBalanceLock(BigDecimal frontUserBalanceLock) {
		this.frontUserBalanceLock = frontUserBalanceLock;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getIncreaseDAmount() {
		return increaseDAmount;
	}

	public void setIncreaseDAmount(BigDecimal increaseDAmount) {
		this.increaseDAmount = increaseDAmount;
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

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsFirsttime() {
		return isFirsttime;
	}

	public void setIsFirsttime(Boolean isFirsttime) {
		this.isFirsttime = isFirsttime;
	}

	public String getCapitalPlatform() {
		return capitalPlatform;
	}

	public void setCapitalPlatform(String capitalPlatform) {
		this.capitalPlatform = capitalPlatform;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

}