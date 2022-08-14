package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserPaymentOrder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1520409863832397345L;
	
	@Id
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String goodsIssue;
	private String goodsId;
	private String gameType;
	private Long orderNum;
	private String orderType;
	
	private BigDecimal dAmount;
	private Boolean isVoucherUsed;
	private String voucher;
	private BigDecimal actualDAmount;
	private Integer buyCount;
	private BigDecimal poundage;
	private Timestamp createtime;
	private String status;
	
	private String remark;
	private Boolean isPromotion;
	private String promotionId;
	
	private Boolean isLucky;
	private Integer luckyNumber;
	private String paymentGroup;
    
    public class FrontUserPaymentOrderStatus{
    	public final static String NORMAL = "normal";
    	public final static String SUCCESS = "success";
    	public final static String FAILED = "failed";
    	public final static String DELETE = "delete";
    	public final static String CLOSE = "close";
    }
    
    public class FrontUserPaymentOrderType{
    	public final static String COMPANY_BANKCARD = "company_bankcard";
    	public final static String COMPANY_ALIPAY = "company_alipay";
    	public final static String PERSONAL_BANKCARD = "personal_bankcard";
    	public final static String PERSONAL_ALIPAY = "personal_alipay";
    	public final static String WECHAT = "wechat";
    	public final static String REAPAL = "reapal";
    	public final static String ARTIFICIAL = "artificial";
    	public final static String USER_BET = "user_bet";
    }
	
	public class FrontUserPaymentOrderGroup {
		public final static String PERSONAL = "personal";
		public final static String LUCKY = "lucky";
		public final static String RAIDER = "raider";
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

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
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

	public BigDecimal getdAmount() {
		return dAmount;
	}

	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsPromotion() {
		return isPromotion;
	}

	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public Boolean getIsLucky() {
		return isLucky;
	}

	public void setIsLucky(Boolean isLucky) {
		this.isLucky = isLucky;
	}

	public Integer getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(Integer luckyNumber) {
		this.luckyNumber = luckyNumber;
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

	public BigDecimal getActualDAmount() {
		return actualDAmount;
	}

	public void setActualDAmount(BigDecimal actualDAmount) {
		this.actualDAmount = actualDAmount;
	}

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getPaymentGroup() {
		return paymentGroup;
	}

	public void setPaymentGroup(String paymentGroup) {
		this.paymentGroup = paymentGroup;
	}
}