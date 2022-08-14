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
 * @description 【数据对象】企财宝管理员
 */

@Entity
@Table(name = "qcb_admin", uniqueConstraints = {@UniqueConstraint(columnNames = "mobile")})
public class QcbAdmin extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1609136917602189210L;
	private String uuid;
	private String mobile;
	private String name;
	private String password;
	private Integer passwordLevel;
	private String wechatNum;
	private Boolean wechatFlag;
	private String status;
	private Timestamp lastLoginTime;
	private String lastLoginIp;
	private Timestamp createtime;
	
	public class QcbAdminStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
//		public final static String LOCKED = "locked";
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "mobile", unique = true, nullable = false, length = 11)
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "password", nullable = false, length = 300)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "password_level")
	public Integer getPasswordLevel() {
		return passwordLevel;
	}

	public void setPasswordLevel(Integer passwordLevel) {
		this.passwordLevel = passwordLevel;
	}

	@Column(name = "wechat_num", length = 50)
	public String getWechatNum() {
		return wechatNum;
	}
	
	public void setWechatNum(String wechatNum) {
		this.wechatNum = wechatNum;
	}
	
	@Column(name = "wechat_flag", nullable = false)
	public Boolean getWechatFlag() {
		return wechatFlag;
	}
	
	public void setWechatFlag(Boolean wechatFlag) {
		this.wechatFlag = wechatFlag;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "last_login_time")
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "last_login_ip", length = 30)
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
}
