package cn.product.payment.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class Callback implements Serializable {
	
	/**
	 * 回调
	 */
	private static final long serialVersionUID = -2870631637824942233L;
	
	@Id
	private String uuid;
	private String type;
	private String channel;
	private String orderInfo;
	private String url;
	private String body;
	private Integer times;
	private String status;
	private String processtime;
	private Timestamp lasttime;
	private Timestamp createtime;
	
	public class CallbackStatus{
		public final static String NORMAL = "normal";
		public final static String SUCCESS = "success";
		public final static String FAIL = "fail";
	}
	
	public class CallbackType{
		public final static String USER_RECHARGE = "user_recharge";
		public final static String USER_WITHDRAW = "user_withdraw";
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getProcesstime() {
		return processtime;
	}

	public void setProcesstime(String processtime) {
		this.processtime = processtime;
	}

	public Timestamp getLasttime() {
		return lasttime;
	}

	public void setLasttime(Timestamp lasttime) {
		this.lasttime = lasttime;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
