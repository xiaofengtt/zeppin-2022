package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;

import cn.product.worldmall.entity.CapitalAccount;

public class CapitalAccountVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529678434857703117L;
	
	private String uuid;
	private String name;
	private BigDecimal min;
	private BigDecimal max;
	private String remark;
	private String type;
	private String transType;
	private String status;
	private Integer sort;
	private String logo;
	private String logoUrl;
	
	private String explanation;
	private String explanImg;
	private String explanImgUrl;
	private Integer userPreReceive; 
	
	public CapitalAccountVO(CapitalAccount ca){
		this.uuid = ca.getUuid();
		this.name = ca.getName();
		this.min = ca.getMin();
		this.max = ca.getMax();
		this.remark = ca.getRemark();
		this.type = ca.getType();
		this.status = ca.getStatus();
		this.sort = ca.getSort();
		this.logo = ca.getLogo();
		this.transType = ca.getTransType();
		this.userPreReceive = ca.getUserPreReceive() == null ? -1 : ca.getUserPreReceive();
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
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

	public String getExplanImgUrl() {
		return explanImgUrl;
	}

	public void setExplanImgUrl(String explanImgUrl) {
		this.explanImgUrl = explanImgUrl;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Integer getUserPreReceive() {
		return userPreReceive;
	}

	public void setUserPreReceive(Integer userPreReceive) {
		this.userPreReceive = userPreReceive;
	}
}