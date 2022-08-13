package com.whaty.platform.entity.graduatedesign;

import java.util.List;
import java.util.Map;

import com.whaty.platform.util.Page;

/**
 * @author Administrator
 *
 */
public interface BasicGraduateList {
	/**
	 *��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ����
	 */
	public abstract List getSubjectSearchStatusChangedStudent(String[] ids);

	/**
	 *��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ����
	 */
	public abstract List getRegBeginCourseStatusChangedStudent(String[] ids);

	/**
	 *��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ����
	 */
	public abstract List getMetaphaseCheckStatusChangedStudent(String[] ids);

	/**
	 *��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ����
	 */
	public abstract List getRejoinRequesStatusChangedStudent(String[] ids);

	/**
	 *��ѯ���걨��Ŀ״̬�ı����վָ����ʦ���ͨ����
	 */
	public abstract List getDiscourseStatusChangedStudent(String[] ids);

	/**
	 *��ѯ������ҵ����״̬�ı����վָ����ʦ���ͨ����
	 */
	public abstract List getExerciseScoreSubmitStatusChangedStudent(String[] ids);

	/**
	 *��ѯ����ҵ��Ʒ���״̬�ı����վָ����ʦ���ͨ����
	 */
	public abstract List getDiscourseScoreSubmitStatusChangedStudent(
			String[] ids);

	/**
	 * ����������ѯ��ҵ�������
	 * @param page
	 * @param searchproperty
	 * @param orderProperty
	 * @return
	 */
	public List getGraduateDesignBatch(Page page, List searchProperty,
			List orderProperty);

	public int getGraduateDesignBatchNum(List searchProperty);

	public Pici getGraduateDesignBatch(List searchProperty);

	
	/**
	 * ��ñ�ҵ���ͣ�����ҵ�����ģ������ڸ�����ʦ
	 * @param teacher_id
	 * @return
	 */
	public Map getGraduateTypes(String teacher_id) ;
	
	/**
	 * ��ҵ���������δѡ����꼶���רҵ
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getGradeEduTypeMajors(Page page, List searchProperty,
			List orderProperty);

	public int getGradeEduTypeMajorsNum(List searchProperty);

	/**
	 * ��ҵ��������Ѿ������˵��꼶���רҵ
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getGradeEduTypeMajorsForPici(Page page, List searchProperty,
			List orderProperty);

	public int getGradeEduTypeMajorsForPiciNum(List searchProperty);

	/**
	 * ��Ŀ������Ϣ�б�
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getSubjectQuestionaryList(Page page, List searchProperty,
			List orderProperty);

	
	public List getSubjectQuestionaryList2(Page page, List searchProperty,
			List orderProperty);
	
	/**
	 * ��ÿ�����˻򲵻ص�ѧ���б� ����
	 * 
	 * */
	public String getActiveSubjectSearchStudentIdString();

	/**
	 * ��ÿ�����˻򲵻ص�ѧ���б�  ����
	 * 
	 * */
	public String getActiveRegBeginCourseStudentIdString(List searchProperty);

	/**
	 * ��ÿ�����˻򲵻ص�ѧ���б�  ����
	 * 
	 * */
	public String getActiveMetaphaseCheckStudentIdString(List searchProperty);

	/**
	 * ��ÿ�����˻򲵻ص�ѧ���б�  ��������
	 * 
	 * */
	public String getActiveDiscourseCheckStudentIdString(List searchProperty);

	public int getSubjectQuestionaryListNum(List searchProperty);

	
	public int getSubjectQuestionaryListNum2(List searchProperty);
    /**
     * ���εǼ���Ϣ�б�
     */
	public List getRegBeginCourseList(Page page, List searchProperty,
			List orderProperty);
	
	   /**
     * ���εǼ���Ϣ�б�������վ��ʦ��
     */
	public List getRegBeginCourseList1(Page page, List searchProperty,
			List orderProperty,String teacher_id);
	/**
     * ���εǼ���Ϣ�б����ڷ�վ��ʦ��
     */
	public List getRegBeginCourseList2(Page page, List searchProperty,
			List orderProperty,String teacher_id);

