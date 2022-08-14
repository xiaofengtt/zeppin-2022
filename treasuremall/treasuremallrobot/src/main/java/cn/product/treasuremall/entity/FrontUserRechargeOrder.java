package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserRechargeOrder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1138381857089389174L;
	
	@Id
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private Long orderNum;
	private String orderType;
	private String orderChannel;
	private BigDecimal amount;
	private BigDecimal increaseDAmount;
	
	private String type;
	private String capitalAccount;
	private String operator;
	private Timestamp operattime;
	private Timestamp createtime;
	private String status;
	
	private String transData;
	private String remark;
	private Boolean isFirsttime;
	
	//20200603新增活动相关参数
	private Boolean isActivity;
	private String activityId;
	private String prize;
	private String offsetOrder;
    
    public class FrontUserRechargeOrderStatus{
    	public final static String NORMAL = "normal";
    	public final static String CHECKING = "checking";
    	public final static String CHECKED = "checked";
    	public final static String CANCEL = "cancel";
    	public final static String CLOSE = "close";
    }
    
    public class FrontUserRechargeOrderType{
    	public final static String USER_RECHARGE = "user_recharge";
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

	public Boolean getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(Boolean isActivity) {
		this.isActivity = isActivity;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getOffsetOrder() {
		return offsetOrder;
	}

	public void setOffsetOrder(String offsetOrder) {
		this.offsetOrder = offsetOrder;
	}
}