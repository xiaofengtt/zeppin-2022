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
 * @description 【数据对象】企财宝企业薪金发放
 */

@Entity
@Table(name = "qcb_company_payroll")
public class QcbCompanyPayroll extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2554308328342185111L;
	private String uuid;
	private String qcbCompany;
	private String title;
	private Timestamp payTime;
	private String type;
	private String remark;
	private String status;
	private String columnData;
	private Integer reward;
	private Boolean flagSms;
	private Boolean flagHide;
	private String sourcefile;
	private String creator;
	private Timestamp createtime;
	
	public class QcbCompanyPayrollStatus{
		public final static String DRAFT = "draft";
		public final static String SUBMIT = "submit";
		public final static String SUCCESS = "success";
		public final static String FAILED = "failed";
		public final static String DELETED = "deleted";
	}

	public class QcbCompanyPayrollType{
		public final static String WAGE = "wage";//工资
		public final static String BONUS = "bonus";//奖金
		public final static String SUBSIDY = "subsidy";//补贴
		public final static String REIMBURSEMENT = "reimbursement";//报销
		public final static String COMMISSION = "commission";//提成
		public final static String REISSUE = "reissue";//补发
		public final static String OTHER = "other";//其他
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "qcb_company", nullable = false, length = 36)
	public String getQcbCompany() {
		return qcbCompany;
	}
	
	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}
	
	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "pay_time", nullable = false)
	public Timestamp getPayTime() {
		return payTime;
	}
	
	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "remark", length = 100)
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "column_data")
	public String getColumnData() {
		return columnData;
	}
	
	public void setColumnData(String columnData) {
		this.columnData = columnData;
	}
	
	@Column(name = "reward")
	public Integer getReward() {
		return reward;
	}
	
	public void setReward(Integer reward) {
		this.reward = reward;
	}

	@Column(name = "flag_sms")
	public Boolean getFlagSms() {
		return flagSms;
	}
	
	public void setFlagSms(Boolean flagSms) {
		this.flagSms = flagSms;
	}

	@Column(name = "flag_hide")
	public Boolean getFlagHide() {
		return flagHide;
	}
	
	public void setFlagHide(Boolean flagHide) {
		this.flagHide = flagHide;
	}
	
	@Column(name = "sourcefile", length = 36)
	public String getSourcefile() {
		return sourcefile;
	}
	
	public void setSourcefile(String sourcefile) {
		this.sourcefile = sourcefile;
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
