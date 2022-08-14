package cn.zeppin.product.itic.backadmin.output;

import java.math.BigInteger;

import cn.zeppin.product.itic.core.entity.base.Entity;

public class RoleCountVO implements Entity {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
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
