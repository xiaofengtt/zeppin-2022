/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Coupon;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponStatus;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe 2016年2月8日
 */

public class CouponVO extends BaseEntity {
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
	
	private String creator;
	private String creatorCN;
	private Timestamp createtime;
	private String createtimeCN;
	private String status;
	private String statusCN;
	
	private Integer count = 1;
	
	
	public CouponVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponVO(Coupon cp) {
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
		this.createtime = cp.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToPointString(cp.getCreatetime());
		this.creator = cp.getCreator();
		this.creatorCN = "--";
		this.status = cp.getStatus();
		if(CouponStatus.NORMAL.equals(cp.getStatus())){
			this.statusCN = "正常";
		} else if (CouponStatus.DELETED.equals(cp.getStatus())) {
			this.statusCN = "已删除";
		} else if (CouponStatus.DISABLE.equals(cp.getStatus())) {
			this.statusCN = "已失效";
		} else {
			this.statusCN = "-";
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getCreatorCN() {
		return creatorCN;
	}

	public void setCreatorCN(String creatorCN) {
		this.creatorCN = creatorCN;
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
