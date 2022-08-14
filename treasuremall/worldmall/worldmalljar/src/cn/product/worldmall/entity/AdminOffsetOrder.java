package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AdminOffsetOrder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4379950320745496267L;
	
    private String uuid;
	private String frontUser;
	private Long orderNum;
	private String orderType;
	private String type;
	private BigDecimal dAmount;
	private String remark;
	
	private String operator;
	private Timestamp operattime;
	private String reason;
	private String status;
    private Timestamp createtime;
    
    public class AdminOffsetOrderStatus{
    	public final static String NORMAL = "normal";
    	public final static String CHECKED = "checked";
    	public final static String CANCEL = "cancel";
    	public final static String CLOSE = "close";
    }
    
    public class AdminOffsetOrderType{
    	public final static String ADMIN_ADD = "admin_add";
    	public final static String ADMIN_SUB = "admin_sub";
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getFrontUser() {
		return frontUser;
	}

	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getdAmount() {
		return dAmount;
	}

	public void setdAmount(BigDecimal dAmount) {
		this.dAmount = dAmount;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}