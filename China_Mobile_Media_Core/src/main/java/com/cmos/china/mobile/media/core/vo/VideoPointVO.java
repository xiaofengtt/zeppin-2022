package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.cmos.china.mobile.media.core.util.DataTimeConvert;
import com.cmos.china.mobile.media.core.util.Utlity;

public class VideoPointVO implements Serializable{
	
	private static final long serialVersionUID = 1127052004167094188L;
	private String id;
	private String video;
	private String iframe;
	private String iframePath;
	private String commodity;
	private String commodityName;
	private String timepoint;
	private String timepointSecond;
	private String showMessage;
	private String showBanner;
	private String showBannerUrl;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getIframe() {
		return iframe;
	}

	public void setIframe(String iframe) {
		this.iframe = iframe;
	}
	
	public String getIframePath() {
		return iframePath;
	}

	public void setIframePath(String iframePath) {
		this.iframePath = iframePath;
	}
	
	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	
	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
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
	
	public String getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}
	
	public String getShowBanner() {
		return showBanner;
	}

	public void setShowBanner(String showBanner) {
		this.showBanner = showBanner;
	}
	
	public String getShowBannerUrl() {
		return showBannerUrl;
	}

	public void setShowBannerUrl(String showBannerUrl) {
		this.showBannerUrl = showBannerUrl;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
		this.createtimeCN = Utlity.timeSpanToString(createtime);
	}
	
	public String getCreatetimeCN() {
		return createtimeCN;
	}

	public void setCreatetimeCN(String createtimeCN) {
		this.createtimeCN = createtimeCN;
	}
}
