package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.CompanyAccount;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountStatus;
import cn.zeppin.product.ntb.core.entity.CompanyAccount.CompanyAccountType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class CompanyAccountDetailVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private String accountName;
	private String accountNum;
	private String accountNumStar;
	private String companyName;
	private String type;
	private String typeCN;
	private String bank;
	private String bankName;
	private String bankIconUrl;
	private String branchBank;
	private String branchBankName;
	private BigDecimal investment;
	private String investmentCN;
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private BigDecimal totalRedeem;
	private String totalRedeemCN;
	private BigDecimal totalReturn;
	private String totalReturnCN;
	private String status;
	private String statusCN;
	
	public CompanyAccountDetailVO(){
		
	}
	
	public CompanyAccountDetailVO(CompanyAccount companyAccount){
		this.uuid = companyAccount.getUuid();
		this.accountName = companyAccount.getAccountName();
		this.accountNum = companyAccount.getAccountNum();
		this.accountNumStar = Utlity.getStarAccountNum(companyAccount.getAccountNum());
		this.companyName = companyAccount.getCompanyName();
		this.type = companyAccount.getType();
		if(CompanyAccountType.COLLECT.equals(companyAccount.getType())){
			this.typeCN = "募集户";
		}else if(CompanyAccountType.INVEST.equals(companyAccount.getType())){
			this.typeCN = "投资户";
		}else if(CompanyAccountType.THIRD.equals(companyAccount.getType())){
			this.typeCN = "第三方";
		}else if(CompanyAccountType.REDEEM.equals(companyAccount.getType())){
			this.typeCN = "结算户";
		}
		this.bank = companyAccount.getBank();
		this.branchBank = companyAccount.getBranchBank();
		this.investment = companyAccount.getInvestment();
		this.investmentCN = Utlity.numFormat4UnitDetail(companyAccount.getInvestment());
		this.accountBalance = companyAccount.getAccountBalance();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(companyAccount.getAccountBalance());
		this.totalRedeem = companyAccount.getTotalRedeem();
		this.totalRedeemCN = Utlity.numFormat4UnitDetail(companyAccount.getTotalRedeem());
		this.totalReturn = companyAccount.getTotalReturn();
		this.totalReturnCN = Utlity.numFormat4UnitDetail(companyAccount.getTotalReturn());
		this.status = companyAccount.getStatus();
		if(CompanyAccountStatus.NORMAL.equals(companyAccount.getStatus())){
			this.statusCN = "正常";
		}else if(CompanyAccountStatus.DISABLE.equals(companyAccount.getStatus())){
			this.statusCN = "停用";
		}else if(CompanyAccountStatus.DELETED.equals(companyAccount.getStatus())){
			this.statusCN = "删除";
		}
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountNum() {
		return accountNum;
	}
	
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
		this.accountNumStar = Utlity.getStarAccountNum(accountNum);
	}
	
	public String getAccountNumStar() {
		return accountNumStar;
	}

	public void setAccountNumStar(String accountNumStar) {
		this.accountNumStar = accountNumStar;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankIconUrl() {
		return bankIconUrl;
	}

	public void setBankIconUrl(String bankIconUrl) {
		this.bankIconUrl = bankIconUrl;
	}

	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getInvestmentCN() {
		return investmentCN;
	}

	public void setInvestmentCN(String investmentCN) {
		this.investmentCN = investmentCN;
	}

	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}

	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}

	public BigDecimal getTotalRedeem() {
		return totalRedeem;
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
	
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
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
}
