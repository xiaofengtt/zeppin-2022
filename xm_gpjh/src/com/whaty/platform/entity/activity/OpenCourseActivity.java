/**
 * 
 */
package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.Semester;

/**
 * @author chenjian
 *
 */
public abstract class OpenCourseActivity {
	
	private Semester semester;
	
	private List courseList;

	
	public List getCourseList() {
		return courseList;
	}

	public void setCourseList(List courseList) {
		this.courseList = courseList;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	/**�÷���Ϊ����semester�����ѧ�ڿ���courseList�б��еĿγ�
	 * @throws PlatformException
	 */
	public abstract void openCourses() throws PlatformException;
	
	/**�÷���Ϊ����semester�����ѧ��ȡ����courseList�б��еĿγ�
	 * @throws PlatformException
	 */
	public abstract void unOpenCourses() throws PlatformException;
	
	/**����ִ�мƻ��Զ�����
	 * @param implementPlan
	 * @throws PlatformException
	 */
	public abstract void openCoursesByImplementPlan() throws PlatformException;
	
	
	/**���ѧ��semester_id��course_id���open_course_id
	 * @param implementPlan
	 * @throws PlatformException
	 */
	public abstract String getOpenCourseId(String semester_id,String course_id) throws PlatformException;

}
