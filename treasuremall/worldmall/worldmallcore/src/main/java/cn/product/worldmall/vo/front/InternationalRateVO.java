package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.worldmall.entity.InternationalRate;

public class InternationalRateVO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -315478650211338630L;
	
	private Timestamp updatetime;
	private String currencyCode;
	private String currencyName;
	private String baseCurrency;
	private BigDecimal realRate;	
	private String symbol;
	
	public InternationalRateVO() {
		super();
	}
	
	public InternationalRateVO(InternationalRate ir) {
		this.updatetime = ir.getUpdatetime();
		this.baseCurrency = ir.getBaseCurrency();
		this.currencyCode = ir.getCurrencyCode();
		this.currencyName = ir.getCurrencyName();
		this.realRate = ir.getRealRate();
		this.symbol = ir.getCurrencyCode();
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
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

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public BigDecimal getRealRate() {
		return realRate;
	}

	public void setRealRate(BigDecimal realRate) {
		this.realRate = realRate;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}