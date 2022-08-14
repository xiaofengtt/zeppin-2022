package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductDailyVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8722983870914500386L;
	private String uuid;
	private String bankFinancialProduct;
	private BigDecimal netValue;
	private String statistime;	
	private String creator;
	private String creatorName;
	
	public BankFinancialProductDailyVO() {
		super();
	}
	
	public BankFinancialProductDailyVO(BankFinancialProductDaily bfp) {
		this.uuid = bfp.getUuid();
		this.bankFinancialProduct = bfp.getBankFinancialProduct();
		this.netValue = bfp.getNetValue();
		this.statistime =  Utlity.timeSpanToDateString(bfp.getStatistime());
		this.creator = bfp.getCreator();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}

	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
	}

	public BigDecimal getNetValue() {
		return netValue;
	}

	public void setNetValue(BigDecimal netValue) {
		this.netValue = netValue;
	}

	public String getStatistime() {
		return statistime;
	}

	public void setStatistime(String statistime) {
		this.statistime = statistime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
}
