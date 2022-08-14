package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.InternationalInfoVersion;

public class InternationalInfoVersionVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8175072725846649012L;
	
	private String uuid;
	private String internationalInfo;
	private String internationalInfoName;
	private String internationalInfoNameEn;
	private String version;
	
	private String status;
	private Timestamp createtime;
	private String code;
	private String channel;
	private String versionName;
	private Boolean flagWithdraw;
	
	public InternationalInfoVersionVO() {
		super();
	}
	
	public InternationalInfoVersionVO (InternationalInfoVersion iiv) {
		this.uuid = iiv.getUuid();
		this.internationalInfo = iiv.getInternationalInfo();
		this.version = iiv.getVersion();
		this.status = iiv.getStatus();
		this.createtime = iiv.getCreatetime();
		this.code = iiv.getCode();
		this.channel = iiv.getChannel();
		this.versionName = iiv.getVersionName();
		this.flagWithdraw = iiv.getFlagWithdraw();
		this.internationalInfoName = "";
		this.internationalInfoNameEn = "";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getInternationalInfo() {
		return internationalInfo;
	}

	public void setInternationalInfo(String internationalInfo) {
		this.internationalInfo = internationalInfo;
	}

	public String getInternationalInfoName() {
		return internationalInfoName;
	}

	public void setInternationalInfoName(String internationalInfoName) {
		this.internationalInfoName = internationalInfoName;
	}

	public String getInternationalInfoNameEn() {
		return internationalInfoNameEn;
	}

	public void setInternationalInfoNameEn(String internationalInfoNameEn) {
		this.internationalInfoNameEn = internationalInfoNameEn;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Boolean getFlagWithdraw() {
		return flagWithdraw;
	}

	public void setFlagWithdraw(Boolean flagWithdraw) {
		this.flagWithdraw = flagWithdraw;
	}
	
}