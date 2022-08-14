package cn.product.treasuremall.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderGroup;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.util.Utlity;

public class LuckygameGoodsIssueVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1915358797564310439L;
	private String uuid;//抽奖期数ID
	private String luckygameGoods;//抽奖期数所属奖品ID
	private String luckygameGoodsStatus;//抽奖期数所属奖品ID
	private String goodsId;//奖品所属商品ID
	private String gameType;//游戏类型
	private String title;//商品标题
	private String shortTitle;//商品短标题
	private BigDecimal dPrice;//商品金币价格
	private BigDecimal price;//商品金币价格对应
	private BigDecimal betPerShare;//平均单笔价格
	
	private Integer shares;//总参数人次
	private Integer issueNum;//当前期数
	private Integer sort;//排序参数
	private Boolean promotionFlag;//是否参与活动
	
	private Integer betShares;//已参与人次
	private Integer remainShares;//剩余参与人次
	
	private String status;//抽奖奖品状态
	
	private Timestamp createtime;//抽奖开启时间
	private Timestamp lotterytime;//抽奖募集时间
	private Timestamp finishedtime;//抽奖结束时间
	private Integer luckyNumber;//本次抽奖幸运号码
	private String luckyGroup;//本次抽奖幸运号码
	
	//中奖人信息
	private String showIdStr;//中奖人SHOWID
	private String nickname;//中奖人昵称
	private BigDecimal dAmount;//中奖人参与金币金额
	private BigDecimal actualDAmount;//中奖人实际参与金币金额
	private String imageUrl;//用户头像
	private Integer buyCount;//参与人次
	private String frontUser;
	
	//商品信息
	private String coverImg;//商品封面
	private String code;//商品编号
	private String goodsType;
	
	private List<String> imgList;//商品列表图
	private List<String> imgDetail;//商品详情图
	private List<String> imgShow;//商品展示图
	
	private Integer currentIssueNum;//进行的当前期数
	private String currentIssueUuid;//当前进行中的抽奖期数ID--暂未更新
	private String currentIssueStatus;//进行的当前期数
	private Long timeLine;//抽奖倒计时
	
	private Boolean isFirstBuy;
	
	private Map<String, Integer> groupShares;
	private String tabs;//标签信息
	
	public LuckygameGoodsIssueVO(LuckygameGoodsIssue lgi) {
		this.uuid = lgi.getUuid();
		this.luckygameGoods = lgi.getLuckygameGoods();
		this.goodsId = lgi.getGoodsId();
		this.gameType = lgi.getGameType();
		this.title = lgi.getTitle();
		this.shortTitle = lgi.getShortTitle();
		this.dPrice = lgi.getdPrice();
		this.betPerShare = lgi.getBetPerShare();
		this.shares = lgi.getShares();
		this.issueNum = lgi.getIssueNum();
		this.sort = lgi.getSort();
		this.promotionFlag = lgi.getPromotionFlag();
		this.betShares = lgi.getBetShares();
		this.remainShares = lgi.getRemainShares();
		this.status = lgi.getStatus();
		this.createtime = lgi.getCreatetime();
		this.lotterytime = lgi.getLotterytime();
		this.finishedtime = lgi.getFinishedtime();
		this.luckyNumber = lgi.getLuckyNumber();
		this.luckyGroup = lgi.getLuckyGroup();
		this.showIdStr = "无";
		this.nickname = "无";
		this.dAmount = BigDecimal.ZERO;
		this.actualDAmount = BigDecimal.ZERO;
		this.goodsType = lgi.getGoodsType();
		this.imageUrl = Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png";//默认头像
		this.isFirstBuy = true;
		this.groupShares = new HashMap<String, Integer>();
		this.groupShares.put(FrontUserPaymentOrderGroup.LUCKY, Integer.valueOf(shares/2));
		this.groupShares.put(FrontUserPaymentOrderGroup.RAIDER, Integer.valueOf(shares/2));
		this.tabs = "";
	}


	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
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
	public BigDecimal getdPrice() {
		return dPrice;
	}
	public void setdPrice(BigDecimal dPrice) {
		this.dPrice = dPrice;
	}
	public BigDecimal getBetPerShare() {
		return betPerShare;
	}
	public void setBetPerShare(BigDecimal betPerShare) {
		this.betPerShare = betPerShare;
	}
	public Integer getShares() {
		return shares;
	}
	public void setShares(Integer shares) {
		this.shares = shares;
	}
	public String getLuckygameGoods() {
		return luckygameGoods;
	}
	public void setLuckygameGoods(String luckygameGoods) {
		this.luckygameGoods = luckygameGoods;
	}
	public Integer getIssueNum() {
		return issueNum;
	}
	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Boolean getPromotionFlag() {
		return promotionFlag;
	}
	public void setPromotionFlag(Boolean promotionFlag) {
		this.promotionFlag = promotionFlag;
	}
	public Integer getBetShares() {
		return betShares;
	}
	public void setBetShares(Integer betShares) {
		this.betShares = betShares;
	}
	public Integer getRemainShares() {
		return remainShares;
	}
	public void setRemainShares(Integer remainShares) {
		this.remainShares = remainShares;
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
	public Timestamp getLotterytime() {
		return lotterytime;
	}
	public void setLotterytime(Timestamp lotterytime) {
		this.lotterytime = lotterytime;
	}
	public Timestamp getFinishedtime() {
		return finishedtime;
	}
	public void setFinishedtime(Timestamp finishedtime) {
		this.finishedtime = finishedtime;
	}
	public Integer getLuckyNumber() {
		return luckyNumber;
	}
	public void setLuckyNumber(Integer luckyNumber) {
		this.luckyNumber = luckyNumber;
	}


	public String getShowIdStr() {
		return showIdStr;
	}


	public void setShowIdStr(String showIdStr) {
		this.showIdStr = showIdStr;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public BigDecimal getdAmount() {
		return dAmount;
	}


	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
	}


	public BigDecimal getActualDAmount() {
		return actualDAmount;
	}


	public void setActualDAmount(BigDecimal actualDAmount) {
		this.actualDAmount = actualDAmount;
	}


	public String getCoverImg() {
		return coverImg;
	}


	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public List<String> getImgList() {
		return imgList;
	}


	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}


	public List<String> getImgDetail() {
		return imgDetail;
	}


	public void setImgDetail(List<String> imgDetail) {
		this.imgDetail = imgDetail;
	}


	public List<String> getImgShow() {
		return imgShow;
	}


	public void setImgShow(List<String> imgShow) {
		this.imgShow = imgShow;
	}


	public Integer getCurrentIssueNum() {
		return currentIssueNum;
	}


	public void setCurrentIssueNum(Integer currentIssueNum) {
		this.currentIssueNum = currentIssueNum;
	}


	public String getCurrentIssueUuid() {
		return currentIssueUuid;
	}


	public void setCurrentIssueUuid(String currentIssueUuid) {
		this.currentIssueUuid = currentIssueUuid;
	}


	public String getGoodsType() {
		return goodsType;
	}


	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public Integer getBuyCount() {
		return buyCount;
	}


	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}


	public Long getTimeLine() {
		return timeLine;
	}


	public void setTimeLine(Long timeLine) {
		this.timeLine = timeLine;
	}


	public Boolean getIsFirstBuy() {
		return isFirstBuy;
	}


	public void setIsFirstBuy(Boolean isFirstBuy) {
		this.isFirstBuy = isFirstBuy;
	}


	public String getFrontUser() {
		return frontUser;
	}


	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getLuckygameGoodsStatus() {
		return luckygameGoodsStatus;
	}


	public void setLuckygameGoodsStatus(String luckygameGoodsStatus) {
		this.luckygameGoodsStatus = luckygameGoodsStatus;
	}


	public String getCurrentIssueStatus() {
		return currentIssueStatus;
	}


	public void setCurrentIssueStatus(String currentIssueStatus) {
		this.currentIssueStatus = currentIssueStatus;
	}


	public String getLuckyGroup() {
		return luckyGroup;
	}


	public void setLuckyGroup(String luckyGroup) {
		this.luckyGroup = luckyGroup;
	}


	public Map<String, Integer> getGroupShares() {
		return groupShares;
	}


	public void setGroupShares(Map<String, Integer> groupShares) {
		this.groupShares = groupShares;
	}


	public String getTabs() {
		return tabs;
	}


	public void setTabs(String tabs) {
		this.tabs = tabs;
	}
}