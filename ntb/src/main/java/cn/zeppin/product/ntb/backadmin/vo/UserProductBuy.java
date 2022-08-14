/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】投资人购买记录
 */

public class UserProductBuy extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6121638623124350691L;
	
	private String uuid;
	private String user;
	private String userType;
	private String product;
	private BigDecimal totalAmount;
	private BigDecimal totalReturn;
	private Timestamp createtime;
	private String stage;
	
	private BigDecimal accountBalance;
	private BigDecimal totalRedeem;
	private String type;
	
	public class UserProductBuyUserType{
		public final static String INVESTOR = "investor";
		public final static String QCBEMPLOYEE = "qcbEmployee";
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProduct() {
		return product;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public BigDecimal getTotalReturn() {
		return totalReturn;
	}
	
	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}
	
	public String getStage() {
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public BigDecimal getTotalRedeem() {
		return totalRedeem;
	}

	public void setTotalRedeem(BigDecimal totalRedeem) {
		this.totalRedeem = totalRedeem;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
