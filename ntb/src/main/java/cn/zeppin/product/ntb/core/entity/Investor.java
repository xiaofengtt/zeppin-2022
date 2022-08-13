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
 * @author Clark.R 2016年9月19日
 *
 * 尝试不用Hibernate的外键对象原因（所有外键字段全部以基本型数据而不是对象体现）
 * 1、外键对象全部读取效率低，高效率的lazy load配置复杂
 * 2、利于构建基于Uuid的缓存层的实现（全部用dao调用实现关联对象读取）
 * 3、对象Json序列化涉及到对象循环引用引起的死循环的问题
 * 4、Control层返回对象基本不用多级对象直接生成的Json格式，Json返回值格式以扁平化结构为佳
 * 5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
 * 
 * 
 * @description 【数据对象】前台投资人
 */

@Entity
@Table(name = "investor", uniqueConstraints = {@UniqueConstraint(columnNames = "idcard"),  @UniqueConstraint(columnNames = "mobile"), @UniqueConstraint(columnNames = "email")})
public class Investor extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2579882874513481593L;
	
	private String uuid;
	private String nickname;
	private String realname;
	private String idcard;
	private String mobile;
	private String email;
	
	private String loginPassword;
	private String secretPassword;
	
	private String thirdPaymentAccount;
	private String thirdPaymentPassword;
	
	private String status;
	
	private Boolean bindingMobileFlag;
	private Boolean bindingEmailFlag;
	private Boolean realnameAuthFlag;
	private Boolean secretPasswordFlag;
	
	private BigDecimal totalAmount;
	private BigDecimal totalReturn;
	private BigDecimal accountBalance;
	
	private Timestamp createtime;
	
	private String referrer;
	private String registSource;
	private Timestamp lastLoginTime;
	private String lastLoginIp;
	
	
	public class InvestorStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String LOCKED = "locked";
		public final static String DELETED = "deleted";
		public final static String UNOPEN = "unopen";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "nickname", unique = true, nullable = false, length = 50)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Column(name = "realname", length = 50)
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Column(name = "mobile", unique = true, nullable = false,  length = 20)
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
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "idcard", length = 20)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "login_password", nullable = false, length = 300)
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

	@Column(name = "third_payment_account", length = 300)
	public String getThirdPaymentAccount() {
		return thirdPaymentAccount;
	}

	public void setThirdPaymentAccount(String thirdPaymentAccount) {
		this.thirdPaymentAccount = thirdPaymentAccount;
	}

	@Column(name = "third_payment_password", length = 300)
	public String getThirdPaymentPassword() {
		return thirdPaymentPassword;
	}

	public void setThirdPaymentPassword(String thirdPaymentPassword) {
		this.thirdPaymentPassword = thirdPaymentPassword;
	}

	@Column(name = "binding_mobile_flag", nullable = false)
	public Boolean getBindingMobileFlag() {
		return bindingMobileFlag;
	}

	public void setBindingMobileFlag(Boolean bindingMobileFlag) {
		this.bindingMobileFlag = bindingMobileFlag;
	}

	@Column(name = "binding_email_flag", nullable = false)
	public Boolean getBindingEmailFlag() {
		return bindingEmailFlag;
	}

	public void setBindingEmailFlag(Boolean bindingEmailFlag) {
		this.bindingEmailFlag = bindingEmailFlag;
	}

	@Column(name = "realname_auth_flag", nullable = false)
	public Boolean getRealnameAuthFlag() {
		return realnameAuthFlag;
	}

	public void setRealnameAuthFlag(Boolean realnameAuthFlag) {
		this.realnameAuthFlag = realnameAuthFlag;
	}

	@Column(name = "secret_password_flag", nullable = false)
	public Boolean getSecretPasswordFlag() {
		return secretPasswordFlag;
	}

	public void setSecretPasswordFlag(Boolean secretPasswordFlag) {
		this.secretPasswordFlag = secretPasswordFlag;
	}

	@Column(name = "total_amount", nullable = false, length = 20)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	@Column(name = "referrer", length = 36)
	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	@Column(name = "regist_source", nullable = false, length = 36)
	public String getRegistSource() {
		return registSource;
	}

	public void setRegistSource(String registSource) {
		this.registSource = registSource;
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
}
