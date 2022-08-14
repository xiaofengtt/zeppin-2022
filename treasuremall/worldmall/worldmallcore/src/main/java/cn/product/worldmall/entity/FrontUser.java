package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1648430524231385202L;
	
	@Id
    private String uuid;
	private Integer showId;
	
    private String realname;
    private String idcard;
    private String nickname;
    private String mobile;
    private String email;
    private String sex;
    private String password;
    private Boolean realnameflag;
    
    private String type;
    private String level;
    private String status;
    
    private String registerChannel;
    
    private String openid;
    private String wechaticon;
    
    private String image;
    private String ip;
    private String area;
    private String country;
    
    private Timestamp lastonline;
    private String lastaccessip;
    
    private String agent;
    private Timestamp createtime;
    
    public class FrontUserStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    	public final static String BLACKLIST = "blacklist";//20200507增加黑名单
    	public final static String HIGHRISK = "highrisk";//20200507增加高危用户
    }
    
    public class FrontUserType{
    	public final static String NORMAL = "normal";
    	public final static String ROBOT = "robot";
    }
    
    /**
     * tourists/registered/recharged/VIP
     * @author Administrator
     *
     */
    public class FrontUserLevel{
    	public final static String VISITOR = "visitor";
    	public final static String REGISTERED = "registered";
    	public final static String RECHARGED = "recharged";
    	public final static String VIP = "VIP";
    	public final static String DEMO = "demo";
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRegisterChannel() {
		return registerChannel;
	}

	public void setRegisterChannel(String registerChannel) {
		this.registerChannel = registerChannel;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Timestamp getLastonline() {
		return lastonline;
	}

	public void setLastonline(Timestamp lastonline) {
		this.lastonline = lastonline;
	}

	public String getLastaccessip() {
		return lastaccessip;
	}

	public void setLastaccessip(String lastaccessip) {
		this.lastaccessip = lastaccessip;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}