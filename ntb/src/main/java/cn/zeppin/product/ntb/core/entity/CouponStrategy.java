/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】优惠券策略信息
 */

@Entity
@Table(name = "coupon_strategy")
public class CouponStrategy extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3255628932260509116L;
	
	private String uuid;
	private String strategyIdentification;
	private Timestamp expiryDate;
	private String coupon;
	
	private String creator;
	private Timestamp createtime;
	private String status;
	
	public class CouponStrategyStatus{
		public final static String OPEN = "open";
		public final static String CLOSE = "close";
		public final static String DELETED = "deleted";
	}
	
	public class CouponStrategyUuid{
		public final static String REGISTERED = "b6461106-6c03-46d5-bcfb-d0a156bc2a2a";
		public final static String INVESTED = "36ddca87-c9c1-4811-b71f-03b714bafcda";
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

	@Column(name = "expiry_date")
	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	@Column(name = "strategy_identification", nullable = false, length = 20)
	public String getStrategyIdentification() {
		return strategyIdentification;
	}
	
	public void setStrategyIdentification(String strategyIdentification) {
		this.strategyIdentification = strategyIdentification;
	}
	
	@Column(name = "coupon", nullable = false, length = 36)
	public String getCoupon() {
		return coupon;
	}
	

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

}
