package cn.product.score.vo.back;

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
	private static final long serialVersionUID = -8373815763308425210L;
	
	private String uuid;
	private String frontUser;
	private String frontUserName;
	private String frontUserMobile;
	private String type;
	private BigDecimal price;
	private BigDecimal monovalent;
	private String bet;
	private Map<String, Object> betMap;
	private List<FrontUserBetDetailVO> detailList;
	private String compose;
	private BigDecimal award;
	private String checker;
	private String checkerName;
	private Timestamp checktime;
	private String status;
	private Timestamp createtime;
	
	public FrontUserBetVO(FrontUserBet fub){
		this.uuid = fub.getUuid();
		this.frontUser = fub.getFrontUser();
		this.type = fub.getType();
		this.price = fub.getPrice();
		this.monovalent = fub.getMonovalent();
		this.bet = fub.getBet();
		this.betMap = JSONUtils.json2map(fub.getBet());
		this.compose = fub.getCompose();
		this.award = fub.getAward();
		this.checker = fub.getChecker();
		this.checktime = fub.getChecktime();
		this.status = fub.getStatus();
		this.createtime = fub.getCreatetime();
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

	public String getFrontUserName() {
		return frontUserName;
	}

	public void setFrontUserName(String frontUserName) {
		this.frontUserName = frontUserName;
	}

	public String getFrontUserMobile() {
		return frontUserMobile;
	}

	public void setFrontUserMobile(String frontUserMobile) {
		this.frontUserMobile = frontUserMobile;
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

	public String getBet() {
		return bet;
	}

	public void setBet(String bet) {
		this.bet = bet;
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

	public String getCompose() {
		return compose;
	}

	public void setCompose(String compose) {
		this.compose = compose;
	}

	public BigDecimal getAward() {
		return award;
	}

	public void setAward(BigDecimal award) {
		this.award = award;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
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
}