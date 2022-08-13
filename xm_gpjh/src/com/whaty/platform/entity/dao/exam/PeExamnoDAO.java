package com.whaty.platform.entity.dao.exam;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeExamno;
import com.whaty.platform.entity.util.Page;

public interface PeExamnoDAO {
	/**
	 * 根据id获得场次信息
	 * @param id
	 * @return
	 */
	public PeExamno getPeExamnoById(String id);
	
	/**
	 * 根据名称获得场次信息
	 * @param name
	 * @return
	 */
	public PeExamno getPeExamnoByName(String name);
	
	/**
	 * 保存和更新场次信息
	 * @param recExam
	 * @return
	 */
	public PeExamno savePeExamno(PeExamno examno);
	
	/**
	 * 删除场次信息
	 * @param recExam
	 */
	public void deletePeExamno (PeExamno examno);
	
	/**
	 * 删除场次信息
	 * @param ids 为考试批次id的list
	 */
	public int deletePeExamnoByIds(List ids);
	
	
	/**
	 * 根据条件获得场次信息
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Page getPeExamnoByPage(DetachedCriteria detachedCriteria, int pageSize,int startIndex);
	
	
	/**
	 * 得到符合条件的场次数目
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getPeExamnoTotalCount(DetachedCriteria detachedCriteria);
	
	/**
	 * 根据条件查找
	 * @param detachedCriteria
	 * @return
	 */
	public List getList(final DetachedCriteria detachedCriteria);
}
