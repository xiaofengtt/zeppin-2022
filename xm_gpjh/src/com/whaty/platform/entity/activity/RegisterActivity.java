/*
 * Register.java
 *
 * Created on 2005��5��25��, ����4:50
 */

package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * ע�������Ϣ
 * 
 * @author Ligang
 */
public abstract class RegisterActivity {

	/** Creates a new instance of Register */
	public RegisterActivity() {
	}

	/**
	 * ��semester_idѧ����ע��ѧ��
	 * 
	 * @param semester_id
	 * @param students
	 * @return ע���ѧ����
	 */
	public abstract int regStudents(String semester_id, List students)
			throws PlatformException;

	public abstract int regStudentActiveSemester(String regNo)
			throws PlatformException;

	public abstract int isRegStudent(String semester_id, String student_id)
			throws PlatformException;

	public abstract int regStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id)
			throws PlatformException;

	public abstract int unRegStudents(String semester_id, List students)
			throws PlatformException;

	public abstract int unRegStudents(String[] register_id)
			throws PlatformException;

	public abstract int unRegStudentsBatch(String semester_id, String[] id,
			String[] major_id, String[] edutype_id, String[] grade_id)
			throws PlatformException;
}
