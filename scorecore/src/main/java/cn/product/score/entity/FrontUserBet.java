package cn.product.score.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserBet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9083162811338783275L;
	
	@Id
	private String uuid;
	private String frontUser;
	private String type;
	private BigDecimal price;
	private BigDecimal monovalent;
	private String bet;
	private String compose;
	private Timestamp lastTime;
	private BigDecimal award;
	private String checker;
	private Timestamp checktime;
	private String status;
	private Timestamp createtime;
    
    public class FrontUserBetStatus{
    	public final static String NORMAL = "normal";
    	public final static String FAILED = "failed";
    	public final static String CONFIRM = "confirm";
    	public final static String FINISHED = "finished";
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

	public String getCompose() {
		return compose;
	}

	public void setCompose(String compose) {
		this.compose = compose;
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
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