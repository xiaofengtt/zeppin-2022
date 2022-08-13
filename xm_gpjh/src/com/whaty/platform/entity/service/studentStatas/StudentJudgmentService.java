package com.whaty.platform.entity.service.studentStatas;

import com.whaty.platform.entity.exception.EntityException;

public interface StudentJudgmentService {

	/**
	 * 教学计划公共必修课学生所得学分判断
	 * @param id
	 * 		学生ID 
	 * @return
	 * @throws EntityException
	 */
	public abstract boolean matchpublicrequired(String id)
			throws EntityException;

	/**
	 * 教学计划专业必修课学生所得学分判断
	 * @param id
	 * 		学生ID 
	 * @return
	 * @throws EntityException
	 */
	public abstract boolean matchmajorrequired(String id)
			throws EntityException;

	/**
	 * 教学计划专业选修课学生所得学分判断
	 * @param id
	 * 		学生ID 
	 * @return
	 * @throws EntityException
	 */
	public abstract boolean matchmajoralternation(String id)
			throws EntityException;

	/**
	 * 教学计划公共选修课学生所得学分判断
	 * @param id
	 * 		学生ID 
	 * @return
	 * @throws EntityException
	 */
	public abstract boolean matchpublicalternation(String id)
			throws EntityException;

	/**
	 * 总学分的毕业判断 
	 * @param id 
	 * 		学生ID
	 * @return
	 */
	public abstract boolean matchTotalCredit(String id) throws EntityException;

	/**
	 * 学生是否符合毕业条件的判断
	 * @param id
	 * @return
	 * @throws EntityException
	 */
	public abstract boolean matchGraduation(String id) throws EntityException;

	/**
	 * 学生是否符合获得学位的判断
	 * @param id
	 * @return
	 * @throws EntityException
	 */
	public abstract boolean matchDegree(String id) throws EntityException;

	/**
	 * 获得总学分
	 * @param id
	 * @return
	 */
	public abstract double getTotalCredit(String id);

	/**
	 * 获得平均学分
	 * @param id
	 * @return
	 */
	public abstract double getAverageScore(String id);

}