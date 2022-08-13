package com.whaty.platform.entity.user;

import com.whaty.platform.Items;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.basic.EduType;
import com.whaty.platform.entity.basic.Grade;
import com.whaty.platform.entity.basic.Major;

/**
 * ������ָ����ʦ��Ҫ��˵ı�ҵ���רҵ���
 * @author Administrator
 *
 */
public abstract class TeacherLimit implements Items {
	private String id; //

	private Teacher teacher; //��ʦ��Ϣ

	private Grade grade; //�꼶��Ϣ

	private EduType eduType; //�����Ϣ

	private Major major; //רҵ��Ϣ

	public EduType getEduType() {
		return eduType;
	}

	public void setEduType(EduType eduType) {
		this.eduType = eduType;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	
	public abstract int IsExsitGradeEduMajor(String teacherId) throws PlatformException ;
}
