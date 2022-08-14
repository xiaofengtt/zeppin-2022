package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class FrontUserMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 701939305605919625L;
	
    private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String title;
	private String content;
	private String sourceId;
	private String sourceType;
	private String sourceUrl;
	private String sourceImage;
	private String type;
	private String status;
    private Timestamp createtime;
	
    
    public class FrontUserMessageStatus{
    	public final static String NORMAL = "normal";
    	public final static String READ = "read";
    	public final static String DELETE = "delete";
    }
    
    public class FrontUserMessageType{
    	public final static String SYSTEM_NOTICE = "system_notice";
    	public final static String USER_ORDER = "user_order";
    	public final static String USER_WIN = "user_win";
    }
    
    public class FrontUserMessageSourceType{
    	public final static String ORDER_TYPE_SYSTEM_ADD = "system_add";
    	public final static String ORDER_TYPE_SYSTEM_SUB = "system_sub";
    	public final static String ORDER_TYPE_SYSTEM_GIVE = "system_give";
    	public final static String ORDER_TYPE_USER_RECHARGE = "user_recharge";
    	public final static String ORDER_TYPE_USER_WITHDRAW = "user_withdraw";
    	public final static String ORDER_TYPE_USER_PAYMENT = "user_payment";
    	public final static String ORDER_TYPE_USER_EXCHANGE = "user_exchange";
    	public final static String ORDER_TYPE_USER_WIN = "user_win";
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

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
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

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(String sourceImage) {
		this.sourceImage = sourceImage;
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
}