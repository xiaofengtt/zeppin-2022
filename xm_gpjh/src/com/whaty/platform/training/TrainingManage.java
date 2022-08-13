/**
 * 
 */
package com.whaty.platform.training;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.leaveword.LeaveWordManage;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;
import com.whaty.platform.standard.aicc.operation.AiccCourseManage;
import com.whaty.platform.standard.scorm.operation.ScormManage;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.basic.TrainingCourseType;
import com.whaty.platform.training.basic.courseware.AiccTrainingCourseware;
import com.whaty.platform.training.basic.courseware.Scorm12TrainingCourseware;
import com.whaty.platform.training.skill.Skill;
import com.whaty.platform.training.skill.SkillChain;
import com.whaty.platform.training.user.TrainingClassManager;
import com.whaty.platform.training.user.TrainingManagerPriv;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class TrainingManage {

	private TrainingManagerPriv managerPriv;

	public TrainingManagerPriv getManagefPriv() {
		return managerPriv;
	}

	public void setManagePriv(TrainingManagerPriv managerpriv) {
		this.managerPriv = managerPriv;
	}

	/**
	 * ��ӿγ�����
	 * 
	 * @param name
	 * @param status
	 * @param note
	 * @param parentId
	 * @throws PlatformException
	 */
	public abstract void addTrainingCourseType(String name, String status,
			String note, String parentId) throws PlatformException;

	/**
	 * �޸Ŀγ�����
	 * 
	 * @param id
	 * @param name
	 * @param note
	 * @param status
	 * @throws PlatformException
	 */
	public abstract void updateTrainingCourseType(String id, String name,
			String note, String status) throws PlatformException;

	/**
	 * ����γ�����
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void activeTrainingCourseType(List typeIds, boolean flag)
			throws PlatformException;

	/**
	 * ����ѧԱ
	 * 
	 * @param studentIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingStudent(List studentIds, boolean flag)
			throws PlatformException;

	/**
	 * ��ĳ�γ������Ƶ���һ������
	 * 
	 * @param id
	 * @param parentId
	 * @throws PlatformException
	 */
	public abstract void moveTrainingCourseType(String id, String parentId)
			throws PlatformException;

	/**
	 * ɾ��һ���γ�����
	 * 
	 * @param ids
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingCourseType(List ids)
			throws PlatformException;

	/**
	 * �õ�ĳ���γ������µ�������
	 * 
	 * @param parentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourseTypes(String parentId)
			throws PlatformException;

	/**
	 * @param parentId
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourseTypes(String parentId, String field,
			String order) throws PlatformException;

	/**
	 * �õ�ĳ���γ�����
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourseType getTrainingCourseType(String id)
			throws PlatformException;

	/**
	 * �õ�ȫ���γ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getAllCoursesNum() throws PlatformException;

	/**
	 * �õ�ȫ���γ�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllCourses() throws PlatformException;

	/**
	 * �����γ�
	 * 
	 * @param page
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param active
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchCourses(Page page, String courseId,
			String courseName, String courseTypeId, String active)
			throws PlatformException;

	/**
	 * @param page
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param active
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchCourses(Page page, String courseId,
			String courseName, String courseTypeId, String active,
			String field, String order) throws PlatformException;

	/**
	 * ��ȡ�γ�����
	 * 
	 * @param courseId
	 * @param courseName
	 * @param courseTypeId
	 * @param active
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCoursesNum(String courseId, String courseName,
			String courseTypeId, String active) throws PlatformException;

	/**
	 * �õ�ĳ���γ������µĿγ�
	 * 
	 * @param typeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllCourses(String typeId) throws PlatformException;

	/**
	 * �õ�ĳ���γ�
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourse getTrainingCourse(String id)
			throws PlatformException;

	/**
	 * �õ�ĳ���γ��µ�Aicc�μ�
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccTrainingCourseware getAiccCourseware(String courseId)
			throws PlatformException;

	/**
	 * �õ�Aicc�μ�������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccCourseManage getAiccCourseManage()
			throws PlatformException;

	/**
	 * �õ�ĳ���γ��µ�Scorm12�μ�
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Scorm12TrainingCourseware getScorm12Courseware(
			String courseId) throws PlatformException;

	/**
	 * �õ�Scorm12�μ�������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScormManage getScormManage() throws PlatformException;

	/**
	 * �õ�ĳ�ſγ̵���ʽѡ��ѧԱ����
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCourseCheckedStudentsNum(String courseId)
			throws PlatformException;

	public abstract int getCourseCheckedStudentsNum(String courseId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * �õ�ĳ���γ̵���ʽѡ��ѧԱ
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseCheckedStudents(String courseId)
			throws PlatformException;

	/**
	 * ��ҳ�õ�ĳ�ſγ̵���ʽѡ��ѧԱ
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseCheckedStudents(Page page, String courseId)
			throws PlatformException;

	public abstract List getCourseCheckedStudents(Page page, String courseId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * @param page
	 * @param courseId
	 * @param student_id
	 * @param student_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseCheckedStudents(Page page, String courseId,
			String student_id, String student_name, String field, String order)
			throws PlatformException;

	/**
	 * �õ�ĳ�γ̵�����ѡ��ѧԱ����
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCourseApplyededStudentsNum(String courseId)
			throws PlatformException;

	public abstract int getCourseApplyededStudentsNum(String courseId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * �õ�ĳ���γ̵�����ѡ��ѧԱ
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseApplyededStudents(String courseId)
			throws PlatformException;

	/**
	 * ��ҳ�õ�ĳ�γ̵�����ѡ��ѧԱ
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseApplyededStudents(Page page, String courseId)
			throws PlatformException;

	public abstract List getCourseApplyededStudents(Page page, String courseId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * @param page
	 * @param courseId
	 * @param student_id
	 * @param student_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseApplyededStudents(Page page, String courseId,
			String student_id, String student_name, String field, String order)
			throws PlatformException;

	/**
	 * ��ӿγ�
	 * 
	 * @param id
	 * @param name
	 * @param credit
	 * @param studyTime
	 * @param status
	 * @param typeId
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void addTrainingCourse(String id, String name,
			String credit, String studyTime, String status, String typeId,
			String note) throws PlatformException;

	/**
	 * �޸Ŀγ�
	 * 
	 * @param id
	 * @param name
	 * @param credit
	 * @param studyTime
	 * @param status
	 * @param typeId
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void updateTrainingCourse(String id, String name,
			String credit, String studyTime, String status, String typeId,
			String note) throws PlatformException;

	/**
	 * ɾ���γ�
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingCourse(String id)
			throws PlatformException;

	/**
	 * ����flag������������γ�
	 * 
	 * @param classId
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingCourse(List courseIds, boolean flag)
			throws PlatformException;

	/**
	 * ɾ������γ�
	 * 
	 * @param ids
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingCourses(List ids)
			throws PlatformException;

	/**
	 * ���γ�����ĳ���γ�������
	 * 
	 * @param courseId
	 * @param typeId
	 * @throws PlatformException
	 */
	public abstract void moveToOtherType(String courseId, String typeId)
			throws PlatformException;

	/**
	 * Ϊ�γ���ӿμ�
	 * 
	 * @param courseId
	 * @param courewareId
	 * @param coursewareTitle
	 * @param coursewareType
	 * @throws PlatformException
	 */
	public abstract void addTrainingCourseware(String courseId,
			String coursewareId, String coursewareType)
			throws PlatformException;

	/**
	 * Ϊ�γ�ɾ���μ�
	 * 
	 * @param courseId
	 * @param coursewareId
	 * @throws PlatformException
	 */
	public abstract void removeTrainingCourseware(String courseId,
			String coursewareId) throws PlatformException;

	/**
	 * �õ�ĳ��ѧ������Ϣ
	 * 
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingStudent getTrainingStudent(String studentId)
			throws PlatformException;

	/**
	 * �õ�����ѧ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingStudent() throws PlatformException;

	/**
	 * ����ѧ��
	 * 
	 * @param page
	 * @param studentId
	 * @param studentName
	 * @param studentNickName
	 * @param checked
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchTrainingStudents(Page page, String studentId,
			String studentName, String studentNickName, String checked)
			throws PlatformException;

	/**
	 * @param page
	 * @param studentId
	 * @param studentName
	 * @param studentNickName
	 * @param checked
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchTrainingStudents(Page page, String studentId,
			String studentName, String studentNickName, String checked,
			String field, String order) throws PlatformException;

	/**
	 * ��ȡ����������ѧ������
	 * 
	 * @param studentId
	 * @param studentName
	 * @param studentNickName
	 * @param checked
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingStudentsNum(String studentId,
			String studentName, String studentNickName, String checked)
			throws PlatformException;

	/**
	 * ���ѧ��
	 * 
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @throws PlatformException
	 */
	public abstract void addTrainingStudent(String id, String name,
			String nickName, String email) throws PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @param mobilePhone
	 * @param active
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void addTrainingStudent(String id, String name,
			String nickName, String email, String mobilePhone, String active,
			String note) throws PlatformException;

	public abstract void addTrainingManager(String id, String name,
			String nickName, String email, String note)
			throws PlatformException;

	/**
	 * �޸�ѧ����Ϣ
	 * 
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @throws PlatformException
	 */
	public abstract void updateTrainingStudent(String id, String name,
			String nickName, String email) throws PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @param mobilePhone
	 * @throws PlatformException
	 */
	public abstract void updateTrainingStudent(String id, String name,
			String nickName, String email, String mobilePhone)
			throws PlatformException;

	public abstract void updateTrainingStudent(String id, String name,
			String nickName, String email, String mobilePhone, int status)
			throws PlatformException;

	public abstract void updateTrainingStudent(String id, String name,
			String nickName, String email, String mobilePhone, int status,
			String note) throws PlatformException;

	/**
	 * ɾ��ѧ��
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingStudent(String id)
			throws PlatformException;

	public abstract void deleteTrainingManager(List managerIdList)
			throws PlatformException;

	/**
	 * �õ�ȫ������ѵ����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getAllTrainingClassNum() throws PlatformException;

	/**
	 * �õ�ȫ������ѵ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClass() throws PlatformException;

	/**
	 * ��ҳ�õ�ȫ������ѵ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClass(Page page)
			throws PlatformException;

	/**
	 * @param page
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClass(Page page, String field,
			String order) throws PlatformException;

	/**
	 * �����ѵ��
	 * 
	 * @param name
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param startSelectDate
	 * @param startEndDate
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void addTrainingClass(String name, String status,
			String startDate, String endDate, String startSelectDate,
			String endSelectDate, String note) throws PlatformException;

	/**
	 * �޸���ѵ��
	 * 
	 * @param id
	 * @param name
	 * @param status
	 * @param startDate
	 * @param endDate
	 * @param startSelectDate
	 * @param startEndDate
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void updateTrainingClass(String id, String name,
			String status, String startDate, String endDate,
			String startSelectDate, String endSelectDate, String note)
			throws PlatformException;

	/**
	 * ɾ����ѵ��
	 * 
	 * @param ids
	 * @throws PlatformException
	 */
	public abstract void deleteTrainingClass(List ids) throws PlatformException;

	/**
	 * ���ݱ�־�������������ѵ��
	 * 
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeTrainingClass(List classIds, boolean flag)
			throws PlatformException;

	/**
	 * Ϊĳ����ѵ��������ѵ������
	 * 
	 * @param chiefIds
	 * @throws PlatformException
	 */
	public abstract void addClassManagers(String classId, List chiefIds)
			throws PlatformException;

	/**
	 * Ϊĳ����ѵ��ɾ����ѵ������
	 * 
	 * @param chiefIds
	 * @throws PlatformException
	 */
	public abstract void removeClassManagers(String classId, List chiefIds)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ���µĿγ��б�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getClassCoursesNum(String classId)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ���µĿγ��б�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getClassCoursesNum(String classId, String course_id,
			String course_name) throws PlatformException;

	/**
	 * �õ�ĳ����ѵ���µĿγ��б�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(String classId)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ���µĿγ��б�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(Page page, String classId)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ���µĿγ��б�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(Page page, String classId,
			String course_id, String course_name) throws PlatformException;

	/**
	 * @param page
	 * @param classId
	 * @param course_id
	 * @param course_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(Page page, String classId,
			String course_id, String course_name, String field, String order)
			throws PlatformException;

	/**
	 * Ϊĳ����ѵ����ӿγ�
	 * 
	 * @param classId
	 * @param courseIds
	 * @throws PlatformException
	 */
	public abstract void addClassCourses(String classId, List courseIds)
			throws PlatformException;

	/**
	 * Ϊĳ����ѵ��ɾ���γ�
	 * 
	 * @param classId
	 * @param courseIds
	 * @throws PlatformException
	 */
	public abstract void removeClassCourses(String classId, List courseIds)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ����ѧ������
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCheckedClassStudentsNum(String classId)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ����ѧ������
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getCheckedClassStudentsNum(String classId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * �õ�ĳ����ѵ����ѧ���б�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedClassStudents(String classId)
			throws PlatformException;

	/**
	 * ��ҳ�õ�ĳ����ѵ����ѧ���б�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedClassStudents(Page page, String classId)
			throws PlatformException;

	/**
	 * ��ҳ�õ�ĳ����ѵ���·���������ѧ���б�
	 * 
	 * @param page
	 * @param classId
	 * @param student_id
	 * @param student_name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedClassStudents(Page page, String classId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * @param page
	 * @param classId
	 * @param student_id
	 * @param student_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCheckedClassStudents(Page page, String classId,
			String student_id, String student_name, String field, String order)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ����δ���ͨ����ѧ����
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getUnCheckedClassStudentsNum(String classId)
			throws PlatformException;

	public abstract int getUnCheckedClassStudentsNum(String classId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * �õ�ĳ����ѵ����δ���ͨ����ѧ��
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedClassStudents(String classId)
			throws PlatformException;

	/**
	 * ��ҳ�õ�ĳ����ѵ����δ���ͨ����ѧ��
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedClassStudents(Page page, String classId)
			throws PlatformException;

	public abstract List getUnCheckedClassStudents(Page page, String classId,
			String student_id, String student_name) throws PlatformException;

	/**
	 * @param page
	 * @param classId
	 * @param student_id
	 * @param student_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCheckedClassStudents(Page page, String classId,
			String student_id, String student_name, String field, String order)
			throws PlatformException;

	/**
	 * Ϊĳ����ѵ�����ѧԱ
	 * 
	 * @param classId
	 * @param courseIds
	 * @throws PlatformException
	 */
	public abstract void addClassStudents(String classId, List studentIds)
			throws PlatformException;

	/**
	 * Ϊĳ����ѵ��ȷ��ѧԱѡ����ѵ��
	 * 
	 * @param classId
	 * @param studentIds
	 * @throws PlatformException
	 */
	public abstract void checkClassStudents(String classId, List studentIds)
			throws PlatformException;

	/**
	 * Ϊĳ����ѵ��ɾ��ѧԱ
	 * 
	 * @param classId
	 * @param courseIds
	 * @throws PlatformException
	 */
	public abstract void removeClassStudents(String classId, List studentIds)
			throws PlatformException;

	/**
	 * �õ�ĳ��������
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract SkillChain getSkillChain(String chainId)
			throws PlatformException;

	/**
	 * �õ�ȫ��������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllSkillChain() throws PlatformException;

	/**
	 * @param chainName
	 * @param chainNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getSkillChainNum(String chainName, String chainNote)
			throws PlatformException;

	/**
	 * @param page
	 * @param chainName
	 * @param chainNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSkillChain(Page page, String chainName,
			String chainNote, String field, String order)
			throws PlatformException;

	/**
	 * ���Ӽ�����
	 * 
	 * @param name
	 * @param status
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void addSkillChain(String name, String status, String note)
			throws PlatformException;

	/**
	 * �޸ļ�����
	 * 
	 * @param id
	 * @param name
	 * @param status
	 * @param note
	 * @throws PlatformException
	 */
	public abstract void updateSkillChain(String id, String name,
			String status, String note) throws PlatformException;

	/**
	 * ɾ��������
	 * 
	 * @param idList
	 * @throws PlatformException
	 */
	public abstract void deleteSkillChain(List idList) throws PlatformException;

	/**
	 * �õ�ĳ���������µļ���
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getChainSkills(String chainId)
			throws PlatformException;

	/**
	 * �õ����м�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getAllSkillsNum() throws PlatformException;

	public abstract int getSkillsNum(String skill_id, String skill_name)
			throws PlatformException;

	/**
	 * �õ����м���
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllSkills() throws PlatformException;

	/**
	 * ��ҳ�õ����м���
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllSkills(Page page) throws PlatformException;

	public abstract List getSkills(Page page, String skill_id, String skill_name)
			throws PlatformException;

	/**
	 * @param page
	 * @param skill_id
	 * @param skill_name
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSkills(Page page, String skill_id,
			String skill_name, String field, String order)
			throws PlatformException;

	/**
	 * ���Ӽ���
	 * 
	 * @param name
	 * @param status
	 * @param note
	 * @param chainId
	 * @throws PlatformException
	 */
	public abstract void addSkill(String name, String status, String note,
			String chainId) throws PlatformException;

	/**
	 * �޸ļ���
	 * 
	 * @param id
	 * @param name
	 * @param status
	 * @param note
	 * @param chainId
	 * @throws PlatformException
	 */
	public abstract void updateSkill(String id, String name, String status,
			String note, String chainId) throws PlatformException;

	/**
	 * ɾ������
	 * 
	 * @param id
	 * @throws PlatformException
	 */
	public abstract void deleteSkill(String id) throws PlatformException;

	/**
	 * ɾ��һ������
	 * 
	 * @param ids
	 * @throws PlatformException
	 */
	public abstract void deleteSkills(List ids) throws PlatformException;

	/**
	 * �����������ĳЩskill
	 * 
	 * @param skillIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeSkill(List skillIds, boolean flag)
			throws PlatformException;

	/**
	 * �����������ĳЩskillChain
	 * 
	 * @param skillChainIds
	 * @param flag
	 * @throws PlatformException
	 */
	public abstract void activeSkillChain(List skillChainIds, boolean flag)
			throws PlatformException;

	/**
	 * Ϊĳ����������Ӽ���
	 * 
	 * @param skills
	 * @param skillChainId
	 * @throws PlatformException
	 */
	public abstract void addChainSkills(List skills, String skillChainId)
			throws PlatformException;

	/**
	 * ��ĳ��������ɾ������
	 * 
	 * @param skills
	 * @param skillChainId
	 * @throws PlatformException
	 */
	public abstract void removeChainSkills(List skills, String skillChainId)
			throws PlatformException;

	/**
	 * Ϊĳ��������ӿγ�
	 * 
	 * @param courses
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void addSkillCourses(List courses, String skillId)
			throws PlatformException;

	/**
	 * Ϊĳ������ɾ���γ�
	 * 
	 * @param courses
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void removeSkillCourses(List courses, String skillId)
			throws PlatformException;

	/**
	 * �õ�ĳ���γ̵��Ⱦ�����
	 * 
	 * @param skillId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPreSkills(String skillId) throws PlatformException;

	/**
	 * Ϊĳ����������Ⱦ�����
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void addPreSkill(List skills, String skillId)
			throws PlatformException;

	/**
	 * Ϊĳ������ɾ���Ⱦ�����
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void removePreSkill(List skills, String skillId)
			throws PlatformException;

	/**
	 * ���ѡ����Ϣ
	 * 
	 * @param studentIds
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void addCourseStudent(List studentIds, String courseId)
			throws PlatformException;

	/**
	 * ���ѡ����Ϣ
	 * 
	 * @param studentIds
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void checkCourseStudent(List studentIds, String courseId)
			throws PlatformException;

	/**
	 * ɾ��ѡ����Ϣ
	 * 
	 * @param studentIds
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void removeCourseStudent(List studentIds, String courseId)
			throws PlatformException;

	/**
	 * ����Ŀ�꼼��
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void targetSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * ȡ��Ŀ�꼼��
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void unTargetSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * ���輼��
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void rewardSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * ������輼��
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void unRewardSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * �õ�������������ѵ����Ŀ
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingClassNum(String classId, String className)
			throws PlatformException;

	/**
	 * �õ�������������ѵ��
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClass(Page page, String classId,
			String className) throws PlatformException;

	/**
	 * �õ�ĳ����ѵ��
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingClass getTrainingClass(String classId)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ������
	 * 
	 * @param classManagerId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingClassManager getTrainingClassManager(
			String classManagerId) throws PlatformException;

	/**
	 * ������ѵ������
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchTrainingClassManagers(Page page, String id,
			String name, String classId) throws PlatformException;

	/**
	 * �õ�������������ѵ����������
	 * 
	 * @param managerId
	 * @param managerName
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTrainingClassManagersNum(String managerId,
			String managerName) throws PlatformException;

	/**
	 * �õ�ȫ����ѵ������
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClassManagers(Page page)
			throws PlatformException;

	/**
	 * �õ�������������ѵ������
	 * 
	 * @param page
	 * @param managerId
	 * @param managerName
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClassManagers(Page page,
			String managerId, String managerName) throws PlatformException;

	/**
	 * @param page
	 * @param managerId
	 * @param managerName
	 * @param field
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllTrainingClassManagers(Page page,
			String managerId, String managerName, String field, String order)
			throws PlatformException;

	/**
	 * �õ��γ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingCourseTypeTree() throws PlatformException;

	/**
	 * �õ�ĳ�������ܹ��ƶ����Ŀγ�Ŀ¼��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getMoveTrainingCourseTypeTree(String courseTypeId)
			throws PlatformException;

	/**
	 * �õ�ĳ������
	 * 
	 * @param skillId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Skill getSkill(String skillId) throws PlatformException;

	/**
	 * ��������
	 * 
	 * @param page
	 * @param skillId
	 * @param skillName
	 * @param flag
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchSkill(Page page, String skillId,
			String skillName, String flag) throws PlatformException;

	/**
	 * ������ѡ�γ�
	 * 
	 * @param courseId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unApplyCourseStudent(String courseId, List studentList)
			throws PlatformException;

	/**
	 * ȡ��ȷ��ѡ��
	 * 
	 * @param courseId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unCheckCourseStudent(String courseId, List studentList)
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

	/**
	 * ����ɾ��������ѵ�������
	 * 
	 * @param classId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unApplyClassStudent(String classId, List studentList)
			throws PlatformException;

	/**
	 * @param skillId
	 * @param courseId
	 * @return �ж�ĳ���ܿγ��Ƿ��Ѿ�����
	 * @throws PlatformException
	 */
	public abstract boolean isSkillCourseExisted(String skillId, String courseId)
			throws PlatformException;

	/**
	 * ɾ��ĳ�������µ�ѧ��
	 * 
	 * @param students
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void removeSkillStudents(List students, String skillId)
			throws PlatformException;

	/**
	 * ���ĳ���ܵ�ѧ��������
	 * 
	 * @param studentIds
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void checkSkillStudent(List studentIds, String skillId)
			throws PlatformException;

	/**
	 * ȡ�����ĳ���ܵ�ѧ��������
	 * 
	 * @param courseId
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void unCheckSkillStudent(String skillId, List studentList)
			throws PlatformException;

	/**
	 * ������ѵ�����Ա����Ϣ
	 * 
	 * @param id
	 * @param name
	 * @param nickName
	 * @param email
	 * @param mobilePhone
	 * @throws PlatformException
	 */
	public abstract void updateTrainingClassManager(String id, String name,
			String nickName, String email, String note, String mobilePhone)
			throws PlatformException;

	public abstract LeaveWordManagerPriv getLeaveWordManagerPriv(String id)
			throws PlatformException;

	public abstract LeaveWordManage getLeaveWordManage(LeaveWordManagerPriv priv)
			throws PlatformException;
}
