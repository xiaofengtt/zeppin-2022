package com.whaty.platform.info.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoNewsType;

public interface InfoNewsTypeDao {

	/**
	 * 保存新闻类型
	 * @param transientInstance
	 * @return
	 */
    public InfoNewsType save(InfoNewsType transientInstance);
    
    /**
     * 根据id列表 删除新闻信息
     * @param ids
     */
    public void deleteInfoNewsTypeByIds(final List ids);
    
    /**
     * 删除单个新闻类型
     * @param persistentInstance
     */
	public void delete(InfoNewsType persistentInstance);
	
	/**
	 * 根据id查找新闻类型
	 * @param id
	 * @return
	 */
    public InfoNewsType getInfoNewsTypeById( java.lang.String id);
    
    /**
     * 根据样例查找新闻;
     * @param instance
     * @return
     */
    public List getInfoNewsTypeByExample(final InfoNewsType instance);
    
    /**
     * 根据条件查找新闻类型
     * @param detachedCriteria
     * @return
     */
     public List getInfoNewsTypeList(final DetachedCriteria detachedCriteria);
     
     /**
 	 * 根据条件分页获得新闻类型
 	 */
 	public Page getInfoNewsTypeByPage(final DetachedCriteria detachedCriteria,
 			final int pageSize, final int startIndex);
 	
 	/**
	 * 根据条件获得新闻总数
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getInfoNewsTypeTotalCount(final DetachedCriteria detachedCriteria);
}
