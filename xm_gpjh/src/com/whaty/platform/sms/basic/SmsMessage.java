/**
 * 
 */
package com.whaty.platform.sms.basic;

/**
 * @author wq
 * 
 */
public abstract class SmsMessage {
	private String msgId;

	private String targets;

	private String content;

	private String status = "0";

	private String sender;

	private String time;

	private String scope;

	private String checker;

	private String siteId;

	private String note;

	private String tea_id;

	private String sendObjNum;

	private String splitItem;

	private String type;

	private String setTime;
	
	private String sendStatus ="0";

	public String getTea_id() {
		return tea_id;
	}

	public void setTea_id(String tea_id) {
		this.tea_id = tea_id;
	}

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

	public String getSendObjNum() {
		return sendObjNum;
	}

	public void setSendObjNum(String sendObjNum) {
		this.sendObjNum = sendObjNum;
	}

	public String getSplitItem() {
		return splitItem;
	}

	public void setSplitItem(String splitItem) {
		this.splitItem = splitItem;
	}

	public String getSetTime() {
		return setTime;
	}

	public void setSetTime(String setTime) {
		this.setTime = setTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

}
