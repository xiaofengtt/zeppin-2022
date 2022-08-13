package com.whaty.platform.vote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;
import com.whaty.platform.vote.basic.VotePaper;
import com.whaty.platform.vote.basic.VoteQuestion;
import com.whaty.platform.vote.basic.VoteSuggest;
import com.whaty.platform.vote.user.VoteManagerPriv;

public abstract class VoteManage {
	
	private VoteManagerPriv priv;
	
	/**
	 * @return the priv
	 */
	public VoteManagerPriv getPriv() {
		return priv;
	}

	/**
	 * @param priv the priv to set
	 */
	public void setPriv(VoteManagerPriv priv) {
		this.priv = priv;
	}

	public abstract void addVotePaper(HttpServletRequest request) throws PlatformException;

	public abstract void updateVotePaper(String id,HttpServletRequest request) throws PlatformException;

	public abstract void deleteVotePaper(String id) throws PlatformException;
	
	public abstract void activeVotePapers(HttpServletRequest request,String active) throws PlatformException;
	
	public abstract VotePaper getVotePaper(String id) throws PlatformException;
	
	public abstract List getVotePapers(Page page,HttpServletRequest request) throws PlatformException;
	
	public abstract int getVotePaperNum(HttpServletRequest request) throws PlatformException;
	
	public abstract VoteQuestion getVoteQuestion(String id) throws PlatformException;
	
	public abstract List getVoteQuestions(String votePaperId,HttpServletRequest request) throws PlatformException;

	public abstract void addVoteQuestion(String votePaperId,HttpServletRequest request) throws PlatformException;
	
	public abstract void updateVoteQuestion(String question_id,HttpServletRequest request) throws PlatformException;

	public abstract void deleteVoteQuestions(HttpServletRequest request) throws PlatformException;
	
	public abstract void deleteVoteQuestion(String votePaperId,String questionId) throws PlatformException;
	
	public abstract VoteSuggest getVoteSuggest(String id)throws PlatformException;
	
	public abstract List getVoteSuggests(Page page,String votePaperId,HttpServletRequest request) throws PlatformException;

	public abstract int getVoteSuggestNum(String votePaperId,HttpServletRequest request) throws PlatformException;

	public abstract void deleteVoteSuggests(HttpServletRequest request) throws PlatformException;

	public abstract void clearVotePaperResults(String votePaperId) throws PlatformException;
	
	public abstract void clearVoteQuestionResult(String voteQuestionId) throws PlatformException;

	public abstract void activeVoteSuggests(HttpServletRequest request,String active) throws PlatformException;
	
	public abstract void deleteVoteSuggest(String id) throws PlatformException;
	
//	public abstract WhatyEditorConfig getWhatyEditorConfig (HttpSession session)
//	throws PlatformException;
}
