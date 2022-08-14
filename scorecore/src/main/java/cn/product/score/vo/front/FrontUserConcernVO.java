package cn.product.score.vo.front;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.score.entity.FrontUserConcern;

public class FrontUserConcernVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9218670636628625256L;
	
	private String uuid;
    private String user;
    private String team;
    private String teamName;
    private String teamIconUrl;
    private String category;
    private String categoryName;
    private Timestamp createtime;
    
    public FrontUserConcernVO(FrontUserConcern fuc){
    	this.uuid = fuc.getUuid();
    	this.user = fuc.getUser();
    	this.team = fuc.getTeam();
    	this.category = fuc.getCategory();
    	this.createtime = fuc.getCreatetime();
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}