package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.FundInvest;
import cn.zeppin.product.ntb.core.entity.FundInvest.FundInvestType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class FundInvestVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -319610007975398756L;
	
	private String uuid;
	private String fund;
	private String fundName;
	private String fundPublish;
	private String fundPublishName;
	private String companyAccount;
	private String companyAccountName;
	private BigDecimal accountBalance;
	private BigDecimal totalAmount;
	private String accountBalanceCN;
	private String totalAmountCN;
	private String type;//交易类型
	private String typeCN;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public FundInvestVO() {
		super();
	}
	
	public FundInvestVO(FundInvest bfpi) {
		this.uuid = bfpi.getUuid();
		this.fund = bfpi.getFund();
		this.fundPublish = bfpi.getFundPublish();
		this.companyAccount = bfpi.getCompanyAccount();
		this.type = bfpi.getType();
		if (FundInvestType.INVEST.equals(bfpi.getType())) {
			this.typeCN = "投资";
		} else if (FundInvestType.REDEEM.equals(bfpi.getType())) {
			this.typeCN = "赎回";
		}
		this.accountBalance = bfpi.getAccountBalance();
		this.totalAmount = bfpi.getTotalAmount();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(bfpi.getAccountBalance());
		this.totalAmountCN = Utlity.numFormat4UnitDetail(bfpi.getTotalAmount());
		
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

	public String getTotalAmountCN() {
		return totalAmountCN;
	}

	public void setTotalAmountCN(String totalAmountCN) {
		this.totalAmountCN = totalAmountCN;
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

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundPublish() {
		return fundPublish;
	}

	public void setFundPublish(String fundPublish) {
		this.fundPublish = fundPublish;
	}

	public String getFundPublishName() {
		return fundPublishName;
	}

	public void setFundPublishName(String fundPublishName) {
		this.fundPublishName = fundPublishName;
	}
}
