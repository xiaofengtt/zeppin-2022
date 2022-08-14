package cn.product.score.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;



public class Orderinfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1520409863832397345L;
	
	
	private String uuid;
	private String type;
	private String orderNum;
	private String payNum;
	private String prepayId;
	private String body;
	private BigDecimal fee;
	private Timestamp time;
	private String status;
	private String message;
	private String errorDes;
	private String errorCode;
	private String frontUser;
	private Timestamp createtime;
    
    public class OrderinfoStatus{
    	public final static String NORMAL = "normal";
    	public final static String SUCCESS = "success";
    	public final static String FAILED = "failed";
    	public final static String DELETE = "delete";
    	public final static String CLOSE = "close";
    }
    
    public class OrderinfoType{
    	public final static String COMPANY_BANKCARD = "company_bankcard";
    	public final static String COMPANY_ALIPAY = "company_alipay";
    	public final static String PERSONAL_BANKCARD = "personal_bankcard";
    	public final static String PERSONAL_ALIPAY = "personal_alipay";
    	public final static String WECHAT = "wechat";
    	public final static String REAPAL = "reapal";
    	public final static String ARTIFICIAL = "artificial";
    	public final static String USER_BET = "user_bet";
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getErrorDes() {
		return errorDes;
	}

	public void setErrorDes(String errorDes) {
		this.errorDes = errorDes;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}