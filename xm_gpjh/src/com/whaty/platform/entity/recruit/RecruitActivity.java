package com.whaty.platform.entity.recruit;

import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.util.Page;

public interface RecruitActivity {
	
	/**
	 * ��ȡĳվ��,��κͿ����µĿ����ֲ���
	 */
	public List getExamroomDisply(Page page, String siteId, String batchId,
			String sortId,String edutype_id);
	
	public int getExamroomDisplyNum(String siteId, String batchId, String sortId,String edutype_id) ;
	/**
	 * ��ȡĳվ��,��κͿ����µĿ����ֲ���
	 */
	public List getExamroomDisply2(Page page, String siteId, String batchId,
			String sortId,String edutype_id);
	
	public int getExamroomDisplyNum2(String siteId, String batchId, String sortId,String edutype_id) ;
	
	/**
	 * ������ѧ�����Ϊ��ѧ����ѧ��
	 * 
	 * @param studentList
	 * @param testCourse
	 * @throws PlatformException
	 */
	public int addRecruitTestStudents(List studentList,
			RecruitTestCourse testCourse) throws PlatformException;

	/**
	 * ����ѧ����ѧ��ɾ��ѧ��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public int deleteRecruitTestStudents(List studentList)
			throws PlatformException;

	/**
	 * Ϊĳ������ε�ѧ����俼��
	 * 
	 * @param testBatch
	 * @param site
	 * @param num
	 * @throws PlatformException
	 */
	public void allotStudents(String batch_id, String site_id, String num)
			throws PlatformException;

	/**
	 * ¼ȡ����ѧ��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public void recruitStudents(List studentList) throws PlatformException;

	/**
	 * ȡ����¼ȡ�ı���ѧ��
	 * 
	 * @param studentList
	 * @throws PlatformException
	 */
	public void unRecruitStudents(List studentList) throws PlatformException;

	/**
	 * ��majorList�б��е�רҵ�����Sort��j
	 * 
	 * @param majorList
	 * @return 0Ϊ��j���ɹ� ����0Ϊ�ɹ�
	 * @throws PlatformException
	 */
	public int addMajorToSort(List majorList) throws PlatformException;

	/**
	 * �����ɼ�
	 * 
	 * @param
	 * @return 0Ϊ���ɹ� ����0Ϊ�ɹ�
	 * @throws PlatformException
	 */
	public int releaseScore(List searchProperty) throws PlatformException;

	public int unreleaseScore(List searchProperty) throws PlatformException;

	public int releaseMatriculate(List searchProperty) throws PlatformException;

	public int unreleaseMatriculate(List searchProperty)
			throws PlatformException;

	/**
	 * ���ϱ�ѧ��ɼ�
	 * 
	 * @param courseList
	 * @param scoreList
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract void updateRecruitStudentScoreBatch(List courseList,
			List scoreList) throws NoRightException, PlatformException;


}