	public int getRegBeginCourseListNum(List searchProperty);
	/**
	 * ��ѯ������Ϣ���������ڽ�ʦ��
	 * @param searchProperty
	 * @return
	 */
	public int getRegBeginCourseListNum1(List searchProperty,String teacher_id);
	/**
	 * ��ѯ������Ϣ���������ڸ�����ʦ��
	 * @param searchProperty
	 * @return
	 */
	public int getRegBeginCourseListNum2(List searchProperty,String teacher_id);

	/**
	 * ��վָ����ʦ��Ϣ�б�
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getSiteTutorTeacherList(Page page, List searchProperty,
			List orderProperty);

	public int getSiteTutorTeacherListNum(List searchProperty);

	/**
	 * ���ڼ����Ϣ�б�
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getMetaphaseCheckList(Page page, List searchProperty,
			List orderProperty);

	public int getMetaphaseCheckListNum(List searchProperty);

	/**
	 * ���������Ϣ�б�
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getRejoinRequesList(Page page, List searchProperty,
			List orderProperty);

	public int getRejoinRequesListNum(List searchProperty);

	/**
	 * ����������Ϣ�б�
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 */
	public List getDisCourseList(Page page, List searchProperty,
			List orderProperty);

	public int getDisCourseListNum(List searchProperty);

	/**
	 * *********************************��վָ����ʦ**************************************************
	 */
	public List getNoneGradeEduMajorsForTeacher(Page page, List searchProperty,
			List orderProperty);

	public int getNoneGradeEduMajorsForTeacherNum(List searchProperty);

	public List getGradeEduMajorsForTeacher(Page page, List searchProperty,
			List orderProperty);

	public int getGradeEduMajorsForTeacherNum(List searchProperty);

	//**********************************************************************************

	public List getSelectStudentAtCurrentPici(Page page, List searchProperty,
			List orderProperty);

	public int getSelectStudentAtCurrentPiciNum(List searchProperty);

	public List getSelectGraduateStudentAtCurrentPici(Page page,
			List searchProperty, List orderProperty);

	public int getSelectGraduateStudentAtCurrentPiciNum(List searchProperty);

	public List getNotSelectStudentAtCurrentPici(Page page,
			List searchProperty, List orderProperty);

	public int getNotSelectStudentAtCurrentPiciNum(List searchProperty);

	public List getStudentMajors(String teacherId);

	public int isGraduate(String studentId);

	/**
	 * ��ñ�ҵ�����������ѧ������
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public int getStudentFreeApplyNum(List searchproperty, List orderproperty);

	/**
	 * ��ñ�ҵ�����������ѧ���б�
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */

	public List getStudentFreeApply(Page page, List searchproperty,
			List orderproperty);
	/**
	 * ��ñ�ҵ��ƻ��ҵ����ҵ��������ѧ���б�
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	
	//-----------------------------
	public List getStudentFreeApplyExec(Page page, List searchproperty,
			List orderproperty);
	
	/**
	 * ��ñ�ҵ����ҵ��������ѧ������
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public int getStudentFreeApplyExecNum(List searchproperty, List orderproperty);
	
	
	//---���ڶ���վ��ʦ
	public int getStudentFreeApplyTutorNum(List searchproperty, List orderproperty);

	/**
	 * ��ñ�ҵ�����������ѧ���б�
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */

	public List getStudentFreeApplyTutor(Page page, List searchproperty,
			List orderproperty);
	/**
	 * ��ñ�ҵ��ƻ��ҵ����ҵ��������ѧ���б�
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	
	//-----------------------------
	public List getStudentFreeApplyExecTutor(Page page, List searchproperty,
			List orderproperty);
	
	/**
	 * ��ñ�ҵ����ҵ��������ѧ������
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public int getStudentFreeApplyExecTutorNum(List searchproperty, List orderproperty);

}