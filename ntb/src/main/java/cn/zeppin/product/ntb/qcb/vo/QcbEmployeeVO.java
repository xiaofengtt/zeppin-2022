package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.QcbEmployee;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class QcbEmployeeVO implements Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5579429803003028972L;
	
	private String name;
	private String nameFull;
	private String idcard;
	private String mobile;
	private String mobileFull;
	private String bankcardCount;
	private String accountBalance;
	private String currentAccount;
	private Boolean flagCurrent;
	
	private String publicName;
	private String uuid;
//	private String wechatName;
	private String wechatIcon;
	
	private Boolean pwdFlag;
	private Boolean realnameAuthFlag;
	
 	public QcbEmployeeVO(){
		
	}
	
	public QcbEmployeeVO(QcbEmployee qe){
		this.uuid = qe.getUuid();
		this.name = qe.getRealname()==null ? "" : qe.getRealname();
		this.nameFull = Utlity.getStarName(qe.getRealname());
		this.idcard = Utlity.getStarIdcard(qe.getIdcard());
		this.mobile = Utlity.getStarMobile(qe.getMobile());
		this.mobileFull = qe.getMobile();
		this.wechatIcon = qe.getWechatIcon();
		this.realnameAuthFlag = true;
		if(qe.getAccountBalance() != null){
			this.accountBalance = qe.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.accountBalance = "0.00";
		}
		this.flagCurrent = qe.getFlagCurrent();
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
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getBankcardCount() {
		return bankcardCount;
	}

	public void setBankcardCount(String bankcardCount) {
		this.bankcardCount = bankcardCount;
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

	public String getNameFull() {
		return nameFull;
	}

	public void setNameFull(String nameFull) {
		this.nameFull = nameFull;
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

	public String getMobileFull() {
		return mobileFull;
	}
	
	public void setMobileFull(String mobileFull) {
		this.mobileFull = mobileFull;
	}
}
