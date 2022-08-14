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
 * @description 【数据对象】企财宝企业管理员菜单权限
 */

@Entity
@Table(name = "qcb_company_admin_menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"qcb_company_admin","qcb_menu"})})
public class QcbCompanyAdminMenu extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1563655041682609796L;
	private String uuid;
	private String qcbCompanyAdmin;
	private String qcbCompany;
	private String qcbAdmin;
	private String qcbMenu;
	private String creator;
	private Timestamp createtime;

	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "qcb_company_admin", nullable = false, length = 36)
	public String getQcbCompanyAdmin() {
		return qcbCompanyAdmin;
	}
	
	public void setQcbCompanyAdmin(String qcbCompanyAdmin) {
		this.qcbCompanyAdmin = qcbCompanyAdmin;
	}
	
	@Column(name = "qcb_company", nullable = false, length = 36)
	public String getQcbCompany() {
		return qcbCompany;
	}
	
	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}
	
	@Column(name = "qcb_admin", nullable = false, length = 36)
	public String getQcbAdmin() {
		return qcbAdmin;
	}
	
	public void setQcbAdmin(String qcbAdmin) {
		this.qcbAdmin = qcbAdmin;
	}

	@Column(name = "qcb_menu", nullable = false, length = 36)
	public String getQcbMenu() {
		return qcbMenu;
	}
	
	public void setQcbMenu(String qcbMenu) {
		this.qcbMenu = qcbMenu;
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
