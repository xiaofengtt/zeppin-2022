package com.whaty.platform.entity;

import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.activity.score.ScoreModify;
import com.whaty.platform.entity.activity.score.ScoreSetTime;
import com.whaty.platform.util.Page;

public abstract class BasicScoreManage {

	/**
	 * ��ѯѧ��ɼ�
	 * 
	 * @param pageover
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveScores(Page pageover,
			String open_course_id, String major_id, String site_id,
			String edu_type_id, String grade_id) throws PlatformException;

	/**
	 * ��ѯѧ��ɼ�
	 * 
	 * @param pageover
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveScores(Page pageover,
			String open_course_id, String major_id, String site_id,
			String edu_type_id, String grade_id, String semester_id)
			throws PlatformException;

	/**
	 * ��ѯѧ��ɼ�
	 * 
	 * @param pageover
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveScores(Page pageover,
			String open_course_id, String major_id, String site_id,
			String edu_type_id, String grade_id, String semester_id,
			String cardNo, String course_id) throws PlatformException;

	public abstract List getElectiveScores(Page pageover,
			String open_course_id, String major_id, String site_id,
			String edu_type_id, String grade_id, String semester_id,
			String cardNo, String course_id, String free_status)
			throws PlatformException;

	/**
	 * ͳ��ѧ��ɼ�
	 * 
	 * @param pageover
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveScoresStaticsTotalScore(Page pageover,
			String semester_id, String course_id, String site_id,
			String grade_id, String edu_type_id, String major_id, String status)
			throws PlatformException;

	public abstract List getElectiveScoresStaticsExamScore(Page pageover,
			String semester_id, String course_id, String site_id,
			String grade_id, String edu_type_id, String major_id, String status)
			throws PlatformException;

	public abstract List getElectiveExpendScores(Page pageover,
			String open_course_id, String major_id, String site_id,
			String edu_type_id, String grade_id, String semester_id,
			String cardNo, String course_id, String expend_score_student_status)
			throws PlatformException;

	/**
	 * ��ѯѧ��ɼ�
	 * 
	 * @param pageover
	 * @param course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @param num
	 * @param order
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveScores(Page pageover, String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, int num, String order)
			throws PlatformException;

	/**
	 * ��ѯѧ��ɼ�
	 * 
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveScores(String reg_no)
			throws PlatformException;

	/**
	 * ��ѯѧ���Բ�ɼ�
	 * 
	 * @param studentId
	 * @param teachClassId
	 * @return
	 * @throws PlatformException
	 */
	public abstract String getTestScore(String studentId, String teachClassId)
			throws PlatformException;

