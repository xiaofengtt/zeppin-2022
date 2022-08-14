package cn.product.treasuremall.entity;

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
	private String creator;
	private Timestamp createtime;
	private String creatortype;
	private String status;
	
	
	public class MobileCodeStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
	}
	
	public class MobileCodeCreatorType{
		public final static String ADMIN = "admin";
		public final static String FRONT = "front";
	}
	
	public class MobileCodeTypes{
		public final static String CODE = "code";//短信验证码
		public final static String REGISTER = "register";//注册验证码
		public final static String RESETPWD = "resetpwd";//重置密码验证码
		public final static String FUNDCODE = "fundcode";//资金（充值、提现、解绑银行卡）
		public final static String OTHERCODE = "othercode";//其他
		public final static String NOTICE = "notice";
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
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
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

	public String getCreatortype() {
		return creatortype;
	}

	public void setCreatortype(String creatortype) {
		this.creatortype = creatortype;
	}

	
}