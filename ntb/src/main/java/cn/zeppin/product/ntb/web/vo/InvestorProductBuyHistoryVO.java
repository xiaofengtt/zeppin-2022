/**
 * 
 */
package cn.zeppin.product.ntb.web.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.Coupon.CouponType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * 
 * @author Administrator
 *
 */
public class InvestorProductBuyHistoryVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6947703206763148941L;
	
	private String uuid;
	private String investor;
	
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
	
	public InvestorProductBuyHistoryVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvestorProductBuyHistoryVO(InvestorAccountHistory iah) {
		this.uuid = iah.getUuid();
		this.investor = iah.getInvestor();
		if(iah.getIncome().compareTo(BigDecimal.valueOf(0)) == 1){
			this.price = iah.getIncome();
			this.priceCN = Utlity.numFormat4UnitDetail(iah.getIncome());
		} else if (iah.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			this.price = iah.getPay();
			this.priceCN = Utlity.numFormat4UnitDetail(iah.getPay());
		} else {
			this.price = BigDecimal.ZERO;
			this.priceCN = "0.00";
		}
		
		this.createtime = iah.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToChinaString(iah.getCreatetime());
		this.createtimeLessCN = Utlity.timeSpanToChinaStringLess(iah.getCreatetime());
	}
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getInvestor() {
		return investor;
	}
	public void setInvestor(String investor) {
		this.investor = investor;
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
