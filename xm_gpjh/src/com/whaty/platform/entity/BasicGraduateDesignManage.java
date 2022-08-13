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
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.util.Page;

public abstract class BasicGraduateDesignManage {
	/** Creates a new instance of BasicRecruitManage */
	public BasicGraduateDesignManage() {
	}

	public BasicGraduateDesignManage(ManagerPriv managerPriv) {
	}

	/**
	 * �޸ı�ҵ�������״̬������ĳһ��������
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateGraduateDesignBatchStatus(String id)
			throws PlatformException;

	/**
	 * ��ӱ�ҵ�������
	 * 
	 * @param id
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param subjectSTime
	 * @param subjectETime
	 * @param openSubSTime
	 * @param openSubETime
	 * @param subSubSTime
	 * @param subSubETime
	 * @param reJoinSTime
	 * @param reJoinETime
	 * @param reportGradeSTime
	 * @param reportGradeETime
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException;
	public abstract int addGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginDate,String writeDiscourseEndDate)
			throws PlatformException;
	public abstract int addGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginDate,String writeDiscourseEndDate,String semester)
			throws PlatformException;
	
	/**
	 * ɾ����������
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteGraduateDesignBatch(String id)
			throws PlatformException, PlatformException;

	/**
	 * �޸ı�ҵ�������
	 * 
	 * @param id
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param subjectSTime
	 * @param subjectETime
	 * @param openSubSTime
	 * @param openSubETime
	 * @param subSubSTime
	 * @param subSubETime
	 * @param reJoinSTime
	 * @param reJoinETime
	 * @param reportGradeSTime
	 * @param reportGradeETime
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException;
	
	public abstract int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime)
			throws PlatformException;
	public abstract int updateGraduateDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime,String semester_id)
			throws PlatformException;
	/**
	 * ����aid�õ��������ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateDesignBatch(java.lang.String aid)
			throws PlatformException;

	/**
	 * ��ȡ��ǰ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateDesignBatch()
			throws PlatformException;

	/**
	 * �������κ����������Ʋ�ѯ��ر�ҵ��������б�
	 * 
	 * @param page
	 * @param piciNo
	 * @param pciName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateDesignBatches(Page page, String piciNo,
			String pciName) throws PlatformException;

	public abstract int getGraduateDesignBatchesNum(String piciNo,
			String pciName) throws PlatformException;
	
	
//	-----------��ҵ����ҵ��������start
	/**
	 * ��ӱ�ҵ����ҵ����
	 */
	
	public abstract int addGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException;
	public abstract int addGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginDate,String writeDiscourseEndDate)
			throws PlatformException;
	public abstract int addGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginDate,String writeDiscourseEndDate,String semester_id)
			throws PlatformException;
	
	/**
	 * �޸ı�ҵ�������״̬������ĳһ��������
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateGraduateExecDesignBatchStatus(String id)
			throws PlatformException;
	
	/**
	 * ɾ����������
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteGraduateExecDesignBatch(String id)
			throws PlatformException, PlatformException;

	/**
	 * �޸ı�ҵ�������
	 * 
	 * @param id
	 * @param name
	 * @param startTime
	 * @param endTime
	 * @param subjectSTime
	 * @param subjectETime
	 * @param openSubSTime
	 * @param openSubETime
	 * @param subSubSTime
	 * @param subSubETime
	 * @param reJoinSTime
	 * @param reJoinETime
	 * @param reportGradeSTime
	 * @param reportGradeETime
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime)
			throws PlatformException;
	
	public abstract int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime)
			throws PlatformException;
	public abstract int updateGraduateExecDesginBatch(String id, String name,
			String startTime, String endTime, String subjectSTime,
			String subjectETime, String openSubSTime, String openSubETime,
			String subSubSTime, String subSubETime, String reJoinSTime,
			String reJoinETime, String reportGradeSTime, String reportGradeETime,String writeDiscourseBeginTime,String writeDiscourseEndTime,String semester_id)
			throws PlatformException;
	/**
	 * ����aid�õ��������ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateExecDesignBatch(java.lang.String aid)
			throws PlatformException;

	/**
	 * ��ȡ��ǰ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateExecDesignBatch()
			throws PlatformException;

	/**
	 * �������κ����������Ʋ�ѯ��ر�ҵ��������б�
	 * 
	 * @param page
	 * @param piciNo
	 * @param pciName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateExecDesignBatches(Page page, String piciNo,
			String pciName) throws PlatformException;

	public abstract int getGraduateExecDesignBatchesNum(String piciNo,
			String pciName) throws PlatformException;


	//-----------��ҵ����ҵ�����end

	/**
	 * ĳһ��ҵ��������·���Ҫ����꼶���רҵ
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
	 * �꼶���רҵ�ĵѿ�����
	 * 
	 * @param page
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGradeEduTypeMajorList(Page page, String pici_id,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getGradeEduTypeMajorListNum(String pici_id,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	/**
	 * Ϊ��Ӧ�������꼶רҵ���
	 * 
	 * @param piciId
	 * @param gradeId
	 * @param eduTypeId
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addGradeEduTypeMajors(String piciId, String gradeId,
			String eduTypeId, String majorId) throws PlatformException;

	public abstract int removeGradeEduTypeMajors(String id)
			throws PlatformException;

	public abstract int IsExitGraduateStudent(String id)
			throws PlatformException;

	/**
	 * ��ѯ�Ƿ��б�ҵ������������ò�κ�רҵ
	 * 
	 * @throws PlatformException
	 */
	public abstract int IsExistBatchId(String batch_id)
			throws PlatformException;

	public abstract FreeApply getStudentFreeApply(String studentId)
			throws PlatformException;

	/**
	 * ��ѯѧԱ�ı�ҵ����ҵ�б���Ϣ
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
	 * ��ѯѧԱ�ı�ҵ����ҵ�б���Ϣ
	 * 
	 * @param page
	 * @param reg_no
	 * @param name
	 * @param site
	 * @param grade
	 * @param eduTypeID
	 * @param majorId
	 * @param piciId
	 *            ��ҵ�������ID
	 * @param courseId
	 *            ��ҵ��ƿγ�ID
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
	//�������µĲ�����ʹ������԰���˽����ѯ��score;������type
	public abstract List getStudentFreeApplyList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
//	�������µĲ�����ʹ������԰���˽����ѯ��score;��������type 
	public abstract int getStudentFreeApplyListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
	
	/**
	 * ��ñ�ҵ����ҵ����������
	 */
