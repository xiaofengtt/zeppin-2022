package com.whaty.platform.util;

/**
 * @author 陈健
 * 该类描述地址信息
 *
 */
public class Address {
	
	private String  address=null; //地址
	private String postalcode=null; //邮编
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

}
