package com.whaty.platform.vote.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.vote.bean.VotePaper;
import com.whaty.platform.vote.util.exception.VoteException;

public interface VotePaperService {
	/**
	 * 保存
	 * @param transientInstance
	 * @return
	 */
    public VotePaper save(VotePaper transientInstance)throws VoteException;
    
    /**
     * 根据id列表 删除
     * @param ids
     */
    public int deleteByIds(final List ids)throws VoteException;
    
    /**
     * 删除单个
     * @param persistentInstance
     */
	public void delete(VotePaper persistentInstance)throws VoteException;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return
	 */
    public VotePaper getById( String id);
    
    /**
     * 根据样例
     * @param instance
     * @return
     */
    public List getByExample(final VotePaper instance);
    
    /**
     * 根据条件查找
     * @param detachedCriteria
     * @return
     */
     public List getList(final DetachedCriteria detachedCriteria);
     
     /**
 	 * 根据条件分页获得
 	 */
 	public Page getsByPage(final DetachedCriteria detachedCriteria,
 			final int pageSize, final int startIndex);
 	
 	/**
 	 * 批量更新一个字段为某??
 	 * @param ids
 	 * @param column
 	 * @param value
 	 * @return
 	 */
 	public int updateColumnByIds(final List ids,final String column,final String value)throws VoteException;
 	
 	/**
 	 * 使用sql查询的接口
 	 * @param sql
 	 * @return
 	 */
 	public List getBySQL(String sql);
 	
 	public List getByHQL(String sql);
 	
 	public int executeByHQL(final String hql);
 	
 	public int executeBySQL(final String sql);
}
