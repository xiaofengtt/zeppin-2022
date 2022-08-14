package cn.product.payment.entity;

import java.io.Serializable;

public class Channel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6334785507668363732L;
	
    private String uuid;
	private String name;
	private String code;
    private String data;
    private String type;
    private String status;
    
    public class ChannelUuid{
    	
    	
    }
    
    public class ChannelStatus{
    	public final static String NORMAL = "normal";
    	public final static String DISABLE = "disable";
    	public final static String DELETE = "delete";
    }
    
    public class ChannelType{
    	public final static String WITHDRAW = "withdraw";
    	public final static String RECHARGE = "recharge";
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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
}