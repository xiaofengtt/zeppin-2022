package cn.product.payment.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class RoleMethod implements Serializable{
	
    /**
	 * 角色方法权限
	 */
	private static final long serialVersionUID = 6138414495947531523L;
	
	@Id
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