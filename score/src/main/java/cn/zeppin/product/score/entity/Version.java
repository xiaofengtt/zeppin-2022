package cn.zeppin.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class Version implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9133214617141071431L;
	
	@Id
	private String uuid;
	private String type;
	private String bundleid;
	private String displayname;
	private String channel;
	private String name;
	private Integer code;
	private String mainurl;
	private String tempurl;
	private String downloadurl;
	private Boolean flag;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	private String adverturl;
	private Boolean flagupdate;
	private Boolean flagcompel;
	
	public class VersionStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public class VersionType{
		public final static String IOS = "iOS";
		public final static String ANDROID = "android";
	}
	
	public class VersionChannel{
		public final static String APPSTORE = "appStore";
		public final static String TENCENT  = "tencent";
		public final static String BAIDU  = "baidu";
		public final static String HUAWEI  = "huawei";
		public final static String XIAOMI  = "xiaomi";
		public final static String MEIZU  = "meizu";
		public final static String OPPO  = "oppo";
		public final static String VIVO  = "vivo";
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBundleid() {
		return bundleid;
	}

	public void setBundleid(String bundleid) {
		this.bundleid = bundleid;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMainurl() {
		return mainurl;
	}

	public void setMainurl(String mainurl) {
		this.mainurl = mainurl;
	}

	public String getTempurl() {
		return tempurl;
	}

	public void setTempurl(String tempurl) {
		this.tempurl = tempurl;
	}

	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getAdverturl() {
		return adverturl;
	}

	public void setAdverturl(String adverturl) {
		this.adverturl = adverturl;
	}

	public Boolean getFlagupdate() {
		return flagupdate;
	}

	public void setFlagupdate(Boolean flagupdate) {
		this.flagupdate = flagupdate;
	}

	public Boolean getFlagcompel() {
		return flagcompel;
	}

	public void setFlagcompel(Boolean flagcompel) {
		this.flagcompel = flagcompel;
	}

}