package cn.product.score.entity;

import java.io.Serializable;

public class AdminRole implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4636983766845407536L;
	
	
	private String uuid;
    private String admin;
    private String role;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}