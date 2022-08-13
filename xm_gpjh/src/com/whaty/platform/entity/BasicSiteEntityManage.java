/**
 * 
 */
package com.whaty.platform.entity;

/**
 * @author wq
 * 
 */
import java.sql.SQLException;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.fee.ChargeLevelManage;
import com.whaty.platform.entity.fee.FeeManage;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.info.InfoManage;
import com.whaty.platform.info.Template;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.util.Page;

public abstract class BasicSiteEntityManage {
	/** Creates a new instance of BasicEntityManage */
	public BasicSiteEntityManage() {
	}

	public abstract FeeManage getFeeManage();

	public abstract ChargeLevelManage getChargeLevelManage();

	/**
	 * @param id
	 * @param name
	 *            ����רҵ����
	 * @return רҵ����
	 * @throws NoRightException
	 */
	public abstract Major creatMajor(java.lang.String id, java.lang.String name)
			throws NoRightException;

	/**
	 * ���aid�õ�רҵ����
	 * 
	 * @param aid
	 * @return רҵ����
	 * @throws NoRightException
	 */
	public abstract Major getMajor(java.lang.String aid)
			throws NoRightException;

	/**
	 * @param id
	 * @param name
	 *            ������ζ���
	 * @return ��ζ���
	 * @throws NoRightException
	 */
	public abstract EduType creatEduType(java.lang.String id,
			java.lang.String name) throws NoRightException;

	/**
	 * ���aid�õ���ζ���
	 * 
	 * @param aid
	 * @return ��ζ���
	 * @throws NoRightException
	 */
	public abstract EduType getEduType(java.lang.String aid)
			throws NoRightException;

	/**
	 * �γ��б�
	 * 
	 * @return �γ��б�
	 * @throws NoRightException
	 */
	public abstract List getCourses() throws NoRightException;

	/**
	 * �γ��б�
	 * 
	 * @return �γ��б�
	 * @throws NoRightException
	 */
	public abstract Course getCourse(String course_id) throws NoRightException;

	/**
	 * ��ӿγ�
	 * 
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void addCourseBatch(List courseList)
			throws PlatformException;

	/**
	 * @param id
	 * @param name
	 *            �����꼶����
	 * @return �꼶����
	 * @throws NoRightException
	 */
	public abstract Grade creatGrade(java.lang.String id, java.lang.String name)
			throws NoRightException;

	/**
	 * ���aid�õ��꼶����
	 * 
	 * @param aid
	 * @return �꼶����
	 * @throws NoRightException
	 */
	public abstract Grade getGrade(java.lang.String aid)
			throws NoRightException;

	/**
	 * @param id
	 * @param name
	 *            ������ѧվ����
	 * @return ��ѧվ����
	 * @throws NoRightException
	 */
	public abstract Site creatSite(java.lang.String id, java.lang.String name)
			throws NoRightException;

	/**
	 * ���aid�õ���ѧվ����
	 * 
	 * @param aid
	 * @return ��ѧվ����
	 * @throws NoRightException
	 */
	public abstract Site getSite(java.lang.String aid) throws NoRightException;

	/**
	 * @param id
	 * @param name
	 *            ����ѧ�ڶ���
	 * @return ѧ�ڶ���
	 * @throws NoRightException
	 */
	public abstract Semester creatSemester(java.lang.String id,
			java.lang.String name) throws NoRightException;

	/**
	 * ���aid�õ�ѧ�ڶ���
	 * 
	 * @param aid
	 * @return ѧ�ڶ���
	 * @throws NoRightException
	 */
	public abstract Semester getSemester(java.lang.String aid)
			throws NoRightException;

	/**
	 * @param id
	 * @param name
	 *            ����ģ�����
	 * @return ģ�����
	 * @throws NoRightException
	 */
	public abstract Template creatTemplate(java.lang.String id,
			java.lang.String name) throws NoRightException;

