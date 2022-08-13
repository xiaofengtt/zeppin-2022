package com.whaty.platform.vote.basic;

import java.util.List;

import com.whaty.platform.vote.util.exception.VoteException;

public abstract class VoteActivity {

	public abstract void activeVotePapers(List paperIds,boolean active) throws VoteException;
	
	public abstract void activeVoteSuggests(List suggestIds,boolean active) throws VoteException;
	
	public abstract boolean checkSessionAndIp(String paperId,String sessionId,String Ip) throws VoteException;
	
}
