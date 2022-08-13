package com.whaty.platform.leaveword.basic;

import java.text.SimpleDateFormat;

public abstract class ReplyLeaveWord implements com.whaty.platform.Items {

	private String id;

	private String title;

	private String content;

	private String createrId;
	
	private String createrName;

	private String createDate;
	
	private String leaveWordId;
	
	public ReplyLeaveWord()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		this.createDate = format.format(new java.util.Date());
		//System.out.print("init leaveword property createdate:"+this.createDate);
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

	public String getLeaveWordId() {
		return leaveWordId;
	}

	public void setLeaveWordId(String leaveWordId) {
		this.leaveWordId = leaveWordId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

}
