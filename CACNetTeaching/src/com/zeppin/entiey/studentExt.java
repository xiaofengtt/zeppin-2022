/**
 * 
 */
package com.zeppin.entiey;

/**
 * @author Administrator
 * 
 */
public class studentExt
{
    Student student;
    String sex;
    String classes;
    String group;// 所在组
    float score;// 成绩

    /**
     * @return the student
     */
    public Student getStudent()
    {
	return student;
    }

    /**
     * @param student
     *            the student to set
     */
    public void setStudent(Student student)
    {
	this.student = student;
    }

    /**
     * @return the sex
     */
    public String getSex()
    {
	return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(String sex)
    {
	this.sex = sex;
    }

    /**
     * @return the classes
     */
    public String getClasses()
    {
	return classes;
    }

    /**
     * @param group
     *            the group to set
     */
    public void setClasses(String classes)
    {
	this.classes = classes;
    }
    
    /**
     * @return the group
     */
    public String getGroup()
    {
	return group;
    }

    /**
     * @param group
     *            the group to set
     */
    public void setGroup(String group)
    {
	this.group = group;
    }

    /**
     * @return the score
     */
    public float getScore()
    {
	return score;
    }

    /**
     * @param score
     *            the score to set
     */
    public void setScore(float score)
    {
	this.score = score;
    }

}
