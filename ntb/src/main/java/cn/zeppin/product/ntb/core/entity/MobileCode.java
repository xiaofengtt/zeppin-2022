package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 *  
 */
@Entity
@Table(name = "mobile_code")
public class MobileCode extends BaseEntity {
	
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
	private String creatorType;
	private String status;
	
	
	public class MobileCodeStatus{
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETED = "deleted";
	}
	
	public class MobileCodeCreatorType{
		public final static String ADMIN = "admin";
		public final static String INVESTOR = "investor";
		public final static String QCB_ADMIN = "qcb_admin";
		public final static String QCB_EMP = "qcb_emp";
		public final static String QCB_COMPANY = "qcb_company";
		public final static String SHBX_USER = "shbx_user";
	}
	
	public class MobileCodeTypes{
		public final static String CODE = "code";//短信验证码
		public final static String REGISTER = "register";//注册验证码
		public final static String RESETPWD = "resetpwd";//重置密码验证码
		public final static String FUNDCODE = "fundcode";//资金（充值、提现、解绑银行卡）
		public final static String OTHERCODE = "othercode";//其他
		public final static String NOTICE = "notice";
		public final static String QCB_BANKCARD_ADD = "qcb_bankcard_add";
		public final static String QCB_BANKCARD_DELETE = "qcb_bankcard_delete";
		public final static String QCB_WITHDRAW = "qcb_withdraw";
		public final static String QCB_PAYROLL = "qcb_payroll";
		public final static String QCB_MOBILE_MODIFY = "qcb_mobile_modify";
		public final static String EMP_BANKCARD_ADD = "emp_bankcard_add";
		public final static String EMP_BANKCARD_DELETE = "emp_bankcard_delete";
		public final static String EMP_WITHDRAW = "emp_withdraw";
		public final static String EMP_REPAYMENT = "emp_repayment";
	}
	
	
	public MobileCode(String mobile, String code,
			String uuid, String type, Timestamp createtime, String status, String content, String creatorType) {
		super();
		this.uuid = uuid;
		this.type = type;
		this.mobile = mobile;
		this.code = code;
		this.content = content;
		this.uuid = uuid;
		this.createtime = createtime;
		this.status = status;
		this.creatorType = creatorType;
	}
	
	public MobileCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "MOBILE",nullable = false)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "CODE", nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "CREATETIME", nullable = false, length = 19)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp creattime) {
		this.createtime = creattime;
	}

	@Column(name = "STATUS", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Column(name = "creator", length = 36)
	public String getCreator() {
		return creator;
	}
	

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Column(name = "type", nullable = false)
	public String getType() {
		return type;
	}
	

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "content", nullable = false, length = 200)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "creator_type", nullable = false)
	public String getCreatorType() {
		return creatorType;
	}

	public void setCreatorType(String creatorType) {
		this.creatorType = creatorType;
	}

	
}