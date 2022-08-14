package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;

import javax.persistence.Id;

public class RobotSetting implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5857531660477638041L;

	@Id
    private String robotId;
    private String gameType;
    
    private BigDecimal minPay;
    private BigDecimal maxPay;
    
    private Integer periodMin;
    private Integer periodRandom;
    
    private Time worktimeBegin;
    private Time worktimeEnd;
    
    private String goods;
    private BigDecimal goodsPriceMin;
    private BigDecimal goodsPriceMax;
    
    private String status;
    
    private Boolean isAll;
    
    public class RobotSettingStatus {
    	public final static String START = "start";
    	public final static String STOP = "stop";
    }

	public String getRobotId() {
		return robotId;
	}

	public void setRobotId(String robotId) {
		this.robotId = robotId;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public BigDecimal getMinPay() {
		return minPay;
	}

	public void setMinPay(BigDecimal minPay) {
		this.minPay = minPay;
	}

	public BigDecimal getMaxPay() {
		return maxPay;
	}

	public void setMaxPay(BigDecimal maxPay) {
		this.maxPay = maxPay;
	}

	public Integer getPeriodMin() {
		return periodMin;
	}

	public void setPeriodMin(Integer periodMin) {
		this.periodMin = periodMin;
	}

	public Integer getPeriodRandom() {
		return periodRandom;
	}

	public void setPeriodRandom(Integer periodRandom) {
		this.periodRandom = periodRandom;
	}

	public Time getWorktimeBegin() {
		return worktimeBegin;
	}

	public void setWorktimeBegin(Time worktimeBegin) {
		this.worktimeBegin = worktimeBegin;
	}

	public Time getWorktimeEnd() {
		return worktimeEnd;
	}

	public void setWorktimeEnd(Time worktimeEnd) {
		this.worktimeEnd = worktimeEnd;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public BigDecimal getGoodsPriceMin() {
		return goodsPriceMin;
	}

	public void setGoodsPriceMin(BigDecimal goodsPriceMin) {
		this.goodsPriceMin = goodsPriceMin;
	}

	public BigDecimal getGoodsPriceMax() {
		return goodsPriceMax;
	}

	public void setGoodsPriceMax(BigDecimal goodsPriceMax) {
		this.goodsPriceMax = goodsPriceMax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
    
}