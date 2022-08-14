/**
 * 
 */
package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeProductBuy.QcbEmployeeProductBuyStage;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;


/**
 * @description 【数据对象】企财宝员工购买记录明细
 */
public class QcbEmployeeProductBuyDetailsVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String qcbEmployee;
	
	private String product;
	private String productName;
	private String bankName;
	private String productScode;
	
	private String incomeDate;//预计到账时间
	private String valueDate;//起息日
	private String maturityDate;//到期日
	
	private String targetAnnualizedReturnRate;//目标年化收益率
	private String term;//产品期限
	
	private Boolean flagBuy;
	
	private BigDecimal price;
	private String priceCN;
	private BigDecimal totalReturn;
	private String totalReturnCN;//实际收益
	
	private Timestamp paytime;
	private String paytimeCN;
	
//	private String bill;
	private String orderNum;
	private String orderType;
	private String orderTypeCN;
	
	private String status;
	
	private String stage;
	private String stageCN;
	
	private String agreementName;
	private String agreementUrl;
	private String realReturnRate;//实际收益
	private String realReturnRateCN;//实际收益
	
	private List<QcbEmployeeProductBuyHistoryVO> accountHistory;
	
	public QcbEmployeeProductBuyDetailsVO() {
		super();
	}
	
	public QcbEmployeeProductBuyDetailsVO(QcbEmployeeProductBuy ipbr) {
		this.uuid = ipbr.getUuid();
		this.qcbEmployee = ipbr.getQcbEmployee();
		this.product = ipbr.getProduct();
		this.price = ipbr.getTotalAmount();
		if(ipbr.getTotalAmount() != null){
			this.priceCN = Utlity.numFormat4UnitDetailLess(ipbr.getTotalAmount());
		}else{
			this.priceCN = "0.00";
		}
		this.totalReturn = ipbr.getTotalReturn();
		if(ipbr.getTotalReturn() != null){
			this.totalReturnCN = Utlity.numFormat4UnitDetail(ipbr.getTotalReturn());
		}else{
			this.totalReturnCN = "0.00";
		}
		this.paytime = ipbr.getCreatetime();
		this.paytimeCN = Utlity.timeSpanToString(ipbr.getCreatetime());
//		this.bill = ipbr.getBill();
		this.stage = ipbr.getStage();
		if(QcbEmployeeProductBuyStage.CONFIRMING.equals(ipbr.getStage())){
			this.stageCN = "确认中";
		} else if (QcbEmployeeProductBuyStage.BALANCE.equals(ipbr.getStage())) {
			this.stageCN = "还款中";
		} else if (QcbEmployeeProductBuyStage.PROFIT.equals(ipbr.getStage())) {
			this.stageCN = "持有中";
		} else if (QcbEmployeeProductBuyStage.FINISHED.equals(ipbr.getStage())) {
			this.stageCN = "已完成";
		}
//		this.status = ipbr.getStatus();
		
//		long oneday = 24*60*60*1000;
//		Timestamp time = ipbr.getCreatetime();
//		Timestamp returntime = new Timestamp(time.getTime()+oneday);
//		this.incomeDate = Utlity.timeSpanToDateString(returntime);
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getQcbEmployee() {
		return qcbEmployee;
	}
	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
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
	public String getTargetAnnualizedReturnRate() {
		return targetAnnualizedReturnRate;
	}
	public void setTargetAnnualizedReturnRate(String targetAnnualizedReturnRate) {
		this.targetAnnualizedReturnRate = targetAnnualizedReturnRate;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderTypeCN() {
		return orderTypeCN;
	}
	public void setOrderTypeCN(String orderTypeCN) {
		this.orderTypeCN = orderTypeCN;
	}
	public String getPaytimeCN() {
		return paytimeCN;
	}
	public void setPaytimeCN(String paytimeCN) {
		this.paytimeCN = paytimeCN;
	}
	public String getAgreementName() {
		return agreementName;
	}
	public void setAgreementName(String agreementName) {
		this.agreementName = agreementName;
	}
	public String getAgreementUrl() {
		return agreementUrl;
	}
	public void setAgreementUrl(String agreementUrl) {
		this.agreementUrl = agreementUrl;
	}
	public Boolean getFlagBuy() {
		return flagBuy;
	}
	public void setFlagBuy(Boolean flagBuy) {
		this.flagBuy = flagBuy;
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
	public List<QcbEmployeeProductBuyHistoryVO> getAccountHistory() {
		return accountHistory;
	}
	public void setAccountHistory(List<QcbEmployeeProductBuyHistoryVO> accountHistory) {
		this.accountHistory = accountHistory;
	}
	
}
