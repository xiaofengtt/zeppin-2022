/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * 
 * @description 【数据对象】企业账户交易操作信息
 */

@Entity
@Table(name = "company_account_transfer_operate")
public class CompanyAccountTransferOperate extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 81546832200989606L;
	
	private String uuid;
	private String companyAccountHistory;
	private String type;
	private String value;
	private String reason;
	private String status;
	private String checker;
	private Timestamp checktime;
	private String creator;
	private Timestamp createtime;
	private Timestamp submittime;
	
	private String receipt;//上传凭证
	
	public class CompanyAccountTransferOperateType{
		public final static String TRANSFER = "transfer";//转账
		public final static String RECHARGE = "recharge";//企业充值
		public final static String EXPEND = "expend";//费用支出
	}
	
	public class CompanyAccountTransferOperateStatus{
		public final static String DRAFT = "draft";//草稿
		public final static String UNCHECKED = "unchecked";
		public final static String CHECKED = "checked";
		public final static String UNPASSED = "unpassed";
		public final static String DELETED = "deleted";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "company_account_history", length = 36)
	public String getCompanyAccountHistory() {
		return companyAccountHistory;
	}

	public void setCompanyAccountHistory(String companyAccountHistory) {
		this.companyAccountHistory = companyAccountHistory;
	}
	
	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "value", nullable = false)
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name = "reason", length = 500)
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "checktime")
	public Timestamp getChecktime() {
		return checktime;
	}
	
	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}
	
	@Column(name = "checker", length = 36)
	public String getChecker() {
		return checker;
	}
	
	public void setChecker(String checker) {
		this.checker = checker;
	}	
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}	
	
	@Column(name = "submittime")
	public Timestamp getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Timestamp submittime) {
		this.submittime = submittime;
	}

	@Column(name = "receipt")
	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
}
