package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class FrontUserBuyfreeOrder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1958637488064256362L;
	
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String activityInfoBuyfree;
	private String goodsId;
	private Integer sharenum;
	private Boolean isLucky;
	private Timestamp createtime;
	private String status;
	private String remark;

	private String operator;
	private Timestamp operattime;
	private Timestamp winningTime;
	private String ip;
	
	private String provideInfo;
	private String receiveType;
    
    public class FrontUserBuyfreeOrderStatus{
    	public final static String NORMAL = "normal";
    	public final static String NOWIN = "nowin";
    	public final static String WIN = "win";
		public static final String FINISHED = "finished";
    	public static final String RECEIVE = "receive";
		public static final String CONFIRM = "confirm";
		public static final String RETURN = "return";
    }
    public class FrontUserBuyfreeOrderReceiveType {
    	public static final String NORMAL = "normal";//未领奖
		public static final String GOLD = "gold";
		public static final String ENTITY = "entity";
		public static final String VOUCHER = "voucher";
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

	public String getActivityInfoBuyfree() {
		return activityInfoBuyfree;
	}

	public void setActivityInfoBuyfree(String activityInfoBuyfree) {
		this.activityInfoBuyfree = activityInfoBuyfree;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSharenum() {
		return sharenum;
	}

	public void setSharenum(Integer sharenum) {
		this.sharenum = sharenum;
	}

	public Boolean getIsLucky() {
		return isLucky;
	}

	public void setIsLucky(Boolean isLucky) {
		this.isLucky = isLucky;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public Timestamp getWinningTime() {
		return winningTime;
	}

	public void setWinningTime(Timestamp winningTime) {
		this.winningTime = winningTime;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
}