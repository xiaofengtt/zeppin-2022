package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyAccountFeeVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4474928969951847392L;
	private String uuid;
	private String merchantId;
	private String name;
	
	private BigDecimal feeTicket;
	private String feeTicketCN;
	
	public QcbCompanyAccountFeeVO(){
		
	}
	
	public QcbCompanyAccountFeeVO(QcbCompanyAccount company){
		this.uuid = company.getUuid();
		this.merchantId = company.getMerchantId();
		this.name = company.getName();
		
		this.feeTicket = company.getFeeTicket();
		if(company.getFeeTicket() != null){
			this.feeTicketCN = Utlity.numFormat4UnitDetail(company.getFeeTicket().multiply(BigDecimal.valueOf(100)));
		} else {
			this.feeTicketCN = "--";
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public BigDecimal getFeeTicket() {
		return feeTicket;
	}
	

	public void setFeeTicket(BigDecimal feeTicket) {
		this.feeTicket = feeTicket;
	}
	

	public String getFeeTicketCN() {
		return feeTicketCN;
	}
	

	public void setFeeTicketCN(String feeTicketCN) {
		this.feeTicketCN = feeTicketCN;
	}
}
