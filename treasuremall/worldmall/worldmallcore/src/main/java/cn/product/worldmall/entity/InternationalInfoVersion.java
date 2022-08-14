package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class InternationalInfoVersion implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2047090915588606007L;
	
	@Id
	private String uuid;
	private String internationalInfo;
	private String version;
	
	private String status;
	private Timestamp createtime;
	private String code;
	private String channel;
	private String versionName;
	private Boolean flagWithdraw;
	
	
	public class InternationalInfoVersionStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
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