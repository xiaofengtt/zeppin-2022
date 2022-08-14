package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class ActivityInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3001125379075483568L;
	
	@Id
	private String name;
	private String title;
	private String status;
	private Timestamp createtime;
	private String configuration;
	private Timestamp starttime;
	private Timestamp endtime;
	private String bannerUrl;
	private String linkUrl;
	private Integer sort;
	
	public class ActivityInfoName {
		public final static String BUYFREE = "buyfree";//0元购
    	public final static String CHECKIN = "checkin";//签到
    	public final static String SCORELOTTERY = "scorelottery";//积分抽奖
    	public final static String FIRSTCHARGE = "firstcharge";//首充送
    	public final static String RECHARGE = "recharge";//充值送
    	public final static String RECOMMEND = "recommend";//拉皮条
	}
	
    public class ActivityInfoStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
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