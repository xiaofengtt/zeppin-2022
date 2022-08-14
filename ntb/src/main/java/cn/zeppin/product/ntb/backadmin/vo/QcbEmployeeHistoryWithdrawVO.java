/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryProcessStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @description 【数据对象】企财宝员工提现数据
 */
public class QcbEmployeeHistoryWithdrawVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String qcbEmployee;
	private String qcbEmployeeName;
	
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
	
	
	public QcbEmployeeHistoryWithdrawVO() {
		super();
	}
	public QcbEmployeeHistoryWithdrawVO(QcbEmployeeHistory qeh) {
		this.uuid = qeh.getUuid();
		this.qcbEmployee = qeh.getQcbEmployee();
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
			this.orderTypeCN = "支付宝支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_BALANCE.equals(qeh.getOrderType())) {
			this.orderTypeCN = "平台余额支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_BANKCARD.equals(qeh.getOrderType())) {
			this.orderTypeCN = "银行卡支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_CHANPAY.equals(qeh.getOrderType())) {
//			this.orderTypeCN = "畅捷快捷支付";
			this.orderTypeCN = "银行卡支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_WECHART.equals(qeh.getOrderType())) {
			this.orderTypeCN = "微信支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_COMPANY.equals(qeh.getOrderType())) {
			this.orderTypeCN = "平台结算";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_ALIPAY_TRANSFER.equals(qeh.getOrderType())) {
			this.orderTypeCN = "支付宝转账";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL.equals(qeh.getOrderType())) {
			this.orderTypeCN = "银行卡支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_QCB_COMPANY.equals(qeh.getOrderType())) {
			this.orderTypeCN = "企财宝企业转账";
		} else {
			this.orderTypeCN = "无";
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
		}  else {
			this.typeCN = "";
		}
		this.accountBalance = qeh.getAccountBalance();
		this.accountBalanceCN = qeh.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		
		
		if(qeh.getIncome().compareTo(BigDecimal.valueOf(0)) == 1){
			this.price = Utlity.numFormat4UnitDetail(qeh.getIncome());
		} else if (qeh.getPay().compareTo(BigDecimal.valueOf(0)) == 1) {
			this.price = Utlity.numFormat4UnitDetail(qeh.getPay());
		} else {
			this.price = "0.00";
		}
		
		
		this.status = qeh.getStatus();
		if(QcbEmployeeHistoryStatus.FAIL.equals(qeh.getStatus())){
			this.statusCN = "交易失败";
		} else if (QcbEmployeeHistoryStatus.CLOSED.equals(qeh.getStatus())) {
			this.statusCN = "交易关闭";
		} else if (QcbEmployeeHistoryStatus.CANCELLED.equals(qeh.getStatus())) {
			this.statusCN = "交易取消";
		} else if (QcbEmployeeHistoryStatus.TRANSACTING.equals(qeh.getStatus())) {
			this.statusCN = "交易中";
		} else  if (QcbEmployeeHistoryStatus.SUCCESS.equals(qeh.getStatus())) {
			this.statusCN = "交易成功";
		} else {
			this.statusCN = "交易错误";
		}
		
		this.createtime = qeh.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(qeh.getCreatetime());
		this.createtimeLessCN = Utlity.timeSpanToChinaStringLess(qeh.getCreatetime());
		this.time = Utlity.timeSpanToChinaStringesLess(qeh.getCreatetime());
		
		this.pay = qeh.getPay();
		this.payCN = Utlity.numFormat4UnitDetailLess(qeh.getPay());
		
		this.bankcard = qeh.getBankcard() == null ? "" : qeh.getBankcard();
		this.processCompanyAccount = qeh.getProcessCompanyAccount() == null ? "" : qeh.getProcessCompanyAccount();
		this.processCreatetime = qeh.getProcessCreatetime();
		if(qeh.getProcessCreatetime() != null){
			this.processCreatetimeCN = Utlity.timeSpanToString(qeh.getProcessCreatetime());
		}
		this.processCreator = qeh.getProcessCreator() == null ? "" : qeh.getProcessCreator();
		this.processingStatus = qeh.getProcessingStatus();
		if(qeh.getProcessingStatus() != null){
			if(QcbEmployeeHistoryProcessStatus.SUCCESS.equals(qeh.getProcessingStatus())){
				this.processingStatusCN = "已处理";
			} else if(QcbEmployeeHistoryProcessStatus.FAIL.equals(qeh.getProcessingStatus())){
				this.processingStatusCN = "处理失败";
			} else if(QcbEmployeeHistoryProcessStatus.PROCESSING.equals(qeh.getProcessingStatus())){
				this.processingStatusCN = "处理中";
			} else if(QcbEmployeeHistoryProcessStatus.UNPROCESS.equals(qeh.getProcessingStatus())){
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
	
	public String getQcbEmployee() {
		return qcbEmployee;
	}
	
	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}
	
	public String getQcbEmployeeName() {
		return qcbEmployeeName;
	}
	
	public void setQcbEmployeeName(String qcbEmployeeName) {
		this.qcbEmployeeName = qcbEmployeeName;
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
