package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundPublishDailyVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8722983870914500386L;
	private String uuid;
	private String fundPublish;
	private BigDecimal netValue;
	private String statistime;	
	
	public FundPublishDailyVO() {
		super();
	}
	
	public FundPublishDailyVO(FundPublishDaily fundPublishDaily) {
		this.uuid = fundPublishDaily.getUuid();
		this.fundPublish = fundPublishDaily.getFundPublish();
		this.netValue = fundPublishDaily.getNetValue();
		this.statistime =  Utlity.timeSpanToDateString(fundPublishDaily.getStatistime());
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFundPublish() {
		return fundPublish;
	}

	public void setFundPublish(String fundPublish) {
		this.fundPublish = fundPublish;
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
