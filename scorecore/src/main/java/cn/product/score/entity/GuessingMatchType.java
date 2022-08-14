package cn.product.score.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class GuessingMatchType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3624511413022908482L;
	
	@Id
	private String uuid;
	private String guessingMatch;
	private String oddsType;
	private String type;
	private BigDecimal maxMoney;
	private Boolean flagSingle;
	private Timestamp endtime;
	private String status;
	private String creator;
	private Timestamp createtime;
    
	public class GuessingMatchTypeStatus{
		public final static String NORMAL = "normal";
    	public final static String PUBLISH = "publish";
    	public final static String WAITING = "waiting";
    	public final static String FINISHED = "finished";
    	public final static String DELETE = "delete";
    }
	
    public class GuessingMatchTypeType{
    	public final static String VICTORY = "victory";
    	public final static String SCORE = "score";
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getGuessingMatch() {
		return guessingMatch;
	}

	public void setGuessingMatch(String guessingMatch) {
		this.guessingMatch = guessingMatch;
	}

	public String getOddsType() {
		return oddsType;
	}

	public void setOddsType(String oddsType) {
		this.oddsType = oddsType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(BigDecimal maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Boolean getFlagSingle() {
		return flagSingle;
	}

	public void setFlagSingle(Boolean flagSingle) {
		this.flagSingle = flagSingle;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}