	/**
	 * ���aid�õ�ģ�����
	 * 
	 * @param aid
	 * @return ģ�����
	 * @throws NoRightException
	 */
	public abstract Template getTemplate(java.lang.String aid)
			throws NoRightException;

	/**
	 * ��bһ��Course����
	 * 
	 * @param
	 * @return Course
	 * @throws PlatformException
	 */
	public abstract Course createCourse() throws PlatformException;

	/**
	 * @param id
	 * @param name
	 *            ����ѧԺ����
	 * @return ѧԺ����
	 * @throws NoRightException
	 */
	public abstract Dep creatDep(java.lang.String id, java.lang.String name)
			throws NoRightException;

	/**
	 * ���aid�õ�ѧԺ����
	 * 
	 * @param aid
	 * @return ѧԺ����
	 * @throws NoRightException
	 */
	public abstract Dep getDep(java.lang.String aid) throws NoRightException;

	/**
	 * ɾ��Ժϵ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteDep(String id) throws NoRightException,
			PlatformException;

	/**
	 * ɾ����
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int deleteEduType(String id) throws NoRightException;

	/**
	 * ɾ��רҵ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteMajor(String id) throws NoRightException,
			PlatformException;

	/**
	 * ɾ���꼶
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteGrade(String id) throws NoRightException,
			PlatformException;

	/**
	 * ɾ���꼶
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int deleteSite(String id) throws NoRightException;

	/**
	 * ɾ��ѧ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteSemester(String id) throws NoRightException,
			PlatformException;

	/**
	 * ɾ��ģ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int deleteTemplate(String id) throws NoRightException;

	/**
	 * ɾ��ѧ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int deleteStudent(String id) throws NoRightException,
			PlatformException;

	/**
	 * ���ѧԺ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addDep(String id, String name, String forshort,
			String note) throws NoRightException, PlatformException;

	/**
	 * ��Ӳ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int addEduType(String id, String name, String forshort,
			String introduction) throws NoRightException;

	/**
	 * ����꼶
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int addGrade(String name, String begin_date)
			throws NoRightException;

	/**
	 * ���רҵ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addMajor(String id, String name, String major_alias,
			String major_link, String note, String dep_id)
			throws NoRightException, PlatformException;

	/**
	 * ��ӽ�ѧվ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addSite(String id, String name, String address,
			String email, String fax, String found_date, String linkman,
			String manager, String phone, String zip_code, String note)
			throws NoRightException, PlatformException;

	/**
	 * ���ѧ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int addSemester(String name, String start_date,
			String end_date, String start_elective, String end_elective)
			throws NoRightException;

	/**
	 * ���ģ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int addTemplate(String id, String name, String content,
			String type, String pub_type, String note, String mark)
			throws NoRightException;

	/**
	 * �������ƻ�
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int addRecruitPlan(String edu_type_id, String major_id,
			String recruitnum, String site_id) throws NoRightException,
			PlatformException;

	/**
	 * ���ѧ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int addRecruitStudent(String name, String password,
			String gender, String folk, String birthday, String zzmm,
			String edu, String site_id, String edutype_id, String major_id,
			String card_type, String card_no, String hometown, String[] email,
			String postalcode, String address, String[] mobilephone,
			String[] phone, String considertype, String status)
			throws NoRightException, PlatformException;

	public abstract int addRecruitStudent(String name, String password,
			String gender, String folk, String birthday, String zzmm,
			String edu, String site_id, String edutype_id, String major_id,
			String card_type, String card_no, String hometown, String[] email,
			String postalcode, String address, String[] mobilephone,
			String[] phone, String considertype, String status,
			String premajor_status, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws NoRightException, PlatformException;

	/**
	 * �޸�ѧԺ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateDep(String id, String name, String forshort,
			String note) throws NoRightException;

	/**
	 * �޸Ĳ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateEduType(String id, String name, String forshort,
			String introduction) throws NoRightException, PlatformException;

	/**
	 * �޸��꼶
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateGrade(String id, String name, String begin_date)
			throws NoRightException;

	/**
	 * �޸�רҵ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateMajor(String id, String name, String major_alias,
			String major_link, String note) throws NoRightException;

	/**
	 * �޸Ľ�ѧվ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateSite(String id, String name, String address,
			String email, String fax, String found_date, String linkman,
			String manager, String phone, String zip_code, String website,
			String note) throws NoRightException;

	public abstract int updateSite(String id, String name, String address,
			String email, String fax, String found_date, String linkman,
			String manager, String phone, String zip_code, String website,
			String note,String site_company,String site_type,String corporation,String first_student_date) throws NoRightException;

	/**
	 * �޸�ѧ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateSemester(String id, String name,
			String start_date, String end_date, String start_elective,
			String end_elective) throws NoRightException;

	/**
	 * �޸�ģ��
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws NoRightException
	 */
	public abstract int updateTemplate(String id, String name, String content,
			String type, String pub_type, String note, String mark)
			throws NoRightException;

