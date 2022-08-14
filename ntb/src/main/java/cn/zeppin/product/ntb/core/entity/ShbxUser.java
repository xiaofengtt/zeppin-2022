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
import javax.persistence.UniqueConstraint;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @description 【数据对象】社保熊用户
 */

@Entity
@Table(name = "shbx_user", uniqueConstraints = {@UniqueConstraint(columnNames = "mobile"), @UniqueConstraint(columnNames = "openid"), @UniqueConstraint(columnNames = "idcard")})
public class ShbxUser extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -11696583793023051L;
	private String uuid;
	private String realname;
	private String idcard;
	private String mobile;
	private String email;
	private String loginPassword;
	private String secretPassword;
	private Boolean secretPasswordFlag;
	
	private Boolean realnameAuthFlag;
	
	private BigDecimal accountBalance;
	private BigDecimal totalInvest;
	private BigDecimal totalReturn;
	private BigDecimal currentAccount;
	private BigDecimal currentAccountYesterday;
	private Boolean flagCurrent;
	
	private String openid;
	private String wechatIcon;
	private String sex;
	private String status;
	private Timestamp lastLoginTime;
	private String lastLoginIp;
	private Timestamp createtime;
	
	public class ShbxUserStatus{
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

	@Column(name = "realname", length = 50)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	@Column(name = "mobile", unique = true, nullable = false, length = 20)
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "idcard", unique = true, length = 20)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@Column(name = "login_password", length = 300)
	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Column(name = "secret_password", length = 300)
	public String getSecretPassword() {
		return secretPassword;
	}

	public void setSecretPassword(String secretPassword) {
		this.secretPassword = secretPassword;
	}

	@Column(name = "secret_password_flag", nullable = false)
	public Boolean getSecretPasswordFlag() {
		return secretPasswordFlag;
	}

	public void setSecretPasswordFlag(Boolean secretPasswordFlag) {
		this.secretPasswordFlag = secretPasswordFlag;
	}
	
	@Column(name = "realname_auth_flag", nullable = false)
	public Boolean getRealnameAuthFlag() {
		return realnameAuthFlag;
	}

	public void setRealnameAuthFlag(Boolean realnameAuthFlag) {
		this.realnameAuthFlag = realnameAuthFlag;
	}
	
	@Column(name = "total_invest", nullable = false, length = 20)
	public BigDecimal getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(BigDecimal totalInvest) {
		this.totalInvest = totalInvest;
	}

	@Column(name = "total_return", nullable = false, length = 20)
	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}
	
	@Column(name = "account_balance", nullable = false, length = 20)
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	@Column(name = "current_account", nullable = false, length = 20)
	public BigDecimal getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(BigDecimal currentAccount) {
		this.currentAccount = currentAccount;
	}
	
	@Column(name = "current_account_yesterday", nullable = false)
	public BigDecimal getCurrentAccountYesterday() {
		return currentAccountYesterday;
	}

	public void setCurrentAccountYesterday(BigDecimal currentAccountYesterday) {
		this.currentAccountYesterday = currentAccountYesterday;
	}
	
	@Column(name = "flag_current", nullable = false)
	public Boolean getFlagCurrent() {
		return flagCurrent;
	}

	public void setFlagCurrent(Boolean flagCurrent) {
		this.flagCurrent = flagCurrent;
	}
	@Column(name = "openid", length = 30)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "wechat_icon", length = 100)
	public String getWechatIcon() {
		return wechatIcon;
	}

	public void setWechatIcon(String wechatIcon) {
		this.wechatIcon = wechatIcon;
	}

	@Column(name = "sex", length = 10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
