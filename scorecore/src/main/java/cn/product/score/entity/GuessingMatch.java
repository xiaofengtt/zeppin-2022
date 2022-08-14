package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class GuessingMatch implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6976654813495693056L;
	
	@Id
	private String uuid;
	private String category;
	private String infoMatch;
	private Timestamp time;
	private String remark;
	private Integer sort;
	private String status;
	private String operator;
	private Timestamp operatetime;
	private String creator;
	private Timestamp createtime;
    
    public class GuessingMatchStatus{
    	public final static String NORMAL = "normal";
    	public final static String PUBLISH = "publish";
    	public final static String WAITING = "waiting";
    	public final static String FINISHED = "finished";
    	public final static String DELETE = "delete";
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInfoMatch() {
		return infoMatch;
	}

	public void setInfoMatch(String infoMatch) {
		this.infoMatch = infoMatch;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperatetime() {
		return operatetime;
	}

	public void setOperatetime(Timestamp operatetime) {
		this.operatetime = operatetime;
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