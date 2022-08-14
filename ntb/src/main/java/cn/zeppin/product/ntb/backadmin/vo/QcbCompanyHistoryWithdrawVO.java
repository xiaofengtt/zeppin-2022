/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccountHistory.QcbCompanyAccountHistoryType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @description 【数据对象】企财宝企业提现数据
 */
public class QcbCompanyHistoryWithdrawVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String qcbCompanyAccount;
	private String qcbCompanyAccountName;
	
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
	
	
	public QcbCompanyHistoryWithdrawVO() {
		super();
	}
	public QcbCompanyHistoryWithdrawVO(QcbCompanyAccountHistory qcah) {
		this.uuid = qcah.getUuid();
		this.qcbCompanyAccount = qcah.getQcbCompany();
		this.order = qcah.getOrderId();
		this.orderNum = qcah.getOrderNum();
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
		this.orderType = qcah.getOrderType();
		if(QcbCompanyAccountHistoryOrderType.PAY_TYPE_ALIPAY.equals(qcah.getOrderType())){
			this.orderTypeCN = "支付宝支付";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_BALANCE.equals(qcah.getOrderType())) {
			this.orderTypeCN = "余额支付";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_BANKCARD.equals(qcah.getOrderType())) {
			this.orderTypeCN = "银行卡支付";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_CHANPAY.equals(qcah.getOrderType())) {
			this.orderTypeCN = "银行卡支付";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_WECHART.equals(qcah.getOrderType())) {
			this.orderTypeCN = "微信支付";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_COMPANY.equals(qcah.getOrderType())) {
			this.orderTypeCN = "平台结算";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_ALIPAY_TRANSFER.equals(qcah.getOrderType())) {
			this.orderTypeCN = "支付宝转账";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_REAPAL.equals(qcah.getOrderType())) {
			this.orderTypeCN = "银行卡支付";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_BANK_TRANSFER.equals(qcah.getOrderType())) {
			this.orderTypeCN = "银行转账";
		} else if (QcbCompanyAccountHistoryOrderType.PAY_TYPE_QCB_COMPANY.equals(qcah.getOrderType())) {
			this.orderTypeCN = "企财宝企业转账";
		} else {
			this.orderTypeCN = "—";
		}
		this.priceflag = true;
		this.type = qcah.getType();
		if(QcbCompanyAccountHistoryType.WITHDRAW.equals(qcah.getType())){
			this.typeCN = "提现";
			this.remark = "账户提现";
			this.priceflag = false;
		} else if (QcbCompanyAccountHistoryType.INCOME.equals(qcah.getType())) {
			this.typeCN = "充值";
			this.remark = "账户充值";
			this.priceflag = true;
		} else if (QcbCompanyAccountHistoryType.BUY.equals(qcah.getType())) {
			this.typeCN = "购买";
			this.remark = "购买";
			this.priceflag = false;
		} else if (QcbCompanyAccountHistoryType.DIVIDEND.equals(qcah.getType())) {
			this.typeCN = "收益";
			this.remark = "理财收益";
			this.priceflag = true;
		} else if (QcbCompanyAccountHistoryType.RETURN.equals(qcah.getType())) {
			this.typeCN = "返还本金";
			this.remark = "返还本金";
			this.priceflag = true;
		} else {
			this.typeCN = "";
		}
		if(!Utlity.checkStringNull(qcah.getRemark())){
			this.remark = qcah.getRemark();
		}
		this.accountBalance = qcah.getAccountBalance();
		this.accountBalanceCN = qcah.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		
		if(qcah.getIncome().compareTo(BigDecimal.valueOf(0)) == 1){
			this.price = Utlity.numFormat4UnitDetail(qcah.getIncome());
		} else if (qcah.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			this.price = Utlity.numFormat4UnitDetail(qcah.getPay());
		} else {
			this.price = "0.00";
		}
		
		
		this.status = qcah.getStatus();
		if(QcbCompanyAccountHistoryStatus.FAIL.equals(qcah.getStatus())){
			this.statusCN = "交易失败";
		} else if (QcbCompanyAccountHistoryStatus.CLOSED.equals(qcah.getStatus())) {
			this.statusCN = "交易关闭";
		} else if (QcbCompanyAccountHistoryStatus.CANCELLED.equals(qcah.getStatus())) {
			this.statusCN = "交易取消";
		} else if (QcbCompanyAccountHistoryStatus.TRANSACTING.equals(qcah.getStatus())) {
			this.statusCN = "交易中";
		} else  if (QcbCompanyAccountHistoryStatus.SUCCESS.equals(qcah.getStatus())) {
			this.statusCN = "交易成功";
		} else {
			this.statusCN = "交易错误";
		}
		
		this.createtime = qcah.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qcah.getCreatetime());
		this.createtimeLessCN = Utlity.timeSpanToChinaStringLess(qcah.getCreatetime());
		this.time = Utlity.timeSpanToChinaStringesLess(qcah.getCreatetime());
		
		this.pay = qcah.getPay();
		this.payCN = Utlity.numFormat4UnitDetailLess(qcah.getPay());
		
		this.bankcard = qcah.getBankcard() == null ? "" : qcah.getBankcard();
		this.processCompanyAccount = qcah.getProcessCompanyAccount() == null ? "" : qcah.getProcessCompanyAccount();
		this.processCreatetime = qcah.getProcessCreatetime();
		if(qcah.getProcessCreatetime() != null){
			this.processCreatetimeCN = Utlity.timeSpanToString(qcah.getProcessCreatetime());
		}
		this.processCreator = qcah.getProcessCreator() == null ? "" : qcah.getProcessCreator();
		this.processingStatus = qcah.getProcessingStatus();
		if(qcah.getProcessingStatus() != null){
			if(QcbCompanyAccountHistoryProcessStatus.SUCCESS.equals(qcah.getProcessingStatus())){
				this.processingStatusCN = "已处理";
			} else if(QcbCompanyAccountHistoryProcessStatus.FAIL.equals(qcah.getProcessingStatus())){
				this.processingStatusCN = "处理失败";
			} else if(QcbCompanyAccountHistoryProcessStatus.PROCESSING.equals(qcah.getProcessingStatus())){
				this.processingStatusCN = "处理中";
			} else if(QcbCompanyAccountHistoryProcessStatus.UNPROCESS.equals(qcah.getProcessingStatus())){
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
	
	public String getQcbCompanyAccount() {
		return qcbCompanyAccount;
	}
	
	public void setQcbCompanyAccount(String qcbCompanyAccount) {
		this.qcbCompanyAccount = qcbCompanyAccount;
	}
	
	public String getQcbCompanyAccountName() {
		return qcbCompanyAccountName;
	}
	
	public void setQcbCompanyAccountName(String qcbCompanyAccountName) {
		this.qcbCompanyAccountName = qcbCompanyAccountName;
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
