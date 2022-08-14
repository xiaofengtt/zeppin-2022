package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.ActivityInfo;
import cn.product.worldmall.entity.ActivityInfo.ActivityInfoName;
import cn.product.worldmall.entity.activity.ConfigBuyfree;
import cn.product.worldmall.entity.activity.ConfigScorelottery;
import cn.product.worldmall.util.JSONUtils;

public class ActivityInfoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7279673426376358338L;
	
	private String name;//活动UUID
	private String title;//活动名称
	private String status;//状态normal-开启/disable-结束
	private String configuration;//配置信息跟config字段一致，可以直接取config里的值
	private Timestamp starttime;//开始时间
	private Timestamp endtime;//结束时间
	private String bannerUrl;//banner图地址
	private String linkUrl;//链接地址
	private Integer sort;//排序
	
	private Object config;//配置信息
	private Integer currentTimes;//当前可参与次数
	private Boolean isPartake;//当前是否可参与
	
	public ActivityInfoVO (ActivityInfo ai) {
		this.name = ai.getName();
		this.title = ai.getTitle();
		this.status = ai.getStatus();
		this.configuration = ai.getConfiguration();
		this.starttime = ai.getStarttime();
		this.endtime = ai.getEndtime();
		this.bannerUrl = ai.getBannerUrl();
		this.linkUrl = ai.getLinkUrl();
		this.sort = ai.getSort();
		if(ActivityInfoName.BUYFREE.equals(ai.getName())) {
			this.config = JSONUtils.json2obj(ai.getConfiguration(), ConfigBuyfree.class);
		} else if (ActivityInfoName.SCORELOTTERY.equals(ai.getName())) {
			this.config = JSONUtils.json2obj(ai.getConfiguration(), ConfigScorelottery.class);
		} else if (ActivityInfoName.RECOMMEND.equals(ai.getName())) {
			this.config = JSONUtils.json2map(ai.getConfiguration());
		}
		this.currentTimes = 1;
		this.isPartake = true;
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
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
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

	public Object getConfig() {
		return config;
	}

	public void setConfig(Object config) {
		this.config = config;
	}

	public Integer getCurrentTimes() {
		return currentTimes;
	}

	public void setCurrentTimes(Integer currentTimes) {
		this.currentTimes = currentTimes;
	}

	public Boolean getIsPartake() {
		return isPartake;
	}

	public void setIsPartake(Boolean isPartake) {
		this.isPartake = isPartake;
	}
}