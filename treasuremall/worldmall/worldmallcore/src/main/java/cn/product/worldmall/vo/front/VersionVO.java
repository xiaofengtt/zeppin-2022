package cn.product.worldmall.vo.front;

import java.io.Serializable;

import cn.product.worldmall.entity.Version;

public class VersionVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6778651880807550347L;
	
	private String type;
	private String bundleid;
	private String displayname;
	private String channel;
	private String name;
	private Integer code;
//	private String adverturl;
//	private String mainurl;
	private Boolean flagupdate;
	private Boolean flagcompel;
//	private String downloadurl;
	private Boolean flag;
	private Boolean flagfund;

	public VersionVO() {
		
	}
	
	public VersionVO(Version version) {
		this.type = version.getType();
		this.bundleid = version.getBundleid();
		this.displayname = version.getDisplayname();
		this.channel = version.getChannel();
		this.name = version.getName();
		this.code = version.getCode();
//		this.adverturl = version.getAdverturl();
//		if(version.getFlag()) {
//			this.adverturl = version.getMainurl();
//		} else {
//			this.adverturl = null;
//		}
//		this.mainUrl
		this.flagupdate = version.getFlagupdate();
		this.flagcompel = version.getFlagcompel();
//		this.downloadurl = version.getDownloadurl();
		this.flag = version.getFlag();
		this.flagfund = false;
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

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Boolean getFlagfund() {
		return flagfund;
	}

	public void setFlagfund(Boolean flagfund) {
		this.flagfund = flagfund;
	}
}