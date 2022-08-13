/**
 * 
 */
package com.whaty.platform.entity;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.test.TestBatch;
import com.whaty.platform.entity.test.TestCourse;
import com.whaty.platform.entity.test.TestSequence;
import com.whaty.platform.entity.test.TestSite;
import com.whaty.platform.test.TestPriv;
import com.whaty.platform.test.exam.ExamCourse;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class EntityTestManage {

	/**
	 * ����titleģ���ѯ�������
	 * 
	 * @param title
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchTestBatch(String title) throws PlatformException;

	/**
	 * �÷�����4���һ���µĿ������
	 * 
	 * @param title
	 *            ������ε����
	 * @param startDate
	 *            ������ο�ʼʱ��
	 * @param endDate
	 *            ��ʼ��ν���ʱ��
	 * @param note
	 *            ˵��
	 * @throws PlatformException
	 */
	public abstract void addTestBatch(String title, String startDate,
			String endDate, String note) throws PlatformException;

	/**
	 * �޸�һ�������
	 * 
	 * @param id
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void updateTestBatch(String id, String title,
			String startDate, String endDate, String note)
			throws PlatformException;

	/**
	 * ɾ��һ�������
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteTestBatch(List idList) throws PlatformException;

	/**
	 * �õ�һ�������
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TestBatch getTestBatch(String id) throws PlatformException;

	/**
	 * �õ�һ��յĿ������
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TestBatch getTestBatch() throws PlatformException;

	/**
	 * �õ�ȫ���������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTestBatchs() throws PlatformException;

	/**
	 * �÷�����4Ϊĳ��������һ���Գ���
	 * 
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param note
	 * @param testBatch
	 *            �������
	 * @throws PlatformException
	 */
	public abstract void addTestSequence(String title, Date startTime,
			Date endTime, String note, TestBatch testBatch)
			throws PlatformException;

	/**
	 * �޸�һ���Գ���
	 * 
	 * @param id
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param note
	 * @param testBatch
	 * @throws PlatformException
	 */
	public abstract void updateTestSequence(String id, String title,
			Date startTime, Date endTime, String note, TestBatch testBatch)
			throws PlatformException;

	/**
	 * ɾ��һ���Գ���
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteTestSequence(List idList)
			throws PlatformException;

	/**
	 * �õ�һ���Գ���
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TestSequence getTestSequence(String id)
			throws PlatformException;

	/**
	 * �õ�һ��յĿ��Գ���
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TestSequence getTestSequence() throws PlatformException;

	/**
	 * �õ�ĳ���������ȫ�����Գ���
	 * 
	 * @param testBatch
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTestSequences(TestBatch testBatch)
			throws PlatformException;

	/**
	 * �÷�����4��ĳ�������ӿ��Կγ�
	 * 
	 * @param courseId
	 * @param courseName
	 * @param note
	 * @param testSequence
	 *            ���Գ���
	 * @throws PlatformException
	 */
	public abstract void addCourses(String courseId, String courseName,
			String note, TestSequence testSequence) throws PlatformException;

	/**
	 * ɾ��ĳ����µĿ��Կγ�
	 * 
	 * @param courseIds
	 * @param testSequence
	 * @throws PlatformException
	 */
	public abstract void deleteCourses(List idList, TestSequence testSequence)
			throws PlatformException;

	/**
	 * �õ�ĳ���Կγ�
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TestCourse getTestCourse(String courseId)
			throws PlatformException;

	/**
	 * �õ�һ��յĿ��Կγ�
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TestCourse getTestCourse() throws PlatformException;

	/**
	 * �õ�ĳ����µĿ��Կγ�
	 * 
	 * @param testSequence
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTestCourses(TestSequence testSequence)
			throws PlatformException;

	/**
	 * �õ�ĳ������µĿ��Կγ�
	 * 
	 * @param testSequence
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTestCourses(TestBatch testBatch)
			throws PlatformException;

	/**
	 * Ϊĳ��γ���ӿ�����俼��
	 * 
	 * @param courseId
	 * @param studentList
	 * @param testSite
	 * @throws PlatformException
	 */
	public abstract void addStudentOfCourse(TestCourse testCourse,
			List studentList, TestSite testSite) throws PlatformException;

	/**
	 * ɾ��ĳ�����ĳ�ſ��ԵĿ���
	 * 
	 * @param courseId
	 * @param studentList
	 * @param testSite
	 * @throws PlatformException
	 */
	public abstract void deleteStudentOfCourse(TestCourse testCourse,
			List studentList, TestSite testSite) throws PlatformException;

	/**
	 * �õ�ĳ����¿�ĳ�ſ��ԵĿ�������
	 * 
	 * @param testCourse
	 * @param testSite
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getNumberOfStudent(TestCourse testCourse,
			TestSite testSite) throws PlatformException;

	/**
	 * Ϊĳ�����ӿ���
	 * 
	 * @param title
	 * @param address
	 * @param note
	 * @param testSite
	 * @throws PlatformException
	 */
	public abstract void addTestRoom(String title, String address, String note,
			TestSite testSite) throws PlatformException;

	/**
	 * Ϊĳ����޸Ŀ���
	 * 
	 * @param id
	 * @param title
	 * @param address
	 * @param note
	 * @param testSite
	 * @throws PlatformException
	 */
	public abstract void updateTestRoom(String id, String title,
			String address, String note, TestSite testSite)
			throws PlatformException;

	/**
	 * Ϊĳ���ɾ��
	 * 
	 * @param id
	 * @param testSite
	 * @throws PlatformException
	 */
	public abstract void deleteTestRoom(List idList, TestSite testSite)
			throws PlatformException;

	/**
	 * Ϊĳ������µĿ��԰��ſ���
	 * 
	 * @param testBatch
	 * @throws PlatformException
	 */
	public abstract void arrangeDesk(TestBatch testBatch)
			throws PlatformException;

	/**
	 * Ϊĳ�����ĳ����ΰ��ſ���
	 * 
	 * @param testBatch
	 * @throws PlatformException
	 */
	public abstract void arrangeDesk(TestBatch testBatch, TestSite testSite)
			throws PlatformException;

	/**
	 * Ϊĳ����µĿ��԰��ſ���
	 * 
	 * @param testSequence
	 * @throws PlatformException
	 */
	public abstract void arrangeDesk(TestSequence testSequence)
			throws PlatformException;

	/**
	 * Ϊĳ����ĳ��ΰ��ſ���
	 * 
	 * @param testSequence
	 * @param testSite
	 * @throws PlatformException
	 */
	public abstract void arrangeDesk(TestSequence testSequence,
			TestSite testSite) throws PlatformException;

	/**
	 * Ϊĳ��γ̰��ſ���
	 * 
	 * @param testCourse
	 * @throws PlatformException
	 */
	public abstract void arrangeDesk(TestCourse testCourse)
			throws PlatformException;

	/**
	 * Ϊĳ����µ�ĳ��γ̰��ſ���
	 * 
	 * @param testCourse
	 * @param testSite
	 * @throws PlatformException
	 */
	public abstract void arrangeDesk(TestCourse testCourse, TestSite testSite)
			throws PlatformException;

	/**
	 * �õ�������ѧ��
	 * 
	 * @param testSequence
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTestStudent(Page page, String site_id,
			String edutype_id, String major_id, String grade_id, String name,
			String regno) throws PlatformException;

	public abstract int getTestStudentNum(String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno)
			throws PlatformException;

	public abstract int addTestStudent(String login_id)
			throws PlatformException;

	public abstract List getTestStudentByCourse(Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract int getTestStudentByCourseNum(HttpServletRequest request)
			throws PlatformException;

	public abstract List getTestStudentForCourse(Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract List getStudent(Page page, String site_id,
			String edutype_id, String major_id, String grade_id, String name,
			String regno) throws PlatformException;

	public abstract int getStudentNum(String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno)
			throws PlatformException;

	public abstract List getStudent(Page page, String site_id,
			String edutype_id, String major_id, String grade_id, String name,
			String regno, String batch_id) throws PlatformException;

	public abstract int getStudentNum(String site_id, String edutype_id,
			String major_id, String grade_id, String name, String regno,
			String batch_id) throws PlatformException;

	/**
	 * �������б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ����б�
	 */
	public abstract List getBatches(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	public abstract int importStudent(String open_course_id, String course_id,
			String batch_id) throws PlatformException;

	public abstract int importStudentExpend(String open_course_id,
			String course_id, String batch_id) throws PlatformException;

	public abstract int addTestUserBatch(String user_id, String batch_id)
			throws PlatformException;

	public abstract int addTestUserCourse(String user_id, String course_id)
			throws PlatformException;

	public abstract int getTestStudentForCourseNum(HttpServletRequest request)
			throws PlatformException;

	public abstract void allotExamRoom(List userList, List courseList,
			String num) throws PlatformException;

	public abstract void allotSiteExamRoom(String site_id, List userList,
			List courseList, String num) throws PlatformException;

	public abstract TestPriv getTestPriv() throws PlatformException;
	
	public abstract List getStudentsByCourseId(List CourseId, String examBatchId)
			throws PlatformException;

	public abstract List getSiteStudentsByCourseId(String site_id,
			List CourseId, String examBatchId) throws PlatformException;

	public abstract List getTotalTestRooms(Page page, String batch_id)
			throws PlatformException;

	public abstract List getTotalTestRooms(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract List getSiteTotalTestRooms(Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract List getSiteTotalTestRooms(String site_id, Page page,
			String batch_id,String examsequence_id) throws PlatformException;

	public abstract List getSiteTotalTestRooms(String site_id, Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract List getTestRooms(String site_id, Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract int getTotalTestRoomsNum(String batch_id)
			throws PlatformException;

	public abstract int getTotalTestRoomsNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int getSiteTotalTestRoomsNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int getSiteTestRoomsNum(String site_id, String batch_id,String examsequence_id)
			throws PlatformException;

	public abstract int getSiteTestRoomsNum(String site_id,
			HttpServletRequest request) throws PlatformException;

	public abstract int getTestRoomsNum(String site_id,
			HttpServletRequest request) throws PlatformException;

	public abstract List getAllotTestRooms(Page page, String batch_id)
			throws PlatformException;

	public abstract int getAllotTestRoomsNum(String batch_id)
			throws PlatformException;

	public abstract int allotTestRooms(String[] examroom_id)
			throws PlatformException;

	public abstract List getRoomNo(String course_id, String batchId)
			throws PlatformException;

	public abstract List getExamRooms(String course_id, String batchId,
			String siteId) throws PlatformException;

	public abstract List getExamRoomNoStudents(String course_id,
			String room_no, String batchId) throws PlatformException;

	public abstract List getSiteExamRoomNoStudents(String course_id,
			String room_no, String batchId, String site_id)
			throws PlatformException;

	public abstract ExamCourse getExamInfo(String course_id, String batch_id)
			throws PlatformException;

	public abstract void setExamCode(String batch_id) throws PlatformException;

	public abstract List getExamCourses(String user_id)
			throws PlatformException;

	public abstract String getTotalStudentsByCourseId(String course_id)
			throws PlatformException;

	public abstract String getSiteTotalStudentsByCourseId(String site_id,
			String course_id) throws PlatformException;

	public abstract int getTotalExamRoomNumSet(String course_id)
			throws PlatformException;

	public abstract int getSiteTotalExamRoomNumSet(String site_id,
			String course_id) throws PlatformException;

	public abstract int addExamRoomSpotAndNum(String course_id, String address,
			String room_num) throws PlatformException;

	public abstract int addExamRoomSpotAndNum(String course_id, String address,
			String room_num, String teacher) throws PlatformException;

	public abstract int addSiteExamRoomSpotAndNum(String site_id,
			String course_id, String address, String room_num, String teacher)
			throws PlatformException;

	public abstract List getExamRoom(String course_id) throws PlatformException;

	public abstract List getSiteExamRoom(String site_id, String course_id)
			throws PlatformException;

	public abstract int updateExamRoom(String id, String address, String num)
			throws PlatformException;

	public abstract int updateExamRoom(String id, String address, String num,
			String teacher) throws PlatformException;

	public abstract int deleteExamRoom(String id) throws PlatformException;

	public abstract int setStudent(String course_id, String batch_id)
			throws PlatformException;

	public abstract int setSiteStudent(String site_id, String course_id,
			String batch_id) throws PlatformException;

	public abstract List getExamStat(String batch_id, String site_id)
			throws PlatformException;

	public abstract List getExamStat(String batch_id, String site_id,
			String order) throws PlatformException;

	public abstract List getExamStatByCourse(String batch_id, String site_id,
			String order) throws PlatformException;

	public abstract List getExamStat2(String batch_id, String site_id)
			throws PlatformException;

	public abstract List getExamStat3(String batch_id, String site_id,
			String course_id, String sequence_id) throws PlatformException;

	public abstract List getTestCourses(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract int getTestCoursesNum(HttpServletRequest request)
			throws PlatformException;

	public abstract List getSiteTestCourses(Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract int getSiteTestCoursesNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int getOpenCoursesNum(String major_id, String semesterId,
			String course_id, String course_name, String batch_id)
			throws PlatformException;

	public abstract List getOpenCourses(Page page, String major_id,
			String semesterId, String course_id, String course_name,
			String batch_id) throws PlatformException;

	public abstract List getSiteStatus(String examBatchId)
			throws PlatformException;

}
