package com.product.worldpay.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *  
 */
public class MobileCode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3396716929215125093L;
	
	private String uuid;
	private String mobile;
	private String code;
	private String content;
	private String type;
	private Timestamp createtime;
	private String status;
	
	public class MobileCodeStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
	}
	
	public class MobileCodeTypes{
		public final static String COMPANY_ADMIN = "company_admin";
		public final static String COMPANY_PAY = "company_pay";
		public final static String USER = "user";
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp creattime) {
		this.createtime = creattime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}