package cn.product.score.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



public class GuessingMatchOdds implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5028543581189688998L;
	
	
	private String uuid;
	private String guessingMatch;
	private String guessingMatchType;
	private String spread;
	private String result;
	private BigDecimal odds;
	private Boolean isRight;
	private String status;
	private String creator;
	private Timestamp createtime;
    
    public class GuessingMatchOddsStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
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

	public String getGuessingMatchType() {
		return guessingMatchType;
	}

	public void setGuessingMatchType(String guessingMatchType) {
		this.guessingMatchType = guessingMatchType;
	}

	public String getSpread() {
		return spread;
	}

	public void setSpread(String spread) {
		this.spread = spread;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public BigDecimal getOdds() {
		return odds;
	}

	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
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