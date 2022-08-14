package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserEdit implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8657439125387205528L;
	
	@Id
	private String uuid;
	private String frontUser;
	private String type;
	private String infoBefore;
	private String status;
	private String infoAfter;
	private Timestamp createtime;
	private String reason;
	
	private String operator;
	private Timestamp operattime;
	
	public class FrontUserEditStatus {
    	public final static String NORMAL = "normal";
    	public final static String CHECKED = "checked";
    	public final static String NOPASS = "nopass";
    	public final static String DELETE = "delete";
	}
	
	public class FrontUserEditType {
    	public final static String NICKNAME = "nickname";
    	public final static String HEADIMAGE = "headimage";
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfoBefore() {
		return infoBefore;
	}

	public void setInfoBefore(String infoBefore) {
		this.infoBefore = infoBefore;
	}

	public String getInfoAfter() {
		return infoAfter;
	}

	public void setInfoAfter(String infoAfter) {
		this.infoAfter = infoAfter;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}
}