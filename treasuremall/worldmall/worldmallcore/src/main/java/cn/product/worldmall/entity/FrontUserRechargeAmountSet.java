package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

public class FrontUserRechargeAmountSet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7815374718761658669L;
	@Id
    private String uuid;
	private BigDecimal amount;
	private BigDecimal dAmount;
	private BigDecimal giveDAmount;
	private String status;
	private Integer sort;
	private Boolean isPreferential;
    
    public class FrontUserRechargeAmountSetStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }
    
    
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

	public BigDecimal getdAmount() {
		return dAmount;
	}

	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getIsPreferential() {
		return isPreferential;
	}

	public void setIsPreferential(Boolean isPreferential) {
		this.isPreferential = isPreferential;
	}

	public BigDecimal getGiveDAmount() {
		return giveDAmount;
	}

	public void setGiveDAmount(BigDecimal giveDAmount) {
		this.giveDAmount = giveDAmount;
	}

}