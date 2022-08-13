package com.whaty.platform.test.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public abstract class ExamActivity {

	/*
	 * private String id;
	 * 
	 * private String user_id;
	 * 
	 * private String course_id;
	 * 
	 * private String room_id;
	 * 
	 * private String desk_no;
	 * 
	 * private String score;
	 * 
	 * private String status;
	 * 
	 * public String getCourse_id() { return course_id; }
	 * 
	 * public void setCourse_id(String course_id) { this.course_id = course_id; }
	 * 
	 * public String getDesk_no() { return desk_no; }
	 * 
	 * public void setDesk_id(String desk_no) { this.desk_no = desk_no; }
	 * 
	 * public String getId() { return id; }
	 * 
	 * public void setId(String id) { this.id = id; }
	 * 
	 * public String getRoom_id() { return room_id; }
	 * 
	 * public void setRoom_id(String room_id) { this.room_id = room_id; }
	 * 
	 * public String getScore() { return score; }
	 * 
	 * public void setScore(String score) { this.score = score; }
	 * 
	 * public String getStatus() { return status; }
	 * 
	 * public void setStatus(String status) { this.status = status; }
	 * 
	 * public String getUser_id() { return user_id; }
	 * 
	 * public void setUser_id(String user_id) { this.user_id = user_id; }
	 */
	public abstract int courseAddUser(String user_id, String course_id)
			throws PlatformException;

	public abstract void courseAddUser(List examCourseList, List examUserList)
			throws PlatformException;

	public abstract void courseRemoveUser(List examCourseList, List examUserList)
			throws PlatformException;

	/**
	 * 分配考场
	 * 
	 * @param idList1
	 *            考生Id的List
	 * @param idList2
	 *            考试课程的List
	 * @param num
	 * @throws PlatformException
	 */
	public abstract void allotStudents(List idList1, List idList2, String num)
			throws PlatformException;

	/**
	 * 取得某门课程下的学生
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
