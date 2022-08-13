package com.whaty.platform.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.Manager;
import com.whaty.platform.entity.user.SiteManager;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.entity.user.Teacher;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;

/**�����������û�����Ļ���
 * @author chenjian
 *
 */
/**
 * @author chenjian
 *
 */
/**
 * @author chenjian
 * 
 */
public abstract class BasicUserManage {
	/** Creates a new instance of BasicEntityManage */
	public BasicUserManage() {
	}

	/**
	 * ���ѧ���û���Sso��ص���Ϣ
	 * 
	 * @param login_id
	 * @param name
	 * @param email
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addSsoUser(String login_id, String password,
			String name, String email) throws PlatformException;

	/**
	 * ��Sso���û���Ϊѧ��
	 * 
	 * @param ssoUserList
	 * @throws PlatformException
	 */
	public abstract void addStudentFromSso(List ssoUserList)
			throws PlatformException;

	/**
	 * �޸Ĺ���Ա����
	 * 
	 * @param login_id
	 * @param old_pwd
	 * @param new_pwd
	 * @return 1��ʾ�ɹ���0��ʾʧ��
	 * @throws PlatformException
	 */
	public abstract int updateManagerPwd(String sso_id, String old_pwd,
			String new_pwd) throws PlatformException;

	/**
	 * ���ѧ����
	 * 
	 * @param searchProperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStudentsNum(List searchProperties)
			throws PlatformException;

	public abstract int getStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract int getStudentsFeeNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;
	
	public abstract int getStudentsNum(String reg_no, String id_card,
			String site_id, String major_id, String edu_type_id,
			String grade_id, String semester_id) throws PlatformException;

	public abstract int getStudentsNum(String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException;

	public abstract int getAllStudentsNum(String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException;

	public abstract int getUnRegStudentsNum(String semesterId, String reg_no,
			String name, String id_card, String card_type, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String gender, String folk) throws PlatformException;

	/**
	 * ��ҳ���ѧ���б�
	 * 
	 * @param page
	 * @param searchProperties
	 * @param orderProperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudents(Page page, List searchProperties,
			List orderProperties) throws PlatformException;

	/**
	 * ��ѯѧ���ѧ�ѱ�׼��Ϣ�б�
	 *@param page
	 * @param searchProperties
	 * @param orderProperties
	 *@return
	 *@throws PlatformException
	 */
	public abstract List getStudentsFee(Page page, List searchProperties,
			List orderProperties) throws PlatformException;

	/**
	 * ��ѯѧ���ѧ�ѱ�׼��Ϣ�б��¼����
	 *@param searchProperties
	 *@return
	 *@throws PlatformException
	 */
	public abstract int getStudentsFeeNum(List searchProperties)
			throws PlatformException;

