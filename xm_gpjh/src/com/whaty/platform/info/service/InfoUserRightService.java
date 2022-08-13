package com.whaty.platform.info.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoUserRight;
import com.whaty.platform.info.exception.InfoException;

public interface InfoUserRightService {
	
	/**
	 * 保存
	 * @param transientInstance
	 * @return
	 */
    public InfoUserRight save(InfoUserRight transientInstance)throws InfoException;
    
    /**
     * 根据id列表 删除
     * @param ids
     */
    public int deleteByIds(final List ids)throws InfoException;
    
    /**
     * 删除单个
     * @param persistentInstance
     */
	public void delete(InfoUserRight persistentInstance)throws InfoException;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
    public InfoUserRight getById( String id);
    
    /**
     * 根据样例
     * @param instance
     * @return
     */
    public List getByExample(final InfoUserRight instance);
    
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
 	public int updateColumnByIds(final List ids,final String column,final String value)throws InfoException;
 	
 	/**
 	 * 使用sql查询的接口
 	 * @param sql
 	 * @return
 	 */
 	public List getBySQL(String sql);
 	
 	public List getByHQL(String sql);
 	
 	/**
 	 * 设置用户新闻类型权限
 	 */
 	public int updateInfoUserRight(String userId,List pageNewsTypeIds,List selectedNewsTypeIds)throws InfoException;
 	
 	
 	/**
	 * 根据newtype 列表删除记录
	 */
	public int deleteByNewsTypeIds(List newsTypeIds)throws InfoException;
}
