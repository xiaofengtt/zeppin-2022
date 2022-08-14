/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;
import java.util.Map;

import cn.zeppin.product.ntb.core.entity.Coupon.CouponStatus;
import cn.zeppin.product.ntb.core.entity.CouponStrategy;
import cn.zeppin.product.ntb.core.entity.base.BaseEntity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe 2016年2月8日
 */

public class CouponStrategyVO extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8025156049869106400L;
	
	private String uuid;
	private String strategyIdentification;
	private Timestamp expiryDate;
	private String expiryDateCN;
	private String coupon;
	private Map<String, Object> couponMap;
	
	private String creator;
	private String creatorCN;
	private Timestamp createtime;
	private String createtimeCN;
	private String status;
	private String statusCN;
	
	
	public CouponStrategyVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponStrategyVO(CouponStrategy cp) {
		this.uuid = cp.getUuid();
		this.strategyIdentification = cp.getStrategyIdentification();
		this.expiryDate = cp.getExpiryDate();
		if(cp.getExpiryDate() != null){
			this.expiryDateCN = Utlity.timeSpanToString(cp.getExpiryDate());
		} else {
			this.expiryDateCN = "";
		}
		this.coupon = cp.getCoupon();
		this.couponMap = JSONUtils.json2map(cp.getCoupon());
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

	
	public String getStrategyIdentification() {
		return strategyIdentification;
	}
	

	public void setStrategyIdentification(String strategyIdentification) {
		this.strategyIdentification = strategyIdentification;
	}
	

	public String getCoupon() {
		return coupon;
	}
	

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	
	public Map<String, Object> getCouponMap() {
		return couponMap;
	}
	

	public void setCouponMap(Map<String, Object> couponMap) {
		this.couponMap = couponMap;
	}

}
