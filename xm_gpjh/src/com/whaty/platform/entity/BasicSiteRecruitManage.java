package com.whaty.platform.entity;

import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.entity.recruit.RecruitTestRoom;
import com.whaty.platform.entity.recruit.RecruitTestStudent;
import com.whaty.platform.util.Page;

/**
 * ���������˷�վ����Ա��������ֵĹ���
 * 
 * @author chenjian
 * 
 */

public abstract class BasicSiteRecruitManage {

	/**
	 * ��ȡ����ƻ�
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract List getSitePlans(Page page, String site_id, String active)
			throws NoRightException;

	public abstract RecruitBatch getRecruitBatch(java.lang.String aid)
			throws NoRightException;

	/**
	 * ��ȡ����ƻ���
	 */
	public abstract int getPlansNum(String site_id, String active);

	/**
	 * ɾ������ƻ�
	 */
	public abstract int deletePlan(String planId) throws NoRightException,
			PlatformException;

	/**
	 * ��ɾ������ƻ�
	 */
	public abstract String[][] batchDeletePlan(String[] planIds)
			throws NoRightException, PlatformException;

	public abstract int updateRecruitPlanBatch(String[] id, String[] num)
			throws PlatformException, NoRightException;

	/**
	 * ��ȡ����ͳ��
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract List getSignStatistic(Page page, String batch_id,
			String site_id) throws NoRightException;

	/**
	 * ��ȡ����ͳ������
	 * 
	 * @throws NoRightException
	 */
	public abstract int getSignStatisticNum(String batch_id, String site_id)
			throws NoRightException;

	/**
	 * ��ȡ��ǰ�������
	 */
	public abstract List getActiveBatch() throws NoRightException;
	
