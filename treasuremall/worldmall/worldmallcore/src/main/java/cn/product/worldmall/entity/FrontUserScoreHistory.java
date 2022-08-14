package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserScoreHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2953467541990020977L;
	
	@Id
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private Long orderNum;
	private String orderId;
	private String orderType;
	
	private String type;
	private Timestamp createtime;
	private BigDecimal scoreBalanceBefore;
	private BigDecimal sAmount;
	private BigDecimal scoreBalanceAfter;
	
	private String reason;
	private String remark;
    
    
    public class FrontUserScoreHistoryType{
    	public final static String USER_ADD = "user_add";
    	public final static String USER_SUB = "user_sub";
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}

	public BigDecimal getScoreBalanceBefore() {
		return scoreBalanceBefore;
	}

	public void setScoreBalanceBefore(BigDecimal scoreBalanceBefore) {
		this.scoreBalanceBefore = scoreBalanceBefore;
	}

	public BigDecimal getsAmount() {
		return sAmount;
	}

	public void setsAmount(BigDecimal sAmount) {
		this.sAmount = sAmount;
	}

	public BigDecimal getScoreBalanceAfter() {
		return scoreBalanceAfter;
	}

	public void setScoreBalanceAfter(BigDecimal scoreBalanceAfter) {
		this.scoreBalanceAfter = scoreBalanceAfter;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}