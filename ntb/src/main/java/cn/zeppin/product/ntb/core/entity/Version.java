package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;


/**
 * Version entity. 
 */

@Entity
@Table(name = "version")
public class Version extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3892482108161141199L;
	
	private String uuid;
	private Integer version;
	private String versionName;
	private String versionDes;
	private String resource;
	private String device;
	private Boolean flagCompel;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public class VersionStatus{
		public final static String PUBLISHED = "published";
		public final static String UNPUBLISH = "unpublish";
		public final static String DELETED = "deleted";
		public final static String DISABLE = "disable";
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "status", nullable = false, length = 20)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "version", nullable = false, length = 11)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "version_name", nullable = false, length = 20)
	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	@Column(name = "version_des", length = 200)
	public String getVersionDes() {
		return versionDes;
	}

	public void setVersionDes(String versionDes) {
		this.versionDes = versionDes;
	}

	@Column(name = "resource", nullable = false, length = 36)
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Column(name = "device", nullable = false, length = 10)
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	@Column(name = "flag_compel", nullable = false)
	public Boolean getFlagCompel() {
		return flagCompel;
	}

	public void setFlagCompel(Boolean flagCompel) {
		this.flagCompel = flagCompel;
	}

	@Column(name = "creator", nullable = false, length = 36)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
}
