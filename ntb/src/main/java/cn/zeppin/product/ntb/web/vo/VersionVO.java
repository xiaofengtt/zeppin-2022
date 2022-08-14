package cn.zeppin.product.ntb.web.vo;

import cn.zeppin.product.ntb.core.entity.Version;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

public class VersionVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String uuid;
	private Integer version;
	private String versionName;
	private String versionDes;
	private String resource;
	private String resourceUrl;
	private String device;
	private Boolean flagCompel;
	private String status;
	
	public VersionVO(){
		
	}
	
	public VersionVO(Version version) {
		this.uuid = version.getUuid();
		this.version = version.getVersion();
		this.versionName = version.getVersionName();
		this.versionDes = version.getVersionDes();
		this.resource = version.getResource();
		this.device = version.getDevice();
		if(Utlity.DEVICE_NUMBER_ANDROID.equals(version.getDevice())){
			this.device = "Android";
		} else if(Utlity.DEVICE_NUMBER_IOS.equals(version.getDevice())){
			this.device = "IOS";
		} else {
			this.device = "-";
		}
		this.flagCompel = version.getFlagCompel();
		this.status = version.getStatus();
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public String getVersionName() {
		return versionName;
	}


	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}


	public String getVersionDes() {
		return versionDes;
	}


	public void setVersionDes(String versionDes) {
		this.versionDes = versionDes;
	}


	public String getResource() {
		return resource;
	}


	public void setResource(String resource) {
		this.resource = resource;
	}


	public String getDevice() {
		return device;
	}


	public void setDevice(String device) {
		this.device = device;
	}


	public Boolean getFlagCompel() {
		return flagCompel;
	}


	public void setFlagCompel(Boolean flagCompel) {
		this.flagCompel = flagCompel;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	
}
