package com.whaty.platform.entity.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;

public interface AbstractEntityDao<T,PK extends Serializable> {
	/**
	 * 保存
	 * @param transientInstance
	 * @return
	 */
    public T save(T transientInstance);
    
    /**
     * 根据id列表 删除
     * @param ids
     */
    public int deleteByIds(final List ids);
    
    /**
     * 删除单个
     * @param persistentInstance
     */
	public void delete(T persistentInstance);
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
    public T getById( PK id);
    
    /**
     * 根据样例
     * @param instance
     * @return
     */
    public List getByExample(final T instance);
    
    /**
     * 根据条件查找
     * @param detachedCriteria
     * @return
     */
     public List getList(final DetachedCriteria detachedCriteria);
     
     /**
 	 * 根据条件分页获得
 	 */
 	public Page getByPage(final DetachedCriteria detachedCriteria,
 			final int pageSize, final int startIndex);
 	
 	/**
 	 * 批量更新一个字段为某??
 	 * @param ids
 	 * @param column
 	 * @param value
 	 * @return
 	 */
 	public int updateColumnByIds(final List ids,final String column,final String value);
 	
 	/**
 	 * 使用sql查询的接口
 	 * @param sql
 	 * @return
 	 */
 	public List getBySQL(String sql);
 	/**
 	 * 根据sql分页获得
 	 * @param sql
 	 * @param pageSize
 	 * @param startIndex
 	 * @return
 	 */
 	public Page getByPageSQL(String sql, int pageSize, int startIndex);
 	
 	public List getByHQL(String sql);
 	
 	public int executeByHQL(final String hql);
 	
 	public int executeBySQL(final String sql);
}
