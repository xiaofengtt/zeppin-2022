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
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】社保熊第三方支付平台订单信息记录
 */

@Entity
@Table(name = "shbx_orderinfo_by_thirdparty", uniqueConstraints = {@UniqueConstraint(columnNames = "order_num")})
public class ShbxOrderinfoByThirdparty extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3706577247529008508L;
	private String uuid;
	private String type;
	private String orderNum;
	private String payNum;
	private String body;
	private BigDecimal totalFee;
	private String paySource;
	private Timestamp paytime;
	private String status;
	private String errCode;
	private String errCodeDes;
	private String message;
	private Timestamp createtime;
	
	private String prepayId;
	
	private String account;
	private String accountType;
	
	public class ShbxOrderinfoByThirdpartyAccountType{
		public final static String COMPANY = "company";
		public final static String EMPLOYEE = "employee";
	}
	
	public class ShbxOrderinfoByThirdpartyReturnStatus{
		public final static String FAIL = "FAIL";
		public final static String SUCCESS = "SUCCESS";
		public final static String TRADE_SUCCESS = "TRADE_SUCCESS";
		public final static String TRADE_CLOSED = "TRADE_CLOSED";
		public final static String TRADE_FINISHED = "TRADE_FINISHED";
		public final static String TRADE_FAILURE = "TRADE_FAILURE";
		public final static String WITHDRAW_SUCCESS= "0";
		public final static String WITHDRAW_PROCESS= "1";
		public final static String WITHDRAW_FAIL= "2";
		public final static String WITHDRAWAL_SUBMITTED = "WITHDRAWAL_SUBMITTED";
		public final static String WITHDRAWAL_SUCCESS = "WITHDRAWAL_SUCCESS";
		public final static String WITHDRAWAL_FAIL = "WITHDRAWAL_FAIL";
		public final static String RETURN_TICKET = "RETURN_TICKET";
		
		public final static String REAPAL_SUCCESS = "0000";
		public final static String REAPAL_UNCHECK = "4035";
		public final static String REAPAL_CHECKED = "4036";
	}
	
	public class ShbxOrderinfoByThirdpartyType{
		public final static String WEXIN = "weixin";
		public final static String ZHIFUBAO = "zhifubao";
		public final static String ZHIFUBAO_TRANSFER = "zhifubao_transfer";
		public final static String CHANPAY = "chanpay";
		public final static String REAPAL = "reapal";
	}
	public class ShbxOrderinfoByThirdpartyResultStatus{
		public final static String SUCCESS = "SUCCESS";
		public final static String NOAUTH = "NOAUTH";
		public final static String NOTENOUGH = "NOTENOUGH";
		public final static String ORDERPAID = "ORDERPAID";
		public final static String ORDERCLOSED = "ORDERCLOSED";
		public final static String SYSTEMERROR = "SYSTEMERROR";
		public final static String APPID_NOT_EXIST = "APPID_NOT_EXIST";
		public final static String MCHID_NOT_EXIST = "MCHID_NOT_EXIST";
		public final static String APPID_MCHID_NOT_MATCH = "APPID_MCHID_NOT_MATCH";
		public final static String LACK_PARAMS = "LACK_PARAMS";
		public final static String OUT_TRADE_NO_USED = "OUT_TRADE_NO_USED";
		public final static String SIGNERROR = "SIGNERROR";
		public final static String XML_FORMAT_ERROR = "XML_FORMAT_ERROR";
		public final static String REQUIRE_POST_METHOD = "REQUIRE_POST_METHOD";
		public final static String POST_DATA_EMPTY = "POST_DATA_EMPTY";
		public final static String NOT_UTF8 = "NOT_UTF8";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "type", nullable = false, length = 10)
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

	@Column(name = "pay_num", length = 32)
	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	@Column(name = "body", length = 100)
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Column(name = "total_fee", nullable = false, length = 20)
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	@Column(name = "pay_source", nullable = false, length = 36)
	public String getPaySource() {
		return paySource;
	}

	public void setPaySource(String paySource) {
		this.paySource = paySource;
	}

	@Column(name = "pay_time")
	public Timestamp getPaytime() {
		return paytime;
	}

	public void setPaytime(Timestamp paytime) {
		this.paytime = paytime;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "message", nullable = false, length = 128)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "prepay_id", length = 64)
	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	@Column(name = "err_code", length = 64)
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	@Column(name = "err_code_des", length = 64)
	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	@Column(name = "account", nullable = false, length = 36)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	@Column(name = "account_type", nullable = false, length = 10)
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}
