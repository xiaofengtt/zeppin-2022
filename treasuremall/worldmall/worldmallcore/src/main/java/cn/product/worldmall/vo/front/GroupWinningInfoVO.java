package cn.product.worldmall.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.LuckygameGoodsIssue;

public class GroupWinningInfoVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2166684011580601065L;
	
	private String uuid;//抽奖期数ID
	private String luckygameGoods;//抽奖期数所属奖品ID
	private String goodsId;//奖品所属商品ID
	private String gameType;//游戏类型
	private String title;//商品标题
	private String shortTitle;//商品短标题
	
	private Integer shares;//总参数人次
	private Integer issueNum;//当前期数
	
	private Timestamp createtime;//抽奖开启时间
	private Timestamp lotterytime;//抽奖募集时间
	private Timestamp finishedtime;//抽奖结束时间
	private Integer luckyNumber;//本次抽奖幸运号码
	private String luckyGroup;//本次抽奖幸运号码
	
	
	public GroupWinningInfoVO() {
		super();
	}
	
	public GroupWinningInfoVO (LuckygameGoodsIssue lgi) {
		this.uuid = lgi.getUuid();
		this.luckygameGoods = lgi.getLuckygameGoods();
		this.goodsId = lgi.getGoodsId();
		this.gameType = lgi.getGameType();
		this.title = lgi.getTitle();
		this.shortTitle = lgi.getShortTitle();
		this.createtime = lgi.getCreatetime();
		this.lotterytime = lgi.getLotterytime();
		this.finishedtime = lgi.getFinishedtime();
		this.luckyNumber = lgi.getLuckyNumber();
		this.luckyGroup = lgi.getLuckyGroup();
		this.shares = lgi.getShares();
		this.issueNum = lgi.getIssueNum();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getLuckygameGoods() {
		return luckygameGoods;
	}

	public void setLuckygameGoods(String luckygameGoods) {
		this.luckygameGoods = luckygameGoods;
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

	public String getLuckyGroup() {
		return luckyGroup;
	}

	public void setLuckyGroup(String luckyGroup) {
		this.luckyGroup = luckyGroup;
	}

	public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
	}

	public Integer getIssueNum() {
		return issueNum;
	}

	public void setIssueNum(Integer issueNum) {
		this.issueNum = issueNum;
	}
}