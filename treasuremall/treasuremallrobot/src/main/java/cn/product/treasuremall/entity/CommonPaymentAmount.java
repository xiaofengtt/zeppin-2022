package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

public class CommonPaymentAmount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9072102911810521929L;
	
	@Id
    private String uuid;
	private Integer sort;
	private BigDecimal amount;
    private String remark;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}