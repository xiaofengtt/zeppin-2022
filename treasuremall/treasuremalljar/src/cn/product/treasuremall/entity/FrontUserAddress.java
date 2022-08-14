package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;



public class FrontUserAddress implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4465092093128949965L;
	
	private String uuid;
	private String frontUser;
	private String receiver;
	private String area;
	private String address;
	private String phone;
	private Timestamp createtime;
	private Boolean isDefault;
	
	private String status;
	
	public class FrontUserAddressStatus {
		public static final String NORMAL = "normal";
		public static final String DELETE = "delete";
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

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}