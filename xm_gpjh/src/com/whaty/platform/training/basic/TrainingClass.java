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
			return "Ŀǰ��û�м��";
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

		
	/**Ϊ����ѵ����ӿγ�
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void addTrainingCourse(List courseList) throws PlatformException;
	
	/**Ϊ����ѵ��ɾ���γ�
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void removeTrainingCourse(List courseList) throws PlatformException;
	
	/**�õ�����ѵ���µĿγ�
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourse() throws PlatformException;
	
	/**�õ�����ѵ���¿γ̵���Ŀ
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingCourseNum() throws PlatformException;
		
	/**�����ѵ��ѧ��
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void addTrainingStudent(List studentList) throws PlatformException;
	
	/**ɾ����ѵ��ѧ��
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void removeTrainingStudent(List studentList) throws PlatformException;
	
	/**�õ���ѵ��ѧ��
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingStudents() throws PlatformException;
	
	/**�õ���ѵ��ѧ������Ŀ
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingStudentsNum() throws PlatformException;
	
	/**ȷ����ѵ���ѧ��
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void checkTrainingStudent(List studentList) throws PlatformException;
	
	/**�õ���ĳ��ѧ���Ĺ�ϵ
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract StudentClass getStudentClass(String studentId) throws PlatformException;
	
	/**�õ�ѵ���������б�
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassManagers() throws PlatformException;
	
	/**���ѵ��������
	 * @param chiefList
	 * @throws PlatformException
	 */
	public abstract void addClassManagers(List chiefList)  throws PlatformException;
	
	/**�Ƴ�ѵ��������
	 * @param chiefList
	 * @throws PlatformException
	 */
	public abstract void removeClassManagers(List chiefList)  throws PlatformException;
}
