package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.cmos.china.mobile.media.core.util.DataTimeConvert;
import com.cmos.china.mobile.media.core.util.Utlity;

public class VideoinfoVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String province;
	private String title;
	private String context;
	private String tag;
	private String status;
	private String statusCN;
	private String thumbnail;
	private String video;
	private String timeLength;
	private String timeLengthSecond;
	private String copyright;
	private String source;
	private String author;
	private String creatorName;
	private Timestamp createtime;
	private String createtimeCN;
	private String originalVideoPath;
	private String originalVideoUrl;
	private String originalVideoDpi;
	private String opinion;
	private Integer sequence;
	
	private String getStatusCN(String status){
		if("unchecked".equals(status)){
			return "未审核";
		}else if("uploaded".equals(status)){
			return "待处理";
		}else if("transcoding".equals(status)){
			return "处理中";
		}else if("failed".equals(status)){
			return "处理失败";
		}else if("checked".equals(status)){
			return "已审核";
		}else if("deleted".equals(status)){
			return "已删除";
		}else if("nopass".equals(status)){
			return "审核不通过";
		}else{
			return "";
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		this.statusCN = this.getStatusCN(status);
	}
	
	public void setStatusCN(String statusCN) {
		this.statusCN = statusCN;
	}

	public String getStatusCN() {
		return statusCN;
	}
	
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(String timeLength) {
		this.timeLength = timeLength;
		this.timeLengthSecond = DataTimeConvert.getSecondTime(timeLength);
	}
	
	public String getTimeLengthSecond() {
		return timeLengthSecond;
	}

	public void setTimeLengthSecond(String timeLengthSecond) {
		this.timeLengthSecond = timeLengthSecond;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
	
	public String getOriginalVideoPath() {
		return originalVideoPath;
	}

	public void setOriginalVideoPath(String originalVideoPath) {
		this.originalVideoPath = originalVideoPath;
	}
	
	public String getOriginalVideoUrl() {
		return originalVideoUrl;
	}

	public void setOriginalVideoUrl(String originalVideoUrl) {
		this.originalVideoUrl = originalVideoUrl;
	}
	
	public String getOriginalVideoDpi() {
		return originalVideoDpi;
	}

	public void setOriginalVideoDpi(String originalVideoDpi) {
		this.originalVideoDpi = originalVideoDpi;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
}
