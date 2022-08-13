package com.whaty.platform.vote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;
import com.whaty.platform.vote.basic.VotePaper;
import com.whaty.platform.vote.basic.VoteQuestion;

public abstract class VoteNormalManage {

	/**
	 * 投票
	 * 
	 * @param votePaperId
	 * @param request
	 * @throws PlatformException
	 */
	public abstract void votePaper(String votePaperId,
			HttpServletRequest request) throws PlatformException;

	/**
	 * 得到全部活动的调查问卷
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getActiveVotePapers() throws PlatformException;

	public abstract List getActiveVotePapers(String voteType)
			throws PlatformException;

	public abstract List getActiveCourseVotePapers(Page page,
			String openCourseId) throws PlatformException;

	public abstract int getActiveCourseVotePapersNum(String openCourseId)
			throws PlatformException;

	/**
	 * 得到调查问卷
	 * 
	 * @param votePaperId
	 * @throws PlatformException
	 */
	public abstract VotePaper getVotePaper(String votePaperId)
			throws PlatformException;

	/**
	 * 得到调查问卷下的问题和结果
	 * 
	 * @param votePaperId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getVoteQuestions(String votePaperId)
			throws PlatformException;

	/**
	 * 得到调查问卷中某个题目的问题和结果
	 * 
	 * @param voteQuestionId
	 * @return
	 * @throws PlatformException
	 */
	public abstract VoteQuestion getVoteQuestion(String voteQuestionId)
			throws PlatformException;

	public abstract boolean checkSessionAndIp(VotePaper paper,
			HttpServletRequest request) throws PlatformException;
}
