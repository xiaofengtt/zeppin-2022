package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

public class FrontUserAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8110148901943548074L;
	
	@Id
	private String frontUser;
	private BigDecimal balance;
	private BigDecimal balanceLock;
	private BigDecimal voucherBalance;
	private String accountStatus;
	
	private BigDecimal totalRecharge;
	private BigDecimal totalWithdraw;
	private BigDecimal totalPayment;
	private BigDecimal totalWinning;
	private BigDecimal totalDelivery;
	private BigDecimal totalExchange;
	
	private Integer paymentTimes;
	private Integer winningTimes;
	private Integer rechargeTimes;
	private Integer withdrawTimes;
	private Integer deliveryTimes;
	private Integer exchangeTimes;
	
	private BigDecimal scoreBalance;
	
    public class FrontUserAccountStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceLock() {
		return balanceLock;
	}

	public void setBalanceLock(BigDecimal balanceLock) {
		this.balanceLock = balanceLock;
	}

	public BigDecimal getVoucherBalance() {
		return voucherBalance;
	}

	public void setVoucherBalance(BigDecimal voucherBalance) {
		this.voucherBalance = voucherBalance;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
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

	public BigDecimal getTotalWinning() {
		return totalWinning;
	}

	public void setTotalWinning(BigDecimal totalWinning) {
		this.totalWinning = totalWinning;
	}

	public BigDecimal getTotalDelivery() {
		return totalDelivery;
	}

	public void setTotalDelivery(BigDecimal totalDelivery) {
		this.totalDelivery = totalDelivery;
	}

	public BigDecimal getTotalExchange() {
		return totalExchange;
	}

	public void setTotalExchange(BigDecimal totalExchange) {
		this.totalExchange = totalExchange;
	}

	public Integer getPaymentTimes() {
		return paymentTimes;
	}

	public void setPaymentTimes(Integer paymentTimes) {
		this.paymentTimes = paymentTimes;
	}

	public Integer getWinningTimes() {
		return winningTimes;
	}

	public void setWinningTimes(Integer winningTimes) {
		this.winningTimes = winningTimes;
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

	public Integer getDeliveryTimes() {
		return deliveryTimes;
	}

	public void setDeliveryTimes(Integer deliveryTimes) {
		this.deliveryTimes = deliveryTimes;
	}

	public Integer getExchangeTimes() {
		return exchangeTimes;
	}

	public void setExchangeTimes(Integer exchangeTimes) {
		this.exchangeTimes = exchangeTimes;
	}

	public BigDecimal getScoreBalance() {
		return scoreBalance;
	}

	public void setScoreBalance(BigDecimal scoreBalance) {
		this.scoreBalance = scoreBalance;
	}
    
}