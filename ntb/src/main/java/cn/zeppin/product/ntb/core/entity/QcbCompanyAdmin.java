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
 * @description 【数据对象】企财宝企业管理员
 */

@Entity
@Table(name = "qcb_company_admin", uniqueConstraints = {@UniqueConstraint(columnNames = {"qcb_company","qcb_admin"})})
public class QcbCompanyAdmin extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8197619402760749771L;
	private String uuid;
	private String qcbCompany;
	private String qcbAdmin;
	private String role;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class QcbCompanyAdminStatus{
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
	
	@Column(name = "qcb_admin", nullable = false, length = 80)
	public String getQcbAdmin() {
		return qcbAdmin;
	}
	
	public void setQcbAdmin(String qcbAdmin) {
		this.qcbAdmin = qcbAdmin;
	}
	
	@Column(name = "role", nullable = false, length = 50)
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
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
