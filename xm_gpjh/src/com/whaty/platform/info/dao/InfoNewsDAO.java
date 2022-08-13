package com.whaty.platform.info.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoNews;

public interface InfoNewsDAO {
	
	/**
	 * 保存
	 * @param transientInstance
	 */
    public InfoNews save(InfoNews transientInstance);
    
    /**
     * 批量更新活动状态
     */
    public int updateActiveStatusByIds(final List list);
    
    /**
     * 批量更新置顶状态
     */
    public int updateTopStatusByIds(final List list);
    
    /**
     * 批量更新弹出状态
     */
    public int updatePopStatusByIds(final List list);
    
    /**
     * 批量更新弹出状态
     */
    public int updateConfirmStatusByIds(final List list);
    
    /**
     * 根据id列表 删除新闻信息
     * @param ids
     */
    public void deleteInfoNewsByIds(final List ids);
   
    
    /**
     * 删除
     * @param persistentInstance
     */
	public void delete(InfoNews persistentInstance);
	
	/**
	 * 根据id获得新闻信息
	 * @param id
	 * @return
	 */
    public InfoNews getInfoNewsById( java.lang.String id);
    
    /**
     * 根据样例条件获得新闻信息
     * @param instance
     * @return
     */
    public List getInfoNewsByExample(final InfoNews instance);
    
    /**
     * 根据条件查找新闻信息
     * @param detachedCriteria
     * @return
     */
   public List getInfoNewsList(final DetachedCriteria detachedCriteria);
   
   /**
	 * 根据条件分页获得新闻信息
	 */
	public Page getInfoNewsByPage(final DetachedCriteria detachedCriteria,
			final int pageSize, final int startIndex);
	
	/**
	 * 根据条件获得新闻总数
	 * @param detachedCriteria
	 * @return
	 */
	public Integer getInfoNewsTotalCount(final DetachedCriteria detachedCriteria);
    
}
