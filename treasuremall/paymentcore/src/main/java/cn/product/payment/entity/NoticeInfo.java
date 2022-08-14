package cn.product.payment.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class NoticeInfo implements Serializable {

	/**
	 * 三方通知回调
	 */
	private static final long serialVersionUID = -2913577991245006575L;
	
	@Id
	private String uuid;
	private String channel;
	private String type;
	private String orderNum;
	private String source;
	private String data;
	private String status;
	private Timestamp createtime;
	
	public class NoticeInfoStatus{
		public final static String NORMAL = "normal";
		public final static String SUCCESS = "success";
		public final static String FAIL = "fail";
	}
	
	public class NoticeInfoType{
		public final static String RECHARGE = "recharge";
		public final static String WITHDRAW = "withdraw";
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
