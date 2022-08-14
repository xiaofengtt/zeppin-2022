package cn.product.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CompanyAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5031360582166508241L;
	
	private String uuid;
	private BigDecimal balance;
	private BigDecimal balanceLock;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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

}