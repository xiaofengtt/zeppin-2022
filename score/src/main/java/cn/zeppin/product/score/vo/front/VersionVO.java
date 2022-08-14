package cn.zeppin.product.score.vo.front;

import java.io.Serializable;

import cn.zeppin.product.score.entity.Version;

public class VersionVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3731388162474553677L;
	
	private String type;
	private String bundleid;
	private String displayname;
	private String channel;
	private String name;
	private Integer code;
	private String adverturl;
	private String mainurl;
	private Boolean flagupdate;
	private Boolean flagcompel;
	private String downloadurl;

	public VersionVO() {
		
	}
	
	public VersionVO(Version version) {
		this.type = version.getType();
		this.bundleid = version.getBundleid();
		this.displayname = version.getDisplayname();
		this.channel = version.getChannel();
		this.name = version.getName();
		this.code = version.getCode();
		this.adverturl = version.getAdverturl();
		if(version.getFlag()) {
			this.adverturl = version.getMainurl();
		} else {
			this.adverturl = null;
		}
//		this.mainUrl
		this.flagupdate = version.getFlagupdate();
		this.flagcompel = version.getFlagcompel();
		this.downloadurl = version.getDownloadurl();
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

	public String getAdverturl() {
		return adverturl;
	}

	public void setAdverturl(String adverturl) {
		this.adverturl = adverturl;
	}

	public String getMainurl() {
		return mainurl;
	}

	public void setMainurl(String mainurl) {
		this.mainurl = mainurl;
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

	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
}