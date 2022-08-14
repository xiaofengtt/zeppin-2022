package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.util.Utlity;

public class FrontUserVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6280837852687880279L;
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
    
    private String ip;
    private String area;
    
    private Timestamp lastonline;
    private String lastaccessip;
    
    private String agent;
    private Timestamp createtime;
	
	private String image;
	private String imageURL;

    private String country;
    private String areaCode;
	
	public FrontUserVO() {
		super();
	}
    
	public FrontUserVO(FrontUser fu){
		this.uuid = fu.getUuid();
		this.showId = fu.getShowId();
		this.realname = fu.getRealname();
		this.idcard = fu.getIdcard();
		this.nickname = fu.getNickname();
		this.mobile = fu.getMobile();
		this.email = fu.getEmail();
		this.sex = fu.getSex();
		this.password = "password";
		this.realnameflag = fu.getRealnameflag();
		this.type = fu.getType();
		this.status = fu.getStatus();
		this.createtime = fu.getCreatetime();
		this.lastonline = fu.getLastonline();
		this.lastaccessip = fu.getLastaccessip();
		this.agent = fu.getAgent();
		this.level = fu.getLevel();
		this.area = fu.getArea();
		this.ip = fu.getIp();
		this.registerChannel = fu.getRegisterChannel();
		this.image = fu.getImage();
		this.imageURL = Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png";
		this.country = fu.getCountry();
		this.areaCode = "";
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

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}