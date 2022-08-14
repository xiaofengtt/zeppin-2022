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
 * @description 【数据对象】企财宝企业财税服务申请
 */

@Entity
@Table(name = "qcb_company_finance_apply")
public class QcbCompanyFinanceApply extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2743290796260223598L;
	private String uuid;
	private String qcbCompany;
	private String contacts;
	private String contactsMobile;
	private String status;
	private String checkreason;
	private String checker;
	private Timestamp checktime;
	private String creator;
	private Timestamp createtime;
	
	public class QcbCompanyFinanceApplyStatus{
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
	
	@Column(name = "qcb_company", nullable = false, length = 36)
	public String getQcbCompany() {
		return qcbCompany;
	}
	
	public void setQcbCompany(String qcbCompany) {
		this.qcbCompany = qcbCompany;
	}
	
	@Column(name = "contacts_mobile", nullable = false, length = 100)
	public String getContactsMobile() {
		return contactsMobile;
	}
	
	public void setContactsMobile(String contactsMobile) {
		this.contactsMobile = contactsMobile;
	}
	
	@Column(name = "contacts", nullable = false, length = 100)
	public String getContacts() {
		return contacts;
	}
	
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "checkreason", length = 200)
	public String getCheckreason() {
		return checkreason;
	}
	
	public void setCheckreason(String checkreason) {
		this.checkreason = checkreason;
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
}
