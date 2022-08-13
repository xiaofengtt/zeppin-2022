package com.whaty.platform.entity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.campus.CampusManage;
import com.whaty.platform.campus.CampusNormalManage;
import com.whaty.platform.campus.user.CampusInfoManagerPriv;
import com.whaty.platform.campus.user.CampusManagerPriv;
import com.whaty.platform.entity.basic.Course;
import com.whaty.platform.entity.basic.Dep;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;
import com.whaty.platform.entity.basic.Semester;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.basic.SiteDiqu;
import com.whaty.platform.entity.basic.TeachBook;
import com.whaty.platform.entity.fee.ChargeLevelManage;
import com.whaty.platform.entity.fee.FeeManage;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.info.InfoManage;
import com.whaty.platform.info.Template;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.leaveword.LeaveWordManage;
import com.whaty.platform.leaveword.LeaveWordNormalManage;
import com.whaty.platform.leaveword.user.LeaveWordManagerPriv;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;
import com.whaty.platform.util.Page;

/**
 * 该类描述了管理员管理教务基本数据的功能
 * 
 * @author chenjian
 * 
 */
public abstract class BasicEntityManage {

	/** Creates a new instance of BasicEntityManage */
	public BasicEntityManage() {
	}

	/**
	 * @param id
	 * @param name
	 *            创建专业对象
	 * @return 专业对象
	 * @throws NoRightException
	 */
	public abstract Major creatMajor(java.lang.String id, java.lang.String name)
			throws PlatformException;

	/**
	 * 根据aid得到专业对象
	 * 
	 * @param aid
	 * @return 专业对象
	 * @throws PlatformException
	 */
	public abstract Major getMajor(java.lang.String aid)
			throws PlatformException;

	/**
	 * @param id
	 * @param name
	 *            创建层次对象
	 * @return 层次对象
	 * @throws PlatformException
	 */
	public abstract EduType creatEduType(java.lang.String id,
			java.lang.String name) throws PlatformException;

	/**
	 * 根据aid得到层次对象
	 * 
	 * @param aid
	 * @return 层次对象
	 * @throws PlatformException
	 */
	public abstract EduType getEduType(java.lang.String aid)
			throws PlatformException;

	/**
	 * 添加课程
	 * 
	 * @param courseList
	 * @throws PlatformException
	 */
	public abstract void addCourseBatch(List courseList)
			throws PlatformException;

	/**
	 * 批量导入教材
	 * 
	 * @param srcFile
	 *            教材的批量信息
	 * @throws PlatformException
	 */
	public abstract void addBookBatch(String srcFile) throws PlatformException;

	/**
	 * @param id
	 * @param name
	 *            创建年级对象
	 * @return 年级对象
	 * @throws PlatformException
	 */
	public abstract Grade creatGrade(java.lang.String id, java.lang.String name)
			throws PlatformException;

	/**
	 * 根据aid得到年级对象
	 * 
	 * @param aid
	 * @return 年级对象
	 * @throws PlatformException
	 */
	public abstract Grade getGrade(java.lang.String aid)
			throws PlatformException;

	/**
	 * @param id
	 * @param name
	 *            创建教学站对象
	 * @return 教学站对象
	 * @throws PlatformException
	 */
	public abstract Site creatSite(java.lang.String id, java.lang.String name)
			throws PlatformException;

	/**
	 * 根据aid得到教学站对象
	 * 
	 * @param aid
	 * @return 教学站对象
	 * @throws PlatformException
	 */
	public abstract Site getSite(java.lang.String aid) throws PlatformException;

	/**
	 * @param id
	 * @param name
	 *            创建学期对象
	 * @return 学期对象
	 * @throws PlatformException
	 */
	public abstract Semester creatSemester(java.lang.String id,
			java.lang.String name) throws PlatformException;

	/**
	 * 根据aid得到学期对象
	 * 
	 * @param aid
	 * @return 学期对象
	 * @throws PlatformException
	 */
	public abstract Semester getSemester(java.lang.String aid)
			throws PlatformException;

	/**
	 * @param id
	 * @param name
	 *            创建模板对象
	 * @return 模板对象
	 * @throws PlatformException
	 */
	public abstract Template creatTemplate(java.lang.String id,
			java.lang.String name) throws PlatformException;

	/**
	 * 根据aid得到模板对象
	 * 
	 * @param aid
	 * @return 模板对象
	 * @throws PlatformException
	 */
	public abstract Template getTemplate(java.lang.String aid)
			throws PlatformException;

