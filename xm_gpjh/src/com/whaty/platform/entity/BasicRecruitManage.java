package com.whaty.platform.entity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitCourse;
import com.whaty.platform.entity.recruit.RecruitNoExamCondition;
import com.whaty.platform.entity.recruit.RecruitSort;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.entity.recruit.RecruitTestCourse;
import com.whaty.platform.entity.recruit.RecruitTestStudent;
import com.whaty.platform.util.Page;

/**
 * ���������˹���Ա�����������ֵĹ���
 * 
 * @author chenjian
 * 
 */

public abstract class BasicRecruitManage {
	/** Creates a new instance of BasicRecruitManage */
	public BasicRecruitManage() {
	}

	/**
	 * ���רҵ����
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addRecruitSort(String edutype_id, String name,
			String note) throws PlatformException;

	/**
	 * 
	 * @param edutype_id
	 * @param name
	 * @param note
	 * @return
	 * @throws PlatformException
	 */
	public abstract int setRegDate(String batch_id, String reg_startdate,
			String reg_enddate) throws PlatformException;

	/**
	 * רҵ�����б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @param searchProperty
	 *            ����������SearchProperty��һ��List
	 * @param orderProperty
	 *            ����������OrderProperty��һ��List
	 * @return רҵ�����б�
	 * @throws PlatformException
	 */
	public abstract List getRecruitSorts(Page page) throws PlatformException;

	/**
	 * רҵ��������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitSortsNum() throws PlatformException;

	/**
	 * ����aid�õ�רҵ�������
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitSort getRecruitSort(String aid)
			throws PlatformException;

	/**
	 * �޸�רҵ����
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateRecruitSort(String id, String edutype_id,
			String name, String note) throws PlatformException,
			PlatformException;

	/**
	 * ɾ��רҵ����
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteRecruitSort(String id) throws PlatformException,
			PlatformException;

	/**
	 * ���רҵ��������רҵ
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int setRecruitSortMajors(String recruitSortId,
			List majorId, List majorIds) throws PlatformException,
			PlatformException;

	/**
	 * רҵ��������רҵ�б�
	 * 
	 * @param aid
	 * @return רҵ��������רҵ�б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitSortMajors(Page page, String aid,
			String status) throws PlatformException;

	public abstract int getRecruitSortMajorsNum(String aid, String status)
			throws PlatformException;

	/**
	 * ���רҵ���������γ�
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int setRecruitSortCourses(String recruitSortId,
			List courseId, List courseIds) throws PlatformException,
			PlatformException;

	/**
	 * רҵ���������γ��б�
	 * 
	 * @param aid
	 * @return רҵ���������γ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitSortCourses(Page page, String aid,
			String status) throws PlatformException;

	public abstract int getRecruitSortCoursesNum(String aid, String status)
			throws PlatformException;

	/**
	 * רҵ�����������Կγ��б�
	 * 
	 * @return רҵ�����������Կγ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitSortTestCourses(String batchId, String sortId)
			throws PlatformException;

	/**
	 * ��ӿγ�����רҵ����
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int setRecruitCourseSorts(String recruitCourseId,
			List sortId, List sortIds) throws PlatformException,
			PlatformException;

	/**
	 * �γ�����רҵ�����б�
	 * 
	 * @param aid
	 * @return �γ�����רҵ�����б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitCourseSorts(Page page, String aid,
			String status) throws PlatformException;

	public abstract int getRecruitCourseSortsNum(String aid, String status)
			throws PlatformException;

	/**
	 * �޸���������״̬������ĳһ��������
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateRecruitBatchStatus(String id)
			throws PlatformException;

	/**
	 * �޸������ƻ�״̬�����ĳһ�����ƻ�
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateRecruitPlanStatus(String id, String status,
			String num, String note) throws PlatformException;

	/**
	 * �޸������ƻ�״̬�����ĳһ�����ƻ�
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateRecruitPlanStatus(String id, String status)
			throws PlatformException;

	/**
	 * ������ϱ�idΪbatchId���������ε������ƻ��Ľ�ѧվ
	 * 
	 * @param batchId
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitPlanSites(Page page, String batchId)
			throws PlatformException;

	/**
	 * ���Կγ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitCourseNum() throws PlatformException;

	/**
	 * ���Կγ��б�
	 * 
	 * @return ���Կγ��б�
	 * @throws PlatformException
	 */
	public abstract List getRecruitCourse(Page page) throws PlatformException;

