package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontDeviceTokenMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -594226818613141190L;
	
	@Id
    private String uuid;
	private String frontDeviceToken;
	private String title;
	private String content;
	private String type;
	private String status;
    private Timestamp createtime;
    private Boolean flagUserSend;
    
    private String result;
	
    
    public class FrontDeviceTokenMessageStatus{
    	public final static String NORMAL = "normal";
    	public final static String READ = "read";
    	public final static String DELETE = "delete";
    }
    
    public class FrontDeviceTokenMessageType{
    	public final static String SYSTEM_NOTICE = "system_notice";
    	public final static String USER_ORDER = "user_order";
    	public final static String USER_WIN = "user_win";
    }
    
    
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontDeviceToken() {
		return frontDeviceToken;
	}

	public void setFrontDeviceToken(String frontDeviceToken) {
		this.frontDeviceToken = frontDeviceToken;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean getFlagUserSend() {
		return flagUserSend;
	}

	public void setFlagUserSend(Boolean flagUserSend) {
		this.flagUserSend = flagUserSend;
	}

}