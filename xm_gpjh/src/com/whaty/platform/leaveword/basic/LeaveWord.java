package com.whaty.platform.leaveword.basic;


public abstract class LeaveWord implements com.whaty.platform.Items {

	private String id;

	private String title;

	private String content;

	private String createrId;

	private String createDate;

	private String ip;

	private int limitTimes;

	private int replyTimes;

	private String type;

	private String replyDate;
	
	private int status;

	/*
	 * public LeaveWord() { SimpleDateFormat format = new
	 * SimpleDateFormat("yyyy-MM-dd hh:mm"); this.createDate = format.format(new
	 * java.util.Date()); }
	 */
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLimitTimes() {
		return limitTimes;
	}

	public void setLimitTimes(int limitTimes) {
		this.limitTimes = limitTimes;
	}

	public int getReplyTimes() {
		return replyTimes;
	}

	public void setReplyTimes(int replyTimes) {
		this.replyTimes = replyTimes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}

	public abstract int changeLeaveWordLimitTimes();

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
