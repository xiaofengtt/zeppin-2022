package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserScoreHistory;

public class FrontUserScoreHistoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6101756030971954898L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String orderNum;
	private String orderId;
	private String orderType;
	
	private String type;
	private Timestamp createtime;
	private BigDecimal scoreBalanceBefore;
	private BigDecimal sAmount;
	private BigDecimal scoreBalanceAfter;
	
	private String reason;
	private String remark;
	
	private String title;
	
    
	public FrontUserScoreHistoryVO(FrontUserScoreHistory fush){
		this.uuid = fush.getUuid();
		this.frontUser = fush.getFrontUser();
		this.frontUserShowId = fush.getFrontUserShowId();
		this.orderNum = String.valueOf(fush.getOrderNum());
		this.orderId = fush.getOrderId();
		this.orderType = fush.getOrderType();
		
		this.type = fush.getType();
		this.createtime = fush.getCreatetime();
		
		this.scoreBalanceBefore = fush.getScoreBalanceBefore();
		this.scoreBalanceAfter = fush.getScoreBalanceAfter();
		this.sAmount = fush.getsAmount();
		
		this.reason = fush.getReason();
		this.remark = fush.getRemark();
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
}