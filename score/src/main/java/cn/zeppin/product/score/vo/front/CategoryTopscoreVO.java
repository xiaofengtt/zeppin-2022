package cn.zeppin.product.score.vo.front;

import java.io.Serializable;

import cn.zeppin.product.score.entity.CategoryTopscore;

public class CategoryTopscoreVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5179503847853379694L;
	
	private String uuid;
	private String category;
	private String team;
	private String teamName;
	private String player;
	private String playerName;
	private Integer place;
	private String goals;
	
	public CategoryTopscoreVO(CategoryTopscore ct){
		this.uuid = ct.getUuid();
		this.category = ct.getCategory();
		this.team = ct.getTeam();
		this.player = ct.getPlayer();
		this.place = ct.getPlace();
		this.goals = ct.getGoals();
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

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}
}