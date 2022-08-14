/**
 * 
 */
package cn.product.treasuremall.vo.back;

import java.io.Serializable;

import cn.product.treasuremall.util.JSONUtils;

public class ProvideInfoVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1571160004677857775L;
	
	private String name;
	private String mobile;
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
	
	public static void main(String[] args) {
		ProvideInfoVO p = new ProvideInfoVO();
		p.setAddress("北京市北京市昌平区");
		p.setMobile("17766554320");
		p.setName("朱某某");
		System.out.println(JSONUtils.obj2json(p));
		System.out.println("{\"address\":\"北京市北京市昌平区\",\"mobile\":\"17766554320\",\"name\":\"朱某某\"}");
	}
}
