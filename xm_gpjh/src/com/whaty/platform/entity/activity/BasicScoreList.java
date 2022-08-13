package com.whaty.platform.entity.activity;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.activity.score.ScoreModify;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

public abstract class BasicScoreList {

	/**
	 * @param page
	 * @param searchproperties
	 * @param orderproperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchElectiveScores(Page page, List searchproperties,
			List orderproperties) throws PlatformException;

	/**
	 * @param searchproperties
	 * @param orderproperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract int searchElectiveScoresNum(List searchproperties,
			List orderproperties) throws PlatformException;

	/**
	 * @param scoretype
	 * @param searchproperties
	 * @param orderproperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract List searchMaxElectiveScores(String scoretype,
			List searchproperties, List orderproperties)
			throws PlatformException;

	/**
	 * @param scoretype
	 * @param searchproperties
	 * @param orderproperties
	 * @return
	 * @throws PlatformException
	 */
	public abstract int searchMaxElectiveScoresNum(String scoretype,
			List searchproperties, List orderproperties)
			throws PlatformException;

	/**
	 * @param Student_id
	 * @param major_id
	 * @param edutype_id
	 * @return返回学生成绩
	 * @throws PlatformException
	 */
	public abstract List getStudentScoreReport(String Student_id,
			String major_id, String edutype_id) throws PlatformException;

	public abstract List getStudentScoreReport(String student_id)
			throws PlatformException;

	public abstract List searchElectiveScoresStatics1(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype, String[] sec, String status)
			throws PlatformException;

	public abstract List<ScoreModify> getScoreModifyRecords(Page page,
			List<SearchProperty> searchList, List<OrderProperty> orderList)
			throws PlatformException;

	public abstract int getScoreModifyRecordsNum(List<SearchProperty> searchList)
			throws PlatformException;
}
