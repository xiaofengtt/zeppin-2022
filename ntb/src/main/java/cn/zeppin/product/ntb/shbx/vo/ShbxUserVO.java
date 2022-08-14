package cn.zeppin.product.ntb.shbx.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class ShbxUserVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String realname;
	private String realnameFull;
	private String idcard;
	private String idcardFull;
	private String phone;
	private String phoneFull;
	private Boolean realnameAuthFlag;
	private String bankcardCount;
	private String accountBalance;
	private String currentAccount;
	private Boolean flagCurrent;
	
	private String publicName;
	
	private String uuid;
	
	private Boolean pwdFlag;
	
	private Integer couponCount;
	private Boolean messageFlag;
	
 	public ShbxUserVO(){
		
	}
	
	public ShbxUserVO(ShbxUser shbxUser){
		this.uuid = shbxUser.getUuid();
		this.realnameFull = shbxUser.getRealname()==null ? "" : shbxUser.getRealname();
		this.realname = Utlity.getStarName(shbxUser.getRealname());
		this.idcardFull = shbxUser.getIdcard();
		this.idcard = Utlity.getStarIdcard(shbxUser.getIdcard());
		this.phoneFull = shbxUser.getMobile();
		this.phone = Utlity.getStarMobile(shbxUser.getMobile());
		this.realnameAuthFlag = shbxUser.getRealnameAuthFlag();
		if(shbxUser.getAccountBalance() != null){
			this.accountBalance = shbxUser.getAccountBalance().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		}else{
			this.accountBalance = "0.00";
		}
		this.flagCurrent = shbxUser.getFlagCurrent();
		String sex = "";
		if(shbxUser.getSex() != null && !"".equals(shbxUser.getSex())){
			if(Utlity.SEX_MAN.equals(shbxUser.getSex())){
				sex = "先生";
			} else {
				sex = "女士";
			}
			if(shbxUser.getRealnameAuthFlag()){
				this.publicName = shbxUser.getRealname().substring(0, 1)+sex;
			}else{
				this.publicName = "";
			}
		} else {
			this.publicName = "";
		}
		
		if(!Utlity.checkStringNull(shbxUser.getLoginPassword())){
			this.pwdFlag = true;
		} else {
			this.pwdFlag = false;
		}
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

	public String getIdcardFull() {
		return idcardFull;
	}

	public void setIdcardFull(String idcardFull) {
		this.idcardFull = idcardFull;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneFull() {
		return phoneFull;
	}

	public void setPhoneFull(String phoneFull) {
		this.phoneFull = phoneFull;
	}

	public Boolean getRealnameAuthFlag() {
		return realnameAuthFlag;
	}

	public void setRealnameAuthFlag(Boolean realnameAuthFlag) {
		this.realnameAuthFlag = realnameAuthFlag;
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
