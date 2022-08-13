/**
 * 
 */
package com.whaty.platform.training.basic;


import java.util.Date;
import java.util.List;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;


/**
 * @author chenjian
 *
 */
public abstract class TrainingClass implements Items {
	
	private String id;
	
	private String name;
	
	private Date startDate;
	
	private Date endDate;
	
	private boolean isActive;
	
	private String note;
	
	private Date startElectCourse;
	
	private Date endElectCourse;
	
		
	private int courseNum;
	
	public int getCourseNum() {
		return courseNum;
	}
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	
	

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndElectCourse() {
		return endElectCourse;
	}

	public void setEndElectCourse(Date endElectCourse) {
		this.endElectCourse = endElectCourse;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		if(note == null || note.length() == 0)
			return "目前还没有简介";
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartElectCourse() {
		return startElectCourse;
	}

	public void setStartElectCourse(Date startElectCourse) {
		this.startElectCourse = startElectCourse;
	}

		
	/**为该培训班添加课程
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void addTrainingCourse(List courseList) throws PlatformException;
	
	/**为该培训班删除课程
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void removeTrainingCourse(List courseList) throws PlatformException;
	
	/**得到该培训班下的课程
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourse() throws PlatformException;
	
	/**得到该培训班下课程的数目
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingCourseNum() throws PlatformException;
		
	/**添加培训班学生
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void addTrainingStudent(List studentList) throws PlatformException;
	
	/**删除培训班学生
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void removeTrainingStudent(List studentList) throws PlatformException;
	
	/**得到培训班学生
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingStudents() throws PlatformException;
	
	/**得到培训班学生的数目
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingStudentsNum() throws PlatformException;
	
	/**确认培训班的学生
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void checkTrainingStudent(List studentList) throws PlatformException;
	
	/**得到和某个学生的关系
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract StudentClass getStudentClass(String studentId) throws PlatformException;
	
	/**得到训练班主管列表
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassManagers() throws PlatformException;
	
	/**添加训练班主管
	 * @param chiefList
	 * @throws PlatformException
	 */
	public abstract void addClassManagers(List chiefList)  throws PlatformException;
	
	/**移除训练班主管
	 * @param chiefList
	 * @throws PlatformException
	 */
	public abstract void removeClassManagers(List chiefList)  throws PlatformException;
}
