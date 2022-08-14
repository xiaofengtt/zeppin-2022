package cn.zeppin.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.zeppin.product.score.entity.InfoMatch;

public class MatchVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5147005060781313545L;
	
	private String uuid;
	private String category;
	private String categoryName;
	private String season;
	private String round;
	private String roundName;
	private Timestamp time;
	private String progress;
	private String hometeam;
	private String hometeamName;
	private String hometeamIconUrl;
	private String awayteam;
	private String awayteamName;
	private String awayteamIconUrl;
	private String finalresult;
	private String penaltyresult;
	private String status;
	
	public MatchVO(InfoMatch match){
		this.uuid = match.getUuid();
		this.category = match.getCategory();
		this.season = match.getSeason();
		this.round = match.getRound();
		this.roundName = match.getRoundName();
		this.time = match.getTime();
		this.progress = match.getProgress();
		this.hometeam = match.getHometeam();
		this.awayteam = match.getAwayteam();
		this.finalresult = match.getFinalresult();
		this.penaltyresult = match.getPenaltyresult();
		this.status = match.getStatus();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getRoundName() {
		return roundName;
	}

	public void setRoundName(String roundName) {
		this.roundName = roundName;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getHometeam() {
		return hometeam;
	}

	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}

	public String getAwayteam() {
		return awayteam;
	}

	public void setAwayteam(String awayteam) {
		this.awayteam = awayteam;
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

	public String getHometeamIconUrl() {
		return hometeamIconUrl;
	}

	public void setHometeamIconUrl(String hometeamIconUrl) {
		this.hometeamIconUrl = hometeamIconUrl;
	}

	public String getAwayteamIconUrl() {
		return awayteamIconUrl;
	}

	public void setAwayteamIconUrl(String awayteamIconUrl) {
		this.awayteamIconUrl = awayteamIconUrl;
	}

	public String getFinalresult() {
		return finalresult;
	}

	public void setFinalresult(String finalresult) {
		this.finalresult = finalresult;
	}

	public String getPenaltyresult() {
		return penaltyresult;
	}

	public void setPenaltyresult(String penaltyresult) {
		this.penaltyresult = penaltyresult;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}