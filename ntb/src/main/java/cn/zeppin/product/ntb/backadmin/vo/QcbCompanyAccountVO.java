package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountAuthStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount.QcbCompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyAccountVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4474928969951847392L;
	private String uuid;
	private String merchantId;
	private String name;
	
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private BigDecimal accountPay;
	private String accountPayCN;
	private BigDecimal totalInvest;
	private String totalInvestCN;
	private BigDecimal totalReturn;
	private String totalReturnCN;
	private BigDecimal totalAmount;
	private String totalAmountCN;
	
	private String status;
	private String statusCN;
	
	private String logo;
	private String logoUrl;
	
	private String authStatus;
	private String authStatusCN;
	
	private BigDecimal feeTicket;
	private String feeTicketCN;
	
	private BigDecimal qcbEmpTotalBalance;
	private String qcbEmpTotalBalanceCN;
	
	public QcbCompanyAccountVO(){
		
	}
	
	public QcbCompanyAccountVO(QcbCompanyAccount company){
		this.uuid = company.getUuid();
		this.merchantId = company.getMerchantId();
		this.name = company.getName();
		
		this.status = company.getStatus();
		if(QcbCompanyAccountStatus.UNAUTH.equals(company.getStatus())){
			this.statusCN = "未认证";
		} else if (QcbCompanyAccountStatus.NORMAL.equals(company.getStatus())) {
			this.statusCN = "已认证";
		} else if (QcbCompanyAccountStatus.NOPASS.equals(company.getStatus())) {
			this.statusCN = "认证未通过";
		} else if (QcbCompanyAccountStatus.DELETED.equals(company.getStatus())) {
			this.statusCN = "已删除";
		} else if (QcbCompanyAccountStatus.UNCHECK.equals(company.getStatus())) {
			this.statusCN = "审核中";
		} else {
			this.statusCN = "--";
		}
		
		this.authStatus = company.getAuthStatus();
		if(QcbCompanyAccountAuthStatus.UNAUTH.equals(company.getAuthStatus())){
			this.authStatusCN = "未认证";
		} else if (QcbCompanyAccountAuthStatus.NORMAL.equals(company.getAuthStatus())) {
			this.authStatusCN = "已认证";
		} else if (QcbCompanyAccountAuthStatus.NOPASS.equals(company.getAuthStatus())) {
			this.authStatusCN = "认证未通过";
		} else if (QcbCompanyAccountAuthStatus.UNCHECK.equals(company.getAuthStatus())) {
			this.authStatusCN = "审核中";
		} else {
			this.authStatusCN = "--";
		}
		
		this.accountPay = company.getAccountPay();
		this.accountPayCN = Utlity.numFormat4UnitDetail(company.getAccountPay());
		this.accountBalance = company.getAccountBalance();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(company.getAccountBalance());
		this.totalInvest = company.getTotalInvest();
		this.totalInvestCN = Utlity.numFormat4UnitDetail(company.getTotalInvest());
		this.totalReturn = company.getTotalReturn();
		this.totalReturnCN = Utlity.numFormat4UnitDetail(company.getTotalReturn());
		this.totalAmount = this.accountBalance.add(this.totalInvest);
		this.totalAmountCN = Utlity.numFormat4UnitDetail(this.totalAmount);
		this.feeTicket = company.getFeeTicket();
		if(company.getFeeTicket() != null){
			this.feeTicketCN = Utlity.numFormat4UnitDetail(company.getFeeTicket().multiply(BigDecimal.valueOf(100)));
		} else {
			this.feeTicketCN = "--";
		}
		
		this.logo = company.getLogo();
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

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}

	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}

	public BigDecimal getAccountPay() {
		return accountPay;
	}

	public void setAccountPay(BigDecimal accountPay) {
		this.accountPay = accountPay;
	}

	public String getAccountPayCN() {
		return accountPayCN;
	}

	public void setAccountPayCN(String accountPayCN) {
		this.accountPayCN = accountPayCN;
	}

	public BigDecimal getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(BigDecimal totalInvest) {
		this.totalInvest = totalInvest;
	}

	public String getTotalInvestCN() {
		return totalInvestCN;
	}

	public void setTotalInvestCN(String totalInvestCN) {
		this.totalInvestCN = totalInvestCN;
	}

	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}

	public String getTotalReturnCN() {
		return totalReturnCN;
	}

	public void setTotalReturnCN(String totalReturnCN) {
		this.totalReturnCN = totalReturnCN;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public String getAuthStatusCN() {
		return authStatusCN;
	}

	public void setAuthStatusCN(String authStatusCN) {
		this.authStatusCN = authStatusCN;
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


	public BigDecimal getQcbEmpTotalBalance() {
		return qcbEmpTotalBalance;
	}
	

	public void setQcbEmpTotalBalance(BigDecimal qcbEmpTotalBalance) {
		this.qcbEmpTotalBalance = qcbEmpTotalBalance;
	}
	

	public String getQcbEmpTotalBalanceCN() {
		return qcbEmpTotalBalanceCN;
	}
	

	public void setQcbEmpTotalBalanceCN(String qcbEmpTotalBalanceCN) {
		this.qcbEmpTotalBalanceCN = qcbEmpTotalBalanceCN;
	}
}