	/**
	 * 建立一个Course对象
	 * 
	 * @param
	 * @return Course
	 * @throws PlatformException
	 */
	public abstract Course createCourse() throws PlatformException;

	public abstract Student getStudentInfo(String studentId)
			throws PlatformException;

	/**
	 * @param id
	 * @param name
	 *            创建学院对象
	 * @return 学院对象
	 * @throws PlatformException
	 */
	public abstract Dep creatDep(java.lang.String id, java.lang.String name)
			throws PlatformException;

	/**
	 * 根据aid得到学院对象
	 * 
	 * @param aid
	 * @return 学院对象
	 * @throws PlatformException
	 */
	public abstract Dep getDep(java.lang.String aid) throws PlatformException;

	/**
	 * 删除院系
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteDep(String id) throws PlatformException;

	/**
	 * 删除层次
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int deleteEduType(String id) throws PlatformException;

	/**
	 * 删除专业
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteMajor(String id) throws PlatformException;

	/**
	 * 删除年级
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteGrade(String id) throws PlatformException;

	/**
	 * 删除年级
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int deleteSite(String id) throws PlatformException;

	/**
	 * 删除学期
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteSemester(String id) throws PlatformException;

	/**
	 * 删除模板
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int deleteTemplate(String id) throws PlatformException;

	/**
	 * 添加学院
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addDep(String id, String name, String forshort,
			String note) throws PlatformException;

	/**
	 * 添加层次
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int addEduType(String id, String name, String forshort,
			String introduction) throws PlatformException;

	/**
	 * 添加年级
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int addGrade(String name, String begin_date, String grade_id)
			throws PlatformException;

	/**
	 * 添加专业
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addMajor(String id, String name, String major_alias,
			String major_link, String note, String dep_id)
			throws PlatformException;

	/**
	 * 添加教学站
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addSite(String id, String name, String address,
			String email, String fax, String found_date, String linkman,
			String manager, String phone, String zip_code, String note,
			String URL, String diquId) throws PlatformException;

	public abstract int addSite(String id, String name, String address,
			String email, String fax, String found_date, String linkman,
			String manager, String phone, String zip_code, String note,
			String URL, String diquId,String site_company,String site_type,String corporation,String first_student_date) throws PlatformException;

	
	/**
	 * 添加学期
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int addSemester(String name, String start_date,
			String end_date, String start_elective, String end_elective,
			String start_test, String end_test) throws PlatformException;

	/**
	 * 添加模板
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int addTemplate(String id, String name, String content,
			String type, String pub_type, String note, String mark)
			throws PlatformException;

	/**
	 * 修改学院
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int updateDep(String id, String name, String forshort,
			String note) throws PlatformException;

	/**
	 * 修改层次
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateEduType(String id, String name, String forshort,
			String introduction) throws PlatformException;

	/**
	 * 修改年级
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int updateGrade(String id, String name, String begin_date)
			throws PlatformException;

	/**
	 * 修改专业
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int updateMajor(String id, String name, String major_alias,
			String major_link, String note) throws PlatformException;

	/**
	 * 修改教学站
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int updateSite(String id, String name, String address,
			String email, String fax, String found_date, String linkman,
			String manager, String phone, String zip_code, String webSite,
			String note, String diquId) throws PlatformException;

	public abstract int updateSite(String id, String name, String address,
			String email, String fax, String found_date, String linkman,
			String manager, String phone, String zip_code, String webSite,
			String note, String diquId,String site_company,String site_type,String corporation,String first_student_date) throws PlatformException;

	/**
	 * 修改学期
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int updateSemester(String id, String name,
			String start_date, String end_date, String start_elective,
			String end_elective, String start_test, String end_test)
			throws PlatformException;

	/**
	 * 修改模板
	 * 
	 * @return 1为成功,0为不成功
	 * @throws PlatformException
	 */
	public abstract int updateTemplate(String id, String name, String content,
			String type, String pub_type, String note, String mark)
			throws PlatformException;

	/**
	 * 学院列表
	 * 
	 * @return 学院列表
	 * @throws PlatformException
	 */
	public abstract List getDeps() throws PlatformException;

	/**
	 * 学院列表
	 * 
	 * @return 学院列表
	 * @throws PlatformException
	 */
	public abstract List getDeps(Page page) throws PlatformException;

