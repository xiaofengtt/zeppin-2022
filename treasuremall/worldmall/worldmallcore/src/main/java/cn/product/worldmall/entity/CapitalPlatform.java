package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

public class CapitalPlatform implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6761154855694696915L;
	
	@Id
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
	private String explanation;
	private String explanImg;
	private String remark;
	
	private BigDecimal min;
	private BigDecimal max;
	
    public class CapitalPlatformStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String TEST = "test";
    	public final static String DELETE = "delete";
    }

    public class CapitalPlatformType{
    	public final static String WECHAT = "wechat";
    	public final static String ALIPAY = "alipay";
    	public final static String REAPAL = "reapal";
    	public final static String ACICPAY = "acicpay";//兴达支付
    	public final static String JINZUN = "jinzun";//金樽支付
    	public final static String UNION = "union";//Payduoduo
    	public final static String WORLDPAY = "worldpay";//worldpay
    	public final static String CREDIT = "credit";//credit
    	public final static String PAYPAL = "paypal";//paypal
    	public final static String STRIPE = "stripe";//stripe
    }

    public class CapitalPlatformTransType{
    	public final static String COMPANY_ALIPAY = "company_alipay";
    	public final static String ALIPAY_CODE = "alipay_code";
    	public final static String ALIPAY_BANKCARD = "alipay_bankcard";
    	public final static String COMPANY_WECHAT = "company_wechat";
    	public final static String WECHAT_CODE = "wechat_code";
    	public final static String PERSONAL_BANKCARD = "personal_bankcard";
    	public final static String COMPANY_BANKCARD = "company_bankcard";
    	public final static String PAYPAL = "paypal";
    	public final static String STRIPE = "stripe";//stripe
    	public final static String CREDIT = "credit";//credit
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
}