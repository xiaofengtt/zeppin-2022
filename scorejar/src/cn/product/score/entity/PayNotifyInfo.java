/**
 * 
 */
package cn.product.score.entity;

import java.io.Serializable;
import java.sql.Timestamp;



/**
 * @description 【数据对象】三方支付异步通知反馈消息
 */

public class PayNotifyInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7653870957041358522L;

	
	private String uuid;
	
	private Timestamp createtime;
	private String status;
	
	private String source;
	private String orderNum;
	
	private String payType;
	
	public class PayNotifyInfoStatus{
		public final static String UNPRO = "unpro";
		public final static String SUCCESS = "success";
		public final static String FAIL = "fail";
	}
	
	public class PayNotifyInfoPayType{
		
		public final static String RECHARGE_ALIPAY_COM = "recharge_alipay_com";
		public final static String RECHARGE_WECHAT_COM = "recharge_wechat_com";
		public final static String WITHDRAW_REAPAL_COM = "withdraw_reapal_com";
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}
