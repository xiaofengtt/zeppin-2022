package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import cn.product.treasuremall.entity.ActivityInfoFirstchargePrize;

public class ActivityInfoFirstchargePrizeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5899132979586273352L;
	
	private String uuid;
	private String activityInfo;
	private String status;
	private Timestamp createtime;
	private String creator;
	private String prizeType;
	private String prize;
	private BigDecimal amount;
	private String capitalAccount;
	private Integer sort;
	private String prizeCoverUrl;
	private List<Object> listPrize;

	public ActivityInfoFirstchargePrizeVO(ActivityInfoFirstchargePrize aifp) {
		this.uuid = aifp.getUuid();
		this.activityInfo = aifp.getActivityInfo();
		this.status = aifp.getActivityInfo();
		this.createtime = aifp.getCreatetime();
		this.creator = aifp.getCreator();
		this.sort = aifp.getSort();
		this.prizeType = aifp.getPrizeType();
		this.prize = aifp.getPrize();
		this.amount = aifp.getAmount();
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCapitalAccount() {
		return capitalAccount;
	}

	public void setCapitalAccount(String capitalAccount) {
		this.capitalAccount = capitalAccount;
	}

	public String getPrizeCoverUrl() {
		return prizeCoverUrl;
	}

	public void setPrizeCoverUrl(String prizeCoverUrl) {
		this.prizeCoverUrl = prizeCoverUrl;
	}

	public List<Object> getListPrize() {
		return listPrize;
	}

	public void setListPrize(List<Object> listPrize) {
		this.listPrize = listPrize;
	}
}