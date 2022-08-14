package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserWithdrawOrder;

public class FrontUserWithdrawOrderVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6564910706990382304L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String frontUserNickname;
	private BigDecimal frontUserBalance;
	private BigDecimal frontUserBalanceLock;
	
	private Long orderNum;
	private String orderType;
	private String orderChannel;
	
	private BigDecimal reduceDAmount;
	private BigDecimal amount;
	private BigDecimal poundage;
	private BigDecimal actualAmount;
	
	private String type;
	private String capitalAccount;
	private String frontUserBankcard;
	private String frontUserBankName;
	private String frontUserBranchBank;
	private String frontUserAccountNumber;
	private String frontUserAccountHolder;
	
	private String operator;
	private String operatorName;
	private Timestamp operattime;
	private Timestamp createtime;
	private String status;
	
	private String transData;
	private String remark;
	
	public FrontUserWithdrawOrderVO() {
		super();
	}
	
	public FrontUserWithdrawOrderVO(FrontUserWithdrawOrder fuwo){
		this.uuid = fuwo.getUuid();
		this.frontUser = fuwo.getFrontUser();
		this.frontUserShowId = fuwo.getFrontUserShowId();
		this.orderNum = fuwo.getOrderNum();
		this.orderType = fuwo.getOrderType();
		this.orderChannel = fuwo.getOrderChannel();
		
		this.reduceDAmount = fuwo.getReduceDAmount();
		this.amount = fuwo.getAmount();
		this.poundage = fuwo.getPoundage();
		this.actualAmount = fuwo.getActualAmount();
		this.type = fuwo.getType();
		this.capitalAccount = fuwo.getCapitalAccount();
		this.frontUserBankcard = fuwo.getFrontUserBankcard();
		
		this.operator = fuwo.getOperator();
		this.operattime = fuwo.getOperattime();
		this.status = fuwo.getStatus();
		this.transData = fuwo.getTransData();
		this.remark = fuwo.getRemark();
		this.createtime = fuwo.getCreatetime();
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

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
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

	public BigDecimal getReduceDAmount() {
		return reduceDAmount;
	}

	public void setReduceDAmount(BigDecimal reduceDAmount) {
		this.reduceDAmount = reduceDAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
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

	public String getFrontUserBankcard() {
		return frontUserBankcard;
	}

	public void setFrontUserBankcard(String frontUserBankcard) {
		this.frontUserBankcard = frontUserBankcard;
	}

	public String getFrontUserBankName() {
		return frontUserBankName;
	}

	public void setFrontUserBankName(String frontUserBankName) {
		this.frontUserBankName = frontUserBankName;
	}

	public String getFrontUserBranchBank() {
		return frontUserBranchBank;
	}

	public void setFrontUserBranchBank(String frontUserBranchBank) {
		this.frontUserBranchBank = frontUserBranchBank;
	}

	public String getFrontUserAccountNumber() {
		return frontUserAccountNumber;
	}

	public void setFrontUserAccountNumber(String frontUserAccountNumber) {
		this.frontUserAccountNumber = frontUserAccountNumber;
	}

	public String getFrontUserAccountHolder() {
		return frontUserAccountHolder;
	}

	public void setFrontUserAccountHolder(String frontUserAccountHolder) {
		this.frontUserAccountHolder = frontUserAccountHolder;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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
	
}