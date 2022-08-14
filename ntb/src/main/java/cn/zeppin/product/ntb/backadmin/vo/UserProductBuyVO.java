/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.backadmin.vo.UserProductBuy.UserProductBuyUserType;
import cn.zeppin.product.ntb.core.entity.BankFinancialProductPublish.BankFinancialProductPublishStage;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyStage;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;


/**
 * @description 【数据对象】投资人购买记录
 */
public class UserProductBuyVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String user;
	private String userName;
	private String userType;
	private String userTypeCN;
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
	
	public UserProductBuyVO() {
		super();
	}
	public UserProductBuyVO(UserProductBuy upb) {
		this.uuid = upb.getUuid();
		this.user = upb.getUser();
		this.userType = upb.getUserType();
		if(UserProductBuyUserType.INVESTOR.equals(upb.getUserType())){
			this.userTypeCN = "牛投理财用户";
		}else if(UserProductBuyUserType.QCBEMPLOYEE.equals(upb.getUserType())){
			this.userTypeCN = "企财宝员工";
		}
		this.product = upb.getProduct();
		this.accountBalance = upb.getAccountBalance();
		this.totalAmount = upb.getTotalAmount();
		this.totalRedeem = upb.getTotalRedeem();
		this.totalReturn = upb.getTotalReturn();
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(upb.getAccountBalance());
		this.totalAmountCN = Utlity.numFormat4UnitDetail(upb.getTotalAmount());
		this.totalRedeemCN = Utlity.numFormat4UnitDetail(upb.getTotalRedeem());
		this.totalReturnCN = Utlity.numFormat4UnitDetail(upb.getTotalReturn());
		this.stage = upb.getStage();
		if(InvestorProductBuyStage.CONFIRMING.equals(upb.getStage())){
			this.stageCN = "确认中";
		} else if (InvestorProductBuyStage.BALANCE.equals(upb.getStage())) {
			this.stageCN = "还款中";
		} else if (InvestorProductBuyStage.PROFIT.equals(upb.getStage())) {
			this.stageCN = "持有中";
		} else if (BankFinancialProductPublishStage.FINISHED.equals(upb.getStage())) {
			this.stageCN = "已完成";
		}
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getUserTypeCN() {
		return userTypeCN;
	}
	
	public void setUserTypeCN(String userTypeCN) {
		this.userTypeCN = userTypeCN;
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
