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
	 * �õ���ѧ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingStudent getTrainingStudent()
			throws PlatformException;

	/**
	 * �õ�ĳ��������ȫ���γ̳��пγ���
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
	 * �õ�ĳ��������ȫ���γ̳��пγ�
	 * 
	 * @param type
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPubCourses(String type) throws PlatformException;

	/**
	 * ��ҳ�õ�ĳ��������ȫ���γ̳��пγ�
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
	 * �õ�����ѡ���Ŀγ�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSelectedCourses() throws PlatformException;

	/**
	 * �õ��Ѿ����ѧϰ�Ŀγ�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCompletedCourse() throws PlatformException;

	/**
	 * �õ���δ���ѧϰ�Ŀγ�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getUnCompletedCourse() throws PlatformException;

	/**
	 * ��ȡ������������ѵ�����Ŀ
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
	 * �õ�ȫ����ѵ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getClasses() throws PlatformException;

	/**
	 * ��ҳ�õ�������������ѵ��
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
	 * �õ��Ѿ��μӵ���ѵ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSelectedClasses() throws PlatformException;

	/**
	 * �õ��γ���Ϣ
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourse getTrainingCourse(String courseId)
			throws PlatformException;

	/**
	 * �õ���ѵ����Ϣ
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingClass getTrainingClass(String classId)
			throws PlatformException;

	/**
	 * �õ�ĳ����ѵ���µĿγ�
	 * 
	 * @param classId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTrainingClassCourses(String classId)
			throws PlatformException;

	/**
	 * �õ���ѧ����ѧϰ״̬
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
	 * �õ�ƽ̨�ϵļ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSkillChains() throws PlatformException;

	public abstract List getSkillChains(Page page, String skillName,
			String skillNote) throws PlatformException;

	/**
	 * �õ�ĳ�����ܵ���ϸ��Ϣ
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract SkillChain getSkillChain(String chainId)
			throws PlatformException;

	/**
	 * �õ�ĳ���������µļ����б�
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSkills(String chainId) throws PlatformException;

	/**
	 * �õ����û�ĳ�����������Ѿ���õļ���
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentSkills(String chainId)
			throws PlatformException;

	/**
	 * �õ����û��ͼ��ܹ�ϵ
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentSkills() throws PlatformException;

	/**
	 * ��ȡ���������ļ�����
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
	 * �õ����û���õļ���
	 * 
	 * @param chainId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getObtainedSkills() throws PlatformException;

	/**
	 * �õ�ĳ�����ܵ���ϸ��Ϣ
	 * 
	 * @param skillId
	 * @return
	 * @throws PlatformException
	 */
	public abstract Skill getSkill(String skillId) throws PlatformException;

	/**
	 * �õ�ĳ��ѧ����ĳ�����ܵĹ�ϵ
	 * 
	 * @param skillId
	 * @return
	 * @throws PlatformException
	 */
	public abstract StudentSkill getStudentSkill(String skillId)
			throws PlatformException;

	/**
	 * �õ��γ������б�������״Ŀ¼˳������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getCourseTypeTree() throws PlatformException;

	/**
	 * �õ�ĳ���γ�����
	 * 
	 * @param typeId
	 * @return
	 * @throws PlatformException
	 */
	public abstract TrainingCourseType getCourseType(String typeId)
			throws PlatformException;

	/**
	 * �õ�ĳ���γ����͵��µ���Ŀ¼
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
	 * �õ����û���γ̵Ĺ�ϵ�б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentCourses() throws PlatformException;

	/**
	 * ��ҳ�õ����������Ŀγ�
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
	 * ��ҳ�õ�����������ǰ���ſγ�
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
	 * �õ�ĳ���γ̵Ŀμ�
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
	 * �õ��û�����ѵ��Ĺ�ϵ
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentClasses() throws PlatformException;

	/**
	 * ��ȡ������������ѵ��
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
	 * �õ��û���ĳ����ѵ��Ĺ�ϵ
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
	 * �õ��û���ĳ���γ̵Ĺ�ϵ
	 * 
	 * @param courseId
	 * @return
	 * @throws PlatformException
	 */
	public abstract StudentCourse getStudentCourse(String courseId)
			throws PlatformException;

	/**
	 * �õ�Aicc�μ�����Ȩ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccUserDataManagePriv getAiccUserDataManagePriv()
			throws PlatformException;

	/**
	 * �õ�Aicc�μ��û���Ϣ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccUserDataManage getAiccUserDataManage()
			throws PlatformException;

	/**
	 * �õ�Aicc�μ��γ���Ϣ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract AiccCourseManage getAiccCourseManage()
			throws PlatformException;

	/**
	 * �õ�Scorm12�μ�����Ȩ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScormStudentPriv getScormStudentPriv()
			throws PlatformException;

	/**
	 * �õ�Scorm12�μ�ѧ��������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScormStudentManage getScormStudentManage()
			throws PlatformException;

	/**
	 * �޸�ѧ��studentIdѧϰ�γ�courseId��״̬
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
	 * ѧ������ѡ��
	 * 
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void applyCourse(String courseId) throws PlatformException;

	/**
	 * ѧ����ѡ�γ�
	 * 
	 * @param courseId
	 * @throws PlatformException
	 */
	public abstract void unApplyCourse(String courseId)
			throws PlatformException;

	/**
	 * ѧ��������ѵ��
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
	 * ѧ�����뼼��
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void applySkill(String skillId) throws PlatformException;

	/**
	 * ��������ΪĿ��
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void targetSkill(String skillId) throws PlatformException;

	/**
	 * ��Ŀ�꼼��ȡ��
	 * 
	 * @param skillId
	 * @throws PlatformException
	 */
	public abstract void unTargetSkill(String skillId) throws PlatformException;

	public abstract int updateTrainingUser(String name, String nickName,
			String email, String mobilephone, String phone)
			throws PlatformException;
}
