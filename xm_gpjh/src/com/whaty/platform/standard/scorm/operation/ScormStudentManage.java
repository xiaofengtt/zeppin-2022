/**
 * 
 */
package com.whaty.platform.standard.scorm.operation;

import java.util.List;

import com.whaty.platform.standard.scorm.Exception.ScormException;

/**
 * @author chenjian
 *
 */
public abstract class ScormStudentManage {
	
	private ScormStudentPriv studentPriv;

	public ScormStudentPriv getStudentPriv() {
		return studentPriv;
	}

	public void setStudentPriv(ScormStudentPriv studentPriv) {
		this.studentPriv = studentPriv;
	}

	public ScormStudentManage() {
		
	}
	
	/**���ĳ��γ��¸�ѧ��ȫ��SCO�����ѧϰ���
	 * @param courseId
	 * @return
	 * @throws ScormException
	 */
	public abstract List getUserScos(String courseId)throws ScormException;
	
	/**�õ�ĳ��ѧ��ѧϰĳ��γ̵����
	 * @param courseId
	 * @return
	 * @throws ScormException
	 */
	public abstract UserCourseData getUserCourseData(String courseId) throws ScormException;
	
	/**�õ�ĳ��γ�
	 * @param coursewarecode
	 * @return
	 */
	public abstract ScormCourse getCourse(String courseId) throws ScormException;
	
	public abstract void addAttemptNum(String courseId);
}
