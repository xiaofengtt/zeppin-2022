package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductDaily;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductOperateDailyVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8722983870914500386L;
	private String type;
	private String typeCN;
	private BigDecimal oldValue;
	private String statistimeCN;
	private BankFinancialProductDaily bankFinancialProductDaily;
	
	public BankFinancialProductOperateDailyVO() {
		super();
	}
	
	public BankFinancialProductOperateDailyVO(BankFinancialProductDaily bfpd, String type) {
		this.bankFinancialProductDaily = bfpd;
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
	
	public BankFinancialProductDaily getBankFinancialProductDaily() {
		return bankFinancialProductDaily;
	}

	public void setBankFinancialProductDaily(
			BankFinancialProductDaily bankFinancialProductDaily) {
		this.bankFinancialProductDaily = bankFinancialProductDaily;
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
