package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.QcbEmployee.QcbEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.DataTimeConvert;
import cn.zeppin.product.utility.Utlity;

public class QcbEmployeeVO implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5579429803003028972L;
	
	private String name;
	private String idcard;
	private String mobile;
	private String email;
	
	private String accountBalance;
	private String totalAmount;
	private String totalInvest;
	private String totalReturn;
	private String currentAccount;
	private Boolean flagCurrent;
	
	private String publicName;
	private String uuid;
	
	private String openid;
	private String wechatIcon;
	
	private Boolean pwdFlag;
	private Boolean realnameAuthFlag;
	private Boolean bindingMobileFlag;
	private Boolean bindingBankcardFlag;
	
	private String secretPassword;
	private Boolean secretPasswordFlag;
	
	private String status;
	private String statusCN;
	
	private Timestamp lastLoginTime;
	private String lastLoginTimeCN;
	private String lastLoginIp;
	private Timestamp createtime;
	private String createtimeCN;
	
	
 	public QcbEmployeeVO(){
		
	}
	
	public QcbEmployeeVO(QcbEmployee qe){
		this.uuid = qe.getUuid();
		this.name = qe.getRealname()==null ? "" : qe.getRealname();
		this.idcard = qe.getIdcard();
		this.mobile = qe.getMobile();
		this.email = qe.getEmail() == null ? "" : qe.getEmail();
		this.openid = qe.getOpenid() == null ? "" : qe.getOpenid();
		this.wechatIcon = qe.getWechatIcon() == null ? "" : qe.getWechatIcon();
		this.realnameAuthFlag = true;
		this.bindingMobileFlag = true;
		this.secretPassword = qe.getSecretPassword() == null ? "" : qe.getSecretPassword();
		this.secretPasswordFlag = qe.getSecretPasswordFlag() == null ? false : qe.getSecretPasswordFlag();
		this.flagCurrent = qe.getFlagCurrent();
		if(qe.getAccountBalance() != null){
			this.accountBalance = qe.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.accountBalance = "0.00";
		}
		
		if(qe.getTotalInvest() != null){
			this.totalInvest = Utlity.numFormat4UnitDetail(qe.getTotalInvest());
		}else{
			this.totalInvest = "0.00";
		}
		
		if(qe.getTotalInvest() != null && qe.getAccountBalance() != null){
			this.totalAmount = Utlity.numFormat4UnitDetail(qe.getTotalInvest().add(qe.getAccountBalance()));
		} else {
			this.totalAmount = "0.00";
		}
		
		if(qe.getTotalReturn() != null ){
			this.totalReturn = Utlity.numFormat4UnitDetail(qe.getTotalReturn());
		} else {
			this.totalReturn = "0.00";
		}
		
		String sex = "";
		if(qe.getSex() != null && !"".equals(qe.getSex())){
			if(Utlity.SEX_MAN.equals(qe.getSex())){
				sex = "先生";
			} else {
				sex = "女士";
			}
			this.publicName = qe.getRealname().substring(0, 1)+sex;
		} else {
			this.publicName = "";
		}
		
		if(!Utlity.checkStringNull(qe.getLoginPassword())){
			this.pwdFlag = true;
		} else {
			this.pwdFlag = false;
		}
		
		this.lastLoginTime = qe.getLastLoginTime();
		if(qe.getLastLoginTime() != null){
			this.lastLoginTimeCN = DataTimeConvert.timespanToString(qe.getLastLoginTime(),"yyyy-MM-dd HH:mm:ss");
		} else {
			this.lastLoginTimeCN = "";
		}
		
		this.lastLoginIp = qe.getLastLoginIp() == null ? "" : qe.getLastLoginIp();
		
		this.status = qe.getStatus();
		if(QcbEmployeeStatus.NORMAL.equals(qe.getStatus())){
			this.statusCN = "正常";
		} else if (QcbEmployeeStatus.DISABLE.equals(qe.getStatus())){
			this.statusCN = "注销";
		} else if (QcbEmployeeStatus.DELETED.equals(qe.getStatus())){
			this.statusCN = "删除";
		} else {
			this.statusCN = "无";
		}
		
		this.createtime = qe.getCreatetime();
		this.createtimeCN = DataTimeConvert.timespanToString(qe.getCreatetime(),"yyyy-MM-dd HH:mm:ss");
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(String currentAccount) {
		this.currentAccount = currentAccount;
	}

	public Boolean getFlagCurrent() {
		return flagCurrent;
	}

	public void setFlagCurrent(Boolean flagCurrent) {
		this.flagCurrent = flagCurrent;
	}

	public String getPublicName() {
		return publicName;
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWechatIcon() {
		return wechatIcon;
	}

	public void setWechatIcon(String wechatIcon) {
		this.wechatIcon = wechatIcon;
	}

	public Boolean getPwdFlag() {
		return pwdFlag;
	}
	
	public void setPwdFlag(Boolean pwdFlag) {
		this.pwdFlag = pwdFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getRealnameAuthFlag() {
		return realnameAuthFlag;
	}

	public void setRealnameAuthFlag(Boolean realnameAuthFlag) {
		this.realnameAuthFlag = realnameAuthFlag;
	}

	public String getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getTotalInvest() {
		return totalInvest;
	}
	
	public void setTotalInvest(String totalInvest) {
		this.totalInvest = totalInvest;
	}
	
	public String getTotalReturn() {
		return totalReturn;
	}
	
	public void setTotalReturn(String totalReturn) {
		this.totalReturn = totalReturn;
	}

	public Boolean getBindingMobileFlag() {
		return bindingMobileFlag;
	}

	public void setBindingMobileFlag(Boolean bindingMobileFlag) {
		this.bindingMobileFlag = bindingMobileFlag;
	}

	public Boolean getBindingBankcardFlag() {
		return bindingBankcardFlag;
	}

	public void setBindingBankcardFlag(Boolean bindingBankcardFlag) {
		this.bindingBankcardFlag = bindingBankcardFlag;
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

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getLastLoginTimeCN() {
		return lastLoginTimeCN;
	}

	public void setLastLoginTimeCN(String lastLoginTimeCN) {
		this.lastLoginTimeCN = lastLoginTimeCN;
	}

	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
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

	public String getSecretPassword() {
		return secretPassword;
	}

	public void setSecretPassword(String secretPassword) {
		this.secretPassword = secretPassword;
	}

	public Boolean getSecretPasswordFlag() {
		return secretPasswordFlag;
	}

	public void setSecretPasswordFlag(Boolean secretPasswordFlag) {
		this.secretPasswordFlag = secretPasswordFlag;
	}
}
