package cn.product.treasuremall.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Id;

public class FrontUserCheckinHistory implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 786895349823069649L;
	
	@Id
	private String uuid;
	private String frontUser;
	private Integer frontUserShowId;
	private String activityInfoCheckinPrize;
	
	private String prizeTitle;
	private String prizeType;
	private String prizeCover;
	private String prize;
	private Integer dayNum;
	
	private Timestamp createtime;
	private String status;
	private String remark;

	private String operator;
	private Timestamp operattime;
	
	private String ip;
	
	private String provideInfo;
	private String receiveType;
	private String frontUserHistory;
	
	public class FrontUserCheckinHistoryStatus {
		public static final String NORMAL = "normal";
    	public final static String FINISHED = "finished";
    	public static final String RECEIVE = "receive";
		public static final String CONFIRM = "confirm";
		public static final String RETURN = "return";
	}

    public class FrontUserCheckinHistoryReceiveType {
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

	public String getActivityInfoCheckinPrize() {
		return activityInfoCheckinPrize;
	}

	public void setActivityInfoCheckinPrize(String activityInfoCheckinPrize) {
		this.activityInfoCheckinPrize = activityInfoCheckinPrize;
	}

	public String getPrizeTitle() {
		return prizeTitle;
	}

	public void setPrizeTitle(String prizeTitle) {
		this.prizeTitle = prizeTitle;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrizeCover() {
		return prizeCover;
	}

	public void setPrizeCover(String prizeCover) {
		this.prizeCover = prizeCover;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
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

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getFrontUserHistory() {
		return frontUserHistory;
	}

	public void setFrontUserHistory(String frontUserHistory) {
		this.frontUserHistory = frontUserHistory;
	}
}