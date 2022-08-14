package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserScoreHistory;

public class FrontUserScoreHistoryVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7912821288687882825L;
	
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
    
    private String goodsIssue;
    private String goodsTitle;
    private BigDecimal totalDAmount;
	private BigDecimal actualDAmount;
	private Integer buyCount;
    
    private BigDecimal amount;
    private BigDecimal poundage;
    
    public FrontUserScoreHistoryVO(FrontUserScoreHistory fush) {
    	this.uuid = fush.getUuid();
		this.frontUser = fush.getFrontUser();
		this.orderNum = String.valueOf(fush.getOrderNum());
		this.type = fush.getType();
		this.orderId = fush.getOrderId();
		this.orderType = fush.getOrderType();
		this.scoreBalanceBefore = fush.getScoreBalanceBefore();
		this.scoreBalanceAfter = fush.getScoreBalanceAfter();
		this.sAmount = fush.getsAmount(); 
		this.reason = fush.getReason();
		this.remark = fush.getRemark();
		this.createtime = fush.getCreatetime();
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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

	public String getGoodsIssue() {
		return goodsIssue;
	}

	public void setGoodsIssue(String goodsIssue) {
		this.goodsIssue = goodsIssue;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
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

	public BigDecimal getTotalDAmount() {
		return totalDAmount;
	}

	public void setTotalDAmount(BigDecimal totalDAmount) {
		this.totalDAmount = totalDAmount;
	}

	public BigDecimal getActualDAmount() {
		return actualDAmount;
	}

	public void setActualDAmount(BigDecimal actualDAmount) {
		this.actualDAmount = actualDAmount;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
}