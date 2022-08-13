/**
 * 
 */
package com.whaty.platform.training;

import java.util.List;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.standard.aicc.operation.AiccCourseManage;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManage;
import com.whaty.platform.standard.aicc.operation.AiccUserDataManagePriv;
import com.whaty.platform.standard.scorm.operation.ScormStudentManage;
import com.whaty.platform.standard.scorm.operation.ScormStudentPriv;
import com.whaty.platform.training.basic.StudentClass;
import com.whaty.platform.training.basic.StudentCourse;
import com.whaty.platform.training.basic.StudentStudyStatus;
import com.whaty.platform.training.basic.TrainingClass;
import com.whaty.platform.training.basic.TrainingCourse;
import com.whaty.platform.training.basic.TrainingCourseType;
import com.whaty.platform.training.basic.TrainingCourseware;
import com.whaty.platform.training.skill.Skill;
import com.whaty.platform.training.skill.SkillChain;
import com.whaty.platform.training.skill.StudentSkill;
import com.whaty.platform.training.user.TrainingStudent;
import com.whaty.platform.training.user.TrainingStudentPriv;
import com.whaty.platform.util.Page;

/**
 * @author chenjian
 * 
 */
public abstract class TrainingStudentOperationManage {

	private TrainingStudentPriv trainingStudentPriv;

	public TrainingStudentPriv getTrainingStudentPriv() {
		return trainingStudentPriv;
	}

	public void setTrainingStudentPriv(TrainingStudentPriv trainingStudentPriv) {
		this.trainingStudentPriv = trainingStudentPriv;
	}

	/**
	 * 得到本学生
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingStudent getTrainingStudent()
			throws PlatformException;

	/**
	 * 得到某个类型下全部课程超市课程数
	 * 
	 * @param type
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getPubCoursesNum(String type) throws PlatformException;

	/**
	 * @param type
	 * @param courseName
	 * @param courseNote
	 * @param tearcher
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getPubCoursesNum(String type, String courseName,
			String courseNote, String courseTeacher) throws PlatformException;

	/**
	 * 得到某个类型下全部课程超市课程
	 * 
	 * @param type
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPubCourses(String type) throws PlatformException;

	/**
	 * 分页得到某个类型下全部课程超市课程
	 * 
	 * @param page
	 * @param type
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPubCourses(Page page, String type)
			throws PlatformException;

	/**
	 * @param page
	 * @param type
	 * @param courseName
	 * @param courseNote
	 * @param tearcher
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPubCourses(Page page, String type,
			String courseName, String courseNote, String courseTeacher)
			throws PlatformException;

	/**
	 * 得到本人选过的课程
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSelectedCourses() throws PlatformException;

	/**
	 * 得到已经完成学习的课程
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCompletedCourse() throws PlatformException;

	/**
	 * 得到尚未完成学习的课程
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCompletedCourse() throws PlatformException;

	/**
	 * 获取符合条件的培训班的数目
	 * 
	 * @param className
	 * @param classNote
	 * @param studentClassStatus
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getClassesNum(String className, String classNote,
			String studentClassStatus) throws PlatformException;

	/**
	 * 得到全部培训班
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClasses() throws PlatformException;

	/**
	 * 分页得到符合条件的培训班
	 * 
	 * @param page
	 * @param className
	 * @param classNote
	 * @param studentClassStatus
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClasses(Page page, String className,
			String classNote, String studentClassStatus)
			throws PlatformException;

	/**
	 * 得到已经参加的培训班
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSelectedClasses() throws PlatformException;

	/**
	 * 得到课程信息
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourse getTrainingCourse(String courseId)
			throws PlatformException;

	/**
	 * 得到培训班信息
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingClass getTrainingClass(String classId)
			throws PlatformException;

	/**
	 * 得到某个培训班下的课程
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClassCourses(String classId)
			throws PlatformException;

	/**
	 * 得到本学生的学习状态
	 * 
	 * @param sudentId
	 * @return
	 * @throws PlatformException
	 */
	public abstract StudentStudyStatus getStudentStudyStatus()
			throws PlatformException;

	public abstract int getSkillChainsNum(String skillName, String skillNote)
			throws PlatformException;

	/**
	 * 得到平台上的技能链
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSkillChains() throws PlatformException;

	public abstract List getSkillChains(Page page, String skillName,
			String skillNote) throws PlatformException;

	/**
	 * 得到某个技能的详细信息
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract SkillChain getSkillChain(String chainId)
			throws PlatformException;

	/**
	 * 得到某个技能链下的技能列表
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSkills(String chainId) throws PlatformException;

	/**
	 * 得到本用户某个技能链下已经获得的技能
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentSkills(String chainId)
			throws PlatformException;

	/**
	 * 得到本用户和技能关系
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentSkills() throws PlatformException;

	/**
	 * 获取符合条件的技能数
	 * 
	 * @param skillId
	 * @param skillName
	 * @param studentSkillStatus
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStudentSkillsNum(String skillId, String skillName,
			String skillNote, String studentSkillStatus)
			throws PlatformException;

	/**
	 * 得到本用户获得的技能
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getObtainedSkills() throws PlatformException;

	/**
	 * 得到某个技能的详细信息
	 * 
	 * @param skillId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Skill getSkill(String skillId) throws PlatformException;

	/**
	 * 得到某个学生与某个技能的关系
	 * 
	 * @param skillId
	 * @return
	 * @throws PlatformException
	 */
	public abstract StudentSkill getStudentSkill(String skillId)
			throws PlatformException;

