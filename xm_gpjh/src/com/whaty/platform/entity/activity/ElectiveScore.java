/*
 * ElectiveScore.java
 *
 * Created on 2004��12��30��, ����10:05
 */

package com.whaty.platform.entity.activity;

import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.score.ScoreDef;

/**
 * 
 * @author Administrator
 */
public abstract class ElectiveScore {

	private Elective elective;

	private List scoreList;

	private String score_status;

	/**
	 * ��4��ʶ�ĸ�ɼ���Ϊѧ������ճɼ�
	 */
	private String finalScoreType;

	public Elective getElective() {
		return elective;
	}

	public void setElective(Elective elective) {
		this.elective = elective;
	}

	public List getScoreList() {
		return scoreList;
	}

	public void setScoreList(List scoreList) {
		this.scoreList = scoreList;
	}

	public String getScore_Status() {
		return score_status;
	}

	public void setScore_Status(String score_status) {
		this.score_status = score_status;
	}

	public String getFinalScoreType() {
		return finalScoreType;
	}

	public void setFinalScoreType(String finalScoreType) {
		this.finalScoreType = finalScoreType;
	}

	/**
	 * �õ�ĳ��ѡ����ĳ�����͵ĳɼ�
	 * 
	 * @param scoreType
	 * @return
	 * @throws PlatformException
	 */
	public abstract ScoreDef getCoreScore(String scoreType)
			throws PlatformException;

	public abstract List getCoreScores() throws PlatformException;

	public abstract void updateCoreScore(ScoreDef newScore)
			throws PlatformException;

	public abstract void updateCoreScores(List newScoreList)
			throws PlatformException;

	public abstract void updateCoreScoresById(List newScoreList,String elective_id)
	throws PlatformException;
	public abstract void updateMaxScores(List newScoreList)
			throws PlatformException;

	public abstract int updateScoreBatch(List dataList, List searchProperties)
			throws PlatformException;

	public abstract void updateScoreBatch(List dataList, List idList,
			List reg_noList, List nameList, String open_course_id,
			boolean checkExpend) throws PlatformException;

	public abstract void makeTotalScore(String usual_score, String percent)
			throws PlatformException;

	public abstract void makeExpendList(List searchProperties, String total_line)
			throws PlatformException;
	
	public abstract int updateScoreStatus(String elective_id, String usual_score_status, String exam_score_status, String total_score_status,String expend_score_status)throws PlatformException;
	
	public abstract int updateScoreStatus(String elective_id, String strsql)throws PlatformException;
	
	public abstract int updateStudentExpendScoreStatus(String student_id, String expend_score_status)throws PlatformException;
	
	public abstract String getTestScore(String student_id,String teachclass_id) throws PlatformException ;

	/**
	 * �õ�ĳ��ѡ���³ɼ�״̬�Ƿ񷢲�
	 * 
	 * @param scoreType
	 * @return
	 * @throws PlatformException
	 */
	public abstract HashMap getScoreStatus(String open_course_id) throws PlatformException ;
}