	/**
	 * ���ѧ���б�
	 * 
	 * @param searchProperties
	 * @param orderProperties
	 * @return
	 * @throws PlatformException
	 */
	// public abstract List getStudents(List searchProperties,List
	// orderProperties) throws PlatformException;
	public abstract List getStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;
	
	
	public abstract List getStudentsFee(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	public abstract List getStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException;

	public abstract List getNoneExistStudents(Page page, String reg_no,
			String name, String id_card, String card_type, String site_id,
			String major_id, String edu_type_id, String grade_id,
			String gender, String folk, String classId)
			throws PlatformException;

	public abstract int getNoneExistStudentsNum(String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String classId) throws PlatformException;

	public abstract List getAllStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException;

	public abstract List getUnRegStudents(Page page, String semesterId,
			String reg_no, String name, String id_card, String card_type,
			String site_id, String major_id, String edu_type_id,
			String grade_id, String gender, String folk)
			throws PlatformException;

	public abstract List getRegStudentIds(String semesterId)
			throws PlatformException;

	/**
	 * ���ѧ��
	 * 
	 * @param name
	 * @param password
	 * @param email
	 * @param normalInfo
	 * @param eduInfo
	 * @param platformInfo
	 * @param redundance
	 * @throws PlatformException
	 */
	public abstract int addStudent(String name, String password, String email,
			HumanNormalInfo normalInfo, StudentEduInfo eduInfo,
			HumanPlatformInfo platformInfo, RedundanceData redundance)
			throws PlatformException;

	/**
	 * ���ѧ��
	 * 
	 * @param name
	 * @param password
	 * @param email
	 * @param normalInfo
	 * @param eduInfo
	 * @param platformInfo
	 * @param redundance
	 * @throws PlatformException
	 */
	public abstract int addStudent(String name, String password, String email,
			HumanNormalInfo normalInfo, StudentEduInfo eduInfo,
			HumanPlatformInfo platformInfo) throws PlatformException;

	/**
	 * ���ѧ��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void addStudentBatch(List studentList)
			throws PlatformException;

	/**
	 * ��ӽ�ʦ
	 * 
	 * @param teacherList
	 * @throws PlatformException
	 */
	public abstract void addTeacherBatch(List teacherList)
			throws PlatformException;

	/**
	 * �޸�ѧ�����ϸ��Ϣ
	 * 
	 * @param studentId
	 * @param name
	 * @param password
	 * @param email
	 * @param normalInfo
	 * @param teachereduInfo
	 * @param platformInfo
	 * @param redundace
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateStudent(String studentId, String name,
			String password, String email, HumanNormalInfo normalInfo,
			StudentEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException;

	/**
	 * �޸�ѧ������
	 * 
	 * @param id
	 * @param new_pwd
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateStudentPassword(String id, String new_pwd)
			throws PlatformException;

	/**
	 * �޸�ѧ�����ϸ��Ϣ
	 * 
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateStudent(String studentId,
			HumanNormalInfo normalInfo, StudentEduInfo eduInfo,
			HumanPlatformInfo platformInfo, RedundanceData redundanceData)
			throws PlatformException;

	/**
	 * ɾ��ѧ����Ϣ
	 * 
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteStudent(String studentId)
			throws PlatformException;

	/**
	 * ɾ��ѧ����Ϣ
	 * 
	 * @param studentList
	 * @return
	 * @throws PlatformException
	 */
	public abstract void deleteStudentBatch(String studentId[])
			throws PlatformException;

	/**
	 * �õ�ĳ��ѧ�����ϸ��Ϣ
	 * 
	 * @param studentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Student getStudent(String studentId)
			throws PlatformException;

	/**
	 * ��ѧ�ŵõ�ѧ�����ϸ��Ϣ
	 * 
	 * @param regNo
	 * @return
	 * @throws PlatformException
	 */
	public abstract Student getStudentByRegNo(String regNo)
			throws PlatformException;

	/**
	 * ���aid�õ���ʦ����
	 * 
	 * @param tid
	 * @return ��ʦ����
	 * @throws NoRightException
	 */
	public abstract Teacher getTeacher(String tid) throws PlatformException;

	/**
	 * ���aidɾ���ʦ����
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int deleteTeacher(String tid) throws PlatformException;

	/**
	 * ��SsoUser����ӽ�ʦ
	 * 
	 * @param ssoUserList
	 * @throws PlatformException
	 */
	public abstract void addTeacherFormSso(List ssoUserList)
			throws PlatformException;

	public abstract int deleteSiteTeacher(String tid) throws PlatformException;

	/**
	 * ��ӽ�ʦ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int addTeacher(String name, String password, String email,
			HumanNormalInfo normalInfo, TeacherEduInfo teachereduInfo,
			HumanPlatformInfo platformInfo, RedundanceData redundace)
			throws PlatformException;

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
	 * ���·�վ��ʦ
	 */
	public abstract int updateSiteTeacher(String teacherId, String name,
			String password, String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException;

	/**
	 * ��ʦ�б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getTeachers(Page page) throws PlatformException;

	/**
	 * ����б�
	 * 
	 * @param teachclass_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getAssistanceTeachersNum(String teachclass_id)
			throws PlatformException;

	/**
	 * ����б�
	 * 
	 * @param teachclass_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAssistanceTeachers(Page page, String teachclass_id)
			throws PlatformException;

	/**
	 * ���ҽ�ʦ�б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 * @param orderProperty
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	// public abstract List getTeachers(Page page,List searchProperty,List
	// orderproperty) throws PlatformException;
	public abstract List getTeachers(Page page, String search_type,
			String search_value, String teach_level) throws PlatformException;

	/**
	 * ��ʦ�б�
	 * 
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getTeachers() throws PlatformException;

	/**
	 * ��ʦ�б�
	 * 
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getTeachers(Page page, String name, String cardno,
			String gender, String teacher_level) throws PlatformException;

	public abstract List getTeachers(Page page, String name, String cardno,
			String gender, String teacher_level, String dep_id)
			throws PlatformException;

	/**
	 * ��ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	public abstract int getTeachersNum(String name, String cardno,
			String gender, String teacher_level) throws PlatformException;

	public abstract int getTeachersNum(String name, String cardno,
			String gender, String teacher_level, String dep_id)
			throws PlatformException;

	/**
	 * ��ʦ�б�
	 * 
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getNoneExistTeachers(Page page, String name,
			String cardno, String gender, String teacher_level, String classId)
			throws PlatformException;

	/**
	 * ��ʦѧ��ְ��ͳ�Ʒ���
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract Map getTeacherPTStat(Page page) throws PlatformException;

	/**
	 * ��ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	public abstract int getNoneExistTeachersNum(String name, String cardno,
			String gender, String teacher_level, String classId)
			throws PlatformException;

	/**
	 * ��ʦ�б�
	 * 
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getTeacherList(Page page, String name, String cardno,
			String gender) throws PlatformException;

	public abstract int getTeacherListNum(String name, String cardno,
			String gender) throws PlatformException;

	/**
	 * ��ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	public abstract int getTeachersNum() throws PlatformException;

	/**
	 * ���ҽ�ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	// public abstract int getTeachersNum(List searchProperty) throws
	// PlatformException;
	public abstract int getTeachersNum(String search_type, String search_value,
			String teach_level) throws PlatformException;

	/**
	 * ���ҽ�ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	public abstract List getEachStatusTeachersNum() throws PlatformException;

	/**
	 * ��ӹ���Ա
	 * 
	 * @param login_id
	 * @param name
	 * @param password
	 * @param type
	 * @param status
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addManager(String login_id, String name,
			String password, String type, String status)
			throws PlatformException;

	/**
	 * ɾ�����Ա
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteManager(String id) throws PlatformException;

	/**
	 * ��ù���Ա��Ŀ
	 * 
	 * @param searchProperty
	 * @return
	 * @throws PlatformException
	 */
	// public abstract int getManagerNum(List searchProperty) throws
	// PlatformException;
	public abstract int getManagerNum(String id, String login_id, String name)
			throws PlatformException;

	public abstract int getManagerNum(String id, String login_id, String name,
			String gender) throws PlatformException;

	public abstract int getNoneExistManagerNum(String id, String login_id,
			String name, String classId) throws PlatformException;

	/**
	 * ��ù���Ա����
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract Manager getManager(String id) throws PlatformException;

	/**
	 * ��ù���Ա�б�
	 * 
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	// public abstract List getManagers(List searchProperty,List orderProperty)
	// throws PlatformException;
	public abstract List getManagers(Page page, String id, String login_id,
			String name) throws PlatformException;

	public abstract List getManagers(Page page, String id, String login_id,
			String name, String gender) throws PlatformException;

	public abstract List getNoneExistManagers(Page page, String id,
			String login_id, String name, String classId)
			throws PlatformException;

	/**
	 * ��ҳ��ù���Ա�б�
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	// public abstract List getManagers(Page page,List searchProperty,List
	// orderProperty)throws PlatformException;
	/**
	 * �ı����Ա����Ȩ����
	 * 
	 * @param manager_id
	 * @param group_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int changeManagerGroup(String manager_id, String group_id)
			throws PlatformException;

	/**
	 * ��bһ��Student����
	 * 
	 * @param
	 * @return Student
	 * @throws PlatformException
	 */
	public abstract Student createStudent() throws PlatformException;

	/**
	 * ��bһ��Teacher����
	 * 
	 * @param
	 * @return Teacher
	 * @throws PlatformException
	 */
	public abstract Teacher createTeacher() throws PlatformException;

	/**
	 * ��ӷ�վ����Ա
	 * 
	 * @param login_id
	 * @param name
	 * @param password
	 * @param type
	 * @param status
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addSiteManager(String login_id, String name,
			String password, String site_id, String status)
			throws PlatformException;

	/**
	 * ɾ���վ����Ա
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteSiteManager(String id) throws PlatformException;

	/**
	 * ��÷�վ����Ա��Ŀ
	 * 
	 * @param searchProperty
	 * @return
	 * @throws PlatformException
	 */
	// public abstract int getSiteManagerNum(List searchProperty) throws
	// PlatformException;
	public abstract int getSiteManagerNum(String id, String login_id,
			String name, String site_id) throws PlatformException;

	public abstract int getSiteManagerNum(String id, String login_id,
			String name, String site_id, String sex) throws PlatformException;

	public abstract int getNoneExistSiteManagerNum(String id, String login_id,
			String name, String site_id, String classId)
			throws PlatformException;

	/**
	 * ��÷�վ����Ա����
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract SiteManager getSiteManager(String id)
			throws PlatformException;

	/**
	 * ��÷�վ����Ա�б�
	 * 
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 */
	// public abstract List getSiteManagers(List searchProperty,List
	// orderProperty) throws PlatformException;
	public abstract List getSiteManagers(Page page, String id, String login_id,
			String name, String site_id) throws PlatformException;

	public abstract List getSiteManagers(Page page, String id, String login_id,
			String name, String site_id, String gender)
			throws PlatformException;

	public abstract List getNoneExistSiteManagers(Page page, String id,
			String login_id, String name, String site_id, String classId)
			throws PlatformException;

	/**
	 * ��ҳ��÷�վ����Ա�б�
	 * 
	 * @param page
	 * @param searchProperty
	 * @param orderProperty
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	// public abstract List getSiteManagers(Page page,List searchProperty,List
	// orderProperty)throws PlatformException;
	public abstract String getNextRegNo(String reg_no) throws PlatformException;

	/**
	 * ��ѧ��ע��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public abstract void regNewStudentBatch(String studentId[], String gradeId)
			throws PlatformException;

	/**
	 * ע���ʱ��������е����֤����
	 * 
	 * @param studentId
	 * @param gradeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract String[] checkExistRegIdCard(String studentId[],
			String gradeId) throws PlatformException;

	public abstract void unRegNewStudentBatch(String[] studentId)
			throws PlatformException;

	/**
	 * ������ʦ�б�
	 * 
	 * @return ������ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getSiteTeachers(Page page, String name, String cardno,
			String gender, String isConfirm, String siteId)
			throws PlatformException;

	public abstract int getSiteTeachersNum(String name, String cardno,
			String gender, String isConfirm, String siteId)
			throws PlatformException;

	/**
	 * ��վָ����ʦ �б�
	 * 
	 * @param page
	 * @param name
	 * @param cardno
	 * @param gender
	 * @param type
	 * @param isConfirm
	 * @param siteId
	 * @param pici
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSiteTeachers(Page page, String name, String cardno,
			String gender, String type, String isConfirm, String siteId,
			String pici) throws PlatformException;
	/**
	 * ��վָ����ʦ �б�
	 * 
	 * @param page
	 * @param name
	 * @param cardno
	 * @param gender
	 * @param type
	 * @param isConfirm
	 * @param siteId
	 * @param pici
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSiteTeachers2(Page page, String name, String cardno,
			String gender, String type, String isConfirm, String siteId,
			String pici) throws PlatformException;

	/**
	 * �õ���ָ��ѧ��ķ�վ��ʦ�б�.
	 */
	public abstract List getAppointStudentSiteTeachers(Page page, String name,
			String cardno, String gender, String type, String isConfirm,
			String siteId, String pici,String semesterId) throws PlatformException;
	public abstract List getAppointStudentSiteTeachers2(Page page, String name,
			String cardno, String gender, String type, String isConfirm,
			String siteId, String pici,String semesterId) throws PlatformException;

	/**
	 * 
	 * @param name
	 * @param cardno
	 * @param gender
	 * @param type
	 * @param isConfirm
	 * @param siteId
	 * @param pici
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getSiteTeachersNum(String name, String cardno,
			String gender, String type, String isConfirm, String siteId,
			String pici) throws PlatformException;

	/**
	 * �õ���ָ��ѧ��ķ�վ��ʦ����;
	 */
	public abstract int getAppointStudentSiteTeachersNum(String name,
			String cardno, String gender, String type, String isConfirm,
			String siteId, String pici,String semesterId) throws PlatformException;
	public abstract int getAppointStudentSiteTeachersNum2(String name,
			String cardno, String gender, String type, String isConfirm,
			String siteId, String pici,String semesterId) throws PlatformException;

	/**
	 * ������״̬
	 * 
	 * @param pageIds
	 *            ҳ�������е�id
	 * @param selectedIds
	 *            ҳ��������ѡ�е�id
	 * @throws PlatformException
	 */
	public abstract void confirmSiteTeachers(String[] selectedIds,
			String[] notes) throws PlatformException;

	public abstract void confirmSiteTeachers(String[] selectedIds)
			throws PlatformException;

	public abstract void rejectSiteTeachers(String[] selectedIds, String[] notes)
			throws PlatformException;

	public abstract void deleteSiteTeachers(String[] selectedIds)
			throws PlatformException;

	public abstract SiteTeacher getSiteTeacher(String id)
			throws PlatformException;

	public abstract List getRegStat() throws PlatformException;

	public abstract HashMap getStudentStatByEduType(String siteId)
			throws PlatformException;

	public abstract HashMap getStudentStatByEduType(String siteId,
			String gradeId) throws PlatformException;

	public abstract HashMap getStudentStatByGrade(String siteId, String gradeId)
			throws PlatformException;

	/**
	 * �ϴ���Ƭ
	 */
	public abstract int uploadImage(String card_no, String filename)
			throws PlatformException, NoRightException;

	/**
	 * ���ϴ���Ƭ
	 */
	public abstract int uploadBatchImage(String card_no, String filename)
			throws PlatformException, NoRightException;

	public abstract int isExistGradeEduTypeMajor(String teacherId)
			throws PlatformException;
}
