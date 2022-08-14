package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;



public class InfoCategoryTeam implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3064744001500911691L;
	
	
    private String uuid;
    private String team;
    private String category;
    
    private String creator;
    private String status;
    private Timestamp createtime;
    
    private String teaminterid;
    private String categoryinterid;
    
    public class InfoCategoryTeamStatus{
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTeaminterid() {
		return teaminterid;
	}

	public void setTeaminterid(String teaminterid) {
		this.teaminterid = teaminterid;
	}

	public String getCategoryinterid() {
		return categoryinterid;
	}

	public void setCategoryinterid(String categoryinterid) {
		this.categoryinterid = categoryinterid;
	}

}