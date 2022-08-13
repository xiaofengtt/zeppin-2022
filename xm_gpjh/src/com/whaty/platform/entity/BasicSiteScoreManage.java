/**
 * 
 */
package com.whaty.platform.entity;

/**
 * @author wq
 * 
 */

import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.score.ScoreModify;
import com.whaty.platform.entity.activity.score.ScoreSetTime;
import com.whaty.platform.util.Page;

public abstract class BasicSiteScoreManage {
	
	/**��ѯѧ��ɼ�
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveScores(String reg_no)
			throws PlatformException;
	
	/**
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
			String open_course_id, String major_id, 
			String edu_type_id, String grade_id) throws PlatformException;

	/**
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
			String open_course_id, String major_id, 
			String edu_type_id, String grade_id, String semester_id)
			throws PlatformException;
	
	/**��ѯѧ��ɼ�
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
			String major_id, String edu_type_id,
			String grade_id, String semester_id, int num, String order)
			throws PlatformException;

	/**
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
			String open_course_id, String major_id, 
			String edu_type_id, String grade_id, String semester_id,
			String total_line) throws PlatformException;

	
	
	
	public  abstract List getElectiveScoresStaticsScore(String open_course_id,String grade_id,String edutype_id,String site_id,String scoretype)
	throws PlatformException ;
	
	public  abstract List getElectiveScoresStaticsScore(String open_course_id,String grade_id,String edutype_id,String site_id,String scoretype,String[] secs)
	throws PlatformException ;

	/**
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
	 * @param semester_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract List getElectiveMaxUsualScores(String semester_id)
			throws PlatformException;

	/**
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
	 * @param semester_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveMaxUsualScoresNum(String semester_id)
			throws PlatformException;

	/**��ѯѧ��ɼ���Ŀ
	 * @param reg_no
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveScoresNum(String reg_no)
			throws PlatformException;
	
	/**
	 * @param open_course_id
	 * @param major_id
	 * @param site_id
	 * @param edu_type_id
	 * @param grade_id
	 * @return
	 * @throws PlatformException
	 */
	public abstract int getElectiveScoresNum(String open_course_id,
			String major_id, String edu_type_id, String grade_id)
			throws PlatformException;

	/**
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
			String major_id, String edu_type_id,
			String grade_id, String semester_id) throws PlatformException;
	
	/**��ѯѧ��ɼ���Ŀ
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
			String edu_type_id, String grade_id,
			String semester_id, int num) throws PlatformException;

	/**
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
			String major_id, String edu_type_id,
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
			String[] renew_score, String[] score_status, String[] id) throws PlatformException;
		
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
			String[] usual_score, String[] exam_score, String[] expend_score,String[] experiment_score,String[] renew_score, String[] score_status, String[] id)
		throws PlatformException;
	/**throws PlatformException;

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
	public abstract void updateScoreBatch(List total_scoreList,List usual_scoreList, List exam_scoreList, List expend_scoreList, List experiment_score,List renew_scoreList, List idList, List reg_noList, List nameList,String open_course_id) throws PlatformException;

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
	 * @param usual_score
	 * @param percent
	 * @param open_course_id
	 * @throws PlatformException
	 */
	public abstract void makeTotalScore(String usual_score, String percent,
			String open_course_id) throws PlatformException;

	/**
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
			String edu_type_id, String grade_id,
			String semester_id, String total_line) throws PlatformException;
	
  public abstract ScoreSetTime  getScoreSetTime(String semester_id) throws PlatformException;

	public abstract int getElectiveScoresNum(String open_course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, String cardNo,String course_id) throws PlatformException;

	/**��ѯѧ��ɼ�
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
			String edu_type_id, String grade_id, String semester_id,String cardNo,String course_id)
			throws PlatformException;
	
	
	/**����޸ĳɼ�״̬

	 * @param elective_id
	 
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScoreModify  getScoreModifyStatus(String elective_id,String which_score,String student_id) throws PlatformException;


	/**���ĳ�ſγ̳ɼ��Ƿ񷢲�״̬

	 * @param elective_id
	 
	 * @return
	 * @throws PlatformException
	 */
	
	public abstract HashMap getScoreStatus(String open_course_id) throws PlatformException ;
	
	
	/**��ȡѧ��ɼ���

	 * @param student_id
	 * @return
	 * @throws PlatformException
	 */
	public  abstract List getStudentScoreReport(String student_id) throws PlatformException;
	
	public  abstract List getStudentScoreReport1(String student_id) throws PlatformException;
	
	/**��ȡѧ��ɼ���

	 * @param student_id
	 * @param major_id
	 * @param edutype_id
	 * @return
	 * @throws PlatformException
	 */
	public  abstract List getStudentScoreReport(String student_id, String major_id, String edutype_id) throws PlatformException;

	public abstract int getTotalElectiveScoresNum(String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,String reg_no,String name) throws PlatformException;
	
	public abstract int getTotalElectiveScoresNum(String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,String reg_no,String name,String expend_score_student_status) throws PlatformException;
	
	public abstract List getTotalElectiveScores(Page pageover, String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,String reg_no,String name,String student_expend_score_status)
			throws PlatformException ;
	
	
	
	public abstract List getTotalElectiveScores(Page pageover, String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,String reg_no,String name)
			throws PlatformException ;
	
	public abstract List getTotalElectiveScores1(Page pageover, String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,String reg_no,String name)
			throws PlatformException ;
	
	public abstract int getTotalElectiveScoresNum1(String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,String reg_no,String name) throws PlatformException;
}