	public abstract RecruitCourse getRecruitCourse(String aid)
			throws PlatformException;

	/**
	 * ɾ�����Կγ�
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteRecruitCourse(String id) throws PlatformException;

	/**
	 * �����������
	 * 
	 * @param id
	 * @param name
	 * @param planBeginTime
	 * @param planEndTime
	 * @param signBeginTime
	 * @param signEndTime
	 * @param examBeginTime
	 * @param examEndTime
	 * @param note
	 * @param simpleNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract int addRecruitBatch(String id, String name,
			String planBeginTime, String planEndTime, String signBeginTime,
			String signEndTime, String examBeginTime, String examEndTime,
			String note, String simpleNote) throws PlatformException;

	/**
	 * ����������Կγ�
	 * 
	 * @param id
	 * @param name
	 * @param note
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addRecruitCourse(String id, String name, String note)
			throws PlatformException;

	public abstract int updateRecruitCourse(String id, String name, String note)
			throws PlatformException;

	/**
	 * ɾ����������
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteRecruitBatch(String id) throws PlatformException,
			PlatformException;

	/**
	 * �޸���������
	 * 
	 * @param id
	 * @param name
	 * @param planBeginTime
	 * @param planEndTime
	 * @param signBeginTime
	 * @param signEndTime
	 * @param examBeginTime
	 * @param examEndTime
	 * @param note
	 * @param simpleNote
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateRecruitBatch(String id, String name,
			String planBeginTime, String planEndTime, String signBeginTime,
			String signEndTime, String examBeginTime, String examEndTime,
			String note, String simpleNote) throws PlatformException;

	/**
	 * ����aid�õ��������ζ���
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitBatch getRecruitBatch(java.lang.String aid)
			throws PlatformException;

	/**
	 * ��ȡ��ǰ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitBatch getActiveRecruitBatch()
			throws PlatformException;

	/**
	 * ���������б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitBatches(Page page) throws PlatformException;

	/**
	 * @param page
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitBatches(Page page, String name)
			throws PlatformException;
	/**
	 * @param page
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitBatches(Page page, String recruitNo,String recruitName)
			throws PlatformException;

	/**
	 * �����ƻ��б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitPlans(Page page, String batchId,
			String siteId, String status) throws PlatformException;

	/**
	 * ��������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitBatchesNum() throws PlatformException;

	/**
	 * ��������������������
	 * 
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitBatchesNum(String name)
			throws PlatformException;
	/**
	 * ��������������������
	 * 
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitBatchesNum(String id,String name)
			throws PlatformException;

	/**
	 * ��������������ѧ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitScoreStudents(Page page, String siteId,
			String eduTypeId, String majorId, String batchId, String[] sec)
			throws PlatformException;

	/**
	 * ��������������ѧ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitScoreStudents(Page page, String courseId,
			String batchId, String[] sec) throws PlatformException;

	/**
	 * ��������������ѧ����������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitScoreStudentsNum(String siteId,
			String eduTypeId, String majorId, String batchId, String[] sec)
			throws PlatformException;

	/**
	 * �����ƻ�����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getRecruitPlansNum(String batchId, String siteId,
			String status) throws PlatformException;

	/**
	 * ����ָ��id��ȡ���Կγ���Ϣ
	 * 
	 * @param id
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract RecruitTestCourse getRecruitTestCourse(String id)
			throws PlatformException;

	/**
	 * ���Կγ��б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ���Կγ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitTestCourses(Page page, String batchId,
			String courseId) throws PlatformException;

	/**
	 * ���Կγ���Ŀ
	 * 
	 * @return ���Կγ���Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRecruitTestCoursesNum(String batchId, String courseId)
			throws PlatformException;

	/**
	 * �ǿ��Կγ��б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return �ǿ��Կγ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnRecruitTestCourses(Page page, String batchId)
			throws PlatformException;

	/**
	 * �ǿ��Կγ���Ŀ
	 * 
	 * @return �ǿ��Կγ���Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getUnRecruitTestCoursesNum(String batchId)
			throws PlatformException;

	/**
	 * ѧ���ɼ��б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���ɼ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitStudentScores(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String signDate) throws PlatformException,
			PlatformException;

	/**
	 * �ֶ�¼ȡѧ��ʱ��ѧ��״̬�ĸı�
	 * 
	 * @throws PlatformException
	 * @throws PlatformException
	 */

