package cn.zeppin.project.chinamobile.media.web.vo.iface;


import java.util.List;

import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.utility.DataTimeConvert;

public class WebVideoVO implements Entity {
	
	private static final long serialVersionUID = 2394291890491517372L;
	
	private String videoThumbanil;
	private String videoTimeLength;
	private String timepointSecond;
	private String videoTitle;
	private String videoContext;
	private List<String> videoURLs;
	private List<WebVideoPointVO> webVideoPoints;
	
	
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}
	public String getVideoContext() {
		return videoContext;
	}
	public void setVideoContext(String videoContext) {
		this.videoContext = videoContext;
	}
	public List<String> getVideoURLs() {
		return videoURLs;
	}
	public void setVideoURLs(List<String> videoURLs) {
		this.videoURLs = videoURLs;
	}
	public List<WebVideoPointVO> getWebVideoPoints() {
		return webVideoPoints;
	}
	public void setWebVideoPoints(List<WebVideoPointVO> webVideoPoints) {
		this.webVideoPoints = webVideoPoints;
	}
	public String getVideoThumbanil() {
		return videoThumbanil;
	}
	public void setVideoThumbanil(String videoThumbanil) {
		this.videoThumbanil = videoThumbanil;
	}
	public String getVideoTimeLength() {
		return videoTimeLength;
	}
	public void setVideoTimeLength(String videoTimeLength) {
		this.videoTimeLength = videoTimeLength;
		this.timepointSecond = DataTimeConvert.getSecondTime(videoTimeLength);
	}
	public String getTimepointSecond() {
		return timepointSecond;
	}
	public void setTimepointSecond(String timepointSecond) {
		this.timepointSecond = timepointSecond;
	}
	
}
