package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.DataTimeConvert;

public class InvestorVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8380233637752469096L;
	
	private String uuid;
	private String nickname;
	private String realname;
	private String idcard;
	private String mobile;
	private String email;
	private String status;
	private String statusCN;
	private BigDecimal totalAmount;
	private BigDecimal totalReturn;
	private BigDecimal accountBalance;
	private Timestamp createtime;
	private String createtimeCN;
	private String referrer;
	private String referrerName;
	private String registSource;
	private Timestamp lastLoginTime;
	private String lastLoginTimeCN;
	private String lastLoginIp;
	
	
	public InvestorVO(){
		
	}
	
	public InvestorVO(Investor investor){
		this.uuid = investor.getUuid();
		this.nickname=investor.getNickname();
		this.realname=investor.getRealname();
		this.idcard=investor.getIdcard();
		this.mobile=investor.getMobile();
		this.email=investor.getEmail();
		this.status = investor.getStatus();
		this.totalAmount = investor.getTotalAmount();
		this.totalReturn = investor.getTotalReturn();
		this.accountBalance = investor.getAccountBalance();
		this.createtime = investor.getCreatetime();
		this.createtimeCN = DataTimeConvert.timespanToString(investor.getCreatetime(),"yyyy-MM-dd HH:mm:ss");
		this.referrer = investor.getReferrer();
		this.registSource = investor.getRegistSource();
		this.lastLoginTime = investor.getLastLoginTime();
		if(investor.getLastLoginTime() != null){
			this.lastLoginTimeCN = DataTimeConvert.timespanToString(investor.getLastLoginTime(),"yyyy-MM-dd HH:mm:ss");
		}
		
		this.lastLoginIp = investor.getLastLoginIp();
		
		if(Investor.InvestorStatus.NORMAL.equals(investor.getStatus())){
			this.statusCN = "正常";
		} else if (Investor.InvestorStatus.DISABLE.equals(investor.getStatus())){
			this.statusCN = "注销";
		}  else if (Investor.InvestorStatus.LOCKED.equals(investor.getStatus())){
			this.statusCN = "锁定";
		}   else if (Investor.InvestorStatus.UNOPEN.equals(investor.getStatus())){
			this.statusCN = "未开通";
		}   else if (Investor.InvestorStatus.DELETED.equals(investor.getStatus())){
			this.statusCN = "删除";
		} else {
			this.statusCN = "无";
		}
		
		
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getRealname() {
		return realname;
	}
	
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
		this.createtimeCN = DataTimeConvert.timespanToString(createtime,"yyyy-MM-dd HH:mm:ss");
	}
	
	public String getCreatetimeCN() {
		return createtimeCN;
	}
	
	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalReturn() {
		return totalReturn;
	}

	public void setTotalReturn(BigDecimal totalReturn) {
		this.totalReturn = totalReturn;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getRegistSource() {
		return registSource;
	}

	public void setRegistSource(String registSource) {
		this.registSource = registSource;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
		this.lastLoginTimeCN = DataTimeConvert.timespanToString(lastLoginTime,"yyyy-MM-dd HH:mm:ss");
	}

	public String getLastLoginTimeCN() {
		return lastLoginTimeCN;
	}

	public void setLastLoginTimeCN(String lastLoginTimeCN) {
		this.lastLoginTimeCN = lastLoginTimeCN;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}
	
	
}
