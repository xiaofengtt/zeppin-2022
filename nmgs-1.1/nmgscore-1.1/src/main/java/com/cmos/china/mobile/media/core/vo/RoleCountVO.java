package com.cmos.china.mobile.media.core.vo;

import java.io.Serializable;
import java.math.BigInteger;

public class RoleCountVO implements Serializable{

	private static final long serialVersionUID = 5983693420379627641L;
	private String role;
	private BigInteger count;
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public BigInteger getCount() {
		return count;
	}
	
	public void setCount(BigInteger count) {
		this.count = count;
	}

	
	

}
