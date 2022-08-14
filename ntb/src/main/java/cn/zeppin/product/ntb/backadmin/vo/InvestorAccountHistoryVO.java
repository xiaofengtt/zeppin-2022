/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @description 【数据对象】前台投资人账户明细
 */
public class InvestorAccountHistoryVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String investor;
	private String investorName;
	
	private String order;
	private String orderNum;
	private String orderType;
	private String orderTypeCN;
	
	private String application;
	
	private String type;
	private String typeCN;
	
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	
	private Boolean priceflag;
	private String price;

	private BigDecimal pay;
	private String payCN;
	
	private String status;
	private String statusCN;
	private Timestamp createtime;
	private String createtimeCN;
	private String createtimeLessCN;
	
	private String time;
	private String remark;
	
	private String product;
	private String productType;
	private BankFinancialProductPublishVO productInfo;
	
	public InvestorAccountHistoryVO() {
		super();
	}
	public InvestorAccountHistoryVO(InvestorAccountHistory iah) {
		this.uuid = iah.getUuid();
		this.investor = iah.getInvestor();
		this.order = iah.getOrderId();
		this.orderNum = iah.getOrderNum();
		String apply = this.orderNum.substring(0, 1);
		if(Utlity.BILL_DEVICE_WECHAT.equals(apply)){
			this.application = "微信小程序";
		} else if (Utlity.BILL_DEVICE_ANDROID.equals(apply)) {
			this.application = "Android App";
		} else if (Utlity.BILL_DEVICE_IOS.equals(apply)) {
			this.application = "iOS App";
		} else if (Utlity.BILL_DEVICE_WEB.equals(apply)) {
			this.application = "网站";
		} else if (Utlity.BILL_DEVICE_ALIPAY.equals(apply)) {
			this.application = "支付宝钱包-转账";
		} else if (Utlity.BILL_DEVICE_REDPACKET.equals(apply)) {
			this.application = "活动红包";
		} else {
			this.application = "-";
		}
		this.orderType = iah.getOrderType();
		if(InvestorAccountHistoryOrderType.PAY_TYPE_ALIPAY.equals(iah.getOrderType())){
			this.orderTypeCN = "支付宝";
		} else if (InvestorAccountHistoryOrderType.PAY_TYPE_WECHART.equals(iah.getOrderType())) {
			this.orderTypeCN = "微信支付";
		} else if (InvestorAccountHistoryOrderType.PAY_TYPE_CHANPAY.equals(iah.getOrderType())) {
			this.orderTypeCN = "银行卡(畅捷支付)";
		} else if (InvestorAccountHistoryOrderType.PAY_TYPE_REAPAL.equals(iah.getOrderType())) {
			this.orderTypeCN = "银行卡(融宝支付)";
		} else if (InvestorAccountHistoryOrderType.PAY_TYPE_BANKCARD.equals(iah.getOrderType())) {
			this.orderTypeCN = "银行卡";
		} else if (InvestorAccountHistoryOrderType.PAY_TYPE_BALANCE.equals(iah.getOrderType())) {
			this.orderTypeCN = "余额";
		} else {
			this.orderTypeCN = "-";
		}
		this.priceflag = true;
		this.type = iah.getType();
		if(InvestorAccountHistoryType.WITHDRAW.equals(iah.getType())){
			this.typeCN = "提现";
			this.remark = "账户提现";
			this.priceflag = false;
		} else if (InvestorAccountHistoryType.INCOME.equals(iah.getType())) {
			this.typeCN = "充值";
			this.remark = "账户充值";
			this.priceflag = true;
		} else if (InvestorAccountHistoryType.BUY.equals(iah.getType())) {
			this.typeCN = "购买";
			this.remark = "购买";
			this.priceflag = false;
		} else if (InvestorAccountHistoryType.DIVIDEND.equals(iah.getType())) {
			this.typeCN = "收益";
			this.remark = "理财收益";
			this.priceflag = true;
		} else if (InvestorAccountHistoryType.RETURN.equals(iah.getType())) {
			this.typeCN = "返还本金";
			this.remark = "返还本金";
			this.priceflag = true;
		} else if (InvestorAccountHistoryType.REDPACKET.equals(iah.getType())) {
			this.typeCN = "现金红包";
			this.remark = "现金红包";
			this.priceflag = true;
		} else if (InvestorAccountHistoryType.CURRENT_BUY.equals(iah.getType())) {
			this.typeCN = "活期盈转入";
			this.remark = "活期盈转入";
			this.priceflag = false;
		} else if (InvestorAccountHistoryType.CURRENT_RETURN.equals(iah.getType())) {
			this.typeCN = "活期盈转出";
			this.remark = "活期盈转出";
			this.priceflag = true;
		} else {
			this.typeCN = "";
		}
		this.accountBalance = iah.getAccountBalance();
		this.accountBalanceCN = iah.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		
		if(iah.getIncome().compareTo(BigDecimal.valueOf(0)) == 1){
			this.price = iah.getIncome().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			
		} else if (iah.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			
			this.price = iah.getPay().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} else {
			this.price = "0.00";
		}
		
		
		this.status = iah.getStatus();
		if(InvestorAccountHistoryStatus.FAIL.equals(iah.getStatus())){
			this.statusCN = "交易失败";
		} else if (InvestorAccountHistoryStatus.CLOSED.equals(iah.getStatus())) {
			this.statusCN = "交易关闭";
		} else if (InvestorAccountHistoryStatus.CANCELLED.equals(iah.getStatus())) {
			this.statusCN = "交易取消";
		} else if (InvestorAccountHistoryStatus.TRANSACTING.equals(iah.getStatus())) {
			this.statusCN = "交易中";
		} else  if (InvestorAccountHistoryStatus.SUCCESS.equals(iah.getStatus())) {
			this.statusCN = "交易成功";
		} else {
			this.statusCN = "交易错误";
		}
		
		this.createtime = iah.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(iah.getCreatetime());
		this.createtimeLessCN = Utlity.timeSpanToChinaStringLess(iah.getCreatetime());
		this.time = Utlity.timeSpanToChinaStringesLess(iah.getCreatetime());
		
		this.product = iah.getProduct();
		this.productType = iah.getProductType();
		this.pay = iah.getPay();
		this.payCN = Utlity.numFormat4UnitDetailLess(iah.getPay());
	}
	
	public InvestorAccountHistoryVO(QcbEmployeeHistory qeh) {
		this.uuid = qeh.getUuid();
		this.investor = qeh.getQcbEmployee();
		this.order = qeh.getOrderId();
		this.orderNum = qeh.getOrderNum();
		String apply = this.orderNum.substring(0, 1);
		if(Utlity.BILL_DEVICE_WECHAT.equals(apply)){
			this.application = "微信小程序";
		} else if (Utlity.BILL_DEVICE_ANDROID.equals(apply)) {
			this.application = "Android App";
		} else if (Utlity.BILL_DEVICE_IOS.equals(apply)) {
			this.application = "iOS App";
		} else if (Utlity.BILL_DEVICE_WEB.equals(apply)) {
			this.application = "网站";
		} else if (Utlity.BILL_DEVICE_ALIPAY.equals(apply)) {
			this.application = "支付宝钱包-转账";
		} else if (Utlity.BILL_DEVICE_REDPACKET.equals(apply)) {
			this.application = "活动红包";
		} else {
			this.application = "-";
		}
		this.orderType = qeh.getOrderType();
		if(QcbEmployeeHistoryOrderType.PAY_TYPE_ALIPAY.equals(qeh.getOrderType())){
			this.orderTypeCN = "支付宝";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_WECHART.equals(qeh.getOrderType())) {
			this.orderTypeCN = "微信支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_CHANPAY.equals(qeh.getOrderType())) {
			this.orderTypeCN = "银行卡(畅捷支付)";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL.equals(qeh.getOrderType())) {
			this.orderTypeCN = "银行卡(融宝支付)";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_BANKCARD.equals(qeh.getOrderType())) {
			this.orderTypeCN = "银行卡";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_BALANCE.equals(qeh.getOrderType())) {
			this.orderTypeCN = "余额";
		} else {
			this.orderTypeCN = "-";
		}
		this.priceflag = true;
		this.type = qeh.getType();
		if(QcbEmployeeHistoryType.WITHDRAW.equals(qeh.getType())){
			this.typeCN = "提现";
			this.remark = "账户提现";
			this.priceflag = false;
		} else if (QcbEmployeeHistoryType.INCOME.equals(qeh.getType())) {
			this.typeCN = "充值";
			this.remark = "账户充值";
			this.priceflag = true;
		} else if (QcbEmployeeHistoryType.BUY.equals(qeh.getType())) {
			this.typeCN = "购买";
			this.remark = "购买";
			this.priceflag = false;
		} else if (QcbEmployeeHistoryType.DIVIDEND.equals(qeh.getType())) {
			this.typeCN = "收益";
			this.remark = "理财收益";
			this.priceflag = true;
		} else if (QcbEmployeeHistoryType.RETURN.equals(qeh.getType())) {
			this.typeCN = "返还本金";
			this.remark = "返还本金";
			this.priceflag = true;
		} else if (QcbEmployeeHistoryType.PAYROLL.equals(qeh.getType())) {
			this.typeCN = "福利发放";
			this.remark = "福利发放";
			this.priceflag = true;
		} else if (QcbEmployeeHistoryType.CURRENT_BUY.equals(qeh.getType())) {
			this.typeCN = "活期盈转入";
			this.remark = "活期盈转入";
			this.priceflag = false;
		} else if (QcbEmployeeHistoryType.CURRENT_RETURN.equals(qeh.getType())) {
			this.typeCN = "活期盈转出";
			this.remark = "活期盈转出";
			this.priceflag = true;
		} else {
			this.typeCN = "";
		}
		this.accountBalance = qeh.getAccountBalance();
		this.accountBalanceCN = qeh.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		
		if(qeh.getIncome().compareTo(BigDecimal.valueOf(0)) == 1){
			this.price = qeh.getIncome().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
			
		} else if (qeh.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			
			this.price = qeh.getPay().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} else {
			this.price = "0.00";
		}
		
		
		this.status = qeh.getStatus();
		if(InvestorAccountHistoryStatus.FAIL.equals(qeh.getStatus())){
			this.statusCN = "交易失败";
		} else if (InvestorAccountHistoryStatus.CLOSED.equals(qeh.getStatus())) {
			this.statusCN = "交易关闭";
		} else if (InvestorAccountHistoryStatus.CANCELLED.equals(qeh.getStatus())) {
			this.statusCN = "交易取消";
		} else if (InvestorAccountHistoryStatus.TRANSACTING.equals(qeh.getStatus())) {
			this.statusCN = "交易中";
		} else  if (InvestorAccountHistoryStatus.SUCCESS.equals(qeh.getStatus())) {
			this.statusCN = "交易成功";
		} else {
			this.statusCN = "交易错误";
		}
		
		this.createtime = qeh.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qeh.getCreatetime());
		this.createtimeLessCN = Utlity.timeSpanToChinaStringLess(qeh.getCreatetime());
		this.time = Utlity.timeSpanToChinaStringesLess(qeh.getCreatetime());
		
		this.product = qeh.getProduct();
		this.productType = qeh.getProductType();
		this.pay = qeh.getPay();
		this.payCN = Utlity.numFormat4UnitDetailLess(qeh.getPay());
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
	
	public String getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getOrderType() {
		return orderType;
	}
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public String getStatusCN() {
		return statusCN;
	}
	
	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}
	
	public String getCreatetimeCN() {
		return createtimeCN;
	}
	
	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
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
	
	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}
	
	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public Boolean getPriceflag() {
		return priceflag;
	}
	
	public void setPriceflag(Boolean priceflag) {
		this.priceflag = priceflag;
	}
	
	public String getCreatetimeLessCN() {
		return createtimeLessCN;
	}
	
	public void setCreatetimeLessCN(String createtimeLessCN) {
		this.createtimeLessCN = createtimeLessCN;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getOrderTypeCN() {
		return orderTypeCN;
	}
	
	public void setOrderTypeCN(String orderTypeCN) {
		this.orderTypeCN = orderTypeCN;
	}
	
	public String getOrderNum() {
		return orderNum;
	}
	
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
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
	
	public String getProductType() {
		return productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public BankFinancialProductPublishVO getProductInfo() {
		return productInfo;
	}
	
	public void setProductInfo(BankFinancialProductPublishVO productInfo) {
		this.productInfo = productInfo;
	}
	
	public BigDecimal getPay() {
		return pay;
	}
	
	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}
	
	public String getPayCN() {
		return payCN;
	}
	
	public void setPayCN(String payCN) {
		this.payCN = payCN;
	}
	
}
