/**
 * 
 */
package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;


public class StatisticsFinanceDaily implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4722869216939633868L;
	
	private String uuid;
	private String dailyDate;
	private BigDecimal dPayment;
	private BigDecimal recharge;
	private BigDecimal withdraw;
	private BigDecimal winning;
	private BigDecimal delivery;
	
	public StatisticsFinanceDaily() {
		super();
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}

	public BigDecimal getdPayment() {
		return dPayment;
	}

	public void setdPayment(BigDecimal dPayment) {
		this.dPayment = dPayment;
	}

	public BigDecimal getRecharge() {
		return recharge;
	}

	public void setRecharge(BigDecimal recharge) {
		this.recharge = recharge;
	}

	public BigDecimal getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(BigDecimal withdraw) {
		this.withdraw = withdraw;
	}

	public BigDecimal getWinning() {
		return winning;
	}

	public void setWinning(BigDecimal winning) {
		this.winning = winning;
	}

	public BigDecimal getDelivery() {
		return delivery;
	}

	public void setDelivery(BigDecimal delivery) {
		this.delivery = delivery;
	}
	
}
