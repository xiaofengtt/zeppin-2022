package cn.product.treasuremall.entity;

import java.io.Serializable;

public class FrontUserGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2132090483265580957L;
	
	private String name;
	private String discription;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
}