package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.SubjectCountdown;

public interface ISubjectCountDownService {
	
	/**
	 * 添加考试倒计时
	 * @param subjectCountDown
	 */
	public void addSubjectCountDown(SubjectCountdown subjectCountDown);
	
	/**
	 * 修改
	 * @param subjectCountDown
	 */
	public void updateSubjectCountDown(SubjectCountdown subjectCountDown);
	
	/**
	 * 获取考试倒计时
	 * @param id
	 * @return
	 */
	public SubjectCountdown getSubjectCountDownById(int id);
	
	
	/**
	 * 删除
	 * 
	 * @param subjectCountDown
	 */
	public void deleteSubjectCountDown(SubjectCountdown subjectCountdown);
	
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
	
}
