package com.cmos.china.mobile.media.core.bean;

import java.math.BigInteger;
import java.sql.Timestamp;

import com.cmos.china.mobile.media.core.util.Utlity;

public class AppVersion {
	private String id;
	private String component;
	private String path;
	private String versionna;
	private String versionnu;
	private BigInteger size;
	private String province;
	private String creator;
	private String status;
	private Timestamp createtime;
	private String appexplain;
	private String createtimeCN;
	public String getCreatetimeCN() {
		return createtimeCN;
	}


	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}


	public AppVersion() {
		super();
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getVersionna() {
		return versionna;
	}
	public void setVersionna(String versionna) {
		this.versionna = versionna;
	}
	public String getVersionnu() {
		return versionnu;
	}
	public void setVersionnu(String versionnu) {
		this.versionnu = versionnu;
	}
	public BigInteger getSize() {
		return size;
	}
	public void setSize(BigInteger size) {
		this.size = size;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
		this.createtimeCN = Utlity.timeSpanToString(createtime);
	}


	public String getAppexplain() {
		return appexplain;
	}


	public void setAppexplain(String appexplain) {
		this.appexplain = appexplain;
	}

}
