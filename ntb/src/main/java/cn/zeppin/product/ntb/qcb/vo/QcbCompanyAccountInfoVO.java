package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbCompanyAccountInfoVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5406932723849372511L;
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private BigDecimal accountPay;
	private String accountPayCN;
	private BigDecimal totalInvest;
	private String totalInvestCN;
	private BigDecimal totalReturn;
	private String totalReturnCN;
	
	
	public QcbCompanyAccountInfoVO(){
		
	}
	
	public QcbCompanyAccountInfoVO(QcbCompanyAccount company){
		this.accountPay = company.getAccountPay();
		this.accountPayCN = Utlity.numFormat4UnitDetail(company.getAccountPay());
		this.accountBalance = company.getAccountBalance();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(company.getAccountBalance());
		this.totalInvest = company.getTotalInvest();
		this.totalInvestCN = Utlity.numFormat4UnitDetail(company.getTotalInvest());
		this.totalReturn = company.getTotalReturn();
		this.totalReturnCN = Utlity.numFormat4UnitDetail(company.getTotalReturn());
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
}
