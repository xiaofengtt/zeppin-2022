package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;



public class CategoryStanding implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6943054855975933854L;
	
	
	private String uuid;
	private String category;
	private String team;
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
	private String status;
	private Timestamp createtime;
	
	public class CategoryStandingStatus{
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