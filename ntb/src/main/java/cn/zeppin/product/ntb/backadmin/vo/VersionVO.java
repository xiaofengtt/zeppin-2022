package cn.zeppin.product.ntb.backadmin.vo;

import java.sql.Timestamp;

import cn.zeppin.product.ntb.core.entity.Version;
import cn.zeppin.product.ntb.core.entity.Version.VersionStatus;
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
	private String resourceName;
	private String device;
	private Boolean flagCompel;
	private String flagCompelCN;
	private String status;
	private String statusCN;
	private String creator;
	private String creatorCN;
	private Timestamp createtime;
	private String createtimeCN;
	
	public VersionVO(){
		
	}
	
	public VersionVO(Version version) {
		this.uuid = version.getUuid();
		this.version = version.getVersion();
		this.versionName = version.getVersionName();
		this.versionDes = version.getVersionDes();
		this.resource = version.getResource();
		this.device = version.getDevice();
		this.flagCompel = version.getFlagCompel();
		if(version.getFlagCompel()){
			flagCompelCN = "是";
		} else {
			flagCompelCN = "否";
		}
		this.status = version.getStatus();
		if(VersionStatus.PUBLISHED.equals(version.getStatus())){
			this.statusCN = "发布";
		} else if (VersionStatus.UNPUBLISH.equals(version.getStatus())) {
			this.statusCN = "待发布";
		} else if (VersionStatus.DISABLE.equals(version.getStatus())) {
			this.statusCN = "已停用";
		} else if (VersionStatus.DELETED.equals(version.getStatus())) {
			this.statusCN = "已删除";
		} else {
			this.statusCN = "-";
		}
		this.creator = version.getCreator();
		this.creatorCN = "-";
		this.createtime = version.getCreatetime();
		this.createtimeCN = Utlity.timeSpanToString(version.getCreatetime());
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


	public String getCreatetimeCN() {
		return createtimeCN;
	}


	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getFlagCompelCN() {
		return flagCompelCN;
	}

	public void setFlagCompelCN(String flagCompelCN) {
		this.flagCompelCN = flagCompelCN;
	}

	public String getStatusCN() {
		return statusCN;
	}

	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getCreatorCN() {
		return creatorCN;
	}

	public void setCreatorCN(String creatorCN) {
		this.creatorCN = creatorCN;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
}
