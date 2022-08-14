package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class InternationalRateHistory implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 468303351881609932L;
	
	@Id
	private String uuid;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private String sourceStr;
	private String dataInfo;
	private String dailyDate;
	

	public class InternationalRateHistoryStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String CHECKED = "checked";
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getSourceStr() {
		return sourceStr;
	}
	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}
	public String getDataInfo() {
		return dataInfo;
	}
	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}
	public String getDailyDate() {
		return dailyDate;
	}
	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}
}