	/**
	 * ���շ����߲�ѯѧ�����3ɼ�
	 * 
	 * @param pageover
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @param total_line
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getTotalElectiveScores(Page pageover,
			String open_course_id, String major_id, String site_id,
			String edu_type_id, String grade_id, String semester_id,
			String total_line) throws PlatformException;

	/**
	 * ��ѯѧ��ĳ��ɼ���߷�
	 * 
	 * @param scoretype
	 * @param searchproperties
	 * @param orderproperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveMaxScores(String scoretype,
			List searchproperties, List orderproperties)
			throws PlatformException;

	/**
	 * ��ѯѧ��ƽʱ�ɼ���߷�
	 * 
	 * @param semester_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveMaxUsualScores(String semester_id)
			throws PlatformException;

	/**
	 * ��ѯѧ��ĳ��ɼ���߷���Ŀ
	 * 
	 * @param scoretype
	 * @param searchproperties
	 * @param orderproperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveMaxScoresNum(String scoretype,
			List searchproperties, List orderproperties)
			throws PlatformException;

	/**
	 * ��ѯѧ��ƽʱ�ɼ���߷���Ŀ
	 * 
	 * @param semester_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveMaxUsualScoresNum(String semester_id)
			throws PlatformException;

	/**
	 * ��ѯѧ��ɼ���Ŀ
	 * 
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveScoresNum(String open_course_id,
			String major_id, String site_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
	 * ��ѯѧ��ɼ���Ŀ
	 * 
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveScoresNum(String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id) throws PlatformException;

	/**
	 * ��ѯѧ��ɼ���Ŀ
	 * 
	 * @param course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @param num
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveScoresNum(String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String cardNo, String course_id)
			throws PlatformException;

	public abstract int getElectiveScoresNum(String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String cardNo,
			String course_id, String free_status) throws PlatformException;

	public abstract int getElectiveExpendScoresNum(String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String cardNo,
			String course_id, String expend_score_student_status)
			throws PlatformException;

	public abstract int getTotalElectiveScoresNum(String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,
			String reg_no, String name) throws PlatformException;

	public abstract int getTotalElectiveScoresNum(String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,
			String reg_no, String name, String expend_score_student_status)
			throws PlatformException;

	public abstract List getTotalElectiveScores(Page pageover,
			String course_id, String major_id, String site_id,
			String edu_type_id, String grade_id, String semester_id,
			String total_line, String reg_no, String name)
			throws PlatformException;

	public abstract List getPassTotalElectiveScores(String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,
			String reg_no, String name) throws PlatformException;

	public abstract List getTotalElectiveScores(Page pageover,
			String course_id, String major_id, String site_id,
			String edu_type_id, String grade_id, String semester_id,
			String total_line, String reg_no, String name,
			String student_expend_score_status) throws PlatformException;

	public abstract List getElectiveScoresStaticsScore(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype) throws PlatformException;

	public abstract List getElectiveScoresStaticsScore(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype, String[] sec) throws PlatformException;

	public abstract List getElectiveScoresStaticsScore(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype, String[] sec, String status)
			throws PlatformException;

	/**
	 * ��ѯѧ��ɼ���Ŀ
	 * 
	 * @param course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @param num
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveScoresNum(String course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, int num) throws PlatformException;

	/**
	 * ��ѯѧ��ɼ���Ŀ
	 * 
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveScoresNum(String reg_no)
			throws PlatformException;

	/**
	 * ��ѯѧ�����3ɼ���Ŀ
	 * 
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @param total_line
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getTotalElectiveScoresNum(String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line)
			throws PlatformException;

	/**
	 * ���޸�ѧ��ɼ�
	 * 
	 * @param total_score
	 * @param usual_score
	 * @param exam_score
	 * @param expend_score
	 * @param renew_score
	 * @param status
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateScoreBatch(String[] total_score,
			String[] usual_score, String[] exam_score, String[] expend_score,
			String[] renew_score, String[] score_status, String[] id)
			throws PlatformException;

	/**
	 * ���޸�ѧ��ɼ�
	 * 
	 * @param total_score
	 * @param usual_score
	 * @param exam_score
	 * @param expend_score
	 * @param renew_score
	 * @param status
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateScoreBatch(String[] total_score,
			String[] usual_score, String[] exam_score, String[] expend_score,
			String[] experiment_score, String[] renew_score,
			String[] score_status, String[] id) throws PlatformException;

	public abstract int updateScoreBatch(String[] total_score,
			String[] usual_score, String[] exam_score, String[] expend_score,
			String[] experiment_score, String[] test_score,
			String[] renew_score, String[] score_status, String[] id)
			throws PlatformException;

	public abstract int updateScoreBatch(String[] total_score,
			String[] usual_score, String[] exam_score, String[] expend_score,
			String[] experiment_score, String[] test_score,
			String[] total_expend_score, String[] renew_score,
			String[] score_status, String[] id) throws PlatformException;

	/**
	 * ���޸�ѧ��ɼ�
	 * 
	 * @param total_scoreList
	 * @param usual_scoreList
	 * @param exam_scoreList
	 * @param expend_scoreList
	 * @param renew_scoreList
	 * @param idList
	 * @param reg_noList
	 * @param nameList
	 * @param open_course_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract void updateScoreBatch(List total_scoreList,
			List usual_scoreList, List exam_scoreList, List expend_scoreList,
			List experiment_score, List renew_scoreList, List idList,
			List reg_noList, List nameList, String open_course_id)
			throws PlatformException;

	/**
	 * ���޸�ѧ��ɼ�
	 * 
	 * @param total_scoreList
	 * @param usual_scoreList
	 * @param exam_scoreList
	 * @param expend_scoreList
	 * @param renew_scoreList
	 * @param idList
	 * @param reg_noList
	 * @param nameList
	 * @param open_course_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract void updateScoreBatch(List total_scoreList,
			List usual_scoreList, List exam_scoreList, List expend_scoreList,
			List renew_scoreList, List idList, List reg_noList, List nameList,
			String open_course_id) throws PlatformException;

	public abstract void updateScoreBatch(List total_scoreList,
			List usual_scoreList, List exam_scoreList, List expend_scoreList,
			List renew_scoreList, List idList, List reg_noList, List nameList,
			String open_course_id, String free_total_score_status)
			throws PlatformException;

	public abstract void updateScoreBatch(List total_scoreList,
			List usual_scoreList, List exam_scoreList, List expend_scoreList,
			List experiment_score, List test_scoreList, List renew_scoreList,
			List idList, List reg_noList, List nameList, String open_course_id)
			throws PlatformException;

	/**
	 * �����޸�ѧ��ɼ�
	 * 
	 * @param total_score
	 * @param usual_score
	 * @param exam_score
	 * @param expend_score
	 * @param renew_score
	 * @param score_status
	 * @param elective_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateScore(String total_score, String usual_score,
			String exam_score, String expend_score, String renew_score,
			String elective_id) throws PlatformException;

	/**
	 * �����޸�ѧ��ɼ�
	 * 
	 * @param total_score
	 * @param usual_score
	 * @param exam_score
	 * @param expend_score
	 * @param renew_score
	 * @param score_status
	 * @param elective_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int updateScore(String total_score, String usual_score,
			String exam_score, String expend_score, String experiment_score,
			String renew_score, String elective_id) throws PlatformException;

	/**
	 * �����ѧ�����3ɼ�
	 * 
	 * @param total_score
	 * @param usual_score
	 * @param exam_score
	 * @param expend_score
	 * @param renew_score
	 * @param score_status
	 * @param elective_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int modifyTotalScore(String elective_id,
			String total_score, String low_score, String heigh_score)
			throws PlatformException;

	public abstract int modifyTotalScore(String teachclass_id, String[] ctype,
			String[] calculate, String total_score, String low_score,
			String heigh_score) throws PlatformException;

	public abstract int modifyTotalExpendScore(String teachclass_id,
			String[] ctype, String[] calculate, String total_score,
			String low_score, String heigh_score) throws PlatformException;

	public abstract int modifyTotalExpendScore1(String teachclass_id,
			String[] ctype, String[] calculate, String total_score,
			String low_score, String heigh_score) throws PlatformException;

	/**
	 * ���ո�ķ����趨��߷�
	 * 
	 * @param total_score
	 * @param usual_score
	 * @param exam_score
	 * @param expend_score
	 * @param renew_score
	 * @param open_course_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract void updateMaxScore(String total_score, String usual_score,
			String exam_score, String expend_score, String renew_score,
			String open_course_id) throws PlatformException;

	/**
	 * ������7���
	 * 
	 * @param usual_score
	 * @param percent
	 * @param open_course_id
	 * @throws PlatformException
	 */
	public abstract void makeTotalScore(String usual_score, String percent,
			String open_course_id) throws PlatformException;

