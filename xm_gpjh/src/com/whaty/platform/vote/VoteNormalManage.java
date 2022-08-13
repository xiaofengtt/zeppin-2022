package com.whaty.platform.vote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;
import com.whaty.platform.vote.basic.VotePaper;
import com.whaty.platform.vote.basic.VoteQuestion;

public abstract class VoteNormalManage {

	/**
	 * ͶƱ
	 * 
	 * @param votePaperId
	 * @param request
	 * @throws PlatformException
	 */
	public abstract void votePaper(String votePaperId,
			HttpServletRequest request) throws PlatformException;

	/**
	 * �õ�ȫ����ĵ����ʾ�
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
	 * �õ������ʾ�
	 * 
	 * @param votePaperId
	 * @throws PlatformException
	 */
	public abstract VotePaper getVotePaper(String votePaperId)
			throws PlatformException;

	/**
	 * �õ������ʾ��µ�����ͽ��
	 * 
	 * @param votePaperId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getVoteQuestions(String votePaperId)
			throws PlatformException;

	/**
	 * �õ������ʾ���ĳ����Ŀ������ͽ��
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
