package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import cn.product.worldmall.entity.Goods;

public class GoodsVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 198741553092488683L;
	
	private String uuid;
	private String name;
	private String shortname;
	private String type;
	private String code;
	private String source;
	private String sourceUrl;
	
	private BigDecimal price;
	private BigDecimal costs;
	
	private String description;
	private String video;
	private String videoUrl;
	
	private List<GoodsCoverImageVO> imageList;
	
	private String status;
	private Timestamp createtime;
	private String creator;	
	//20201105增加马甲商品相关字段
	private Boolean demoFlag;
	private String internationalInfo;
	
	public GoodsVO(Goods goods){
		this.uuid = goods.getUuid();
		this.name = goods.getName();
		this.shortname = goods.getShortname();
		this.type = goods.getType();
		this.code = goods.getCode();
		this.source = goods.getSource();
		this.sourceUrl = goods.getSourceUrl();
		this.price = goods.getPrice();
		this.costs = goods.getCosts();
		this.description = goods.getDescription();
		this.video = goods.getVideo();
		this.status = goods.getStatus();
		this.createtime = goods.getCreatetime();
		this.creator = goods.getCreator();
		this.demoFlag = goods.getDemoFlag();
		this.internationalInfo = goods.getInternationalInfo();
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCosts() {
		return costs;
	}

	public void setCosts(BigDecimal costs) {
		this.costs = costs;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public List<GoodsCoverImageVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<GoodsCoverImageVO> imageList) {
		this.imageList = imageList;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Boolean getDemoFlag() {
		return demoFlag;
	}

	public void setDemoFlag(Boolean demoFlag) {
		this.demoFlag = demoFlag;
	}

	public String getInternationalInfo() {
		return internationalInfo;
	}

	public void setInternationalInfo(String internationalInfo) {
		this.internationalInfo = internationalInfo;
	}
}