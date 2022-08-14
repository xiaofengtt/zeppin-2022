package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.util.Utlity;

public class FrontUserPaymentOrderVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6056869485828607744L;
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String goodsIssue;
	private String goodsId;
	private String orderNum;
	private String orderType;
	
	private BigDecimal totalDAmount;
	private BigDecimal actualDAmount;
	private Integer buyCount;
	private BigDecimal poundage;
	private Timestamp createtime;
	private String status;
	
	private String remark;
	private Boolean isPromotion;
	private String promotionId;
	private String promotionTitle;//促销信息（预留）
	
	private Boolean isLucky;
	private Integer luckyNumber;
	
	private String title;
	private String shortTitle;
	private Integer issueNum;
	private String cover;//商品封面图
	private Integer shares;//总份额
	private String code;//商品编码
	private BigDecimal price;//商品价值
	private BigDecimal dPrice;//商品价值金币
	private String gameStatus;
	private Long timeLine;//抽奖倒计时
	private Integer betShares;//已参与人次
	
	private String nickname;
	private String imageURL;
	
	private Boolean isRecevice;//是否兑奖
	private String winningInfo;//兑奖需要的ID
	private Boolean isComment;//是否晒单
	private String receviceType;
	private Timestamp finishedtime;//抽奖结束时间

	public FrontUserPaymentOrderVO() {
		super();
	}


	public FrontUserPaymentOrderVO (FrontUserPaymentOrder fupo) {
		this.uuid = fupo.getUuid();
		this.frontUser = fupo.getFrontUser();
		this.frontUserShowId = fupo.getFrontUserShowId();
		this.goodsIssue = fupo.getGoodsIssue();
		this.goodsId = fupo.getGoodsId();
		this.orderNum = String.valueOf(fupo.getOrderNum());
		this.orderType = fupo.getOrderType();
		this.totalDAmount = fupo.getdAmount();
		this.actualDAmount = fupo.getActualDAmount();
		this.buyCount = fupo.getBuyCount();
		this.poundage = fupo.getPoundage();
		this.createtime = fupo.getCreatetime();
		this.status = fupo.getStatus();
		this.remark = fupo.getRemark();
		this.isPromotion = fupo.getIsPromotion();
		this.promotionId = fupo.getPromotionId();
		this.isLucky = fupo.getIsLucky();
		this.luckyNumber = fupo.getLuckyNumber();
		this.imageURL = Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png";//默认头像
		this.isRecevice = false;
		this.isComment = false;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getGoodsIssue() {
		return goodsIssue;
	}

	public void setGoodsIssue(String goodsIssue) {
		this.goodsIssue = goodsIssue;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getTotalDAmount() {
		return totalDAmount;
	}


	public void setTotalDAmount(BigDecimal totalDAmount) {
		this.totalDAmount = totalDAmount;
	}


	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
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

	public Boolean getIsPromotion() {
		return isPromotion;
	}

	public void setIsPromotion(Boolean isPromotion) {
		this.isPromotion = isPromotion;
	}

	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}

	public Boolean getIsLucky() {
		return isLucky;
	}

	public void setIsLucky(Boolean isLucky) {
		this.isLucky = isLucky;
	}

	public Integer getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(Integer luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public BigDecimal getActualDAmount() {
		return actualDAmount;
	}

	public void setActualDAmount(BigDecimal actualDAmount) {
		this.actualDAmount = actualDAmount;
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


	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}


	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}


	public String getPromotionTitle() {
		return promotionTitle;
	}


	public void setPromotionTitle(String promotionTitle) {
		this.promotionTitle = promotionTitle;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getImageURL() {
		return imageURL;
	}


	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}


	public String getGoodsId() {
		return goodsId;
	}


	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}


	public Integer getIssueNum() {
		return issueNum;
	}


	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}


	public String getGameStatus() {
		return gameStatus;
	}


	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}


	public Integer getBetShares() {
		return betShares;
	}


	public void setBetShares(Integer betShares) {
		this.betShares = betShares;
	}


	public Long getTimeLine() {
		return timeLine;
	}


	public void setTimeLine(Long timeLine) {
		this.timeLine = timeLine;
	}


	public Boolean getIsRecevice() {
		return isRecevice;
	}


	public void setIsRecevice(Boolean isRecevice) {
		this.isRecevice = isRecevice;
	}


	public Boolean getIsComment() {
		return isComment;
	}


	public void setIsComment(Boolean isComment) {
		this.isComment = isComment;
	}


	public String getWinningInfo() {
		return winningInfo;
	}


	public void setWinningInfo(String winningInfo) {
		this.winningInfo = winningInfo;
	}


	public BigDecimal getdPrice() {
		return dPrice;
	}


	public void setdPrice(BigDecimal dPrice) {
		this.dPrice = dPrice;
	}


	public String getReceviceType() {
		return receviceType;
	}


	public void setReceviceType(String receviceType) {
		this.receviceType = receviceType;
	}


	public Timestamp getFinishedtime() {
		return finishedtime;
	}


	public void setFinishedtime(Timestamp finishedtime) {
		this.finishedtime = finishedtime;
	}
}