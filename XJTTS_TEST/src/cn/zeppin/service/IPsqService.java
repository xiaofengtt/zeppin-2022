package cn.zeppin.service;

import java.util.HashMap;
import java.util.List;

import cn.zeppin.entity.Psq;

public interface IPsqService extends IBaseService<Psq, Integer> {
	
	/**
	 * 获取 问卷列表，根据问卷类型获取
	 * @param type 问卷类型
	 * @return 问卷列表
	 */
	public List<Psq> getPsqByType(short type);
	
	/**
	 * 获取 提交个数
	 * @param psqid
	 * @param projectId
	 * @param trainingSubjectId
	 * @param trainingCollegeId
	 * @return
	 */
	public int getSubmitCount(int psqid,int projectId,int trainingSubjectId,int trainingCollegeId);
	
	public int getPsqByTypeCount(short type);
	
	/**
	 * 获取 问卷列表，根据问卷类型获取分页列表
	 * @param type 问卷类型
	 * @param offset 
	 * @param length
	 * @return
	 */
	public List<Psq> getPsqByTypePage(short type,int offset,int length);
	
	/**
	 * 每题统计数据
	 * @param map
	 * @return
	 */
	public HashMap<String, String[]> getPsqPaper(HashMap<String,String> map);
	
	/**
	 * 汇总统计
	 * @param map
	 * @return
	 */
	public List getPsqSummary(HashMap<String,String> map);
	
	/**
	 * 对比统计
	 * @param map
	 * @return
	 */
	public List getPsqContrast(HashMap<String,String> map);
	
	/**
	 * 满意率分析
	 * 
	 * @param map
	 * @return
	 */
	public List getPsqManyiLv(HashMap<String, String> map, String tableName);
	
	/**
	 * 评审率分析
	 * 
	 * @param map
	 * @return
	 */
	public List getExpertPsqManyiLv(HashMap<String, String> map, String tableName);
	
	/**
	 * 获取培训科目，承训单位
	 * @param projectId
	 * @return
	 */
	public List getPsqSearchTraining(String projectId);
}
