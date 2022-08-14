package cn.product.score.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.product.score.entity.FrontUserBet;
import cn.product.score.util.JSONUtils;

public class FrontUserBetVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 938147592190125722L;
	
	private String uuid;
	private String orderNum;
	private String frontUser;
	private String type;
	private BigDecimal price;
	private BigDecimal monovalent;
	private Map<String, Object> betMap;
	private List<FrontUserBetDetailVO> detailList;
	private BigDecimal award;
	private String status;
	private Timestamp checktime;
	private Timestamp createtime;
    
    public FrontUserBetVO(FrontUserBet fub){
    	this.uuid = fub.getUuid();
    	this.orderNum = "5" + String.format("%013d", Long.parseLong(fub.getUuid().substring(fub.getUuid().lastIndexOf("-") + 1, fub.getUuid().length()), 16));
    	this.frontUser = fub.getFrontUser();
    	this.type = fub.getType();
    	this.price = fub.getPrice();
    	this.monovalent = fub.getMonovalent();
    	this.betMap = JSONUtils.json2map(fub.getBet());
    	this.award = fub.getAward();
    	this.status = fub.getStatus();
    	this.checktime = fub.getChecktime();
    	this.createtime = fub.getCreatetime();
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMonovalent() {
		return monovalent;
	}

	public void setMonovalent(BigDecimal monovalent) {
		this.monovalent = monovalent;
	}

	public Map<String, Object> getBetMap() {
		return betMap;
	}

	public void setBetMap(Map<String, Object> betMap) {
		this.betMap = betMap;
	}

	public List<FrontUserBetDetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<FrontUserBetDetailVO> detailList) {
		this.detailList = detailList;
	}

	public BigDecimal getAward() {
		return award;
	}

	public void setAward(BigDecimal award) {
		this.award = award;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}