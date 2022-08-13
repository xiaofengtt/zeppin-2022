package com.whaty.platform.entity.basic;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public abstract class MailInfo {
	private String msgId;

	private String targets;

	private String content;

	private String status;

	private String sender;
	
	private String time;
	
	private String scope;
	
	private String checker;
	
	private String siteId;
	
	private String note;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public abstract int checkMailMessages(String checker, List mailMessages) throws PlatformException;
	
	public abstract int deleteMailMessages(List smsMessages) throws PlatformException;
	
	public abstract int updateMailMessageSendStatus(String id, String email,
			String sendStatus) throws PlatformException;
	
	public abstract int addMailMessageSendStatus(String msgId, String email,
			String sendStatus) throws PlatformException;
	
	public abstract int rejectMailMessages(String checker, String[] msgIds,String[] notes) throws PlatformException ;
	
	public abstract List searchMailMessages(Page page, List searchproperty,
			List orderproperty) throws PlatformException;
	
	public abstract List getMailMessageNumBySite(Page pageover,String site_id,String start_time,String end_time)
	throws PlatformException;
}
