package com.cmos.china.mobile.media.core.bean;

import java.sql.Timestamp;

public class VideoCommodityPoint extends Entity {
	
	private static final long serialVersionUID = -1893804230663939238L;
	private String id;
	private String video;
	private String timepoint;
	private String iframe;
	private String commodity;
	private String showType;
	private String showMessage;
	private String showPosition;
	private String showGif;
	private String showBanner;
	private String status;
	private String creator;
	private Timestamp createtime;
	private Integer showtime;
	
	public VideoCommodityPoint() {
		super();
	}

	public VideoCommodityPoint(String id, String video, String timepoint,
			String iframe, String commodity, String showType,
			String showMessage, String showPosition, String showGif,
			String showBanner, String status, String creator,
			Timestamp createtime,Integer showtime) {
		super();
		this.id = id;
		this.video = video;
		this.timepoint = timepoint;
		this.iframe = iframe;
		this.commodity = commodity;
		this.showType = showType;
		this.showMessage = showMessage;
		this.showPosition = showPosition;
		this.showGif = showGif;
		this.showBanner = showBanner;
		this.status = status;
		this.creator = creator;
		this.createtime = createtime;
		this.showtime = showtime;
	}

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
	
	public String getTimepoint() {
		return timepoint;
	}

	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}
	
	public String getIframe() {
		return iframe;
	}

	public void setIframe(String iframe) {
		this.iframe = iframe;
	}
	
	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	
	public String getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}
	
	public String getShowPosition() {
		return showPosition;
	}

	public void setShowPosition(String showPosition) {
		this.showPosition = showPosition;
	}
	
	public String getShowGif() {
		return showGif;
	}

	public void setShowGif(String showGif) {
		this.showGif = showGif;
	}
	
	public String getShowBanner() {
		return showBanner;
	}

	public void setShowBanner(String showBanner) {
		this.showBanner = showBanner;
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

	public Integer getShowtime() {
		return showtime;
	}

	public void setShowtime(Integer showtime) {
		this.showtime = showtime;
	}
	
}
