package com.whaty.platform.entity.recruit;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public interface BasicRecruitList {
	public RecruitBatch getBatch(List searchproperty);

	public List getTestCourses(Page page, List searchProperty,
			List orderProperty);

	public int getTestCoursesNum(List searchProperty);

	public List getUnTestCourses(Page page, List searchProperty,
			List orderProperty);

	public int getUnTestCoursesNum(List searchProperty);

	public List getTestSequences(Page page, List searchProperty,
			List orderProperty);

	public int getTestSequencesNum(List searchProperty);

	public List getActiveBatchs(Page page, List searchProperty,
			List orderProperty);

	public List getBatchs(Page page, List searchProperty, List orderProperty);

	public int getBatchsNum(List searchProperty);

	public List getPlans(Page page, List searchProperty, List orderProperty);

	public int getPlansNum(List searchProperty);

	public List getRecruitPlanSites(Page page, List searchProperty,
			List orderProperty);

	public List getTestBatchs(Page page, List searchProperty, List orderProperty);

	public int getTestBatchsNum(List searchProperty);

	public List getTestSites(Page page, List searchProperty, List orderProperty);

	public int getTestSitesNum(List searchProperty);

	public List getTestRooms(Page page, List searchProperty, List orderProperty);

	public int getTestRoomsNum(List searchProperty);

	public List getStudents(Page page, List searchProperty, List orderProperty);

	public int getStudentsNum(List searchProperty);

	public List getTestStudents(Page page, List searchProperty,
			List orderProperty, String signDate);

	public int getTestStudentsNum(List searchProperty, String signDate);

	public List getTestDesks(Page page, List searchProperty, List orderProperty);

	public int getTestDesksNum(List searchProperty);

	public List getStudentScores(Page page, List searchProperty,
			List orderProperty);

	public List getRecruitScoreStudents(Page page, List searchProperty,
			List orderProperty, String[] sec);

	public int getRecruitScoreStudentsNum(List searchProperty);

	public int getStudentScoresNum(List searchProperty);

	public HashMap getRecruitStat(String batchId) throws SQLException;

	/**
	 * ��ÿ����б�
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return Sort�����б�
	 */
	public List getSorts(Page page, List searchProperty, List orderProperty);

	/**
	 * ��ÿ�����
	 * 
	 * @param searchProperty
	 * @return Sort�������
	 */
	public int getSortsNum(List searchProperty);

	/**
	 * ��ݿ����øÿ��������רҵ�б�
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return Major�����б�
	 */
	public List getMajorsBySort(Page page, List searchProperty,
			List orderProperty);

	/**
	 * ��ݿ����øÿ��������רҵ��
	 * 
	 * @param searchProperty
	 * @return Major������
	 */
	public int getMajorsNumBySort(List searchProperty);

	/**
	 * �����ѧ���Կγ̵��б�
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return RecruitCourse�����б�
	 */
	public List getCourses(Page page, List searchProperty, List orderProperty);

	/**
	 * �����ѧ���Կγ���
	 * 
	 * @param searchProperty
	 * @return RecruitCourse������
	 */
	public int getCoursesNum(List searchProperty);

	public List getPassStatistic(Page page, List searchProperty,
			List orderProperty);

	public int getPassStatisticNum(List searchProperty, List orderProperty);

	public List getPassStuId(List searchProperty);

	public int updateRecruitStudentPassstatus(String stuId, String passstatus);

	public List getTotalStu(Page page, List searchProperty, List orderProperty);

	public int getTotalStuNum(List searchProperty);

	public int getTotalStuPassNum(List searchProperty);

	public int updateStudentStatus(String status, String student_id);

	public int updateStudentStatus(String status, String student_id,
			String grade_id);

	public List getMatricaluteCondition(Page page, List searchProperty,
			List orderProperty);

	public int getMatricaluteConditionNum(List searchProperty);

	public int updateStudentStatusBatch(String batch_id, String major_id,
			String edutype_id, String grade_id);

	public List getMatricaluteCourse(Page page, List searchProperty,
			List orderProperty);

	public String getIdByMajorandBatch(String batch_id, String major_id,
			String edutype_id);

	public int deleteByMajorandBatch(String batch_id, String major_id,
			String edutype_id);

	public int deleteByMajor(String mc_id);

	public int updateMatricaluteCondition(String batch_id, String major_id,
			String edutype_id, String score, String score1,
			String photo_status, String idcard_status,
			String graduatecard_status);

	public int setMatricaluteCondition(String mc_id, String course_id,
			String score);

	public int getTotalTestRoomNums(String batchId, String edutype_id,
			String major_id, String site_id);

	public List getTotalTestRoom(Page page, String batchId, String edutype_id,
			String major_id, String site_id);

	public int getTotalTestRoomNums(List con);

	public List getTotalTestRoom(Page page, List con);

	public List getTestRooms(Page page, List con);

	public List getTestDesks(Page page, List con);

	public List getRegisterStat(Page page, List searchProperty,
			List orderProperty);

	public int getRegisterStatNum(List searchProperty);

	public List getSignStatisticBySite(String batch_id)
			throws PlatformException;

	public List getSignStatisticByMajor(String batch_id)
			throws PlatformException;

	public List getSignStatistic(Page page, String batch_id, String site_id,
			String major_id);

	public int getSignStatisticNum(String batch_id, String site_id,
			String major_id);

	public List getRecruitNoExamConditions(Page page, List searchProperty,
			List orderProperty);

	public int getRecruitNoExamConditionsNum(List searchProperty);

	public int getPassStudentsNum(List searchProperty);

	public int getUnPassStudentsNum(List searchProperty);

	public List getPassStudents(Page page, List searchProperty,
			List orderProperty);

	public List getUnPassStudents(Page page, List searchProperty,
			List orderProperty);

	public int uploadImage(String card_no, String filename, String type)
			throws PlatformException;

	public int deleteImage(String user_id, String note, String type)
			throws PlatformException;

	public List getRejectStudentPhotos(Page page, List searchProperty,
			List orderProperty);

	public int getRejectStudentPhotosNum(List searchProperty);

	public List getReleaseSites(String batchId) throws PlatformException;

}