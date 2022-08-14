package cn.zeppin.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

import cn.zeppin.product.score.util.Utlity;

public class InfoMatch implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2227441338691359302L;
	
	@Id
	private String uuid;
	private String category;
	private String season;
	private String round;
	private Timestamp time;
	private String progress;
	private String hometeam;
	private String awayteam;
	private String finalresult;
	private String penaltyresult;
	private String homestarting;
	private String awaystarting;
	private String homesubstitutes;
	private String awaysubstitutes;
	private String homecoaches;
	private String awaycoaches;
	private String goals;
	private String cards;
	private String substitutes;
	private String statistics;
	private String interfaceid;
	private String status;
	private Timestamp createtime;
	
	public class InfoMatchStatus{
		public final static String FINISHED = "finished";
		public final static String POSTPONED = "postponed";
		public final static String UNSTART = "unstart";
		public final static String LIVING = "living";
	}
	
	public String getRoundName(){
		if(Utlity.checkStringNull(round)){
			return "";
		}
		
		if(this.category.equals(Category.CategoryMainUuid.UCL)){
			String date = Utlity.timestampFormat(this.time, "MM-dd");
			if(date.compareTo("06-15")> 0 && date.compareTo("08-31") < 0){
				return "资格赛";
			}
		}
		
		switch (round){
			case "1/128-finals":
				return "1/128决赛";
			case "1/64-finals":
				return "1/64决赛";
			case "1/32-finals":
				return "1/32决赛";
			case "1/16-finals":
				return "1/16决赛";
			case "1/8-finals":
				return "1/8决赛";
			case "Quarter-finals":
				return "1/4决赛";
			case "Semi-finals":
				return "半决赛";
			case "Final":
				return "决赛";
			default:
				if(round.indexOf("Round")> -1){
					return round.replace("Round ", "第") + "轮";
				}else{
					return round;
				}
		}
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

	public String getHomestarting() {
		return homestarting;
	}

	public void setHomestarting(String homestarting) {
		this.homestarting = homestarting;
	}

	public String getAwaystarting() {
		return awaystarting;
	}

	public void setAwaystarting(String awaystarting) {
		this.awaystarting = awaystarting;
	}

	public String getHomesubstitutes() {
		return homesubstitutes;
	}

	public void setHomesubstitutes(String homesubstitutes) {
		this.homesubstitutes = homesubstitutes;
	}

	public String getAwaysubstitutes() {
		return awaysubstitutes;
	}

	public void setAwaysubstitutes(String awaysubstitutes) {
		this.awaysubstitutes = awaysubstitutes;
	}

	public String getHomecoaches() {
		return homecoaches;
	}

	public void setHomecoaches(String homecoaches) {
		this.homecoaches = homecoaches;
	}

	public String getAwaycoaches() {
		return awaycoaches;
	}

	public void setAwaycoaches(String awaycoaches) {
		this.awaycoaches = awaycoaches;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public String getCards() {
		return cards;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

	public String getSubstitutes() {
		return substitutes;
	}

	public void setSubstitutes(String substitutes) {
		this.substitutes = substitutes;
	}

	public String getStatistics() {
		return statistics;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	public String getInterfaceid() {
		return interfaceid;
	}

	public void setInterfaceid(String interfaceid) {
		this.interfaceid = interfaceid;
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