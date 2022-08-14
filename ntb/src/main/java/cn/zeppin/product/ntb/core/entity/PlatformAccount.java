/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】平台账户账户
 */

@Entity
@Table(name = "platform_account")
public class PlatformAccount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private BigDecimal investment;
	private BigDecimal totalAmount;
	private BigDecimal totalRedeem;
	private BigDecimal totalReturn;
	
	public class PlatformAccountUuid{
		public final static String TOTAL = "59e780c7-472e-44d5-ac7c-3c30413b7b88";
		public final static String BALANCE = "ffffffff-ffff-ffff-ffff-ffffffffffff";
		public final static String INVESTOR = "00000000-0000-0000-0000-000000000000";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "investment", nullable = false, length = 20)
	public BigDecimal getInvestment() {
		return investment;
	}
	
	public void setInvestment(BigDecimal investment) {
		this.investment = investment;
	}
	
	@Column(name = "total_amount", nullable = false, length = 20)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Column(name = "total_redeem", nullable = false, length = 20)
	public BigDecimal getTotalRedeem() {
		return totalRedeem;
	}

	public void setTotalRedeem(BigDecimal totalRedeem) {
		this.totalRedeem = totalRedeem;
	}
	
	@Column(name = "total_return", nullable = false, length = 20)
	public BigDecimal getTotalReturn() {
		return totalReturn;
	}
	
	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}
}
