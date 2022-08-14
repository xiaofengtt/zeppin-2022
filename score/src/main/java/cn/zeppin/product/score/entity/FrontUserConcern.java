package cn.zeppin.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "front_user_concern")
public class FrontUserConcern implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5458206968241106070L;
	
	@Id
	private String uuid;
    private String user;
    private String team;
    private String category;
    private Timestamp createtime;
    
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}