	/**
	 * 得到课程类型列表，按照树状目录顺序排列
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseTypeTree() throws PlatformException;

	/**
	 * 得到某个课程类型
	 * 
	 * @param typeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourseType getCourseType(String typeId)
			throws PlatformException;

	/**
	 * 得到某个课程类型的下的子目录
	 * 
	 * @param parentTypeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getChildCourseTypes(String parentTypeId)
			throws PlatformException;

	/**
	 * @param courseName
	 * @param courseNote
	 * @param courseTeacher
	 * @param status
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getStudentCoursesNum(String courseName,
			String courseNote, String courseTeacher,
			String studentCourseStatus, String studentLearnStatus)
			throws PlatformException;

	/**
	 * 得到本用户与课程的关系列表
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentCourses() throws PlatformException;

	/**
	 * 分页得到符合条件的课程
	 * 
	 * @param page
	 * @param courseName
	 * @param courseNote
	 * @param courseTeacher
	 * @param status
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentCourses(Page page, String courseName,
			String courseNote, String courseTeacher,
			String studentCourseStatus, String studentLearnStatus)
			throws PlatformException;

	/**
	 * 分页得到符合条件的前几门课程
	 * 
	 * @param courseName
	 * @param courseNote
	 * @param courseTeacher
	 * @param status
	 * @param limit
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentCourses(String courseName,
			String courseNote, String courseTeacher,
			String studentCourseStatus, String studentLearnStatus, int limit)
			throws PlatformException;

	/**
	 * 得到某个课程的课件
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourseware getCourseware(String courseId)
			throws PlatformException;

	public abstract int getStudentClassesNum(String className,
			String classNote, String studentClassStatus)
			throws PlatformException;

	/**
	 * 得到用户和培训班的关系
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentClasses() throws PlatformException;

	/**
	 * 获取符合条件的培训班
	 * 
	 * @param page
	 * @param className
	 * @param classNote
	 * @param limit
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentClasses(Page page, String className,
			String classNote, String studentClassStatus, int limit)
			throws PlatformException;

	/**
	 * 得到用户和某个培训班的关系
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract StudentClass getStudentClass(String classId)
			throws PlatformException;

	/**
	 * get the courses of the class
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClassCourses(String classId)
			throws PlatformException;

	/**
	 * 得到用户和某个课程的关系
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract StudentCourse getStudentCourse(String courseId)
			throws PlatformException;

	/**
	 * 得到Aicc课件管理权限
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccUserDataManagePriv getAiccUserDataManagePriv()
			throws PlatformException;

	/**
	 * 得到Aicc课件用户信息管理类
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccUserDataManage getAiccUserDataManage()
			throws PlatformException;

	/**
	 * 得到Aicc课件课程信息管理类
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccCourseManage getAiccCourseManage()
			throws PlatformException;

	/**
	 * 得到Scorm12课件管理权限
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScormStudentPriv getScormStudentPriv()
			throws PlatformException;

	/**
	 * 得到Scorm12课件学生管理类
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScormStudentManage getScormStudentManage()
			throws PlatformException;

	/**
	 * 修改学生studentId学习课程courseId的状态
	 * 
	 * @param studentId
	 * @param courseId
	 * @param percent
	 * @param learnStatus
	 * @throws PlatformException
	 */
	public abstract void updateStudentCourseData(String studentId,
			String courseId, String percent, String learnStatus)
			throws PlatformException;

	/**
	 * 学生申请选课
	 * 
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void applyCourse(String courseId) throws PlatformException;

	/**
	 * 学生退选课程
	 * 
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void unApplyCourse(String courseId)
			throws PlatformException;

	/**
	 * 学生申请培训班
	 * 
	 * @param classId
	 * @throws PlatformException
	 */
	public abstract void applyClass(String classId) throws PlatformException;
	
	/**
	 * @param classId
	 * @throws PlatformException
	 */
	public abstract void unApplyClass(String classId) throws PlatformException;

	/**
	 * 学深申请技能
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void applySkill(String skillId) throws PlatformException;

	/**
	 * 将技能设为目标
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void targetSkill(String skillId) throws PlatformException;

	/**
	 * 将目标技能取消
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void unTargetSkill(String skillId) throws PlatformException;

	public abstract int updateTrainingUser(String name, String nickName,
			String email, String mobilephone, String phone)
			throws PlatformException;
}
