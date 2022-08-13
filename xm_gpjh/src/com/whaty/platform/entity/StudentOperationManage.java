/**
 * 
 */
package com.whaty.platform.entity;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusManage;
import com.whaty.platform.campus.CampusNormalManage;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.platform.entity.activity.ChangeMajorApply;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.basic.ExecutePlan;
import com.whaty.platform.entity.graduatedesign.Discourse;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.RejoinRequisition;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.info.News;
import com.whaty.platform.interaction.InteractionUserPriv;
import com.whaty.platform.util.Page;

/**
 * ����������ѧ����ݵ�¼��Ĺ���
 * 
 * @author Administrator
 * 
 */
public abstract class StudentOperationManage {

	/**
	 * �÷������ص�ǰ�û�ѡ��Ŀγ�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSelectdTeachClasses() throws PlatformException;

	/**
	 * �õ�ȫ���γ��б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourses(Page page, String id)
			throws PlatformException;

	public abstract int getCoursesNum() throws PlatformException;

	public abstract Student getStudent() throws PlatformException;

	public abstract List getElectives(Page page, Student student)
			throws PlatformException;
	public abstract List getElectives(Page page, Student student,String elective_id)
	throws PlatformException;

	public abstract String getElectiveTimes(String courseId,
			String student_id) throws PlatformException;

	public abstract int setElectiveTimes(String courseId,
			String student_id, String time) throws PlatformException;

	public abstract int getElectivesNum(Student student)
			throws PlatformException;

	public abstract BasicScoreManage creatBasicScoreManage();

	public abstract List getElectives(Page page, String course_id,
			String course_name, String semester_id, Student student,
			boolean confirmStatus) throws PlatformException;

	public abstract int getElectivesNum(String course_id, String course_name,
			String semester_id, Student student, boolean confirmStatus)
			throws PlatformException;

	public abstract int updatePwd(String id, String oldPassword,
			String newPassword) throws PlatformException;

	public abstract int updateStudent(String studentid, String emails,
			String phones, String mobilephones, String address,
			String postalcode, String edu, String zzmm)
			throws PlatformException;

	public abstract List getInfoList(Page page) throws PlatformException;

	public abstract int getInfoListNum() throws PlatformException;

	public abstract News getNews(String news_id) throws PlatformException;

	public abstract List getRegSemester(Page page) throws PlatformException;

	public abstract int getRegSemesterNum() throws PlatformException;

	public abstract List getTeachPlanCourses(Page page, String course_id,
			String course_name, String course_type, String plan_credit,
			String plan_time) throws PlatformException;

	public abstract List getOpenCoursesInTeachPlan(Page page,
			String semesterId, String course_id, String course_name,
			String credit, String course_time) throws PlatformException;

	public abstract List getExecutePlanCourseGroupsByExecutePlanId(
			String executePlanId) throws PlatformException;

	public abstract List getUnConfirmEleCourses(Page page, String studentId,
			String semesterId, String course_id, String course_name)
			throws PlatformException;

	public abstract List getOpenCoursesInExecutePlan(Page page,
			String semesterId, String course_id, String course_name,
			String credit, String course_time) throws PlatformException;

	public abstract int getOpenCoursesInExecutePlanNum(String semesterId,
			String course_id, String course_name, String credit,
			String course_time) throws PlatformException;

	public abstract ExecutePlan getExecutePlan(String semesterId,
			String gradeId, String majorId, String eduTypeId)
			throws PlatformException;

	public abstract List getOpenCoursesNotInTeachPlan(Page page,
			String semesterId, String course_id, String course_name,
			String credit, String course_time) throws PlatformException;

	public abstract List getOpenCoursesNotInExecutePlan(Page page,
			String semesterId, String course_id, String course_name,
			String credit, String course_time) throws PlatformException;

	public abstract List getOpenCourses(Page page, String semester_id)
			throws PlatformException;

	public abstract int getOpenCoursesNum(String semester_id)
			throws PlatformException;

	public abstract int addElectives(String[] opencourse_ids, String semester_id)
			throws PlatformException;

	public abstract int addElectives(HttpServletRequest request)
			throws PlatformException;

	public abstract void addNotExecutePlanElectives(HttpServletRequest request)
			throws PlatformException;

	public abstract OpenCourse getOpenCourse(String opend_course_id)
			throws PlatformException;

	public abstract InteractionUserPriv getInteractionUserPriv(String user_id)
			throws PlatformException;

	public abstract InteractionUserPriv getInteractionUserPriv(String user_id,
			String teachclassId) throws PlatformException;

	public abstract List getUserFeeRecord(Page page, String userId,
			String feeType, String payoutType, String startDate, String endDate)
			throws PlatformException;

	public abstract int getUserFeeRecordNum(String userId, String feeType,
			String payoutType, String startDate, String endDate)
			throws PlatformException;

	public abstract List getUserLeftFee(Page page, String userId, String feeType)
			throws PlatformException;

//	public abstract BankManage getBankManage() throws PlatformException;

//	public abstract HashMap payByBank(HttpServletRequest req)
//			throws PlatformException;

	public abstract void checkPay(String id) throws PlatformException;

	public abstract boolean checkPayByOrderNo(String orderNo)
			throws PlatformException;

	public abstract void repealByOrderNo(String orderNo)
			throws PlatformException;

	public abstract void unCheckPayByOrderNo(String orderNo)
			throws PlatformException;

	public abstract boolean getCheckStatusByOrderNo(String orderNo)
			throws PlatformException;

	public abstract void deleteByOrderNo(String orderNo)
			throws PlatformException;

	public abstract List getTeachersByTeachClass(String teachclass_id)
			throws PlatformException;

	public abstract List getExamRooms(String course_id, String user_id)
			throws PlatformException;

	/**
	 * ��ݿ���ID�õ���ѧ���б�
	 * 
	 * @param open_course_id
	 * @return ��ѧ�����List
	 * @throws PlatformException
	 */
	public abstract List getTeachClassByOpenCourseId(String open_course_id)
			throws PlatformException;

