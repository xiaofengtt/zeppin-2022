package cn.product.score.vo.front;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import cn.product.score.entity.FrontUserBetDetail;

public class FrontUserBetDetailVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 938147592190125722L;
	
	private String uuid;
	private String frontUserBet;
	private String guessingMatch;
	private String guessingMatchOdds;
	private String categoryName;
	private String hometeamName;
	private String awayteamName;
	private Timestamp time;
	private String spread;
	private String result;
	private String finalResult;
	private Boolean flagCorrect;
	private Boolean isRight;
	private BigDecimal odds;
    
    public FrontUserBetDetailVO(FrontUserBetDetail fubd){
    	this.uuid = fubd.getUuid();
    	this.frontUserBet = fubd.getFrontUserBet();
    	this.guessingMatch = fubd.getGuessingMatch();
    	this.guessingMatchOdds = fubd.getGuessingMatchOdds();
    	this.flagCorrect = fubd.getFlagCorrect();
    	this.odds = fubd.getOdds();
    }
    
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getHometeamName() {
		return hometeamName;
	}

	public void setHometeamName(String hometeamName) {
		this.hometeamName = hometeamName;
	}

	public String getAwayteamName() {
		return awayteamName;
	}

	public void setAwayteamName(String awayteamName) {
		this.awayteamName = awayteamName;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
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

	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	public Boolean getFlagCorrect() {
		return flagCorrect;
	}

	public void setFlagCorrect(Boolean flagCorrect) {
		this.flagCorrect = flagCorrect;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

	public BigDecimal getOdds() {
		return odds;
	}

	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}
}