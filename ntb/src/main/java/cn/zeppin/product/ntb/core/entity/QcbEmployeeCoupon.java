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
 * @author hehe
 * @description 【数据对象】企财宝员工优惠券
 */

@Entity
@Table(name = "qcb_employee_coupon")
public class QcbEmployeeCoupon extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5708331789914900714L;
	
	private String uuid;
	private String coupon;
	
	private String qcbEmployee;
	private String status;
	private Timestamp createtime;
	private String creator;
	private Timestamp expiryDate;
	
	public class QcbEmployeeCouponStatus{
		public final static String NOTACTIVE = "notactive";//未激活
		public final static String UNUSE = "unuse";//未使用
		public final static String USED = "used";//已使用
		public final static String DELETED = "deleted";//已删除
		public final static String EXPIRED = "expired";//已过期
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
	
	@Column(name = "qcb_employee", nullable = false)
	public String getQcbEmployee() {
		return qcbEmployee;
	}
	
	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}

	
	@Column(name = "coupon", nullable = false)
	public String getCoupon() {
		return coupon;
	}
	

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	@Column(name = "expiry_date", nullable = false)
	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

}
