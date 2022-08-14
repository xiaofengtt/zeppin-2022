package cn.product.worldmall.entity.rate;

import java.io.Serializable;

public class DailyCurrencyRate implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3166872511039786608L;
	
	private String baseCurrency;
	private String currencyCode;
	private String currencyName;
	private String realRate;
	private String baseRate;
	
	
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getRealRate() {
		return realRate;
	}
	public void setRealRate(String realRate) {
		this.realRate = realRate;
	}
	public String getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}
}