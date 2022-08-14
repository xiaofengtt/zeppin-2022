package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserWithdrawOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2119229615720453351L;
	
	@Id
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
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
	private String operator;
	private Timestamp operattime;
	private Timestamp createtime;
	private String status;
	
	private String transData;
	private String remark;
	private String currency;
	private String currencyRate;
	private String currencyAmount;
    
    public class FrontUserWithdrawOrderStatus{
    	public final static String NORMAL = "normal";
    	public final static String CHECKING = "checking";
    	public final static String CHECKED = "checked";
    	public final static String CANCEL = "cancel";
    	public final static String CLOSE = "close";
    	public final static String FAIL = "fail";
    }
    
    public class FrontUserWithdrawOrderType{
    	public final static String USER_WITHDRAW = "user_withdraw";
    	public final static String DEDUCT = "deduct";
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(String currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getCurrencyAmount() {
		return currencyAmount;
	}

	public void setCurrencyAmount(String currencyAmount) {
		this.currencyAmount = currencyAmount;
	}

}