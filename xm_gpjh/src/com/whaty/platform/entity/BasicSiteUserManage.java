/**
 * 
 */
package com.whaty.platform.entity;

/**
 * @author wq
 * 
 */

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

public abstract class BasicSiteUserManage {
	/** Creates a new instance of BasicSiteUserManage */
	public BasicSiteUserManage() {
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
	public abstract int updateSiteManagerPwd(String sso_id, String old_pwd,
			String new_pwd) throws PlatformException;

	/**
	 * ���ѧ����
	 * 
	 * @param searchProperties
	 * @return
	 * @throws PlatformException
	 */
	// public abstract int getStudentsNum(List searchProperties) throws
	// PlatformException;
	public abstract int getStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract int getStudentsNum(String id, String reg_no, String name,
			String id_card, String phone, String major_id, String edu_type_id,
			String grade_id) throws PlatformException;

	public abstract int getStudentsNum(String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException;

	public abstract int getStudentsNum(String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String degree_status) throws PlatformException;

	/**
	 * ��ҳ���ѧ���б�
	 * 
	 * @param page
	 * @param searchProperties
	 * @param orderProperties
	 * @return
	 * @throws PlatformException
	 */
	// public abstract List getStudents(Page page,List searchProperties,List
	// orderProperties) throws PlatformException;
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

	public abstract List getStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;

	public abstract List getStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk)
			throws PlatformException;

	public abstract List getStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String degree_status) throws PlatformException;

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
	 * ��Ӹ�����ʦ
	 * 
	 * @param teacherList
	 * @throws PlatformException
	 */
	public abstract void addSiteTeacherBatch(List teacherList)
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

	public abstract int updateStudentInfo(String studentId, String name,
			String password, String email, HumanNormalInfo normalInfo)
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
	 * ���aid�õ���ʦ����
	 * 
	 * @param tid
	 * @return ��ʦ����
	 * @throws NoRightException
	 */
	public abstract Teacher getTeacher(String tid) throws PlatformException;

	public abstract SiteTeacher getSiteTeacher(String tid)
			throws PlatformException;

	/**
	 * ���aidɾ���ʦ����
	 * 
	 * @param tid
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int deleteTeacher(String tid) throws PlatformException;

	public abstract int deleteSiteTeacher(String tid) throws PlatformException;
	
	public abstract int delSiteTeacherLimit(String student_id)  throws PlatformException;

	/**
	 * �жϸ÷�վ��ʦ�Ƿ��ѱ�ָ����ָ��ѧ�� lwx 2008-06-05
	 * 
	 * @param tid
	 * @return
	 * @throws PlatformException
	 */
	public abstract int isHaveTutorStudent(String tid) throws PlatformException;

	/**
	 * ��SsoUser����ӽ�ʦ
	 * 
	 * @param ssoUserList
	 * @throws PlatformException
	 */
	public abstract void addTeacherFormSso(List ssoUserList)
			throws PlatformException;

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
	 * ��ӽ�ʦ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 */
	public abstract int addSiteTeacher(String name, String password,
			String email, HumanNormalInfo normalInfo,
			TeacherEduInfo teachereduInfo, HumanPlatformInfo platformInfo,
			RedundanceData redundace) throws PlatformException;

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

	public abstract List getSiteTeachers(Page page) throws PlatformException;

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

	public abstract List getSiteTeachers(Page page, String search_type,
			String search_value, String isConfirm) throws PlatformException;

