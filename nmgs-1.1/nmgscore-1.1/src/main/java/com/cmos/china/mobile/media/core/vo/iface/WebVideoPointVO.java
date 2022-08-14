package com.cmos.china.mobile.media.core.vo.iface;

import com.cmos.china.mobile.media.core.util.DataTimeConvert;

public class WebVideoPointVO {
	
	private String timepoint;
	private String timepointSecond;
	private String showMessage;
	private String showBanner;
	private String showBannerURL;
	private String commodity;
	private String commodityCover;
	private Integer showtime;
	
	public Integer getShowtime() {
		return showtime;
	}
	public void setShowtime(Integer showtime) {
		this.showtime = showtime;
	}
	public String getShowMessage() {
		return showMessage;
	}
	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}
	public String getShowBannerURL() {
		return showBannerURL;
	}
	public void setShowBannerURL(String showBannerURL) {
		this.showBannerURL = showBannerURL;
	}
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	public String getCommodityCover() {
		return commodityCover;
	}
	public void setCommodityCover(String commodityCover) {
		this.commodityCover = commodityCover;
	}
	public String getShowBanner() {
		return showBanner;
	}
	public void setShowBanner(String showBanner) {
		this.showBanner = showBanner;
	}
	public String getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
		this.timepointSecond = DataTimeConvert.getSecondTime(timepoint);
	}
	
	public String getTimepointSecond() {
		return timepointSecond;
	}

	public void setTimepointSecond(String timepointSecond) {
		this.timepointSecond = timepointSecond;
	}
	

}
