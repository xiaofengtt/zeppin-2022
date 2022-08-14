package com.product.worldpay.vo.system;

import java.io.Serializable;
import java.math.BigInteger;

public class StatusCountVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String status;
	private BigInteger count;
	
	public StatusCountVO(){
		
	}
	
	public StatusCountVO(String status, BigInteger count){
		this.status = status;
		this.count = count;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public BigInteger getCount() {
		return count;
	}
	
	public void setCount(BigInteger count) {
		this.count = count;
	}
}
