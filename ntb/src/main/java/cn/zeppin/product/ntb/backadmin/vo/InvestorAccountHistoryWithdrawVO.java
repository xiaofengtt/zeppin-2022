/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.InvestorAccountHistory.InvestorAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @description 【数据对象】前台投资人账户明细
 */
public class InvestorAccountHistoryWithdrawVO implements Entity {

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
	private String orderMessage;
	
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
	
	private String bankcard;
	private String processingStatus;
	private String processingStatusCN;
	private String processCompanyAccount;
	private String processCompanyAccountName;
	private String processCreator;
	private String processCreatorName;
	private Timestamp processCreatetime;
	private String processCreatetimeCN;
	
	
	public InvestorAccountHistoryWithdrawVO() {
		super();
	}
	public InvestorAccountHistoryWithdrawVO(InvestorAccountHistory iah) {
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
			this.price = Utlity.numFormat4UnitDetail(iah.getIncome());
			
		} else if (iah.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			
			this.price = Utlity.numFormat4UnitDetail(iah.getPay());
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
		
		this.pay = iah.getPay();
		this.payCN = Utlity.numFormat4UnitDetailLess(iah.getPay());
		
		this.bankcard = iah.getBankcard() == null ? "" : iah.getBankcard();
		this.processCompanyAccount = iah.getProcessCompanyAccount() == null ? "" : iah.getProcessCompanyAccount();
		this.processCreatetime = iah.getProcessCreatetime();
		if(iah.getProcessCreatetime() != null){
			this.processCreatetimeCN = Utlity.timeSpanToString(iah.getProcessCreatetime());
		}
		this.processCreator = iah.getProcessCreator() == null ? "" : iah.getProcessCreator();
		this.processingStatus = iah.getProcessingStatus();
		if(iah.getProcessingStatus() != null){
			if(InvestorAccountHistoryProcessStatus.SUCCESS.equals(iah.getProcessingStatus())){
				this.processingStatusCN = "已处理";
			} else if(InvestorAccountHistoryProcessStatus.FAIL.equals(iah.getProcessingStatus())){
				this.processingStatusCN = "处理失败";
			} else if(InvestorAccountHistoryProcessStatus.PROCESSING.equals(iah.getProcessingStatus())){
				this.processingStatusCN = "处理中";
			} else if(InvestorAccountHistoryProcessStatus.UNPROCESS.equals(iah.getProcessingStatus())){
				this.processingStatusCN = "未处理";
			} else {
				this.processingStatusCN = "无";
			}
		} else {
			this.processingStatusCN = "无";
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
	
	public String getBankcard() {
		return bankcard;
	}
	
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	
	public String getProcessingStatus() {
		return processingStatus;
	}
	
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	
	public String getProcessCompanyAccount() {
		return processCompanyAccount;
	}
	
	public void setProcessCompanyAccount(String processCompanyAccount) {
		this.processCompanyAccount = processCompanyAccount;
	}
	
	public String getProcessCreator() {
		return processCreator;
	}
	
	public void setProcessCreator(String processCreator) {
		this.processCreator = processCreator;
	}
	
	public Timestamp getProcessCreatetime() {
		return processCreatetime;
	}
	
	public void setProcessCreatetime(Timestamp processCreatetime) {
		this.processCreatetime = processCreatetime;
	}
	
	public String getProcessingStatusCN() {
		return processingStatusCN;
	}
	
	public void setProcessingStatusCN(String processingStatusCN) {
		this.processingStatusCN = processingStatusCN;
	}
	
	public String getProcessCompanyAccountName() {
		return processCompanyAccountName;
	}
	
	public void setProcessCompanyAccountName(String processCompanyAccountName) {
		this.processCompanyAccountName = processCompanyAccountName;
	}
	
	public String getProcessCreatorName() {
		return processCreatorName;
	}
	
	public void setProcessCreatorName(String processCreatorName) {
		this.processCreatorName = processCreatorName;
	}
	
	public String getProcessCreatetimeCN() {
		return processCreatetimeCN;
	}
	
	public void setProcessCreatetimeCN(String processCreatetimeCN) {
		this.processCreatetimeCN = processCreatetimeCN;
	}
	
	public String getOrderMessage() {
		return orderMessage;
	}
	
	public void setOrderMessage(String orderMessage) {
		this.orderMessage = orderMessage;
	}
	
}
