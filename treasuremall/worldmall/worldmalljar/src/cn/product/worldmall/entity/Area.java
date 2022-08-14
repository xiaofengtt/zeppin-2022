package cn.product.worldmall.entity;

import java.io.Serializable;

public class Area implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3890242671747149826L;
	
	private String uuid;
	private String name;
	private Integer level;
	private String pid;
	private String scode;
	
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
	
	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getScode() {
		return scode;
	}
	
	public void setScode(String scode) {
		this.scode = scode;
	}
}