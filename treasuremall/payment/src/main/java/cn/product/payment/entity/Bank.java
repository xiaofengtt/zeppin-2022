package cn.product.payment.entity;

import java.io.Serializable;

import javax.persistence.Id;

public class Bank implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5643438499563659233L;
	
	@Id
	private String uuid;
	private String name;
	private String shortName;
	private String logo;
	private Integer sort;
	private String status;
	
	public class BankStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public Bank() {
		super();
	}

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
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
