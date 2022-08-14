package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import cn.product.payment.util.api.JSONUtils;
import cn.product.worldmall.entity.ActivityInfo;

public class ActivityInfoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3194777591675775142L;
	
	private String name;
	private String title;
	private String status;
	private Timestamp createtime;
	private String configuration;
	private Map<String, Object> configurationMap;
	private Timestamp starttime;
	private Timestamp endtime;
	private String bannerUrl;
	private String linkUrl;
	private Integer sort;

	public ActivityInfoVO(ActivityInfo ai) {
		this.name = ai.getName();
		this.title = ai.getTitle();
		this.status = ai.getStatus();
		this.createtime = ai.getCreatetime();
		this.configuration = ai.getConfiguration();
		if(ai.getConfiguration() != null){
			this.configurationMap = JSONUtils.json2map(configuration);
		}
		this.starttime = ai.getStarttime();
		this.endtime = ai.getEndtime();
		this.bannerUrl = ai.getBannerUrl();
		this.linkUrl = ai.getLinkUrl();
		this.sort = ai.getSort();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Map<String, Object> getConfigurationMap() {
		return configurationMap;
	}

	public void setConfigurationMap(Map<String, Object> configurationMap) {
		this.configurationMap = configurationMap;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}