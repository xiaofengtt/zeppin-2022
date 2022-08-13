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
	
	private String uuid;//id
	private String bankFinancialProduct;//银行理财产品ID
	private String bankFinancialProductPublish;//银行理财发布ID
	private String bankFinancialProductPublishName;//银行理财发布名称
	private BigDecimal amount;//投资额
	private BigDecimal redeemAmount;
	private BigDecimal investIncome;
	private BigDecimal returnCapital;
	private BigDecimal returnInterest;
	private BigDecimal platfomIncome;
	private String targetAnnualizedReturnRate;//目标年化收益
	private String valueDate;//起息日
	private String maturityDate;//到期日
	private String status;//产品状态
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
		this.bankFinancialProduct = bfpi.getBankFinancialProduct();
		this.bankFinancialProductPublish = bfpi.getBankFinancialProductPublish();
		this.amount = bfpi.getAmount();
		this.status = bfpi.getStatus();
		this.stage = bfpi.getStage();
		this.redeemAmount = bfpi.getRedeemAmount();
		this.investIncome = bfpi.getInvestIncome();
		this.returnCapital = bfpi.getReturnCapital();
		this.returnInterest = bfpi.getReturnInterest();
		this.platfomIncome = bfpi.getPlatfomIncome();
		if(BankFinancialProductInvestStage.UNSTART.equals(bfpi.getStage())){
			this.stageCN = "待投资";
		} else if (BankFinancialProductInvestStage.INCOME.equals(bfpi.getStage())) {
			this.stageCN = "收益中";
		} else if (BankFinancialProductInvestStage.FINISHED.equals(bfpi.getStage())) {
			this.stageCN = "已完成";
		}
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

	public String getBankFinancialProduct() {
		return bankFinancialProduct;
	}

	public void setBankFinancialProduct(String bankFinancialProduct) {
		this.bankFinancialProduct = bankFinancialProduct;
	}
	
	public String getBankFinancialProductPublish() {
		return bankFinancialProductPublish;
	}

	public void setBankFinancialProductPublish(String bankFinancialProductPublish) {
		this.bankFinancialProductPublish = bankFinancialProductPublish;
	}
	
	public String getBankFinancialProductPublishName() {
		return bankFinancialProductPublishName;
	}

	public void setBankFinancialProductPublishName(String bankFinancialProductPublishName) {
		this.bankFinancialProductPublishName = bankFinancialProductPublishName;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getRedeemAmount() {
		return redeemAmount;
	}
	
	public void setRedeemAmount(BigDecimal redeemAmount) {
		this.redeemAmount = redeemAmount;
	}
	
	public BigDecimal getInvestIncome() {
		return investIncome;
	}
	
	public void setInvestIncome(BigDecimal investIncome) {
		this.investIncome = investIncome;
	}
	
	public BigDecimal getReturnCapital() {
		return returnCapital;
	}
	
	public void setReturnCapital(BigDecimal returnCapital) {
		this.returnCapital = returnCapital;
	}
	
	public BigDecimal getReturnInterest() {
		return returnInterest;
	}
	
	public void setReturnInterest(BigDecimal returnInterest) {
		this.returnInterest = returnInterest;
	}
	
	public BigDecimal getPlatfomIncome() {
		return platfomIncome;
	}
	
	public void setPlatfomIncome(BigDecimal platfomIncome) {
		this.platfomIncome = platfomIncome;
	}
	
	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTargetAnnualizedReturnRate() {
		return targetAnnualizedReturnRate;
	}

	public void setTargetAnnualizedReturnRate(String targetAnnualizedReturnRate) {
		this.targetAnnualizedReturnRate = targetAnnualizedReturnRate;
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
