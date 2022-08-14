package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserHistoryCheck implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8817954466209367957L;
	
	@Id
	private String uuid;
	private String frontUserHistory;
	private String type;
	private String transType;
	private String value;
	private String old;
	private String reason;
	private String proof;
	private String status;
	private Timestamp submittime;
	private String checker;
	private Timestamp checktime;
	private String creator;
	private Timestamp createtime;
    
    public class FrontUserHistoryCheckStatus{
    	public final static String NORMAL = "normal";
    	public final static String CHECKED = "checked";
    	public final static String NOPASS = "nopass";
    	public final static String DELETE = "delete";
    }
    
    public class FrontUserHistoryCheckType{
    	public final static String ADD = "add";
    	public final static String ROLLBACK = "rollback";
    	public final static String CONFIRM = "confirm";
    	public final static String APPLY = "apply";
    	public final static String DELETE = "delete";
    }
    
    public class FrontUserHistoryCheckTransType{
    	public final static String RECHARGE = "recharge";
    	public final static String WITHDRAW = "withdraw";
    }
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontUserHistory() {
		return frontUserHistory;
	}

	public void setFrontUserHistory(String frontUserHistory) {
		this.frontUserHistory = frontUserHistory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOld() {
		return old;
	}

	public void setOld(String old) {
		this.old = old;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Timestamp submittime) {
		this.submittime = submittime;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}