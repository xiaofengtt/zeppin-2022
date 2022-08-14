package cn.product.worldmall.vo.front;

import java.io.Serializable;

import cn.product.worldmall.entity.InternationalInfo;

public class InternationalInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3051721355775425186L;
	
	private String uuid;
	private String parent;
	private String name;
	private String code;
	private String telCode;
	
	public InternationalInfoVO(){
		
	}
	
	public InternationalInfoVO(InternationalInfo internationalInfo){
		this.uuid = internationalInfo.getUuid();
		this.name = internationalInfo.getNameEn();
		this.parent = internationalInfo.getParent();
		this.code = internationalInfo.getCode();
		this.telCode = internationalInfo.getTelCode();
	}

	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTelCode() {
		return telCode;
	}

	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}
}
