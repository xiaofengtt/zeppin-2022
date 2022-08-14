package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.DataTimeConvert;
import cn.zeppin.product.utility.Utlity;

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
	private BigDecimal totalInvest;
	private String totalInvestCN;
	private BigDecimal totalReturn;
	private String totalReturnCN;
	private BigDecimal accountBalance;
	private String accountBalanceCN;
	private BigDecimal currentAccount;
	private String currentAccountCN;
	private Boolean flagCurrent;
	
	private String totalAmount;
	
	private Timestamp createtime;
	private String createtimeCN;
	private String referrer;
	private String referrerName;
	private String registSource;
	private Timestamp lastLoginTime;
	private String lastLoginTimeCN;
	private String lastLoginIp;
	
	private String openid;
	
	private String idcardImg;
	private String checkstatus;
	private String sex;	
	
	private Boolean bindingMobileFlag;
	private Boolean bindingEmailFlag;
	private Boolean realnameAuthFlag;
	private Boolean secretPasswordFlag;
	private Boolean bindingBankcardFlag;
	
	public InvestorVO(){
		
	}
	
	public InvestorVO(Investor investor){
		this.uuid = investor.getUuid();
		this.nickname = investor.getNickname() == null ? "" : investor.getNickname();
		this.realname = investor.getRealname() == null ? "" : investor.getRealname();
		this.idcard=investor.getIdcard() == null ? "" : investor.getIdcard();
		this.mobile=investor.getMobile();
		this.email=investor.getEmail() == null ? "" : investor.getEmail();
		this.status = investor.getStatus();
		this.totalInvest = investor.getTotalInvest();
		this.totalReturn = investor.getTotalReturn();
		this.accountBalance = investor.getAccountBalance();
		this.currentAccount = investor.getCurrentAccount();
		this.flagCurrent = investor.getFlagCurrent();
		if(investor.getTotalInvest() != null && investor.getAccountBalance() != null){
			this.totalAmount = Utlity.numFormat4UnitDetail(investor.getTotalInvest().add(investor.getAccountBalance()));
		} else {
			this.totalAmount = "0.00";
		}
		this.totalInvestCN = Utlity.numFormat4UnitDetail(investor.getTotalInvest());
		this.totalReturnCN = Utlity.numFormat4UnitDetail(investor.getTotalReturn());
		this.accountBalanceCN = Utlity.numFormat4UnitDetail(investor.getAccountBalance());
		this.createtime = investor.getCreatetime();
		this.createtimeCN = DataTimeConvert.timespanToString(investor.getCreatetime(),"yyyy-MM-dd HH:mm:ss");
		this.referrer = investor.getReferrer();
		this.registSource = investor.getRegistSource();
		this.lastLoginTime = investor.getLastLoginTime();
		if(investor.getLastLoginTime() != null){
			this.lastLoginTimeCN = DataTimeConvert.timespanToString(investor.getLastLoginTime(),"yyyy-MM-dd HH:mm:ss");
		}
		
		this.lastLoginIp = investor.getLastLoginIp() == null ? "" : investor.getLastLoginIp();
		
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
		
		this.openid = investor.getOpenid() == null ? "" : investor.getOpenid();
		this.idcardImg = investor.getIdcardImg() == null ? "" : investor.getIdcardImg();
		this.sex = investor.getSex();
		
		this.bindingEmailFlag = investor.getBindingEmailFlag();
		this.bindingMobileFlag = investor.getBindingMobileFlag();
		this.realnameAuthFlag = investor.getRealnameAuthFlag();
		this.secretPasswordFlag = investor.getSecretPasswordFlag();
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

	public BigDecimal getTotalInvest() {
		return totalInvest;
	}

	public void setTotalInvest(BigDecimal totalInvest) {
		this.totalInvest = totalInvest;
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

	public BigDecimal getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(BigDecimal currentAccount) {
		this.currentAccount = currentAccount;
	}

	public String getCurrentAccountCN() {
		return currentAccountCN;
	}

	public void setCurrentAccountCN(String currentAccountCN) {
		this.currentAccountCN = currentAccountCN;
	}

	public Boolean getFlagCurrent() {
		return flagCurrent;
	}

	public void setFlagCurrent(Boolean flagCurrent) {
		this.flagCurrent = flagCurrent;
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

	public String getOpenid() {
		return openid;
	}
	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getIdcardImg() {
		return idcardImg;
	}
	
	public void setIdcardImg(String idcardImg) {
		this.idcardImg = idcardImg;
	}
	
	public String getCheckstatus() {
		return checkstatus;
	}
	
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}

	public Boolean getBindingMobileFlag() {
		return bindingMobileFlag;
	}

	public void setBindingMobileFlag(Boolean bindingMobileFlag) {
		this.bindingMobileFlag = bindingMobileFlag;
	}

	public Boolean getBindingEmailFlag() {
		return bindingEmailFlag;
	}

	public void setBindingEmailFlag(Boolean bindingEmailFlag) {
		this.bindingEmailFlag = bindingEmailFlag;
	}

	public Boolean getRealnameAuthFlag() {
		return realnameAuthFlag;
	}

	public void setRealnameAuthFlag(Boolean realnameAuthFlag) {
		this.realnameAuthFlag = realnameAuthFlag;
	}

	public Boolean getSecretPasswordFlag() {
		return secretPasswordFlag;
	}

	public void setSecretPasswordFlag(Boolean secretPasswordFlag) {
		this.secretPasswordFlag = secretPasswordFlag;
	}

	public Boolean getBindingBankcardFlag() {
		return bindingBankcardFlag;
	}

	public void setBindingBankcardFlag(Boolean bindingBankcardFlag) {
		this.bindingBankcardFlag = bindingBankcardFlag;
	}

	public String getTotalInvestCN() {
		return totalInvestCN;
	}

	public void setTotalInvestCN(String totalInvestCN) {
		this.totalInvestCN = totalInvestCN;
	}

	public String getTotalReturnCN() {
		return totalReturnCN;
	}

	public void setTotalReturnCN(String totalReturnCN) {
		this.totalReturnCN = totalReturnCN;
	}

	public String getAccountBalanceCN() {
		return accountBalanceCN;
	}

	public void setAccountBalanceCN(String accountBalanceCN) {
		this.accountBalanceCN = accountBalanceCN;
	}
	
}
