package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;



public class FrontUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1648430524231385202L;
	
	
    private String uuid;
    private String realname;
    private String idcard;
    private String nickname;
    private String mobile;
    private String email;
    private String sex;
    private String password;
    private Boolean realnameflag;
    private String type;
    private String status;
    private String openid;
    private String wechaticon;
    private Timestamp lasttime;
    private String lastip;
    private Timestamp createtime;
    
    public class FrontUserStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }
    
    public class FrontUserType{
    	public final static String NORMAL = "normal";
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getWechaticon() {
		return wechaticon;
	}

	public void setWechaticon(String wechaticon) {
		this.wechaticon = wechaticon;
	}

	public Timestamp getLasttime() {
		return lasttime;
	}

	public void setLasttime(Timestamp lasttime) {
		this.lasttime = lasttime;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}