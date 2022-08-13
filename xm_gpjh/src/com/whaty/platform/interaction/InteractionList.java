package com.whaty.platform.interaction;

import java.util.List;

import com.whaty.platform.util.Page;

public interface InteractionList {
	/**
	 * ��ù������
	 * 
	 * @return �������
	 * 
	 */
	public int getAnnouncesNum(List searchproperty);

	/**
	 * ��ù����б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �����б�
	 */
	public List getAnnounces(Page page, List searchproperty, List orderproperty);

	/**
	 * �����ҵ����
	 * 
	 * @return ��ҵ����
	 * 
	 */
	public int getHomeworksNum(List searchproperty);

	/**
	 * �����ҵ�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��ҵ�б�
	 */
	public List getHomeworks(Page page, List searchproperty, List orderproperty);

	/**
	 * �����ҵ���8���
	 * 
	 * @return ��ҵ���8���
	 * 
	 */
	public int getHomeworkChecksNum(List searchproperty);

	/**
	 * �����ҵ�����б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��ҵ�����б�
	 */
	public List getHomeworkChecks(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ����Ͻ�����ҵ����
	 * 
	 * @return �Ͻ�����ҵ����
	 * 
	 */
	public int getInHomeworksNum(List searchproperty);

	/**
	 * ����Ͻ�����ҵ�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �Ͻ�����ҵ�б�
	 */
	public List getInHomeworks(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ����Ͻ�����ҵ�ĵ��8���
	 * 
	 * @return �Ͻ�����ҵ�ĵ��8���
	 * 
	 */
	public int getInHomeworkChecksNum(List searchproperty);

	/**
	 * ����Ͻ�����ҵ�ĵ����б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �Ͻ�����ҵ�ĵ����б�
	 */
	public List getInHomeworkChecks(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ��ô�������ĸ���
	 * 
	 * @return ��������ĸ���
	 * 
	 */
	public int getQuestionsNum(List searchproperty);

	/**
	 * ��ô���������б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ����������б�
	 */
	public List getQuestions(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ô�������Ļش����
	 * 
	 * @return ��������Ļش����
	 * 
	 */
	public int getAnswersNum(List searchproperty);

	/**
	 * ��ô�������Ļش��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��������Ļش��б�
	 */
	public List getAnswers(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ó���������Ŀ¼����
	 * 
	 * @return ����������Ŀ¼����
	 * 
	 */
	public int getQuestionEliteDirsNum(List searchproperty);

	/**
	 * ��ó���������Ŀ¼�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ����������Ŀ¼�б�
	 */
	public List getQuestionEliteDirs(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ��ó����������������
	 * 
	 * @return �����������������
	 * 
	 */
	public int getEliteQuestionsNum(List searchproperty);

	/**
	 * ��ó��������Ĵ��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ���������Ĵ��б�
	 */
	public List getEliteQuestions(Page page, List searchproperty,
			List orderproperty);

	/**
	 * ��ó���𰸿�ĵ��8���
	 * 
	 * @return ����𰸿�ĵ��8���
	 * 
	 */
	public int getEliteAnswersNum(List searchproperty);

	/**
	 * ��ó���𰸿�ĵ����б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ����𰸿�ĵ����б�
	 */
	public List getEliteAnswers(Page page, List searchproperty,
			List orderproperty);

	public int getHomeworkTimes(String teachclassId);

	public int getForumTimes(String teachclassId);

	public int getQuestionTimes(String teachclassId);

	public int getAnswerTimes(String teachclassId);
}