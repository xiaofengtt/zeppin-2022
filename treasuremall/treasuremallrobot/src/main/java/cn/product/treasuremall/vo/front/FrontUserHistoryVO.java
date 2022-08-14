package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.FrontUserHistory;

public class FrontUserHistoryVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7912821288687882825L;
	
	private String uuid;
	private String frontUser;
	private String orderNum;
	
	private String type;
	private String orderId;
	private String orderType;
	private BigDecimal balanceBefore;
	private BigDecimal dAmount;
	private BigDecimal balanceAfter;
	
	private String reason;
	private String remark;
	private Timestamp createtime;
    
    private String goodsIssue;
    private String goodsTitle;
    
    private BigDecimal amount;
    private BigDecimal poundage;
    
    private Boolean isVoucherUsed;
	private String voucher;
	private String voucherTitle;
	private BigDecimal voucherDAmount;
    
    public FrontUserHistoryVO(FrontUserHistory fuh) {
    	this.uuid = fuh.getUuid();
		this.frontUser = fuh.getFrontUser();
		this.orderNum = String.valueOf(fuh.getOrderNum());
		this.type = fuh.getType();
		this.orderId = fuh.getOrderId();
		this.orderType = fuh.getOrderType();
		this.balanceBefore = fuh.getBalanceBefore();
		this.dAmount = fuh.getdAmount();
		this.balanceAfter = fuh.getBalanceAfter();
		this.reason = fuh.getReason();
		this.remark = fuh.getRemark();
		this.createtime = fuh.getCreatetime();
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

	public BigDecimal getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(BigDecimal balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public BigDecimal getdAmount() {
		return dAmount;
	}

	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}

	public BigDecimal getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(BigDecimal balanceAfter) {
		this.balanceAfter = balanceAfter;
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

	public Boolean getIsVoucherUsed() {
		return isVoucherUsed;
	}

	public void setIsVoucherUsed(Boolean isVoucherUsed) {
		this.isVoucherUsed = isVoucherUsed;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public String getVoucherTitle() {
		return voucherTitle;
	}

	public void setVoucherTitle(String voucherTitle) {
		this.voucherTitle = voucherTitle;
	}

	public BigDecimal getVoucherDAmount() {
		return voucherDAmount;
	}

	public void setVoucherDAmount(BigDecimal voucherDAmount) {
		this.voucherDAmount = voucherDAmount;
	}
}