	public abstract String getOrderNo(String regNo) throws PlatformException;

	/***************************************************************************
	 * UserFeeReturnApply ����
	 **************************************************************************/

	/**
	 * ���UserFeeReturnApply
	 * 
	 * @param userId
	 * @param amount
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addUserFeeReturnApply(String userId, double amount,
			String note) throws PlatformException;

	/**
	 * �޸�UserFeeReturnApply
	 * 
	 * @param id
	 * @param userId
	 * @param amount
	 * @param applyTime
	 * @param isChecked
	 * @param checkTime
	 * @param isReturned
	 * @param returnTime
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateUserFeeReturnApply(String id, String userId,
			double amount, String applyTime, String isChecked,
			String checkTime, String isReturned, String returnTime, String note)
			throws PlatformException;

	/**
	 * ɾ��UserFeeReturnApply
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteUserFeeReturnApply(String id)
			throws PlatformException;

	/**
	 * ��ȡ���������˷�������
	 * 
	 * @param userId
	 * @param isChecked
	 * @param isReturned
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getUserFeeReturnApplyNum(String userId,
			String isChecked, String isReturned, String note)
			throws PlatformException;

	/**
	 * ��ȡ���������˷������б�
	 * 
	 * @param page
	 * @param userId
	 * @param isChecked
	 * @param isReturned
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUserFeeReturnApplyList(Page page, String userId,
			String isChecked, String isReturned, String note)
			throws PlatformException;

	public abstract List getChangeMajorApplys(String studentId)
			throws PlatformException;

	public abstract int addChangeMajorApply(String studentId,
			String old_site_id, String new_site_id, String old_major_id,
			String new_major_id, String old_edu_type_id,
			String new_edu_type_id, String old_grade_id, String new_grade_id)
			throws PlatformException;

	public abstract int updateChangeMajorApply(String id, String old_site_id,
			String new_site_id, String old_major_id, String new_major_id,
			String old_edu_type_id, String new_edu_type_id,
			String old_grade_id, String new_grade_id) throws PlatformException;

	public abstract int deleteChangeMajorApply(String id)
			throws PlatformException;

	public abstract ChangeMajorApply getChangeMajorApply(String id)
			throws PlatformException;

	/**
	 * רҵ�б�
	 * 
	 * @return רҵ�б�
	 * @throws NoRightException
	 */
	public abstract List getAllMajors() throws NoRightException;

