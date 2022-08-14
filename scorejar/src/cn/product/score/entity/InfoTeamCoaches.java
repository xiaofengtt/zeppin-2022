package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;



public class InfoTeamCoaches implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3064744001500911691L;
	
	
    private String uuid;
    private String team;
    private String infocoaches;
    
    private String creator;
    private String status;
    private Timestamp createtime;
    
    public class InfoTeamCoachesStatus{
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


	public String getInfocoaches() {
		return infocoaches;
	}

	public void setInfocoaches(String infocoaches) {
		this.infocoaches = infocoaches;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}