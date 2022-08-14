package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeCouponHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbEmployeeCouponHistoryVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String qcbEmployeeProductBuy;
	private String coupon;
	private BigDecimal price;
	
	private Timestamp createtime;
	private String creator;
	
	private String couponName;
	private String couponType;
	private String couponTypeCN;
	private BigDecimal couponPrice;
	private String couponPriceCN;
	private String status;
	
	
 	public QcbEmployeeCouponHistoryVO(){
		
	}
	
	public QcbEmployeeCouponHistoryVO(QcbEmployeeCouponHistory qech){
		this.uuid = qech.getUuid();
		this.coupon = qech.getCoupon();
		this.status = qech.getStatus();
		this.qcbEmployeeProductBuy = qech.getQcbEmployeeProductBuy();
		this.price = qech.getPrice();
		this.creator = qech.getCreator();
		this.createtime = qech.getCreatetime();
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

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
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

	public String getQcbEmployeeProductBuy() {
		return qcbEmployeeProductBuy;
	}

	public void setQcbEmployeeProductBuy(String qcbEmployeeProductBuy) {
		this.qcbEmployeeProductBuy = qcbEmployeeProductBuy;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
