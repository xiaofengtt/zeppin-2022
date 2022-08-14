package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.FrontUserWithdrawOrder;

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
	private String frontUserLevel;
	private String frontUserStatus;
	
	private String orderNum;
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
	
	private String ip;
    private String area;
	private BigDecimal scoreBalance;
    private BigDecimal totalRecharge;
	private BigDecimal totalWithdraw;
	private BigDecimal totalPayment;
	private BigDecimal totalDelivery;
	private Timestamp registertime;
	
    
	public FrontUserWithdrawOrderVO() {
		super();
	}
	
	public FrontUserWithdrawOrderVO(FrontUserWithdrawOrder fuwo){
		this.uuid = fuwo.getUuid();
		this.frontUser = fuwo.getFrontUser();
		this.frontUserShowId = fuwo.getFrontUserShowId();
		this.orderNum = String.valueOf(fuwo.getOrderNum());
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public BigDecimal getScoreBalance() {
		return scoreBalance;
	}

	public void setScoreBalance(BigDecimal scoreBalance) {
		this.scoreBalance = scoreBalance;
	}

	public BigDecimal getTotalRecharge() {
		return totalRecharge;
	}

	public void setTotalRecharge(BigDecimal totalRecharge) {
		this.totalRecharge = totalRecharge;
	}

	public BigDecimal getTotalWithdraw() {
		return totalWithdraw;
	}

	public void setTotalWithdraw(BigDecimal totalWithdraw) {
		this.totalWithdraw = totalWithdraw;
	}

	public BigDecimal getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}

	public Timestamp getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Timestamp registertime) {
		this.registertime = registertime;
	}

	public String getFrontUserLevel() {
		return frontUserLevel;
	}

	public void setFrontUserLevel(String frontUserLevel) {
		this.frontUserLevel = frontUserLevel;
	}

	public String getFrontUserStatus() {
		return frontUserStatus;
	}

	public void setFrontUserStatus(String frontUserStatus) {
		this.frontUserStatus = frontUserStatus;
	}

	public BigDecimal getTotalDelivery() {
		return totalDelivery;
	}

	public void setTotalDelivery(BigDecimal totalDelivery) {
		this.totalDelivery = totalDelivery;
	}
}