	/**
	 * ��ʦ�б�
	 * 
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getTeachers() throws PlatformException;

	public abstract List getSiteTeachers() throws PlatformException;

	/**
	 * ��ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	public abstract int getTeachersNum() throws PlatformException;

	public abstract int getSiteTeachersNum() throws PlatformException;

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

	public abstract int getSiteTeachersNum(String search_type,
			String search_value, String isConfirm) throws PlatformException;

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
	 * ��bһ��Teacher����
	 * 
	 * @param
	 * @return Teacher
	 * @throws PlatformException
	 */
	public abstract SiteTeacher createSiteTeacher() throws PlatformException;

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
			String name, String site_id, String gender)
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

	/**
	 * ��ȡ����ѧ��
	 * 
	 * @param page
	 * @param id
	 * @param reg_no
	 * @param name
	 * @param id_card
	 * @param phone
	 * @param major_id
	 * @param edu_type_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getAllStudents(Page page, String id, String reg_no,
			String name, String id_card, String phone, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;;

	public abstract HashMap getStudentStatByEduType(String siteId)
			throws PlatformException;

	public abstract HashMap getStudentStatByEduType(String siteId,
			String gradeId) throws PlatformException;

	public abstract HashMap getStudentStatByGrade(String siteId, String gradeId)
			throws PlatformException;

	/**
	 * ��ʦ�б�
	 * 
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getTeachers(Page page, String name, String cardno,
			String gender, String teacher_level) throws PlatformException;

	/**
	 * ��ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	public abstract int getTeachersNum(String name, String cardno,
			String gender, String teacher_level) throws PlatformException;

	/**
	 * ��ʦ�б�
	 * 
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getSiteTeachers(Page page, String name, String cardno,
			String gender, String isConfirm) throws PlatformException;

	/**
	 * ��ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	public abstract int getSiteTeachersNum(String name, String cardno,
			String gender, String isConfirm) throws PlatformException;

	/**
	 * ָ����ʦ�б�
	 * 
	 * @return ��ʦ�б�
	 * @throws PlatformException
	 */
	public abstract List getSiteTeachers(Page page, String name, String cardno,
			String gender, String type, String isConfirm)
			throws PlatformException;

	/**
	 * ָ�� ��ʦ��
	 * 
	 * @return ��ʦ��
	 * @throws PlatformException
	 */
	public abstract int getSiteTeachersNum(String name, String cardno,
			String gender, String type, String isConfirm)
			throws PlatformException;

	public abstract int addSiteAdmin(String login_id, String name,
			String password, String mobilePhone, String status, String type,
			String site_id, String group_id) throws PlatformException;

	public abstract int addSiteAdmin(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateSiteAdmin(HttpServletRequest request)
			throws PlatformException;

	public abstract int getSiteManagerListNum(String siteId, String groupId)
			throws PlatformException;

	public abstract List getSiteManagerList(Page page, String siteId,
			String groupId) throws PlatformException;

	public abstract List getSiteManagerList(String siteId, String groupId)
			throws PlatformException;

	public abstract int updateSiteAdmin(String login_id, String name,
			String password, String mobilephone, String status, String site_id)
			throws PlatformException;

	public abstract int updateStudentPassword(String id, String new_pwd)
			throws PlatformException;

	public abstract List getRegStudents(Page page, String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String batchId, String status) throws PlatformException;

	public abstract int getRegStudentsNum(String reg_no, String name,
			String id_card, String card_type, String site_id, String major_id,
			String edu_type_id, String grade_id, String gender, String folk,
			String batchId, String status) throws PlatformException;

	/**
	 * ��������б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitBatches(Page page) throws PlatformException;

	/**
	 * ע��ѧ����
	 * 
	 * @param searchProperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRegStudentsNum(List searchProperties)
			throws PlatformException;

	/**
	 * ע�����ͳ��
	 * 
	 * @return ע�����ͳ��
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRegisterStat(Page page, String batchId,
			String siteId, String majorId, String eduTypeId)
			throws PlatformException;

	/**
	 * ע�����ͳ��
	 * 
	 * @return ע�����ͳ��
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRegisterStatNum(String batchId, String siteId,
			String majorId, String eduTypeId) throws PlatformException,
			PlatformException;

	/**
	 * ��վ������ʦ�б�
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

	public abstract List getStudentsFee(Page page, String id, String reg_no,
			String name, String id_card, String phone, String site_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

 
	public abstract int getStudentsFeeNum(String id, String reg_no, String name,
			String id_card, String phone, String site_id, String major_id,
			String edu_type_id, String grade_id) throws PlatformException;
	/**
	 * ��ѯѧ���ѧ�ѱ�׼��Ϣ�б��¼����
	 * 
	 * @param searchProperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStudentsFeeNum(List searchProperties)
			throws PlatformException;

	/**
	 * ��ѯѧ���ѧ�ѱ�׼��Ϣ�б�
	 * 
	 * @param page
	 * @param searchProperties
	 * @param orderProperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentsFee(Page page, List searchProperties,
			List orderProperties) throws PlatformException;
	
	public abstract List getGraduateStudent(Page page,String name,String reg_no,String site,String edutype_id,String major_id,String grade_id,String teacher_id);
	
	public abstract int getGraduateStudentNum(String name,String reg_no,String site,String edutype_id,String major_id,String grade_id,String teacher_id);

}
