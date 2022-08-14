/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe 2016年2月8日
 */

public class CouponLessVO extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8025156049869106400L;
	
	private String uuid;
	private String couponName;
	private String couponType;
	private String couponTypeCN;
	private BigDecimal couponPrice;
	private String couponPriceCN;
	private BigDecimal minInvestAccount;
	private String minInvestAccountCN;
	private Timestamp expiryDate;
	private String expiryDateCN;
	private Integer deadline;
	
	private Integer count;
	
	
	public CouponLessVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponLessVO(Coupon cp) {
		this.uuid = cp.getUuid();
		this.couponName = cp.getCouponName();
		this.couponType = cp.getCouponType();
		if(CouponType.CASH.equals(cp.getCouponType())){
			this.couponTypeCN = "现金券";
		} else if (CouponType.INTERESTS.equals(cp.getCouponType())) {
			this.couponTypeCN = "加息券";
		} else {
			this.couponTypeCN = "-";
		}
		this.couponPrice = cp.getCouponPrice();
		this.couponPriceCN = Utlity.numFormat4UnitDetailLess(cp.getCouponPrice());
		this.minInvestAccount = cp.getMinInvestAccount() == null ? BigDecimal.ZERO : cp.getMinInvestAccount();
		this.minInvestAccountCN = Utlity.numFormat4UnitDetailLess(this.minInvestAccount);
		this.expiryDate = cp.getExpiryDate();
		if(cp.getExpiryDate() != null){
			this.expiryDateCN = Utlity.timeSpanToString(cp.getExpiryDate());
		} else {
			this.expiryDateCN = "";
		}
		this.deadline = cp.getDeadline() == null ? 0 : cp.getDeadline();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	}

	public BigDecimal getMinInvestAccount() {
		return minInvestAccount;
	}

	public void setMinInvestAccount(BigDecimal minInvestAccount) {
		this.minInvestAccount = minInvestAccount;
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

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public String getCouponPriceCN() {
		return couponPriceCN;
	}

	public void setCouponPriceCN(String couponPriceCN) {
		this.couponPriceCN = couponPriceCN;
	}

	public String getMinInvestAccountCN() {
		return minInvestAccountCN;
	}

	public void setMinInvestAccountCN(String minInvestAccountCN) {
		this.minInvestAccountCN = minInvestAccountCN;
	}

	
	public Integer getCount() {
		return count;
	}
	

	public void setCount(Integer count) {
		this.count = count;
	}

}
