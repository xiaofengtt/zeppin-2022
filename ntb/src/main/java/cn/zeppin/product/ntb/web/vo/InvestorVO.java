package cn.zeppin.product.ntb.web.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.Investor;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class InvestorVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String nickname;
	private String realname;
	private String realnameFull;
	private String idcard;
	private String phone;
	private Boolean realnameAuthFlag;
	private Boolean bindingAliFlag;
	private String idcardImgStatus;
	private String idcardImgStatusCN;
	private String bankcardCount;
	private String accountBalance;
	private String currentAccount;
	private Boolean flagCurrent;
	
	private String publicName;
	
	private String uuid;
	
	private String aliPhoto;
	private String aliUserid;
	private String aliNickname;
	
	private Boolean pwdFlag;
	
	private Integer couponCount;
	private Boolean messageFlag;
	
 	public InvestorVO(){
		
	}
	
	public InvestorVO(Investor investor){
		this.uuid = investor.getUuid();
		this.nickname = investor.getNickname()==null ? "" : investor.getNickname();
		this.realnameFull = investor.getRealname()==null ? "" : investor.getRealname();
		this.realname = Utlity.getStarName(investor.getRealname());
		this.idcard = Utlity.getStarIdcard(investor.getIdcard());
		this.phone = Utlity.getStarMobile(investor.getMobile());
		this.realnameAuthFlag = investor.getRealnameAuthFlag();
		if(investor.getAccountBalance() != null){
			this.accountBalance = investor.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.accountBalance = "0.00";
		}
		this.flagCurrent = investor.getFlagCurrent();
		String sex = "";
		if(investor.getSex() != null && !"".equals(investor.getSex())){
			if(Utlity.SEX_MAN.equals(investor.getSex())){
				sex = "先生";
			} else {
				sex = "女士";
			}
			this.publicName = investor.getRealname().substring(0, 1)+sex;
		} else {
			this.publicName = "";
		}
		if(Utlity.checkStringNull(investor.getAliUserid())){
			this.bindingAliFlag = false;
		} else {
			this.bindingAliFlag = true;
		}
		
		this.aliPhoto = investor.getAliPhoto() == null ? "" : investor.getAliPhoto();
		this.aliUserid = investor.getAliUserid() == null ? "" : investor.getAliUserid();
		this.aliNickname = investor.getAliNickname() == null ? "" : investor.getAliNickname();
		
		if(!Utlity.checkStringNull(investor.getLoginPassword())){
			this.pwdFlag = true;
		} else {
			this.pwdFlag = false;
		}
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getRealnameAuthFlag() {
		return realnameAuthFlag;
	}

	public void setRealnameAuthFlag(Boolean realnameAuthFlag) {
		this.realnameAuthFlag = realnameAuthFlag;
	}

	public String getIdcardImgStatus() {
		return idcardImgStatus;
	}

	public void setIdcardImgStatus(String idcardImgStatus) {
		this.idcardImgStatus = idcardImgStatus;
	}

	public String getBankcardCount() {
		return bankcardCount;
	}

	public void setBankcardCount(String bankcardCount) {
		this.bankcardCount = bankcardCount;
	}

	public String getIdcardImgStatusCN() {
		return idcardImgStatusCN;
	}

	public void setIdcardImgStatusCN(String idcardImgStatusCN) {
		this.idcardImgStatusCN = idcardImgStatusCN;
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
	
	public String getRealnameFull() {
		return realnameFull;
	}
	
	public void setRealnameFull(String realnameFull) {
		this.realnameFull = realnameFull;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Boolean getBindingAliFlag() {
		return bindingAliFlag;
	}

	public void setBindingAliFlag(Boolean bindingAliFlag) {
		this.bindingAliFlag = bindingAliFlag;
	}

	public String getAliPhoto() {
		return aliPhoto;
	}

	public void setAliPhoto(String aliPhoto) {
		this.aliPhoto = aliPhoto;
	}

	public String getAliUserid() {
		return aliUserid;
	}

	public void setAliUserid(String aliUserid) {
		this.aliUserid = aliUserid;
	}

	public String getAliNickname() {
		return aliNickname;
	}

	public void setAliNickname(String aliNickname) {
		this.aliNickname = aliNickname;
	}

	public Boolean getPwdFlag() {
		return pwdFlag;
	}
	
	public void setPwdFlag(Boolean pwdFlag) {
		this.pwdFlag = pwdFlag;
	}
	
	public Integer getCouponCount() {
		return couponCount;
	}
	
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	
	public Boolean getMessageFlag() {
		return messageFlag;
	}
	
	public void setMessageFlag(Boolean messageFlag) {
		this.messageFlag = messageFlag;
	}
}
