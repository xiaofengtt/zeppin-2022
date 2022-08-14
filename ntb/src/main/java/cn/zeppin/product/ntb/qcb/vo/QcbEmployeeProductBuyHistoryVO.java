/**
 * 
 */
package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * 
 * @author Administrator
 *
 */
public class QcbEmployeeProductBuyHistoryVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6947703206763148941L;
	
	private String uuid;
	private String qcbEmployee;
	
	private BigDecimal price;
	private String priceCN;
	
	private Timestamp createtime;
	private String createtimeCN;
	private String createtimeLessCN;
	
	private String coupon;
	private String couponName;
	private String couponType;
	private String couponTypeCN;
	private BigDecimal couponPrice;
	private String couponPriceCN;
	
	public QcbEmployeeProductBuyHistoryVO() {
		super();
	}
	
	public QcbEmployeeProductBuyHistoryVO(QcbEmployeeHistory qeh) {
		this.uuid = qeh.getUuid();
		this.qcbEmployee = qeh.getQcbEmployee();
		if(qeh.getIncome().compareTo(BigDecimal.valueOf(0)) == 1){
			this.price = qeh.getIncome();
			this.priceCN = Utlity.numFormat4UnitDetail(qeh.getIncome());
		} else if (qeh.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			this.price = qeh.getPay();
			this.priceCN = Utlity.numFormat4UnitDetail(qeh.getPay());
		} else {
			this.price = BigDecimal.ZERO;
			this.priceCN = "0.00";
		}
		
		this.createtime = qeh.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToChinaString(qeh.getCreatetime());
		this.createtimeLessCN = Utlity.timeSpanToChinaStringLess(qeh.getCreatetime());
	}
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getQcbEmployee() {
		return qcbEmployee;
	}
	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getCreatetimeCN() {
		return createtimeCN;
	}
	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
	public String getCreatetimeLessCN() {
		return createtimeLessCN;
	}
	public void setCreatetimeLessCN(String createtimeLessCN) {
		this.createtimeLessCN = createtimeLessCN;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getPriceCN() {
		return priceCN;
	}
	public void setPriceCN(String priceCN) {
		this.priceCN = priceCN;
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
}
