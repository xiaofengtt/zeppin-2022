package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvest.BankFinancialProductInvestStage;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductInvestVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4491225193452390207L;
	
	private String uuid;
	private String product;
	private String productName;
	private String productPublish;
	private String productPublishName;
	private String productPublishTargetReturn;
	private String productPublishMaturityDate;
	private String productTargetReturn;
	private String productMaturityDate;
	private String companyAccount;
	private String companyAccountName;
	private BigDecimal accountBalance;
	private BigDecimal totalRedeem;
	private BigDecimal totalAmount;
	private BigDecimal totalReturn;
	private String accountBalanceCN;
	private String totalRedeemCN;
	private String totalAmountCN;
	private String totalReturnCN;
	private String stage;//产品阶段
	private String stageCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public BankFinancialProductInvestVO() {
		super();
	}
	
	public BankFinancialProductInvestVO(BankFinancialProductInvest bfpi) {
		this.uuid = bfpi.getUuid();
		this.product = bfpi.getBankFinancialProduct();
		this.productPublish = bfpi.getBankFinancialProductPublish();
		this.stage = bfpi.getStage();
		if (BankFinancialProductInvestStage.NORMAL.equals(bfpi.getStage())) {
			this.stageCN = "收益中";
		} else if (BankFinancialProductInvestStage.FINISHED.equals(bfpi.getStage())) {
			this.stageCN = "已完成";
		}
		this.accountBalance = bfpi.getAccountBalance();
		this.totalAmount = bfpi.getTotalAmount();
		this.totalRedeem = bfpi.getTotalRedeem();
		this.totalReturn = bfpi.getTotalReturn();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(bfpi.getAccountBalance());
		this.totalAmountCN = Utlity.numFormat4UnitDetail(bfpi.getTotalAmount());
		this.totalRedeemCN = Utlity.numFormat4UnitDetail(bfpi.getTotalRedeem());
		this.totalReturnCN = Utlity.numFormat4UnitDetail(bfpi.getTotalReturn());
		
		this.creator = bfpi.getCreator();
		this.createtime = bfpi.getCreatetime();
		if(bfpi.getCreatetime() != null && !"".equals(bfpi.getCreatetime())){
			this.createtimeCN =  Utlity.timeSpanToDateString(bfpi.getCreatetime());
		}else{
			this.createtimeCN =  "";
		}
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	public String getProductPublish() {
		return productPublish;
	}

	public void setProductPublish(String productPublish) {
		this.productPublish = productPublish;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPublishName() {
		return productPublishName;
	}

	public void setProductPublishName(String productPublishName) {
		this.productPublishName = productPublishName;
	}
	
	public String getProductPublishTargetReturn() {
		return productPublishTargetReturn;
	}

	public void setProductPublishTargetReturn(String productPublishTargetReturn) {
		this.productPublishTargetReturn = productPublishTargetReturn;
	}

	public String getProductPublishMaturityDate() {
		return productPublishMaturityDate;
	}

	public void setProductPublishMaturityDate(String productPublishMaturityDate) {
		this.productPublishMaturityDate = productPublishMaturityDate;
	}

	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	
	public String getCompanyAccountName() {
		return companyAccountName;
	}

	public void setCompanyAccountName(String companyAccountName) {
		this.companyAccountName = companyAccountName;
	}

	public BigDecimal getTotalRedeem() {
		return totalRedeem;
	}

	public void setTotalRedeem(BigDecimal totalRedeem) {
		this.totalRedeem = totalRedeem;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
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

	public String getTotalRedeemCN() {
		return totalRedeemCN;
	}

	public void setTotalRedeemCN(String totalRedeemCN) {
		this.totalRedeemCN = totalRedeemCN;
	}

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
	}

	public String getTotalReturnCN() {
		return totalReturnCN;
	}

	public void setTotalReturnCN(String totalReturnCN) {
		this.totalReturnCN = totalReturnCN;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getStageCN() {
		return stageCN;
	}

	public void setStageCN(String stageCN) {
		this.stageCN = stageCN;
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
	}
	
	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getProductTargetReturn() {
		return productTargetReturn;
	}

	public void setProductTargetReturn(String productTargetReturn) {
		this.productTargetReturn = productTargetReturn;
	}

	public String getProductMaturityDate() {
		return productMaturityDate;
	}

	public void setProductMaturityDate(String productMaturityDate) {
		this.productMaturityDate = productMaturityDate;
	}
	
}
