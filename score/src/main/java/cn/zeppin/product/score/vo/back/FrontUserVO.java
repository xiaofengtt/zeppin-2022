package cn.zeppin.product.score.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.FrontUserAccount;

public class FrontUserVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3614089750174839122L;
	
	private String uuid;
	private String realname;
    private String idcard;
    private String nickname;
    private String mobile;
    private String email;
    private String sex;
    private Boolean realnameflag;
    private String type;
    private String status;
    private String FrontUserAccount;
    private BigDecimal balanceLock;
	private BigDecimal balanceFree;
    private Timestamp createtime;
    
	public FrontUserVO(FrontUser fu, FrontUserAccount fua){
		this.uuid = fu.getUuid();
		this.realname = fu.getRealname();
		this.idcard = fu.getIdcard();
		this.nickname = fu.getNickname();
		this.mobile = fu.getMobile();
		this.email = fu.getEmail();
		this.sex = fu.getSex();
		this.realnameflag = fu.getRealnameflag();
		this.type = fu.getType();
		this.status = fu.getStatus();
		this.createtime = fu.getCreatetime();
		if(fua != null){
			this.FrontUserAccount = fua.getUuid();
			this.balanceFree = fua.getBalanceFree();
			this.balanceLock = fua.getBalanceLock();
		}else{
			this.balanceFree = BigDecimal.ZERO;
			this.balanceLock = BigDecimal.ZERO;
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

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Boolean getRealnameflag() {
		return realnameflag;
	}

	public void setRealnameflag(Boolean realnameflag) {
		this.realnameflag = realnameflag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFrontUserAccount() {
		return FrontUserAccount;
	}

	public void setFrontUserAccount(String frontUserAccount) {
		FrontUserAccount = frontUserAccount;
	}

	public BigDecimal getBalanceLock() {
		return balanceLock;
	}

	public void setBalanceLock(BigDecimal balanceLock) {
		this.balanceLock = balanceLock;
	}

	public BigDecimal getBalanceFree() {
		return balanceFree;
	}

	public void setBalanceFree(BigDecimal balanceFree) {
		this.balanceFree = balanceFree;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}