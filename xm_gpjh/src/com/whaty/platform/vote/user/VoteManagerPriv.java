package com.whaty.platform.vote.user;

public abstract class VoteManagerPriv {
	
	public String messageId ="";
	
	public int addVotePaper=0;
	
	public int getVotePaper = 0;
	
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

}
