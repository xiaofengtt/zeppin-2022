/**
 * 
 */
package com.whaty.platform.training.basic;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public interface TrainingBasicList {

	/**
	 * �õ��γ����͵�Ŀ¼���б�
	 * 
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchCourseTypeTree(List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * ��ȡ�γ�����
	 * 
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public int searchCourseNum(List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * �����γ�
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchCourse(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * ����ѧ���Ϳγ̵Ĺ�ϵ
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchStudentCourse(Page page, List searchProperty,
			List orderProperty, TrainingStudent student)
			throws PlatformException;

	/**
	 * ��ȡ��ѵ����
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public int getClassNum(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * ������ѵ��
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	public List searchClass(Page page, List searchProperty, List orderProperty)
			throws PlatformException;

	/**
	 * ����ĳ��ѧ������ѵ��Ĺ�ϵ
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public List searchStudentClass(Page page, List searchProperty,
			List orderProperty, TrainingStudent student)
			throws PlatformException;

	/**
	 * ����ĳ����ѵ���µĿγ�
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public List searchClassCourses(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ���µĿγ���Ŀ
	 * 
	 * @param searchProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public int searchClassCoursesNum(List searchProperty, TrainingClass newClass)
			throws PlatformException;

	/**
	 * ��ȡĳ����ѵ���µ�ѧ������
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public int getClassStudentsNum(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException;

	/**
	 * ����ĳ����ѵ���µ�ѧ��
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public List searchClassStudents(Page page, List searchProperty,
			List orderProperty, TrainingClass newClass)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ���µ�ѧ����Ŀ
	 * 
	 * @param searchProperty
	 * @param newClass
	 * @return
	 * @throws PlatformException
	 */
	public int searchClassStudentsNum(List searchProperty,
			TrainingClass newClass) throws PlatformException;

	/**
	 * ����ĳ���γ��µ�ѧ��
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @param course
	 * @return
	 * @throws PlatformException
	 */
	public List searchCourseStudents(Page page, List searchProperty,
			List orderProperty, TrainingCourse course) throws PlatformException;

	/**
	 * �õ�ĳ���γ���ѧ��������
	 * 
	 * @param searchProperty
	 * @param course
	 * @return
	 * @throws PlatformException
	 */
	public int searchCourseStudentsNum(List searchProperty,
			TrainingCourse course) throws PlatformException;

	/**
	 * @param searchProperty
	 * @param orderProperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public int searchStudentCourseNum(List searchProperty, List orderProperty,
			TrainingStudent student) throws PlatformException;

	/**
	 * @param searchProperty
	 * @param orderProperty
	 * @param student
	 * @return
	 * @throws PlatformException
	 */
	public int getStudentClassNum(List searchProperty, List orderProperty,
			TrainingStudent student) throws PlatformException;

	public int searchClassCourseStudentsNum(List searchProperty,
			List orderProperty, TrainingClass trainingClass,
			TrainingCourse course) throws PlatformException;

	public List searchClassCourseStudents(Page page, List searchProperty,
			List orderProperty, TrainingClass trainingClass,
			TrainingCourse course) throws PlatformException;
}