	/**
	 * ��ѧվ�б�
	 * 
	 * @return ��ѧվ�б�
	 * @throws NoRightException
	 */
	public abstract List getAllSites() throws NoRightException;

	/**
	 * �ж�һ��ѧ���Ƿ�������
	 * 
	 * @param ssoId
	 * @return
	 * @throws NoRightException
	 */
	public abstract boolean isNewStudent(String ssoId) throws NoRightException;

	/**
	 * ���ѧԱ��ѡ����Ϣ
	 * @param studentid  ѧ��ID
	 * @param mainjob    ���幤��
	 * @param subjectname  ��ҵ�������
	 * @param subjectcontent  ��ҵ��������
	 * @param remark      ��ע
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addSubjectQuestionary(String studentid, String mainjob,
			String subjectname, String subjectcontent, String remark)
			throws PlatformException;
	
	
	/**
	 * ���ѧԱ��ѡ����Ϣ
	 * @param studentid  ѧ��ID
	 * @param mainjob    ���幤��
	 * @param subjectname  ��ҵ�������
	 * @param subjectcontent  ��ҵ��������
	 * @param link  ·��
	 * @param remark      ��ע
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addSubjectQuestionary(String studentid, String mainjob,
			String subjectname, String subjectcontent, String link ,String remark)
			throws PlatformException;

	public abstract int editSubjectQuestionary(String studentid, String mainjob,
			String subjectname, String subjectcontent, String link ,String remark)
			throws PlatformException;

	
	public abstract int updateSubjectQuestionary(String id, String mainjob,
			String subjectname, String subjectcontent, String remark)
			throws PlatformException;

	/**
	 * ���ѧԱ�Ŀ�����Ϣ
	 * @param studentid
	 * @param discoursename   �������
	 * @param discoursecontent ��������
	 * @param discoursePlan  ��ҵ���Ľ���Լ�ʱ�䰲��
	 * @param remark
	 * @return  
	 * @throws PlatformException
	 */
	public abstract int addRegBeginCourse(String studentid,
			String discoursename, String discoursecontent,
			String discoursePlan, String remark) throws PlatformException;
	
	/**
	 * ���ѧԱ�Ŀ�����Ϣ
	 * @param studentid
	 * @param discoursename   �������
	 * @param discoursecontent ��������
	 * @param discoursePlan  ��ҵ���Ľ���Լ�ʱ�䰲��
	 * @param link   ·��
	 * @param remark
	 * @return  
	 * @throws PlatformException
	 */
	public abstract int addRegBeginCourse(String studentid,
			String discoursename, String discoursecontent,
			String discoursePlan,String link, String remark) throws PlatformException;
	

	public abstract int updateRegBeginCourse(String id, String discoursename,
			String discoursecontent, String discoursePlan, String remark)
			throws PlatformException;

