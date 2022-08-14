package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoods.LuckygameGoodsStatus;

public class LuckygameGoodsVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5842415832988995368L;
	
	private String uuid;
	private String goodsId;
	private String gameType;
	private String title;
	private String shortTitle;
	private BigDecimal dPrice;
	private BigDecimal profitRate;
	private BigDecimal betPerShare;
	
	private Integer shares;
	private Integer totalIssueNum;
	private Integer currentIssueNum;
	private Integer endIssueNum;
	
	private String status;
	private Integer sort;
	
	private Boolean promotionFlag;
	private Timestamp createtime;
	
	
	//开奖信息
	private String totalEnd;
	private String currentIssueNumCN;

	private String coverImg;
	private String code;
	
	public LuckygameGoodsVO(LuckygameGoods lg) {
		this.uuid = lg.getUuid();
		this.goodsId = lg.getGoodsId();
		this.gameType = lg.getGameType();
		this.title = lg.getTitle();
		this.shortTitle = lg.getShortTitle();
		this.dPrice = lg.getdPrice();
		this.profitRate = lg.getProfitRate();
		this.betPerShare = lg.getBetPerShare();
		this.shares = lg.getShares();
		this.totalIssueNum = lg.getTotalIssueNum();
		this.currentIssueNum = lg.getCurrentIssueNum();
		this.status = lg.getStatus();
		this.sort = lg.getSort();
		this.promotionFlag = lg.getPromotionFlag();
		this.createtime = lg.getCreatetime();
		this.totalEnd = "";
		this.currentIssueNumCN = lg.getCurrentIssueNum()+"";
		if(lg.getCurrentIssueNum() != null && lg.getCurrentIssueNum() > 0) {
			if(LuckygameGoodsStatus.NORMAL.equals(lg.getStatus())) {
				this.endIssueNum = lg.getCurrentIssueNum() - 1;
			} else {
				this.endIssueNum = lg.getCurrentIssueNum();
			}
		} else {
			this.endIssueNum = 0;
		}
		
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
	public BigDecimal getProfitRate() {
		return profitRate;
	}
	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
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
	public Integer getTotalIssueNum() {
		return totalIssueNum;
	}
	public void setTotalIssueNum(Integer totalIssueNum) {
		this.totalIssueNum = totalIssueNum;
	}
	public Integer getCurrentIssueNum() {
		return currentIssueNum;
	}
	public void setCurrentIssueNum(Integer currentIssueNum) {
		this.currentIssueNum = currentIssueNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getTotalEnd() {
		return totalEnd;
	}
	public void setTotalEnd(String totalEnd) {
		this.totalEnd = totalEnd;
	}
	public String getCurrentIssueNumCN() {
		return currentIssueNumCN;
	}
	public void setCurrentIssueNumCN(String currentIssueNumCN) {
		this.currentIssueNumCN = currentIssueNumCN;
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


	public Integer getEndIssueNum() {
		return endIssueNum;
	}


	public void setEndIssueNum(Integer endIssueNum) {
		this.endIssueNum = endIssueNum;
	}
}