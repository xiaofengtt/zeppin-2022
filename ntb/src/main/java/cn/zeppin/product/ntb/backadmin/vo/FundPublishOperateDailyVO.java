package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.FundPublishDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundPublishOperateDailyVO implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6523655645060790355L;
	
	private String type;
	private String typeCN;
	private BigDecimal oldValue;
	private String statistimeCN;
	private FundPublishDaily fundPublishDaily;
	
	public FundPublishOperateDailyVO() {
		super();
	}
	
	public FundPublishOperateDailyVO(FundPublishDaily bfpd, String type) {
		this.fundPublishDaily = bfpd;
		this.type = type;
		if("add".equals(type)){
			this.typeCN = "添加";
		}else if ("edit".equals(type)){
			this.typeCN = "修改";
		}else if ("delete".equals(type)){
			this.typeCN = "删除";
		}
		if(bfpd.getStatistime() != null){
			this.statistimeCN = Utlity.timeSpanToDateString(bfpd.getStatistime());
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCN() {
		return typeCN;
	}

	public void setTypeCN(String typeCN) {
		this.typeCN = typeCN;
	}
	
	public FundPublishDaily getFundPublishDaily() {
		return fundPublishDaily;
	}

	public void setFundPublishDaily(FundPublishDaily fundPublishDaily) {
		this.fundPublishDaily = fundPublishDaily;
	}

	public BigDecimal getOldValue() {
		return oldValue;
	}
	
	public void setOldValue(BigDecimal oldValue) {
		this.oldValue = oldValue;
	}

	public String getStatistimeCN() {
		return statistimeCN;
	}
	
	public void setStatistimeCN(String statistimeCN) {
		this.statistimeCN = statistimeCN;
	}
}
