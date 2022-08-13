package com.whaty.platform.leaveword.user;

public abstract class LeaveWordManagerPriv {
	public String managerId;
	
	public int addLeaveWord=1;
	
	public int deleteLeaveWord=0;
	
	public int replyLeaveWord=0;
	
	public int getLeaveWord=0;
	
	public int getReply=0;
	
	public int deleteReply=0;
	
	public int deleteAllLeaveWord=0;

	public LeaveWordManagerPriv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

}
