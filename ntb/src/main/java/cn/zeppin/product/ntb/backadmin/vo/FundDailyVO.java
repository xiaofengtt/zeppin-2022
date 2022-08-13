package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.FundDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundDailyVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8722983870914500386L;
	private String uuid;
	private String fund;
	private BigDecimal netValue;
	private String statistime;	
	
	public FundDailyVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public FundDailyVO(FundDaily fundDaily) {
		this.uuid = fundDaily.getUuid();
		this.fund = fundDaily.getFund();
		this.netValue = fundDaily.getNetValue();
		this.statistime =  Utlity.timeSpanToDateString(fundDaily.getStatistime());
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
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
