/**
 * 
 */
package cn.product.treasuremall.vo.back;

import java.io.Serializable;

public class ProvideInfoVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1571160004677857775L;
	
	private String name;
	private String mobile;
	private String code;
	private String address;
	private String company;
	private String expressNumber;
	
	
	public ProvideInfoVO() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	
}
