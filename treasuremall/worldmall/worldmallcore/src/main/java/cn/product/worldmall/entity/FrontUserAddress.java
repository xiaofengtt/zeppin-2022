package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserAddress implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4465092093128949965L;
	
	@Id
	private String uuid;
	private String frontUser;
	private String receiver;
	private String area;
	
	private String address;
	private String asub;
	private String city;
	private String state;
	private String internationalInfo;
	
	private String phone;
	private String zipcode;
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

	public String getAsub() {
		return asub;
	}

	public void setAsub(String asub) {
		this.asub = asub;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInternationalInfo() {
		return internationalInfo;
	}

	public void setInternationalInfo(String internationalInfo) {
		this.internationalInfo = internationalInfo;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}