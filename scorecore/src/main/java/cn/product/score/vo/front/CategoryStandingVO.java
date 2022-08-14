package cn.product.score.vo.front;

import java.io.Serializable;

import cn.product.score.entity.CategoryStanding;

public class CategoryStandingVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -465044262358057393L;
	
	private String uuid;
	private String category;
	private String team;
	private String teamName;
	private String teamIconUrl;
	private String season;
	private String round;
	private Integer place;
	private String played;
	private String won;
	private String drawn;
	private String lost;
	private String scored;
	private String against;
	private String difference;
	private String point;
	
	public CategoryStandingVO(CategoryStanding cs){
		this.uuid = cs.getUuid();
		this.category = cs.getCategory();
		this.team = cs.getTeam();
		this.season = cs.getSeason();
		this.round = cs.getRound();
		this.place = cs.getPlace();
		this.played = cs.getPlayed();
		this.won = cs.getWon();
		this.drawn = cs.getDrawn();
		this.lost = cs.getLost();
		this.scored = cs.getScored();
		this.against = cs.getAgainst();
		this.difference = cs.getDifference();
		this.point = cs.getPoint();
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

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamIconUrl() {
		return teamIconUrl;
	}

	public void setTeamIconUrl(String teamIconUrl) {
		this.teamIconUrl = teamIconUrl;
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

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public String getPlayed() {
		return played;
	}

	public void setPlayed(String played) {
		this.played = played;
	}

	public String getWon() {
		return won;
	}

	public void setWon(String won) {
		this.won = won;
	}

	public String getDrawn() {
		return drawn;
	}

	public void setDrawn(String drawn) {
		this.drawn = drawn;
	}

	public String getLost() {
		return lost;
	}

	public void setLost(String lost) {
		this.lost = lost;
	}

	public String getScored() {
		return scored;
	}

	public void setScored(String scored) {
		this.scored = scored;
	}

	public String getAgainst() {
		return against;
	}

	public void setAgainst(String against) {
		this.against = against;
	}

	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
}