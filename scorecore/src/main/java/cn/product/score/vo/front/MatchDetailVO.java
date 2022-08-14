package cn.product.score.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.product.score.entity.InfoMatch;
import cn.product.score.util.JSONUtils;

public class MatchDetailVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 221444053763858024L;
	
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
	private List<Map<String, Object>> homestarting;
	private List<Map<String, Object>> awaystarting;
	private List<Map<String, Object>> homesubstitutes;
	private List<Map<String, Object>> awaysubstitutes;
	private List<Map<String, Object>> homecoaches;
	private List<Map<String, Object>> awaycoaches;
	private List<Map<String, Object>> goals;
	private List<Map<String, Object>> cards;
	private List<Map<String, Object>> substitutes;
	private List<Map<String, Object>> statistics;
	private String status;
	
	@SuppressWarnings("unchecked")
	public MatchDetailVO(InfoMatch match){
		this.uuid = match.getUuid();
		this.category = match.getCategory();
		this.season = match.getSeason();
		this.round = match.getRound();
		this.roundName = match.getRoundName();
		this.time = match.getTime();
		this.progress = match.getProgress();
		this.hometeam = match.getHometeam();
		this.awayteam = match.getAwayteam();
		this.homestarting = JSONUtils.json2obj(match.getHomestarting(), List.class);
		this.awaystarting = JSONUtils.json2obj(match.getAwaystarting(), List.class);
		this.homesubstitutes = JSONUtils.json2obj(match.getHomesubstitutes(), List.class);
		this.awaysubstitutes = JSONUtils.json2obj(match.getAwaysubstitutes(), List.class);
		this.homecoaches = JSONUtils.json2obj(match.getHomecoaches(), List.class);
		this.awaycoaches = JSONUtils.json2obj(match.getAwaycoaches(), List.class);
		this.goals = JSONUtils.json2obj(match.getGoals(), List.class);
		this.cards = JSONUtils.json2obj(match.getCards(), List.class);
		this.substitutes = JSONUtils.json2obj(match.getSubstitutes(), List.class);
		this.statistics = JSONUtils.json2obj(match.getStatistics(), List.class);
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

	public String getHometeamName() {
		return hometeamName;
	}

	public void setHometeamName(String hometeamName) {
		this.hometeamName = hometeamName;
	}

	public String getHometeamIconUrl() {
		return hometeamIconUrl;
	}

	public void setHometeamIconUrl(String hometeamIconUrl) {
		this.hometeamIconUrl = hometeamIconUrl;
	}

	public String getAwayteam() {
		return awayteam;
	}

	public void setAwayteam(String awayteam) {
		this.awayteam = awayteam;
	}

	public String getAwayteamName() {
		return awayteamName;
	}

	public void setAwayteamName(String awayteamName) {
		this.awayteamName = awayteamName;
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

	public List<Map<String, Object>> getHomestarting() {
		return homestarting;
	}

	public void setHomestarting(List<Map<String, Object>> homestarting) {
		this.homestarting = homestarting;
	}

	public List<Map<String, Object>> getAwaystarting() {
		return awaystarting;
	}

	public void setAwaystarting(List<Map<String, Object>> awaystarting) {
		this.awaystarting = awaystarting;
	}

	public List<Map<String, Object>> getHomesubstitutes() {
		return homesubstitutes;
	}

	public void setHomesubstitutes(List<Map<String, Object>> homesubstitutes) {
		this.homesubstitutes = homesubstitutes;
	}

	public List<Map<String, Object>> getAwaysubstitutes() {
		return awaysubstitutes;
	}

	public void setAwaysubstitutes(List<Map<String, Object>> awaysubstitutes) {
		this.awaysubstitutes = awaysubstitutes;
	}

	public List<Map<String, Object>> getHomecoaches() {
		return homecoaches;
	}

	public void setHomecoaches(List<Map<String, Object>> homecoaches) {
		this.homecoaches = homecoaches;
	}

	public List<Map<String, Object>> getAwaycoaches() {
		return awaycoaches;
	}

	public void setAwaycoaches(List<Map<String, Object>> awaycoaches) {
		this.awaycoaches = awaycoaches;
	}

	public List<Map<String, Object>> getGoals() {
		return goals;
	}

	public void setGoals(List<Map<String, Object>> goals) {
		this.goals = goals;
	}

	public List<Map<String, Object>> getCards() {
		return cards;
	}

	public void setCards(List<Map<String, Object>> cards) {
		this.cards = cards;
	}

	public List<Map<String, Object>> getSubstitutes() {
		return substitutes;
	}

	public void setSubstitutes(List<Map<String, Object>> substitutes) {
		this.substitutes = substitutes;
	}

	public List<Map<String, Object>> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<Map<String, Object>> statistics) {
		this.statistics = statistics;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}