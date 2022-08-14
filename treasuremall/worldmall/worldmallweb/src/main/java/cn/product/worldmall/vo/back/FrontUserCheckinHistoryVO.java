package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserCheckinHistory;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;

public class FrontUserCheckinHistoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4201977114466675792L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String activityInfoCheckinPrize;
	
	private String prizeTitle;
	private String prizeType;
	private String prizeCover;
	private String prize;
	private Integer dayNum;
	
	private Timestamp createtime;
	private String status;
	private String remark;

	private String operator;
	private Timestamp operattime;
	
	private String ip;
	
	private String provideInfo;
    
	
	private String nickname;
	private String imageUrl;
	private ProvideInfoVO detailInfo;
	private String prizeCoverUrl;
	private String mobile;
	
	public FrontUserCheckinHistoryVO () {
		
	}
	
	public FrontUserCheckinHistoryVO (FrontUserCheckinHistory fuch) {
		this.uuid = fuch.getUuid();
		this.frontUser = fuch.getFrontUser();
		this.frontUserShowId = fuch.getFrontUserShowId();
		this.activityInfoCheckinPrize = fuch.getActivityInfoCheckinPrize();
		this.prize = fuch.getPrize();
		this.prizeTitle = fuch.getPrizeTitle();
		this.prizeCover = fuch.getPrizeCover();
		this.prizeType = fuch.getPrizeType();
		this.dayNum = fuch.getDayNum();
		
		this.createtime = fuch.getCreatetime();
		this.status = fuch.getStatus();
		this.remark = fuch.getRemark();
		this.operattime = fuch.getOperattime();
		this.ip = fuch.getIp();
		this.provideInfo = fuch.getProvideInfo();
		if(!Utlity.checkStringNull(fuch.getProvideInfo())) {
			this.detailInfo = JSONUtils.json2obj(fuch.getProvideInfo(), ProvideInfoVO.class);
		}
		this.imageUrl = "/image/img-defaultAvatar.png";//默认头像
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

	public String getActivityInfoCheckinPrize() {
		return activityInfoCheckinPrize;
	}

	public void setActivityInfoCheckinPrize(String activityInfoCheckinPrize) {
		this.activityInfoCheckinPrize = activityInfoCheckinPrize;
	}

	public String getPrizeTitle() {
		return prizeTitle;
	}

	public void setPrizeTitle(String prizeTitle) {
		this.prizeTitle = prizeTitle;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrizeCover() {
		return prizeCover;
	}

	public void setPrizeCover(String prizeCover) {
		this.prizeCover = prizeCover;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getProvideInfo() {
		return provideInfo;
	}

	public void setProvideInfo(String provideInfo) {
		this.provideInfo = provideInfo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ProvideInfoVO getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(ProvideInfoVO detailInfo) {
		this.detailInfo = detailInfo;
	}

	public String getPrizeCoverUrl() {
		return prizeCoverUrl;
	}

	public void setPrizeCoverUrl(String prizeCoverUrl) {
		this.prizeCoverUrl = prizeCoverUrl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}