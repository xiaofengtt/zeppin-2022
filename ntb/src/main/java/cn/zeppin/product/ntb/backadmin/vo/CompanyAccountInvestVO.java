package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class CompanyAccountInvestVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4916478952518224940L;
	
	private String uuid;
	private String companyAccount;
	private String companyAccountName;
	private String bankFinancialProduct;
	private String bankFinancialProductName;
	private BigDecimal accountBalance;
	private BigDecimal totalAmount;
	private BigDecimal totalRedeem;
	private BigDecimal totalReturn;
	private BigDecimal returnRate;
	private String returnRateCN;
	private Timestamp maturityDate;
	private String maturityDateCN;
	private String accountBalanceCN;
	private String totalAmountCN;
	private String totalRedeemCN;
	private String totalReturnCN;
	private String stage;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public CompanyAccountInvestVO(){
		
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getCompanyAccount() {
		return companyAccount;
	}

	public String getCompanyAccountName() {
		return companyAccountName;
	}

	public void setCompanyAccountName(String companyAccountName) {
		this.companyAccountName = companyAccountName;
	}

	public String getBankFinancialProductName() {
		return bankFinancialProductName;
	}

	public void setBankFinancialProductName(String bankFinancialProductName) {
		this.bankFinancialProductName = bankFinancialProductName;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}

	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(accountBalance);
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
		this.totalAmountCN = Utlity.numFormat4UnitDetail(totalAmount);
	}

	public BigDecimal getTotalRedeem() {
		return totalRedeem;
	}

	public void setTotalRedeem(BigDecimal totalRedeem) {
		this.totalRedeem = totalRedeem;
		this.totalRedeemCN = Utlity.numFormat4UnitDetail(totalRedeem);
	}

	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
		this.totalReturnCN = Utlity.numFormat4UnitDetail(totalReturn);
	}

	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}

	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}

	public String getTotalRedeemCN() {
		return totalRedeemCN;
	}

	public void setTotalRedeemCN(String totalRedeemCN) {
		this.totalRedeemCN = totalRedeemCN;
	}

	public String getTotalReturnCN() {
		return totalReturnCN;
	}

	public void setTotalReturnCN(String totalReturnCN) {
		this.totalReturnCN = totalReturnCN;
	}

	public BigDecimal getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(BigDecimal returnRate) {
		this.returnRate = returnRate;
		this.returnRateCN = returnRate.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}

	public String getReturnRateCN() {
		return returnRateCN;
	}

	public void setReturnRateCN(String returnRateCN) {
		this.returnRateCN = returnRateCN;
	}

	public Timestamp getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Timestamp maturityDate) {
		this.maturityDate = maturityDate;
		this.maturityDateCN = Utlity.timeSpanToDateString(maturityDate);
	}

	public String getMaturityDateCN() {
		return maturityDateCN;
	}

	public void setMaturityDateCN(String maturityDateCN) {
		this.maturityDateCN = maturityDateCN;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
		this.createtimeCN = Utlity.timeSpanToDateString(createtime);
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
}
