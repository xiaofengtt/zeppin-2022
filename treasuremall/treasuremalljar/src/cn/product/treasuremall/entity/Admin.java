package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;



public class Admin implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8027470395997675385L;
	
	
    private String uuid;
    private String username;
    private String password;
    private String realname;
    private String role;
    private String status;
    private Timestamp createtime;
    
    public class AdminUuid{
    	public final static String SPIDER = "ffffffff-ffff-ffff-ffff-ffffffffffff";
    }
    
    public class AdminStatus{
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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


	public String getRole() {
		return role;
	}
	

	public void setRole(String role) {
		this.role = role;
	}
}