	/**
	 * ѧԺ�б�
	 * 
	 * @return ѧԺ�б�
	 * @throws NoRightException
	 */
	public abstract List getDeps() throws NoRightException;

	/**
	 * ѧԺ�б�
	 * 
	 * @return ѧԺ�б�
	 * @throws NoRightException
	 */
	public abstract List getDeps(Page page) throws NoRightException;

	/**
	 * ����б�
	 * 
	 * @return ����б�
	 * @throws NoRightException
	 */
	public abstract List getEduTypes() throws NoRightException;

	/**
	 * ����б�
	 * 
	 * @return ����б�
	 * @throws NoRightException
	 */
	public abstract List getEduTypes(Page page) throws NoRightException;

	/**
	 * @param page
	 * @param sp
	 * @return
	 */
	// public abstract List getEduTypes(Page page, List searchproperty,List
	// orderproperty) throws NoRightException;
	/**
	 * ���ѧԺ�б�
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return ѧԺ�б�
	 */
	public abstract List getDeps(Page page, List searchproperty,
			List orderproperty) throws NoRightException;

	/**
	 * רҵ�б�
	 * 
	 * @return רҵ�б�
	 * @throws NoRightException
	 */
	public abstract List getMajors() throws NoRightException;

	/**
	 * רҵ�б�
	 * 
	 * @return רҵ�б�
	 * @throws NoRightException
	 */
	public abstract List getMajors(Page page) throws NoRightException;

	/**
	 * רҵ�б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return רҵ�б�
	 * @throws NoRightException
	 */
	public abstract List getMajors(Page page, List searchproperty,
			List orderproperty) throws NoRightException;

	/**
	 * �꼶�б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return �꼶�б�
	 * @throws NoRightException
	 */
	// public abstract List getGrades(Page page, List searchproperty,List
	// orderproperty) throws NoRightException;
	public abstract List getGrades(Page page, String name, String start_date,
			String end_date) throws PlatformException;

	/**
	 * �꼶�б�
	 * 
	 * @return �꼶�б�
	 * @throws NoRightException
	 */
	public abstract List getGrades() throws NoRightException;

	/**
	 * �꼶�б�
	 * 
	 * @return �꼶�б�
	 * @throws NoRightException
	 */
	public abstract List getGrades(Page page) throws NoRightException;

	/**
	 * ��ѧվ�б�
	 * 
	 * @return ��ѧվ�б�
	 * @throws NoRightException
	 */
	public abstract List getSites() throws NoRightException;

	/**
	 * ѧ���б�
	 * 
	 * @return ѧ���б�
	 * @throws NoRightException
	 */
	public abstract List getSemesters() throws NoRightException;

