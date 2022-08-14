package cn.zeppin.product.score.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Id;

public class FrontUserBetDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8216051208991605174L;
	
	@Id
	private String uuid;
	private String frontUserBet;
	private String guessingMatch;
	private String guessingMatchOdds;
	private Boolean flagCorrect;
	private BigDecimal odds;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontUserBet() {
		return frontUserBet;
	}

	public void setFrontUserBet(String frontUserBet) {
		this.frontUserBet = frontUserBet;
	}

	public String getGuessingMatch() {
		return guessingMatch;
	}

	public void setGuessingMatch(String guessingMatch) {
		this.guessingMatch = guessingMatch;
	}

	public String getGuessingMatchOdds() {
		return guessingMatchOdds;
	}

	public void setGuessingMatchOdds(String guessingMatchOdds) {
		this.guessingMatchOdds = guessingMatchOdds;
	}

	public Boolean getFlagCorrect() {
		return flagCorrect;
	}

	public void setFlagCorrect(Boolean flagCorrect) {
		this.flagCorrect = flagCorrect;
	}

	public BigDecimal getOdds() {
		return odds;
	}

	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}
}