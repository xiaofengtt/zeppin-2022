package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.FrontUserScorelotteryHistory;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;

public class FrontUserScorelotteryHistoryVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1337852879495572917L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String activityInfoScorelotteryPrize;
	
	private BigDecimal scoreAmount;
	
	private String prizeTitle;
	private String prizeType;
	private String prizeCover;
	private String prize;
	
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
	
	public FrontUserScorelotteryHistoryVO () {
		
	}
	
	public FrontUserScorelotteryHistoryVO (FrontUserScorelotteryHistory fush) {
		this.uuid = fush.getUuid();
		this.frontUser = fush.getFrontUser();
		this.frontUserShowId = fush.getFrontUserShowId();
		this.activityInfoScorelotteryPrize = fush.getActivityInfoScorelotteryPrize();
		
		this.scoreAmount = fush.getScoreAmount();
		
		this.prize = fush.getPrize();
		this.prizeTitle = fush.getPrizeTitle();
		this.prizeCover = fush.getPrizeCover();
		this.prizeType = fush.getPrizeType();
		
		this.createtime = fush.getCreatetime();
		this.status = fush.getStatus();
		this.remark = fush.getRemark();
		this.operattime = fush.getOperattime();
		this.ip = fush.getIp();
		this.provideInfo = fush.getProvideInfo();
		if(!Utlity.checkStringNull(fush.getProvideInfo())) {
			this.detailInfo = JSONUtils.json2obj(fush.getProvideInfo(), ProvideInfoVO.class);
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

	public String getActivityInfoScorelotteryPrize() {
		return activityInfoScorelotteryPrize;
	}

	public void setActivityInfoScorelotteryPrize(String activityInfoScorelotteryPrize) {
		this.activityInfoScorelotteryPrize = activityInfoScorelotteryPrize;
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

	public BigDecimal getScoreAmount() {
		return scoreAmount;
	}

	public void setScoreAmount(BigDecimal scoreAmount) {
		this.scoreAmount = scoreAmount;
	}

}