	/**
	 * �ѧ���б�
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract List getActiveSemesters() throws NoRightException;

	/**
	 * �ѧ����
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract int getActiveSemestersNum() throws NoRightException;

	/**
	 * ��ѧվ�б�
	 * 
	 * @return ��ѧվ�б�
	 * @throws NoRightException
	 */
	public abstract List getSites(Page page) throws NoRightException;

	/**
	 * ѧ���б�
	 * 
	 * @return ѧ���б�
	 * @throws NoRightException
	 */
	public abstract List getSemesters(Page page) throws NoRightException;

	/**
	 * ��ѧվ�б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return ��ѧվ�б�
	 * @throws NoRightException
	 */
	// public abstract List getSites(Page page, List searchProperty, List
	// orderProperty) throws NoRightException;
	public abstract List getSites(Page page, String result)
			throws PlatformException;

	/**
	 * ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return ѧ���б�
	 * @throws NoRightException
	 */
	public abstract List getSemesters(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/**
	 * ģ���б�
	 * 
	 * @return ģ���б�
	 * @throws NoRightException
	 */
	public abstract List getTemplates() throws NoRightException;

	/**
	 * ģ���б�
	 * 
	 * @return ģ���б�
	 * @throws NoRightException
	 */
	public abstract List getTemplates(Page page) throws NoRightException;

	/**
	 * ģ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return ģ���б�
	 * @throws NoRightException
	 */
	public abstract List getTemplates(Page page, List searchProperty,
			List orderProperty) throws NoRightException;

	/**
	 * ѧԺ��
	 * 
	 * @return ѧԺ��
	 * @throws NoRightException
	 */
	public abstract int getDepsNum() throws NoRightException;

	/**
	 * �����
	 * 
	 * @return �����
	 * @throws NoRightException
	 */
	public abstract int getEduTypesNum() throws NoRightException;

	/**
	 * רҵ��
	 * 
	 * @return רҵ��
	 * @throws NoRightException
	 */
	public abstract int getMajorsNum() throws NoRightException;

	/**
	 * �꼶��
	 * 
	 * @return �꼶��
	 * @throws NoRightException
	 */
	public abstract int getGradesNum() throws NoRightException;

	/**
	 * ��ѧվ��
	 * 
	 * @return ��ѧվ��
	 * @throws NoRightException
	 */
	public abstract int getSitesNum() throws NoRightException;

	/**
	 * ѧ����
	 * 
	 * @return ѧ����
	 * @throws NoRightException
	 */
	public abstract int getSemestersNum() throws NoRightException;

	/**
	 * ģ����
	 * 
	 * @return ģ����
	 * @throws NoRightException
	 */
	public abstract int getTemplatesNum() throws NoRightException;

	/**
	 * ѧԺ��
	 * 
	 * @return ѧԺ��
	 * @throws NoRightException
	 */
	// public abstract int getDepsNum(List searchProperty) throws
	// NoRightException;
	/**
	 * �����
	 * 
	 * @return �����
	 * @throws NoRightException
	 */
	// public abstract int getEduTypesNum(List searchProperty) throws
	// NoRightException;
	/**
	 * רҵ��
	 * 
	 * @return רҵ��
	 * @throws NoRightException
	 */
	// public abstract int getMajorsNum(List searchProperty) throws
	// NoRightException;
	/**
	 * �꼶��
	 * 
	 * @return �꼶��
	 * @throws NoRightException
	 */
	// public abstract int getGradesNum(List searchProperty) throws
	// NoRightException;
	public abstract int getGradesNum(String name, String start_date,
			String end_date) throws PlatformException;

	/**
	 * ��ѧվ��
	 * 
	 * @return ��ѧվ��
	 * @throws NoRightException
	 */
	// public abstract int getSitesNum(List searchProperty) throws
	// NoRightException;
	public abstract int getSitesNum(String result) throws PlatformException;

	/**
	 * ���ѧ�ڼ���״̬
	 * 
	 * @return 1 Ϊ�ɹ��� 0 Ϊʧ��
	 */
	public abstract int reverseSemesterActive(String semesterId);

	/**
	 * ���aid�õ�רҵ�¿γ̵���Ŀ
	 * 
	 * @param aid
	 * @return �γ���Ŀ
	 * @throws NoRightException
	 */
	public abstract int getMajorCoursesNum(java.lang.String aid)
			throws NoRightException;

	/**
	 * ���page��רҵaid�õ��γ��б�
	 * 
	 * @param page
	 * @param aid
	 * @return �γ��б�List
	 * @throws NoRightException
	 */
	public abstract List getMajorCourses(Page page, java.lang.String aid)
			throws NoRightException;

	/**
	 * ���רҵaid�ı��רҵ��״̬
	 * 
	 * @param aid
	 * @return 1 Ϊ�ɹ��� 0 Ϊʧ��
	 * @throws NoRightException
	 * @throws SQLException
	 */
	public abstract int changeMajorStatus(java.lang.String aid)
			throws NoRightException, SQLException;

	public abstract InfoManagerPriv getInfoManagerPriv(String managerId)
			throws PlatformException;

	public abstract InfoManagerPriv getInfoManagerPriv(String managerId,
			String newsTypeId) throws PlatformException;

	public abstract InfoManage getInfoManage(InfoManagerPriv infoManagerPriv)
			throws PlatformException;

	public abstract SmsManage getSmsManage() throws PlatformException;

	/**
	 * ����б�
	 * 
	 * @return ����б�
	 * @throws NoRightException
	 */
	public abstract List getAllEduTypes() throws NoRightException;

	/**
	 * ȫ��רҵ�б?����Σ�
	 * 
	 * @return ȫ��רҵ�б?����Σ�
	 * @throws NoRightException
	 */
	public abstract List getAllMajors() throws NoRightException;

	/**
	 * �꼶�б�
	 * 
	 * @return �꼶�б�
	 * @throws NoRightException
	 */
	public abstract List getAllGrades() throws NoRightException;

	/**
	 * ���н�ѧվ�б�
	 * 
	 * @return ��ѧվ�б�
	 * @throws NoRightException
	 */
	public abstract List getAllSites() throws NoRightException;

	public abstract List getInfoList(Page page) throws PlatformException;

	/**
	 * ��ҳ��ȡĳ���������͵�����
	 * 
	 * @param page
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getInfoList(Page page, String newsTypeId)
			throws PlatformException;

	public abstract int getInfoListNum() throws PlatformException;

	/**
	 * ָ���������͵���������
	 * 
	 * @param newsTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getInfoListNum(String newsTypeId)
			throws PlatformException;

	/**
	 * ����ƻ�����б�
	 * 
	 * @return ����ƻ�����б�
	 * @throws NoRightException
	 */
	public abstract List getPlanEduTypes(String batchId, String siteId)
			throws NoRightException;

	/**
	 * ����ƻ�רҵ�б�
	 * 
	 * @return ����ƻ�רҵ�б�
	 * @throws NoRightException
	 */
	public abstract List getPlanMajors(String batchId, String siteId,
			String eduType) throws NoRightException;

	/**
	 * ���entity��������Ҫ�õ�WhatyEditor�����ò���
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
//	public abstract WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException;

	/**
	 * �޸�������Ϣ
	 * 
	 * @return �޸�������Ϣ
	 * @throws NoRightException
	 */
	public abstract int updateRecruitStudentInfo(String id, String name,
			String password, String gender, String folk, String birthday,
			String zzmm, String edu, String site_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws PlatformException, NoRightException;

	public abstract Student getStudentInfo(String studentId)
			throws PlatformException;
	
	public abstract List getGraduateStudent(Page page,String name,String reg_no,String site,String edutype_id,String major_id,String grade_id,String teacher_id);
	
	public abstract int getGraduateStudentNum(String name,String reg_no,String site,String edutype_id,String major_id,String grade_id,String teacher_id);
}
