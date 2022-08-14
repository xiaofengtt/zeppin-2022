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
 * @description 【数据对象】微信鉴权
 */

@Entity
@Table(name = "qcb_admin_wechat_auth")
public class QcbAdminWechatAuth extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1183098900230122468L;
	private String uuid;
	private String qcbAdmin;
	private String qcbCompanyPayroll;
	private String type;
	private String token;
	private String openid;
	private String status;
	private Timestamp createtime;
	
	public class QcbAdminWechatAuthType{
		public final static String BIND = "bind";
		public final static String REMOVE = "remove";
		public final static String PAYROLL = "payroll";
		public final static String LOGIN = "login";
	}
	
	public class QcbAdminWechatAuthStatus{
		public final static String NORMAL = "normal";
		public final static String FAILED = "failed";
		public final static String SUCCESS = "success";
		public final static String DISABLE = "disable";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "qcb_admin", nullable = false, length = 36)
	public String getQcbAdmin() {
		return qcbAdmin;
	}
	
	public void setQcbAdmin(String qcbAdmin) {
		this.qcbAdmin = qcbAdmin;
	}
	
	@Column(name = "qcb_company_payroll", length = 36)
	public String getQcbCompanyPayroll() {
		return qcbCompanyPayroll;
	}

	public void setQcbCompanyPayroll(String qcbCompanyPayroll) {
		this.qcbCompanyPayroll = qcbCompanyPayroll;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "token", nullable = false, length = 10)
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	@Column(name = "openid", length = 50)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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
}