	public abstract int updateHandSingleStudentStatus(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	/**
	 * ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���ɼ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getFreeRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String considertype, String considertype_status)
			throws PlatformException;

	public abstract int updateFreeRecruitStudentStatus(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String considertype, String considertype_status)
			throws PlatformException;

	/**
	 * ¼ȡѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���ɼ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	public abstract List getPassRecruitStudents(Page page, String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status) throws PlatformException;
	/**
	 * 
	 * @param page
	 * @param batchId
	 * @param siteId
	 * @param majorId
	 * @param eduTypeId
	 * @param register_status
	 * @param stuName
	 * @param idCard
	 * @param regNo      ��ʱδ��
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPassRecruitStudents(Page page, String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String stuName ,String testcard_id,String regNo) throws PlatformException;
	/**
	 * ѧ�ŷ���״̬ѧ���б�     (ѧ��״̬��1Ϊ������2Ϊδ����)
	 * @param page
	 * @param batchId
	 * @param siteId
	 * @param majorId
	 * @param eduTypeId
	 * @param register_status
	 * @param pubStatus
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPassRecruitStudents(Page page, String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String pubStatus,String reg_no) throws PlatformException;
	/**
	 * 
	 * @param page
	 * @param batchId
	 * @param siteId
	 * @param majorId
	 * @param eduTypeId
	 * @param register_status
	 * @param pubStatus
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPassRecruitStudentsList(Page page, String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String pubStatus) throws PlatformException;

	/**
	 * ¼ȡѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���ɼ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	/**
	 * ��ע��ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���ɼ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String cardNo, String regNo) throws PlatformException,
			PlatformException;
	/**
	 * 
	 * @param page
	 * @param siteId
	 * @param batchId
	 * @param gradeId
	 * @param majorId
	 * @param eduTypeId
	 * @param name
	 * @param cardNo
	 * @param regNo
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String gradeId,String majorId, String eduTypeId,
			String name, String cardNo, String regNo) throws PlatformException,
			PlatformException;

	/**
	 * δע��ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���ɼ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String regNo) throws PlatformException,
			PlatformException;
	/**
	 * δע��ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ���ɼ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String regNo,String gender) throws PlatformException,
			PlatformException;
	
	public abstract List getAllUnRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String regNo) throws PlatformException,
			PlatformException;

	/**
	 * ѧ���ɼ���Ŀ
	 * 
	 * @return ѧ���ɼ���Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRecruitStudentScoresNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String signDate) throws PlatformException,
			PlatformException;

	/**
	 * ����ѧ����Ŀ
	 * 
	 * @return ����ѧ����Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getFreeRecruitStudentNum(String siteId, String batchId,
			String majorId, String eduTypeId, String name, String cardNo,
			String considertype, String considertype_status)
			throws PlatformException;

	/**
	 * ¼ȡѧ����Ŀ
	 * 
	 * @return ¼ȡѧ����Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	public abstract int getPassRecruitStudentsNum(String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status) throws PlatformException;

	public abstract int getPassRecruitStudentsNum(String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String pubStatus,String reg_no) throws PlatformException;
	
	public abstract int getPassRecruitStudentsNum(String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String stuName,String testcard_id,String reg_no) throws PlatformException;
	/**
	 * ѧ�����ɵ���δ������ѧ���ܼ�
	 * @param batchId
	 * @param siteId
	 * @param majorId
	 * @param eduTypeId
	 * @param register_status
	 * @param pubStatus
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getPassRecruitStudentsListNum(String batchId,
			String siteId, String majorId, String eduTypeId,
			String register_status,String pubStatus) throws PlatformException;
	
	public abstract int updateStudentSiteAndMajor(String[] ids, String siteId,
			String majorId) throws PlatformException;

	public abstract int updateStudentRegNoAndPwd(String[] ids)
			throws PlatformException;

	/**
	 * ¼ȡѧ����Ŀ
	 * 
	 * @return ¼ȡѧ����Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getUnPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	/**
	 * ¼ȡ��ע��ѧ����Ŀ
	 * 
	 * @return ¼ȡѧ����Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String regNo) throws PlatformException,
			PlatformException;
	/**
	 * 
	 * @param siteId
	 * @param batchId
	 * @param gradeId
	 * @param majorId
	 * @param eduTypeId
	 * @param name
	 * @param cardNo
	 * @param regNo
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRegisterPassRecruitStudentsNum(String siteId,
			String batchId,String gradeId, String majorId, String eduTypeId, String name,
			String cardNo, String regNo) throws PlatformException,
			PlatformException;

	/**
	 * ¼ȡδע��ѧ����Ŀ
	 * 
	 * @return ¼ȡѧ����Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo) throws PlatformException;
	
	public abstract int getUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo,String gender) throws PlatformException;
	
	public abstract int getAllUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo) throws PlatformException;

	/**
	 * ��ӿ��Կγ�
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int addRecruitTestCourses(String batchId, List courseId,
			String startdate, String enddate) throws PlatformException,
			PlatformException;

	/**
	 * ɾ�����Կγ�
	 * 
	 * @return 1Ϊ�ɹ�,0Ϊ���ɹ�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int deleteRecruitTestCourse(String id)
			throws PlatformException;

	/**
	 * �ϱ�ѧ���ɼ�
	 * 
	 * @param score
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateRecruitStudentScore(HashMap score)
			throws PlatformException;

	/**
	 * �����ϱ�ѧ���ɼ�
	 * 
	 * @param scoreList
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateRecruitStudentScoreBatch(List scoreList,
			String type) throws PlatformException;

	/**
	 * ����ѧ���ɼ�
	 * 
	 * @param batchId
	 * @param eduTypeId
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int releaseScore(String batchId, String eduTypeId,
			String majorId) throws PlatformException;

	/**
	 * ����ѧ���ɼ�
	 * 
	 * @param batchId
	 * @param eduTypeId
	 * @param majorId
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int releaseScore(String batchId, String siteId)
			throws PlatformException;

	public abstract int unreleaseScore(String batchId, String siteId)
			throws PlatformException;

	public abstract int releaseMatriculate(String batchId, String siteId)
			throws PlatformException;

	public abstract int unreleaseMatriculate(String batchId, String siteId)
			throws PlatformException;

	public abstract List getReleaseSites(String batchId)
			throws PlatformException;

	/**
	 * ¼ȡѧ���б�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getNormalStudents(Page page, String batchId,
			String siteId, String eduTypeId, String majorId, String name,
			String cardNo, String pass_status) throws PlatformException;

	/**
	 * ¼ȡѧ������
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getNormalStudentsNum(String batchId, String siteId,
			String eduTypeId, String majorId, String name, String cardNo)
			throws PlatformException;

	/**
	 * ��ѯ�����ֲ�
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTotalTestRoom(Page page, String batchId,
			String edutype_id, String major_id, String site_id)
			throws PlatformException;

	public abstract int getTotalTestRoomNums(String batchId, String edutype_id,
			String major_id, String site_id) throws PlatformException,
			PlatformException;

	public abstract List getTotalTestRoom(Page page, String batchId,
			String site_id) throws PlatformException;

	public abstract int getTotalTestRoomNums(String batchId, String site_id)
			throws PlatformException, PlatformException;

	public abstract List getEdutypeMajorTestDesk(String batchId,
			String edutype_id, String major_id, String card_no, String site_id)
			throws PlatformException;

	public abstract List getTestRooms(Page page, String batchId, String site_id)
			throws PlatformException;

	public abstract List getTestDesks(Page page, String room_id)
			throws PlatformException;

	/**
	 * רҵ���������б�
	 * 
	 * @return רҵ���������б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitMajorSorts(String majorId)
			throws PlatformException;

	/**
	 * ����¼ȡ���ͳ��
	 * 
	 * @return ����¼ȡ���ͳ��
	 * @throws PlatformException
	 * @throws PlatformException
	 * @throws SQLException
	 * @throws PlatformException
	 * @throws SQLException
	 */
	public abstract HashMap getRecruitStat(String batchId)
			throws PlatformException, SQLException;

	/**
	 * ¼ȡѧ����Ϣ����
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getPassStatistic(Page page, String batch_id,
			String site_id, String major_id, String edutype_id)
			throws PlatformException;

	public abstract int getPassStatisticNum(String batch_id, String site_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	public abstract List getPassStuId(String batch_id, String status,
			String major_id, String edutype_id, String site_id,
			String pass_status) throws PlatformException;

	public abstract int updateRecruitStudentPassstatus(String stuId,
			String passstatus) throws PlatformException;

	public abstract List getTotalStu(Page page, String site_id,
			String batch_id, String major_id, String edutype_id,
			List orderProperty) throws PlatformException;

	public abstract List getStatStudents(String site_id, String batch_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	public abstract int getTotalStuNum(String site_id, String batch_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	public abstract int getTotalStuPassNum(String site_id, String batch_id,
			String major_id, String edutype_id, String status)
			throws PlatformException;

	public abstract int updateStudentStatus(String status, String student_id)
			throws PlatformException;

	public abstract int updateStudentStatus(String status, String student_id,
			String grade_id) throws PlatformException;

	public abstract List getMatricaluteCondition(Page page, String batch_id,
			String major_id, String edutype_id, String sort_id)
			throws PlatformException;

	public abstract int getMatricaluteConditionNum(String batch_id,
			String major_id, String edutype_id, String sort_id)
			throws PlatformException;

	public abstract int updateStudentStatusBatch(String batch_id,
			String major_id, String edutype_id, String grade_id)
			throws PlatformException;

	public abstract List getMatricaluteCourse(Page page, String major_id,
			String batch_id, List orderProperty) throws PlatformException,
			PlatformException;

	public abstract String getIdByMajorandBatch(String batch_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	public abstract int deleteByMajorandBatch(String batch_id, String major_id,
			String edutype_id) throws PlatformException;

	public abstract int deleteByMajor(String mc_id) throws PlatformException,
			PlatformException;

	public abstract int updateMatricaluteCondition(String batch_id,
			String major_id, String edutype_id, String score, String score1,
			String photo_status, String idcard_status,
			String graduatecard_status) throws PlatformException;

	public abstract int setMatricaluteCondition(String mc_id, String course_id,
			String score) throws PlatformException;

	/**
	 * ����aid�õ�����ѧ����Ϣ
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitTestStudent getTestStudent(String aid)
			throws PlatformException;

	/**
	 * ע�����ͳ��
	 * 
	 * @return ע�����ͳ��
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRegisterStat(Page page, String batchId,
			String siteId, String majorId, String eduTypeId)
			throws PlatformException;

	public abstract List getMatricaluteCourseScore(String mc_id)
			throws PlatformException;

	/**
	 * ע�����ͳ��
	 * 
	 * @return ע�����ͳ��
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRegisterStatNum(String batchId, String siteId,
			String majorId, String eduTypeId) throws PlatformException,
			PlatformException;

	public abstract List getMatricaluteConditionTotalScore(String batch_id,
			String major_id, String edutype_id) throws PlatformException,
			PlatformException;

	/**
	 * �޸Ŀ��Կγ̵Ŀ���ʱ��
	 * 
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int updateRecruitTestCourse(String id, String startTime,
			String endTime) throws PlatformException;

	/**
	 * ��ȡ����ͳ��
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getSignStatistic(Page page, String batch_id,
			String site_id, String major_id) throws PlatformException;

	public abstract List getSignStatisticBySite(String batch_id)
			throws PlatformException;

	public abstract List getSignStatisticByMajor(String batch_id)
			throws PlatformException;

	/**
	 * ��ȡѧ����Ϣ
	 * 
	 * @throws PlatformException
	 */
	public abstract List getStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status) throws PlatformException;

