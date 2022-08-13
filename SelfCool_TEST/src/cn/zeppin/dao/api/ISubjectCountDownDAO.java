package cn.zeppin.dao.api;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SubjectCountdown;

public interface ISubjectCountDownDAO extends IBaseDAO<SubjectCountdown, Integer> {

	/**
	 * 获取考试倒计时
	 * @param map
	 * @return
	 */
	public int getSubjectCountdownCount(Map<String,Object> map);
	
	/**
	 * 获取考试倒计时
	 * @param map
	 * @return
	 */
	public List<SubjectCountdown> getSubjectCountdowns(Map<String,Object> map,int offset,int length);

	/**
	 * 获取下一次考试日期
	 * @param subjectID
	 * @return
	 */
	public Timestamp getNextExamTime(Integer subjectID);
	
}
