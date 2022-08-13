package com.whaty.platform.test.reexam;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public interface ReExamList {
	/**
	 * �����������
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ��������
	 */
	public int getBatchNum(List searchproperty);

	/**
	 * ��������б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �����б�
	 */
	public List getBatches(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ÿγ�����
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return �γ�����
	 */
	public int getCourseNum(List searchproperty);

	/**
	 * ��ÿγ��б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return �γ��б�
	 */
	public List getCourses(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ÿ��Խ�������
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ���Խ�������
	 */
	public int getRoomNum(List searchproperty);

	/**
	 * ��ÿ��Խ����б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ���Խ����б�
	 */
	public List getRooms(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ÿ�������
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return ���Խ�������
	 */
	public int getExamUserNum(List searchproperty);

	/**
	 * ��ÿ��Խ����б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ���Խ����б�
	 */
	public List getExamUsers(Page page, List searchproperty, List orderproperty);

	/**
	 * ��ÿ��Բμ�ĳ�ſ��Կγ̵Ŀ����б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ���Բμ�ĳ�ſ��Կγ̵Ŀ����б�
	 */
	public List getActiveExamSequences(Page page, List searchproperty,
			List orderproperty);

	public List getExamStat(String batch_id, String site_id, String order)
			throws PlatformException;

	public List getExamStat(String batch_id, String site_id)
			throws PlatformException;

	public List getExamStat2(String batch_id, String site_id)
			throws PlatformException;

	public List getExamStat3(String batch_id, String site_id, String course_id,
			String sequence_id) throws PlatformException;

	public int getBasicSequencesNum(List searchproperty);

	public List getBasicSequences(Page page, List searchproperty,
			List orderproperty);

	public int getOtherFeeTypesNum(List searchproperty);

	public List getOtherFeeTypes(Page page, List searchproperty,
			List orderproperty);

	public List getExamRooms(String course_id, String user_id)
			throws PlatformException;
}