	/**
	 * ������7���
	 * 
	 * @param usual_score
	 * @param percent
	 * @param open_course_id
	 * @throws PlatformException
	 */
	public abstract int generateTotalScore(String elective_id,
			String CalculateType, String percent, String percent1,
			String percent2, String percent3, String[] calculate)
			throws PlatformException;

	public abstract int generateTotalScore(String teachlass_id, String percent,
			String percent1, String percent2, String percent3)
			throws PlatformException;

	public abstract int generateExpendScore(String teachlass_id,
			String percent, String percent1, String percent2, String percent3)
			throws PlatformException;

	public abstract int generateModifyTotalScore(String elective_id,
			String CalculateType, String[] calculate) throws PlatformException;

	/**
	 * �ı����״̬
	 * 
	 * @throws PlatformException
	 */
	public abstract int updateScoreStatus(String elective_id,
			String usual_score_status, String exam_score_status,
			String total_score_status, String expend_score_status)
			throws PlatformException;

	/**
	 * �ı����״̬
	 * 
	 * @throws PlatformException
	 */
	public abstract int updateScoreStatus(String elective_id, String strsql)
			throws PlatformException;

	/**
	 * �ı��Ƿ񲻿�״̬
	 * 
	 * @param usual_score
	 * @param percent
	 * @param open_course_id
	 * @throws PlatformException
	 */
	public abstract int updateStudentExpendScoreStatus(String elective_id,
			String expend_score_status) throws PlatformException;

	/**
	 * ��ɲ����ɼ��б�
	 * 
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @param semester_id
	 * @param total_line
	 * @return
	 * @throws PlatformException
	 */
	public abstract void makeExpendList(String open_course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, String total_line) throws PlatformException;

