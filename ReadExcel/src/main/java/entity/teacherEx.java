/**
 * 
 */
package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sj
 * 
 */
public class teacherEx
{
    private Teacher teacher; // 教师
    private String areaString;// 教师所在地区
    private String sexString;// 性别
    private String ageString;// 年龄
    private String teachingAge;// 教龄
    private String isMultiLanguage;// 是否双语
    private TeachingLanguage mainTeachingLanguage = new TeachingLanguage();// 主要教学语言
    private List<TeachingLanguage> unMainTeachingLanguages = new ArrayList<>();// 非主要教学语言
    private TeachingGrade mainTeachingClass = new TeachingGrade();// 主要教学学段
    private List<TeachingGrade> unMainTeachingGrades = new ArrayList<>();// 非主要教学学段
    private TeachingSubject mainTeachingCourse = new TeachingSubject();// 主要教学学科
    private List<TeachingSubject> unMaintTeachingSubjects = new ArrayList<>();// 非主要教学学科
    private String creator;// 创建人
    private Organization organization;// 所属学校

    /**
     * @return the teacher
     */
    public Teacher getTeacher()
    {
	return teacher;
    }

    /**
     * @param teacher
     *            the teacher to set
     */
    public void setTeacher(Teacher teacher)
    {
	this.teacher = teacher;
    }

    /**
     * @return the areaString
     */
    public String getAreaString()
    {
	return areaString;
    }

    /**
     * @param areaString
     *            the areaString to set
     */
    public void setAreaString(String areaString)
    {
	this.areaString = areaString;
    }

    /**
     * @return the sexString
     */
    public String getSexString()
    {
	return sexString;
    }

    /**
     * @param sexString
     *            the sexString to set
     */
    public void setSexString(String sexString)
    {
	this.sexString = sexString;
    }

    /**
     * @return the ageString
     */
    public String getAgeString()
    {
	return ageString;
    }

    /**
     * @param ageString
     *            the ageString to set
     */
    public void setAgeString(String ageString)
    {
	this.ageString = ageString;
    }

    /**
     * @return the teachingAge
     */
    public String getTeachingAge()
    {
	return teachingAge;
    }

    /**
     * @param teachingAge
     *            the teachingAge to set
     */
    public void setTeachingAge(String teachingAge)
    {
	this.teachingAge = teachingAge;
    }

    /**
     * @return the isMultiLanguage
     */
    public String getIsMultiLanguage()
    {
	return isMultiLanguage;
    }

    /**
     * @param isMultiLanguage
     *            the isMultiLanguage to set
     */
    public void setIsMultiLanguage(String isMultiLanguage)
    {
	this.isMultiLanguage = isMultiLanguage;
    }

    /**
     * @return the creator
     */
    public String getCreator()
    {
	return creator;
    }

    /**
     * @param creator
     *            the creator to set
     */
    public void setCreator(String creator)
    {
	this.creator = creator;
    }

    /**
     * @return the mainTeachingLanguage
     */
    public TeachingLanguage getMainTeachingLanguage()
    {
	return mainTeachingLanguage;
    }

    /**
     * @param mainTeachingLanguage
     *            the mainTeachingLanguage to set
     */
    public void setMainTeachingLanguage(TeachingLanguage mainTeachingLanguage)
    {
	this.mainTeachingLanguage = mainTeachingLanguage;
    }

    /**
     * @return the unMainTeachingLanguages
     */
    public List<TeachingLanguage> getUnMainTeachingLanguages()
    {
	return unMainTeachingLanguages;
    }

    /**
     * @param unMainTeachingLanguages
     *            the unMainTeachingLanguages to set
     */
    public void setUnMainTeachingLanguages(
	    List<TeachingLanguage> unMainTeachingLanguages)
    {
	this.unMainTeachingLanguages = unMainTeachingLanguages;
    }

    /**
     * @return the mainTeachingClass
     */
    public TeachingGrade getMainTeachingClass()
    {
	return mainTeachingClass;
    }

    /**
     * @param mainTeachingClass
     *            the mainTeachingClass to set
     */
    public void setMainTeachingClass(TeachingGrade mainTeachingClass)
    {
	this.mainTeachingClass = mainTeachingClass;
    }

    /**
     * @return the unMainTeachingGrades
     */
    public List<TeachingGrade> getUnMainTeachingGrades()
    {
	return unMainTeachingGrades;
    }

    /**
     * @param unMainTeachingGrades
     *            the unMainTeachingGrades to set
     */
    public void setUnMainTeachingGrades(List<TeachingGrade> unMainTeachingGrades)
    {
	this.unMainTeachingGrades = unMainTeachingGrades;
    }

    /**
     * @return the mainTeachingCourse
     */
    public TeachingSubject getMainTeachingCourse()
    {
	return mainTeachingCourse;
    }

    /**
     * @param mainTeachingCourse
     *            the mainTeachingCourse to set
     */
    public void setMainTeachingCourse(TeachingSubject mainTeachingCourse)
    {
	this.mainTeachingCourse = mainTeachingCourse;
    }

    /**
     * @return the unMaintTeachingSubjects
     */
    public List<TeachingSubject> getUnMaintTeachingSubjects()
    {
	return unMaintTeachingSubjects;
    }

    /**
     * @param unMaintTeachingSubjects
     *            the unMaintTeachingSubjects to set
     */
    public void setUnMaintTeachingSubjects(
	    List<TeachingSubject> unMaintTeachingSubjects)
    {
	this.unMaintTeachingSubjects = unMaintTeachingSubjects;
    }

    /**
     * @return the organization
     */
    public Organization getOrganization()
    {
	return organization;
    }

    /**
     * @param organization
     *            the organization to set
     */
    public void setOrganization(Organization organization)
    {
	this.organization = organization;
    }

}
