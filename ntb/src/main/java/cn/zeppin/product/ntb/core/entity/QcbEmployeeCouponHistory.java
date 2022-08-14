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
 * @author hehe
 * @description 【数据对象】应用内消息
 */

@Entity
@Table(name = "qcb_employee_coupon_history")
public class QcbEmployeeCouponHistory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7103067181358004425L;
	private String uuid;
	private String qcbEmployeeProductBuy;
	private String coupon;
	private BigDecimal price;
	
	private Timestamp createtime;
	private String creator;
	private String status;
	
	private String qcbEmployeeHistory;
	private BigDecimal dividend;
	
	public class QcbEmployeeCouponHistoryStatus {
		public final static  String NORMAL = "normal";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "creator", length = 36, nullable = false)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "status", length = 10, nullable = false)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "qcb_employee_product_buy", length = 36, nullable = false)
	public String getQcbEmployeeProductBuy() {
		return qcbEmployeeProductBuy;
	}

	public void setQcbEmployeeProductBuy(String qcbEmployeeProductBuy) {
		this.qcbEmployeeProductBuy = qcbEmployeeProductBuy;
	}

	@Column(name = "coupon", length = 36, nullable = false)
	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "qcb_employee_history", length = 36, nullable = false)
	public String getQcbEmployeeHistory() {
		return qcbEmployeeHistory;
	}

	public void setQcbEmployeeHistory(String qcbEmployeeHistory) {
		this.qcbEmployeeHistory = qcbEmployeeHistory;
	}
	
	@Column(name = "dividend")
	public BigDecimal getDividend() {
		return dividend;
	}

	public void setDividend(BigDecimal dividend) {
		this.dividend = dividend;
	}
	
}
