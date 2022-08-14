package cn.product.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChannelAccountDaily implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6168242327602716685L;
	
	private String uuid;
	private BigDecimal amount;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}