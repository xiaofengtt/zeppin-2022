package cn.product.worldmall.entity;

import java.io.Serializable;


public class RoleMethod implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6138414495947531523L;
	
	
    private String uuid;
    private String role;
    private String method;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
    
}