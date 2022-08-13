package com.whaty.platform.entity.dao.recruit;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.bean.PeRecExamcourse;
import com.whaty.platform.entity.util.Page;

public interface PeRecExamcourseDao {

	/**
	 * 保存招生考试课程
	 * @param transientInstance
	 * @return
	 */
    public PeRecExamcourse save(PeRecExamcourse transientInstance);
    
    /**
     * 根据id列表 删除招生考试课程
     * @param ids
     */
    public int deletePeRecExamcourseByIds(final List ids);
    
    /**
     * 删除单个招生考试课程
     * @param persistentInstance
     */
	public void delete(PeRecExamcourse persistentInstance);
	
	/**
	 * 根据id查找招生考试课程
	 * @param id
	 * @return
	 */
    public PeRecExamcourse getPeRecExamcourseById( java.lang.String id);
    
    /**
     * 根据name查找招生考试课程;
     * @param instance
     * @return
     */
    /**
     * 根据条件查找
     * 
     */
    public List getPeRecExamcourseList(final DetachedCriteria detachedCriteria);
    
    
    public PeRecExamcourse getPeRecExamcourseByName(java.lang.String name);
        
     /**
 	 * 根据条件分页获得招生考试课程
 	 */
 	public Page getPeRecExamcourseByPage(final DetachedCriteria detachedCriteria,
 			final int pageSize, final int startIndex);
 	
 	/**
	 * 根据条件获得招生考试课程总数
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getPeRecExamcoursesTotalCount(final DetachedCriteria detachedCriteria);
}
