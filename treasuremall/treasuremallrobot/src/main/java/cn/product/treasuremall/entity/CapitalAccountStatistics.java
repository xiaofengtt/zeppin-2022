package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

public class CapitalAccountStatistics implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1660451648331228809L;
	@Id
	private String capitalAccount;
	private BigDecimal balance;
	private BigDecimal dailySum;
	private BigDecimal totalRecharge;
	private BigDecimal totalWithdraw;
	private BigDecimal totalPayment;
	private Integer rechargeTimes;
	private Integer withdrawTimes;
	private Integer paymentTimes;
	public String getCapitalAccount() {
		return capitalAccount;
	}
	public void setCapitalAccount(String capitalAccount) {
		this.capitalAccount = capitalAccount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getDailySum() {
		return dailySum;
	}
	public void setDailySum(BigDecimal dailySum) {
		this.dailySum = dailySum;
	}
	public BigDecimal getTotalRecharge() {
		return totalRecharge;
	}
	public void setTotalRecharge(BigDecimal totalRecharge) {
		this.totalRecharge = totalRecharge;
	}
	public BigDecimal getTotalWithdraw() {
		return totalWithdraw;
	}
	public void setTotalWithdraw(BigDecimal totalWithdraw) {
		this.totalWithdraw = totalWithdraw;
	}
	public BigDecimal getTotalPayment() {
		return totalPayment;
	}
	public void setTotalPayment(BigDecimal totalPayment) {
		this.totalPayment = totalPayment;
	}
	public Integer getRechargeTimes() {
		return rechargeTimes;
	}
	public void setRechargeTimes(Integer rechargeTimes) {
		this.rechargeTimes = rechargeTimes;
	}
	public Integer getWithdrawTimes() {
		return withdrawTimes;
	}
	public void setWithdrawTimes(Integer withdrawTimes) {
		this.withdrawTimes = withdrawTimes;
	}
	public Integer getPaymentTimes() {
		return paymentTimes;
	}
	public void setPaymentTimes(Integer paymentTimes) {
		this.paymentTimes = paymentTimes;
	}
    
}