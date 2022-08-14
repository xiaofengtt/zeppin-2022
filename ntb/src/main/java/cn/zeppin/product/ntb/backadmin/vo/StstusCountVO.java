package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigInteger;

import cn.zeppin.product.ntb.core.entity.base.Entity;

public class StstusCountVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String status;
	private BigInteger count;
	
	public StstusCountVO(){
		
	}
	
	public StstusCountVO(String status, BigInteger count){
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
