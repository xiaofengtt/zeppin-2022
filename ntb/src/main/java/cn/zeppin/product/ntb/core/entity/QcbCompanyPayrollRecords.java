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
 * @description 【数据对象】企财宝企业薪金发放详情
 */

@Entity
@Table(name = "qcb_company_payroll_records")
public class QcbCompanyPayrollRecords extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2117947936719199283L;
	private String uuid;
	private String qcbCompanyPayroll;
	private String qcbCompany;
	private String qcbEmployee;
	private Timestamp payTime;
	private BigDecimal price;
	private String value;
	private String data;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class QcbCompanyPayrollRecordsStatus{
		public final static String DRAFT = "draft";
		public final static String SUCCESS = "success";
		public final static String CONFIRM = "confirm";
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
	
	@Column(name = "qcb_company", nullable = false, length = 36)
	public String getQcbCompany() {
		return qcbCompany;
	}
	
	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}

	@Column(name = "qcb_employee", nullable = false, length = 36)
	public String getQcbEmployee() {
		return qcbEmployee;
	}
	
	public void setQcbEmployee(String qcbEmployee) {
		this.qcbEmployee = qcbEmployee;
	}

	@Column(name = "pay_time", nullable = false)
	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Column(name = "value", nullable = false)
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name = "data")
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
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
