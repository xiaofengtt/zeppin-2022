/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyStage;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;


/**
 * @description 【数据对象】投资人购买记录
 */
public class InvestorProductBuyVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String investor;
	private String investorName;
	private String product;
	private String productName;
	private BigDecimal accountBalance;
	private BigDecimal totalRedeem;
	private BigDecimal totalAmount;
	private BigDecimal totalReturn;
	private String accountBalanceCN;
	private String totalRedeemCN;
	private String totalAmountCN;
	private String totalReturnCN;
	private String status;
	private String stage;
	private String stageCN;
	
	public InvestorProductBuyVO() {
		super();
	}
	public InvestorProductBuyVO(InvestorProductBuy ipbr) {
		this.uuid = ipbr.getUuid();
		this.investor = ipbr.getInvestor();
		this.product = ipbr.getProduct();
		this.accountBalance = ipbr.getAccountBalance();
		this.totalAmount = ipbr.getTotalAmount();
		this.totalRedeem = ipbr.getTotalRedeem();
		this.totalReturn = ipbr.getTotalReturn();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(ipbr.getAccountBalance());
		this.totalAmountCN = Utlity.numFormat4UnitDetail(ipbr.getTotalAmount());
		this.totalRedeemCN = Utlity.numFormat4UnitDetail(ipbr.getTotalRedeem());
		this.totalReturnCN = Utlity.numFormat4UnitDetail(ipbr.getTotalReturn());
		this.stage = ipbr.getStage();
		if(InvestorProductBuyStage.CONFIRMING.equals(ipbr.getStage())){
			this.stageCN = "确认中";
		} else if (InvestorProductBuyStage.BALANCE.equals(ipbr.getStage())) {
			this.stageCN = "还款中";
		} else if (InvestorProductBuyStage.PROFIT.equals(ipbr.getStage())) {
			this.stageCN = "持有中";
		} else if (BankFinancialProductPublishStage.FINISHED.equals(ipbr.getStage())) {
			this.stageCN = "已完成";
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getInvestor() {
		return investor;
	}
	
	public void setInvestor(String investor) {
		this.investor = investor;
	}
	
	public String getInvestorName() {
		return investorName;
	}
	
	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}
	
	public String getProduct() {
		return product;
	}
	
	public void setProduct(String product) {
		this.product = product;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
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
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
}
