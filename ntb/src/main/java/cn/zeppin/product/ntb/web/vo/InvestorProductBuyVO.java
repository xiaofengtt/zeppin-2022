/**
 * 
 */
package cn.zeppin.product.ntb.web.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.InvestorProductBuy;
import cn.zeppin.product.ntb.core.entity.InvestorProductBuy.InvestorProductBuyStage;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;


/**
 * @description 【数据对象】前台投资人购买记录明细
 */
public class InvestorProductBuyVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String investor;
	
	private String product;
	private String productName;
	private String bankName;
	private String iconColorUrl;
	private String productScode;
	
	private String incomeDate;//预计到账时间
	private String valueDate;//起息日
	private String maturityDate;//到期日
	
	private String targetAnnualizedReturnRate;//目标年化收益率
	
	private BigDecimal price;
	private String priceCN;
	private BigDecimal totalReturne;
	private String totalReturn;//实际收益
	
	private Timestamp paytime;
	private String paytimeCN;
//	private String bill;
	
	private String status;
	
	private String stage;
	private String stageCN;
	
	private String realReturnRate;//实际收益
	private String realReturnRateCN;//实际收益
	
	private Boolean flagCashCoupon;//是否使用现金增值券
	private Boolean flagInterestsCoupon;//是否使用加息券
	
	
	public InvestorProductBuyVO() {
		super();
	}
	public InvestorProductBuyVO(InvestorProductBuy ipbr) {
		this.uuid = ipbr.getUuid();
		this.investor = ipbr.getInvestor();
		this.product = ipbr.getProduct();
		this.price = ipbr.getTotalAmount();
		if(ipbr.getTotalAmount() != null){
			this.priceCN = Utlity.numFormat4UnitDetailLess(ipbr.getTotalAmount());
		}else{
			this.priceCN = "0.00";
		}
		this.paytime = ipbr.getCreatetime();
		this.paytimeCN = Utlity.timeSpanToString(ipbr.getCreatetime());
//		this.bill = ipbr.getBill();
		this.stage = ipbr.getStage();
		if(InvestorProductBuyStage.CONFIRMING.equals(ipbr.getStage())){
			this.stageCN = "确认中";
		} else if (InvestorProductBuyStage.BALANCE.equals(ipbr.getStage())) {
			this.stageCN = "还款中";
		} else if (InvestorProductBuyStage.PROFIT.equals(ipbr.getStage())) {
			this.stageCN = "持有中";
		} else if (InvestorProductBuyStage.FINISHED.equals(ipbr.getStage())) {
			this.stageCN = "已完成";
		}
		this.totalReturne = ipbr.getTotalReturn();
//		this.totalReturn = Utlity.numFormat4UnitDetail(ipbr.getTotalReturn());
		if(ipbr.getTotalReturn() != null){
			this.totalReturn = Utlity.numFormat4UnitDetail(ipbr.getTotalReturn());
		}else{
			this.totalReturn = "0.00";
		}
//		this.status = ipbr.getStatus();
		//计算预计到账时间--当前规则是购买时间延后一天
//		long oneday = 24*60*60*1000;
//		Timestamp time = ipbr.getCreatetime();
//		Timestamp returntime = new Timestamp(time.getTime()+oneday);
//		this.incomeDate = Utlity.timeSpanToDateString(returntime);
		this.flagCashCoupon = false;
		this.flagInterestsCoupon = false;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getProductScode() {
		return productScode;
	}
	public void setProductScode(String productScode) {
		this.productScode = productScode;
	}
	public String getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(String incomeDate) {
		this.incomeDate = incomeDate;
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
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getPriceCN() {
		return priceCN;
	}
	public void setPriceCN(String priceCN) {
		this.priceCN = priceCN;
	}
	public String getTotalReturn() {
		return totalReturn;
	}
	public void setTotalReturn(String totalReturn) {
		this.totalReturn = totalReturn;
	}
	public Timestamp getPaytime() {
		return paytime;
	}
	public void setPaytime(Timestamp paytime) {
		this.paytime = paytime;
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
	public String getPaytimeCN() {
		return paytimeCN;
	}
	public void setPaytimeCN(String paytimeCN) {
		this.paytimeCN = paytimeCN;
	}
	public String getIconColorUrl() {
		return iconColorUrl;
	}
	public void setIconColorUrl(String iconColorUrl) {
		this.iconColorUrl = iconColorUrl;
	}
	public String getTargetAnnualizedReturnRate() {
		return targetAnnualizedReturnRate;
	}
	public void setTargetAnnualizedReturnRate(String targetAnnualizedReturnRate) {
		this.targetAnnualizedReturnRate = targetAnnualizedReturnRate;
	}
	public BigDecimal getTotalReturne() {
		return totalReturne;
	}
	public void setTotalReturne(BigDecimal totalReturne) {
		this.totalReturne = totalReturne;
	}
	public String getRealReturnRate() {
		return realReturnRate;
	}
	public void setRealReturnRate(String realReturnRate) {
		this.realReturnRate = realReturnRate;
	}
	public String getRealReturnRateCN() {
		return realReturnRateCN;
	}
	public void setRealReturnRateCN(String realReturnRateCN) {
		this.realReturnRateCN = realReturnRateCN;
	}
	public Boolean getFlagCashCoupon() {
		return flagCashCoupon;
	}
	public void setFlagCashCoupon(Boolean flagCashCoupon) {
		this.flagCashCoupon = flagCashCoupon;
	}
	public Boolean getFlagInterestsCoupon() {
		return flagInterestsCoupon;
	}
	public void setFlagInterestsCoupon(Boolean flagInterestsCoupon) {
		this.flagInterestsCoupon = flagInterestsCoupon;
	}
}
