package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserBuyfreeOrder;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;

public class FrontUserBuyfreeOrderVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4201977114466675792L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String activityInfoBuyfree;
	private String goodsId;
	private Integer sharenum;
	private Boolean isLucky;
	private Timestamp createtime;
	private String status;
	private String remark;

	private Timestamp operattime;
	private Timestamp winningTime;
	private String ip;
	
	private String provideInfo;
    
	private String title;
	private String shortTitle;
	private String cover;//商品封面图
	private Integer shares;//总份额
	private String code;//商品编码
	private BigDecimal price;//商品价值
	private Integer issueNum;
	
	private String nickname;
	private String imageUrl;
	private ProvideInfoVO detailInfo;
	private String receiveType;
	
	public FrontUserBuyfreeOrderVO (FrontUserBuyfreeOrder fubo) {
		this.uuid = fubo.getUuid();
		this.frontUser = fubo.getFrontUser();
		this.frontUserShowId = fubo.getFrontUserShowId();
		this.activityInfoBuyfree = fubo.getActivityInfoBuyfree();
		this.goodsId = fubo.getGoodsId();
		this.sharenum = fubo.getSharenum();
		this.isLucky = fubo.getIsLucky();
		
		this.createtime = fubo.getCreatetime();
		this.status = fubo.getStatus();
		this.remark = fubo.getRemark();
		this.operattime = fubo.getOperattime();
		this.winningTime = fubo.getWinningTime();
		this.ip = fubo.getIp();
		this.provideInfo = fubo.getProvideInfo();
		if(!Utlity.checkStringNull(fubo.getProvideInfo())) {
			this.detailInfo = JSONUtils.json2obj(fubo.getProvideInfo(), ProvideInfoVO.class);
		}
		this.imageUrl = "/image/img-defaultAvatar.png";//默认头像
		this.receiveType = fubo.getReceiveType();
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

	public String getActivityInfoBuyfree() {
		return activityInfoBuyfree;
	}

	public void setActivityInfoBuyfree(String activityInfoBuyfree) {
		this.activityInfoBuyfree = activityInfoBuyfree;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSharenum() {
		return sharenum;
	}

	public void setSharenum(Integer sharenum) {
		this.sharenum = sharenum;
	}

	public Boolean getIsLucky() {
		return isLucky;
	}

	public void setIsLucky(Boolean isLucky) {
		this.isLucky = isLucky;
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

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public Timestamp getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Timestamp winningTime) {
		this.winningTime = winningTime;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public Integer getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
}