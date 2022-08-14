/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】优惠券信息
 */

@Entity
@Table(name = "coupon")
public class Coupon extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3255628932260509116L;
	
	private String uuid;
	private String couponName;
	private String couponType;
	private BigDecimal couponPrice;
	private BigDecimal minInvestAccount;
	private Timestamp expiryDate;
	private Integer deadline;
	
	private String creator;
	private Timestamp createtime;
	private String status;
	
	public class CouponStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
	}
	
	public class CouponType{
		public final static String CASH = "cash";
		public final static String INTERESTS = "interests";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "coupon_name", nullable = false, length = 50)
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	@Column(name = "coupon_type", nullable = false, length = 10)
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	@Column(name = "coupon_price", nullable = false)
	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	@Column(name = "min_invest_account")
	public BigDecimal getMinInvestAccount() {
		return minInvestAccount;
	}

	public void setMinInvestAccount(BigDecimal minInvestAccount) {
		this.minInvestAccount = minInvestAccount;
	}

	@Column(name = "expiry_date")
	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "deadline")
	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}
	
}
