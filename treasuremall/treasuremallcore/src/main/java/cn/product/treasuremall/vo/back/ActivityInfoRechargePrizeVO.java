package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import cn.product.treasuremall.entity.ActivityInfoRechargePrize;

public class ActivityInfoRechargePrizeVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1050407038620680479L;
	
	private String uuid;
	private String activityInfo;
	private String status;
	private Timestamp createtime;
	private String creator;
	private String prizeType;
	private String prize;
	private String capitalAccount;
	private Integer sort;
	private List<Object> listPrize;

	public ActivityInfoRechargePrizeVO(ActivityInfoRechargePrize aifp) {
		this.uuid = aifp.getUuid();
		this.activityInfo = aifp.getActivityInfo();
		this.status = aifp.getActivityInfo();
		this.createtime = aifp.getCreatetime();
		this.creator = aifp.getCreator();
		this.sort = aifp.getSort();
		this.prizeType = aifp.getPrizeType();
		this.prize = aifp.getPrize();
		this.capitalAccount = aifp.getCapitalAccount();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getCapitalAccount() {
		return capitalAccount;
	}

	public void setCapitalAccount(String capitalAccount) {
		this.capitalAccount = capitalAccount;
	}


	public List<Object> getListPrize() {
		return listPrize;
	}

	public void setListPrize(List<Object> listPrize) {
		this.listPrize = listPrize;
	}
}