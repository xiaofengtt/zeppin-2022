package com.whaty.platform.entity.user;

public abstract class StatusChangedStudent extends Student{

	private String statusChange_date;
	
	private String statusChangeEndDate;
	
	private String ssid;	//ÍËÑ§¼ÇÂ¼ID

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getStatusChangeEndDate() {
		return statusChangeEndDate;
	}

	public void setStatusChangeEndDate(String statusChangeEndDate) {
		this.statusChangeEndDate = statusChangeEndDate;
	}

	public String getStatusChange_date() {
		return statusChange_date;
	}

	public void setStatusChange_date(String suspend_date) {
		this.statusChange_date = suspend_date;
	}
}
