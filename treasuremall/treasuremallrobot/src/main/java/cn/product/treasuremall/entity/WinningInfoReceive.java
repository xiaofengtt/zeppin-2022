package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class WinningInfoReceive implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5535681301105237794L;
	
	@Id
	private String winningInfo;
	private String orderId;
	private String frontUser;
	private Integer showId;
	private String goodsId;

	private String status;
	
	private String type;
	private String ip;
	
	private String provideInfo;
	
	private Timestamp createtime;
	
	private String operator;
	private Timestamp operattime;
	
	public class WinningInfoReceiveStatus {
		public static final String NORMAL = "normal";
		public static final String RECEIVE = "receive";
		public static final String FINISHED = "finished";
		public static final String CONFIRM = "confirm";
		public static final String RETURN = "return";
	}
	
	public class WinningInfoReceiveType {
		public static final String GOLD = "gold";
		public static final String ENTITY = "entity";
	}
	
	public String getWinningInfo() {
		return winningInfo;
	}
	public void setWinningInfo(String winningInfo) {
		this.winningInfo = winningInfo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getFrontUser() {
		return frontUser;
	}
	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getProvideInfo() {
		return provideInfo;
	}
	public void setProvideInfo(String provideInfo) {
		this.provideInfo = provideInfo;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
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
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getShowId() {
		return showId;
	}
	public void setShowId(Integer showId) {
		this.showId = showId;
	}
}