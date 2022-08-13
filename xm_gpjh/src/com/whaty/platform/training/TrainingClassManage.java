/**
 * 
 */
package com.whaty.platform.training;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.user.TrainingClassManagerPriv;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class TrainingClassManage {
	private TrainingClassManagerPriv trainingClassManagerPriv;

	public TrainingClassManagerPriv getTrainingClassManagerPriv() {
		return this.trainingClassManagerPriv;
	}

	public void setTrainingClassManagerPriv(TrainingClassManagerPriv priv) {
		this.trainingClassManagerPriv = priv;
	}

	/**
	 * Ϊĳ����ѵ����ӿγ�
	 * 
	 * @param courseIdList
	 * @throws PlatformException
	 */
	public abstract void addTrainingClassCourse(String classId,
			List courseIdList) throws PlatformException;

	/**
	 * Ϊĳ����ѵ��ɾ��γ�
	 * 
	 * @param courseIdList
	 * @throws PlatformException
	 */
	public abstract void removeTrainingClassCourse(String classId,
			List courseIdList) throws PlatformException;

	/**
	 * Ϊĳ����ѵ�����ѧ��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void addTrainingClassStudent(String classId,
			List studentIdList) throws PlatformException;

	/**
	 * @param classId
	 * @param studentIdList
	 * @throws PlatformException
	 */
	public abstract void checkTrainingClassStudent(String classId,
			List studentIdList) throws PlatformException;

	/**
	 * Ϊĳ����ѵ��ɾ��ѧ��
	 * 
	 * @param courseIdList
	 * @throws PlatformException
	 */
	public abstract void removeTrainingClassStudent(String classId,
			List studentIdList) throws PlatformException;

	/**
	 * �����������ѵ���б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClass() throws PlatformException;

	/**
	 * ��ȡĳ����ѵ���µĿγ��б�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClassCourse(String classId)
			throws PlatformException;

	/**
	 * @param classId
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClassCourse(String classId, String field,
			String order) throws PlatformException;

	/**
	 * ��ȡĳ����ѵ���µķ������Ŀγ��б�
	 * 
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClassCourse(String courseId,
			String courseName, String courseTypeId, String classId)
			throws PlatformException;

	/**
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param classId
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClassCourse(String courseId,
			String courseName, String courseTypeId, String classId,
			String field, String order) throws PlatformException;

	/**
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingCourseNum(String courseId,
			String courseName, String courseTypeId) throws PlatformException;

	/**
	 * @param page
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourse(Page page, String courseId,
			String courseName, String courseTypeId) throws PlatformException;

	public abstract TrainingCourse getTrainingCourse(String courseId)
			throws PlatformException;

	/**
	 * @param page
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourse(Page page, String courseId,
			String courseName, String courseTypeId, String field, String order)
			throws PlatformException;

	/**
	 * ��ȡĳ����ѵ���µ�ѧ������
	 * 
	 * @param studentId
	 * @param studentName
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingClassStudentNum(String studentId,
			String studentName, String classId) throws PlatformException;

	/**
	 * ��ȡĳ����ѵ���µ�ѧ���б�
	 * 
	 * @param page
	 * @param studentId
	 * @param studentName
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClassStudent(Page page, String studentId,
			String studentName, String classId) throws PlatformException;

	public abstract List getTrainingClassStudent(Page page, String studentId,
			String studentName, String field, String order, String classId)
			throws PlatformException;

	/**
	 * @param page
	 * @param studentId
	 * @param studentName
	 * @param nichName
	 * @param active
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingStudent(Page page, String studentId,
			String studentName, String nickName, String active)
			throws PlatformException;

	/**
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingStudent getTrainingStudent(String studentId)
			throws PlatformException;

	/**
	 * @param page
	 * @param studentId
	 * @param studentName
	 * @param nickName
	 * @param active
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingStudent(Page page, String studentId,
			String studentName, String nickName, String active, String field,
			String order) throws PlatformException;

	/**
	 * @param studentId
	 * @param studentName
	 * @param nickName
	 * @param active
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingStudentNum(String studentId,
			String studentName, String nickName, String active)
			throws PlatformException;

	/**
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedStudents(String classId)
			throws PlatformException;

	/**
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getUnCheckedClassStudentsNum(String classId)
			throws PlatformException;

	/**
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedClassStudents(Page page, String classId)
			throws PlatformException;

	/**
	 * @param page
	 * @param field
	 * @param order
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedClassStudents(Page page, String field,
			String order, String classId) throws PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param note
	 * @param status
	 * @param classId
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getClassCourseStudentsNum(String id, String name,
			String note, String status, String classId, String courseId)
			throws PlatformException;

	/**
	 * @param page
	 * @param id
	 * @param name
	 * @param note
	 * @param field
	 * @param order
	 * @param status
	 * @param classId
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourseStudents(Page page, String id,
			String name, String note, String field, String order,
			String status, String classId, String courseId)
			throws PlatformException;

	public abstract void unApplyCourseStudent(String courseId, List studentList)
			throws PlatformException;

	/**
	 * @param studentIds
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void checkCourseStudent(List studentIds, String courseId)
			throws PlatformException;

	/**
	 * @param studentIds
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void unCheckCourseStudent(List studentIds, String courseId)
			throws PlatformException;
	
	/**
	 * ȡ��ȷ�ϼ�����ѵ��
	 * 
	 * @param classId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unCheckClassStudent(String classId, List studentList)
			throws PlatformException;

}
