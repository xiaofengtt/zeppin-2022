package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.product.treasuremall.entity.CapitalPlatform;

public class CapitalPlatformVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8825164147760853113L;
	
	private String uuid;
	private String name;
	private String transType;
	private String type;
	private Integer sort;
	
	private Boolean isRecommend;
	private Boolean isUniqueAmount;
	private Boolean isRandomAmount;
	private String status;
	private String logo;
	private String logoUrl;
	private String explanation;
	private String explanImg;
	private String explanImgUrl;
	private String remark;
	
	private BigDecimal min;
	private BigDecimal max;
	
	public CapitalPlatformVO(CapitalPlatform cp){
		this.uuid = cp.getUuid();
		this.name = cp.getName();
		this.transType = cp.getTransType();
		this.type = cp.getType();
		this.sort = cp.getSort();
		this.isRecommend = cp.getIsRecommend();
		this.isUniqueAmount = cp.getIsUniqueAmount();
		this.isRandomAmount = cp.getIsRandomAmount();
		this.status = cp.getStatus();
		this.logo = cp.getLogo();
		this.explanation = cp.getExplanation();
		this.explanImg = cp.getExplanImg();
		this.remark = cp.getRemark();
		this.max = cp.getMax();
		this.min = cp.getMin();
		
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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Boolean getIsUniqueAmount() {
		return isUniqueAmount;
	}

	public void setIsUniqueAmount(Boolean isUniqueAmount) {
		this.isUniqueAmount = isUniqueAmount;
	}

	public Boolean getIsRandomAmount() {
		return isRandomAmount;
	}

	public void setIsRandomAmount(Boolean isRandomAmount) {
		this.isRandomAmount = isRandomAmount;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getExplanImg() {
		return explanImg;
	}

	public void setExplanImg(String explanImg) {
		this.explanImg = explanImg;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getExplanImgUrl() {
		return explanImgUrl;
	}

	public void setExplanImgUrl(String explanImgUrl) {
		this.explanImgUrl = explanImgUrl;
	}
}