package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import cn.zeppin.product.ntb.core.entity.PlatformAccount;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class PlatformAccountVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private BigDecimal investment;
	private BigDecimal totalAmount;
	private BigDecimal totalRedeem;
	private BigDecimal totalReturn;
	private String investmentCN;
	private String totalAmountCN;
	private String totalRedeemCN;
	private String totalReturnCN;
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private BigDecimal totalBalance;
	private String totalBalanceCN;
	private BigDecimal InvestorInvestment;
	private String InvestorInvestmentCN;
	private BigDecimal qcbEmpTotalBalance;
	private String qcbEmpTotalBalanceCN;
	
	public PlatformAccountVO(){
		
	}
	
	public PlatformAccountVO(PlatformAccount pa){
		this.uuid = pa.getUuid();
		this.investment = pa.getInvestment();
		this.investmentCN = Utlity.numFormat4UnitDetail(pa.getInvestment().setScale(2, RoundingMode.FLOOR));
		this.totalAmount = pa.getTotalAmount();
		this.totalAmountCN = Utlity.numFormat4UnitDetail(pa.getTotalAmount().setScale(2, RoundingMode.FLOOR));
		this.totalRedeem = pa.getTotalRedeem();
		this.totalRedeemCN = Utlity.numFormat4UnitDetail(pa.getTotalRedeem().setScale(2, RoundingMode.FLOOR));
		this.totalReturn = pa.getTotalReturn();
		this.totalReturnCN = Utlity.numFormat4UnitDetail(pa.getTotalReturn().setScale(2, RoundingMode.FLOOR));
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public BigDecimal getInvestment() {
		return investment;
	}
	
	public void setInvestment(BigDecimal investment) {
		this.investment = investment;
	}

	public BigDecimal getTotalRedeem() {
		return totalRedeem;
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setTotalRedeem(BigDecimal totalRedeem) {
		this.totalRedeem = totalRedeem;
	}

	public String getTotalRedeemCN() {
		return totalRedeemCN;
	}

	public void setTotalRedeemCN(String totalRedeemCN) {
		this.totalRedeemCN = totalRedeemCN;
	}

	public String getInvestmentCN() {
		return investmentCN;
	}

	public void setInvestmentCN(String investmentCN) {
		this.investmentCN = investmentCN;
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

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getTotalBalanceCN() {
		return totalBalanceCN;
	}

	public void setTotalBalanceCN(String totalBalanceCN) {
		this.totalBalanceCN = totalBalanceCN;
	}

	public BigDecimal getInvestorInvestment() {
		return InvestorInvestment;
	}

	public void setInvestorInvestment(BigDecimal investorInvestment) {
		InvestorInvestment = investorInvestment;
	}

	public String getInvestorInvestmentCN() {
		return InvestorInvestmentCN;
	}

	public void setInvestorInvestmentCN(String investorInvestmentCN) {
		InvestorInvestmentCN = investorInvestmentCN;
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