	/**
	 * ��ѯѧ��ɼ�
	 * 
	 * @param elective_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract Elective getElective(String elective_id)
			throws PlatformException;

	public abstract int modifyScoreReason(String elective_id, String new_score,
			String student_id, String which_score, String old_score,
			String status, String techer_name, String reason)
			throws PlatformException;

	public abstract int modifyScoreReason(String elective_id, String new_score,
			String student_id, String which_score, String old_score,
			String status, String techer_name, String reason, String teacher_id)
			throws PlatformException;

	/**
	 * ����޸ĳɼ�״̬
	 * 
	 * @param elective_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScoreModify getScoreModifyStatus(String elective_id,
			String which_score, String student_id) throws PlatformException;

	/**
	 * ɾ���Ѿ��޸ĵóɼ�
	 * 
	 * @param elective_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int deleteScoreRecord(String modi_id)
			throws PlatformException;

	/**
	 * ����޸ĳɼ���ϸ��Ϣ
	 * 
	 * @param elective_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScoreModify getScoreModifyInfo(String id)
			throws PlatformException;

	/**
	 * ȷ���Ѿ��޸ĵóɼ�
	 * 
	 * @param elective_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int ConfirmScoreRecord(String elective_id,
			String usual_score, String exam_score, String total_score,
			String expend_score, String renew_score, String modi_id,
			String modi_status) throws PlatformException;

	/**
	 * ȷ���Ѿ��޸ĵóɼ�
	 * 
	 * @param elective_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int ConfirmScoreRecord(String elective_id,
			String usual_score, String exam_score, String total_score,
			String expend_score, String experiment_score, String renew_score,
			String modi_id, String modi_status) throws PlatformException;

	/**
	 * ȷ���Ѿ��޸ĵóɼ�
	 * 
	 * @param elective_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract int ConfirmScoreRecord(String elective_id,
			String usual_score, String exam_score, String total_score,
			String expend_score, String experiment_score, String test_score,
			String total_expend_score, String renew_score, String modi_id,
			String modi_status) throws PlatformException;

	/**
	 * �����ϱ��ɼ�ʱ��
	 * 
	 * @param elective_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScoreSetTime getScoreSetTime(String semester_id)
			throws PlatformException;

	public abstract int updateScoreSetTime(String semester_id,
			String start_usual_time, String end_usual_time,
			String start_experiment_time, String end_experiment_time,
			String start_exam_time, String end_exam_time,
			String status_usual_time, String status_experiment_time,
			String status_exam_time) throws PlatformException;

	public abstract int addScoreSetTime(String semester_id,
			String start_usual_time, String end_usual_time,
			String start_experiment_time, String end_experiment_time,
			String start_exam_time, String end_exam_time,
			String status_usual_time, String status_experiment_time,
			String status_exam_time) throws PlatformException;

	public abstract int updateScoreSetTimeStatus(String semester_id,
			String status, String which_time) throws PlatformException;

	/**
	 * ��ȡѧ��ɼ���
	 * 
	 * @param student_id
	 * @param major_id
	 * @param edutype_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentScoreReport(String student_id,
			String major_id, String edutype_id) throws PlatformException;

	/**
	 * ��ȡѧ��ɼ���
	 * 
	 * @param student_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getStudentScoreReport(String student_id)
			throws PlatformException;

	public abstract List getStudentScoreReport1(String student_id)
			throws PlatformException;

	/**
	 * ��ȡĳ�ſγ̳ɼ��Ƿ񷢲�״̬;
	 * 
	 * @param open_course_id
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract HashMap getScoreStatus(String open_course_id)
			throws PlatformException;

	public abstract int updateFreeScoreBatch(String elect_id,
			String free_total_score, String free_total_score_status)
			throws PlatformException;

	/**
	 * ��ҳ��ȡ�������ĳɼ��޸ļ�¼
	 * 
	 * @param page
	 * @param siteId
	 * @param gradeId
	 * @param majorId
	 * @param eduTypeId
	 * @param semesterId
	 * @param courseId
	 * @param scoreType
	 * @param scoreStatus
	 * @param regNo
	 * @param cardNo
	 * @return
	 * @throws PlatformException
	 */
	public abstract List<ScoreModify> getScoreModifyRecords(Page page,
			String siteId, String gradeId, String majorId, String eduTypeId,
			String semesterId, String courseId, String scoreType,
			String scoreStatus, String regNo, String cardNo)
			throws PlatformException;

	/**
	 * ��ȡ�������ĳɼ��޸ļ�¼��
	 * 
	 * @param siteId
	 * @param gradeId
	 * @param majorId
	 * @param eduTypeId
	 * @param semesterId
	 * @param courseId
	 * @param scoreType
	 * @param scoreStatus
	 * @param regNo
	 * @param cardNo
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getScoreModifyRecordsNum(String siteId, String gradeId,
			String majorId, String eduTypeId, String semesterId,
			String courseId, String scoreType, String scoreStatus,
			String regNo, String cardNo) throws PlatformException;

}
