package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestRecords;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductInvestRecords.BankFinancialProductInvestRecordsType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class BankFinancialProductInvestRecordsVO implements Entity {
	
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
	private String companyAccount;
	private String companyAccountName;
	private BigDecimal totalAmount;
	private String totalAmountCN;
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private String type;
	private String typeCN;
	private String remark;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public BankFinancialProductInvestRecordsVO() {
		super();
	}
	
	public BankFinancialProductInvestRecordsVO(BankFinancialProductInvestRecords bfpir) {
		this.uuid = bfpir.getUuid();
		this.product = bfpir.getBankFinancialProduct();
		this.productPublish = bfpir.getBankFinancialProductPublish();
		this.companyAccount = bfpir.getCompanyAccount();
		this.totalAmount = bfpir.getTotalAmount();
		this.totalAmountCN =  Utlity.numFormat4UnitDetail(bfpir.getTotalAmount());
		this.accountBalance = bfpir.getAccountBalace();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(bfpir.getAccountBalace());
		this.type = bfpir.getType();
		if (BankFinancialProductInvestRecordsType.INVEST.equals(bfpir.getType())) {
			this.typeCN = "投资";
		} else if (BankFinancialProductInvestRecordsType.REDEEM.equals(bfpir.getType())) {
			this.typeCN = "赎回";
		}
		this.creator = bfpir.getCreator();
		this.createtime = bfpir.getCreatetime();
		if(bfpir.getCreatetime() != null && !"".equals(bfpir.getCreatetime())){
			this.createtimeCN =  Utlity.timeSpanToDateString(bfpir.getCreatetime());
		}else{
			this.createtimeCN =  "";
		}
		this.remark = bfpir.getRemark();
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}