	/**
	 * 层次列表
	 * 
	 * @return 层次列表
	 * @throws PlatformException
	 */
	public abstract List getAllEduTypes() throws PlatformException;

	/**
	 * 层次列表
	 * 
	 * @return 层次列表
	 * @throws PlatformException
	 */
	public abstract List getEduTypes() throws PlatformException;

	/**
	 * 层次列表
	 * 
	 * @return 层次列表
	 * @throws PlatformException
	 */
	public abstract List getEduTypes(Page page) throws PlatformException;

	/**
	 * @param page
	 * @param sp
	 * @return
	 */
	// public abstract List getEduTypes(Page page, List searchproperty,List
	// orderproperty) throws PlatformException;
	/**
	 * 获得学院列表
	 * 
	 * @param page
	 * @param searchproperty
	 * @param orderproperty
	 * @return 学院列表
	 */
	public abstract List getDeps(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	/**
	 * 全部专业列表（包含公共课）
	 * 
	 * @return 全部专业列表（包含公共课）
	 * @throws PlatformException
	 */
	public abstract List getAllMajors() throws PlatformException;

	/**
	 * 专业列表
	 * 
	 * @return 专业列表
	 * @throws PlatformException
	 */
	public abstract List getMajors() throws PlatformException;

	/**
	 * 专业列表
	 * 
	 * @return 专业列表
	 * @throws PlatformException
	 */
	public abstract List getMajors(Page page) throws PlatformException;

	/**
	 * 专业列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 专业列表
	 * @throws PlatformException
	 */
	public abstract List getMajors(Page page, List searchproperty,
			List orderproperty) throws PlatformException;

	/**
	 * 年级列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 年级列表
	 * @throws PlatformException
	 */
	// public abstract List getGrades(Page page, List searchproperty,List
	// orderproperty) throws PlatformException;
	public abstract List getGrades(Page page, String name, String start_date,
			String end_date) throws PlatformException;

	/**
	 * 年级列表
	 * 
	 * @return 年级列表
	 * @throws PlatformException
	 */
	public abstract List getAllGrades() throws PlatformException;

	/**
	 * 年级列表
	 * 
	 * @return 年级列表
	 * @throws PlatformException
	 */
	public abstract List getGrades() throws PlatformException;

	/**
	 * 年级列表
	 * 
	 * @return 年级列表
	 * @throws PlatformException
	 */
	public abstract List getGrades(Page page) throws PlatformException;

	/**
	 * 所有教学站列表
	 * 
	 * @return 教学站列表
	 * @throws PlatformException
	 */
	public abstract List getAllSites() throws PlatformException;

	/**
	 * 教学站列表
	 * 
	 * @return 教学站列表
	 * @throws PlatformException
	 */
	public abstract List getSites() throws PlatformException;
	/**
	 * 教学站列表
	 * 
	 * @return 教学站列表
	 * @throws PlatformException
	 */
	public abstract Map getSitesMap() throws PlatformException;

	/**
	 * 改变编号为aid的教学站的显示状态
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract int changeSiteShow(java.lang.String aid)
			throws PlatformException;

	/**
	 * 学期列表
	 * 
	 * @return 学期列表
	 * @throws PlatformException
	 */
	public abstract List getSemesters() throws PlatformException;

	/**
	 * 活动学期列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getActiveSemesters() throws PlatformException;

	/**
	 * 活动学期数量
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getActiveSemestersNum() throws PlatformException;

	/**
	 * 课程列表
	 * 
	 * @return 课程列表
	 * @throws PlatformException
	 */
	public abstract List getCourses() throws PlatformException;

	/**
	 * 获取指定的课程
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract Course getCourse(String id) throws PlatformException;

	/**
	 * 教学站列表
	 * 
	 * @return 教学站列表
	 * @throws PlatformException
	 */
	public abstract List getSites(Page page) throws PlatformException;

	/**
	 * 学期列表
	 * 
	 * @return 学期列表
	 * @throws PlatformException
	 */
	public abstract List getSemesters(Page page) throws PlatformException;

	/**
	 * 学期列表
	 * 
	 * @return 学期列表
	 * @throws PlatformException
	 */
	public abstract List getListSemesters(Page page, String name,
			String start_date, String end_date, String start_elective,
			String end_elective, String start_test, String end_test)
			throws PlatformException;

	/**
	 * 教学站列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 教学站列表
	 * @throws PlatformException
	 */
	// public abstract List getSites(Page page, List searchProperty, List
	// orderProperty) throws PlatformException;
	public abstract List getSites(Page page, String result)
			throws PlatformException;

	/**
	 * 教学站列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param id
	 * @param name
	 * @return 教学站列表
	 * @throws PlatformException
	 */
	public abstract List getSites(Page page, String id, String name)
			throws PlatformException;

	public abstract List getSites(String sites) throws PlatformException;

	public abstract List getSitesByDiqu(Page page, String diquId)
			throws PlatformException;

	/**
	 * 层次列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param id
	 * @param name
	 * @return 层次列表
	 * @throws PlatformException
	 */
	public abstract List getEduTypes(Page page, String id, String name)
			throws PlatformException;

	/**
	 * 专业列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param id
	 * @param name
	 * @return 专业列表
	 * @throws PlatformException
	 */
	public abstract List getMajors(Page page, String id, String name)
			throws PlatformException;

	/**
	 * 学院列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param id
	 * @param name
	 * @return 学院列表
	 * @throws PlatformException
	 */
	public abstract List getDeps(Page page, String id, String name)
			throws PlatformException;

	/**
	 * 年级列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param id
	 * @param name
	 * @return 年级列表
	 * @throws PlatformException
	 */
	public abstract List getGrades(Page page, String id, String name)
			throws PlatformException;

	public abstract int getSiteDiquNum(HttpServletRequest request)
			throws PlatformException;

	public abstract SiteDiqu getSiteDiqu(String id) throws PlatformException;

	public abstract List getSiteDiqu(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract int addSiteDiqu(HttpServletRequest request)
			throws PlatformException;

	public abstract int setSiteRight(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateSiteDiqu(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteSiteDiqu(HttpServletRequest request)
			throws PlatformException;

	/**
	 * 学期列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 学期列表
	 * @throws PlatformException
	 */
	public abstract List getSemesters(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	/**
	 * 模板列表
	 * 
	 * @return 模板列表
	 * @throws PlatformException
	 */
	public abstract List getTemplates() throws PlatformException;

	/**
	 * 模板列表
	 * 
	 * @return 模板列表
	 * @throws PlatformException
	 */
	public abstract List getTemplates(Page page) throws PlatformException;

	/**
	 * 模板列表
	 * 
	 * @param page
	 *            翻页公用类
	 * @param searchProperty
	 *            查找属性类SearchProperty的一个List
	 * @param orderProperty
	 *            排序属性类OrderProperty的一个List
	 * @return 模板列表
	 * @throws PlatformException
	 */
	public abstract List getTemplates(Page page, List searchProperty,
			List orderProperty) throws PlatformException;

	/**
	 * 学院数量
	 * 
	 * @return 学院数量
	 * @throws PlatformException
	 */
	public abstract int getDepsNum() throws PlatformException;

	/**
	 * 层次数量
	 * 
	 * @return 层次数量
	 * @throws PlatformException
	 */
	public abstract int getEduTypesNum() throws PlatformException;

	/**
	 * 全部专业数量（包含公共课）
	 * 
	 * @return 全部专业数量（包含公共课）
	 * @throws PlatformException
	 */
	public abstract int getAllMajorsNum() throws PlatformException;

	/**
	 * 专业数量
	 * 
	 * @return 专业数量
	 * @throws PlatformException
	 */
	public abstract int getMajorsNum() throws PlatformException;

	/**
	 * 年级数量
	 * 
	 * @return 年级数量
	 * @throws PlatformException
	 */
	public abstract int getGradesNum() throws PlatformException;

	/**
	 * 教学站数量
	 * 
	 * @return 教学站数量
	 * @throws PlatformException
	 */
	public abstract int getSitesNum() throws PlatformException;

	/**
	 * 学期数量
	 * 
	 * @return 学期数量
	 * @throws PlatformException
	 */
	public abstract int getSemestersNum() throws PlatformException;

	/**
	 * 学期数量
	 * 
	 * @return 学期数量
	 * @throws PlatformException
	 */
	public abstract int getSemestersNum(String name, String start_date,
			String end_date, String start_elective, String end_elective,
			String start_test, String end_test) throws PlatformException;

	/**
	 * 模板数量
	 * 
	 * @return 模板数量
	 * @throws PlatformException
	 */
	public abstract int getTemplatesNum() throws PlatformException;

	/**
	 * 学院数量
	 * 
	 * @return 学院数量
	 * @throws PlatformException
	 */
	// public abstract int getDepsNum(List searchProperty) throws
	// PlatformException;
	/**
	 * 层次数量
	 * 
	 * @return 层次数量
	 * @throws PlatformException
	 */
	// public abstract int getEduTypesNum(List searchProperty) throws
	// PlatformException;
	/**
	 * 专业数量
	 * 
	 * @return 专业数量
	 * @throws PlatformException
	 */
	// public abstract int getMajorsNum(List searchProperty) throws
	// PlatformException;
	/**
	 * 年级数量
	 * 
	 * @return 年级数量
	 * @throws PlatformException
	 */
	// public abstract int getGradesNum(List searchProperty) throws
	// PlatformException;
	public abstract int getGradesNum(String name, String start_date,
			String end_date) throws PlatformException;

	/**
	 * 教学站数量
	 * 
	 * @return 教学站数量
	 * @throws PlatformException
	 */
	// public abstract int getSitesNum(List searchProperty) throws
	// PlatformException;
	public abstract int getSitesNum(String result) throws PlatformException;

	/**
	 * 教学站数量
	 * 
	 * @return 教学站数量
	 * @throws PlatformException
	 */
	public abstract int getSitesNum(String id, String name)
			throws PlatformException;

	/**
	 * 层次数量
	 * 
	 * @return 层次数量
	 * @throws PlatformException
	 */
	public abstract int getEduTypesNum(String id, String name)
			throws PlatformException;

	/**
	 * 专业数量
	 * 
	 * @return 专业数量
	 * @throws PlatformException
	 */
	public abstract int getMajorsNum(String id, String name)
			throws PlatformException;

	/**
	 * 学期数量
	 * 
	 * @return 学期数量
	 * @throws PlatformException
	 */
	public abstract int getDepsNum(String id, String name)
			throws PlatformException;

	/**
	 * 年级数量
	 * 
	 * @return 年级数量
	 * @throws PlatformException
	 */
	public abstract int getGradesNum(String id, String name)
			throws PlatformException;

	/**
	 * 更改学期激活状态
	 * 
	 * @return 1 为成功， 0 为失败
	 * @throws PlatformException
	 */
	public abstract int reverseSemesterActive(String semesterId)
			throws PlatformException;

	/**
	 * 根据aid得到专业下课程的数目
	 * 
	 * @param aid
	 * @return 课程数目
	 * @throws PlatformException
	 */
	public abstract int getMajorCoursesNum(java.lang.String aid)
			throws PlatformException;

	/**
	 * 根据page，专业aid得到课程列表
	 * 
	 * @param page
	 * @param aid
	 * @return 课程列表List
	 * @throws PlatformException
	 */
	public abstract List getMajorCourses(Page page, java.lang.String aid)
			throws PlatformException;

	/**
	 * 根据专业aid改变此专业的状态
	 * 
	 * @param aid
	 * @return 1 为成功， 0 为失败
	 * @throws PlatformException
	 * @throws SQLException
	 * @throws PlatformException
	 */
	public abstract int changeMajorStatus(java.lang.String aid)
			throws PlatformException;

	public abstract int updateInfoRight(String newsTypeId,
			String[] pageManagerId, String[] selectedManagerId)
			throws PlatformException;

	public abstract List getInfoRightUserIds(String newsTypeId)
			throws PlatformException;

	public abstract InfoManagerPriv getInfoManagerPriv(String managerId)
			throws PlatformException;

	public abstract CampusInfoManagerPriv getCampusInfoManagerPriv(String managerId)
			throws PlatformException;

	public abstract SmsManagerPriv getSmsManagerPriv(String managerId)
			throws PlatformException;

	public abstract InfoManagerPriv getInfoManagerPriv(String managerId,
			String newsTypeId) throws PlatformException;

	public abstract InfoManage getInfoManage(InfoManagerPriv infoManagerPriv)
			throws PlatformException;

	public abstract FeeManage getFeeManage();

	public abstract ChargeLevelManage getChargeLevelManage();

	public abstract SmsManage getSmsManage(SmsManagerPriv smsManagerPriv)
			throws PlatformException;

	public abstract List getInfoList(Page page) throws PlatformException;

	public abstract int getInfoListNum() throws PlatformException;

	/***************************************************************************
	 * 以下部分为TestBook管理 *
	 **************************************************************************/
	public abstract TeachBook getTeachBook(String id) throws PlatformException;

	public abstract List getTeachBookList(Page page, HttpServletRequest request)
			throws PlatformException;

	public abstract List getNoSelectTeachBookList(Page page,
			HttpServletRequest request, String selTbId)
			throws PlatformException;

	public abstract List getTeachBookList() throws PlatformException;

	public abstract int addTeachBook(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateTeachBook(HttpServletRequest request)
			throws PlatformException;

	public abstract int deleteTeachBook(String id) throws PlatformException;

	public abstract int getTeachBookNum(HttpServletRequest request)
			throws PlatformException;

	public abstract int getNoSelectTeachBookNum(HttpServletRequest request,
			String selectedID) throws PlatformException;

	/**
	 * 获取指定课程的教材数量
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTeachBooksNumOfCourse(String courseId)
			throws PlatformException;

	/**
	 * 获取指定课程的教材列表
	 * 
	 * @param page
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTeachBooksOfCourse(Page page, String courseId)
			throws PlatformException;

	/**
	 * 获取指定课程可添加的教材数
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTeachBooksNumToAddOfCourse(String courseId)
			throws PlatformException;

	/**
	 * 获取指定课程可添加的教材列表
	 * 
	 * @param page
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTeachBooksToAddOfCourse(Page page, String courseId)
			throws PlatformException;

	/**
	 * 获取站点招生计划层次专业信息
	 * 
	 * @return 获取站点招生计划层次专业信息
	 * @throws PlatformException
	 */
	public abstract List getPlanEduTypes(String batchId, String siteId,
			String edutype);

	/**
	 * 获得entity管理中需要用的WhatyEditor的配置参数
	 * 
	 * @param coursewareId
	 * @return
	 * @throws PlatformException
	 */
//	public abstract WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException;

	/**
	 * 获取符合条件的总站管理员，分站管理员，教师和学生的移动号码
	 * 
	 * @param persons
	 * @param siteIds
	 * @param gradeIds
	 * @param majorIds
	 * @param eduTypeIds
	 * @return
	 * @throws PlatformException
	 */
	public abstract String getMobilePhones(String[] persons, String[] siteIds,
			String[] gradeIds, String[] majorIds, String[] eduTypeIds)
			throws PlatformException;

	/**
	 * @param persons
	 * @param siteIds
	 * @param gradeIds
	 * @param majorIds
	 * @param eduTypeIds
	 * @return
	 * @throws PlatformException
	 */
	public abstract String getScope(String[] persons, String[] siteIds,
			String[] gradeIds, String[] majorIds, String[] eduTypeIds)
			throws PlatformException;

	/**
	 * 招生计划层次列表
	 * 
	 * @return 招生计划层次列表
	 * @throws PlatformException
	 */
	public abstract List getPlanEduTypes(String batchId, String siteId)
			throws PlatformException;

	/**
	 * 招生计划专业列表
	 * 
	 * @return 招生计划专业列表
	 * @throws PlatformException
	 */
	public abstract List getPlanMajors(String batchId, String siteId,
			String eduType) throws PlatformException;

	public abstract List getPlanSites(String batchId) throws PlatformException;

	public abstract int updateRecruitStudentInfo(String id, String name,
			String password, String gender, String folk, String birthday,
			String zzmm, String edu, String site_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws PlatformException;

	public abstract int addRecruitStudent(String name, String password,
			String gender, String folk, String birthday, String zzmm,
			String edu, String site_id, String grade_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String batchId) throws PlatformException;

	public abstract int addMatriculatetudent(String name, String password,
			String gender, String folk, String birthday, String zzmm,
			String edu, String site_id, String grade_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String batchId, String reg_no, String score_1,
			String score_2, String score_3, String score_4, String score,
			String testcard_id, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws PlatformException, PlatformException;

	public abstract LeaveWordManagerPriv getLeaveWordManagerPriv(String id)
			throws PlatformException;

	public abstract LeaveWordManage getLeaveWordManage(LeaveWordManagerPriv priv)
			throws PlatformException;

	public abstract LeaveWordNormalManage getLeaveWordNormalManage()
			throws PlatformException;
	
	public abstract CampusManagerPriv getCampusManagerPriv(String id)
	throws PlatformException;

	public abstract CampusManage getCampusManage(CampusManagerPriv priv)
		throws PlatformException;
	
	public abstract CampusNormalManage getCampusNormalManage()
	throws PlatformException;
	
	/**
	 * 添加招生计划
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	public abstract int addRecruitPlan(String edu_type_id, String major_id,
			String recruitnum, String site_id) throws NoRightException,
			PlatformException;
	
}
