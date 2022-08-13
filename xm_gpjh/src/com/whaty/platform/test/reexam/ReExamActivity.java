package com.whaty.platform.test.reexam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public abstract class ReExamActivity {

	public abstract int courseAddUser(String user_id, String course_id)
			throws PlatformException;

	public abstract void courseAddUser(List examCourseList, List examUserList)
			throws PlatformException;

	public abstract void courseRemoveUser(List examCourseList, List examUserList)
			throws PlatformException;

	/**
	 * ���俼��
	 * 
	 * @param idList1
	 *            ����Id��List
	 * @param idList2
	 *            ���Կγ̵�List
	 * @param num
	 * @throws PlatformException
	 */
	public abstract void allotStudents(List idList1, List idList2, String num)
			throws PlatformException;

	/**
	 * ȡ��ĳ�ſγ��µ�ѧ��
	 * 
	 * @param CourseId
	 * @throws PlatformException
	 */
	public abstract List getStudentsByCourseId(List ListcourseId,
			String examBatchId) throws PlatformException;

	public abstract List getTotalTestRooms(Page page, String batchId)
			throws PlatformException;

	public abstract List getSiteTestRooms(String siteId, Page page,
			String batchId, String examsequence_id) throws PlatformException;

	public abstract int getTotalTestRoomsNum(String siteId, String batchId)
			throws PlatformException;

	public abstract int getSiteTestRoomsNum(String siteId, String batchId,
			String examsequence_id) throws PlatformException;

	public abstract String getTotalStudentsByCourseId(String course_id)
			throws PlatformException;

	public abstract String getSiteTotalStudentsByCourseId(String site_id,
			String course_id) throws PlatformException;

	public abstract List getExamRoomNoStudents(String course_id,
			String room_no, String batchId) throws PlatformException;

	public abstract int getTestCoursesNum(HttpServletRequest request)
			throws PlatformException;

	public abstract List getTestCourses(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract int getSiteTestCoursesNum(HttpServletRequest request)
			throws PlatformException;

	public abstract List getSiteTestCourses(Page page,
			HttpServletRequest request) throws PlatformException;

	public abstract int searchOpenCourseNum(List searchproperty, String batch_id)
			throws PlatformException;

	public abstract List searchOpenCourse(Page page, List searchproperty,
			List orderproperty, String batch_id) throws PlatformException;
}
