package cn.zeppin.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class CapitalPlatform implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6761154855694696915L;
	
	@Id
	private String uuid;
	private String name;
	private String type;
	private String remark;
	private String transType;
	private String status;
	private String sort;
	private String creator;
	private Timestamp createtime;
    
    public class CapitalPlatformStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }

    public class CapitalPlatformType{
    	public final static String COMPANY_BANKCARD = "company_bankcard";
    	public final static String COMPANY_ALIPAY = "company_alipay";
    	public final static String PERSONAL_BANKCARD = "personal_bankcard";
    	public final static String PERSONAL_ALIPAY = "personal_alipay";
    	public final static String WECHAT = "wechat";
    	public final static String REAPAL = "reapal";
    }
    
    public class CapitalPlatformTransType{
    	public final static String RECHARGE = "recharge";
    	public final static String WITHDRAW = "withdraw";
    }
    
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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