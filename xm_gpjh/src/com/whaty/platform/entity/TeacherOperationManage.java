/**
 * 
 */
package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.courseware.CoursewareManage;
import com.whaty.platform.courseware.CoursewareManagerPriv;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.info.News;
import com.whaty.platform.interaction.InteractionManage;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;

/**
 * @author Administrator
 * 
 */
public abstract class TeacherOperationManage {

	private TeacherPriv teacherPriv;

	public TeacherOperationManage() {

	}

	public TeacherPriv getTeacherPriv() {
		return teacherPriv;
	}

	public void setTeacherPriv(TeacherPriv teacherPriv) {
		this.teacherPriv = teacherPriv;
	}

	public abstract Teacher getTeacher() throws PlatformException;

	public abstract List getTeachClasses() throws PlatformException;

	public abstract List getInfoList(Page page) throws PlatformException;

	public abstract int getInfoListNum() throws PlatformException;

	/**
	 * ���½�ʦ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int updateTeacher(String teacherId, String name,
			String password, String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException;

	/**
	 * ���½�ʦ����
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int updatePwd(String id, String oldPassword,
			String newPassword) throws PlatformException;

	public abstract News getNews(String news_id) throws PlatformException;

	public abstract List getCourses(Page page, String id)
			throws PlatformException;

	public abstract OpenCourse getOpenCourse(String opend_course_id)
			throws PlatformException;

	public abstract InteractionManage createInteractionManage()
			throws PlatformException;

	public abstract InteractionUserPriv getInteractionUserPriv(String user_id)
			throws PlatformException;

	public abstract InteractionUserPriv getInteractionUserPriv(String user_id,
			String teachclassId) throws PlatformException;

	public abstract CoursewareManage creatCoursewareManage();

	public abstract CoursewareManagerPriv getCoursewareManagerPriv();

	public abstract BasicActivityManage createBasicActivityManage();

	public abstract BasicScoreManage createBasicScoreManage();
	
	public abstract BasicUserManage createBasicUserManage();

	public abstract ManagerPriv getManagerPriv();
	
	public abstract SmsManagerPriv getSmsManagerPriv();
	
	public abstract SmsManage getSmsManage(SmsManagerPriv smsManagerPriv)
			throws PlatformException;
	
	public abstract List getTheachItem(String id);
	
	public abstract int updateTeachItemStatus(String item, String status,
			String id) throws PlatformException;
	
	/**
	 * ��ѧվ�б�
	 * 
	 * @return ��ѧվ�б�
	 * @throws PlatformException
	 */
	public abstract List getAllSites() throws PlatformException;
	
	/**
	 * �꼶�б�
	 * 
	 * @return �꼶�б�
	 * @throws PlatformException
	 */
	public abstract List getAllGrades() throws PlatformException;
	
	
	/**
	 * ����б�
	 * 
	 * @return ����б�
	 * @throws PlatformException
	 */
	public abstract List getAllEduTypes() throws PlatformException;
	