	public abstract List getBatches() throws NoRightException;
	/**
	 * ��������б�
	 * 
	 * @param page
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getRecruitBatches(Page page) throws PlatformException;
	/**
	 * ��ȡѧ����Ϣ
	 * 
	 * @throws NoRightException
	 */
	public abstract List getStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status) throws NoRightException;

	public abstract List getStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status, String photo)
			throws NoRightException;

	/**
	 * ��ȡѧ����Ϣ
	 * 
	 * @throws NoRightException
	 */
	public abstract List getFreeStudents(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String considertype_status)
			throws NoRightException;

	/**
	 * ��ȡѧ����
	 */
	public abstract int getStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);

	public abstract int getStudentsNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status, String photo);

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
			throws NoRightException;

	public abstract List getStudentsByMajorEdutype(Page page, String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException;

	public abstract int getStudentsByMajorEdutypeNum(String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException;

	public abstract List getConferStudentsByMajorEdutype(Page page,
			String batchId, String siteId, String majorId, String eduTypeId)
			throws NoRightException;

	public abstract int getConferStudentsByMajorEdutypeNum(String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException;

	public abstract List getConferFreeStudentsByMajorEdutype(Page page,
			String batchId, String siteId, String majorId, String eduTypeId)
			throws NoRightException;

	public abstract int getConferFreeStudentsByMajorEdutypeNum(String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException;

	/**
	 * ȷ��ѧ����Ϣ
	 */
	public abstract int confirmStudent(String[] studentIds)
			throws PlatformException, NoRightException;

	/**
	 * ȷ��ѧ��������Ϣ
	 */
	public abstract int confirmFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException, NoRightException;

	/**
	 * ȡ��ȷ��ѧ����Ϣ
	 */
	public abstract int unConfirmStudent(String[] studentIds)
			throws PlatformException, NoRightException;

	/**
	 * ȡ��ȷ��ѧ��������Ϣ
	 */
	public abstract int unConfirmFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException, NoRightException;

	public abstract int rejectFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException, NoRightException;

	/**
	 * ��ȡѧ���Կγ�
	 */
	public abstract List getStudentTestCourse(Page page, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id) throws PlatformException, NoRightException;

	/**
	 * ��ȡѧ���Կγ���Ŀ
	 */
	public abstract int getStudentTestCoursesNum(String name, String card_no,
			String zglx, String edu_type_id, String major_id, String site_id)
			throws PlatformException, NoRightException;

	/**
	 * �ϴ���Ƭ
	 */
	public abstract int uploadImage(String card_no, String filename)
			throws PlatformException, NoRightException;

	/**
	 * ���ϴ���Ƭ
	 */
	public abstract int uploadBatchImage(String card_no, String filename)
			throws PlatformException, NoRightException;

	/**
	 * �ϴ����֤
	 */
	public abstract int uploadIdCard(String card_no, String filename)
			throws PlatformException, NoRightException;

	/**
	 * �ϴ���ҵ֤
	 */
	public abstract int uploadGraduateCard(String card_no, String filename)
			throws PlatformException, NoRightException;

	/**
	 * ��ȡ���רҵ�¿���������ͳ����Ϣ
	 */
	public abstract List getTotalTestRoom(Page page, String batchId,
			String edutype_id, String major_id, String site_id)
			throws PlatformException, NoRightException;

	public abstract int getTotalTestRoomNums(String batchId, String edutype_id,
			String major_id, String site_id) throws PlatformException,
			NoRightException;

	public abstract List getEdutypeMajorTestDesk(String batchId,
			String edutype_id, String major_id, String card_no, String site_id,
			String testroom_id) throws PlatformException, NoRightException;

	public abstract List getEdutypeMajorTestDesk(String batchId,
			String edutype_id, String major_id, String card_no, String site_id,
			String testroom_id,String reg_no) throws PlatformException, NoRightException;
	
	public abstract void allotStudents(String batch_id, String site_id,
			String numroom) throws PlatformException, NoRightException;

	/**
	 * ��ȡ����µ�������
	 */
	public abstract List getBatchEdutypes(String batchID)
			throws PlatformException, NoRightException;

	public abstract List getBatchMajors(String batchId, String edu_type_id)
			throws PlatformException, NoRightException;

	public abstract List getBatchMajors1(String batchId, String edu_type_id,
			String site_id) throws PlatformException, NoRightException;

	public abstract List getBatchEduTypeMajors(String batchId,
			String edu_type_id) throws PlatformException, NoRightException;

	public abstract List getMajors(String batch_id, String edu_type_id)
			throws PlatformException, NoRightException;

	public abstract List getRecruitNoExamConditions(Page page)
			throws NoRightException;

	public abstract List getRecruitSorts() throws PlatformException;

	/**
	 * ��ȡĳվ��,��κͿ����µĿ����ֲ���
	 */
	public abstract List getExamroomDisplay(Page page, String siteId,
			String batchId, String sortId) throws PlatformException,
			NoRightException;

	public abstract int getExamroomDisplayNum(String siteId, String batchId,
			String sortId) throws PlatformException, NoRightException;
	
	public abstract List getExamroomDisplay2(Page page, String siteId,
			String batchId, String sortId) throws PlatformException,
			NoRightException;

	public abstract int getExamroomDisplayNum2(String siteId, String batchId,
			String sortId) throws PlatformException, NoRightException;
	public abstract List getExamroomDisplay2(Page page, String siteId,
			String batchId, String sortId,String edutype_id) throws PlatformException,
			NoRightException;

	public abstract int getExamroomDisplayNum2(String siteId, String batchId,
			String sortId,String edutype_id) throws PlatformException, NoRightException;

	public abstract List getExamroomDisplay(Page page, String siteId,
			String batchId, String sortId,String edutype_id) throws PlatformException,
			NoRightException;

	public abstract int getExamroomDisplayNum(String siteId, String batchId,
			String sortId,String edutype_id) throws PlatformException, NoRightException;

	public abstract int updateSite(String id, String site)
			throws PlatformException, NoRightException;

	public abstract List getExamroomDisplayInfo(String testroomId)
			throws PlatformException, NoRightException;

	public abstract List getExamroomDisplayInfo1(String testroomId)
			throws PlatformException, NoRightException;

	public abstract List getExamroomDisplayInfo2(String testroomId)
			throws PlatformException, NoRightException;

	public abstract RecruitTestRoom getRecruitTestroom(String testroomId)
			throws PlatformException, NoRightException;

	/**
	 * ȫ��ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ȫ��ѧ���б�
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getTotalRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException, NoRightException;

	/**
	 * ¼ȡѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ¼ȡѧ���б�
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String status, String pass_status)
			throws PlatformException, NoRightException;

	/**
	 * δ¼ȡѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return δ¼ȡѧ���б�
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getUnPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String status, String pass_status)
			throws PlatformException, NoRightException;

	public abstract List getUnPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	public abstract int getUnPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException;

	/**
	 * ȫ��ѧ����Ŀ
	 * 
	 * @return ȫ��ѧ����Ŀ
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract int getTotalRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException, NoRightException;

	/**
	 * ¼ȡѧ����Ŀ
	 * 
	 * @return ¼ȡѧ����Ŀ
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract int getPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String status, String pass_status)
			throws PlatformException, NoRightException;

	/**
	 * δ¼ȡѧ����Ŀ
	 * 
	 * @return δ¼ȡѧ����Ŀ
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract int getUnPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String status, String pass_status)
			throws PlatformException, NoRightException;

	/**
	 * ѧ��ɼ���Ŀ
	 * 
	 * @return ѧ��ɼ���Ŀ
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract int getRecruitStudentScoresNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String signDate, String score_status)
			throws PlatformException, NoRightException;

	/**
	 * ѧ��ɼ��б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ��ɼ��б�
	 * @throws NoRightException
	 * @throws NoRightException
	 */
	public abstract List getRecruitStudentScores(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String signDate, String score_status)
			throws PlatformException, NoRightException;

	public abstract List getRejectStudentPhotos(Page page, String batchId,
			String name, String card_no, String zglx, String edu_type_id,
			String major_id, String site_id, String status)
			throws NoRightException;

	public abstract int getRejectStudentPhotosNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status);
	
	/**
	 * ��ѯδע��ѧ����Ŀ
	 * 
	 * @return ��ѯδע��ѧ����Ŀ
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract int getUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo) throws PlatformException;
	public abstract int getUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo,String gender) throws PlatformException;
	/**
	 * δע��ѧ���б�
	 * 
	 * @param page
	 *            ��ҳ������
	 * @return ѧ��ɼ��б�
	 * @throws PlatformException
	 * @throws PlatformException
	 */
	public abstract List getUnRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String regNo) throws PlatformException,
			PlatformException;
	public abstract List getUnRegisterPassRecruitStudents(Page page,
			String siteId, String batchId, String majorId, String eduTypeId,
			String name, String regNo,String gender) throws PlatformException,
			PlatformException;
	/**
	 * ���aid�õ�����ѧ����Ϣ
	 * 
	 * @param aid
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitTestStudent getTestStudent(String aid)
			throws PlatformException;

}
