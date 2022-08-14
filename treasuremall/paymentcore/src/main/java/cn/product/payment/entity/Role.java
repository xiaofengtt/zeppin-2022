package cn.product.payment.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class Role implements Serializable{
	
    /**
	 * 角色
	 */
	private static final long serialVersionUID = 5857531660477638041L;

	@Id
    private String uuid;
    private String name;
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}