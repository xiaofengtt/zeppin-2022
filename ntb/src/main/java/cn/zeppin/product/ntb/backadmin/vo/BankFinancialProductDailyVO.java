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
	
	public BankFinancialProductDailyVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BankFinancialProductDailyVO(BankFinancialProductDaily bfp) {
		this.uuid = bfp.getUuid();
		this.bankFinancialProduct = bfp.getBankFinancialProduct();
		this.netValue = bfp.getNetValue();
		this.statistime =  Utlity.timeSpanToDateString(bfp.getStatistime());
		
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

	
}
