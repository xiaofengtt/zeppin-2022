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
 * @description 【数据对象】企财宝企业薪资发放反馈
 */

@Entity
@Table(name = "qcb_company_payroll_feedback")
public class QcbCompanyPayrollFeedback extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1862692344318198690L;
	private String uuid;
	private String qcbCompanyPayroll;
	private String qcbCompanyPayrollRecords;
	private String content;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class QcbCompanyPayrollFeedbackStatus{
		public final static String NORMAL = "normal";
		public final static String UNREAD = "unread";
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
	
	@Column(name = "qcb_company_payroll", nullable = false, length = 36)
	public String getQcbCompanyPayroll() {
		return qcbCompanyPayroll;
	}

	public void setQcbCompanyPayroll(String qcbCompanyPayroll) {
		this.qcbCompanyPayroll = qcbCompanyPayroll;
	}

	@Column(name = "qcb_company_payroll_records", nullable = false, length = 36)
	public String getQcbCompanyPayrollRecords() {
		return qcbCompanyPayrollRecords;
	}
	
	public void setQcbCompanyPayrollRecords(String qcbCompanyPayrollRecords) {
		this.qcbCompanyPayrollRecords = qcbCompanyPayrollRecords;
	}
	
	@Column(name = "content", nullable = false, length = 500)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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
}
