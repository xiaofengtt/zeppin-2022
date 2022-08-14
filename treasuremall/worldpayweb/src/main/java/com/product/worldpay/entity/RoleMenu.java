package com.product.worldpay.entity;

import java.io.Serializable;
	
public class RoleMenu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2810683516560606504L;
	
    private String uuid;
    private String role;
    private String menu;
    
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

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
    
}