	/**
	 * רҵ�б�
	 * 
	 * @return רҵ�б�
	 * @throws PlatformException
	 */
	public abstract List getAllMajors() throws PlatformException;
	
	
	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateDesignBatch()
			throws PlatformException;
	/**
	 * ���aid�õ�������ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateDesignBatch(java.lang.String aid)
			throws PlatformException;
	
	/**
	 * ��ȡ��ǰ�����ҵ���
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateExecDesignBatch()
	throws PlatformException;
	/**
	 * ���aid�õ���ҵ����ҵ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateExecDesignBatch(java.lang.String aid)
	throws PlatformException;
   
	 /**
	  * (��վָ��Ա�Ѿ����ȷ��)ѧԱ����Ŀ�����Ϣ��
	  * 
	  * @param page
	  * @param reg_no  ѧԱѧ��
	  * @param name   ����
	  * @param site  վ��
	  * @param grade  �꼶
	  * @param eduTypeID  ���
	  * @param majorId  רҵ
	  * @param status  ״̬λ   0Ϊδȷ��;1Ϊ��վȷ�ϣ�2Ϊ��վ���أ�3Ϊ��վȷ�ϣ�4Ϊ��վ����
	  * @return
	  * @throws PlatformException
	  */
	public abstract List getSubjectQuestionaryList(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;

	public abstract List getSubjectQuestionaryList2(Page page, String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;

	/*
	 * ��ÿ�����˻򲵻ص�ѧ��ѧ�Ŵ� ����
	 * */
	public abstract String getActiveRegBeginCourseStudentIdString(String name ,String regNo , String siteId , String gradeId , String eduTypeId , String majorId) throws PlatformException;

	/*
	 * ��ÿ�����˻򲵻ص�ѧ��ѧ�Ŵ�  �걨��Ŀ
	 * */
	public abstract String getActiveSubjectSearchStudentIdString() throws PlatformException;

	/*
	 * ��ÿ�����˻򲵻ص�ѧ��ѧ�Ŵ�  ����
	 * */
	public abstract String getActiveMetaphaseCheckStudentIdString(String name ,String regNo , String siteId , String gradeId , String eduTypeId , String majorId) throws PlatformException;

	/*
	 * ��ÿ�����˻򲵻ص�ѧ��ѧ�Ŵ�  ���ۻ���
	 * */
	public abstract String getActiveDiscourseCheckStudentIdString(String name ,String regNo , String siteId , String gradeId , String eduTypeId , String majorId) throws PlatformException;


	public abstract int getSubjectQuestionaryListNum(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;

	public abstract int getSubjectQuestionaryListNum2(String reg_no,
			String name, String site, String grade, String eduTypeID,
			String majorId,String status) throws PlatformException;

	public abstract int changeSubjectQuestionary(String id,String status,String message);


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
	 *            ��ҵ������ID
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
	 * (��վָ��Ա�Ѿ����ȷ��)ѧԱ�Ŀ��εǼ���Ϣ
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
			String majorId,String status,String semesterId) throws PlatformException;

	public abstract int getRegBeginCourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status,String semesterId)
			throws PlatformException;
	public abstract int changeRegBeginCourse(String id,String status,String message);

	/**
	 * ��վָ��Ա��Ҫ��˵�ָ����ʦ�б�
	 * @param page
	 * @param teacherId
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSiteTutorTeacherList(Page page, String teacherId,
			String name,String status) throws PlatformException;

	public abstract int getSiteTutorTeacherListNum(String reg_no, String name,String status)
			throws PlatformException;
	public abstract int changeSiteTutorTeacher(String id,String status,String message);
	/**
	 * ��վָ��Ա�Ѿ�ȷ�ϵ�ѧԱ���ڼ����Ϣ�б�
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
			String majorId,String status) throws PlatformException;

	public abstract int getMetaphaseCheckListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,String status)
			throws PlatformException;
	public abstract int changeMetaphaseCheck(String id,String status,String message);
	/**
	 * ��վָ��Ա�Ѿ�ȷ�ϵ�ѧԱ���������Ϣ�б�
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
			String majorId, String type,String status) throws PlatformException;

	public abstract int getRejoinRequisitionListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status) throws PlatformException;
	public abstract int changeRejoinRequisition(String id,String status,String message);
	/**
	 * ����������Ϣ�б�
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
			String majorId, String type,String status) throws PlatformException;

	public abstract int getDiscourseListNum(String reg_no, String name,
			String site, String grade, String eduTypeID, String majorId,
			String type,String status) throws PlatformException;
	public abstract int changeDiscourse(String id,String status,String message);
	
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
	
	/**
	 * @���ָ��ѧ��רҵ�б�
	 * 
	 * @return ѧ���б�
	 */
	public abstract List getStudentsMajors(String siteTeacher_id) ;
	
	/**
	 * @�����������ѧ����
	 * 
	 * @return ѧ���б�
	 */
	public abstract int getStudentFreeApplyNum(String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo) throws PlatformException;

	/**
	 * @�����������ѧ����
	 * 
	 * @return ѧ���б�
	 */
	public abstract int getStudentFreeApplyNum(String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId) throws PlatformException;
	/**
	 * ��ø���վָ����ʦָ���꼶���רҵ�µ�ѧ�����������б��¼����
	 * @param siteId
	 * @param edutypeId
	 * @param majorId
	 * @param gradeId
	 * @param status
	 * @param studentName
	 * @param regNo
	 * @param courseId
	 * @param teacher_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStudentFreeApplyNum(String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId,String teacher_id,String semesterId) throws PlatformException;
	
	/**
	 * @�����������ѧ���б�
	 * 
	 * @return ѧ���б�
	 */
	public abstract List getStudentFreeApply(Page page,String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo) throws PlatformException;
	
	/**
	 * @�����������ѧ���б�
	 * 
	 * @return ѧ���б�
	 */
	public abstract List getStudentFreeApply(Page page,String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId) throws PlatformException;
	/**
	 * ��ø���վָ����ʦָ���꼶���רҵ�µ�ѧ���ҵ������������б�
	 * @param page
	 * @param siteId
	 * @param edutypeId
	 * @param majorId
	 * @param gradeId
	 * @param status
	 * @param studentName
	 * @param regNo
	 * @param courseId
	 * @param teacher_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentFreeApply(Page page,String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId,String teacher_id,String semesterId) throws PlatformException;
	
	/**
	 * �����վ��ʦָ����ѧ���ҵ����ҵ���б�
	 * @param page
	 * @param siteId
	 * @param edutypeId
	 * @param majorId
	 * @param gradeId
	 * @param status
	 * @param studentName
	 * @param regNo
	 * @param courseId
	 * @param teacher_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentFreeApplyExec(Page page,String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId,String teacher_id,String semesterId) throws PlatformException;
	
	/**
	 * ��ҵ����ҵ����������
	 */
	public abstract int getStudentFreeApplyExecNum(String siteId,String edutypeId,String majorId,String gradeId,String status,String studentName,String regNo,String courseId,String teacher_id,String semesterId) throws PlatformException;
	
	
	
	/**
	 * ���freeapply id ���freeApply
	 */
	public abstract FreeApply getStudentFreeApply(String id) throws PlatformException;

	public abstract FreeApply getStudentFreeApplyExec(String id) throws PlatformException;
	/**
	 * @�����������
	 * 
	 * @return int
	 */
	public abstract int checkFreeApply(String id,String type,String note) throws PlatformException;
	
	/**
	 * ����������룬����ɼ���
	 */
	public abstract int checkFreeApply(String id,String type,String note,String score) throws PlatformException;
	
	
	/**
	 * ��õ�ǰѧ��
	 */
	public abstract List getActiveSemeser() throws PlatformException;
	
	
	/**
	 * ����������ĳɼ����뵽������
	 */
	public abstract int checkFreeApplyToTatalGrade(String studentId,String semesterId,String courseId,String score) throws PlatformException;
	
	
	/**
	 * @���ñ�ҵ��Ʒ���
	 * 
	 * @return int
	 */
	public abstract int setDiscourseScore(String subjectId,String score);

	/**
	 * @���ñ�ҵ����ҵ����
	 * 
	 * @return int
	 */
	public abstract int setExerciseScore(String subjectId,String score);
	
	/**
	 * @ȷ�ϱ�ҵ��Ʒ���
	 * 
	 * @return int
	 */
	public abstract int confirmDiscourseScore(String subjectId,String note);
	
	/**
	 * @ȷ�ϱ�ҵ����ҵ����
	 * 
	 * @return int
	 */
	public abstract int confirmExerciseScore(String subjectId,String note);

	/**
	 * @���ر�ҵ����ҵ����
	 * 
	 * @return int
	 */
	public abstract int rejectExerciseScore(String subjectId,String note);
	
	/**
	 * @���ر�ҵ��Ʒ���
	 * 
	 * @return int
	 */
	public abstract int rejectDiscourseScore(String subjectId,String note);
	
	/**
	 * ��ñ�ҵ����б�
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduatePici() throws PlatformException;
	
	/**
	 * ��ô���ҵ���
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getGraduateExecPici() throws PlatformException;

}
