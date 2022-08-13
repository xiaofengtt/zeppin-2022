package com.whaty.platform.entity.user;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author whaty
 * 
 */
public interface UserBatchActivity {

	/**
	 * �����ѧ��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public void studentAddBatch(List studentList) throws PlatformException;

	/**
	 * ��ɾ��ѧ��
	 * 
	 * @param studentId
	 * @throws PlatformException
	 */
	public void studentDeleteBatch(String studentId[]) throws PlatformException;

	/**
	 * ����ӽ�ʦ
	 * 
	 * @param teacherList
	 * @throws PlatformException
	 */
	public void teacherAddBatch(List teacherList) throws PlatformException;

	/**
	 * ��ע������
	 * 
	 * @param studentId
	 * @throws PlatformException
	 */
	public void newStudentRegBatch(String[] studentId, String gradeId)
			throws PlatformException;
	
	public void newStudentRegBatch(String[] studentId, String gradeId,
			String ssoId) throws PlatformException;

	public void newStudentUnRegBatch(String[] studentId)
			throws PlatformException;

	public void siteTeacherAddBatch(List teacherList) throws PlatformException;

	public String[] checkExistRegIdCard(String[] studentId, String gradeId)
			throws PlatformException;
}
