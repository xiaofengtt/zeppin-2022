package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserMessage;

public class FrontUserMessageVO implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7996275447500988830L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String title;
	private String content;
	private String sourceId;
	private String sourceType;
	private String sourceUrl;
	private String sourceImage;
	private String sourceImageUrl;
	private String type;
	private String status;
    private Timestamp createtime;
	
    public FrontUserMessageVO(FrontUserMessage fum) {
    	this.uuid = fum.getUuid();
    	this.frontUser = fum.getFrontUser();
		this.frontUserShowId = fum.getFrontUserShowId();
		this.title = fum.getTitle();
		this.content = fum.getContent();
		this.sourceId = fum.getSourceId();
		this.sourceType = fum.getSourceType();
		this.sourceUrl = fum.getSourceUrl();
		this.sourceImage = fum.getSourceImage();
		this.type = fum.getTitle();
		this.status = fum.getStatus();
		this.createtime = fum.getCreatetime();
    }

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(String sourceImage) {
		this.sourceImage = sourceImage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getSourceImageUrl() {
		return sourceImageUrl;
	}

	public void setSourceImageUrl(String sourceImageUrl) {
		this.sourceImageUrl = sourceImageUrl;
	}
}