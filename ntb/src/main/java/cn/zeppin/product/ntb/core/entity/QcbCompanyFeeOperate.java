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
 * @description 【数据对象】企财宝企业费率操作信息
 */

@Entity
@Table(name = "qcb_company_fee_operate")
public class QcbCompanyFeeOperate extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1665991581642480253L;
	
	private String uuid;
	private String qcbCompanyAccount;
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
	
	public class QcbCompanyFeeOperateType{
		public final static String FEE_TICKET = "fee_ticket";//开票费率
	}
	
	public class QcbCompanyFeeOperateStatus{
		public final static String DRAFT = "draft";
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

	@Column(name = "qcb_company_account")
	public String getQcbCompanyAccount() {
		return qcbCompanyAccount;
	}

	public void setQcbCompanyAccount(String qcbCompanyAccount) {
		this.qcbCompanyAccount = qcbCompanyAccount;
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