	public abstract List getStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status, String photo_status)
			throws PlatformException;

	/**
	 * ��ȡѧ����Ϣ
	 * 
	 * @throws PlatformException
	 */
	public abstract List getFreeStudents(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String considertype_status)
			throws PlatformException;

	/**
	 * ��ȡѧ����
	 */
	public abstract int getStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	public abstract int getStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status, String photo_status);

	/**
	 * ��ȡѧ����Ϣ
	 * 
	 * @throws PlatformException
	 */
	public abstract List getTestStudents(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status)
			throws PlatformException;

	public abstract List getTestStudents(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status,
			String considertype_status) throws PlatformException;

	/**
	 * ��ȡѧ����
	 */
	public abstract int getTestStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	/**
	 * ��ȡ����ѧ����
	 */
	public abstract int getFreeStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String considertype_status);

	/**
	 * ��ȡѧ����
	 */
	public abstract RecruitStudent getStudent(String id)
			throws PlatformException;

	/**
	 * ȷ��ѧ��������Ϣ
	 */
	public abstract int confirmFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException;

	/**
	 * ȡ��ȷ��ѧ��������Ϣ
	 */
	public abstract int unConfirmFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException;

	public abstract int rejectFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException;

	/**
	 * ��ȡ����ͳ������
	 * 
	 * @throws PlatformException
	 */
	public abstract int getSignStatisticNum(String batch_id, String site_id,
			String major_id) throws PlatformException;

	public abstract List getStudentsByMajorEdutype(String batchId,
			String siteId, String majorId, String eduTypeId)
			throws PlatformException;

	/**
	 * ���������κͲ�λ�ȡ����רҵ
	 * 
	 * @throws PlatformException
	 */
	public abstract List getMajors(String batch_id, String edu_type_id)
			throws PlatformException;

	/**
	 * ���������κͲ�λ�ȡ����רҵ����
	 * 
	 * @throws PlatformException
	 */
	public abstract int getMajorsNum(String batch_id, String edu_type_id)
			throws PlatformException;

	/**
	 * ���������κͲ�λ�ȡ����רҵ
	 * 
	 * @throws PlatformException
	 */
	public abstract List getMajors(Page page, String batch_id,
			String edu_type_id) throws PlatformException;

	/**
	 * ɾ���������κͲ�λ�ȡ����רҵ
	 * 
	 * @throws PlatformException
	 */
	public abstract int deleteMajors(String batch_id, String edu_type_id)
			throws PlatformException;

	public abstract int deleteMajors(String batch_id, String edu_type_id,
			String major_id) throws PlatformException;

	/**
	 * ɾ���������κͲ�λ�ȡ����רҵ
	 * 
	 * @throws PlatformException
	 */
	public abstract int addMajors(String batch_id, String edu_type_id,
			String major_id) throws PlatformException;

	/**
	 * ��ѯ�Ƿ����������������ò�κ�רҵ
	 * 
	 * @throws PlatformException
	 */
	public abstract int IsExistBatchId(String batch_id)
			throws PlatformException;

	/**
	 * ��ѯ�Ƿ����������������ò�κ�רҵ
	 * 
	 * @throws PlatformException
	 */
	public abstract List getAddBatchMajors(Page page, String batchId,
			String edu_type_id) throws PlatformException;

	public abstract int getAddBatchMajorsNum(String batchId, String edu_type_id)
			throws PlatformException;

	public abstract int addRecruitNoExamCondition(String name, String note)
			throws PlatformException;

	public abstract int deleteRecruitNoExamCondition(String id)
			throws PlatformException;

	public abstract RecruitNoExamCondition getRecruitNoExamCondition(String aid)
			throws PlatformException;

	public abstract List getRecruitNoExamConditions(Page page)
			throws PlatformException;

	public abstract int getRecruitNoExamConditionsNum()
			throws PlatformException;

	public abstract int updateRecruitNoExamCondition(String id, String name,
			String note) throws PlatformException;

	/**
	 * ��ȡ��������
	 */
	public abstract int getStudentCharacter(String id, String signDate)
			throws PlatformException;

	/**
	 * �ϴ���Ƭ
	 */
	public abstract int uploadImage(String card_no, String filename)
			throws PlatformException;

	/**
	 * �����ϴ���Ƭ
	 */
	public abstract int uploadBatchImage(String card_no, String filename)
			throws PlatformException;

	/**
	 * �ϴ����֤
	 */
	public abstract int uploadIdCard(String card_no, String filename)
			throws PlatformException;

	/**
	 * �ϴ���ҵ֤
	 */
	public abstract int uploadGraduateCard(String card_no, String filename)
			throws PlatformException;

	public abstract List getStudentPhotos(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status)
			throws PlatformException;

	public abstract int getStudentPhotosNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	/**
	 * ɾ����Ƭ
	 */
	public abstract int deleteStudentPhoto(String user_id, String note)
			throws PlatformException;

	public abstract int confirmStudentPhoto(String user_id)
			throws PlatformException;

	public abstract int confirmStudentPhoto(String user_id, String status)
			throws PlatformException;

	public abstract List getRejectStudentPhotos(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status)
			throws PlatformException;

	public abstract int getRejectStudentPhotosNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	/**
	 * ����ѧ����ѧϰ����
	 * @param status
	 * @param student_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateStudentSite(String site, String student_id)
			throws PlatformException;

	/**
	 * �ж�ת���ѧϰ�����Ƿ����ѧ��ԭ����רҵ�Ͳ��
	 * @param change_siteId  ת���վ��ID
	 * @param batchId       ��������
	 * @param stu_Id       ѧ��ID
	 * @return
	 */
	public abstract int judgeMajorAndEdu(String change_siteId, String batchId,
			String stu_Id);
	 /**
	  * ����ѧ����ѧ�ŷ���״̬
	  * 1Ϊѧ�ŷ�������Ϊѧ��δ����
	  * @return
	  * @throws PlatformException
	  */
	
	public abstract int releaseRegNoAndPwd(String stuID,String status) throws PlatformException;
	
	/**
	 * ��ȡĳһѧ��״̬ѧ����Ŀ
	 * 
	 * @return ��ȡѧ��ѧ��״̬ѧ����Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getRecruitStudentsStudyStatusNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo,String studyStatus) throws PlatformException;
	
	/**
	 * ��ȡĳһѧ��״̬ѧ��
	 * 
	 * @return ��ȡѧ��ѧ��״̬ѧ��
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getRecruitStudentsStudyStatus(Page page,String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo,String studyStatus) throws PlatformException;
	
	public abstract int updateStudentStudyStatus(String status, String student_id)
	throws PlatformException;
	
	public abstract int releaseMatriculate(String studentId)
	throws PlatformException;

	public abstract int unreleaseMatriculate(String studentId)
	throws PlatformException;
	
	/**
	 * ��ȡ¼ȡ״̬������δ����ѧ����Ŀ
	 * 
	 * @return ��ȡ¼ȡ״̬������δ����ѧ����Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getReleaseStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo,String releaseStatus,String studyStatus) throws PlatformException;
	
	/**
	 * ��ȡ¼ȡ״̬������δ����ѧ��
	 * 
	 * @return ��ȡ¼ȡ״̬������δ����ѧ��
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getReleaseStudents(Page page,String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo,String releaseStatus,String studyStatus) throws PlatformException;

	/**
	 * ��ȡ�����µ��������
	 */
		public abstract List getBatchEdutypes(String batchID)
			throws PlatformException, NoRightException;
		
	/**
	 * ��ȡ�����µ��������
	 */
		
	public abstract List getBatchMajors1(String batchId, String edu_type_id,
			String site_id) throws PlatformException, NoRightException;
	
}