	/**
	 * ���ѧԱ�����ڼ����Ϣ
	 * @param studentid
	 * @param discourseName  ��ҵ����������
	 * @param completetask   ��ҵ��������������
	 * @param anaphasePlan   ���ڼƻ�
	 * @param remark         ��ע
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addMetaphaseCheck(String studentid,
			String discourseName, String completetask, String anaphasePlan,
			String remark) throws PlatformException;

	/**
	 * ���ѧԱ�����ڼ����Ϣ
	 * @param studentid
	 * @param discourseName  ��ҵ����������
	 * @param completetask   ��ҵ��������������
	 * @param anaphasePlan   ���ڼƻ�
	 * @param link   ·��
	 * @param remark         ��ע
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addMetaphaseCheck(String studentid,
			String discourseName, String completetask, String anaphasePlan,String link,
			String remark) throws PlatformException;

	
	public abstract int updateMetaphaseCheck(String id, String discourseName,
			String completetask, String anaphasePlan, String remark)
			throws PlatformException;

	/**
	 * ���ѧԱ�Ĵ��(����)������Ϣ
	 * @param studentid
	 * @param discourseName   �������
	 * @param completeStatus  �������״̬
	 * @param requisitionType ��������
	 * @param remark          ��ע
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addRejoinRequisition(String studentid,
			String discourseName, String completeStatus,
			String requisitionType, String remark) throws PlatformException;
	
	/**
	 * ���ѧԱ�Ĵ��(����)������Ϣ
	 * @param studentid
	 * @param discourseName   �������
	 * @param completeStatus  �������״̬
	 * @param requisitionType ��������
	 * @param link   ·��
	 * @param remark          ��ע
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addRejoinRequisition(String studentid,
			String discourseName, String completeStatus,
			String requisitionType,String link, String remark) throws PlatformException;

	

	public abstract int updateRejoinRequisition(String id,
			String discourseName, String completeStatus,
			String requisitionType, String remark) throws PlatformException;

	/**
	 * ���ѧԱ��������Ϣ
	 * @param studentid            
	 * @param discourseName           ������Ŀ
	 * @param discourseContent        ��������
	 * @param requisitionType         ��������
	 * @param remark
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addDiscourse(String studentid, String discourseName,
			String discourseContent, String requisitionType, String remark)
			throws PlatformException;

	/**
	 * ���ѧԱ��������Ϣ
	 * @param studentid            
	 * @param discourseName           ������Ŀ
	 * @param discourseContent        ��������
	 * @param requisitionType         ��������
	 * @param link						·��
	 * @param remark
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addDiscourse(String studentid, String discourseName,
			String discourseContent, String requisitionType,String link, String remark)
			throws PlatformException;

	
	public abstract int updateDiscourse(String id, String discourseName,
			String discourseContent, String requisitionType, String remark)
			throws PlatformException;

	public abstract SubjectQuestionary getSubjectQuestionary(String studentId)
			throws PlatformException;

	public abstract RegBgCourse getRegBeginCourse(String studentId)
			throws PlatformException;

	public abstract SiteTeacher getSiteTutorTeacher(String studentId)
			throws PlatformException;

	public abstract MetaphaseCheck getMetaphaseCheck(String studentId)
			throws PlatformException;

	public abstract RejoinRequisition getRejoinRequisition(String studentId)
			throws PlatformException;

	public abstract Discourse getDiscourse(String studentId)
			throws PlatformException;

	/**
	 * ��ȡ��ǰ��ı�ҵ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateDesignBatch()
			throws PlatformException;
	
	/**
	 * ��ȡ��ǰ���ҵ����ҵ���
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getActiveGraduateExecDesignBatch()
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
	 * ���id ��ñ�ҵ����ҵ��ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract Pici getGraduateExecDesignBatch(java.lang.String aid)
	throws PlatformException;

	/**
	 * �ϴ���Ŀ����ĵ�(.doc)
	 * @param reg_no
	 * @param filename
	 * @return
	 * @throws PlatformException
	 */
	public abstract int upLoadSubjectQuestionaryWord(String reg_no,
			String fileLink) throws PlatformException;
	public abstract int upLoadSubjectQuestionaryWordFinally(String reg_no,
			String fileLink) throws PlatformException;

