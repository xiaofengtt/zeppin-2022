package cn.product.score.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8110148901943548074L;
	
	@Id
	private String uuid;
	private String frontUser;
	private BigDecimal balanceLock;
	private BigDecimal balanceFree;
	private String status;
	private Timestamp createtime;
    
    public class FrontUserAccountStatus{
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

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public BigDecimal getBalanceLock() {
		return balanceLock;
	}

	public void setBalanceLock(BigDecimal balanceLock) {
		this.balanceLock = balanceLock;
	}

	public BigDecimal getBalanceFree() {
		return balanceFree;
	}

	public void setBalanceFree(BigDecimal balanceFree) {
		this.balanceFree = balanceFree;
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
}