/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】企财宝企业员工
 */

@Entity
@Table(name = "qcb_company_employee", uniqueConstraints = {@UniqueConstraint(columnNames = {"qcb_company","qcb_employee"})})
public class QcbCompanyEmployee extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3164798578703904129L;
	private String uuid;
	private String qcbCompany;
	private String qcbEmployee;
	private String department;
	private String duty;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class QcbCompanyEmployeeStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
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
	
	@Column(name = "qcb_company", nullable = false, length = 80)
	public String getQcbCompany() {
		return qcbCompany;
	}
	
	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}
	
	@Column(name = "qcb_employee", nullable = false, length = 80)
	public String getQcbEmployee() {
		return qcbEmployee;
	}
	
	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}
	
	@Column(name = "department", length = 100)
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Column(name = "duty", length = 100)
	public String getDuty() {
		return duty;
	}
	
	public void setDuty(String duty) {
		this.duty = duty;
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
