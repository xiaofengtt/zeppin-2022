package com.whaty.platform.entity.dao.recruit;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeRecExam;
import com.whaty.platform.entity.util.Page;


public interface ExamPiCiInfoDao {
	
	/**
	 * 根据id获得考试批次信息
	 * @param id
	 * @return
	 */
	public PeRecExam getPeRecExamById(String id);
	
	/**
	 * 根据名称获得考试批次信息
	 * @param name
	 * @return
	 */
	public PeRecExam getPeRecExamByName(String name);
	
	/**
	 * 保存和更新考试批次信息
	 * @param recExam
	 * @return
	 */
	public PeRecExam savePeRecExam(PeRecExam recExam);
	
	/**
	 * 删除考试批次信息
	 * @param recExam
	 */
	public void deletePeRecExam (PeRecExam recExam);
	
	/**
	 * 删除考试批次信息
	 * @param ids 为考试批次id的list
	 */
	public int deletePeRecExamByIds(List ids);
	
	
	/**
	 * 根据条件获得考试批次信息
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Page getRecExamByPage(DetachedCriteria detachedCriteria, int pageSize,int startIndex);
	
	
	/**
	 * 得到符合条件的考试批次数目
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getRecExamsTotalCount(DetachedCriteria detachedCriteria);
	
	/**
	 * 根据条件查找
	 * @param detachedCriteria
	 * @return
	 */
	public List getList(final DetachedCriteria detachedCriteria);
	
	/**
	 * 设置当前活动考试批次
	 * @param ids
	 * @param column
	 * @param value
	 * @return
	 */
	public int updateColumnByIds(final List ids,final String column,final String value);
}
