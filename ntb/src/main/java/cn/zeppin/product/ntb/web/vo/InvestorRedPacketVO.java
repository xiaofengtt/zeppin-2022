package cn.zeppin.product.ntb.web.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.InvestorRedPacket;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class InvestorRedPacketVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String couponName;
	private String couponType;
	private String couponTypeCN;
	private BigDecimal couponPrice;
	private String couponPriceCN;
	private Timestamp expiryDate;
	private String expiryDateCN;
	private String status;
	
	
 	public InvestorRedPacketVO(){
		
	}
	
	public InvestorRedPacketVO(InvestorRedPacket ii){
		this.uuid = ii.getUuid();
		this.status = ii.getStatus();
		this.expiryDate = ii.getExpiryDate();
		if(ii.getExpiryDate() != null){
			this.expiryDateCN = Utlity.timeSpanToDateString(ii.getExpiryDate());
		} else {
			this.expiryDateCN = "";
		}
		this.couponPrice = ii.getPirce();
		this.couponPriceCN = Utlity.numFormat4UnitDetail(ii.getPirce());
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
		if(CouponType.CASH.equals(couponType)){
			this.couponTypeCN = "现金券";
		} else if (CouponType.INTERESTS.equals(couponType)) {
			this.couponTypeCN = "加息券";
		} else if ("redPacket".equals(couponType)) {
			this.couponTypeCN = "现金红包";
		} else {
			this.couponTypeCN = "-";
		}
	}

	public String getCouponTypeCN() {
		return couponTypeCN;
	}

	public void setCouponTypeCN(String couponTypeCN) {
		this.couponTypeCN = couponTypeCN;
	}

	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
		this.couponPriceCN = Utlity.numFormat4UnitDetailLess(couponPrice);
	}

	public String getCouponPriceCN() {
		return couponPriceCN;
	}

	public void setCouponPriceCN(String couponPriceCN) {
		this.couponPriceCN = couponPriceCN;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getExpiryDateCN() {
		return expiryDateCN;
	}

	public void setExpiryDateCN(String expiryDateCN) {
		this.expiryDateCN = expiryDateCN;
	}
	
}
