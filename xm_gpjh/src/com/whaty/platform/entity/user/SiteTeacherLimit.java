package com.whaty.platform.entity.user;

import com.whaty.platform.Items;

/**
 * �������վָ����ʦ��Ҫָ����ѧ��
 * @author Administrator
 *
 */
public abstract class SiteTeacherLimit implements Items {
	private String id;

	private SiteTeacher siteTeacher; //��վָ����ʦ

	private Student student; //ѧ����Ϣ

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SiteTeacher getSiteTeacher() {
		return siteTeacher;
	}

	public void setSiteTeacher(SiteTeacher siteTeacher) {
		this.siteTeacher = siteTeacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
   
}