	public abstract int upLoadRegBeginCourseWord(String reg_no, String fileLink)
			throws PlatformException;

	public abstract int upLoadMetaphaseCheckWord(String reg_no, String fileLink)
			throws PlatformException;

	public abstract int upLoadRejoinRequisitionWord(String reg_no,
			String fileLink) throws PlatformException;

	public abstract int upLoadDiscourseWord(String reg_no, String fileLink)
			throws PlatformException;

	/**
	 * ��ҵ�������ʱ���
	 * @param type
	 *  TYPE1:��ҵ�������ʱ��
	 *  TYPE2:ѡ��ʱ�� 
	 *	TYPE3:����ʱ��
	 *	TYPE4:�ύ����ʱ��
	 *	TYPE5:���(����)ʱ��
	 *	TYPE6:�ɼ��ϱ�ʱ��
	 *  TYPE7:׫д����ʱ��
	 * @return
	 * @throws PlatformException
	 */
	public abstract int limitTime(String type) throws PlatformException;
	
	public abstract int isGraduate(String studentId) throws PlatformException;
	
	//У԰�Ļ�ģ�������
	public abstract boolean isClassManager(String studentId,String classId) throws PlatformException;
	
	public abstract boolean isAssociationManager(String studentId,String associationId) throws PlatformException;
	
	public abstract boolean isClassMember(String studentId,String classId) throws PlatformException;
	
	public abstract boolean isAssociationMember(String studentId,String associationId) throws PlatformException;
	
	public abstract List getClassMemberes(Page page,String classId,String studentId) throws PlatformException;
	
	public abstract List getAssociationMemberes(Page page,String associationId,String studentId) throws PlatformException;
	
	public abstract int getClassMemberesNum(String classId,String studentId) throws PlatformException;
	
	public abstract int getAssociationMemberesNum(String associationId,String studentId) throws PlatformException;
	
	public abstract CampusManage getCampusManage(CampusManagerPriv priv)
	throws PlatformException;

	public abstract CampusNormalManage getCampusNormalManage()
	throws PlatformException;
	
	public abstract ManagerPriv getNormalEntityManagePriv()
	throws PlatformException;
	
	/**
	 * �����ҵ��������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int subjectFreeApply(String student_id,String production_name,String link)
	throws PlatformException;
	
	/**
	 *  �����ҵ��������,��������
	 */
	public abstract int subjectFreeApply(String student_id,String production_name,String link,String type)
	throws PlatformException;
	
	/**
	 * ��ȡ��ҵ������������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract FreeApply getSubjectFreeApply(String student_id)
	throws PlatformException;
	
	/**
	 * ��ñ�ҵ����ָ����ʦ��Ϣ
	 * 
	 * @return SiteTeacher
	 * @param String studentId
	 */
	public abstract SiteTeacher getGraduateSiteTeacher(String studentId) throws PlatformException;
	/**
	 * ���ѧ��ѡ��,ͬʱ����ѡ��ȷ�ϲ��۷�
	 * ���Ҫ��ĳ�ſ�,���Խ�selectIds�óɿ�,allIdsΪҪ�˿γ̵�teachclass_id
	 * 
	 * @param idSet
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithFee(String[] selectIds, String[] allIds,
			String semester_id, String student_id) throws PlatformException;

	/**
	 * ���ѧ�����ѡ��,δ����ѡ��ȷ�Ϲʲ��۷�
	 * ���Ҫ��ĳ�ſ�,���Խ�selectIds�óɿ�,allIdsΪҪ�˿γ̵�teachclass_id
	 * @param selectIds
	 * @param allIds
	 * @param semester_id
	 * @param student_id
	 * @throws PlatformException
	 */
	public abstract void electiveWithoutFee(String[] selectIds,
			String[] allIds, String semester_id, String student_id)
			throws PlatformException;
	public abstract HashMap getElectiveRejectNote(String student_id);
}
