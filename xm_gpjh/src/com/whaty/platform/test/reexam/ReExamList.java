package com.whaty.platform.test.reexam;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public interface ReExamList {
	/**
	 * 获得批次数量
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return 批次数量
	 */
	public int getBatchNum(List searchproperty);

	/**
	 * 获得批次列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return 批次列表
	 */
	public List getBatches(Page page, List searchproperty, List orderproperty);

	/**
	 * 获得课程数量
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return 课程数量
	 */
	public int getCourseNum(List searchproperty);

	/**
	 * 获得课程列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return 课程列表
	 */
	public List getCourses(Page page, List searchproperty, List orderproperty);

	/**
	 * 获得考试教室数量
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return 考试教室数量
	 */
	public int getRoomNum(List searchproperty);

	/**
	 * 获得考试教室列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return 考试教室列表
	 */
	public List getRooms(Page page, List searchproperty, List orderproperty);

	/**
	 * 获得考生数量
	 * 
	 * @param searchproperty
	 * @param orderproperty
	 * @return 考试教室数量
	 */
	public int getExamUserNum(List searchproperty);

	/**
	 * 获得考试教室列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return 考试教室列表
	 */
	public List getExamUsers(Page page, List searchproperty, List orderproperty);

	/**
	 * 获得可以参加某门考试课程的考生列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return 可以参加某门考试课程的考生列表
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
