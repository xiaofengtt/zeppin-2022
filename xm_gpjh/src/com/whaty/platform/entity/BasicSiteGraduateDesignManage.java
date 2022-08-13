package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.util.Page;

public abstract class BasicSiteGraduateDesignManage {

	/**
	 * ���aid�õ���ǰ��ҵ�����ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateDesignBatch(java.lang.String aid)
			throws PlatformException;
	
	/**
	 * ���aid�õ���ǰ��ҵ����ҵ��ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateExecDesignBatch(java.lang.String aid)
	throws PlatformException;

	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateDesignBatch()
			throws PlatformException;
	
	/**
	 * ��ȡ��ǰ�����ҵ��� lwx 2008-05-29
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateExecDesignBatch()
	throws PlatformException;

	/**
	 * ĳһ��ҵ�������·��Ҫ����꼶���רҵ
	 * 
	 * @param page
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGradeEduTypeMajor(Page page, String piciId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getGradeEduTypeMajorNum(String piciId, String gradeId,
			String eduTypeID, String majorId) throws PlatformException;

	/**
	 * ���ѧԱid���ѧԱ��������Ϣ��
	 * 
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract FreeApply getStudentFreeApply(String studentId)
			throws PlatformException;

	/**
	 * ��ѯѧԱ����Ŀ�����Ϣ��
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId) throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId) throws PlatformException;

	/**
	 * ��ѯѧ��ı�ҵ����ҵ��Ϣ
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId) throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId) throws PlatformException;

	/**
	 * ��ѯѧ��ı�ҵ����ҵ��Ϣ
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param piciId
	 * @param courseId
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId)
			throws PlatformException;

	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId)
			throws PlatformException;

	/**
	 * ��ѯѧԱ���������б���Ϣ
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param piciId
	 * @param courseId
	 * @param semesterId
	 * @return
	 * @throws PlatformException
	 */
//	�������µĲ���ʹ������԰���˽���ѯ��score;��
	public abstract List getStudentFreeApplyList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
//	�������µĲ���ʹ������԰���˽���ѯ��score;��
	public abstract int getStudentFreeApplyListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
	
//---------��ñ�ҵ����ҵ��������  lwx 2008-05-30
//	�������µĲ���ʹ������԰���˽���ѯ��score;��
	public abstract List getStudentFreeApplyExecList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
//	�������µĲ���ʹ������԰���˽���ѯ��score;��
	public abstract int getStudentFreeApplyExecListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId, String courseId, String semesterId,String score)
			throws PlatformException;


	/**
	 * ���εǼ���Ϣ�б�
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
//	����ѧԺ��˲�ѯ���conResult
	public abstract List getRegBeginCourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId,String conResult,String semesterId) throws PlatformException;
//	����ѧԺ��˲�ѯ���conResult
	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId,String conResult,String semesterId) throws PlatformException;
	/**
	 * ���εǼ���Ϣ�б�
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param piciId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRegBeginCourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId) throws PlatformException;

	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId) throws PlatformException;

	/**
	 * ָ����ʦ�б�
	 * 
	 * @param page
	 * @param teacherId
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSiteTutorTeacherList(Page page, String teacherId,
			String name) throws PlatformException;

	public abstract int getSiteTutorTeacherListNum(String reg_no, String name)
			throws PlatformException;

	/**
	 * ���ڼ����Ϣ�б�
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getMetaphaseCheckList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
			throws PlatformException;

	/**
	 * ���ڼ����Ϣ�б�
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param piciId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getMetaphaseCheckList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId) throws PlatformException;

	/**
	 * ���������Ϣ�б�
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRejoinRequisitionList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type) throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException;

	/**
	 * ���������Ϣ�б�
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param type
	 * @param piciId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRejoinRequisitionList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type, String piciId,String semesterId)
			throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String piciId,String semesterId) throws PlatformException;

	/**
	 * ����������Ϣ�б�
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getDiscourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type) throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type) throws PlatformException;

	/**
	 * �����β�ѯ ����������Ϣ�б�
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param type
	 * @param piciId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getDiscourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String type, String piciId,String semesterId)
			throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type, String piciId,String semesterId) throws PlatformException;

	public abstract SubjectQuestionary getSubjectQuestionary(String id)
			throws PlatformException;

	public abstract RegBgCourse getRegBeginCourse(String id)
			throws PlatformException;

	public abstract SiteTeacher getSiteTutorTeacher(String id)
			throws PlatformException;

	public abstract MetaphaseCheck getMetaphaseCheck(String id)
			throws PlatformException;

	public abstract RejoinRequisition getRejoinRequisition(String id)
			throws PlatformException;

	public abstract Discourse getDiscourse(String id) throws PlatformException;

	// ******************************************************************************
	/**
	 * ��վָ����ʦ��Ҫָ����ѧ��
	 * 
	 * @param page
	 * @param piciId
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSelectStudentAtCurrentPici(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getStudentAtCurrentPiciNum(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	/**
	 * �鿴��ʦ�Ѿ�ָ��ѧ��
	 * 
	 * @param page
	 * @param teacerId
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSelectStudentAtCurrentPici(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId,
			String course) throws PlatformException;

	public abstract int getStudentAtCurrentPiciNum(String teacerId,
			String gradeId, String eduTypeID, String majorId, String course)
			throws PlatformException;

	public abstract List getNotSelectStudentAtCurrentPici(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getNotSelectStudentAtCurrentPici(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract List getNotSelectStudentAtCurrentPici(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId,
			String siteid, String reg_no, String name) throws PlatformException;

	public abstract int getNotSelectStudentAtCurrentPiciNum(String teacerId,
			String gradeId, String eduTypeID, String majorId, String siteid,
			String reg_no, String name) throws PlatformException;

	/**
	 * ��ҵѧ���б�
	 * 
	 * @param page
	 * @param teacerId
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @param siteid
	 * @param reg_no
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getNotSelectStudentAtCurrentPici(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId,
			String siteid, String reg_no, String name, String courseId)
			throws PlatformException;

	public abstract int getNotSelectStudentAtCurrentPiciNum(String teacerId,
			String gradeId, String eduTypeID, String majorId, String siteid,
			String reg_no, String name, String courseId)
			throws PlatformException;

	/**
	 * ָ����Ӧѧ����վ������ʦ
	 * 
	 * @param teacherId
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addStudentForTeacher(String teacherId, String studentId)
			throws PlatformException;

	public abstract int removeStudentsForTeahcer(String id)
			throws PlatformException;

	public abstract Discourse createDiscourse() throws PlatformException;

	public abstract void addDiscourseAssessGradeByRegNO(List discourseList)
			throws PlatformException;

	public abstract void addDiscourseRejionGradeByRegNO(List discourseList)
			throws PlatformException;
	
	
//	------------------------�����ҵ��Ƴɼ�, ������������ lwx 2008-06-07
	public abstract void addDiscourseAssessGradeByRegNOTotalGrade(List discourseList ,String semesterId)
	throws PlatformException;

	public abstract void addDiscourseRejionGradeByRegNOTotalGrade(List discourseList,String semesterId)
	throws PlatformException;

	
	//-------------------------------------
	

	public abstract int updateDiscourseGrade(String id, String grade)
			throws PlatformException;
	
	/**
	 * �ѱ�ҵ��Ƽ������3ɼ�,
	 */
	public abstract int updateDiscourseGradeAndTotalGrade(String disId, String grade,String studentId,String courseId,String semesterId)
	throws PlatformException;

	/**
	 * ��ҵ�������ʱ���
	 * 
	 * @param type
	 *            TYPE1:��ҵ�������ʱ�� TYPE2:ѡ��ʱ�� TYPE3:����ʱ�� TYPE4:�ύ����ʱ��
	 *            TYPE5:���(����)ʱ�� TYPE6:�ɼ��ϱ�ʱ��
	 * @throws PlatformException
	 */
	public abstract int limitTime(String type) throws PlatformException;

	/**
	 * ȡ�õ�ǰ��ҵ��ƻ���
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateActivePici() throws PlatformException;

	/**
	 * ��ѧԱ�ı�ҵ����ҵ�ɼ���Ϣ���е����޸�
	 * 
	 * @param id
	 *            ��ҵ����ҵ���м�¼���
	 * @param score
	 *            ��ҵ����ҵҪ�԰ٷ��Ƽ�¼
	 * @return ִ�гɹ���
	 * @throws PlatformException
	 */
	public abstract int updateGraduateHomeWork(String id, String score)
			throws PlatformException;
	
	/**
	 * ��ӱ�ҵ����ҵ�ɼ�,���������3ɼ���
	 */
	public abstract int updateGraduateHomeWorkToTotalGrade(String homeworkId,String studentId,String semesterId,String courseId,String score)throws PlatformException;
	

	/**
	 * ����ӻ��޸�ѧԱ�ı�ҵ����ҵ�ɼ���Ϣ
	 * 
	 * @param scoreList
	 *            ѧԱ�Ĵ���ҵ�ɼ���Ϣ
	 * @throws PlatformException
	 */
	public abstract void updateBatchGraduateHomeWork(List scoreList)
			throws PlatformException;
	
	/**
	 * ����ӻ��޸�ѧԱ�ı�ҵ����ҵ�ɼ���Ϣ,�����뵽���3ɼ���
	 * @param scoreList   ѧԱ�Ĵ���ҵ�ɼ���Ϣ
	 * @throws PlatformException
	 */
	public abstract void updateBatchGraduateHomeWorkTotalGrade(List scoreList,String semesterId)throws PlatformException;

	/**
	 *  ��÷�վָ����ʦָ����רҵ 
	 *  @return List ��ʦָ��רҵ�б� 
	 *  @param String teacherId ��ʦ���
	 */
	public abstract List getGraduateSiteTeacherMajors(String teacherId)
			throws PlatformException;

}
