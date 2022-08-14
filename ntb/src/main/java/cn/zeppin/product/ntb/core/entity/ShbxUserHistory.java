/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】社保熊用户历史
 */

@Entity
@Table(name = "shbx_user_history")
public class ShbxUserHistory extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3209218045068871086L;
	private String uuid;
	private String shbxUser;
	
	private String orderId;
	private String orderType;
	private String orderNum;
	
	private BigDecimal income;
	private BigDecimal pay;
	private BigDecimal accountBalance;
	
	private String status;
	private Timestamp createtime;
	
	private String type;
	private String companyAccount;
	private String product;
	private String productType;
	private BigDecimal poundage;
	private String bankcard;
	
	private String processingStatus;
	private String processCompanyAccount;
	private String processCreator;
	private Timestamp processCreatetime;
	
	private String shbxSecurityOrder;
	
	public class ShbxUserHistoryStatus{
		public final static String FAIL = "FAIL";
		public final static String SUCCESS = "SUCCESS";
		public final static String TRANSACTING = "TRANSACTING";
		public final static String CLOSED = "CLOSED";
		public final static String CANCELLED = "CANCELLED";
	}
	public class ShbxUserHistoryProcessStatus{
		public final static String FAIL = "fail";
		public final static String SUCCESS = "success";
		public final static String PROCESSING = "processing";
		public final static String UNPROCESS = "unprocess";
		public final static String PROCESSED = "processed";//记录手动重试 成功但未接收到异步通知的数据
	}
	public class ShbxUserHistoryType{
		public final static String WITHDRAW = "withdraw";
		public final static String INCOME = "income";
		public final static String BUY = "buy";
		public final static String DIVIDEND = "dividend";
		public final static String RETURN = "return";
		public final static String PAYROLL = "payroll";
		public final static String REPAYMENT = "repayment";//代还信用卡
		public final static String CURRENT_BUY = "cur_buy";//活期购买
		public final static String CURRENT_RETURN = "cur_return";//活期卖出
		public final static String BUY_SHBX = "buy_shbx";//社保熊买入
	}
	public class ShbxUserHistoryOrderType{
		public static final String PAY_TYPE_BALANCE = "balance";
		public static final String PAY_TYPE_WECHART = "wechart";
		public static final String PAY_TYPE_ALIPAY = "alipay";
		public static final String PAY_TYPE_ALIPAY_TRANSFER = "alipay_transfer";
		public static final String PAY_TYPE_BANKCARD = "bankcard";
		public static final String PAY_TYPE_CHANPAY = "chanpay";
		public static final String PAY_TYPE_COMPANY = "company";
		public static final String PAY_TYPE_REAPAL = "reapal";
		public static final String PAY_TYPE_QCB_COMPANY = "pay_type_qcb_company";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "income", length = 20)
	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	@Column(name = "pay", length = 20)
	public BigDecimal getPay() {
		return pay;
	}

	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}

	@Column(name = "account_balance", length = 20)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "order_id", length = 36)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "order_type", length = 36)
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "type", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "order_num", nullable = false, length = 32)
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "company_account", length = 36)
	public String getCompanyAccount() {
		return companyAccount;
	}

	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}

	@Column(name = "product", length = 36)
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	
	@Column(name = "product_type", length = 20)
	public String getProductType() {
		return productType;
	}
	

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Column(name = "poundage", nullable = false, length = 20)
	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	@Column(name = "bankcard", length = 36)
	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	@Column(name = "processing_status", length = 20)
	public String getProcessingStatus() {
		return processingStatus;
	}

	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}

	@Column(name = "process_company_account", length = 36)
	public String getProcessCompanyAccount() {
		return processCompanyAccount;
	}

	public void setProcessCompanyAccount(String processCompanyAccount) {
		this.processCompanyAccount = processCompanyAccount;
	}

	@Column(name = "process_creator", length = 36)
	public String getProcessCreator() {
		return processCreator;
	}

	public void setProcessCreator(String processCreator) {
		this.processCreator = processCreator;
	}

	@Column(name = "process_createtime")
	public Timestamp getProcessCreatetime() {
		return processCreatetime;
	}

	public void setProcessCreatetime(Timestamp processCreatetime) {
		this.processCreatetime = processCreatetime;
	}


	@Column(name = "shbx_user", nullable = false, length = 36)
	public String getShbxUser() {
		return shbxUser;
	}
	

	public void setShbxUser(String shbxUser) {
		this.shbxUser = shbxUser;
	}
	
	@Column(name = "shbx_security_order", length = 36)
	public String getShbxSecurityOrder() {
		return shbxSecurityOrder;
	}
	

	public void setShbxSecurityOrder(String shbxSecurityOrder) {
		this.shbxSecurityOrder = shbxSecurityOrder;
	}
}
