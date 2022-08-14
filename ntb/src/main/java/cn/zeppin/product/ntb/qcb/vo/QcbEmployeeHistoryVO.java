/**
 * 
 */
package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryStatus;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryType;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory;
import cn.zeppin.product.ntb.core.entity.QcbEmployeeHistory.QcbEmployeeHistoryOrderType;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @description 【数据对象】前台投资人账户明细
 */
public class QcbEmployeeHistoryVO implements Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5298209401230439482L;
	private String uuid;
	private String qcbEmployee;
	private String qcbEmployeeName;
	private String order;
	private String orderNum;
	private String orderType;
	private String orderTypeCN;
	
	private String type;
	private String typeCN;
	
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	
	private Boolean priceflag;
	private String price;
	
	private String bankName;
	private String bankcard;
	
	private String status;
	private String statusCN;
	private Timestamp createtime;
	private String createtimeCN;
	private String createtimeLessCN;
	
	private Timestamp processCreatetime;
	private String processCreatetimeCN;
	
	private String time;
	
	private String remark;
	
	private String product;
	private String productType;
	
	
	public QcbEmployeeHistoryVO() {
		super();
	}
	public QcbEmployeeHistoryVO(QcbEmployeeHistory iah) {
		this.uuid = iah.getUuid();
		this.qcbEmployee = iah.getQcbEmployee();
		this.order = iah.getOrderId();
		this.orderType = iah.getOrderType();
		if(QcbEmployeeHistoryOrderType.PAY_TYPE_ALIPAY.equals(iah.getOrderType())){
			this.orderTypeCN = "支付宝支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_BALANCE.equals(iah.getOrderType())) {
			this.orderTypeCN = "平台余额支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_BANKCARD.equals(iah.getOrderType())) {
			this.orderTypeCN = "银行卡支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_CHANPAY.equals(iah.getOrderType())) {
//			this.orderTypeCN = "畅捷快捷支付";
			this.orderTypeCN = "银行卡支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_WECHART.equals(iah.getOrderType())) {
			this.orderTypeCN = "微信支付";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_COMPANY.equals(iah.getOrderType())) {
			this.orderTypeCN = "平台结算";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_ALIPAY_TRANSFER.equals(iah.getOrderType())) {
			this.orderTypeCN = "支付宝转账";
		} else if (QcbEmployeeHistoryOrderType.PAY_TYPE_REAPAL.equals(iah.getOrderType())) {
			this.orderTypeCN = "银行卡支付";
		} else {
			this.orderTypeCN = "无";
		}
		this.orderNum = iah.getOrderNum();
		this.priceflag = true;
		this.type = iah.getType();
		if(QcbEmployeeHistoryType.WITHDRAW.equals(iah.getType())){
			this.typeCN = "提现";
			this.remark = "账户提现";
			this.priceflag = false;
		} else if (QcbEmployeeHistoryType.INCOME.equals(iah.getType())) {
			this.typeCN = "充值";
			this.remark = "账户充值";
			this.priceflag = true;
		} else if (QcbEmployeeHistoryType.BUY.equals(iah.getType())) {
			this.typeCN = "购买";
			this.remark = "购买";
			this.priceflag = false;
		} else if (QcbEmployeeHistoryType.DIVIDEND.equals(iah.getType())) {
			this.typeCN = "收益";
			this.remark = "理财收益";
			this.priceflag = true;
		} else if (QcbEmployeeHistoryType.RETURN.equals(iah.getType())) {
			this.typeCN = "返还本金";
			this.remark = "返还本金";
			this.priceflag = true;
		} else if (QcbEmployeeHistoryType.PAYROLL.equals(iah.getType())) {
			this.typeCN = "福利发放";
			this.remark = "福利发放";
			this.priceflag = true;
		} else if (QcbEmployeeHistoryType.CURRENT_BUY.equals(iah.getType())) {
			this.typeCN = "活期盈转入";
			this.remark = "活期盈转入";
			this.priceflag = false;
		} else if (QcbEmployeeHistoryType.CURRENT_RETURN.equals(iah.getType())) {
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
		if(QcbEmployeeHistoryStatus.FAIL.equals(iah.getStatus())){
			this.statusCN = "交易失败";
		} else if (QcbEmployeeHistoryStatus.CLOSED.equals(iah.getStatus())) {
			this.statusCN = "交易关闭";
		} else if (QcbEmployeeHistoryStatus.CANCELLED.equals(iah.getStatus())) {
			this.statusCN = "交易取消";
		} else if (QcbEmployeeHistoryStatus.TRANSACTING.equals(iah.getStatus())) {
			this.statusCN = "交易中";
		} else  if (QcbEmployeeHistoryStatus.SUCCESS.equals(iah.getStatus())) {
			this.statusCN = "交易成功";
		} else {
			this.statusCN = "交易错误";
		}
		
		this.createtime = iah.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToChinaString(iah.getCreatetime());
		this.createtimeLessCN = Utlity.timeSpanToChinaStringLess(iah.getCreatetime());
		this.processCreatetime = iah.getProcessCreatetime();
		if(iah.getProcessCreatetime() != null){
			this.processCreatetimeCN = Utlity.timeSpanToChinaString(iah.getProcessCreatetime());
		}
		
		this.time = Utlity.timeSpanToChinaStringesLess(iah.getCreatetime());
		this.product = iah.getProduct();
		this.productType = iah.getProductType();
	}
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getOrderTypeCN() {
		return orderTypeCN;
	}
	public void setOrderTypeCN(String orderTypeCN) {
		this.orderTypeCN = orderTypeCN;
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
	public Timestamp getProcessCreatetime() {
		return processCreatetime;
	}
	public void setProcessCreatetime(Timestamp processCreatetime) {
		this.processCreatetime = processCreatetime;
	}
	public String getProcessCreatetimeCN() {
		return processCreatetimeCN;
	}
	public void setProcessCreatetimeCN(String processCreatetimeCN) {
		this.processCreatetimeCN = processCreatetimeCN;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankcard() {
		return bankcard;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
}