//	�������µĲ�����ʹ������԰���˽����ѯ��score;������type
	public abstract List getStudentFreeApplyExecList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId, String courseId, String semesterId,String score)
			throws PlatformException;
//	�������µĲ�����ʹ������԰���˽����ѯ��score;��������type
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
	public abstract List getRegBeginCourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId) throws PlatformException;

	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId)
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
	//����ѧԺ��˲�ѯ����conResult
	public abstract List getRegBeginCourseList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId, String piciId,String conResult,String semesterId) throws PlatformException;
//	����ѧԺ��˲�ѯ����conResult
	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String piciId,String conResult,String semesterId) throws PlatformException;

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
	 * �������β�ѯ,ѧ�� ����������Ϣ�б�
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

	public abstract int updateDiscourseGrade(String id, String grade)
			throws PlatformException;
	
	public abstract int updateDiscourseGradeAndTotalGrade(String disId, String grade,String studentId,String courseId,String semesterId)
	throws PlatformException;

	// ******************************��վָ����ʦ*********************************************************
	/**
	 * ��վ��ʦ��Ҫ��˵��꼶���רҵ
	 * 
	 * @param page
	 * @param piciId
	 * @param gradeId
	 * @param eduTypeID
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGradeEduTypeMajorForTeacher(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getGradeEduTypeMajorForTeacherNum(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract List getNoneGradeEduTypeMajorForTeacher(Page page,
			String teacerId, String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	public abstract int getNoneGradeEduTypeMajorForTeacherNum(String teacerId,
			String gradeId, String eduTypeID, String majorId)
			throws PlatformException;

	/**
	 * Ϊ��Ӧ��վָ����ʦ����꼶���רҵ
	 * 
	 * @param piciId
	 * @param gradeId
	 * @param eduTypeId
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addGradeEduTypeMajorsForTeacher(String teacherId,
			String gradeId, String eduTypeId, String majorId)
			throws PlatformException;

	public abstract int removeGradeEduTypeMajorsForTeahcer(String id)
			throws PlatformException;

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

	public abstract List getGraduatePici() throws PlatformException;
	
	/**
	 * ��ô���ҵ����
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateExecPici() throws PlatformException;

	public abstract Discourse createDiscourse() throws PlatformException;

	public abstract void addDiscourseAssessGradeByRegNO(List discourseList)
			throws PlatformException;

	public abstract void addDiscourseRejionGradeByRegNO(List discourseList)
			throws PlatformException;
	
	//------------------------�����ҵ��Ƴɼ�, ������������ lwx 2008-06-07
	public abstract void addDiscourseAssessGradeByRegNOTotalGrade(List discourseList ,String semesterId)
	throws PlatformException;

	public abstract void addDiscourseRejionGradeByRegNOTotalGrade(List discourseList,String semesterId)
	throws PlatformException;

	
	//-------------------------------------

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
	 * ��ѧԱ�ı�ҵ����ҵ�ɼ���Ϣ���е����޸�
	 * @param id     ��ҵ����ҵ���м�¼���
	 * @param score  ��ҵ����ҵҪ�԰ٷ��Ƽ�¼
	 * @return		 ִ�гɹ���
	 * @throws PlatformException
	 */
	public abstract int updateGraduateHomeWork(String id,String score)throws PlatformException;
	
	/**
	 * ��ӱ�ҵ����ҵ�ɼ�,�����������ɼ���
	 */
	public abstract int updateGraduateHomeWorkToTotalGrade(String homeworkId,String studentId,String semesterId,String courseId,String score)throws PlatformException;
	
	
	/**
	 * ������ӻ��޸�ѧԱ�ı�ҵ����ҵ�ɼ���Ϣ
	 * @param scoreList   ѧԱ�Ĵ���ҵ�ɼ���Ϣ
	 * @throws PlatformException
	 */
	public abstract void updateBatchGraduateHomeWork(List scoreList)throws PlatformException;
	
	/**
	 * ������ӻ��޸�ѧԱ�ı�ҵ����ҵ�ɼ���Ϣ,�����뵽�����ɼ���
	 * @param scoreList   ѧԱ�Ĵ���ҵ�ɼ���Ϣ
	 * @throws PlatformException
	 */
	public abstract void updateBatchGraduateHomeWorkTotalGrade(List scoreList,String semesterId)throws PlatformException;
	
	/**
	 * ��÷�վָ����ʦָ����רҵ
	 * @return List ��ʦָ��רҵ�б�
	 * @param String teacherId ��ʦ���
	 */
	public abstract List getGraduateSiteTeacherMajors(String teacherId) throws PlatformException;
	
}
