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
 * @description 【数据对象】银行理财产品操作信息
 */

@Entity
@Table(name = "bank_financial_product_publish_operate")
public class BankFinancialProductPublishOperate extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4476830891011718313L;
	
	private String uuid;
	private String bankFinancialProductPublish;
	private String type;
	private String value;
	private String old;
	private String reason;
	private String status;
	private String checker;
	private Timestamp checktime;
	private String creator;
	private Timestamp createtime;
	
	private Timestamp submittime;
	
	public class BankFinancialProductPublishOperateType{
		public final static String ADD = "add";
		public final static String EDIT = "edit";
		public final static String DELETE = "delete";
		public final static String EXCEPTION = "exception";
		
		public final static String COLLECT = "collect";//开启认购（募集）
		public final static String UNINVEST = "uninvest";//结束认购（投资中）
	}
	
	public class BankFinancialProductPublishOperateStatus{
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

	@Column(name = "bank_financial_product_publish", length = 36)
	public String getBankFinancialProductPublish() {
		return bankFinancialProductPublish;
	}

	public void setBankFinancialProductPublish(String bankFinancialProductPublish) {
		this.bankFinancialProductPublish = bankFinancialProductPublish;
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
	
	@Column(name = "old")
	public String getOld() {
		return old;
	}

	public void setOld(String old) {
		this.old = old;
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
}
